package sk.seges.corpis.pap.service.hibernate;

import sk.seges.corpis.pap.converter.model.ContextualConverterProviderType;
import sk.seges.corpis.pap.model.printer.converter.HibernateConverterProviderPrinter;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateServiceParameterResolver;
import sk.seges.sesam.pap.model.printer.converter.ConverterProviderPrinter;
import sk.seges.sesam.pap.model.resolver.CacheableConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.ConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.ConverterConstructorParametersResolverProvider.UsageType;
import sk.seges.sesam.pap.model.resolver.api.ConverterConstructorParametersResolver;
import sk.seges.sesam.pap.service.ServiceConverterContextProcessor;
import sk.seges.sesam.pap.service.model.ServiceTypeElement;

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

    protected ConverterConstructorParametersResolverProvider getParametersResolverProvider(final ServiceTypeElement serviceTypeElement) {

        if (converterConstructorParametersResolverProvider == null) {
            converterConstructorParametersResolverProvider = new CacheableConverterConstructorParametersResolverProvider() {

                @Override
                public ConverterConstructorParametersResolver constructParameterResolver(UsageType usageType) {
                    switch (usageType) {
                        case CONVERTER_PROVIDER_CONSTRUCTOR_USAGE:
                            return new HibernateServiceParameterResolver(processingEnv) {
                                @Override
                                protected boolean isConverterCacheParameterPropagated() {
                                    return true;
                                }
                            };
                        case CONVERTER_PROVIDER_CONTEXT_CONSTRUCTOR:
                            return new HibernateServiceParameterResolver(processingEnv) {

                                @Override
                                protected boolean isConverterCacheParameterPropagated() {
                                    return true;
                                }

                                @Override
                                protected boolean isTransactionPropagationPropagated() {
                                    return true;
                                }

                                @Override
                                protected boolean isConverterProviderContextParameterPropagated() {
                                    return true;
                                }
                            };
                        default:
                            return new HibernateServiceParameterResolver(processingEnv);
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