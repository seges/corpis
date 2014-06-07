/**
 * 
 */
package sk.seges.corpis.pay;

import java.util.Set;

import javax.validation.ConstraintViolation;

import sk.seges.corpis.domain.shared.pay.PaymentMethodRequest;
import sk.seges.corpis.domain.shared.pay.PaymentRequest;

/**
 * Request builder is responsible for transformation of
 * {@link PaymentMethodRequest} into {@link PaymentRequest} which represents
 * HTTP request finally called against payment gateway.
 * 
 * The builder takes the request, calculates necessary signatures and prepares
 * all components of HTTP request (method, action, parameters,...) so the client
 * (via a service / resource) can call the payment gateway.
 * 
 * @author ladislav.gazo
 */
public interface ElectronicPaymentRequestBuilder<T extends PaymentMethodRequest> {
	static final String METHOD_GET = "get";
	static final String METHOD_POST = "post";
	
	PaymentRequest generate(T request, String locale);
	Set<ConstraintViolation<T>> validate(T request);
}
