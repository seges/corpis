/**
 * 
 */
package sk.seges.corpis.domain.shared.pay.homecredit;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import sk.seges.corpis.domain.shared.pay.PaymentMethodSettings;
import sk.seges.corpis.domain.shared.pay.SlovakPaymentMethodRequest;
import sk.seges.corpis.domain.shared.pay.trust.TrustPaySettings;

/**
 * @author stefan.sivak
 *
 * @since Dec 19, 2013
 */
public class HomecreditCalcRequest implements SlovakPaymentMethodRequest {

	private static final long serialVersionUID = -1712474931814442201L;

	@Digits(integer = 13, fraction = 2)
	@Column
	@NotNull
	private BigDecimal amt;

	@Valid
	private HomecreditSettings settings;

	@Size(max = 512)
	@Column(length = 512)
	@Pattern(regexp = "[A-Za-z\\d]+")
	@NotNull
	private String sign;

	private Date timeRequest;


	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public HomecreditSettings getSettings() {
		return settings;
	}

	public void setSettings(HomecreditSettings settings) {
		this.settings = settings;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Date getTimeRequest() {
		return timeRequest;
	}

	public void setTimeRequest(Date timeRequest) {
		this.timeRequest = timeRequest;
	}

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
		this.settings = (HomecreditSettings) settings;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.seges.corpis.domain.shared.pay.SlovakPaymentMethodRequest#setVs(java
	 * .lang.Long)
	 */
	@Override
	public void setVs(Long vs) {
		// TODO Auto-generated method stub

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amt == null) ? 0 : amt.hashCode());
		result = prime * result + ((settings == null) ? 0 : settings.hashCode());
		result = prime * result + ((sign == null) ? 0 : sign.hashCode());
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
		HomecreditCalcRequest other = (HomecreditCalcRequest) obj;
		if (amt == null) {
			if (other.amt != null)
				return false;
		} else if (!amt.equals(other.amt))
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
		return true;
	}

}
