/**
 * 
 */
package sk.seges.corpis.pay.homecredit;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map.Entry;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import sk.seges.corpis.domain.shared.pay.PaymentRequest;
import sk.seges.corpis.domain.shared.pay.homecredit.HomecreditRequest;
import sk.seges.corpis.domain.shared.pay.homecredit.HomecreditSettings;
import sk.seges.corpis.pay.signer.MD5Signer;
import sk.seges.corpis.pay.signer.PaymentSigner;

/**
 * @author stefan.sivak
 *
 * @since Dec 13, 2013
 */
public class HomecreditRealRequestBuilderTest {

	private static final String BASEURL = "https://i-shopsk-train.homecredit.net/ishop/entry.do";
	private static final String KEY = "mfrkg/ut73";

	private static final String MID = "9993";
	private static final String AMT_STRING = "11678.80";
	private static final BigDecimal AMT = new BigDecimal(AMT_STRING);
	private static final Long VS = 123456789L;
	private static final String C_NAME = "Pavel";
	private static final String C_SURNAME = "Konečný";
	private static final String G_NAME = "střešní krytina";
	private static final String G_PRODUCER = "Balexmetal";
	private static final Calendar TIME_REQUEST_CALENDAR = new GregorianCalendar(2005, 7, 21, 13, 13, 13);

	private static final String LOCALE = "sk";
	private static final String SIG = "c947e0db82bef789f5328daf11feb530";

	@Test
	public void testBasicRequestBuild() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

		HomecreditSettings settings = new HomecreditSettings();
		settings.setKey(KEY);
		settings.setBaseUrl(BASEURL);
		settings.setUrl("http://hungaropage.hu/");
		settings.setMid(MID);

		HomecreditRequest request = new HomecreditRequest();
		request.setSettings(settings);
		request.setAmt(AMT);
		request.setVs(VS);
		request.setcName(C_NAME);
		request.setcSurname(C_SURNAME);
		request.setgName(G_NAME);
		request.setgProducer(G_PRODUCER);
		request.setTimeRequest(TIME_REQUEST_CALENDAR.getTime());

		PaymentSigner signer = new MD5Signer();
		HomecreditRequestBuilder builder = new HomecreditRequestBuilder(factory.getValidator(), signer);
		PaymentRequest paymentRequest = builder.generate(request, LOCALE);

		
		StringBuilder requestStringBuilder = new StringBuilder();
		requestStringBuilder.append(BASEURL);
		String delimeter = "?";
		for (Entry<String, String> entry : paymentRequest.getParameters().entrySet()) {
			requestStringBuilder.append(delimeter + entry.getKey() + "=" + entry.getValue());
			delimeter = "&";
		}

		System.out.println(requestStringBuilder.toString());

		// assertEquals(SIG,
		// paymentRequest.getParameters().get(EHomecreditParameter.SH.getName()));
		//
		// assertEquals(new DecimalFormat("##0.00",
		// DecimalFormatSymbols.getInstance(new Locale("sk"))).format(AMT),
		// paymentRequest.getParameters().get(EHomecreditParameter.AMT.getName()));
	}
}
