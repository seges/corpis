/**
 * 
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import sk.seges.corpis.server.domain.invoice.server.model.data.OrderData;
import sk.seges.corpis.server.domain.invoice.server.model.data.OrderItemData;

/**
 * @author eldzi
 */
//@MappedSuperclass
public abstract class JpaOrderItemBase extends JpaAccountableItem implements OrderItemData, Comparable<JpaOrderItemBase> {

	private static final long serialVersionUID = -7389416843335701988L;
	
	private OrderData order;
	private Integer sequence;
	private Float dischargedAmount;


	public Float getDischargedAmount() {
		return dischargedAmount;
	}

	public void setDischargedAmount(Float dischargedAmount) {
		this.dischargedAmount = dischargedAmount;
	}
	
	@Override
//	@ManyToOne(fetch=FetchType.LAZY, targetEntity = JpaOrderCore.class)
//	@JoinColumn(name = "orders_id")
	public OrderData getOrder() {
		return order;
	}

	@Override
	public void setOrder(OrderData order) {
		this.order = order;
	}

	@Override
	public Integer getSequence() {
		return sequence;
	}

	@Override
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaOrderItemBase other = (JpaOrderItemBase) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderItemBase [order=" + order + ", toString()=" + super.toString() + "]";
	}

	@Override
	public int compareTo(JpaOrderItemBase o) {
		int result = this.getExtId().compareTo(o.getExtId());
		if (result == 0) {
			return this.getSequence().compareTo(o.getSequence());
		}
		return result;
	}
}