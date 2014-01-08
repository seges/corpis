package sk.seges.sesam.server.domain.converter;

import org.hibernate.collection.PersistentSet;
import sk.seges.sesam.server.model.converter.common.CollectionConverter;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;

import java.util.Collection;
import java.util.HashSet;

public class PersistentConverter<DTO, DOMAIN> extends CollectionConverter<DTO, DOMAIN> {

	public PersistentConverter(ConverterProviderContext converterProviderContext) {
		super(converterProviderContext);
	}

	@Override
	protected <T extends Collection<DTO>> T createDtoInstance(Class<T> targetClass) {
		if (targetClass.isAssignableFrom(PersistentSet.class)) {
			return (T) new HashSet();
		}
		return super.createDtoInstance(targetClass);
	}
}
