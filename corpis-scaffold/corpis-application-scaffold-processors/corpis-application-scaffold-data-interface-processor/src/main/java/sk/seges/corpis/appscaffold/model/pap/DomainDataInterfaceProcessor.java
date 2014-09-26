package sk.seges.corpis.appscaffold.model.pap;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.ElementFilter;

import sk.seges.corpis.appscaffold.model.pap.configurer.DomainDataInterfaceProcessorConfigurer;
import sk.seges.corpis.appscaffold.model.pap.model.DomainDataInterfaceType;
import sk.seges.corpis.appscaffold.shared.annotation.DomainData;
import sk.seges.sesam.core.pap.configuration.api.ProcessorConfigurer;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeMirror;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeMirror.MutableTypeKind;
import sk.seges.sesam.core.pap.model.mutable.api.element.MutableExecutableElement;
import sk.seges.sesam.core.pap.printer.MethodPrinter;
import sk.seges.sesam.core.pap.utils.MethodHelper;
import sk.seges.sesam.core.pap.writer.FormattedPrintWriter;
import sk.seges.sesam.pap.model.accessor.ReadOnlyAccessor;
import sk.seges.sesam.pap.model.annotation.ReadOnly;

/**
 * 
 * @author psloboda
 *
 */
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class DomainDataInterfaceProcessor extends AbstractDataProcessor {
	
	@Override
	protected void processElement(ProcessorContext context) {
		
		super.processElement(context);
		
		List<ExecutableElement> methods = ElementFilter.methodsIn(context.getTypeElement().getEnclosedElements());
		
		Collections.sort(methods, new Comparator<ExecutableElement>() {
			@Override
			public int compare(ExecutableElement method1, ExecutableElement method2) {
				return method1.getSimpleName().toString().compareTo(method2.getSimpleName().toString());	
			}
		});
		FormattedPrintWriter pw = context.getPrintWriter();
		
		for (ExecutableElement method: methods) {
			
			MutableTypeMirror returnType = castToDomainDataInterface(method.getReturnType());

			ReadOnlyAccessor readOnlyAccessor = new ReadOnlyAccessor(method, processingEnv);

			boolean readOnlyProperty = readOnlyAccessor.isReadonly();

			if (!readOnlyProperty) {
				pw.println("public static final String " + getConvertedPropertyName(method.getSimpleName().toString()) + " = \"" + method.getSimpleName() + "\";");
				pw.println();
			}

			MutableTypeMirror printableRetutnType = toPrintableType(context.getTypeElement(), returnType);

			if (isReadOnlyMethod(readOnlyAccessor, method)) {
				//it's getter or setter method, so just copy it

				new MethodPrinter(pw, processingEnv).printMethodDefinition(toPrintableElement(context.getTypeElement(), method));
				pw.println(";");
				pw.println();
			} else {
				if (printableRetutnType.getKind().equals(MutableTypeKind.CLASS) || printableRetutnType.getKind().equals(MutableTypeKind.INTERFACE)) {
					printableRetutnType = ((MutableDeclaredType)printableRetutnType).clone().stripTypeParametersTypes().stripWildcards();
				}
				//to strip wildcards
				pw.print(printableRetutnType, " ");
				if (isPrimitiveBoolean(returnType)) {
					pw.print(MethodHelper.toIsGetter(method));
				} else {
					pw.print(MethodHelper.toGetter(method));
				}
				pw.println(";");
				pw.println();
				
				if (!readOnlyProperty) {
					pw.println("void ", MethodHelper.toSetter(method) + "(", toPrintableType(context.getTypeElement(), returnType), " " + method.getSimpleName() + ");");
					pw.println();
				}
			}
		}
	}

	//TODO copied from PojoPropertyConverter
	private String getConvertedPropertyName(String originalPropertyName) {
		String result = "";

		for (int i = 0; i < originalPropertyName.length(); i++) {
			char c = originalPropertyName.charAt(i);
			if (Character.isUpperCase(c)) {
				result += "_";
			}
			result += c;
		}

		return result.toUpperCase();
	}

	@Override
	protected void printAnnotations(ProcessorContext context) {
		super.printAnnotations(context);
		context.getPrintWriter().println("@", DomainData.class);
	}
	
	@Override
	protected MutableDeclaredType[] getOutputClasses(RoundContext context) {
		return new MutableDeclaredType[] { 
				new DomainDataInterfaceType(context.getMutableType(), processingEnv) 
		};
	}

	@Override
	protected ProcessorConfigurer getConfigurer() {
		return new DomainDataInterfaceProcessorConfigurer();
	}

}
