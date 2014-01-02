package sk.seges.corpis.server.domain.price.api;

import sk.seges.corpis.server.domain.customer.server.model.data.CustomerCoreData;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;
import sk.seges.corpis.shared.domain.price.api.PriceConditionContext;


public interface IPriceCondition {
	
	boolean applies(PriceConditionContext context, String webId, CustomerCoreData customer, ProductData product);

	String getName();
}