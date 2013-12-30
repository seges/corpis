package sk.seges.corpis.server.domain.stock.jpa;

import javax.persistence.*;

import sk.seges.corpis.server.domain.DBNamespace;
import sk.seges.corpis.server.domain.product.jpa.JpaProduct;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;
import sk.seges.corpis.server.domain.stock.server.model.base.StockItemBase;
import sk.seges.corpis.server.domain.stock.server.model.data.StockItemData;
import sk.seges.corpis.server.domain.stock.server.model.data.WarehouseData;

@Entity
@SuppressWarnings("serial")
@Table(name = DBNamespace.TABLE_PREFIX + "stock_item")
public class JpaStockItem extends StockItemBase {

	protected static final String SEQ_PRODUCT_ITEM = "seqItems";

	@Override
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = JpaWarehouse.class)
	public WarehouseData getWarehouse() {
		return super.getWarehouse();
	}

	@Override
	@Id
	@GeneratedValue(generator = SEQ_PRODUCT_ITEM)
	public Long getId() {
		return super.getId();
	}

	@ManyToOne(fetch=FetchType.EAGER, targetEntity = JpaProduct.class)
	public ProductData getProduct() {
		return super.getProduct();
	}

	@Column(name="count")
	public Integer getCount() {
		return super.getCount();
	}


}