package sk.seges.corpis.pay.paypal;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import sk.seges.corpis.domain.shared.pay.PaymentRequest;
import sk.seges.corpis.domain.shared.pay.paypal.PayPalParameter;
import sk.seges.corpis.domain.shared.pay.paypal.PayPalSettings;
import sk.seges.corpis.domain.shared.pay.paypal.PaypalRequest;

public class PaypalRequestBuilderTest {
	
	private static final String BASEURL = "https://www.paypal.com/cgi-bin/webscr";
	
	@Test
	public void testBasicRequestBuild() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		PayPalSettings settings = new PayPalSettings();
		settings.setAction(BASEURL);
		settings.setMethod("post");
		settings.setCmd("_xclick");
		settings.setNo_shipping("2");
		settings.setNo_note("1");
		
		PaypalRequest request = new PaypalRequest();
		request.setSettings(settings);
		request.setAmt(BigDecimal.valueOf(12.7d));
		request.setVs(235664L);
		request.setCurrency_code("EUR");
		
		PaypalRequestBuilder builder = new PaypalRequestBuilder(factory.getValidator());
		PaymentRequest paymentRequest = builder.generate(request, null);
		
		assertEquals("12.7", paymentRequest.getParameters().get(PayPalParameter.AMOUNT.getName()));
	}
}
