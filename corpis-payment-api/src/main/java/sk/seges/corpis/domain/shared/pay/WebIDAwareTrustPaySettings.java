/**
 * 
 */
package sk.seges.corpis.domain.shared.pay;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import sk.seges.corpis.domain.shared.pay.trust.TrustPaySettings;

/**
 * @author stefan.sivak
 *
 * @since Dec 4, 2013
 */
@Entity
@Table(name = "webid_aware_trustpay_settings", uniqueConstraints = @UniqueConstraint(columnNames = {
		WebIDAwareTrustPaySettings.WEB_ID, TrustPaySettings.MID_ATTRIBUTE }))
@SequenceGenerator(name = WebIDAwarePaymentMethodSettings.SEQ_PAYMENT_METHOD_SETTINGS, sequenceName = WebIDAwarePaymentMethodSettings.SEQ_DB_NAME_PAYMENT_METHOD_SETTINGS, initialValue = 1)
public class WebIDAwareTrustPaySettings implements WebIDAwarePaymentMethodSettings {

	private static final long serialVersionUID = -4940844671386434478L;

	@Id
	@GeneratedValue(generator = SEQ_PAYMENT_METHOD_SETTINGS)
	private Long id;

	private String webId;

	@Embedded
	private TrustPaySettings settings;

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.seges.sesam.domain.IDomainObject#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.seges.corpis.domain.shared.pay.WebIDAwarePaymentMethodSettings#getWebId
	 * ()
	 */
	@Override
	public String getWebId() {
		return webId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.seges.corpis.domain.shared.pay.WebIDAwarePaymentMethodSettings#setWebId
	 * (java.lang.String)
	 */
	@Override
	public void setWebId(String webId) {
		this.webId = webId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.seges.corpis.domain.shared.pay.WebIDAwarePaymentMethodSettings#getSettings
	 * ()
	 */
	@Override
	public PaymentMethodSettings getSettings() {
		return settings;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSettings(TrustPaySettings settings) {
		this.settings = settings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((settings == null) ? 0 : settings.hashCode());
		result = prime * result + ((webId == null) ? 0 : webId.hashCode());
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
		WebIDAwareTrustPaySettings other = (WebIDAwareTrustPaySettings) obj;
		if (settings == null) {
			if (other.settings != null)
				return false;
		} else if (!settings.equals(other.settings))
			return false;
		if (webId == null) {
			if (other.webId != null)
				return false;
		} else if (!webId.equals(other.webId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WebIDAwareTrustPaySettings [id=" + id + ", webId=" + webId + ", settings=" + settings + "]";
	}

}
