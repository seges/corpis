package sk.seges.corpis.pap.model.hibernate;

import sk.seges.corpis.pap.converter.hibernate.TransactionalConverter;
import sk.seges.sesam.pap.model.model.ConfigurationTypeElement;
import sk.seges.sesam.pap.model.model.ConverterTypeElement;
import sk.seges.sesam.pap.model.model.EnvironmentContext;
import sk.seges.sesam.pap.model.model.TransferObjectProcessingEnvironment;

public class TransactionalConverterTypeElement extends ConverterTypeElement {

	public TransactionalConverterTypeElement(ConfigurationTypeElement configurationTypeElement, EnvironmentContext<TransferObjectProcessingEnvironment> envContext) {
		super(configurationTypeElement, envContext);
	}

	@Override
	protected Class<?> getGeneratedSuperClass() {
		return TransactionalConverter.class;
	}
}
