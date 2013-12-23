/**
 * 
 */
package sk.seges.corpis.domain.shared.pay.trust;

/**
 * @author stefan.sivak
 * 
 * @since Dec 4, 2013 <br/>
 * <br/>
 *        http://trustpay.eu/trustpay-kontakty-referencie-platobne-metody-
 *        novinky/dokumenty-na-stiahnutie/ <br/>
 * <br/>
 *        http://trustpay.eu/assets/Uploads/Merchant-API-integration-v2.6.pdf
 * 
 * 
 */
public enum ETrustPayParameter {

	/**
	 * MID = AID
	 * 
	 * Description: Merchant account ID. ID of account assigned by TrustPay,
	 * Format: Varchar(10), 
	 * Required: Yes, 
	 * Example: 1234567890,
	 * 
	 */
	MID("AID"),

	/**
	 * Description: Amount of the payment exactly 2 decimal places, 
	 * Format: Decimal(13, 2) en-US format, 
	 * Required: For secure requests, 
	 * Example: 1234.00,
	 * 
	 */
	AMT("AMT"),
	
	/**
	 * Description: Currency of the payment same as currency of merchant account
	 * See Appendix III, 
	 * Format: Char(3), 
	 * Required: Yes, 
	 * Example: EUR,
	 * 
	 */
	CUR("CUR"),
	
	
	/**
	 * VS = REF
	 * 
	 * Description: Reference Merchant’s payment identification, 
	 * Format: Char(500), 
	 * Required: For secure requests, 
	 * Example: ORDER9876543210,
	 * 
	 */
	VS("REF"),

	
	/**
	 * Description: Return URL overrides any default Return URL, can be
	 * overridden further by RURL,CURL,EURL, 
	 * Format: Varchar(256), 
	 * Required: No,
	 * Example: http://www.merchant.com/TrustPayReturn.html,
	 * 
	 */
	URL("URL"),

	/**
	 * Description: Return URL overrides default Success Return URL, 
	 * Format: Varchar(256), 
	 * Required: No, 
	 * Example: http://www.merchant.com/TrustPayReturn.html,
	 * 
	 */
	RURL("RURL"),

	/**
	 * Description: Return URL overrides default Cancel Return URL  
	 * Format: Varchar(256), 
	 * Required: No, 
	 * Example: http://www.merchant.com/TrustPayCancel.html,
	 * 
	 */
	CURL("CURL"),

	/**
	 * Description: Return URL overrides default Error Return URL  
	 * Format: Varchar(256), 
	 * Required: No, 
	 * Example: http://www.merchant.com/TrustPayError.html,
	 * 
	 */
	EURL("EURL"),

	
	/**
	 * Description: Notification  URL overrides default Notification Return URL  
	 * Format: Varchar(256), 
	 * Required: No, 
	 * Example: http://www.merchant.com/TrustPayNotification.html,
	 * 
	 */
	NURL("NURL"),

	/**
	 * Description: Data sign see Appendix I 
	 * Format: Char(64)  
	 * Required: For secure requests  
	 * Example: F3E74F2204C2D187DD303CF0C5B22CE41DEB8FA0C1F18356C05DA0F8DAFF5B69
	 * 
	 */
	SIG("SIG"),

	/**
	 * Description: Language default language for TrustPay site see Appendix IV 
	 * Format: Char(2)  
	 * Required: No  
	 * Example: en
	 * 
	 */
	LNG("LNG"),

	/**
	 * Description: Country default country of client see Appendix V 
	 * Format: Char(2)  
	 * Required: No  
	 * Example: SK
	 * 
	 */
	CNT("CNT"),

	/**
	 * Description: Description free text that will be displayed to the user
	 * Format: Varchar(256) 
	 * Required: No 
	 * Example: Payment for Order XYZ
	 * 
	 */
	DSC("DSC"),

	/**
	 * Description: Customer email prefills the email address fields for the
	 * customer when redirected to TrustPay 
	 * Format: Varchar(256) 
	 * Required: No
	 * Example: email@gmail.com
	 * 
	 */
	EMA("EMA");
	
	
	private String name;

	private ETrustPayParameter(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
