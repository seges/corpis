package sk.seges.corpis.server.domain.product.jpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import sk.seges.corpis.server.domain.product.server.model.data.DiscountedPriceConditionData;
import sk.seges.corpis.shared.domain.price.api.PriceConditionContext;
import sk.seges.corpis.shared.domain.price.api.TerminalPriceCondition;
import sk.seges.corpis.shared.domain.product.PriceConditionAccessor;


@Entity
@DiscriminatorValue("3")
@Table
public class JpaDiscountedPriceCondition extends JpaProductPriceCondition implements TerminalPriceCondition, DiscountedPriceConditionData {

	private static final long serialVersionUID = 8766508644651589311L;
	
	public JpaDiscountedPriceCondition() {}

	public JpaDiscountedPriceCondition(String discountType) {
		setConditionDescription(discountType);
	}

	@Override
	public boolean applies(PriceConditionContext context) {
		return (getConditionDescription() != null && getConditionDescription().equals(
				context.get(PriceConditionAccessor.CTX_DISCOUNT_TYPE)));
	}
}