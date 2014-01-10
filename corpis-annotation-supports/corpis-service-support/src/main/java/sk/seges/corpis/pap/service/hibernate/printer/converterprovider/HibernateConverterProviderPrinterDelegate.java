package sk.seges.corpis.pap.service.hibernate.printer.converterprovider;

import sk.seges.corpis.server.model.converter.provider.AbstractContextualConverterProvider;
import sk.seges.sesam.core.pap.model.mutable.api.element.MutableVariableElement;
import sk.seges.sesam.core.pap.model.mutable.utils.MutableProcessingEnvironment;
import sk.seges.sesam.core.pap.writer.HierarchyPrintWriter;
import sk.seges.sesam.core.pap.writer.LazyPrintWriter;
import sk.seges.sesam.pap.converter.model.HasConstructorParameters;
import sk.seges.sesam.pap.converter.printer.converterprovider.ConverterProviderPrinterDelegate;
import sk.seges.sesam.pap.model.resolver.ConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.service.printer.converterprovider.ConverterProviderContextPrinterDelegate;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;

public class HibernateConverterProviderPrinterDelegate extends ConverterProviderPrinterDelegate {

	public HibernateConverterProviderPrinterDelegate(ConverterConstructorParametersResolverProvider parametersResolverProvider) {
		super(parametersResolverProvider);
	}

	protected Class<? extends ConverterProviderContext> getResultClass() {
		return AbstractContextualConverterProvider.class;
	}

	@Override
	public void initialize(MutableProcessingEnvironment processingEnv, final HasConstructorParameters type, ConverterConstructorParametersResolverProvider.UsageType usageType) {
		super.initialize(processingEnv, type, usageType);

		HierarchyPrintWriter printWriter = getPrintWriter(type);
		printWriter.print(type, " " + ConverterProviderContextPrinterDelegate.RESULT_INSTANCE_NAME + " = new ", type, "(");
		printWriter.addLazyPrinter(new LazyPrintWriter(processingEnv) {
			@Override
			protected void print() {
				int i = 0;
				for (MutableVariableElement parameter: type.getConstructor().getParameters()) {
					if (i > 0) {
						print(", ");
					}
					print(parameter.getSimpleName());
					i++;
				}
			}
		});
		printWriter.println(");");

	}

	@Override
	public void finish(final HasConstructorParameters type) {
		getPrintWriter(type).println("return " + ConverterProviderContextPrinterDelegate.RESULT_INSTANCE_NAME + ";");
		super.finish(type);
	}
}