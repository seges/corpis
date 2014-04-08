package sk.seges.corpis.domain.shared.pay.paypal;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import sk.seges.corpis.RegexConstants;
import sk.seges.corpis.domain.shared.pay.HasKeyPaymentMethodSettings;
import sk.seges.corpis.domain.shared.pay.PaymentMethodSettings;

public class PayPalSettings implements PaymentMethodSettings, HasKeyPaymentMethodSettings {
	
	private static final long serialVersionUID = 5483260229437874966L;
	
	/**
	 * value "https://www.paypal.com/cgi-bin/webscr"
	 */
	@Column
	private String action;
	/**
	 * value = "post"
	 */
	@Column
	private String method;
	
	/**
	 * for buyButton "_xclick"
	 */
	@Column
	private String cmd;
	
	/**
	 * Your PayPal ID or an email address associated with your PayPal account
	 */
	@Column
	private String business;
	
	/**
	 * Do not prompt payers for shipping address. Allowable values:
	 * [0] prompt for an address, but do not require one
	 * [1] do not prompt for an address
	 * [2] prompt for an address, and require one
	 */
	@Column
	private String no_shipping;
	
	/**
	 * Do not prompt payers to include a note with their payments. Allowable values:
	 * [1] hide the text box and the prompt
	 */
	@Column
	private String no_note;
	
	/**
	 * An internet URL where the user will be returned after completing the payment
	 */
	@Pattern(regexp = RegexConstants.WEB_URL)
	@Column
	@NotNull
	private String returnURL;
	
	@Pattern(regexp = RegexConstants.WEB_URL)
	@Column
	@NotNull
	private String cancelReturnUrl;
	
	@Column
	@NotNull
	private String key;

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getNo_shipping() {
		return no_shipping;
	}

	public void setNo_shipping(String no_shipping) {
		this.no_shipping = no_shipping;
	}

	public String getNo_note() {
		return no_note;
	}

	public void setNo_note(String no_note) {
		this.no_note = no_note;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	public String getCancelReturnUrl() {
		return cancelReturnUrl;
	}

	public void setCancelReturnUrl(String cancelReturnUrl) {
		this.cancelReturnUrl = cancelReturnUrl;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("\n  action = ").append(getAction());
		sb.append("\n  method = ").append(getMethod());
		sb.append("\n  cmd = ").append(getCmd());
		sb.append("\n  business = ").append(getBusiness());
		sb.append("\n  no_shipping = ").append(getNo_shipping());
		sb.append("\n  no_note = ").append(getNo_note());
		sb.append("\n  returnURL = ").append(getReturnURL());
		sb.append("\n  cancelReturnUrl = ").append(getCancelReturnUrl());
		return sb.toString();
	}
}
