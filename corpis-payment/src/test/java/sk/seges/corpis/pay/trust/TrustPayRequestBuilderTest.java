/**
 * 
 */
package sk.seges.corpis.pay.trust;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import sk.seges.corpis.domain.shared.pay.PaymentRequest;
import sk.seges.corpis.domain.shared.pay.trust.ETrustPayParameter;
import sk.seges.corpis.domain.shared.pay.trust.TrustPayRequest;
import sk.seges.corpis.domain.shared.pay.trust.TrustPaySettings;
import sk.seges.corpis.pay.signer.HmacSigner;
import sk.seges.corpis.pay.signer.PaymentSigner;

/**
 * @author stefan.sivak
 * 
 * @since Dec 4, 2013
 * 
 * <br/>
 * <br/>
 *        <i>Example page 12: MID = AID, VS = REF </i>
 *        http://trustpay.eu/assets/Uploads/Merchant-API-integration-v2.6.pdf <br/>
 * <br/>
 *        http://trustpay.eu/trustpay-kontakty-referencie-platobne-metody-
 *        novinky/dokumenty-na-stiahnutie/
 */
public class TrustPayRequestBuilderTest {

	private static final String BASEURL = "https://test.trustpay.eu/mapi/pay.aspx";
	private static final String KEY = "abcd1234";

	/**
	 * MID = AID
	 */
	private static final String MID = "9876543210";
	private static final String AMT_STRING = "123.45";
	private static final BigDecimal AMT = new BigDecimal(AMT_STRING);
	private static final String CUR = "EUR";
	private static final String LOCALE = "sk";

	/**
	 * VS = REF
	 */
	private static final Long VS = 1234567890L;
	private static final String SIG = "DF174E635DABBFF7897A82822521DD739AE8CC2F83D65F6448DD2FF991481EA3";

	@Test
	public void testBasicRequestBuild() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

		TrustPaySettings settings = new TrustPaySettings();
		settings.setKey(KEY);
		settings.setBaseUrl(BASEURL);
		settings.setUrl("http://hungaropage.hu/");
		settings.setMid(MID);

		TrustPayRequest request = new TrustPayRequest();
		request.setSettings(settings);
		request.setAmt(AMT);
		request.setCur(CUR);
		request.setVs(VS);

		PaymentSigner signer = new HmacSigner();
		TrustPayRequestBuilder builder = new TrustPayRequestBuilder(factory.getValidator(), signer);
		PaymentRequest paymentRequest = builder.generate(request, LOCALE);

		assertEquals(SIG, paymentRequest.getParameters().get(ETrustPayParameter.SIG.getName()));

		assertEquals(AMT_STRING, paymentRequest.getParameters().get(ETrustPayParameter.AMT.getName()));
	}

}
