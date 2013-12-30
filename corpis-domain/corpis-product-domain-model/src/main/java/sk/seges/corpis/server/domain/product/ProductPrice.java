package sk.seges.corpis.server.domain.product;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.server.domain.Price;
import sk.seges.corpis.shared.domain.price.api.PriceType;
import sk.seges.corpis.shared.domain.price.api.Unit;
import sk.seges.sesam.domain.IMutableDomainObject;
import sk.seges.sesam.pap.model.annotation.ReadOnly;

@DomainInterface
@BaseObject
public interface ProductPrice extends IMutableDomainObject<Long> {

	Price price();
	Short priority();
	String externalId();
	ProductPriceCondition priceCondition();
	PriceType priceType();
	Unit unit();
	Boolean defaultPrice();

	@ReadOnly(ReadOnly.PropertyType.METHOD)
	ProductPrice clone();

}
