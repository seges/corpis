package sk.seges.corpis.server.domain.product.jpa;

import sk.seges.corpis.server.domain.customer.server.model.data.CustomerCoreData;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;
import sk.seges.corpis.shared.domain.price.api.PriceConditionContext;

public class JpaCustomSalesPriceCondition extends JpaSalesPriceCondition {

	private static final long serialVersionUID = 1314089364078203476L;

	@Override
	public boolean applies(PriceConditionContext context, String webId, CustomerCoreData customer, ProductData product) {
		return super.applies(context, webId, customer, product) && ((getActiveForWeb() == null && getActive()) || getActiveForWeb());
	}
}
