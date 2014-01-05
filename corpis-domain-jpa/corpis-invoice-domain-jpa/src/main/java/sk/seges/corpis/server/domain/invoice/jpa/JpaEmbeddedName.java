package sk.seges.corpis.server.domain.invoice.jpa;

import sk.seges.corpis.server.domain.server.model.base.NameBase;
import sk.seges.corpis.server.domain.server.model.data.NameData;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class JpaEmbeddedName extends NameBase {

	private static final long serialVersionUID = -5163495413909118691L;

	public JpaEmbeddedName() {}
	
	public JpaEmbeddedName(String language, String value) {
		setLanguage(language);
		setValue(value);
	}

	@Transient
	protected NameData getInstance() {
		return new JpaEmbeddedName();
	}

	@Override
	public NameData clone() {
		NameData newTagName = getInstance();

		newTagName.setLanguage(getLanguage());
		newTagName.setValue(getValue());

		return newTagName;
	}
}