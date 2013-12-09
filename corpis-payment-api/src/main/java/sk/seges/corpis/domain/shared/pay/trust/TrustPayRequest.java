/**
 * 
 */
package sk.seges.corpis.domain.shared.pay.trust;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import sk.seges.corpis.domain.shared.pay.PaymentMethodSettings;
import sk.seges.corpis.domain.shared.pay.SlovakPaymentMethodRequest;

/**
 * @author stefan.sivak
 *
 * @since Dec 4, 2013
 */
public class TrustPayRequest implements SlovakPaymentMethodRequest {

	private static final long serialVersionUID = -6749188465908225262L;

	public enum LangValues {
		sk, en
	}
	
	@Digits(integer = 13, fraction = 2)
	@Column
	@NotNull
	private BigDecimal amt;

	@Valid
	private TrustPaySettings settings;

	@Size(max = 512)
	@Column(length = 512)
	@Pattern(regexp = "[A-Z\\d]+")
	@NotNull
	private String sign;

	/**
	 * VS = REF
	 */
	@DecimalMax(value = "9999999999")
	@NotNull
	private Long vs;

	@Size(min = 3, max = 3)
	@Column(length = 3)
	@NotNull
	private String cur = "EUR";

	@Column(length = 2)
	@Enumerated(EnumType.STRING)
	protected LangValues lang = LangValues.sk;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.seges.corpis.domain.shared.pay.PaymentMethodRequest#setSettings(sk
	 * .seges.corpis.domain.shared.pay.PaymentMethodSettings)
	 */
	@Override
	public void setSettings(PaymentMethodSettings settings) {
		assert settings instanceof TrustPaySettings;
		this.settings = (TrustPaySettings) settings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.seges.corpis.domain.shared.pay.SlovakPaymentMethodRequest#setAmt(java
	 * .math.BigDecimal)
	 */
	@Override
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public Long getVs() {
		return vs;
	}

	public void setVs(Long vs) {
		this.vs = vs;
	}

	public TrustPaySettings getSettings() {
		return settings;
	}

	public void setSettings(TrustPaySettings settings) {
		this.settings = settings;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public BigDecimal getAmt() {
		return amt;
	}


	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public LangValues getLang() {
		return lang;
	}

	public void setLang(LangValues lang) {
		this.lang = lang;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amt == null) ? 0 : amt.hashCode());
		result = prime * result + ((cur == null) ? 0 : cur.hashCode());
		result = prime * result + ((settings == null) ? 0 : settings.hashCode());
		result = prime * result + ((sign == null) ? 0 : sign.hashCode());
		result = prime * result + ((vs == null) ? 0 : vs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrustPayRequest other = (TrustPayRequest) obj;
		if (amt == null) {
			if (other.amt != null)
				return false;
		} else if (!amt.equals(other.amt))
			return false;
		if (cur == null) {
			if (other.cur != null)
				return false;
		} else if (!cur.equals(other.cur))
			return false;
		if (settings == null) {
			if (other.settings != null)
				return false;
		} else if (!settings.equals(other.settings))
			return false;
		if (sign == null) {
			if (other.sign != null)
				return false;
		} else if (!sign.equals(other.sign))
			return false;
		if (vs == null) {
			if (other.vs != null)
				return false;
		} else if (!vs.equals(other.vs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrustPayRequest [amt=" + amt + ", settings=" + settings + ", sign=" + sign + ", vs=" + vs + ", cur="
				+ cur + "]";
	}

}
