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
import sk.seges.corpis.domain.shared.pay.homecredit.HomecreditRequest;
import sk.seges.corpis.domain.shared.pay.homecredit.HomecreditSettings;
import sk.seges.corpis.pay.signer.MD5Signer;
import sk.seges.corpis.pay.signer.PaymentSigner;

/**
 * @author stefan.sivak
 * 
 * @since Dec 12, 2013
 */
public class HomecreditRequestBuilderTest {

	private static final String BASEURL = "https://i-shopsk-train.homecredit.net/ishop/entry.do";
	private static final String KEY = "wosfhasfasdfasd";

	private static final String MID = "55";
	private static final String AMT_STRING = "15940.40";
	private static final BigDecimal AMT = new BigDecimal(AMT_STRING);
	private static final Long VS = 45124L;
	private static final String C_NAME = "Jan";
	private static final String C_SURNAME = "Novák";
	private static final String G_NAME = "Pračka Z454";
	private static final String G_PRODUCER = "Zanussi";
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

		assertEquals(SIG, paymentRequest.getParameters().get(EHomecreditParameter.SH.getName()));

		assertEquals(new DecimalFormat("##0.00", DecimalFormatSymbols.getInstance(new Locale("sk"))).format(AMT),
				paymentRequest.getParameters().get(EHomecreditParameter.AMT.getName()));

	}
}
