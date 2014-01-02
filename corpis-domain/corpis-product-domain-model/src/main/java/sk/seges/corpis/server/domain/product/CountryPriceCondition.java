package sk.seges.corpis.server.domain.product;

import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.shared.domain.price.api.ExplicitValuePriceCondition;

@DomainInterface
public interface CountryPriceCondition extends PriceCondition, ExplicitValuePriceCondition {

	String countryCode();

}