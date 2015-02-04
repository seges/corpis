package sk.seges.corpis.shared.domain.api;

import sk.seges.corpis.shared.domain.HasWebId;

public interface HasCodeListField extends HasWebId {
	
	public static final String TYPE = "type";
	
	public static final String POSITION = "position";

	int getPosition();
	
	void setPosition(int position);
	
	/**
	 * localized names: {"locale":"localized name","locale_2":"localized_name"}
	 */
	String getNames();
	
	void setNames(String names);
	
	String getType();
	
	void setType(String type);
}
