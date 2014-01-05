/**
 * 
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import sk.seges.corpis.server.domain.invoice.server.model.data.OrderCoreData;
import sk.seges.corpis.server.domain.invoice.server.model.data.OrderData;
import sk.seges.corpis.server.domain.invoice.server.model.data.OrderItemData;

/**
 * @author eldzi
 */
@MappedSuperclass
public abstract class JpaOrderItemBase extends JpaAccountableItem implements OrderItemData {

	private static final long serialVersionUID = -7389416843335701988L;
	
	private OrderData order;

	@Override
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = JpaOrder.class)
	@JoinColumn(name = "orders_id")
	public OrderData getOrder() {
		return order;
	}

	@Override
	public void setOrder(OrderData order) {
		this.order = order;
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
}