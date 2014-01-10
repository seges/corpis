package sk.seges.corpis.server.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.sesam.pap.model.annotation.ReadOnly;

@DomainInterface
@BaseObject
public interface Price extends Serializable {

	BigDecimal value();
	Currency currency();

	@ReadOnly(ReadOnly.PropertyType.METHOD)
	Price clone();
}