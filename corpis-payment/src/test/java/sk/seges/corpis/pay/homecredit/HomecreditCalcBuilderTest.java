/**
 * 
 */
package sk.seges.corpis.pay.homecredit;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import sk.seges.corpis.domain.shared.pay.PaymentRequest;
import sk.seges.corpis.domain.shared.pay.homecredit.EHomecreditParameter;
import sk.seges.corpis.domain.shared.pay.homecredit.HomecreditCalcRequest;
import sk.seges.corpis.domain.shared.pay.homecredit.HomecreditSettings;
import sk.seges.corpis.pay.signer.MD5Signer;
import sk.seges.corpis.pay.signer.PaymentSigner;

/**
 * @author stefan.sivak
 *
 * @since Dec 19, 2013
 */
public class HomecreditCalcBuilderTest {

	private static final String KEY = "wosfhasfasdfasd";
	private static final String BASEURL = "https://i-shopsk-train.homecredit.net/ishop/entry.do";
	private static final String MID = "55";

	private static final String AMT_STRING = "15940.40";
	private static final BigDecimal AMT = new BigDecimal(AMT_STRING);
	private static final Calendar TIME_REQUEST_CALENDAR = new GregorianCalendar(2005, 7, 21, 13, 13, 13);

	private static final String LOCALE = "sk";

	private static final String SIG = "97cedfab32cebbed9d80e706289166ec";

	@Test
	public void testBasicRequestBuild() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

		HomecreditSettings settings = new HomecreditSettings();
		settings.setKey(KEY);
		settings.setBaseUrl(BASEURL);
		settings.setUrl("http://hungaropage.hu/");
		settings.setMid(MID);

		HomecreditCalcRequest request = new HomecreditCalcRequest();
		request.setSettings(settings);
		request.setAmt(AMT);
		request.setTimeRequest(TIME_REQUEST_CALENDAR.getTime());

		PaymentSigner signer = new MD5Signer();
		HomecreditCalcRequestBuilder builder = new HomecreditCalcRequestBuilder(factory.getValidator(), signer);
		PaymentRequest paymentRequest = builder.generate(request, LOCALE);

		assertEquals(SIG, paymentRequest.getParameters().get(EHomecreditParameter.SH.getName()));

		assertEquals(new DecimalFormat("##0.00", DecimalFormatSymbols.getInstance(new Locale("sk"))).format(AMT),
				paymentRequest.getParameters().get(EHomecreditParameter.AMT.getName()));

	}

}
