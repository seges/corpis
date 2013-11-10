package sk.seges.corpis.pap.service.hibernate.printer.converterprovider;

import sk.seges.sesam.core.pap.model.mutable.utils.MutableProcessingEnvironment;
import sk.seges.sesam.pap.model.resolver.ConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.service.model.ConverterProviderContextType;
import sk.seges.sesam.pap.service.printer.converterprovider.ConverterProviderContextPrinterDelegate;

public class HibernateConverterProviderContextPrinterDelegate extends ConverterProviderContextPrinterDelegate {

	public HibernateConverterProviderContextPrinterDelegate(MutableProcessingEnvironment processingEnv, ConverterConstructorParametersResolverProvider parametersResolverProvider) {
		super(processingEnv, parametersResolverProvider);
	}

	public void initialize(MutableProcessingEnvironment processingEnv, ConverterProviderContextType converterProviderType, ConverterConstructorParametersResolverProvider.UsageType usageType) {
		this.converterProviderContextType = converterProviderType;
		this.printerDelegate = new HibernateConverterProviderPrinterDelegate(parametersResolverProvider);
		this.printerDelegate.initialize(processingEnv, converterProviderType, usageType);
	}

}
