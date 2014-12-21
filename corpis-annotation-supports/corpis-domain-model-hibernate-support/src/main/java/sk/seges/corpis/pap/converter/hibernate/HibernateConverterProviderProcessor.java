package sk.seges.corpis.pap.converter.hibernate;

import sk.seges.corpis.pap.converter.hibernate.model.HibernateConverterProviderType;
import sk.seges.corpis.pap.converter.hibernate.resolver.HibernateConverterParameterResolver;
import sk.seges.corpis.pap.model.printer.converter.HibernateConverterProviderPrinter;
import sk.seges.sesam.core.pap.model.ConverterConstructorParameter;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.writer.FormattedPrintWriter;
import sk.seges.sesam.pap.converter.ConverterProviderProcessor;
import sk.seges.sesam.pap.converter.printer.api.ProviderElementPrinter;
import sk.seges.sesam.pap.converter.printer.converterprovider.DomainMethodConverterProviderPrinter;
import sk.seges.sesam.pap.converter.printer.converterprovider.DtoMethodConverterProviderPrinter;
import sk.seges.sesam.pap.model.model.ConverterTypeElement;
import sk.seges.sesam.pap.model.model.TransferObjectProcessingEnvironment;
import sk.seges.sesam.pap.model.printer.converter.ConverterProviderPrinter;
import sk.seges.sesam.pap.model.printer.converter.ConverterTargetType;
import sk.seges.sesam.pap.model.resolver.CacheableConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.ProviderConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.ProviderConstructorParametersResolverProvider.UsageType;
import sk.seges.sesam.pap.model.resolver.api.ConverterConstructorParametersResolver;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import java.util.ArrayList;
import java.util.List;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class HibernateConverterProviderProcessor extends ConverterProviderProcessor {

	@Override
	protected MutableDeclaredType[] getOutputClasses(RoundContext context) {
		return new MutableDeclaredType[] {
				new HibernateConverterProviderType(context.getMutableType(), processingEnv)
		};
	}

    @Override
	protected ProviderConstructorParametersResolverProvider getParametersResolverProvider() {
		return new CacheableConverterConstructorParametersResolverProvider() {

			@Override
			public ConverterConstructorParametersResolver constructParameterResolver(UsageType usageType) {
				return new HibernateConverterParameterResolver(processingEnv);
			}
		};
		
	}

	@Override
	protected ConverterProviderPrinter getConverterProviderPrinter(TransferObjectProcessingEnvironment processingEnv) {
		return new HibernateConverterProviderPrinter(processingEnv, getParametersResolverProvider(), UsageType.PROVIDER_INSIDE_USAGE) {

			@Override
			protected List<ConverterConstructorParameter> getConverterProviderMethodAdditionalParameters(ConverterTypeElement converterTypeElement, ConverterTargetType converterTargetType) {
				return new ArrayList<ConverterConstructorParameter>();
			}
		};
	}

	protected ProviderElementPrinter[] getNestedPrinters(FormattedPrintWriter pw) {
		return new ProviderElementPrinter[] {
			new DomainMethodConverterProviderPrinter(processingEnv, pw, ensureConverterProviderPrinter(processingEnv)),
			new DtoMethodConverterProviderPrinter(processingEnv, pw, ensureConverterProviderPrinter(processingEnv))
		};
	}
}