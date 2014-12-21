package sk.seges.corpis.pap.service.hibernate;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

import sk.seges.corpis.pap.model.printer.converter.HibernateConverterProviderPrinter;
import sk.seges.corpis.pap.service.hibernate.printer.HibernateServiceMethodConverterPrinter;
import sk.seges.sesam.core.pap.model.api.PropagationType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableReferenceType;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateConverterProviderParameterResolver;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateServiceParameterResolver;
import sk.seges.sesam.pap.model.printer.converter.ConverterProviderPrinter;
import sk.seges.sesam.pap.model.resolver.CacheableConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.ProviderConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.ProviderConstructorParametersResolverProvider.UsageType;
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

	private ProviderConstructorParametersResolverProvider converterConstructorParametersResolverProvider;
	
	@Override
	protected ProviderConstructorParametersResolverProvider getParametersResolverProvider(final ServiceTypeElement serviceTypeElement) {
		
		if (converterConstructorParametersResolverProvider == null) {
			converterConstructorParametersResolverProvider = new CacheableConverterConstructorParametersResolverProvider() {
			
				@Override
				public ConverterConstructorParametersResolver constructParameterResolver(UsageType usageType) {
					switch (usageType) {
						case PROVIDER_OUTSIDE_USAGE:
							return new HibernateConverterProviderParameterResolver(processingEnv) {
								@Override
								protected PropagationType getConverterCacheParameterPropagation() {
									return PropagationType.INSTANTIATED;
								}
							};
						case PROVIDER_CONSTRUCTOR_USAGE:
							return new HibernateServiceParameterResolver(processingEnv) {
								@Override
								protected PropagationType getConverterCacheParameterPropagation() {
									return PropagationType.PROPAGATED_MUTABLE;
								}
							};
						case PROVIDER_INSIDE_USAGE:
							return new HibernateServiceParameterResolver(processingEnv);
						case PROVIDER_CONSTRUCTOR:
							return new HibernateServiceParameterResolver(processingEnv) {

								@Override
								protected PropagationType getTransactionPropagationPropagation() {
									return PropagationType.PROPAGATED_IMUTABLE;
								}

								@Override
								protected PropagationType getConverterProviderContextParameterPropagation() {
									return PropagationType.PROPAGATED_IMUTABLE;
								}

								@Override
								protected PropagationType getConverterCacheParameterPropagation() {
									return PropagationType.PROPAGATED_MUTABLE;
								}

								@Override
								protected MutableReferenceType getConverterProviderContextReference() {
									return processingEnv.getTypeUtils().getReference(null, THIS);
								}
							};
						default:
							return new HibernateServiceParameterResolver(processingEnv) {
								@Override
								protected PropagationType getConverterCacheParameterPropagation() {
									return PropagationType.INSTANTIATED;
								}
							};
					}
				}
			};
		}
		
		return converterConstructorParametersResolverProvider;
	}
	
	@Override
	protected void processElement(ProcessorContext context) {
		converterConstructorParametersResolverProvider = null;
		super.processElement(context);
	}
	
	@Override
	protected ServiceConverterElementPrinter[] getElementPrinters(final ServiceTypeElement serviceTypeElement) {
		return new ServiceConverterElementPrinter[] {
				new LocalServiceFieldPrinter(),
				new ConverterParameterFieldPrinter(processingEnv, getParametersFilter(), getParametersResolverProvider(serviceTypeElement)),
				new ServiceConstructorDefinitionPrinter(processingEnv, getParametersFilter(), getParametersResolverProvider(serviceTypeElement)),
				new ServiceConstructorBodyPrinter(processingEnv, getParametersFilter(), getParametersResolverProvider(serviceTypeElement)),
				new HibernateServiceMethodConverterPrinter(processingEnv, getParametersResolverProvider(serviceTypeElement), getConverterProviderPrinter(serviceTypeElement))
		};
	}
	
	@Override
	protected ConverterProviderPrinter getConverterProviderPrinter(ServiceTypeElement serviceTypeElement) {
		return new HibernateConverterProviderPrinter(processingEnv, getParametersResolverProvider(serviceTypeElement), UsageType.PROVIDER_OUTSIDE_USAGE);
	}
}