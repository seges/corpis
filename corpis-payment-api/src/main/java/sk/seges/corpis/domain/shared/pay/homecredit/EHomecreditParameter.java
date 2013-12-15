/**
 * 
 */
package sk.seges.corpis.domain.shared.pay.homecredit;

/**
 * @author stefan.sivak
 *
 * @since Dec 10, 2013
 */
public enum EHomecreditParameter {

	MID("shop"),

	VS("o_code"),

	AMT("o_price"),

	PRODUCT_IDENT("product_ident"),

	PRODUCT_SET("product_set"),

	C_NAME("c_name"),

	C_SURNAME("c_surname"),

	C_TITLE("c_title"),

	C_PHONE("c_phone"),

	C_MOBILE("c_mobile"),

	C_EMAIL("c_email"),

	C_P_STREET("c_p_street"),

	C_P_NUM("c_p_num"),

	C_P_CITY("c_p_city"),

	C_P_ZIP("c_p_zip"),

	C_C_STREET("c_c_street"),

	C_C_NUM("c_c_num"),

	C_C_CITY("c_c_city"),

	C_C_ZIP("c_c_zip"),

	CUSTOMER_IDENTIFICATION("customer_identification"),

	G_NAME("g_name"),

	G_PRODUCER("g_producer"),

	P_ASSISTANT("p_assistant"),

	PARTNER_SHOP_CODE("partner_shop_code"),

	SELLER_NAME("seller_name"),

	SELLER_SURNAME("seller_surname"),
	
	RET_URL("ret_url"),
	
	TIME_REQUEST("time_request"),

	SH("sh"),

	ESH("esh")

	;

	private String name;

	private EHomecreditParameter(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
