/**
 * 
 */
package sk.seges.corpis.pay.homecredit;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import sk.seges.corpis.domain.shared.pay.PaymentRequest;
import sk.seges.corpis.domain.shared.pay.homecredit.EHomecreditParameter;
import sk.seges.corpis.domain.shared.pay.homecredit.HomecreditCalcRequest;
import sk.seges.corpis.pay.JSRValidatorAwarePaymentRequestBuilder;
import sk.seges.corpis.pay.signer.PaymentSigner;

/**
 * @author stefan.sivak
 *
 * @since Dec 19, 2013
 */
public class HomecreditCalcRequestBuilder extends JSRValidatorAwarePaymentRequestBuilder<HomecreditCalcRequest> {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");

	private final PaymentSigner signer;

	/**
	 * @param validator
	 */
	public HomecreditCalcRequestBuilder(Validator validator, PaymentSigner signer) {
		super(validator);
		this.signer = signer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.seges.corpis.pay.ElectronicPaymentRequestBuilder#generate(sk.seges
	 * .corpis.domain.shared.pay.PaymentMethodRequest, java.lang.String)
	 */
	@Override
	public PaymentRequest generate(HomecreditCalcRequest request, String locale) {
		Map<String, String> params = new LinkedHashMap<String, String>();

		NumberFormat ff = new DecimalFormat("##0.00", DecimalFormatSymbols.getInstance(new Locale("sk")));
		String amt = ff.format(request.getAmt());

		fill(params, EHomecreditParameter.MID.getName(), request.getSettings().getMid());
		fill(params, EHomecreditParameter.AMT.getName(), amt);

		if (request.getTimeRequest() == null) {
			request.setTimeRequest(new Date());
		}

		fill(params, EHomecreditParameter.TIME_REQUEST.getName(), DATE_FORMAT.format(request.getTimeRequest()));

		String signature = signer.forgeSignature(request.getSettings(), params.values());
		request.setSign(signature);
		fill(params, EHomecreditParameter.SH.getName(), signature);

		Set<ConstraintViolation<HomecreditCalcRequest>> violations = validate(request);
		if (violations.size() > 0) {
			throw new ValidationException("Request contains validation errors = " + violations);
		}

		PaymentRequest paymentRequest = new PaymentRequest(METHOD_GET, request.getSettings().getCalcUrl(), params,
				"payment-homecreditCalc");

		return paymentRequest;
	}

}
