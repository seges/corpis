package sk.seges.corpis.server.domain.product.jpa;

import sk.seges.corpis.server.domain.customer.server.model.data.CustomerCoreData;
import sk.seges.corpis.server.domain.product.server.model.data.OrderTypeDiscountPriceConditionData;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;
import sk.seges.corpis.shared.domain.customer.ECustomerDiscountType;
import sk.seges.corpis.shared.domain.price.api.PriceConditionContext;

import javax.persistence.*;

@Entity
@DiscriminatorValue("3")
@Table
public class JpaOrderTypeDiscountPriceCondition extends JpaPriceCondition implements OrderTypeDiscountPriceConditionData {
	private static final long serialVersionUID = -8661927126006818012L;

	private ECustomerDiscountType orderType;

	public JpaOrderTypeDiscountPriceCondition() {}

	public JpaOrderTypeDiscountPriceCondition(ECustomerDiscountType orderType) {
		this.orderType = orderType;
	}

	@Column(name = OrderTypeDiscountPriceConditionData.ORDER_TYPE)
	@Enumerated(EnumType.STRING)
	public ECustomerDiscountType getOrderType() {
		return orderType;
	}

	@Override
	public void setOrderType(ECustomerDiscountType orderType) {
		this.orderType = orderType;
	}

	@Override
	@Transient
	public String getName() {
		return getOrderType().name();
	}
	
	@Override
	public boolean applies(PriceConditionContext context, String webId, CustomerCoreData customer, ProductData product) {
		return (super.applies(context, webId, customer, product) && context.get(sk.seges.corpis.shared.domain.product.PriceConditionAccessor.CTX_ORDER_TYPE_DISCOUNT_PRICE_CONDITION) != null &&
				context.get(sk.seges.corpis.shared.domain.product.PriceConditionAccessor.CTX_ORDER_TYPE_DISCOUNT_PRICE_CONDITION).equals(getOrderType()));
	}
}