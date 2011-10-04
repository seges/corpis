package sk.seges.sesam.pap.model.hibernate.printer.method;

import javax.annotation.processing.RoundEnvironment;

import sk.seges.sesam.core.pap.writer.FormattedPrintWriter;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateParameterResolver;
import sk.seges.sesam.pap.model.model.DomainTypeElement;
import sk.seges.sesam.pap.model.model.TransferObjectProcessingEnvironment;
import sk.seges.sesam.pap.model.printer.converter.ConverterProviderPrinter;
import sk.seges.sesam.pap.model.printer.method.CopyFromDtoPrinter;
import sk.seges.sesam.pap.model.resolver.api.EntityResolver;
import sk.seges.sesam.pap.model.resolver.api.ParametersResolver;

public class HibernateCopyFromDtoPrinter extends CopyFromDtoPrinter {
	
	public HibernateCopyFromDtoPrinter(ConverterProviderPrinter converterProviderPrinter, EntityResolver entityResolver,
			ParametersResolver parametersResolver, RoundEnvironment roundEnv, TransferObjectProcessingEnvironment processingEnv,
			FormattedPrintWriter pw) {
		super(converterProviderPrinter, entityResolver, parametersResolver, roundEnv, processingEnv, pw);
	}

	@Override
	protected void printDomainInstancer(FormattedPrintWriter pw, DomainTypeElement domainType) {
		pw.println("if (id != null) {");
		pw.println(domainType, " " + RESULT_NAME + " = (", domainType, ")" + HibernateParameterResolver.ENTITY_MANAGER_NAME + ".find(", domainType.getSimpleName(), ".class, id);");
		pw.println("if (" + RESULT_NAME + " != null) {");
		pw.println("return " + RESULT_NAME + ";");
		pw.println("}");
		pw.println("}");
		pw.println();
		super.printDomainInstancer(pw, domainType);
	}
}