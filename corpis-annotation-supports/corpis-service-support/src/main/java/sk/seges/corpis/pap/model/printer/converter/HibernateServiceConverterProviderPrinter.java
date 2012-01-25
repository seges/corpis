package sk.seges.corpis.pap.model.printer.converter;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.ExecutableElement;

import sk.seges.corpis.pap.service.hibernate.accessor.TransactionPropagationAccessor;
import sk.seges.corpis.service.annotation.TransactionPropagationModel;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType.RenameActionType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableType;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeMirror;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeMirror.MutableTypeKind;
import sk.seges.sesam.core.pap.model.mutable.api.MutableTypeVariable;
import sk.seges.sesam.core.pap.model.mutable.api.MutableWildcardType;
import sk.seges.sesam.core.pap.model.mutable.utils.MutableTypes;
import sk.seges.sesam.core.pap.writer.FormattedPrintWriter;
import sk.seges.sesam.pap.model.hibernate.resolver.HibernateParameterResolverDelegate;
import sk.seges.sesam.pap.model.model.ConverterParameter;
import sk.seges.sesam.pap.model.model.ConverterTypeElement;
import sk.seges.sesam.pap.model.model.TransferObjectProcessingEnvironment;
import sk.seges.sesam.pap.model.printer.converter.ConverterProviderPrinter;
import sk.seges.sesam.pap.model.resolver.api.ParametersResolver;
import sk.seges.sesam.shared.model.converter.api.ConverterProvider;

public class HibernateServiceConverterProviderPrinter extends ConverterProviderPrinter {

	private TransferObjectProcessingEnvironment processingEnv;
	private static final String THIS = "this";
	
	public HibernateServiceConverterProviderPrinter(FormattedPrintWriter pw, TransferObjectProcessingEnvironment processingEnv,
			ParametersResolver parametersResolver) {
		super(pw, processingEnv, parametersResolver);
		this.processingEnv = processingEnv;
	}

	@Override
	protected MutableType[] getConverterParametersUsage(ConverterTypeElement converterTypeElement, ExecutableElement method) {
		MutableType[] converterParameters = super.getConverterParametersUsage(converterTypeElement, method);
		
		MutableTypes typeUtils = processingEnv.getTypeUtils();
		
		MutableDeclaredType transactionPropagationModel = typeUtils.toMutableType(TransactionPropagationModel.class);
		
		TransactionPropagationAccessor transactionPropagationAccessor = new TransactionPropagationAccessor(method, processingEnv);
		
		List<ConverterParameter> converterParametersTypes = converterTypeElement.getConverterParameters(parametersResolver);

		List<MutableType> generatedParams = new ArrayList<MutableType>();

		//TODO - ugly implementation
		for (ConverterParameter converterParametersType: converterParametersTypes) {
			if (!converterParametersType.isPropagated()) {
				if (processingEnv.getTypeUtils().isSameType(converterParametersType.getType(), typeUtils.getArrayType(transactionPropagationModel))) {
					generatedParams.add(processingEnv.getTypeUtils().getReference(typeUtils.getArrayValue(typeUtils.getArrayType(transactionPropagationModel), (Object[])transactionPropagationAccessor.getPropagations()),
							HibernateParameterResolverDelegate.TRANSACTION_PROPAGATION_NAME));
				}
				if (processingEnv.getTypeUtils().isSameType(converterParametersType.getType(), typeUtils.toMutableType(ConverterProvider.class))) {
					generatedParams.add(processingEnv.getTypeUtils().getReference(null, THIS));
				}
			}
		}

		MutableType[] result = new MutableType[converterParameters.length + generatedParams.size()];

		int i = 0;

		for (MutableType converterParameter: generatedParams) {
			result[i++] = converterParameter;
		}

		for (MutableType converterParameter: converterParameters) {
			if (converterParameter instanceof MutableTypeMirror && ((MutableTypeMirror)converterParameter).getKind().isDeclared()) {
				result[i++] = ((MutableDeclaredType)converterParameter).clone().renameTypeParameter(RenameActionType.REPLACE, MutableWildcardType.WILDCARD_NAME);
			} else if (converterParameter instanceof MutableTypeMirror && ((MutableTypeMirror)converterParameter).getKind().equals(MutableTypeKind.TYPEVAR)) {
				if (((MutableTypeVariable)converterParameter).clone().getVariable() != null) {
					result[i++] = ((MutableTypeVariable)converterParameter).clone().setVariable(MutableWildcardType.WILDCARD_NAME);
				} else {
					result[i++] = converterParameter;
				}
			} else {
				result[i++] = converterParameter;
			}
		}

		return result;
	}
}