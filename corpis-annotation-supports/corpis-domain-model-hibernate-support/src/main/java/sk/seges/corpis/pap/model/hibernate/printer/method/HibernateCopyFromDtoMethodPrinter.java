package sk.seges.corpis.pap.model.hibernate.printer.method;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;

import sk.seges.corpis.pap.model.hibernate.resolver.HibernateEntityResolver;
import sk.seges.corpis.shared.converter.utils.utils.ConverterUtils;
import sk.seges.sesam.core.pap.model.PathResolver;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeMirror;
import sk.seges.sesam.core.pap.utils.MethodHelper;
import sk.seges.sesam.core.pap.writer.FormattedPrintWriter;
import sk.seges.sesam.pap.model.context.api.TransferObjectContext;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateParameterResolverDelegate;
import sk.seges.sesam.pap.model.model.Field;
import sk.seges.sesam.pap.model.model.TransferObjectMappingAccessor;
import sk.seges.sesam.pap.model.model.TransferObjectProcessingEnvironment;
import sk.seges.sesam.pap.model.model.api.dto.DtoDeclaredType;
import sk.seges.sesam.pap.model.printer.api.TransferObjectElementPrinter;
import sk.seges.sesam.pap.model.printer.converter.ConverterProviderPrinter;
import sk.seges.sesam.pap.model.printer.converter.ConverterTargetType;
import sk.seges.sesam.pap.model.printer.method.CopyFromDtoMethodPrinter;
import sk.seges.sesam.pap.model.resolver.ConverterConstructorParametersResolverProvider;
import sk.seges.sesam.pap.model.resolver.api.EntityResolver;
import sk.seges.sesam.utils.CastUtils;

public class HibernateCopyFromDtoMethodPrinter extends CopyFromDtoMethodPrinter {

	private HibernateEntityResolver entityResolver;
	
	public HibernateCopyFromDtoMethodPrinter(Set<String> instances, ConverterProviderPrinter converterProviderPrinter, EntityResolver entityResolver,
			ConverterConstructorParametersResolverProvider parametersResolverProvider, RoundEnvironment roundEnv, TransferObjectProcessingEnvironment processingEnv) {
		super(instances, converterProviderPrinter, entityResolver, parametersResolverProvider, roundEnv, processingEnv);
		this.entityResolver = (HibernateEntityResolver)entityResolver;
	}
	
	
	@Override
    protected void printCopyByConverter(TransferObjectContext context, PathResolver domainPathResolver, FormattedPrintWriter pw) {
    	if (entityResolver.isLazyReference(context.getDomainMethod())) {
    		pw.println("if (", ConverterUtils.class,".convertArg(" + HibernateParameterResolverDelegate.TRANSACTION_PROPAGATION_NAME + ", \"" + domainPathResolver.getPath() + "\")) {");
    		pw.println("if (" + TransferObjectElementPrinter.RESULT_NAME + "." + MethodHelper.toGetter(domainPathResolver.getCurrent()) + " != null) {");
    		pw.println("if (" + TransferObjectElementPrinter.DTO_NAME  + "." + MethodHelper.toGetter(context.getDtoFieldName()) + " != null) {");
    		String converterName = "converter" + MethodHelper.toMethod("", context.getDtoFieldName());
    		pw.print(context.getConverter().getConverterBase(), " " + converterName + " = ");
    		Field field = new Field(TransferObjectElementPrinter.DTO_NAME  + "." + MethodHelper.toGetter(context.getDtoFieldName()), context.getConverter().getDto());

    		TransferObjectMappingAccessor transferObjectMappingAccessor = new TransferObjectMappingAccessor(context.getDtoMethod(), processingEnv);
    		if (transferObjectMappingAccessor.isValid() && transferObjectMappingAccessor.getConverter() != null) {
  				converterProviderPrinter.printDtoGetConverterMethodName(context.getConverter().getDto(), field, context.getDtoMethod(), pw, false);
    		} else {
    			converterProviderPrinter.printObtainConverterFromCache(pw, ConverterTargetType.DTO, context.getConverter().getConfiguration().getInstantiableDomain(), field, context.getDomainMethod(), true);
    		}

    		pw.println(";");
    		pw.print(TransferObjectElementPrinter.RESULT_NAME + "." + MethodHelper.toSetter(domainPathResolver.getPath()) + "(");
    		
    		MutableTypeMirror parameterType = getParameterType(context, domainPathResolver);
    		
    		pw.print("(", parameterType, ")");
    		
    		if (isCastReuqired(parameterType)) {
    			pw.print(CastUtils.class, ".cast(");
    		}

    		pw.print(converterName + ".convertFromDto(");
			pw.print(CastUtils.class, ".cast(");
			pw.print(TransferObjectElementPrinter.RESULT_NAME  + "." + MethodHelper.toGetter(domainPathResolver.getCurrent()) + ", ");

			printCastDomainType(context, domainPathResolver, context.getConverter().getDomain(), pw);
			pw.print(".class), ");

    		if (context.getConverter().getDto().getKind().isDeclared() && ((DtoDeclaredType)context.getConverter().getDto()).hasTypeParameters()) {
	    		pw.print(CastUtils.class, ".cast(");
	    		pw.print(TransferObjectElementPrinter.DTO_NAME  + "." + MethodHelper.toGetter(context.getDtoFieldName()) + ", ");
	    		pw.print(getTypeVariableDelegate(getDelegateCast(context.getConverter().getDto(), true)), ".class)");
    		} else {
	    		pw.print(TransferObjectElementPrinter.DTO_NAME  + "." + MethodHelper.toGetter(context.getDtoFieldName()));
    		}
    		
    		pw.print(")");
    		
    		if (isCastReuqired(parameterType)) {
        		pw.print(", ");
	    		printCastDomainType(context, domainPathResolver, processingEnv.getTransferObjectUtils().getDomainType(parameterType), pw);
	    		pw.print(".class)");
    		}
    		pw.println(");");
        	pw.println("} else {");
        	pw.println(TransferObjectElementPrinter.RESULT_NAME + "." + MethodHelper.toSetter(domainPathResolver.getPath()) + "(null);");
        	pw.println("}");
        	pw.println("} else {");
    		super.printCopyByConverter(context, domainPathResolver, pw);
        	pw.println("}");
        	pw.println("}");
    	} else {
    		super.printCopyByConverter(context, domainPathResolver, pw);
    	}
    }    
}