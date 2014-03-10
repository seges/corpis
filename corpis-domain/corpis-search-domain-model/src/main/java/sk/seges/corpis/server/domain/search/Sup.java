package sk.seges.corpis.server.domain.search;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.shared.domain.HasWebId;
import sk.seges.corpis.shared.domain.price.api.Unit;
import sk.seges.sesam.domain.IMutableDomainObject;

@DomainInterface
@BaseObject
public interface Sup extends IMutableDomainObject<Long>, HasWebId {

	Sup parentSup();

	String names();

	String type();

	String classType();

	Unit unit();

	Boolean required();

	String extId();

}