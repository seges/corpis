package sk.seges.corpis.server.domain.user;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.shared.domain.HasWebId;
import sk.seges.sesam.domain.IMutableDomainObject;

@DomainInterface
@BaseObject
public interface UserRole extends IMutableDomainObject<Long>, HasWebId{

	Integer priority();
	
	Role role();

    User user();

}
