/**
 * 
 */
package sk.seges.corpis.pay.trust;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import sk.seges.corpis.domain.shared.pay.PaymentRequest;
import sk.seges.corpis.domain.shared.pay.trust.ETrustPayParameter;
import sk.seges.corpis.domain.shared.pay.trust.TrustPayRequest;
import sk.seges.corpis.domain.shared.pay.trust.TrustPaySettings;
import sk.seges.corpis.pay.JSRValidatorAwarePaymentRequestBuilder;
import sk.seges.corpis.pay.signer.PaymentSigner;

/**
 * @author stefan.sivak
 *
 * @since Dec 4, 2013
 */
public class TrustPayRequestBuilder extends JSRValidatorAwarePaymentRequestBuilder<TrustPayRequest> {

	private final PaymentSigner signer;

	/**
	 * @param validator
	 */
	public TrustPayRequestBuilder(Validator validator, PaymentSigner signer) {
		super(validator);
		this.signer = signer;
	}

	/*
	 * 
	 * MID = AID, VS = REF
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.seges.corpis.pay.ElectronicPaymentRequestBuilder#generate(sk.seges
	 * .corpis.domain.shared.pay.PaymentMethodRequest)
	 */
	@Override
	public PaymentRequest generate(TrustPayRequest request, String locale) {
		Map<String, String> params = new LinkedHashMap<String, String>();

		NumberFormat ff = new DecimalFormat("##0.00", DecimalFormatSymbols.getInstance(new Locale("en")));
		String amt = ff.format(request.getAmt());

		TrustPaySettings settings = request.getSettings();

		// required parameters
		// MID = AID
		fill(params, ETrustPayParameter.MID.getName(), settings.getMid());
		fill(params, ETrustPayParameter.AMT.getName(), amt);
		fill(params, ETrustPayParameter.CUR.getName(), request.getCur());
		// VS = REF
		fill(params, ETrustPayParameter.VS.getName(), request.getVs());
		String signature = signer.forgeSignature(settings, params.values());
		request.setSign(signature);
		fill(params, ETrustPayParameter.SIG.getName(), signature);


		// optional parameters
		if (settings.getUrl() != null) {
			fill(params, ETrustPayParameter.URL.getName(), settings.getUrl());
		}
		if (locale != null) {
			fill(params, ETrustPayParameter.LNG.getName(), locale);
		}
		if (settings.getNurl() != null) {
			fill(params, ETrustPayParameter.NURL.getName(), settings.getNurl());
		}

		if (settings.getRurl() != null) {
			fill(params, ETrustPayParameter.RURL.getName(), settings.getRurl());
		}

		if (settings.getEurl() != null) {
			fill(params, ETrustPayParameter.EURL.getName(), settings.getEurl());
		}

		if (settings.getCurl() != null) {
			fill(params, ETrustPayParameter.CURL.getName(), settings.getCurl());
		}

		Set<ConstraintViolation<TrustPayRequest>> violations = validate(request);
		if (violations.size() > 0) {
			throw new ValidationException("Request contains validation errors = " + violations);
		}


		PaymentRequest paymentRequest = new PaymentRequest(METHOD_GET, request.getSettings().getBaseUrl(), params,
				"payment-trustPay");

		return paymentRequest;
	}

}
