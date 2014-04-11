package sk.seges.corpis.server.domain.invoice.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import sk.seges.corpis.server.domain.DBNamespace;
import sk.seges.corpis.server.domain.server.model.data.NameData;

@Entity
@Table(name = DBNamespace.TABLE_PREFIX + "name")
@SequenceGenerator(name = JpaName.SEQ_OLEA_NAMES, sequenceName = DBNamespace.TABLE_PREFIX + "seq_name", initialValue = 1)
public class JpaName extends JpaEmbeddedName implements NameData {

	private static final long serialVersionUID = -5163495413909118691L;

	protected static final String SEQ_OLEA_NAMES = "seqOleaNames";
	
	private Long id;
	
	public JpaName() {}
	
	public JpaName(String language, String value) {
		setLanguage(language);
		setValue(value);
	}
	
	@Id
	@GeneratedValue(generator = SEQ_OLEA_NAMES)
	public Long getId() {
		return id;
	}

	@Column
	@Override
	public String getLanguage() {
		return super.getLanguage();
	}

	@Override
	@Column
	public String getValue() {
		return super.getValue();
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@Transient
	protected NameData getInstance() {
		return new JpaName();
	}
}