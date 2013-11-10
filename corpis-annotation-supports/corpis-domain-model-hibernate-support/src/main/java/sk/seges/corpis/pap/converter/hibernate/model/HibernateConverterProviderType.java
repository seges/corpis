package sk.seges.corpis.pap.converter.hibernate.model;

import sk.seges.corpis.pap.converter.hibernate.provider.AbstractTransactionalConverterProvider;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.model.mutable.utils.MutableProcessingEnvironment;
import sk.seges.sesam.pap.converter.model.ConverterProviderType;

import javax.lang.model.element.Element;

public class HibernateConverterProviderType extends ConverterProviderType {

	public HibernateConverterProviderType(Element element, MutableProcessingEnvironment processingEnv) {
		super(element, processingEnv);
	}

	public HibernateConverterProviderType(MutableDeclaredType mutableType, MutableProcessingEnvironment processingEnv) {
		super(mutableType, processingEnv);
	}

	@Override
	protected Class<?> getProviderSuperClass() {
		return AbstractTransactionalConverterProvider.class;
	}
}
