package sk.seges.corpis.server.domain.invoice.jpa;

import java.util.List;

import javax.persistence.*;

import sk.seges.corpis.server.domain.invoice.server.model.base.OrderStatusBase;
import sk.seges.corpis.server.domain.server.model.data.NameData;
import sk.seges.corpis.shared.domain.invoice.EOrderStatus;

@Entity
@SequenceGenerator(name = JpaOrderStatus.SEQ_ORDER_STATUSES, sequenceName = "seq_order_statuses", initialValue = 1)
@Table(name = "order_statuses"/*, uniqueConstraints = @UniqueConstraint(columnNames = { "webid", "index" })*/)
public class JpaOrderStatus extends OrderStatusBase {

	private static final long serialVersionUID = 3777383895224806354L;
	
	protected static final String SEQ_ORDER_STATUSES = "seqOrderStatuses";

	@Override
	@Column
	public Integer getIndex() {
		return super.getIndex();
	}

	@Override
	@Enumerated(EnumType.STRING)
	public EOrderStatus getType() {
		return super.getType();
	}
	
	@Override
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, targetEntity = JpaName.class, fetch = FetchType.EAGER)
	public List<NameData> getNames() {
		return super.getNames();
	}

	@Override
	@Column
	public String getWebId() {
		return super.getWebId();
	}

	@Override
	@Id
	@GeneratedValue(generator = SEQ_ORDER_STATUSES)
	public Long getId() {
		return super.getId();
	}
	
}
