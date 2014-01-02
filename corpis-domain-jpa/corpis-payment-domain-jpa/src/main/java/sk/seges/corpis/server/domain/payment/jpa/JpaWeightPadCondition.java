package sk.seges.corpis.server.domain.payment.jpa;

import sk.seges.corpis.server.domain.invoice.server.model.data.OrderData;
import sk.seges.corpis.server.domain.invoice.server.model.data.OrderItemData;
import sk.seges.corpis.server.domain.payment.server.model.data.WeightPadConditionData;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("2")
@Table
public class JpaWeightPadCondition extends JpaPadCondition implements WeightPadConditionData {
	
	private static final long serialVersionUID = 3611784271393425428L;
	
	public static final String WEIGHT_FROM = "weightFrom";
	public static final String WEIGHT_TO = "weightTo";	

	private Float weightFrom;
	private Float weightTo;

	@Column
	public Float getWeightTo() {
		return weightTo;
	}

	public void setWeightTo(Float weightTo) {
		this.weightTo = weightTo;
	}

	@Column
	public Float getWeightFrom() {
		return weightFrom;
	}

	public void setWeightFrom(Float weightFrom) {
		this.weightFrom = weightFrom;
	}

	@Override
	public boolean apply(OrderData order) {
		Float totalOrderWeight = 0f;
		for(OrderItemData orderItem : order.getOrderItems()){
			if(orderItem.getWeight() != null){
				totalOrderWeight += orderItem.getWeight() * orderItem.getAmount();
			}
		}
		return totalOrderWeight >= getWeightFrom() && totalOrderWeight < getWeightTo();
	}
	
}
