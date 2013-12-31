package sk.seges.corpis.server.domain.jpa;

import javax.persistence.*;

import sk.seges.corpis.server.domain.server.model.base.PersonCoreBase;

@Entity
@Table(name="person")
@SequenceGenerator(name = "seqPersons", sequenceName = "SEQ_PERSONS", initialValue = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue(value = "1")
public class JpaPersonCore extends PersonCoreBase {

	private static final long serialVersionUID = -3023607067512088213L;

	public JpaPersonCore() {
		setPerson(new JpaPersonName());
	}

	@Id
	@GeneratedValue(generator = "seqPersons")//$NON-NLS-1$
	public Long getId() {
		return super.getId();
	}

	@Embedded
	public JpaPersonName getPerson() {
		return (JpaPersonName) super.getPerson();
	}

	/**
	 * Use {@link sk.seges.corpis.server.domain.server.model.data.PersonNameData}
	 * @return
	 */
	@Transient
	@Deprecated
	public String getFirstName() {
		String result = null;
		if(null != getPerson()) {
			result = getPerson().getFirstName();
		}
		return result;
	}

	/**
	 * Use {@link sk.seges.corpis.server.domain.server.model.data.PersonNameData}
	 * @return
	 */
	@Deprecated
	public void setFirstName(String firstName) {
		if(null != getPerson()) {
			getPerson().setFirstName(firstName);
		}
	}

	/**
	 * Use {@link sk.seges.corpis.server.domain.server.model.data.PersonNameData}
	 * @return
	 */
	@Transient
	@Deprecated
	public String getLastName() {
		String result = null;
		if(null != getPerson()) {
			result = getPerson().getSurname();
		}
		return result;
	}

	/**
	 * Use {@link sk.seges.corpis.server.domain.server.model.data.PersonNameData}
	 * @return
	 */
	@Deprecated
	public void setLastName(String lastName) {
		if (null != getPerson()) {
			getPerson().setSurname(lastName);
		}
	}

	@Transient
	public String getDisplayName() {
		if (getPerson() == null) {
			return null;
		}
		return getPerson().toString();
	}

}