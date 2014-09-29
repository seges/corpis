package sk.seges.corpis.appscaffold.model.pap.model;

import sk.seges.corpis.appscaffold.shared.annotation.DomainData;
import sk.seges.sesam.core.pap.model.api.ClassSerializer;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeMirror;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeVariable;
import sk.seges.sesam.core.pap.model.mutable.api.MutableWildcardType;
import sk.seges.sesam.core.pap.model.mutable.delegate.DelegateMutableDeclaredType;
import sk.seges.sesam.core.pap.utils.MethodHelper;
import sk.seges.sesam.pap.model.model.ConfigurationContext;
import sk.seges.sesam.pap.model.model.EnvironmentContext;
import sk.seges.sesam.pap.model.model.TransferObjectProcessingEnvironment;
import sk.seges.sesam.pap.model.model.api.domain.DomainDeclaredType;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;
import java.util.*;

public class DataTypeResolver {

	private final EnvironmentContext<TransferObjectProcessingEnvironment> envContext;
	
	public DataTypeResolver(EnvironmentContext<TransferObjectProcessingEnvironment> envContext) {
		this.envContext = envContext;
	}

	public void findDomainData(MutableDeclaredType declaredType, List<MutableDeclaredType> domainDataTypes) {
		
		if (declaredType.getAnnotation(DomainData.class) != null) {
			domainDataTypes.add(declaredType);
		}

		//We need to iterate over interfaces firstly - for cases that some data interfaces will be in hierarchy we have
		//to find most specified data interface
		for (MutableTypeMirror interfaces : declaredType.getInterfaces()) {
			findDomainData((MutableDeclaredType)interfaces, domainDataTypes);
		}

		MutableDeclaredType superClass;
		
		if (declaredType instanceof DelegateMutableDeclaredType) {
			superClass = ((DelegateMutableDeclaredType)declaredType).ensureDelegateType().getSuperClass();
		} else {
			superClass = declaredType.getSuperClass();
		}

		if (superClass != null) {
			findDomainData(superClass, domainDataTypes);
		}
	}

	public List<MutableDeclaredType> getDomainData(MutableDeclaredType declaredType) {
		
		List<MutableDeclaredType> result = new ArrayList<MutableDeclaredType>();
		List<MutableDeclaredType> dataTypes = new ArrayList<MutableDeclaredType>();
		findDomainData(declaredType, dataTypes);

		for (MutableDeclaredType dataType: dataTypes) {
			if (!hasCustomProperties(declaredType, dataType)) {
				result.add(dataType);
			}
		}
		
		return result;
	}

	private static Set<String> getMethodNames(TypeElement typeElement) {
		assert typeElement != null;

		Set<String> result = new HashSet<String>();
		List<ExecutableElement> methods = ElementFilter.methodsIn(typeElement.getEnclosedElements());

		for (ExecutableElement method: methods) {
			result.add(method.getSimpleName().toString());
		}

		if (typeElement.getSuperclass() != null && typeElement.getSuperclass().getKind().equals(TypeKind.DECLARED)) {
			result.addAll(getMethodNames((TypeElement) ((DeclaredType)typeElement.getSuperclass()).asElement()));
		}

		for (TypeMirror interfaceType: typeElement.getInterfaces()) {
			if (interfaceType.getKind().equals(TypeKind.DECLARED)) {
				result.addAll(getMethodNames((TypeElement) ((DeclaredType)interfaceType).asElement()));
			}
		}

		return result;
	}

