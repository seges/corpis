package sk.seges.corpis.pap;

import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.appscaffold.shared.annotation.PersistenceType;
import sk.seges.corpis.appscaffold.shared.annotation.PersistentObject;
import sk.seges.corpis.pap.model.domain.data.model.MockEnum;

/**
* Created by PeterSimun on 29.12.2014.
*/
@DomainInterface
@PersistentObject(PersistenceType.JPA)
public interface MockDomainModel {
    MockEnum mock();
}
