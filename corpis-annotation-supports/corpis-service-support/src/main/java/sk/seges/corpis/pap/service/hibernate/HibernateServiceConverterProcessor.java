package sk.seges.corpis.pap.service.hibernate;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

import sk.seges.corpis.pap.model.printer.converter.HibernateConverterProviderPrinter;
import sk.seges.corpis.pap.model.printer.converter.HibernateServiceConverterProviderParameterResolver;
import sk.seges.corpis.pap.service.hibernate.printer.HibernateServiceMethodConverterPrinter;
import sk.seges.corpis.pap.service.hibernate.printer.ServiceConverterProviderMethodPrinter;
import sk.seges.sesam.core.pap.writer.FormattedPrintWriter;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateServiceParameterResolver;
import sk.seges.sesam.pap.model.printer.converter.ConverterProviderPrinter;
import sk.seges.sesam.pap.model.resolver.api.ConverterConstructorParametersResolver;
import sk.seges.sesam.pap.service.ServiceConverterProcessor;
import sk.seges.sesam.pap.service.model.ServiceTypeElement;
import sk.seges.sesam.pap.service.printer.ConverterParameterFieldPrinter;
import sk.seges.sesam.pap.service.printer.LocalServiceFieldPrinter;
import sk.seges.sesam.pap.service.printer.ServiceConstructorBodyPrinter;
import sk.seges.sesam.pap.service.printer.ServiceConstructorDefinitionPrinter;
import sk.seges.sesam.pap.service.printer.api.ServiceConverterElementPrinter;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class HibernateServiceConverterProcessor extends ServiceConverterProcessor {

	@Override
	protected ConverterConstructorParametersResolver getParametersResolver() {
		return new HibernateServiceParameterResolver(processingEnv);
	}
	
	protected ConverterConstructorParametersResolver getConverterProviderParametersResolver(ServiceTypeElement serviceTypeElement) {
		return new HibernateServiceConverterProviderParameterResolver(processingEnv, serviceTypeElement);
	}

	@Override
	protected ServiceConverterElementPrinter[] getElementPrinters(FormattedPrintWriter pw, ServiceTypeElement serviceTypeElement) {
		return new ServiceConverterElementPrinter[] {
				new LocalServiceFieldPrinter(pw),
				new ConverterParameterFieldPrinter(processingEnv, getParametersFilter(), getParametersResolver(), pw),
				new ServiceConstructorDefinitionPrinter(processingEnv, getParametersFilter(), getParametersResolver(), pw),
				new ServiceConstructorBodyPrinter(processingEnv, getParametersFilter(), getParametersResolver(), pw),
				new HibernateServiceMethodConverterPrinter(processingEnv, getParametersResolver(), pw, getConverterProviderPrinter(pw)),
				new ServiceConverterProviderMethodPrinter(processingEnv, getConverterProviderParametersResolver(serviceTypeElement), pw, converterProviderPrinter)
		};
	}
	
	@Override
	protected ConverterProviderPrinter getConverterProviderPrinter(FormattedPrintWriter pw) {
		return new HibernateConverterProviderPrinter(pw, processingEnv, getParametersResolver());
	}
}