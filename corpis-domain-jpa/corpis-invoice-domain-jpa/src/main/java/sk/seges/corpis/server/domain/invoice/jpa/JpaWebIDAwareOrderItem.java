/**
 * 
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sk.seges.corpis.server.domain.invoice.server.model.data.OrderData;

/**
 * @author eldzi
 */
@Entity
@Table(name = "webid_aware_order_item")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = JpaWebIDAwareOrderItem.SEQ_ORDER_ITEMS, sequenceName = "seq_orders", initialValue = 1)
public class JpaWebIDAwareOrderItem extends JpaOrderItemBase {

	protected static final String SEQ_ORDER_ITEMS = "seqOrderItems";

	private static final long serialVersionUID = 3399448840385713282L;

	private String supValues;

	@Override
	@Id
	@GeneratedValue(generator = SEQ_ORDER_ITEMS)
	public Long getId() {
		return super.getId();
	}

	@Column
	public String getSupValues() {
		return supValues;
	}

	public void setSupValues(String supValues) {
		this.supValues = supValues;
	}
	
	@Override
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = JpaWebIDAwareOrder.class)
	@JoinColumn(name = "order_id")
	public OrderData getOrder() {
		return super.getOrder();
	}
	
}