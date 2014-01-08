package sk.seges.sesam.server.domain.converter;

import org.hibernate.collection.PersistentSet;
import sk.seges.sesam.server.model.converter.common.CollectionConverter;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PersistentSetConverter extends CollectionConverter<Set, PersistentSet> {

	public PersistentSetConverter(ConverterProviderContext converterProviderContext) {
		super(converterProviderContext);
	}

	@Override
	protected <T extends Collection<Set>> T createDtoInstance(Class<T> targetClass) {
		return (T) new HashSet();
	}
}
