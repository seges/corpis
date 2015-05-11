/**
 * 
 */
package sk.seges.corpis.dao.hibernate;

import java.util.List;

import org.hibernate.transform.DistinctResultTransformer;


/**
 * Distinct version of ChainedFieldsTransformer
 * 
 * @author jusmolinsky
 */
public class DistinctChainedFieldsTransformer extends ChainedFieldsTransformer {

	public DistinctChainedFieldsTransformer(Class<?> resultClass, String[] directFields) {
		super(resultClass, directFields);	
	}

	private static final long serialVersionUID = -223716179338048217L;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List collection) {
		return DistinctResultTransformer.INSTANCE.transformList(collection);
	}
}