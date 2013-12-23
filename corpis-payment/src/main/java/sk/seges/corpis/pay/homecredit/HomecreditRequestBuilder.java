/**
 * 
 */
package sk.seges.corpis.pay.homecredit;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import sk.seges.corpis.domain.shared.pay.homecredit.HomecreditRequest;
import sk.seges.corpis.pay.JSRValidatorAwarePaymentRequestBuilder;
import sk.seges.corpis.pay.signer.PaymentSigner;

/**
 * @author stefan.sivak
 * 
 * @since Dec 12, 2013
 */
public class HomecreditRequestBuilder extends JSRValidatorAwarePaymentRequestBuilder<HomecreditRequest> {

	private static final String UTF8 = "UTF-8";

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");

	private final PaymentSigner signer;

	/**
	 * @param validator
	 */
	public HomecreditRequestBuilder(Validator validator, PaymentSigner signer) {
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
	public PaymentRequest generate(HomecreditRequest request, String locale) {
		Map<String, String> params = new LinkedHashMap<String, String>();

		NumberFormat ff = new DecimalFormat("##0.00", DecimalFormatSymbols.getInstance(new Locale("sk")));
		String amt = ff.format(request.getAmt());

		fill(params, EHomecreditParameter.MID.getName(), request.getSettings().getMid());
		fill(params, EHomecreditParameter.VS.getName(), request.getVs());
		fill(params, EHomecreditParameter.AMT.getName(), amt);
		fill(params, EHomecreditParameter.C_NAME.getName(), request.getcName());
		fill(params, EHomecreditParameter.C_SURNAME.getName(), request.getcSurname());
		fill(params, EHomecreditParameter.G_NAME.getName(), request.getgName());
		fill(params, EHomecreditParameter.G_PRODUCER.getName(), request.getgProducer());

		if (request.getTimeRequest() == null) {
			request.setTimeRequest(new Date());
		}

		fill(params, EHomecreditParameter.TIME_REQUEST.getName(), DATE_FORMAT.format(request.getTimeRequest()));

		String signature = signer.forgeSignature(request.getSettings(), params.values());
		request.setSign(signature);
		fill(params, EHomecreditParameter.SH.getName(), signature);

		params.put(EHomecreditParameter.C_NAME.getName(), encodeParameterWithPlusReplacement(request.getcName()));
		params.put(EHomecreditParameter.C_SURNAME.getName(), encodeParameterWithPlusReplacement(request.getcSurname()));
		params.put(EHomecreditParameter.G_NAME.getName(), encodeParameterWithPlusReplacement(request.getgName()));
		params.put(EHomecreditParameter.G_PRODUCER.getName(), encodeParameterWithPlusReplacement(request.getgProducer()));

		Set<ConstraintViolation<HomecreditRequest>> violations = validate(request);
		if (violations.size() > 0) {
			throw new ValidationException("Request contains validation errors = " + violations);
		}

		PaymentRequest paymentRequest = new PaymentRequest(METHOD_GET, request.getSettings().getBaseUrl(), params,
				"payment-homecredit");

		return paymentRequest;
	}

	private String encodeParameterWithPlusReplacement(String param) {
		try {
			return URLEncoder.encode(param, UTF8).replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {}
		return null;
	}

}
