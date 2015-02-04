package sk.seges.corpis.server.domain.search.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sk.seges.corpis.server.domain.search.server.model.base.SupBase;
import sk.seges.corpis.server.domain.search.server.model.data.SupData;
import sk.seges.corpis.shared.domain.price.api.Unit;

@Entity
@Table(name = "sup")
@SequenceGenerator(name = JpaSup.SEQ_SUP, sequenceName=JpaSup.SEQ_SUP, initialValue=1)
public class JpaSup extends SupBase implements SupData {
	private static final long serialVersionUID = -8093324696842872725L;

	protected static final String SEQ_SUP = "seq_sups";

	public JpaSup() {}

	public JpaSup(String classType, String extId, Long id, String names, SupData parentSup, Boolean required, String type, Unit unit, String webId) {
		setClassType(classType);
		setExtId(extId);
		setId(id);
		setNames(names);
		setParentSup(parentSup);
		setRequired(required);
		setType(type);
		setUnit(unit);
		setWebId(webId);
	}

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
		result = prime * result + ((getWebId() == null) ? 0 : getWebId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupBase other = (SupBase) obj;
		if (getType() == null) {
			if (other.getType() != null)
				return false;
		} else if (!getType().equals(other.getType()))
			return false;
		if (getWebId() == null) {
			if (other.getWebId() != null)
				return false;
		} else if (!getWebId().equals(other.getWebId()))
			return false;
		return true;
	}
}
