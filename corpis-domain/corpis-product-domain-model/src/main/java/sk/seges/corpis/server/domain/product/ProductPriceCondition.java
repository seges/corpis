package sk.seges.corpis.server.domain.product;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.shared.domain.price.api.IProductPriceCondition;
import sk.seges.sesam.domain.IMutableDomainObject;
import sk.seges.sesam.pap.model.annotation.ReadOnly;

@DomainInterface
@BaseObject
public interface ProductPriceCondition extends IMutableDomainObject<Long>, IProductPriceCondition {

	String conditionDescription();

	@ReadOnly(ReadOnly.PropertyType.METHOD)
	ProductPriceCondition clone();
}