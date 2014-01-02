package sk.seges.corpis.server.domain.product;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.server.domain.customer.CustomerCore;
import sk.seges.corpis.server.domain.price.api.IPriceCondition;
import sk.seges.sesam.domain.IMutableDomainObject;

@DomainInterface
@BaseObject
public interface PriceCondition extends IMutableDomainObject<Long>, IPriceCondition  {

	Double value();
	
	String webId();
	
	CustomerCore customer();
	
	Product product();
	
}
