package sk.seges.corpis.pap.service.hibernate.printer.converterprovider;

import sk.seges.sesam.core.pap.model.mutable.utils.MutableProcessingEnvironment;
import sk.seges.sesam.pap.converter.printer.base.ProviderTypePrinter;
import sk.seges.sesam.pap.model.resolver.ProviderConstructorParametersResolverProvider;
import sk.seges.sesam.pap.service.printer.converterprovider.ConverterProviderContextPrinter;

public class HibernateConverterProviderContextPrinter extends ConverterProviderContextPrinter {

    public HibernateConverterProviderContextPrinter(MutableProcessingEnvironment processingEnv, ProviderConstructorParametersResolverProvider parametersResolverProvider) {
		super(processingEnv, parametersResolverProvider);
	}

    @Override
    protected ProviderTypePrinter getProviderTypePrinter(ProviderConstructorParametersResolverProvider parametersResolverProvider) {
        return new HibernateConverterProviderTypeContextPrinter(parametersResolverProvider);
    }

}
