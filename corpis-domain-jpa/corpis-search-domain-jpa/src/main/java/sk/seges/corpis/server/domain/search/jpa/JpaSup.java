package sk.seges.corpis.server.domain.search.jpa;

import javax.persistence.*;

import sk.seges.corpis.server.domain.search.server.model.base.SupBase;
import sk.seges.corpis.server.domain.search.server.model.data.SupData;
import sk.seges.corpis.shared.domain.price.api.Unit;

@Entity
@Table(name = "sup")
@SequenceGenerator(name = JpaSup.SEQ_SUP, sequenceName=JpaSup.SEQ_SUP, initialValue=1)
public class JpaSup extends SupBase implements SupData {
	private static final long serialVersionUID = -8093324696842872725L;

	protected static final String SEQ_SUP = "seq_sups";

	@Override
	@Id
	@GeneratedValue(generator = SEQ_SUP)
	public Long getId() {
		return super.getId();
	}

	/**
	 * localized names: {"locale":"localized name","locale_2":"localized_name"}
	 */
	@Override
	@Column
	public String getNames() {
		return super.getNames();
	}

	@Override
	@Column(nullable = false)
	public String getWebId() {
		return super.getWebId();
	}

	@Override
	@ManyToOne(targetEntity = JpaSup.class)
	public JpaSup getParentSup() {
		return (JpaSup) super.getParentSup();
	}

	@Override
	@Column
	public String getType() {
		return super.getType();
	}

	@Override
	@Column
	public String getClassType() {
		return super.getClassType();
	}

	@Override
	@Column
	@Enumerated(EnumType.STRING)
	public Unit getUnit() {
		return super.getUnit();
	}

	@Override
	@Column
	public Boolean getRequired() {
		return super.getRequired();
	}

	@Override
	@Column
	public String getExtId() {
		return super.getExtId();
	}
}
