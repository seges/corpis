package sk.seges.corpis.server.domain.user;

import java.util.List;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.shared.domain.HasDescription;
import sk.seges.corpis.shared.domain.HasEmail;
import sk.seges.corpis.shared.domain.HasName;
import sk.seges.corpis.shared.domain.HasWebId;
import sk.seges.sesam.domain.IMutableDomainObject;

@DomainInterface
@BaseObject
public interface User extends IMutableDomainObject<Long>, HasName, HasDescription, HasWebId, HasEmail {

	List<String> userAuthorities();

	String username();

	String password();

	boolean enabled();

	List<Role> roles();

	String surname();

	String contact();

	UserPreferences userPreferences();
	
	List<UserRole> userRoles();
}