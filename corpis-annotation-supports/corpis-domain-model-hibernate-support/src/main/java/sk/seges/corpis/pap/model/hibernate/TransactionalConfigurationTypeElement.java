package sk.seges.corpis.pap.model.hibernate;

import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.pap.model.model.*;
import sk.seges.sesam.pap.model.model.api.domain.DomainDeclaredType;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

public class TransactionalConfigurationTypeElement extends ConfigurationTypeElement {

	public TransactionalConfigurationTypeElement(MutableDeclaredType domainType, MutableDeclaredType dtoType, Element configurationElement, EnvironmentContext<TransferObjectProcessingEnvironment> envContext, ConfigurationContext configurationContext) {
		super(domainType, dtoType, configurationElement, envContext, configurationContext);
	}

	public TransactionalConfigurationTypeElement(ExecutableElement configurationElementMethod, DomainDeclaredType returnType, EnvironmentContext<TransferObjectProcessingEnvironment> envContext, ConfigurationContext configurationContext) {
		super(configurationElementMethod, returnType, envContext, configurationContext);
	}

	public TransactionalConfigurationTypeElement(Element configurationElement, EnvironmentContext<TransferObjectProcessingEnvironment> envContext, ConfigurationContext configurationContext) {
		super(configurationElement, envContext, configurationContext);
	}

	@Override
	protected ConverterTypeElement getConverterTypeElement() {
		return new TransactionalConverterTypeElement(this, envContext);
	}

}
