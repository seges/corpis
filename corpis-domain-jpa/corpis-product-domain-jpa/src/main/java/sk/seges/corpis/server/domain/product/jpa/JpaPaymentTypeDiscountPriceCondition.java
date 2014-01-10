package sk.seges.corpis.server.domain.product.jpa;

import sk.seges.corpis.server.domain.customer.server.model.data.CustomerCoreData;
import sk.seges.corpis.server.domain.product.server.model.data.PaymentTypeDiscountPriceConditionData;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;
import sk.seges.corpis.shared.domain.EPaymentType;
import sk.seges.corpis.shared.domain.price.api.PriceConditionContext;
import sk.seges.corpis.shared.domain.product.PriceConditionAccessor;

import javax.persistence.*;

@Entity
@DiscriminatorValue("2")
@Table
public class JpaPaymentTypeDiscountPriceCondition extends JpaPriceCondition implements PaymentTypeDiscountPriceConditionData {
	private static final long serialVersionUID = 9119082004533756929L;

	private EPaymentType paymentType;

	public JpaPaymentTypeDiscountPriceCondition() {}

	public JpaPaymentTypeDiscountPriceCondition(EPaymentType paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = PAYMENT_TYPE)
	@Enumerated(EnumType.STRING)
	public EPaymentType getPaymentType() {
		return paymentType;
	}
	
	public void setPaymentType(EPaymentType paymentType) {
		this.paymentType = paymentType;
	}
	
	@Override
	@Transient
	public String getName() {
		return getPaymentType().name();
	}
	
	@Override
	public boolean applies(PriceConditionContext context, String webId, CustomerCoreData customer, ProductData product) {
		return (super.applies(context, webId, customer, product) && context.get(PriceConditionAccessor.CTX_PAYMENT_DISCOUNT_PRICE_CONDITION) != null
				&& context.get(PriceConditionAccessor.CTX_PAYMENT_DISCOUNT_PRICE_CONDITION).equals(getPaymentType()));
	}
}