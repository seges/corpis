package sk.seges.corpis.pap.model.domain.data;

import org.junit.Test;
import sk.seges.corpis.appscaffold.model.pap.DomainDataInterfaceProcessor;
import sk.seges.corpis.appscaffold.model.pap.model.DomainDataInterfaceType;
import sk.seges.corpis.pap.MockDomainModel;
import sk.seges.corpis.pap.MockiestDomainModel;
import sk.seges.sesam.core.pap.test.AnnotationTest;

import javax.annotation.processing.Processor;
import java.io.File;

public class DomainDataInterfaceProcessorTest extends AnnotationTest {

	@Test
	public void testDomainDataInterfaceForJava() {
		 assertCompilationSuccessful(compileFiles(Compiler.JAVAC, MockDomainModel.class, MockiestDomainModel.class));
		 assertOutput(getResourceFile(MockDomainModel.class), getOutputClass(MockDomainModel.class));
		 assertOutput(getResourceFile(MockiestDomainModel.class), getOutputClass(MockiestDomainModel.class));
	}

	@Test
	public void testDomainDataInterfaceForEclipse() {
        assertCompilationSuccessful(compileFiles(Compiler.JAVAC, MockDomainModel.class, MockiestDomainModel.class));
		 assertOutput(getResourceFile(MockDomainModel.class), getOutputClass(MockDomainModel.class));
		 assertOutput(getResourceFile(MockiestDomainModel.class), getOutputClass(MockiestDomainModel.class));
	}

	private File getOutputClass(Class<?> clazz) {
		return toFile(new DomainDataInterfaceType(toMutable(clazz), processingEnv));
	}
	
	@Override
	protected Processor[] getProcessors() {
		return new Processor[] { new DomainDataInterfaceProcessor() };
	}

}