	public boolean hasCustomProperties(Element domainElement, Element dataElement) {
		List<ExecutableElement> methods = ElementFilter.methodsIn(domainElement.getEnclosedElements());

		Set<String> dataMethodNames = getMethodNames((TypeElement) dataElement);

		for (ExecutableElement method: methods) {
			boolean isGetter = MethodHelper.isGetterMethod(method);
			boolean isPublic = method.getModifiers().contains(Modifier.PUBLIC);

			if (isGetter && isPublic) {
				if (!dataMethodNames.contains(method.getSimpleName().toString())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private Element toElement(MutableDeclaredType type) {
		Element domainElement = type.asElement();
		
		if (domainElement == null) {
			TypeMirror fromMutableType = envContext.getProcessingEnv().getTypeUtils().fromMutableType(type);
			domainElement = ((DeclaredType)fromMutableType).asElement();
		}
		
		return domainElement;
	}

	public boolean hasCustomProperties(MutableDeclaredType domainDeclared, MutableDeclaredType dataType) {
		
		if (!domainDeclared.toString(ClassSerializer.CANONICAL, false).equals(dataType.toString(ClassSerializer.CANONICAL, false))) {
			
			Element domainElement = toElement(domainDeclared);
			
			if (domainElement == null) {
				return false;
			}
			
			Element dataElement = toElement(dataType);

			if (dataElement == null) {
				return false;
			}

			return hasCustomProperties(domainElement, dataElement);

		} else if (domainDeclared.hasTypeParameters() && dataType.hasTypeParameters()) {
			
			if (domainDeclared.getTypeVariables().size() != dataType.getTypeVariables().size()) {
				envContext.getProcessingEnv().getMessager().printMessage(Kind.ERROR, "Domain type " + domainDeclared + " has invalid data type found: " + dataType + ". It seems like a bug in the processors!");
				return true;
			}
			
			for (int i = 0; i < domainDeclared.getTypeVariables().size(); i++) {
				MutableTypeVariable domainTypeVariable = domainDeclared.getTypeVariables().get(i);
				MutableTypeVariable dataTypeVariable = dataType.getTypeVariables().get(i);
				
				if (domainTypeVariable.getLowerBounds().size() != dataTypeVariable.getLowerBounds().size()) {
					envContext.getProcessingEnv().getMessager().printMessage(Kind.ERROR, "Domain type " + domainDeclared + " has invalid data type found: " + dataType + ". It seems like a bug in the processors!");
					return true;
				}

				if (domainTypeVariable.getUpperBounds().size() != dataTypeVariable.getUpperBounds().size()) {
					envContext.getProcessingEnv().getMessager().printMessage(Kind.ERROR, "Domain type " + domainDeclared + " has invalid data type found: " + dataType + ". It seems like a bug in the processors!");
					return true;
				}
				
				if (processBounds(domainTypeVariable.getLowerBounds(), dataTypeVariable.getLowerBounds())) {
					return true;
				}

				if (processBounds(domainTypeVariable.getLowerBounds(), dataTypeVariable.getLowerBounds())) {
					return true;
				}

			}
		}
		
		return false;
	}

	private boolean processBounds(Set<? extends MutableTypeMirror> domainBounds, Set<? extends MutableTypeMirror> dataBounds) {
		if (domainBounds.size() > 0) {
			Iterator<? extends MutableTypeMirror> domainIterator = domainBounds.iterator();
			Iterator<? extends MutableTypeMirror> dataIterator = dataBounds.iterator();
			
			while (domainIterator.hasNext()) {
				MutableTypeMirror domain = domainIterator.next();
				
				DomainDeclaredType domainDeclaredType;
				
				if (!(domain instanceof DomainDeclaredType)) {
					domainDeclaredType = (DomainDeclaredType) envContext.getProcessingEnv().getTransferObjectUtils().getDomainType(domain);
				} else {
					domainDeclaredType = (DomainDeclaredType) domain;
				}
				
				if (hasCustomProperties(domainDeclaredType, (MutableDeclaredType)dataIterator.next())) {
					return true;
				}
			}
		}
		
		return false;
	}

	public DomainDeclaredType getDomainDataType(DomainDeclaredType domainType, MutableDeclaredType dtoType, ConfigurationContext configurationContext) {
		List<MutableDeclaredType> result = getDomainData(domainType);
		
		DomainDeclaredType domainDeclared = domainType;
		
		if (result.size() > 0) {
			domainDeclared = new DataDomainDeclared(result.get(0), dtoType, envContext, configurationContext);
			domainDeclared = replaceTypeParamsByWildcard(domainDeclared);
		} else {
			List<MutableTypeVariable> typeParams = new LinkedList<MutableTypeVariable>();
			
			for (MutableTypeVariable typeVariable: domainType.getTypeVariables()) {

				Set<? extends MutableTypeMirror> lbounds;
				
				if (typeVariable.getLowerBounds().size() > 0) {
					lbounds = processBounds(typeVariable.getLowerBounds());
				} else {
					lbounds = new HashSet<MutableTypeMirror>();
				}

				Set<? extends MutableTypeMirror> ubounds;

				if (typeVariable.getUpperBounds().size() > 0) {
					ubounds = processBounds(typeVariable.getUpperBounds());
				} else {
					ubounds = new HashSet<MutableTypeMirror>();
				}
				typeParams.add(envContext.getProcessingEnv().getTypeUtils().getTypeVariable(typeVariable.getVariable(), ubounds.toArray(new MutableTypeMirror[ubounds.size()]), lbounds.toArray(new MutableTypeMirror[] {})));
			}
			
			DomainDeclaredType superClass = domainType.getSuperClass();
			List<? extends MutableTypeMirror> interfaces = domainDeclared.getInterfaces();
			
			domainType.setTypeVariables(typeParams.toArray(new MutableTypeVariable[typeParams.size()]));
			
			domainType.setSuperClass(superClass);
			domainDeclared.setInterfaces(interfaces);
			
			domainDeclared = domainType;
		}
		
		if (!hasCustomProperties(domainType, domainDeclared)) {
			return domainDeclared;
		}

		return domainType;
	}

	private Set<? extends MutableTypeMirror> processBounds(Set<? extends MutableTypeMirror> bounds) {
		if (bounds.size() > 0) {
			Set<MutableTypeMirror> newBounds = new HashSet<MutableTypeMirror>();
			Iterator<? extends MutableTypeMirror> iterator = bounds.iterator();

			while (iterator.hasNext()) {
				MutableTypeMirror next = iterator.next();

				if (next.getKind().isDeclared()) {

					List<MutableDeclaredType> dataInterfaces = new ArrayList<MutableDeclaredType>();
					findDomainData(((MutableDeclaredType)next), dataInterfaces);

					if (dataInterfaces.size() > 0) {
						MutableDeclaredType dtoType = (MutableDeclaredType)envContext.getProcessingEnv().getTransferObjectUtils().getDomainType(next).getDto();
						DomainDeclaredType domainDeclared = new DataDomainDeclared(dataInterfaces.get(0), dtoType, envContext, null/*, configurationContext*/);
						domainDeclared.setTypeVariables(new MutableTypeVariable[]{});

						if (!hasCustomProperties((MutableDeclaredType) next, dataInterfaces.get(0))) {
							newBounds.add(domainDeclared);
						} else {
							newBounds.add(next);
						}

					} else {
						newBounds.add(next);
					}
				} else {
					newBounds.add(next);
				}
			}
			
			return newBounds;
		}

		return bounds;
	}

	private DomainDeclaredType replaceTypeParamsByWildcard(DomainDeclaredType domainDeclared) {
		if (domainDeclared.getTypeVariables().size() > 0) {
			MutableTypeVariable[] typeVariables = new MutableTypeVariable[domainDeclared.getTypeVariables().size()];
			for (int i = 0; i < domainDeclared.getTypeVariables().size(); i++) {
				typeVariables[i] = envContext.getProcessingEnv().getTypeUtils().getTypeVariable(MutableWildcardType.WILDCARD_NAME);
			}
			domainDeclared.setTypeVariables(typeVariables);
		}
		return domainDeclared;
	}
}