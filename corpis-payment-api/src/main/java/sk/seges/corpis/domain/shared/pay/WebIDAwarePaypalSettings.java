package sk.seges.corpis.domain.shared.pay;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import sk.seges.corpis.domain.shared.pay.paypal.PayPalSettings;

@Entity
@Table(name = "webid_aware_paypal_settings", uniqueConstraints = @UniqueConstraint(columnNames = {WebIDAwarePaypalSettings.WEB_ID}))
@SequenceGenerator(name = WebIDAwarePaymentMethodSettings.SEQ_PAYMENT_METHOD_SETTINGS, sequenceName = WebIDAwarePaymentMethodSettings.SEQ_DB_NAME_PAYMENT_METHOD_SETTINGS, initialValue = 1)
public class WebIDAwarePaypalSettings implements WebIDAwarePaymentMethodSettings {

	private static final long serialVersionUID = 8535926449582890494L;

	@Id
	@GeneratedValue(generator = SEQ_PAYMENT_METHOD_SETTINGS)
	private Long id;

	private String webId;

	@Embedded
	private PayPalSettings settings;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getWebId() {
		return webId;
	}

	@Override
	public void setWebId(String webId) {
		this.webId = webId;
	}

	@Override
	public PaymentMethodSettings getSettings() {
		return settings;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSettings(PayPalSettings settings) {
		this.settings = settings;
	}
}
