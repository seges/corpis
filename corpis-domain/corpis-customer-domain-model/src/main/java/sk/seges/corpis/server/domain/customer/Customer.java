package sk.seges.corpis.server.domain.customer;

import java.util.List;
import java.util.Set;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.server.domain.invoice.Order;
import sk.seges.corpis.server.domain.user.User;

@DomainInterface
@BaseObject
public interface Customer extends CustomerCore {

	String webId();

	Set<User> userAccounts();

	List<Order> orders();
}