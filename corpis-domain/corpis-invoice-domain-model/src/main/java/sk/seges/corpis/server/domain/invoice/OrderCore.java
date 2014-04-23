package sk.seges.corpis.server.domain.invoice;

import java.util.List;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.server.domain.Address;
import sk.seges.corpis.server.domain.BasicContact;
import sk.seges.corpis.server.domain.CompanyName;
import sk.seges.corpis.server.domain.PersonName;
import sk.seges.corpis.shared.domain.EPaymentType;
import sk.seges.corpis.shared.domain.invoice.ETransports;

@DomainInterface
@BaseObject
public interface OrderCore extends Accountable {

	String orderId();
	String note();
	ETransports deliveredBy();
	String trackingNumber();
	OrderStatus status();

	Boolean sameDeliveryAddress();

	String userName();

	Address customerAddress();
	PersonName customerPersonName();
	CompanyName customerCompanyName();
	BasicContact customerContact();

	DeliveryPerson deliveryPerson();
	BasicContact deliveryContact();
	Address deliveryAddress();
	Double deliveryPrice();	
	
	String ico();
	String icDph();
	String dic();
	EPaymentType paymentType();
	String accountNumber();
	String projectNumber();
	List<Invoice> invoices();
	Long customerId();
}