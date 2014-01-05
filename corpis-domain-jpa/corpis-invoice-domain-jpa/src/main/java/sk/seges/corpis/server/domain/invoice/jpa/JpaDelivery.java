package sk.seges.corpis.server.domain.invoice.jpa;

import java.math.BigDecimal;

import javax.persistence.*;

import sk.seges.corpis.server.domain.invoice.server.model.base.DeliveryBase;
import sk.seges.corpis.server.domain.invoice.server.model.data.DeliveryData;
import sk.seges.corpis.shared.domain.invoice.ETransports;
import sk.seges.sesam.security.shared.domain.ISecuredObject;

@Entity
@Table(name = "delivery", uniqueConstraints = @UniqueConstraint(columnNames = { DeliveryData.WEB_ID,
		DeliveryData.TRANSPORT_TYPE, DeliveryData.PRICE_CONDITION,
		DeliveryData.PRICE_CONDITION_WITH_V_A_T, DeliveryData.AMOUNT_CONDITION, DeliveryData.WEIGHT_CONDITION }))
@SequenceGenerator(name = JpaDelivery.SEQ_DELIVERY, sequenceName = "seq_delivery", initialValue = 1)
public class JpaDelivery extends DeliveryBase {

	private static final long serialVersionUID = -1965331674453503605L;
	
	protected static final String SEQ_DELIVERY = "seqDeliver";

	public JpaDelivery() {
		setTransportType(ETransports.values()[0]);
		setPriceConditionWithVAT(false);
	}

	@Id
	@GeneratedValue(generator = SEQ_DELIVERY)
	public Long getId() {
		return super.getId();
	}

	@Column
	@Override
	public String getWebId() {
		return super.getWebId();
	}
	
	@Column
	@Override
	@Enumerated(EnumType.STRING)
	public ETransports getTransportType() {
		return super.getTransportType();
	}
	
	@Override
	@Column(precision = 50, scale = 20)
	public BigDecimal getPriceCondition() {
		return super.getPriceCondition();
	}

	@Column
	@Override
	public Boolean getPriceConditionWithVAT() {
		return super.getPriceConditionWithVAT();
	}

	@Column
	@Override
	public Float getAmountCondition() {
		return super.getAmountCondition();
	}

	@Column
	@Override
	public Float getWeightCondition() {
		return super.getWeightCondition();
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
}