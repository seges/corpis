package sk.seges.corpis.shared.domain.customer;

/**
 * 
 * @author psenicka
 *
 */
public enum ECustomerDiscountType {

	DIRECT_SALES("discount_directSales", EDiscountType.PRODUCT),
	COMMISSION_SALES("discount_commissionSales", EDiscountType.PRODUCT),
	COMMISSION_PROCESS("discount_commissionProcess", EDiscountType.PRODUCT),
	CASH("discount_cash", EDiscountType.ORDER),
	COD("discount_cod", EDiscountType.ORDER),
	OFFER("orderType_offer", null);

	
	public enum EDiscountType {
		PRODUCT,ORDER;
	}
	
	private String i18key;
	private EDiscountType discountType;
	
	private ECustomerDiscountType(String i18key, EDiscountType discountType) {
		this.i18key = i18key;
	}
	
	public String getI18key() {
		return i18key;
	}
	
	public EDiscountType getDiscountType() {
		return discountType;
	}
}
