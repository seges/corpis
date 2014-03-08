package sk.seges.corpis.pap.service.hibernate.printer.converterprovider;

import sk.seges.sesam.core.pap.model.ConstructorParameter;
import sk.seges.sesam.core.pap.model.ParameterElement;
import sk.seges.sesam.core.pap.model.api.PropagationType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableReferenceType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableType;
import sk.seges.sesam.core.pap.model.mutable.api.element.MutableVariableElement;
import sk.seges.sesam.core.pap.model.mutable.utils.MutableProcessingEnvironment;
import sk.seges.sesam.core.pap.utils.ProcessorUtils;
import sk.seges.sesam.core.pap.writer.HierarchyPrintWriter;
import sk.seges.sesam.pap.model.resolver.ConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.utils.ConstructorHelper;
import sk.seges.sesam.pap.service.model.ConverterProviderContextType;
import sk.seges.sesam.pap.service.printer.converterprovider.ConverterProviderContextPrinterDelegate;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.List;

public class HibernateConverterProviderContextPrinterDelegate extends ConverterProviderContextPrinterDelegate {

	public HibernateConverterProviderContextPrinterDelegate(MutableProcessingEnvironment processingEnv, ConverterConstructorParametersResolverProvider parametersResolverProvider) {
		super(processingEnv, parametersResolverProvider);
	}

	public void initialize(MutableProcessingEnvironment processingEnv, ConverterProviderContextType converterProviderType, ConverterConstructorParametersResolverProvider.UsageType usageType) {
		this.converterProviderContextType = converterProviderType;
		this.printerDelegate = new HibernateConverterProviderPrinterDelegate(parametersResolverProvider);
		this.printerDelegate.initialize(processingEnv, converterProviderType, usageType);
	}

	@Override
	public void print(Element converterPrinterDelegate) {
		HierarchyPrintWriter pw = ((HibernateConverterProviderPrinterDelegate)printerDelegate).getPrintWriter(converterProviderContextType);
		pw.print(RESULT_INSTANCE_NAME + ".registerConverterProvider(new ", converterPrinterDelegate, "(");
		//ConverterConstructorParametersResolver parameterResolver = parametersResolverProvider.getParameterResolver(UsageType.CONVERTER_PROVIDER_CONSTRUCTOR);
		List<MutableVariableElement> converterParameters = converterProviderContextType.getConstructor().getParameters();
		//converterProviderContextType.getConverterParameters(parameterResolver);

		List<ExecutableElement> constructors = ElementFilter.constructorsIn(converterPrinterDelegate.getEnclosedElements());

		List<ConstructorParameter> constructorParameters = new ArrayList<ConstructorParameter>();

		if (constructors.size() > 0) {
			constructorParameters = ConstructorHelper.getConstructorParameters(processingEnv.getTypeUtils(), constructors.get(0));
		}

		int j = 0;

		ParameterElement[] constructorAditionalParameters = parametersResolverProvider.getParameterResolver(ConverterConstructorParametersResolverProvider.UsageType.CONVERTER_PROVIDER_CONTEXT_CONSTRUCTOR).getConstructorAditionalParameters();

		for (final ConstructorParameter constructorParameter: constructorParameters) {
			if (j > 0) {
				pw.print(", ");
			}
			final MutableVariableElement converterParameter = getParameterElementByType(constructorParameter, converterParameters);

			ParameterElement parameterElement = null;

			for (ParameterElement parameter: constructorAditionalParameters) {
				if (processingEnv.getTypeUtils().isAssignable(parameter.getType(), constructorParameter.getType())) {
					parameterElement = parameter;
					if (parameter.getPropagationType().equals(PropagationType.PROPAGATED_IMUTABLE)) {
						if (!ProcessorUtils.hasField(processingEnv, converterProviderContextType, constructorParameter.getType(), constructorParameter.getName())) {
							ProcessorUtils.addField(processingEnv, converterProviderContextType, constructorParameter.getType(), constructorParameter.getName());
						}
					}
					break;
				}
			}

			if (parameterElement == null) {
				if (converterParameter == null) {

					parameterElement = new ParameterElement(constructorParameter.getType(), constructorParameter.getName(), new ParameterElement.ParameterUsageProvider() {

						@Override
						public MutableType getUsage(ParameterElement.ParameterUsageContext context) {
							return constructorParameter.getType();
						}
					}, PropagationType.PROPAGATED_IMUTABLE);
					if (!ProcessorUtils.hasField(processingEnv, converterProviderContextType, constructorParameter.getType(), constructorParameter.getName())) {
						ProcessorUtils.addField(processingEnv, converterProviderContextType, constructorParameter.getType(), constructorParameter.getName());
					}
				} else {
					parameterElement = new ParameterElement(converterParameter.asType(), converterParameter.getSimpleName(), new ParameterElement.ParameterUsageProvider() {

						@Override
						public MutableType getUsage(ParameterElement.ParameterUsageContext context) {
							return converterParameter.asType();
						}
					}, PropagationType.PROPAGATED_IMUTABLE);
				}
			}

			MutableType usage = parameterElement.getUsage(new ParameterElement.ParameterUsageContext() {

				@Override
				public ExecutableElement getMethod() {
					return null;
				}
			});

			if (usage instanceof MutableReferenceType) {
				pw.print(usage);
			} else {
				pw.print(parameterElement.getName());
			}

			j++;
		}
		pw.println("));");
	}

}
