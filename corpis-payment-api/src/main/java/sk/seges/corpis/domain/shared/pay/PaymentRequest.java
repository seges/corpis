/**
 * 
 */
package sk.seges.corpis.domain.shared.pay;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents all components of HTTP call to a payment gateway. It is a transfer
 * object between payment service and a client going to actually call the
 * payment gateway.<br/>
 * <br/>
 * The client can be e.g.:<br/>
 * <ul>
 * 	<li>client-side (e.g. GWT-built) payment form (submitting the form calls a payment gateway)</li>
 * 	<li>server-side forged payment form</li>
 *  <li>server-side HTTP call to a payment gateway</li>
 * </ul>
 * 
 * @author ladislav.gazo
 */
public class PaymentRequest implements Serializable {
	private static final long serialVersionUID = 7283067971179215106L;
	
	private String method;
	private String action;
	private Map<String, String> parameters;
	private String paymentStyleClass;
	
	public PaymentRequest() {
	}
	
	public PaymentRequest(String method, String action, Map<String, String> parameters, String paymentStyleClass) {
		super();
		this.method = method;
		this.action = action;
		this.parameters = parameters;
		this.paymentStyleClass = paymentStyleClass;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	public String getPaymentStyleClass() {
		return paymentStyleClass;
	}
	
	public void setPaymentStyleClass(String paymentStyleClass) {
		this.paymentStyleClass = paymentStyleClass;
	}

	@Override
	public String toString() {
		return "PaymentRequest [action=" + action + ", method=" + method + ", parameters=" + parameters
				+ ", paymentStyleClass=" + paymentStyleClass + "]";
	}
}
