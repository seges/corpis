package sk.seges.corpis.pap.service.hibernate.printer;

import sk.seges.sesam.core.pap.builder.api.ClassPathTypes;
import sk.seges.sesam.core.pap.model.api.ClassSerializer;
import sk.seges.sesam.core.pap.model.mutable.api.MutableExecutableType;
import sk.seges.sesam.core.pap.model.mutable.api.element.MutableVariableElement;
import sk.seges.sesam.core.pap.writer.HierarchyPrintWriter;
import sk.seges.sesam.pap.converter.model.AbstractProviderContextType;
import sk.seges.sesam.pap.converter.model.AbstractProviderType;
import sk.seges.sesam.pap.model.model.TransferObjectProcessingEnvironment;
import sk.seges.sesam.pap.model.resolver.ProviderConstructorParametersResolverProvider;
import sk.seges.sesam.pap.service.printer.converterprovider.ConverterProviderContextPrinterDelegate;

import javax.lang.model.element.Modifier;
import java.util.List;

public class ServiceConverterProviderContextMethodPrinter extends ConverterProviderContextPrinterDelegate {

    private AbstractProviderContextType contextType;
    private MutableExecutableType initializeContextMethod;

    public ServiceConverterProviderContextMethodPrinter(TransferObjectProcessingEnvironment processingEnv, ProviderConstructorParametersResolverProvider parametersResolverProvider, ClassPathTypes classPathTypes) {
        super(processingEnv, parametersResolverProvider, classPathTypes);
    }

    @Override
	public void initialize(final AbstractProviderContextType contextType) {

        this.contextType = contextType;

		initializeContextMethod = processingEnv.getTypeUtils().getExecutable(processingEnv.getTypeUtils().toMutableType(Void.class), "initialize");
		initializeContextMethod.addModifier(Modifier.PUBLIC);
		contextType.addMethod(initializeContextMethod);

		super.initialize(contextType);
	}

    @Override
    public void print(AbstractProviderType providerType) {

        List<MutableVariableElement> requiredParameters = contextType.getConstructor().getParameters();

        List<MutableVariableElement> localFields = providerType.getFields();

        for (MutableVariableElement generatedParameter: requiredParameters) {
            if (getLocalField(localFields, generatedParameter) == null) {
                initializeContextMethod.addParameter(processingEnv.getElementUtils().getParameterElement(generatedParameter.asType(), generatedParameter.getSimpleName()));
            }
        }

        HierarchyPrintWriter printWriter = initializeContextMethod.getPrintWriter();
        printWriter.print("return new " + providerType.getSimpleName() + "(");
        int i = 0;
        for (MutableVariableElement parameter: requiredParameters) {
            if (i > 0) {
                printWriter.print(", ");
            }

            MutableVariableElement localField = getLocalField(localFields, parameter);

            if (localField == null) {
                MutableVariableElement methodParameter = getLocalField(initializeContextMethod.getParameters(), parameter);

                if (methodParameter == null) {
                    providerType.getField(methodParameter);
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