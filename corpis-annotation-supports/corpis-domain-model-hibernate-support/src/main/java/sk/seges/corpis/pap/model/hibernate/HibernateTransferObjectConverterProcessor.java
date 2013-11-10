package sk.seges.corpis.pap.model.hibernate;

import sk.seges.corpis.pap.model.hibernate.printer.method.HibernateCopyFromDtoPrinter;
import sk.seges.corpis.pap.model.hibernate.printer.method.HibernateCopyToDtoPrinter;
import sk.seges.corpis.pap.model.hibernate.resolver.HibernateEntityResolver;
import sk.seges.sesam.core.pap.writer.FormattedPrintWriter;
import sk.seges.sesam.pap.model.TransferObjectConverterProcessor;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateConverterParameterResolver;
import sk.seges.sesam.pap.model.model.ConfigurationContext;
import sk.seges.sesam.pap.model.model.ConfigurationTypeElement;
import sk.seges.sesam.pap.model.model.api.ElementHolderTypeConverter;
import sk.seges.sesam.pap.model.printer.api.TransferObjectElementPrinter;
import sk.seges.sesam.pap.model.printer.equals.ConverterEqualsPrinter;
import sk.seges.sesam.pap.model.resolver.CacheableConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.ConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.api.ConverterConstructorParametersResolver;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class HibernateTransferObjectConverterProcessor extends TransferObjectConverterProcessor {

	public HibernateTransferObjectConverterProcessor() {
		int a = 0;
	}

	@Override
	public synchronized void init(ProcessingEnvironment pe) {
		super.init(pe);
	}

	@Override
	protected ConfigurationTypeElement getConfigurationTypeElement(TypeElement typeElement) {
		ConfigurationContext configurationContext = new ConfigurationContext(environmentContext.getConfigurationEnv());
		TransactionalConfigurationTypeElement configurationTypeElement = new TransactionalConfigurationTypeElement(typeElement, getEnvironmentContext(), configurationContext);
		configurationContext.addConfiguration(configurationTypeElement);

		return configurationTypeElement;
	}

	@Override
	protected ConverterConstructorParametersResolverProvider getParametersResolverProvider() {
		return new CacheableConverterConstructorParametersResolverProvider() {
			
			@Override
			public ConverterConstructorParametersResolver constructParameterResolver(UsageType usageType) {
				return new HibernateConverterParameterResolver(processingEnv);
			}
		};
	}
	
	@Override
	protected ElementHolderTypeConverter getElementTypeConverter() {
		return new HibernatePersistentElementHolderConverter(processingEnv);
	}

	@Override
	protected HibernateEntityResolver getEntityResolver() {
		return new HibernateEntityResolver(processingEnv);
	}

	@Override
	protected TransferObjectElementPrinter[] getElementPrinters(FormattedPrintWriter pw) {
		return new TransferObjectElementPrinter[] {
				new ConverterEqualsPrinter(converterProviderPrinter, getEntityResolver(), getParametersResolverProvider(), processingEnv, pw),
				new HibernateCopyToDtoPrinter(converterProviderPrinter, getElementTypeConverter(), getEntityResolver(), getParametersResolverProvider(), roundEnv, processingEnv, pw),
				new HibernateCopyFromDtoPrinter(nestedInstances, converterProviderPrinter, getEntityResolver(), getParametersResolverProvider(), roundEnv, processingEnv, pw)
		};
	}
}