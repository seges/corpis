package sk.seges.corpis.server.domain.payment;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.server.domain.payment.api.IPadCondition;
import sk.seges.corpis.server.domain.payment.server.model.data.PadData;
import sk.seges.sesam.domain.IMutableDomainObject;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@DomainInterface
@BaseObject
public interface PadCondition extends IPadCondition, IMutableDomainObject<Long> {

	Pad pad();

}
