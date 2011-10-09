package sk.seges.sesam.pap.model.hibernate;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

import sk.seges.sesam.core.pap.writer.FormattedPrintWriter;
import sk.seges.sesam.pap.model.TransferObjectConverterProcessor;
import sk.seges.sesam.pap.model.hibernate.printer.method.HibernateCopyFromDtoPrinter;
import sk.seges.sesam.pap.model.hibernate.printer.method.HibernateCopyToDtoPrinter;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateEntityResolver;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateParameterResolver;
import sk.seges.sesam.pap.model.model.api.ElementHolderTypeConverter;
import sk.seges.sesam.pap.model.printer.api.TransferObjectElementPrinter;
import sk.seges.sesam.pap.model.printer.equals.ConverterEqualsPrinter;
import sk.seges.sesam.pap.model.resolver.api.EntityResolver;
import sk.seges.sesam.pap.model.resolver.api.ParametersResolver;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class HibernateTransferObjectConverterProcessor extends TransferObjectConverterProcessor {

	@Override
	public synchronized void init(ProcessingEnvironment pe) {
		super.init(pe);
	}

	@Override
	protected ParametersResolver getParametersResolver() {
		return new HibernateParameterResolver(processingEnv);
	}
	
	@Override
	protected ElementHolderTypeConverter getElementTypeConverter() {
		return new HibernatePersistentElementHolderConverter(processingEnv);
	}

	@Override
	protected EntityResolver getEntityResolver() {
		return new HibernateEntityResolver(processingEnv);
	}

	@Override
	protected TransferObjectElementPrinter[] getElementPrinters(FormattedPrintWriter pw) {
		return new TransferObjectElementPrinter[] {
				new ConverterEqualsPrinter(converterProviderPrinter, getEntityResolver(), processingEnv, pw),
				new HibernateCopyToDtoPrinter(converterProviderPrinter, getElementTypeConverter(), getEntityResolver(), getParametersResolver(), roundEnv, processingEnv, pw),
				new HibernateCopyFromDtoPrinter(converterProviderPrinter, getEntityResolver(), getParametersResolver(), roundEnv, processingEnv, pw)
		};
	}

}