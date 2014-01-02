/**
 * 
 */
package sk.seges.corpis.server.domain.customer.jpa;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import sk.seges.corpis.server.domain.DBConstraints;
import sk.seges.corpis.server.domain.customer.server.model.base.CustomerCoreBase;
import sk.seges.corpis.server.domain.customer.server.model.data.CustomerCoreData;
import sk.seges.corpis.server.domain.customer.server.model.data.CustomerData;
import sk.seges.corpis.server.domain.jpa.JpaPersonName;
import sk.seges.corpis.server.domain.server.model.data.AddressData;
import sk.seges.corpis.shared.domain.validation.customer.CompanyCustomerFormCheck;

/**
 * @author ladislav.gazo
 */
@Entity
@SequenceGenerator(name = "seqCustomers", sequenceName = "seq_customers", initialValue = 1)
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue(value = "1")
@AssociationOverride(name = CustomerCoreData.CORRESP_ADDRESS + "." + AddressData.COUNTRY, joinColumns = @JoinColumn(name = DBConstraints.CORRESP_TABLE_PREFIX
			+ AddressData.COUNTRY + "_id"))
public class JpaCustomerCore extends CustomerCoreBase {
	private static final long serialVersionUID = -7070969291047080272L;

	public static final String TABLE_PREFIX = "customer_";

	public JpaCustomerCore() {
		setCompany(new JpaCompanyName());
		setPerson(new JpaPersonName());
		setAddress(new JpaAddress());
		setContact(new JpaBasicContact());
	}

	@Id
	@GeneratedValue(generator = "seqCustomers")
	public Long getId() {
		return super.getId();
	}

	@Column(length = DBConstraints.SHORTCUT_LENGTH)
	public String getShortcut() {
		return super.getShortcut();
	}

	@Column(name = "dic")
	public String getDic() {
		return super.getDic();
	}

	@Column(name = "ic_dph")
	public String getIcDph() {
		return super.getIcDph();
	}

	@Column(name = "ico")
	@NotNull(groups = CompanyCustomerFormCheck.class)
	@Size(min = 1, groups = CompanyCustomerFormCheck.class)
	public String getIco() {
		return super.getIco();
	}

	@Transient
	public String getName() {
		return (!isCompanyCustomerType()) ? (((getPerson().getFirstName() == null) ? "" : getPerson()
				.getFirstName() + " ") + ((getPerson().getSurname() == null) ? "" : getPerson().getSurname()))
				: getCompany().getCompanyName();
	}

	@Column(name = "invoice_payment_interval")
	public Short getInvoicePaymentInterval() {
		return super.getInvoicePaymentInterval();
	}

	@Column(name = "account_number")
	public String getAccountNumber() {
		return super.getAccountNumber();
	}

	@Column(name = "tax_payment")
	public Boolean getTaxPayment() {
		return super.getTaxPayment();
	}

	@Override
	@Column
	public Boolean getCompanyType() {
		return super.getCompanyType();
	}

	@Version
	public Integer getVersion() {
		return super.getVersion();
	}

	@Valid
	@Embedded
	@AttributeOverride(name = JpaCompanyName.COMPANY_NAME, column = @Column(unique = true, nullable = true))
	public JpaCompanyName getCompany() {
		return (JpaCompanyName) super.getCompany();
	}

	@Embedded
	@Valid
	public JpaPersonName getPerson() {
		return (JpaPersonName) super.getPerson();
	}

	@Valid
	@Embedded
	public JpaAddress getAddress() {
		return (JpaAddress) super.getAddress();
	}

	@Embedded
	@Valid
	public JpaBasicContact getContact() {
		return (JpaBasicContact) super.getContact();
	}

	@Column
	public Date getRegistrationDate() {
		return super.getRegistrationDate();
	}

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = JpaCompanyName.COMPANY_NAME, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX
					+ JpaCompanyName.COMPANY_NAME)),
			@AttributeOverride(name = JpaCompanyName.DEPARTMENT, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX
					+ JpaCompanyName.DEPARTMENT)) })
	public JpaCompanyName getCorrespCompany() {
		return (JpaCompanyName) super.getCorrespCompany();
	}

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = JpaPersonName.SALUTATION, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX
					+ JpaPersonName.SALUTATION)),
			@AttributeOverride(name = JpaPersonName.FIRST_NAME, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX
					+ JpaPersonName.FIRST_NAME)),
			@AttributeOverride(name = JpaPersonName.SURNAME, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX
					+ JpaPersonName.SURNAME)) })
	public JpaPersonName getCorrespPerson() {
		return (JpaPersonName) super.getCorrespPerson();
	}

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = JpaAddress.STREET, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX + JpaAddress.STREET)),
			@AttributeOverride(name = JpaAddress.CITY, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX + JpaAddress.CITY)),
			@AttributeOverride(name = JpaAddress.STATE, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX + JpaAddress.STATE)),
			@AttributeOverride(name = JpaAddress.ZIP, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX + JpaAddress.ZIP, length = DBConstraints.ZIP_LENGTH)),
			@AttributeOverride(name = JpaAddress.COUNTRY, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX + JpaAddress.COUNTRY))})
	@AssociationOverride(name = JpaAddress.COUNTRY, joinColumns = @JoinColumn(name = DBConstraints.CORRESP_TABLE_PREFIX
			+ JpaAddress.COUNTRY + "_id"))
	public JpaAddress getCorrespAddress() {
		return (JpaAddress) super.getCorrespAddress();
	}

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = JpaBasicContact.PHONE, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX
					+ JpaBasicContact.PHONE)),
			@AttributeOverride(name = JpaBasicContact.FAX, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX + JpaBasicContact.FAX)),
			@AttributeOverride(name = JpaBasicContact.EMAIL, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX
					+ JpaBasicContact.EMAIL)),
			@AttributeOverride(name = JpaBasicContact.MOBILE, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX
					+ JpaBasicContact.MOBILE)),
			@AttributeOverride(name = JpaBasicContact.WEB, column = @Column(name = DBConstraints.CORRESP_TABLE_PREFIX + JpaBasicContact.WEB)) })
	public JpaBasicContact getCorrespContact() {
		return (JpaBasicContact) super.getCorrespContact();
	}

	@Column
	public Boolean getCommision() {
		return super.getCommision();
	}

	@Transient
	@Deprecated
	public boolean isCompanyCustomerType() {
		return (getCompany() != null && getCompany().getCompanyName() != null && getCompany().getCompanyName().length() > 0);
	}
}