package sk.seges.corpis.server.domain.payment;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.shared.domain.EPaymentType;
import sk.seges.sesam.domain.IMutableDomainObject;
import sk.seges.sesam.security.shared.domain.ISecuredObject;

@DomainInterface
@BaseObject
public interface PaymentMethod extends IMutableDomainObject<Long>, ISecuredObject<Long> {

	String webId();

	EPaymentType paymentType();
}