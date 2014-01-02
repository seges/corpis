package sk.seges.corpis.server.domain.payment.jpa;

import sk.seges.corpis.server.domain.payment.server.model.base.PadConditionBase;
import sk.seges.corpis.server.domain.payment.server.model.data.PadData;

import javax.persistence.*;

@Entity
@Table(name = "pad_conditions")
@SequenceGenerator(name = "seqPadCondition", sequenceName = "SEQ_PAD_CONDITION", initialValue = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CONDITION_TYPE", discriminatorType = DiscriminatorType.INTEGER)
public abstract class JpaPadCondition extends PadConditionBase {

	private static final long serialVersionUID = 8032103273910293766L;

	protected static final String SEQ_PAD_CONDITION = "seqPadCondition";
	public static final String PAD_JOIN_COLUMN = "pad_id";

	@Override
	@Id
	@GeneratedValue(generator = SEQ_PAD_CONDITION)
	public Long getId() {
		return super.getId();
	}

	@ManyToOne(targetEntity = JpaPad.class)
	@JoinColumn(name = PAD_JOIN_COLUMN)
	public PadData getPad() {
		return super.getPad();
	}
}
