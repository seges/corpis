/**
 * 
 */
package sk.seges.corpis.appscaffold.shared.domain;

import sk.seges.sesam.shared.model.api.PropertyHolder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author ladislav.gazo
 */
public class MapBasedObject implements Serializable {
	private static final long serialVersionUID = -1383864308128396L;
	
	protected Map<String, PropertyHolder> map = new HashMap<String, PropertyHolder>();
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		PropertyHolder propertyHolder = map.get(key);

		if (propertyHolder == null) {
			return null;
		}
		return (T) propertyHolder.getValue();
	}
	
	public void set(String key, Object value) {
		map.put(key, new PropertyHolder(value));
	}
	
	public Set<Entry<String, PropertyHolder>> entrySet() {
		return map.entrySet();
	}
	
	public Map<String, PropertyHolder> getMap() {
		return map;
	}

	public void setMap(Map<String, PropertyHolder> map) {
		this.map = map;
	}
}
