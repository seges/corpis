package sk.seges.corpis.domain.shared.pay.paypal;

import java.math.BigDecimal;

import javax.validation.Valid;

import sk.seges.corpis.domain.shared.pay.PaymentMethodSettings;
import sk.seges.corpis.domain.shared.pay.SlovakPaymentMethodRequest;

public class PaypalRequest implements SlovakPaymentMethodRequest {
	private static final long serialVersionUID = -6215046504556055250L;
	
	private Long vs;
	private String item_name;
	private String item_number;
	private BigDecimal amt;
	private String currency_code;
	
	@Valid
	private PayPalSettings settings;
	
	@Override
	public void setSettings(PaymentMethodSettings settings) {
		assert settings instanceof PayPalSettings;
		this.settings = (PayPalSettings) settings;
	}

	public PayPalSettings getSettings() {
		return settings;
	}

	public void setSettings(PayPalSettings settings) {
		this.settings = settings;
	}

	@Override
	public void setVs(Long vs) {
		this.vs = vs;
	}
	
	public Long getVs() {
		return vs;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	@Override
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	
	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_number() {
		return item_number;
	}

	public void setItem_number(String item_number) {
		this.item_number = item_number;
	}
	
	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
}