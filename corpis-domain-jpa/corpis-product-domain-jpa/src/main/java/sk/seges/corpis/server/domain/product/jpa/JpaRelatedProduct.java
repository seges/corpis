package sk.seges.corpis.server.domain.product.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import sk.seges.corpis.server.domain.DBNamespace;
import sk.seges.corpis.server.domain.product.server.model.base.RelatedProductBase;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;

@Entity
@Table(name = DBNamespace.TABLE_PREFIX + "related_product", uniqueConstraints = { @UniqueConstraint(columnNames = {"product", "related_product" }) })
@SequenceGenerator(name = JpaRelatedProduct.SEQ_RELATED_PRODUCTS, sequenceName = DBNamespace.TABLE_PREFIX + "seq_related_products", initialValue = 1)
public class JpaRelatedProduct extends RelatedProductBase {
	private static final long serialVersionUID = -5614882896518185987L;

	protected static final String SEQ_RELATED_PRODUCTS = "seqRelatedProducts";

	public JpaRelatedProduct() {
	}

	public JpaRelatedProduct(ProductData product, ProductData relatedProduct) {
		super();
		setProduct(product);
		setRelatedProduct(relatedProduct);
	}

	@Override
	@Id
	@GeneratedValue(generator = SEQ_RELATED_PRODUCTS)
	public Long getId() {
		return super.getId();
	}
	
	@Override
	@ManyToOne(cascade = { CascadeType.ALL }, targetEntity = JpaProduct.class)
	@JoinColumn(name = "product")
	public ProductData getProduct() {
		return super.getProduct();
	}

	@Override
	@ManyToOne(cascade = { CascadeType.ALL }, targetEntity = JpaProduct.class)
	@JoinColumn(name = "related_product")
	public ProductData getRelatedProduct() {
		return super.getRelatedProduct();
	}
}