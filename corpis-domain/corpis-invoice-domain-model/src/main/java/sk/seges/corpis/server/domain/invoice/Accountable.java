package sk.seges.corpis.server.domain.invoice;

import java.util.Date;
import java.util.List;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.server.domain.Currency;
import sk.seges.sesam.domain.IDomainObject;
import sk.seges.sesam.pap.model.annotation.ReadOnly;
import sk.seges.sesam.security.shared.domain.ISecuredObject;

@DomainInterface
@BaseObject
public interface Accountable extends IDomainObject<Long>, ISecuredObject<Long> {

	Date creationDate();
	Currency currency();
	String processId();

	@ReadOnly(ReadOnly.PropertyType.METHOD)
	List<? extends AccountableItem> fetchAccountableItems();

}