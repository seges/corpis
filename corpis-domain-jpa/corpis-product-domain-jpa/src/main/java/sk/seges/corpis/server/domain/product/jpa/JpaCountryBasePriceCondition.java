package sk.seges.corpis.server.domain.product.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

import sk.seges.corpis.server.domain.customer.server.model.data.CustomerCoreData;
import sk.seges.corpis.server.domain.product.server.model.data.CountryPriceConditionData;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;
import sk.seges.corpis.shared.domain.price.api.PriceConditionContext;

@Entity
public class JpaCountryBasePriceCondition extends JpaPriceCondition implements CountryPriceConditionData {
	private static final long serialVersionUID = -2267788303679723802L;
	
	private String countryCode;

	public JpaCountryBasePriceCondition() {}

	public JpaCountryBasePriceCondition(String countryCode) {
		setCountryCode(countryCode);
	}

	@Column(name = COUNTRY_CODE)
	public String getCountryCode() {
		return countryCode;
	}

	@Override
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public boolean applies(PriceConditionContext context, String webId, CustomerCoreData customer, ProductData product) {
		return (super.applies(context, webId, customer, product) && context.get(sk.seges.corpis.shared.domain.product.PriceConditionAccessor.CTX_COUNTRY_CODE_PRICE_CONDITION) != null &&
				context.get(sk.seges.corpis.shared.domain.product.PriceConditionAccessor.CTX_COUNTRY_CODE_PRICE_CONDITION).equals(getCountryCode()));
	}


}
