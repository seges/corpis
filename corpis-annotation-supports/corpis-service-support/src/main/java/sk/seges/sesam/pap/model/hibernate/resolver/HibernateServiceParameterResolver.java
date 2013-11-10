package sk.seges.sesam.pap.model.hibernate.resolver;

import sk.seges.corpis.server.model.converter.provider.AbstractContextualConverterProvider;
import sk.seges.sesam.core.pap.model.ParameterElement;
import sk.seges.sesam.core.pap.model.api.PropagationType;
import sk.seges.sesam.core.pap.model.mutable.utils.MutableProcessingEnvironment;
import sk.seges.sesam.pap.service.resolver.ServiceConverterConstructorParametersResolver;

import javax.persistence.EntityManager;

public class HibernateServiceParameterResolver extends ServiceConverterConstructorParametersResolver {

	private final HibernateParameterResolverDelegate hibernateParameterResolverDelegate;

	public HibernateServiceParameterResolver(MutableProcessingEnvironment processingEnv) {
		super(processingEnv);
		this.hibernateParameterResolverDelegate = new HibernateParameterResolverDelegate(processingEnv) {

			@Override
			protected PropagationType getTransactionPropagationModelParameterPropagation() {
				return getTransactionPropagationPropagation();
			};
			
			@Override
			protected ParameterElement getEntityManagerModel() {
				return new ParameterElement(processingEnv.getTypeUtils().toMutableType(EntityManager.class),
						ENTITY_MANAGER_NAME, getEntityManagerReference(), getEntityManagerPropagation(), processingEnv);
			}
		};
	}

	@Override
	protected ParameterElement getConverterProviderContextParameter() {
		return new ParameterElement(processingEnv.getTypeUtils().toMutableType(AbstractContextualConverterProvider.class), CONVERTER_PROVIDER_CONTEXT_NAME,
				getConverterProviderContextReference(), getConverterProviderContextParameterPropagation(), processingEnv);
	}

	protected PropagationType getTransactionPropagationPropagation() {
		return PropagationType.INSTANTIATED;
	};
	
	public PropagationType getEntityManagerPropagation() {
		return PropagationType.PROPAGATED_IMUTABLE;
	}
	
	@Override
	public ParameterElement[] getConstructorAditionalParameters() {
		return hibernateParameterResolverDelegate.getConstructorAditionalParameters(super.getConstructorAditionalParameters());
	}	
}