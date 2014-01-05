/**
 * 
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import sk.seges.corpis.server.domain.customer.jpa.JpaAddress;
import sk.seges.corpis.server.domain.customer.jpa.JpaBasicContact;
import sk.seges.corpis.server.domain.customer.jpa.JpaCompanyName;
import sk.seges.corpis.server.domain.customer.jpa.JpaCustomerCore;
import sk.seges.corpis.server.domain.invoice.server.model.base.OrderCoreBase;
import sk.seges.corpis.server.domain.invoice.server.model.data.DeliveryPersonData;
import sk.seges.corpis.server.domain.invoice.server.model.data.OrderCoreData;
import sk.seges.corpis.server.domain.invoice.server.model.data.OrderStatusData;
import sk.seges.corpis.server.domain.jpa.JpaCurrency;
import sk.seges.corpis.server.domain.jpa.JpaPersonName;
import sk.seges.corpis.server.domain.server.model.data.AddressData;
import sk.seges.corpis.server.domain.server.model.data.BasicContactData;
import sk.seges.corpis.shared.domain.EPaymentType;
import sk.seges.corpis.shared.domain.invoice.ETransports;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author eldzi
 */
@MappedSuperclass
public abstract class JpaOrderCore extends OrderCoreBase implements OrderCoreData {

	private static final long serialVersionUID = -6186188601422302822L;

	public JpaOrderCore() {
		setDeliveryContact(new JpaBasicContact());
		setDeliveryPerson(new JpaDeliveryPerson());
		setDeliveryAddress(new JpaAddress());

		setCustomerAddress(new JpaAddress());
		setCustomerContact(new JpaBasicContact());
		setCustomerPersonName(new JpaPersonName());
		setCustomerCompanyName(new JpaCompanyName());
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public JpaCustomerCore getCustomer() {
		return (JpaCustomerCore) super.getCustomer();
	}

	@Column(name = "creation_date")
	public Date getCreationDate() {
		return super.getCreationDate();
	}

	@ManyToOne
	public JpaCurrency getCurrency() {
		return (JpaCurrency) super.getCurrency();
	}

	@Column
	public String getOrderId() {
		return super.getOrderId();
	}

	@Column(name = "note")
	public String getNote() {
		return super.getNote();
	}

	@Column(name = "delivered_by")
	public ETransports getDeliveredBy() {
		return super.getDeliveredBy();
	}

	@Deprecated
	@Column(name = "delivery_price")
	public Double getDeliveryPrice() {
		return super.getDeliveryPrice();
	}

	@Column(name = "tracking_number")
	public String getTrackingNumber() {
		return super.getTrackingNumber();
	}

	@ManyToOne(targetEntity = JpaOrderStatus.class)
	public OrderStatusData getStatus() {
		return super.getStatus();
	}

	@Column(name = "ico")
	public String getIco() {
		return super.getIco();
	}

	@Column(name = "icdph")
	public String getIcDph() {
		return super.getIcDph();
	}

	@Override
	@Column(name="dic")
	public String getDic() {
		return super.getDic();
	}

	@Column
	public EPaymentType getPaymentType() {
		return super.getPaymentType();
	}

	@Column(name = "account_number")
	public String getAccountNumber() {
		return super.getAccountNumber();
	}

	@Column(name = "project_number")
	public String getProjectNumber() {
		return super.getProjectNumber();
	}

	@Column(name = "same_delivery_address")
	public Boolean getSameDeliveryAddress() {
		return super.getSameDeliveryAddress();
	}

	@Column
	@Override
	public String getUserName() {
		return super.getUserName();
	}
}