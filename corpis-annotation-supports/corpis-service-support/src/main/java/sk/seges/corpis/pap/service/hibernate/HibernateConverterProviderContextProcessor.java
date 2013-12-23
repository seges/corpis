package sk.seges.corpis.pap.service.hibernate;

import sk.seges.corpis.pap.converter.model.ContextualConverterProviderType;
import sk.seges.corpis.pap.model.printer.converter.HibernateConverterProviderPrinter;
import sk.seges.corpis.pap.service.hibernate.printer.converterprovider.HibernateConverterProviderContextPrinterDelegate;
import sk.seges.sesam.core.pap.model.ParameterElement;
import sk.seges.sesam.core.pap.model.api.PropagationType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableReferenceType;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateConverterProviderParameterResolver;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateServiceParameterResolver;
import sk.seges.sesam.pap.model.printer.converter.ConverterProviderPrinter;
import sk.seges.sesam.pap.model.resolver.CacheableConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.ConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.ConverterConstructorParametersResolverProvider.UsageType;
import sk.seges.sesam.pap.model.resolver.DefaultConverterConstructorParametersResolver;
import sk.seges.sesam.pap.model.resolver.api.ConverterConstructorParametersResolver;
import sk.seges.sesam.pap.service.ServiceConverterContextProcessor;
import sk.seges.sesam.pap.service.model.ServiceTypeElement;
import sk.seges.sesam.pap.service.printer.api.ConverterContextElementPrinter;
import sk.seges.sesam.pap.service.printer.converterprovider.ConverterProviderContextPrinterDelegate;
import sk.seges.sesam.pap.service.printer.converterprovider.ServiceConverterProviderContextPrinter;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class HibernateConverterProviderContextProcessor extends ServiceConverterContextProcessor {

    @Override
    protected MutableDeclaredType[] getOutputClasses(RoundContext context) {
        return new MutableDeclaredType[] {
                new ContextualConverterProviderType(context.getMutableType(), processingEnv)
        };
    }

    private ConverterConstructorParametersResolverProvider converterConstructorParametersResolverProvider;

	@Override
	protected ConverterConstructorParametersResolverProvider getParametersResolverProvider(MutableDeclaredType contextType) {
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
						return processingEnv.getTypeUtils().getReference(null, ConverterProviderContextPrinterDelegate.RESULT_INSTANCE_NAME);
					}
				};
			}
		};
	}
	@Override
	protected ConverterContextElementPrinter[] getElementPrinters(MutableDeclaredType contextType) {
		return new ConverterContextElementPrinter[] {
				new ServiceConverterProviderContextPrinter(processingEnv, getParametersResolverProvider(contextType), getClassPathTypes()) {
					protected ConverterProviderContextPrinterDelegate getConverterProviderContextPrinterDelegate(ConverterConstructorParametersResolverProvider parametersResolverProvider) {
						return new HibernateConverterProviderContextPrinterDelegate(processingEnv, parametersResolverProvider);
					}
				}
		};
	}

	protected ConverterConstructorParametersResolverProvider getParametersResolverProvider(final ServiceTypeElement serviceTypeElement) {

        if (converterConstructorParametersResolverProvider == null) {
            converterConstructorParametersResolverProvider = new CacheableConverterConstructorParametersResolverProvider() {

                @Override
                public ConverterConstructorParametersResolver constructParameterResolver(UsageType usageType) {
                    switch (usageType) {
                        case CONVERTER_PROVIDER_CONSTRUCTOR_USAGE:
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
                        case CONVERTER_PROVIDER_CONTEXT_CONSTRUCTOR:
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
									return processingEnv.getTypeUtils().getReference(null, ConverterProviderContextPrinterDelegate.RESULT_INSTANCE_NAME);
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
        return new HibernateConverterProviderPrinter(processingEnv, getParametersResolverProvider(serviceTypeElement), UsageType.CONVERTER_PROVIDER_OUTSIDE_USAGE);
    }
}