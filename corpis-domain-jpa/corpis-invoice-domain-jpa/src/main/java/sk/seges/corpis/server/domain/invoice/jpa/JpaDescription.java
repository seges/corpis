package sk.seges.corpis.server.domain.invoice.jpa;

import org.hibernate.annotations.Type;
import sk.seges.corpis.server.domain.DBNamespace;
import sk.seges.corpis.server.domain.server.model.base.DescriptionBase;
import sk.seges.corpis.server.domain.server.model.data.DescriptionData;

import javax.persistence.*;

@Entity
@Table(name = DBNamespace.TABLE_PREFIX + "description")
@SequenceGenerator(name = JpaDescription.SEQ_OLEA_DESC, sequenceName = DBNamespace.TABLE_PREFIX + "seq_desc", initialValue = 1)
public class JpaDescription extends DescriptionBase {

	private static final long serialVersionUID = -1671832806412598227L;
	
	protected static final String SEQ_OLEA_DESC = "seqOleaDesc";

	public JpaDescription() {
		super();
	}
	
	public JpaDescription(String language, String value) {
		setLanguage(language);
		setValue(value);
	}

	@Override
	@Id
	@GeneratedValue(generator = SEQ_OLEA_DESC)
	public Long getId() {
		return super.getId();
	}

	@Override
	@Type(type = "text")
//	@Lob
	@Column(name = "description")
	public String getValue() {
		return super.getValue();
	}

	@Override
	@Column
	public String getLanguage() {
		return super.getLanguage();
	}

	@Override
	public DescriptionData clone() {
		DescriptionData newDescription = new JpaDescription();

		newDescription.setLanguage(getLanguage());
		newDescription.setValue(getValue());

		return newDescription;
	}

}