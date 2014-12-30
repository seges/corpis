package sk.seges.corpis.pap;

import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.appscaffold.shared.annotation.PersistenceType;
import sk.seges.corpis.appscaffold.shared.annotation.PersistentObject;

import java.util.HashMap;
import java.util.List;

/**
* Created by PeterSimun on 29.12.2014.
*/
@DomainInterface
@PersistentObject(PersistenceType.JPA)
public interface MockiestDomainModel extends MockDomainModel {
    HashMap<Long, List<? extends MockDomainModel>> mapWithWildcard();
    MockDomainModel domain();
    List<? extends MockDomainModel> wildcardTypeList();
    List<MockDomainModel> declaredTypeList();
}
