package sk.seges.corpis.pap.model.hibernate;

import org.junit.Test;
import sk.seges.corpis.pap.converter.hibernate.HibernateConverterProviderProcessor;
import sk.seges.corpis.pap.converter.hibernate.model.HibernateConverterProviderType;
import sk.seges.corpis.pap.model.configuration.configuration.MockPageConfiguration;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.test.AnnotationTest;
import sk.seges.sesam.dao.Page;
import sk.seges.sesam.pap.converter.model.ConverterProviderType;
import sk.seges.sesam.pap.model.model.ConfigurationTypeElement;
import sk.seges.sesam.pap.model.model.TransferObjectProcessingEnvironment;
import sk.seges.sesam.pap.model.provider.ConfigurationCache;

import javax.annotation.processing.Processor;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.util.ArrayList;

public class HibernateConverterProviderProcessorTest extends AnnotationTest {

	@Test
	public void testConveterProvider() {
		assertCompilationSuccessful(compileFiles(MockPageConfiguration.class));
		assertOutput(getResourceFile(MockPageConfiguration.class), getOutputFile(MockPageConfiguration.class));
	}

	@Override
	protected File getResourceFile(Class<?> clazz) {
		return getResourceFile("provider", clazz);
	}

	public File getOutputFile(Class<?> type) {
		TypeElement configurationElement = processingEnv.getElementUtils().getTypeElement(type.getCanonicalName());
		return toFile(new HibernateConverterProviderType(processingEnv.getTypeUtils().toMutableType(configurationElement), processingEnv));
	}

	@Override
	protected Processor[] getProcessors() {
		return new Processor[] {
				new HibernateConverterProviderProcessor()
		};
	}

}
