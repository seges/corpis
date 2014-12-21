package sk.seges.corpis.pap.model.printer.converter;

import sk.seges.sesam.core.pap.model.mutable.utils.MutableProcessingEnvironment;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateConverterProviderParameterResolver;
import sk.seges.sesam.pap.model.resolver.ProviderConstructorParametersResolverProvider;
import sk.seges.sesam.pap.service.model.ServiceTypeElement;

@Deprecated
public class HibernateServiceConverterProviderParameterResolver extends HibernateConverterProviderParameterResolver {

	private final ServiceTypeElement serviceTypeElement;
	private final ProviderConstructorParametersResolverProvider parametersResolverProvider;
	
//	private static final String CONVERTER_PROVIDER_CONTEXT_REFERENCE = "converterProviderContext";
//	public static final String GET_CONVERTER_PROVIDER_CONTEXT_METHOD = "getConverterProviderContext";

	public HibernateServiceConverterProviderParameterResolver(ProviderConstructorParametersResolverProvider parametersResolverProvider, MutableProcessingEnvironment processingEnv, ServiceTypeElement serviceTypeElement) {
		super(processingEnv);
		this.parametersResolverProvider = parametersResolverProvider;
		this.serviceTypeElement = serviceTypeElement;
	}

//	@Override
	//TODO move to sesam
//	protected MutableReferenceType getConverterProviderContextReference() {
//		MutableTypes typeUtils = processingEnv.getTypeUtils();
//
//		MutableExecutableElement converterProviderContextMethod = processingEnv.getElementUtils().getExecutableElement(GET_CONVERTER_PROVIDER_CONTEXT_METHOD);
//		//ConverterProviderContextType convertProviderContextType = serviceTypeElement.getServiceConverter().getConvertProviderContextType();
//
//		converterProviderContextMethod.asType().setReturnType(processingEnv.getTypeUtils().toMutableType(ConverterProviderContext.class));
//        //convertProviderContextType);
//
//		ParameterElement[] generatedParameters = new HasConstructorParametersDelegate().getRequiredParameters(processingEnv,
//				parametersResolverProvider.getParameterResolver(UsageType.PROVIDER_CONTEXT_CONSTRUCTOR),
//				parametersResolverProvider.getParameterResolver(UsageType.DEFINITION));
//
//		List<MutableVariableElement> requiredParameters = new ArrayList<MutableVariableElement>();//convertProviderContextType.getConstructor().getParameters();
//
//		List<MutableReferenceType> referenceParams = new ArrayList<MutableReferenceType>();
//
//		for (MutableVariableElement requiredParameter: requiredParameters) {
//
//			boolean found = false;
//
//			for (int i = 0; i < generatedParameters.length; i++) {
//				if (generatedParameters[i].getType().isSameType(requiredParameter.asType())) {
//					found = true;
//					//TODO handle cast
//					MutableReferenceType usage = (MutableReferenceType) generatedParameters[i].getUsage(new ParameterUsageContext() {
//
//						@Override
//						public ExecutableElement getMethod() {
//							return null;
//						}
//					});
//
//					referenceParams.add(usage);
//				}
//			}
//
//			if (!found) {
//				if (!ProcessorUtils.hasFieldByType(serviceTypeElement.getServiceConverter(), requiredParameter.asType())) {
//					ProcessorUtils.addField(processingEnv, serviceTypeElement.getServiceConverter(), requiredParameter.asType(), requiredParameter.getSimpleName()) ;
//				}
////				referenceParams.add(processingEnv.getTypeUtils().getReference(null, requiredParameter.getSimpleName()));
//			}
//
//			found = false;
//		}
//
//		return typeUtils.getReference(typeUtils.getReferenceToMethod(converterProviderContextMethod, referenceParams.toArray(new MutableReferenceType[] {})), CONVERTER_PROVIDER_CONTEXT_REFERENCE);
//	}
}