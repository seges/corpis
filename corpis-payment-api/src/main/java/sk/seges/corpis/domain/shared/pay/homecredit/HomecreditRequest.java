/**
 * 
 */
package sk.seges.corpis.domain.shared.pay.homecredit;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
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
 * @since Dec 10, 2013
 */
public class HomecreditRequest implements SlovakPaymentMethodRequest {

	private static final long serialVersionUID = -6500735979152244518L;

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

	/**
	 * VS = REF
	 */
	@DecimalMax(value = "9999999999")
	@NotNull
	private Long vs;

	private String cName;

	private String cSurname;

	private String gName;

	private String gProducer;

	private Date timeRequest;

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
		this.vs = vs;
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

	public BigDecimal getAmt() {
		return amt;
	}

	public Long getVs() {
		return vs;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcSurname() {
		return cSurname;
	}

	public void setcSurname(String cSurname) {
		this.cSurname = cSurname;
	}

	public String getgName() {
		return gName;
	}

	public void setgName(String gName) {
		this.gName = gName;
	}

	public String getgProducer() {
		return gProducer;
	}

	public void setgProducer(String gProducer) {
		this.gProducer = gProducer;
	}

	public Date getTimeRequest() {
		return timeRequest;
	}

	public void setTimeRequest(Date timeRequest) {
		this.timeRequest = timeRequest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amt == null) ? 0 : amt.hashCode());
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
		HomecreditRequest other = (HomecreditRequest) obj;
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
		if (vs == null) {
			if (other.vs != null)
				return false;
		} else if (!vs.equals(other.vs))
			return false;
		return true;
	}

}
