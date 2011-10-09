/**
 * 
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import sk.seges.corpis.server.domain.customer.jpa.JpaAddress;
import sk.seges.corpis.server.domain.customer.jpa.JpaBasicContact;
import sk.seges.corpis.server.domain.customer.jpa.JpaCompanyName;
import sk.seges.corpis.server.domain.customer.jpa.JpaPersonName;
import sk.seges.corpis.shared.domain.customer.api.AddressData;
import sk.seges.corpis.shared.domain.customer.api.BasicContactData;
import sk.seges.corpis.shared.domain.customer.api.CompanyNameData;
import sk.seges.corpis.shared.domain.customer.api.PersonNameData;
import sk.seges.corpis.shared.domain.invoice.api.EOrderStatus;
import sk.seges.corpis.shared.domain.invoice.api.EPaymentType;
import sk.seges.corpis.shared.domain.invoice.api.ETransports;
import sk.seges.corpis.shared.domain.invoice.api.OrderData;

/**
 * @author eldzi
 */
@MappedSuperclass
public abstract class JpaOrderBase extends JpaAccountable implements OrderData<Long> {

	private static final long serialVersionUID = -6186188601422302822L;

	public static final String ORDER_ID = "orderId";

	private String orderId;

	private String note;

	private ETransports deliveredBy;

	private Double deliveryPrice;

	private String trackingNumber;

	private EOrderStatus status;

	private CompanyNameData company;
	
	private PersonNameData person;
	
	private AddressData address;
	
	private AddressData deliveryAddress;

	private BasicContactData contact;

	private BasicContactData deliveryContact;

	private String ico;

	private String icDph;
	
	private EPaymentType paymentType;

	private String accountNumber;

	private String projectNumber;
		
	@Column
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "Note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "delivered_by")
	public ETransports getDeliveredBy() {
		return deliveredBy;
	}

	public void setDeliveredBy(ETransports deliveredBy) {
		this.deliveredBy = deliveredBy;
	}

	@Column(name = "delivery_price")
	public Double getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(Double deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	@Column(name = "tracking_number")
	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	@Column(name = "status")
	public EOrderStatus getStatus() {
		return status;
	}

	public void setStatus(EOrderStatus status) {
		this.status = status;
	}

	@Embedded
	public JpaCompanyName getCompany() {
		return (JpaCompanyName) company;
	}

	public void setCompany(CompanyNameData company) {
		this.company = company;
	}

	@Embedded
	public JpaPersonName getPerson() {
		return (JpaPersonName) person;
	}

	public void setPerson(PersonNameData person) {
		this.person = person;
	}

	@Embedded
	public JpaAddress getAddress() {
		return (JpaAddress) address;
	}

	public void setAddress(AddressData address) {
		this.address = address;
	}

	@Embedded
	public JpaDeliveryAddress getDeliveryAddress() {
		return (JpaDeliveryAddress) deliveryAddress;
	}

	public void setDeliveryAddress(AddressData deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	@Embedded
	public JpaBasicContact getContact() {
		return (JpaBasicContact) contact;
	}

	public void setContact(BasicContactData contact) {
		this.contact = contact;
	}

	@Embedded
	@AttributeOverrides( {
			@AttributeOverride(name = "phone", column = @Column(name = JpaDeliveryAddress.TABLE_PREFIX + "phone")),
			@AttributeOverride(name = "fax", column = @Column(name = JpaDeliveryAddress.TABLE_PREFIX + "fax")),
			@AttributeOverride(name = "email", column = @Column(name = JpaDeliveryAddress.TABLE_PREFIX + "email")) })
	public JpaBasicContact getDeliveryContact() {
		return (JpaBasicContact) deliveryContact;
	}

	public void setDeliveryContact(BasicContactData deliveryContact) {
		this.deliveryContact = deliveryContact;
	}

	@Column(name = "ICO")
	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	@Column(name = "ICDPH")
	public String getIcDph() {
		return icDph;
	}

	public void setIcDph(String icDph) {
		this.icDph = icDph;
	}

	public EPaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(EPaymentType paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "ACCOUNT_NUMBER")
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Column(name = "PROJECT_NUMBER")
	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
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
		JpaOrderBase other = (JpaOrderBase) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [accountNumber=" + accountNumber + ", address=" + address + ", company=" + company
				+ ", contact=" + contact + ", deliveredBy=" + deliveredBy + ", deliveryAddress="
				+ deliveryAddress + ", deliveryContact=" + deliveryContact + ", deliveryPrice="
				+ deliveryPrice + ", icDph=" + icDph + ", ico=" + ico + ", id=" + getId() + ", note=" + note
				+ ", orderId=" + orderId + ", paymentType=" + paymentType
				+ ", person=" + person + ", projectNumber=" + projectNumber + ", status=" + status
				+ ", trackingNumber=" + trackingNumber + "]";
	}
}