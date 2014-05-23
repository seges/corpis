package sk.seges.corpis.server.domain.product.jpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("5")
@Table
public class JpaCountryPriceCondition extends JpaCountryBasePriceCondition {
	private static final long serialVersionUID = -2267788303679723802L;

	public JpaCountryPriceCondition() {}

	public JpaCountryPriceCondition(String countryCode) {
		super(countryCode);
	}
}
