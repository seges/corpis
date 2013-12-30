package sk.seges.corpis.server.domain;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.sesam.pap.model.annotation.ReadOnly;

@DomainInterface
@BaseObject
public interface Name {

	String language();
	String value();

	@ReadOnly(ReadOnly.PropertyType.METHOD)
	Name clone();

}