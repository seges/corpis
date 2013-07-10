package sk.seges.corpis.shared.domain.price.api;

/**
 *  Unit enum used to display units in price or sup
 *  values
 *  
 *  @author psloboda
 */
public enum Unit {
	METER("m"), M2("m&sup2");
	
	private String sValue;
		
	private Unit(String sValue) {
		this.sValue = sValue;
	}
		
	public String getSValue() {
		return sValue;
	}
}
