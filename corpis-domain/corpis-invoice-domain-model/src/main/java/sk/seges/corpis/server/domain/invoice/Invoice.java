package sk.seges.corpis.server.domain.invoice;

import java.util.Date;
import java.util.Set;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.server.domain.PersonCore;
import sk.seges.corpis.shared.domain.HasVersion;
import sk.seges.corpis.shared.domain.invoice.RemittanceType;
import sk.seges.corpis.shared.domain.invoice.TransportType;
import sk.seges.sesam.domain.IMutableDomainObject;

@DomainInterface
@BaseObject
public interface Invoice extends IMutableDomainObject<Long>, HasVersion {

	Date taxDate();
	Date paybackDate();//datum splatnosti
	Date creationDate();
	Integer invoiceId();
	// CustomerCore customer();
	//TODO
//	Accountable order();
	String csymbol();
	String ssymbol();
	String vsymbol();
	//TODO remove paid and uncomment paymentDate
	Boolean paid();
//	Date paymentDate();
	Boolean prepaid();
	Boolean incomingInvoiceType();
	Set<InvoiceItem> invoiceItems();
	Double pennyBalance();
	Set<Remittance> remittances();
	PersonCore creator();
	String invoiceText();
	Double finalPrice();
	RemittanceType remittanceType();
	TransportType transportType();
	Boolean addVAT();
}
