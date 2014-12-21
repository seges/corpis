package sk.seges.corpis.pap.service.hibernate.printer.converterprovider;

import sk.seges.corpis.server.model.converter.provider.AbstractContextualConverterProvider;
import sk.seges.sesam.pap.converter.printer.converterprovider.ConverterProviderContextTypePrinter;
import sk.seges.sesam.pap.model.resolver.ProviderConstructorParametersResolverProvider;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;

public class HibernateConverterProviderTypeContextPrinter extends ConverterProviderContextTypePrinter {

    public HibernateConverterProviderTypeContextPrinter(ProviderConstructorParametersResolverProvider parametersResolverProvider) {
		super(parametersResolverProvider);
	}

	protected Class<? extends ConverterProviderContext> getResultClass() {
		return AbstractContextualConverterProvider.class;
	}
}