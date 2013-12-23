package sk.seges.sesam.pap.model.hibernate.resolver;

import sk.seges.corpis.server.model.converter.provider.AbstractContextualConverterProvider;
import sk.seges.sesam.core.pap.model.ParameterElement;
import sk.seges.sesam.core.pap.model.api.PropagationType;
import sk.seges.sesam.core.pap.model.mutable.utils.MutableProcessingEnvironment;
import sk.seges.sesam.pap.service.resolver.ServiceConverterConstructorParametersResolver;

public class HibernateConverterProviderParameterResolver extends ServiceConverterConstructorParametersResolver {

	private final HibernateParameterResolverDelegate hibernateParameterResolverDelegate;

	public HibernateConverterProviderParameterResolver(MutableProcessingEnvironment processingEnv) {
		super(processingEnv);
		this.hibernateParameterResolverDelegate = new HibernateParameterResolverDelegate(processingEnv) {

			@Override
			protected PropagationType getTransactionPropagationModelParameterPropagation() {
				return PropagationType.INSTANTIATED;
			}
		};
	}

	@Override
	protected ParameterElement getConverterProviderContextParameter() {
		return new ParameterElement(processingEnv.getTypeUtils().toMutableType(AbstractContextualConverterProvider.class), CONVERTER_PROVIDER_CONTEXT_NAME,
				getConverterProviderContextReference(), getConverterProviderContextParameterPropagation(), processingEnv);
	}

	@Override
	public ParameterElement[] getConstructorAditionalParameters() {
		return hibernateParameterResolverDelegate.getConstructorAditionalParameters(super.getConstructorAditionalParameters());
	}	
	
	@Override
	protected PropagationType getConverterCacheParameterPropagation() {
		return PropagationType.INSTANTIATED;
	}
}