package sk.seges.corpis.pap.service.hibernate.printer;

import sk.seges.sesam.core.pap.builder.api.ClassPathTypes;
import sk.seges.sesam.core.pap.model.api.ClassSerializer;
import sk.seges.sesam.core.pap.model.mutable.api.MutableExecutableType;
import sk.seges.sesam.core.pap.model.mutable.api.element.MutableVariableElement;
import sk.seges.sesam.core.pap.writer.HierarchyPrintWriter;
import sk.seges.sesam.pap.converter.model.ConverterProviderType;
import sk.seges.sesam.pap.model.model.TransferObjectProcessingEnvironment;
import sk.seges.sesam.pap.model.resolver.ConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.service.model.ConverterProviderContextType;
import sk.seges.sesam.pap.service.model.ServiceConverterTypeElement;
import sk.seges.sesam.pap.service.printer.converterprovider.ServiceConverterProviderContextPrinter;

import javax.lang.model.element.Modifier;
import java.util.List;

public class ServiceConverterProviderContextMethodPrinter extends ServiceConverterProviderContextPrinter {

    private ConverterProviderContextType contextType;
    private MutableExecutableType initializeContextMethod;

    public ServiceConverterProviderContextMethodPrinter(TransferObjectProcessingEnvironment processingEnv, ConverterConstructorParametersResolverProvider parametersResolverProvider, ClassPathTypes classPathTypes) {
        super(processingEnv, parametersResolverProvider, classPathTypes);
    }

    @Override
	public void initialize(final ConverterProviderContextType contextType) {

//		final MutableExecutableType converterProviderContextMethod = processingEnv.getTypeUtils().getExecutable(context.getConvertProviderContextType(), HibernateServiceConverterProviderParameterResolver.GET_CONVERTER_PROVIDER_CONTEXT_METHOD);
//		converterProviderContextMethod.addModifier(Modifier.PROTECTED);
//		serviceTypeElement.getServiceConverter().addMethod(converterProviderContextMethod);

//		HierarchyPrintWriter methodPrinter = converterProviderContextMethod.getPrintWriter();

        this.contextType = contextType;

		initializeContextMethod = processingEnv.getTypeUtils().getExecutable(processingEnv.getTypeUtils().toMutableType(Void.class), "initialize");
		initializeContextMethod.addModifier(Modifier.PUBLIC);
		contextType.addMethod(initializeContextMethod);

		super.initialize(contextType);
	}

    @Override
    public void print(ConverterProviderType serviceConverterProvider) {

        List<MutableVariableElement> requiredParameters = contextType.getConstructor().getParameters();

        List<MutableVariableElement> localFields = serviceConverterProvider.getFields();

        for (MutableVariableElement generatedParameter: requiredParameters) {
            if (getLocalField(localFields, generatedParameter) == null) {
                initializeContextMethod.addParameter(processingEnv.getElementUtils().getParameterElement(generatedParameter.asType(), generatedParameter.getSimpleName()));
            }
        }

        HierarchyPrintWriter printWriter = initializeContextMethod.getPrintWriter();
        printWriter.print("return new " + serviceConverterProvider.getSimpleName() + "(");
        int i = 0;
        for (MutableVariableElement parameter: requiredParameters) {
            if (i > 0) {
                printWriter.print(", ");
            }

            MutableVariableElement localField = getLocalField(localFields, parameter);

            if (localField == null) {
                MutableVariableElement methodParameter = getLocalField(initializeContextMethod.getParameters(), parameter);

                if (methodParameter == null) {
                    serviceConverterProvider.getField(methodParameter);
                }

                printWriter.print(parameter.getSimpleName());
            } else {
                printWriter.print(localField.getSimpleName());
            }
            i++;
        }
        printWriter.println(");");
    }

    private boolean isSameParameter(MutableVariableElement field, MutableVariableElement parameter) {
		return (field.getSimpleName().equals(parameter.getSimpleName()) && 
				field.asType().toString(ClassSerializer.CANONICAL, false).equals(parameter.asType().toString(ClassSerializer.CANONICAL, false)));
	}
	
	private MutableVariableElement getLocalField(List<MutableVariableElement> localFields, MutableVariableElement parameter) {
		for (MutableVariableElement localField: localFields) {
			if (isSameParameter(localField, parameter)) {
				return localField;
			}
		}
		
		return null;
	}
}