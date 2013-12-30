package sk.seges.corpis.server.domain.stock;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.server.domain.product.Product;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;
import sk.seges.sesam.domain.IDomainObject;
import sk.seges.sesam.domain.IMutableDomainObject;

import javax.persistence.Column;

@DomainInterface
@BaseObject
public interface StockItem extends IMutableDomainObject<Long> {
	
	Warehouse warehouse();

	Product product();

	Integer count();

}