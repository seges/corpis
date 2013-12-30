package sk.seges.corpis.server.domain.product.jpa;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import sk.seges.corpis.server.domain.DBNamespace;
import sk.seges.corpis.server.domain.jpa.JpaPrice;
import sk.seges.corpis.server.domain.product.server.model.base.ProductPriceBase;
import sk.seges.corpis.server.domain.product.server.model.data.ProductPriceConditionData;
import sk.seges.corpis.server.domain.product.server.model.data.ProductPriceData;
import sk.seges.corpis.server.domain.server.model.data.PriceData;
import sk.seges.corpis.shared.domain.price.api.PriceType;
import sk.seges.corpis.shared.domain.price.api.Unit;

@Entity
@Table(name = DBNamespace.TABLE_PREFIX + "product_prices")
@SequenceGenerator(name = JpaProductPrice.SEQ_PRODUCT_PRICE, sequenceName = DBNamespace.TABLE_PREFIX + "seq_prices", initialValue = 1)
public class JpaProductPrice extends ProductPriceBase {

	private static final long serialVersionUID = 7607037711836969276L;

	protected static final String SEQ_PRODUCT_PRICE = "seqPrices";

	protected static final int EXT_ID_MIN_LENGTH = 1;
	protected static final int EXT_ID_MAX_LENGTH = 30;

	public JpaProductPrice() {
		setPrice(new JpaPrice());
	}

	@Override
	@Valid
	@Embedded
	public JpaPrice getPrice() {
		return (JpaPrice) super.getPrice();
	}

	@Column(name = "defaultPrice")
	public Boolean getDefaultPrice() {
		return super.getDefaultPrice();
	}

	@Override
	@Column
	@Enumerated(EnumType.STRING)
	public PriceType getPriceType() {
		return super.getPriceType();
	}

	@Override
	@Column
	public Short getPriority() {
		return super.getPriority();
	}

	@Override
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	public Unit getUnit() {
		return super.getUnit();
	}

	@Override
	@Id
	@GeneratedValue(generator = SEQ_PRODUCT_PRICE)
	public Long getId() {
		return super.getId();
	}
	
	@Size(min = EXT_ID_MIN_LENGTH, max = EXT_ID_MAX_LENGTH)
	@Column(name = "external_id", length = EXT_ID_MAX_LENGTH, nullable = true)
	public String getExternalId() {
		return super.getExternalId();
	};

	@Override
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = JpaProductPriceCondition.class)
	public ProductPriceConditionData getPriceCondition() {
		return super.getPriceCondition();
	}

	@Override
	public ProductPriceData clone() {
		ProductPriceData newProductPrice = new JpaProductPrice();

		PriceData newPrice = null;
		if (getPrice() != null) {
			newPrice = getPrice().clone();
		}

		ProductPriceConditionData newProductPriceCondition = null;
		if (getPriceCondition() != null) {
			newProductPriceCondition = getPriceCondition().clone();
		}

		newProductPrice.setPrice(newPrice);
		newProductPrice.setPriceCondition(newProductPriceCondition);
		newProductPrice.setPriority(getPriority());
		newProductPrice.setPriceType(getPriceType());

		return newProductPrice;
	}

}