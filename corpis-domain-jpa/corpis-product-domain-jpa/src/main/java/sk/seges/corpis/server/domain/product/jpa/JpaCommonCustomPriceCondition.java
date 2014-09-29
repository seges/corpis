package sk.seges.corpis.server.domain.product.jpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import sk.seges.corpis.server.domain.customer.server.model.data.CustomerCoreData;
import sk.seges.corpis.server.domain.product.server.model.data.CommonCustomPriceConditionData;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;
import sk.seges.corpis.shared.domain.price.api.PriceConditionContext;

@Entity
@DiscriminatorValue("8")
public class JpaCommonCustomPriceCondition extends JpaPriceCondition implements CommonCustomPriceConditionData{

	private static final long serialVersionUID = 1201667767334346597L;

	@Override
	public boolean applies(PriceConditionContext context, String webId, CustomerCoreData customer, ProductData product) {
		return super.applies(context, webId, customer, product);
	}
	
	@Override
	@Transient
	public String getName() {
		return "discount_commonCustom";
	}
}
