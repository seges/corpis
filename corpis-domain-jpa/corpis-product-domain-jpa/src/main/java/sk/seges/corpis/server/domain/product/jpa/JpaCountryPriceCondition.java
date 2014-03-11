package sk.seges.corpis.server.domain.product.jpa;

import sk.seges.corpis.server.domain.customer.server.model.data.CustomerCoreData;
import sk.seges.corpis.server.domain.product.server.model.data.CountryPriceConditionData;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;
import sk.seges.corpis.shared.domain.price.api.ExplicitValuePriceCondition;
import sk.seges.corpis.shared.domain.price.api.PriceConditionContext;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("5")
@Table
public class JpaCountryPriceCondition extends JpaPriceCondition implements CountryPriceConditionData, ExplicitValuePriceCondition {

	private String countryCode;


	public JpaCountryPriceCondition() {}

	public JpaCountryPriceCondition(String countryCode) {
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
