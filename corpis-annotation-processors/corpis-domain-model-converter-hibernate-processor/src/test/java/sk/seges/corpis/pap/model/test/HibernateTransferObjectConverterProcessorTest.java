package sk.seges.corpis.pap.model.test;

import java.io.File;
import java.util.ArrayList;

import javax.annotation.processing.Processor;
import javax.lang.model.element.TypeElement;

import org.junit.Test;

import sk.seges.corpis.pap.model.configuration.MockContentConfiguration;
import sk.seges.corpis.pap.model.configuration.MockContentPkConfiguration;
import sk.seges.corpis.pap.model.configuration.MockEntityDTOConfiguration;
import sk.seges.corpis.pap.model.hibernate.HibernateTransferObjectConverterProcessor;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.test.AnnotationTest;
import sk.seges.sesam.pap.model.model.ConfigurationContext;
import sk.seges.sesam.pap.model.model.ConfigurationEnvironment;
import sk.seges.sesam.pap.model.model.ConfigurationTypeElement;
import sk.seges.sesam.pap.model.model.TransferObjectProcessingEnvironment;
import sk.seges.sesam.pap.model.model.api.dto.DtoDeclaredType;
import sk.seges.sesam.pap.model.provider.ConfigurationCache;

public class HibernateTransferObjectConverterProcessorTest extends AnnotationTest {

//	@Test
//	public void testPkDto() {
//		assertCompilationSuccessful(compileFiles(Compiler.JAVAC, MockContentConfiguration.class));
//		assertCompilationSuccessful(compileFiles(Compiler.JAVAC, MockContentPkConfiguration.class));
//	}
	
	@Test
	public void testDto() {
		assertCompilationSuccessful(compileFiles(Compiler.JAVAC, MockEntityDTOConfiguration.class));
		assertOutput(getResourceFile(MockEntityDTOConfiguration.class), getOutputFile(MockEntityDTOConfiguration.class));
	}

    protected File getOutputFile(Class<?> clazz) {
        MutableDeclaredType inputClass = toMutable(clazz);

        TransferObjectProcessingEnvironment tope = new TransferObjectProcessingEnvironment(processingEnv, roundEnv, new ConfigurationCache(), getProcessors()[0].getClass(), new ArrayList<MutableDeclaredType>());

        ConfigurationEnvironment env = new ConfigurationEnvironment(tope, roundEnv, new ConfigurationCache());

        ConfigurationContext context = new ConfigurationContext(env);
        ConfigurationTypeElement configurationTypeElement = new ConfigurationTypeElement(processingEnv.getElementUtils().getTypeElement(inputClass.getCanonicalName()), tope.getEnvironmentContext(), context);
        context.addConfiguration(configurationTypeElement);
        return toFile(configurationTypeElement.getConverter());
    }

	@Override
	protected Processor[] getProcessors() {
		return new Processor[] {
			new HibernateTransferObjectConverterProcessor()
		};
	}
}