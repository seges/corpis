package sk.seges.corpis.server.domain.product;

import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;

@DomainInterface
public interface CountryPriceCondition extends PriceCondition {

	String countryCode();

}