/**
 * 
 */
package sk.seges.corpis.domain.shared.pay.homecredit;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import sk.seges.corpis.RegexConstants;
import sk.seges.corpis.domain.shared.pay.HasKeyPaymentMethodSettings;
import sk.seges.corpis.domain.shared.pay.PaymentMethodSettings;

/**
 * @author stefan.sivak
 *
 * @since Dec 10, 2013
 */
public class HomecreditSettings implements PaymentMethodSettings, HasKeyPaymentMethodSettings {

	private static final long serialVersionUID = 5675875799481360519L;

	public static final String MID_ATTRIBUTE = "mid";

	/** Present because of ability to switch between production and sandbox. */
	@Pattern(regexp = RegexConstants.WEB_URL)
	@Column
	@NotNull
	protected String baseUrl;

	@Column
	@NotNull
	protected String key;

	@Column
	@NotNull
	protected String mid;

	// don't work for http://nabytok.tvojpriestor.sk/#sk/order-finished
	// @Pattern(regexp = RegexConstants.WEB_URL )
	@Column
	@NotNull
	private String url;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.seges.corpis.domain.shared.pay.HasKeyPaymentMethodSettings#getKey()
	 */
	@Override
	public String getKey() {
		return key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.seges.corpis.domain.shared.pay.HasKeyPaymentMethodSettings#setKey(
	 * java.lang.String)
	 */
	@Override
	public void setKey(String key) {
		this.key = key;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mid == null) ? 0 : mid.hashCode());
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
		HomecreditSettings other = (HomecreditSettings) obj;
		if (mid == null) {
			if (other.mid != null)
				return false;
		} else if (!mid.equals(other.mid))
			return false;
		return true;
	}

}
