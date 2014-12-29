package sk.seges.corpis.appscaffold.model.pap;

import java.util.*;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;

import sk.seges.corpis.appscaffold.model.pap.accessor.DomainInterfaceAccessor;
import sk.seges.corpis.appscaffold.model.pap.model.DomainDataInterfaceType;
import sk.seges.sesam.core.pap.model.api.ClassSerializer;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableExecutableType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeMirror;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeMirror.MutableTypeKind;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeVariable;
import sk.seges.sesam.core.pap.model.mutable.api.element.MutableVariableElement;
import sk.seges.sesam.core.pap.printer.ConstantsPrinter;
import sk.seges.sesam.core.pap.processor.MutableAnnotationProcessor;
import sk.seges.sesam.core.pap.utils.MethodHelper;
import sk.seges.sesam.core.pap.utils.ProcessorUtils;
import sk.seges.sesam.pap.model.accessor.ReadOnlyAccessor;
import sk.seges.sesam.pap.model.annotation.ReadOnly;

public abstract class AbstractDataProcessor extends MutableAnnotationProcessor {

	@Override
	protected void processElement(ProcessorContext context) {
		new ConstantsPrinter(context.getPrintWriter(), processingEnv).copyConstants(context.getTypeElement());
	}

	protected MutableExecutableType toPrintableElement(TypeElement owner, ExecutableElement method) {
		MutableExecutableType mutableExecutableType = processingEnv.getElementUtils().toMutableElement(method).asType();
		mutableExecutableType.setReturnType(toPrintableType(owner, castToDomainDataInterface(method.getReturnType())));

		List<MutableVariableElement> printableParameters = new ArrayList<MutableVariableElement>();

		for (MutableVariableElement parameter: mutableExecutableType.getParameters()) {
			printableParameters.add(processingEnv.getElementUtils().getParameterElement(toPrintableType(owner, castToDomainDataInterface(parameter.asType())), parameter.getSimpleName()));
		}
		mutableExecutableType.setParameters(printableParameters);

		return mutableExecutableType;
	}

	private Set<MutableTypeMirror> toPrintableTypes(TypeElement owner, Set<? extends MutableTypeMirror> bounds) {
		Set<MutableTypeMirror> result = new LinkedHashSet<MutableTypeMirror>();
		for (MutableTypeMirror bound: bounds) {
			result.add(toPrintableType(owner, bound));
		}
		return result;
	}

	protected boolean isReadOnlyMethod(ReadOnlyAccessor readOnlyAccessor, ExecutableElement method) {
		return readOnlyAccessor.isReadonly() && (!MethodHelper.toField(method).equals(method.getSimpleName().toString()) ||
				ReadOnly.PropertyType.METHOD.equals(readOnlyAccessor.getPropertyType()));
	}

	protected boolean isPrimitiveBoolean(MutableTypeMirror type) {
		return type.toString(ClassSerializer.CANONICAL).equals(TypeKind.BOOLEAN.toString().toLowerCase());
	}

	protected MutableTypeMirror toPrintableType(TypeElement owner, MutableTypeMirror mutableType) {
		
		if (mutableType.getKind().equals(MutableTypeKind.TYPEVAR)) {
			MutableTypeVariable mutableTypeVariable = ((MutableTypeVariable)mutableType);
			
			if (mutableTypeVariable.getVariable() != null && mutableTypeVariable.getVariable().length() > 0) {
				TypeMirror erasuredTypeVariable = ProcessorUtils.erasure(owner, mutableTypeVariable.getVariable());
				if (erasuredTypeVariable != null) {
					return processingEnv.getTypeUtils().getTypeVariable(null, processingEnv.getTypeUtils().toMutableType(erasuredTypeVariable));
				}
				return processingEnv.getTypeUtils().getTypeVariable(mutableTypeVariable.getVariable());
			}

			if (mutableTypeVariable.getUpperBounds().size() > 0 ||
				mutableTypeVariable.getLowerBounds().size() > 0) {
				mutableTypeVariable = mutableTypeVariable.clone();
			}
			
			if (mutableTypeVariable.getLowerBounds().size() > 0) {
				mutableTypeVariable.setLowerBounds(toPrintableTypes(owner, mutableTypeVariable.getLowerBounds()));
			}

			if (mutableTypeVariable.getUpperBounds().size() > 0) {
				mutableTypeVariable.setUpperBounds(toPrintableTypes(owner, mutableTypeVariable.getUpperBounds()));
			}

			return mutableTypeVariable;
		}
		
		if (mutableType.getKind().isDeclared() && ((MutableDeclaredType)mutableType).getTypeVariables().size() > 0) {
			List<? extends MutableTypeVariable> typeVariables = ((MutableDeclaredType)mutableType).getTypeVariables();
			
			List<MutableTypeVariable> printableTypeVariables = new ArrayList<MutableTypeVariable>();
					
			for (MutableTypeVariable typeVariable: typeVariables) {
				printableTypeVariables.add((MutableTypeVariable) toPrintableType(owner, typeVariable));
			}
			
			return ((MutableDeclaredType)mutableType).clone().setTypeVariables(printableTypeVariables.toArray(new MutableTypeVariable[] {}));
		}
		
		return mutableType;
	}

	protected MutableTypeMirror castToDomainDataInterface(TypeMirror type) {
		
		if (type == null) {
			return null;
		}

		MutableTypeMirror mutableTypeMirror = processingEnv.getTypeUtils().toMutableType(type);

		MutableTypeMirror result = castToDomainDataInterface(mutableTypeMirror);

		if (result == null) {
			return mutableTypeMirror;
		}

		return result;
	}

	protected MutableTypeMirror castToDomainDataInterface(MutableTypeMirror mutableType) {

		TypeMirror type = processingEnv.getTypeUtils().fromMutableType(mutableType);

		if (type == null) {
			return null;
		}

		if (type.getKind().equals(TypeKind.DECLARED)) {
			Element element = ((DeclaredType)type).asElement();
			if (new DomainInterfaceAccessor(element, processingEnv).isValid()) {
				mutableType = new DomainDataInterfaceType((MutableDeclaredType)mutableType, processingEnv);
			}
			
			List<MutableTypeVariable> arguments = new LinkedList<MutableTypeVariable>();
			
			for (TypeMirror typeArgument: ((DeclaredType)type).getTypeArguments()) {
				switch (typeArgument.getKind()) {
				case TYPEVAR:
					//List<T extends DistributionItem>
					TypeVariable typeVariable = ((TypeVariable)typeArgument);
					arguments.add(processingEnv.getTypeUtils().getTypeVariable(typeVariable.asElement().getSimpleName().toString(), castToDomainDataInterface(typeVariable.getUpperBound()), castToDomainDataInterface(typeVariable.getLowerBound())));
					break;
				case WILDCARD:
					//List<? extends DistributionItem>
					WildcardType wildcardType = ((WildcardType)typeArgument);
					arguments.add(processingEnv.getTypeUtils().getWildcardType(castToDomainDataInterface(wildcardType.getExtendsBound()), castToDomainDataInterface(wildcardType.getSuperBound())));
					break;
				case DECLARED:
					//List<DistributionItem>
					DeclaredType declaredType = ((DeclaredType)typeArgument);
					arguments.add(processingEnv.getTypeUtils().getTypeVariable(null, castToDomainDataInterface(declaredType)));
					break;
				default:
					throw new RuntimeException("Unsupported argument kind " + typeArgument.getKind() + " !");
				}
			}
			
			((MutableDeclaredType)mutableType).setTypeVariables(arguments.toArray(new MutableTypeVariable[] {}));
		}

		return mutableType;
	}
}