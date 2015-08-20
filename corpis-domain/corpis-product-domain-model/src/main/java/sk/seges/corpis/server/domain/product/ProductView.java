package sk.seges.corpis.server.domain.product;

import java.math.BigDecimal;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.sesam.domain.IMutableDomainObject;

/**
 * @author psloboda
 */
@DomainInterface
@BaseObject
public interface ProductView extends IMutableDomainObject<Long> {

	String extId();
	String externalId();
	String webId();
	String language();
	String name();
	BigDecimal price();
	String currency();
}
