package sk.seges.corpis.appscaffold.model.pap.configurer;

import java.lang.reflect.Type;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.sesam.core.pap.configuration.DefaultProcessorConfigurer;

public class ModelBaseProcessorConfigurer extends DefaultProcessorConfigurer {

	@Override
	protected Type[] getConfigurationElement(DefaultConfigurationElement element) {
		return new Type[] {
			BaseObject.class
		};	
	}

}
