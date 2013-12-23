package sk.seges.corpis.pap.model.hibernate;

import org.junit.Test;
import sk.seges.corpis.pap.model.configuration.configuration.MockPageConfiguration;
import sk.seges.corpis.pap.model.hibernate.HibernateTransferObjectConverterProcessor;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.test.AnnotationTest;
import sk.seges.sesam.dao.Page;
import sk.seges.sesam.pap.model.model.*;
import sk.seges.sesam.pap.model.provider.ConfigurationCache;
import sk.seges.sesam.pap.service.model.ServiceTypeElement;

import javax.annotation.processing.Processor;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class HibernateTransferObjectConverterProcessorTest extends AnnotationTest {

	@Test
	public void testConveter() {
		assertCompilationSuccessful(compileFiles(MockPageConfiguration.class));
		assertOutput(getResourceFile(MockPageConfiguration.class), getOutputFile(MockPageConfiguration.class));
	}

	public File getOutputFile(Class<?> type) {
		MutableDeclaredType mutablePage = processingEnv.getTypeUtils().toMutableType(Page.class);
		TypeElement configurationElement = processingEnv.getElementUtils().getTypeElement(type.getCanonicalName());
		TransferObjectProcessingEnvironment transferObjectProcessingEnvironment = new TransferObjectProcessingEnvironment(processingEnv, roundEnv, new ConfigurationCache(), getProcessors()[0].getClass(), new ArrayList<MutableDeclaredType>());
		ConfigurationTypeElement configurationTypeElement = new ConfigurationTypeElement(mutablePage, mutablePage,
				configurationElement, transferObjectProcessingEnvironment.getEnvironmentContext(), null);

		return toFile(configurationTypeElement.getConverter());
	}

	@Override
	protected Processor[] getProcessors() {
		return new Processor[] {
			new HibernateTransferObjectConverterProcessor()
		};
	}
}
