package sk.seges.corpis.pap.service.hibernate;

import sk.seges.corpis.pap.converter.model.ContextualConverterProviderType;
import sk.seges.corpis.pap.model.printer.converter.HibernateConverterProviderPrinter;
import sk.seges.corpis.pap.service.hibernate.printer.converterprovider.HibernateConverterProviderContextPrinter;
import sk.seges.sesam.core.pap.model.api.PropagationType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableReferenceType;
import sk.seges.sesam.core.pap.processor.MutableAnnotationProcessor;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateConverterProviderParameterResolver;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateServiceParameterResolver;
import sk.seges.sesam.pap.model.printer.converter.ConverterProviderPrinter;
import sk.seges.sesam.pap.model.resolver.CacheableConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.ProviderConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.ProviderConstructorParametersResolverProvider.UsageType;
import sk.seges.sesam.pap.model.resolver.api.ConverterConstructorParametersResolver;
import sk.seges.sesam.pap.service.ServiceConverterContextProcessor;
import sk.seges.sesam.pap.service.model.ServiceTypeElement;
import sk.seges.sesam.pap.service.printer.api.ProviderContextElementPrinter;
import sk.seges.sesam.pap.service.printer.converterprovider.ConverterProviderContextPrinter;
import sk.seges.sesam.pap.service.printer.converterprovider.ConverterProviderContextPrinterDelegate;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class HibernateConverterProviderContextProcessor extends ServiceConverterContextProcessor {

    @Override
    protected MutableDeclaredType[] getOutputClasses(MutableAnnotationProcessor.RoundContext context) {
        return new MutableDeclaredType[] {
                new ContextualConverterProviderType(context.getMutableType(), processingEnv)
        };
    }

    private ProviderConstructorParametersResolverProvider converterConstructorParametersResolverProvider;

	@Override
	protected ProviderConstructorParametersResolverProvider getParametersResolverProvider(MutableDeclaredType contextType) {
		return new CacheableConverterConstructorParametersResolverProvider() {
			@Override
			public ConverterConstructorParametersResolver constructParameterResolver(UsageType usageType) {
				return new HibernateConverterProviderParameterResolver(processingEnv) {
					@Override
					protected PropagationType getConverterProviderContextParameterPropagation() {
						return PropagationType.INSTANTIATED;
					}

					@Override
					protected MutableReferenceType getConverterProviderContextReference() {
						//TODO define also in sesam
						return processingEnv.getTypeUtils().getReference(null, ConverterProviderContextPrinter.RESULT_INSTANCE_NAME);
					}
				};
			}
		};
	}
	@Override
	protected ProviderContextElementPrinter[] getElementPrinters(MutableDeclaredType contextType) {
		return new ProviderContextElementPrinter[] {
				new ConverterProviderContextPrinterDelegate(processingEnv, getParametersResolverProvider(contextType), getClassPathTypes()) {
					protected ConverterProviderContextPrinter getProviderContextPrinter(ProviderConstructorParametersResolverProvider parametersResolverProvider) {
						return new HibernateConverterProviderContextPrinter(processingEnv, parametersResolverProvider);
					}
				}
		};
	}

	protected ProviderConstructorParametersResolverProvider getParametersResolverProvider(final ServiceTypeElement serviceTypeElement) {

        if (converterConstructorParametersResolverProvider == null) {
            converterConstructorParametersResolverProvider = new CacheableConverterConstructorParametersResolverProvider() {

                @Override
                public ConverterConstructorParametersResolver constructParameterResolver(UsageType usageType) {
                    switch (usageType) {
                        case PROVIDER_CONSTRUCTOR_USAGE:
                            return new HibernateServiceParameterResolver(processingEnv) {
                                @Override
                                protected PropagationType getConverterCacheParameterPropagation() {
                                    return PropagationType.PROPAGATED_MUTABLE;
                                }

								@Override
								protected MutableReferenceType getConverterProviderContextReference() {
									//TODO RESULT instead of THIS??
									return processingEnv.getTypeUtils().getReference(null, THIS);
								}
							};
                        case PROVIDER_CONTEXT_CONSTRUCTOR:
                            return new HibernateServiceParameterResolver(processingEnv) {

                                @Override
                                protected PropagationType getConverterCacheParameterPropagation() {
                                    return PropagationType.PROPAGATED_MUTABLE;
                                }

                                @Override
                                protected PropagationType getTransactionPropagationPropagation() {
                                    return PropagationType.PROPAGATED_MUTABLE;
                                }

                                @Override
                                protected PropagationType getConverterProviderContextParameterPropagation() {
                                    return PropagationType.PROPAGATED_IMUTABLE;
                                }


								@Override
								protected MutableReferenceType getConverterProviderContextReference() {
									return processingEnv.getTypeUtils().getReference(null, THIS);
								}
                            };
                        default:
                            return new HibernateServiceParameterResolver(processingEnv) {

								@Override
								protected MutableReferenceType getConverterProviderContextReference() {
									//TODO define also in sesam
									return processingEnv.getTypeUtils().getReference(null, ConverterProviderContextPrinter.RESULT_INSTANCE_NAME);
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


    protected ConverterProviderPrinter getConverterProviderPrinter(ServiceTypeElement serviceTypeElement) {
        return new HibernateConverterProviderPrinter(processingEnv, getParametersResolverProvider(serviceTypeElement), UsageType.PROVIDER_OUTSIDE_USAGE);
    }
}