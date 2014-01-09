package sk.seges.corpis.server.domain.product.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import sk.seges.corpis.server.domain.DBNamespace;
import sk.seges.corpis.server.domain.invoice.jpa.JpaEmbeddedName;
import sk.seges.corpis.server.domain.product.server.model.data.TagNameData;
import sk.seges.corpis.server.domain.server.model.data.NameData;

@Entity
@Table(name = DBNamespace.TABLE_PREFIX + "tag_name")
@SequenceGenerator(name = JpaTagName.SEQ_OLEA_TAG_NAMES, sequenceName = DBNamespace.TABLE_PREFIX + "seq_tag_name", initialValue = 1)
public class JpaTagName extends JpaEmbeddedName implements TagNameData {

	private static final long serialVersionUID = 5807542710037902196L;

	protected static final String SEQ_OLEA_TAG_NAMES = "seqOleaTagNames";

	private Long id;
	
	public JpaTagName() {}
	
	public JpaTagName(String language, String value) {
		setLanguage(language);
		setValue(value);
	}

	@Id
	@Override
	@GeneratedValue(generator = SEQ_OLEA_TAG_NAMES)
	public Long getId() {
		return id;
	}	
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@Column
	public String getLanguage() {
		return super.getLanguage();
	}
	
	@Override
	@Column
	public String getValue() {
		return super.getValue();
	}
	
	@Override
	@Transient
	protected NameData getInstance() {
		return new JpaTagName();
	}

	@Override
	public TagNameData clone() {
		TagNameData result = (TagNameData) super.clone();
		result.setId(getId());
		return result;
	}
}