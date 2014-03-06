package sk.seges.corpis.server.domain.payment.jpa;

import javax.persistence.*;

import sk.seges.corpis.server.domain.invoice.jpa.JpaDelivery;
import sk.seges.corpis.server.domain.invoice.jpa.JpaVat;
import sk.seges.corpis.server.domain.invoice.server.model.data.DeliveryData;
import sk.seges.corpis.server.domain.jpa.JpaCountry;
import sk.seges.corpis.server.domain.jpa.JpaPrice;
import sk.seges.corpis.server.domain.payment.server.model.base.PadBase;
import sk.seges.corpis.server.domain.payment.server.model.data.PaymentMethodData;
import sk.seges.corpis.server.domain.server.model.data.CountryData;
import sk.seges.corpis.server.domain.server.model.data.VatData;
import sk.seges.sesam.security.shared.domain.ISecuredObject;

@Entity
@Table (name = "pads")
@SequenceGenerator(name = JpaPad.SEQ_PADS, sequenceName = "seq_pads", initialValue = 1)
public class JpaPad extends PadBase {

	private static final long serialVersionUID = 112L;

	protected static final String SEQ_PADS = "seqPads";

	protected static final String COUNTRY_JOIN = "country_id";
	protected static final String DELIVERY_JOIN = "delivery_id";
	protected static final String PAYMENT_METOD_JOIN = "paymentMethod_id";

	@Override
	@Id
	@GeneratedValue(generator = SEQ_PADS)
	public Long getId() {
		return super.getId();
	}
	
	@Override
	@Column
	public String getWebId() {
		return super.getWebId();
	}
	
	@Override
	@Column
	public String getLocale() {
		return super.getLocale();
	}

	@Override
	@ManyToOne(targetEntity = JpaPaymentMethod.class)
	@JoinColumn(name = PAYMENT_METOD_JOIN)
	public PaymentMethodData getPaymentMethod() {
		return super.getPaymentMethod();
	}
	
	@Override
	@ManyToOne(targetEntity = JpaDelivery.class)
	@JoinColumn(name = DELIVERY_JOIN)
	public DeliveryData getDelivery() {
		return super.getDelivery();
	}

	@Override
	@Embedded
    @AttributeOverride(name = JpaPrice.VALUE, column=@Column(nullable = true))
	public JpaPrice getPrice() {
		return (JpaPrice) super.getPrice();
	}

	@Override
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE }, targetEntity = JpaVat.class)
	public VatData getVat() {
		return super.getVat();
	}

	@Override
	@ManyToOne(targetEntity = JpaCountry.class)
	@JoinColumn(name = COUNTRY_JOIN)
	public CountryData getCountry() {
		return super.getCountry();
	}

	@Override
	@Transient
	public Long getIdForACL() {
		return getId();
	}

	@Override
	@Transient
	public ISecuredObject<?> getParent() {
		return null;
	}

	@Override
	@Transient
	public Class<?> getSecuredClass() {
		return getClass();
	}
}