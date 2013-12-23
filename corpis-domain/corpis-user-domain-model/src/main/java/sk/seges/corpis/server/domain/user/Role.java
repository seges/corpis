package sk.seges.corpis.server.domain.user;

import java.util.List;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.sesam.domain.IMutableDomainObject;

@DomainInterface
@BaseObject
public interface Role extends IMutableDomainObject<Integer> {

	static final String NONE = "none";
	static final String ALL_USERS = "*";
	static final String GRANT = "USER_ROLE";

	List<String> selectedAuthorities();
	String name();
	String description();
	String webId();
}