package sk.seges.corpis.server.domain.invoice.jpa;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import sk.seges.corpis.server.domain.invoice.server.model.data.AccountableItemData;
import sk.seges.corpis.server.domain.invoice.server.model.data.OrderData;
import sk.seges.corpis.server.domain.invoice.server.model.data.OrderItemData;
import sk.seges.sesam.security.shared.domain.ISecuredObject;

@Entity
@SequenceGenerator(name = JpaOrder.SEQ_ORDERS, sequenceName = "seq_orders", initialValue = 1)
//@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "orders", uniqueConstraints = {@UniqueConstraint(columnNames = JpaOrderCore.ORDER_ID)})
public class JpaOrder extends JpaOrderCore implements OrderData {
	
	private static final long serialVersionUID = -3117593133828636987L;

	protected static final String SEQ_ORDERS = "seqOrders";
	
	private Long id;

	private List<OrderItemData> orderItems;

	@Override
	@Id
	@GeneratedValue(generator = SEQ_ORDERS)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = JpaOrderItem.class, mappedBy = OrderItemData.ORDER)
	public List<OrderItemData> getOrderItems() {
		return orderItems;
	}

	@Override
	public void setOrderItems(List<? extends OrderItemData> orderItems) {
		this.orderItems = (List<OrderItemData>) orderItems;
	}

	@Override
	public List<? extends AccountableItemData> fetchAccountableItems() {
		return orderItems;
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
