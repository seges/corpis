package sk.seges.sesam.server.domain.converter;

import org.hibernate.collection.PersistentSet;
import org.hibernate.proxy.HibernateProxy;
import sk.seges.sesam.server.model.converter.common.CollectionConverter;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class PersistentCollectionConverter<DTO, DOMAIN> extends CollectionConverter<DTO, DOMAIN> {

	public PersistentCollectionConverter(ConverterProviderContext converterProviderContext) {
		super(converterProviderContext);
	}

	@Override
	protected DOMAIN getDomainObject(Iterator<?> iterator) {
		DOMAIN result = super.getDomainObject(iterator);

		if (result == null) {
			return null;
		}

		if (result instanceof HibernateProxy) {
			result = (DOMAIN) ((HibernateProxy) result).getHibernateLazyInitializer().getImplementation();
		}

		return result;
	}

	@Override
	protected <T extends Collection<DTO>> T createDtoInstance(Class<T> targetClass) {
		if (targetClass.isAssignableFrom(PersistentSet.class)) {
			return (T) new HashSet();
		}
		return super.createDtoInstance(targetClass);
	}
}
