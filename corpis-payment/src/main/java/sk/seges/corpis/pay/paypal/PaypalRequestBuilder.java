package sk.seges.corpis.pay.paypal;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Validator;

import sk.seges.corpis.domain.shared.pay.PaymentRequest;
import sk.seges.corpis.domain.shared.pay.paypal.PayPalParameter;
import sk.seges.corpis.domain.shared.pay.paypal.PaypalRequest;
import sk.seges.corpis.pay.JSRValidatorAwarePaymentRequestBuilder;

public class PaypalRequestBuilder extends JSRValidatorAwarePaymentRequestBuilder<PaypalRequest> {

	private static DecimalFormat formatter = null;
	
	public PaypalRequestBuilder(Validator validator) {
		super(validator);
	}

	@Override
	public PaymentRequest generate(PaypalRequest request, String locale) {
		Map<String, String> params = new LinkedHashMap<String, String>();
		fill(params, PayPalParameter.CMD.getName(), request.getSettings().getCmd());
		fill(params, PayPalParameter.BUSINESS.getName(), request.getSettings().getBusiness());
		fill(params, PayPalParameter.ITEM_NAME.getName(), request.getItem_name());
		fill(params, PayPalParameter.ITEM_NUMBER.getName(), request.getItem_number());
		fill(params, PayPalParameter.AMOUNT.getName(), getFormatter().format(request.getAmt()));
		fill(params, PayPalParameter.NO_SHIPPING.getName(), request.getSettings().getNo_shipping());
		fill(params, PayPalParameter.NO_NOTE.getName(), request.getSettings().getNo_note());
		fill(params, PayPalParameter.CURRENCY_CODE.getName(), request.getCurrency_code());
		fill(params, PayPalParameter.RETURN.getName(), request.getSettings().getReturnURL());
		fill(params, PayPalParameter.CANCEL_RETURN.getName(), request.getSettings().getCancelReturnUrl());
		
		PaymentRequest paymentRequest = new PaymentRequest(METHOD_POST, request.getSettings().getAction(), params, "paymen-PayPal");
		return paymentRequest;
	}
	
	private DecimalFormat getFormatter() {
		if (formatter == null) {
			formatter = new DecimalFormat("##.##");
			DecimalFormatSymbols dfSymb = formatter.getDecimalFormatSymbols();
			dfSymb.setDecimalSeparator('.');
			formatter.setDecimalFormatSymbols(dfSymb);
		}
		return formatter;
	}
}
