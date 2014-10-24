package sk.seges.corpis.server.domain.invoice;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.server.domain.HasPrice;
import sk.seges.corpis.server.domain.Vat;
import sk.seges.corpis.shared.domain.HasDescription;
import sk.seges.corpis.shared.domain.HasVersion;
import sk.seges.corpis.shared.domain.price.api.ProductUnit;
import sk.seges.sesam.domain.IMutableDomainObject;
import sk.seges.sesam.pap.model.annotation.ReadOnly;

@DomainInterface
@BaseObject
public interface InvoiceItem extends IMutableDomainObject<Long>, HasVersion, HasDescription, HasPrice {

	Invoice invoice();
	Float count();
	ProductUnit unit();
	Vat vat();
	
//	AccountableItem orderItem();

	@ReadOnly(ReadOnly.PropertyType.METHOD)
	InvoiceItem copy(InvoiceItem copy);

}