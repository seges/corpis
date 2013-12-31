package sk.seges.corpis.server.domain.invoice;

import java.math.BigDecimal;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.shared.domain.invoice.ETransports;
import sk.seges.sesam.domain.IMutableDomainObject;
import sk.seges.sesam.security.shared.domain.ISecuredObject;

@DomainInterface
@BaseObject
public interface Delivery extends IMutableDomainObject<Long>, ISecuredObject<Long> {

	String webId();

	ETransports transportType();

	BigDecimal priceCondition();

	Boolean priceConditionWithVAT();

	Float amountCondition();

	Float weightCondition();
}