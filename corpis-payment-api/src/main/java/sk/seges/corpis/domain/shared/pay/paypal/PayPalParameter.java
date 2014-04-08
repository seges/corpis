package sk.seges.corpis.domain.shared.pay.paypal;

public enum PayPalParameter {
	/**
	 * for buyButton "_xclick"
	 */
	CMD("cmd"),
	
	/**
	 * Your PayPal ID or an email address associated with your PayPal account
	 */
	BUSINESS("business"),
	
	/**
	 * Description of item being sold (maximum 127 characters).
	 */
	ITEM_NAME("item_name"),
	
	/**
	 * Pass-through variable for you to track product or service purchased or the contribution made
	 */
	ITEM_NUMBER("item_number"),
	
	/**
	 * The price or amount of the product, service, or contribution, not including shipping, handling, or tax
	 */
	AMOUNT("amount"),
	
	/**
	 * Do not prompt payers for shipping address. Allowable values:
	 * [0] prompt for an address, but do not require one
	 * [1] do not prompt for an address
	 * [2] prompt for an address, and require one
	 */
	NO_SHIPPING("no_shipping"),
	
	/**
	 * Do not prompt payers to include a note with their payments. Allowable values:
	 * [1] hide the text box and the prompt
	 */
	NO_NOTE("no_note"),
	
	CURRENCY_CODE("currency_code"),
	
	/**
	 * An internet URL where the user will be returned after completing the payment
	 */
	RETURN("return"),
	
	/**
	 * An internet URL where the user will be returned after canceling the payment
	 */
	CANCEL_RETURN("cancel_return");
	
    private String name;

    private PayPalParameter(String name) {
    	this.name = name;
    }
    
    public String getName() {
    	return name;
    }
}
