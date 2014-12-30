package sk.seges.corpis.appscaffold.model.pap.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic.Kind;

import sk.seges.corpis.appscaffold.model.pap.accessor.BaseObjectAccessor;
import sk.seges.corpis.appscaffold.model.pap.accessor.DomainInterfaceAccessor;
import sk.seges.corpis.appscaffold.model.pap.accessor.PersistentObjectAccessor;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeMirror;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeVariable;
import sk.seges.sesam.core.pap.model.mutable.utils.MutableProcessingEnvironment;
import sk.seges.sesam.core.pap.structure.DefaultPackageValidator.ImplementationType;
import sk.seges.sesam.core.pap.structure.DefaultPackageValidator.LayerType;
import sk.seges.sesam.core.pap.structure.DefaultPackageValidator.LocationType;
import sk.seges.sesam.domain.IMutableDomainObject;

public class DomainDataInterfaceType extends AbstractDataType {

	private static final String SUFFIX = "Data";
	
	public DomainDataInterfaceType(MutableDeclaredType dataDefinition, MutableProcessingEnvironment processingEnv) {
		super(dataDefinition, processingEnv);		

		Element element = processingEnv.getElementUtils().getTypeElement(dataDefinition.getCanonicalName());

		if (getBaseObjects().size() > 1) {
			processingEnv.getMessager().printMessage(Kind.ERROR, "Multiple base objects interfaces are defined in " + domainDataType + ". You should define only one base object interface!", domainDataType.asElement());
		}

		List<MutableDeclaredType> interfaces = getDataInterfaces();

		if (interfaces.size() == 0) {
			if (new PersistentObjectAccessor(element, processingEnv).isEntity()) {
				MutableDeclaredType domainObjectMutableType = processingEnv.getTypeUtils().toMutableType(IMutableDomainObject.class.getName());
				interfaces.add(domainObjectMutableType);
			} else {
				interfaces.add(processingEnv.getTypeUtils().toMutableType(Serializable.class));
			}
		}

		changePackage(dataDefinition.getPackageName() + "." + LocationType.SERVER.getName() + "." + LayerType.MODEL.getName() + "." + ImplementationType.DATA.getName());
		
		setInterfaces(interfaces);

		setKind(MutableTypeKind.INTERFACE);
		
		setDataTypeVariables();
	}
	
	public DomainDataInterfaceType(TypeElement dataInterfaceType, MutableProcessingEnvironment processingEnv) {
		super(null, processingEnv);
		setDelegate(processingEnv.getTypeUtils().toMutableType(dataInterfaceType.asType()));
	}
	
	public List<MutableDeclaredType> getBaseObjects() {
		List<MutableDeclaredType> baseObjects = new ArrayList<MutableDeclaredType>();
		
		for (MutableTypeMirror domainInterface : domainDataType.getInterfaces()) {
			MutableDeclaredType d = (MutableDeclaredType) domainInterface;
			if (new BaseObjectAccessor(((DeclaredType)d.asType()).asElement(), processingEnv).isValid()) {
				baseObjects.add(d);
			}
		}
		
		return baseObjects;
	}

	protected Set<? extends MutableTypeMirror> getDataBounds(Set<? extends MutableTypeMirror> bounds) {
		Set<MutableTypeMirror> result = new HashSet<MutableTypeMirror>();
		
		if (bounds == null) {
			return result;
		}
		
		for (MutableTypeMirror bound: bounds) {
			result.add(getDataObject(bound));
		}
		
		return result;
	}

	protected MutableTypeMirror getDataObject(MutableTypeMirror type) {
		switch (type.getKind()) {
			case CLASS:
			case INTERFACE:
				return getDataObject((MutableDeclaredType)type);
			case TYPEVAR:
				return getDataObject((MutableTypeVariable)type);
		}
		
		return type;
	}
	
	protected MutableTypeVariable getDataObject(MutableTypeVariable typeVariable) {
		Set<? extends MutableTypeMirror> lowerBounds = getDataBounds(typeVariable.getLowerBounds());
		Set<? extends MutableTypeMirror> upperBounds = getDataBounds(typeVariable.getUpperBounds());
		return processingEnv.getTypeUtils().getTypeVariable(typeVariable.getVariable(), lowerBounds.toArray(new MutableTypeMirror[] {}),
				upperBounds.toArray(new MutableTypeMirror[] {}));
	}
	
	protected MutableDeclaredType getDataObject(MutableDeclaredType type) {
		
		if (type.asType() != null) {
			if (new DomainInterfaceAccessor(((DeclaredType)type.asType()).asElement(), processingEnv).isValid()) {
				return new DomainDataInterfaceType(type, processingEnv);
			}
		}
		
		if (type.hasTypeParameters()) {
			List<? extends MutableTypeVariable> typeVariables = type.getTypeVariables();
			MutableTypeVariable[] dataTypeVariables = new MutableTypeVariable[typeVariables.size()];
			
			int i = 0;
			for (MutableTypeVariable typeVariable: typeVariables) {
				dataTypeVariables[i++] = getDataObject(typeVariable);
			}
			
			type.setTypeVariables(dataTypeVariables);
		}
		
		return type;
	}

	public List<MutableDeclaredType> getDataInterfaces() {
		List<MutableDeclaredType> interfaces = new ArrayList<MutableDeclaredType>();
		
		for (MutableTypeMirror domainInterface : domainDataType.getInterfaces()) {
			MutableDeclaredType d = (MutableDeclaredType) domainInterface;
			interfaces.add(getDataObject(d));
		}
		
		return interfaces;
	}
	
	@Override
	protected MutableDeclaredType getDelegate() {
		return domainDataType.clone().addClassSufix(SUFFIX);
	}	
}