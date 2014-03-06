package sk.seges.corpis.server.domain.payment.jpa;

import javax.persistence.*;

import sk.seges.corpis.server.domain.payment.server.model.base.PaymentMethodBase;
import sk.seges.corpis.server.domain.payment.server.model.data.PaymentMethodData;
import sk.seges.corpis.shared.domain.EPaymentType;
import sk.seges.sesam.security.shared.domain.ISecuredObject;

@Entity
@Table(name = "payment_method", uniqueConstraints = @UniqueConstraint(columnNames = { PaymentMethodData.WEB_ID,
		PaymentMethodData.PAYMENT_TYPE }))
@SequenceGenerator(name = JpaPaymentMethod.SEQ_PAYMENT, sequenceName = "seq_payment_method", initialValue = 1)
public class JpaPaymentMethod extends PaymentMethodBase {

	private static final long serialVersionUID = 287108762974445073L;

	protected static final String SEQ_PAYMENT = "seqPaymentMethod";

	public JpaPaymentMethod() {
		setPaymentType(EPaymentType.values()[0]);
	}

	@Override
	@Id
	@GeneratedValue(generator = SEQ_PAYMENT)
	public Long getId() {
		return super.getId();
	}

	@Override
	@Column
	public String getWebId() {
		return super.getWebId();
	}

	@Override
	@Enumerated(EnumType.STRING)
	public EPaymentType getPaymentType() {
		return super.getPaymentType();
	}

	@Override
	@Transient
	public Long getIdForACL() {
		return getId();
	}

	@Transient
	@Override
	public ISecuredObject<?> getParent() {
		return null;
	}

	@Override
	@Transient
	public Class<?> getSecuredClass() {
		return getClass();
	}
}