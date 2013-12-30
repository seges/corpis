/**
 * 
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import javax.persistence.*;

import sk.seges.corpis.server.domain.invoice.server.model.data.OrderData;
import sk.seges.sesam.domain.IDomainObject;

/**
 * @author eldzi
 */
@Entity
@Table(name = "webid_aware_order_item")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = JpaWebIDAwareOrderItem.SEQ_ORDER_ITEMS, sequenceName = "seq_orders", initialValue = 1)
public class JpaWebIDAwareOrderItem extends JpaOrderItem implements IDomainObject<Long> {

	private static final long serialVersionUID = 3399448840385713282L;

	private String supValues;

	@Override
	@Id
	@GeneratedValue(generator = SEQ_ORDER_ITEMS)
	public Long getId() {
		return super.getId();
	}

	@Override
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = JpaWebIDAwareOrder.class)
	@JoinColumn(name = "orders_id")
	public OrderData getOrder() {
		return super.getOrder();
	}

	@Column
	public String getSupValues() {
		return supValues;
	}

	public void setSupValues(String supValues) {
		this.supValues = supValues;
	}
}