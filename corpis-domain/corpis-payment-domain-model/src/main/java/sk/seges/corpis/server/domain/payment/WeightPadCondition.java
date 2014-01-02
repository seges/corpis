package sk.seges.corpis.server.domain.payment;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.server.domain.payment.api.IPadCondition;

@DomainInterface
@BaseObject
public interface WeightPadCondition extends PadCondition {
	
	Float weightFrom();
	Float weightTo();

}
