/**
 * 
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import sk.seges.corpis.server.domain.customer.jpa.JpaAddress;
import sk.seges.corpis.server.domain.customer.jpa.JpaBasicContact;
import sk.seges.corpis.server.domain.customer.jpa.JpaCompanyName;
import sk.seges.corpis.server.domain.invoice.server.model.base.OrderCoreBase;
import sk.seges.corpis.server.domain.invoice.server.model.data.OrderCoreData;
import sk.seges.corpis.server.domain.invoice.server.model.data.OrderStatusData;
import sk.seges.corpis.server.domain.jpa.JpaCurrency;
import sk.seges.corpis.server.domain.jpa.JpaPersonName;
import sk.seges.corpis.shared.domain.EPaymentType;
import sk.seges.corpis.shared.domain.invoice.ETransports;

/**
 * @author eldzi
 */
@MappedSuperclass
public abstract class JpaOrderCore extends OrderCoreBase implements OrderCoreData {

	private static final long serialVersionUID = -6186188601422302822L;
	public static final String DELIVERY_CONTACT = "deliveryContact";
	public static final String DELIVERY_PERSON = "deliveryPerson";
	public static final String DELIVERY_ADDRESS = "deliveryAddress";
	public static final String CUSTOMER_ADDRESS = "customerAddress";
	public static final String CUSTOMER_CONTACT = "customerContact";
	public static final String CUSTOMER_PERSON_NAME = "customerPersonName";
	public static final String CUSTOMER_COMPANY_NAME = "customerCompanyName";
	
	public static final String CUSTOMER_ID_COLUMN_NAME = "customer_id";

	public JpaOrderCore() {
		setDeliveryContact(new JpaBasicContact());
		setDeliveryPerson(new JpaDeliveryPerson());
		setDeliveryAddress(new JpaAddress());

		setCustomerAddress(new JpaAddress());
		setCustomerContact(new JpaBasicContact());
		setCustomerPersonName(new JpaPersonName());
		setCustomerCompanyName(new JpaCompanyName());
	}

	@Override
	@Column(name = "creation_date")
	public Date getCreationDate() {
		return super.getCreationDate();
	}

	@Override
	@ManyToOne
	public JpaCurrency getCurrency() {
		return (JpaCurrency) super.getCurrency();
	}

	@Override
	@Column
	public String getOrderId() {
		return super.getOrderId();
	}

	@Override
	@Column(name = "note")
	public String getNote() {
		return super.getNote();
	}

	@Override
	@Column(name = "delivered_by")
	public ETransports getDeliveredBy() {
		return super.getDeliveredBy();
	}

	@Override
	@Deprecated
	@Column(name = "delivery_price")
	public Double getDeliveryPrice() {
		return super.getDeliveryPrice();
	}

	@Override
	@Column(name = "tracking_number")
	public String getTrackingNumber() {
		return super.getTrackingNumber();
	}

	@Override
	@ManyToOne(targetEntity = JpaOrderStatus.class)
	public OrderStatusData getStatus() {
		return super.getStatus();
	}

	@Override
	@Column(name = "ico")
	public String getIco() {
		return super.getIco();
	}

	@Override
	@Column(name = "icdph")
	public String getIcDph() {
		return super.getIcDph();
	}

	@Override
	@Column(name="dic")
	public String getDic() {
		return super.getDic();
	}

	@Override
	@Column
	public EPaymentType getPaymentType() {
		return super.getPaymentType();
	}

	@Override
	@Column(name = "account_number")
	public String getAccountNumber() {
		return super.getAccountNumber();
	}

	@Override
	@Column(name = "project_number")
	public String getProjectNumber() {
		return super.getProjectNumber();
	}

	@Override
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