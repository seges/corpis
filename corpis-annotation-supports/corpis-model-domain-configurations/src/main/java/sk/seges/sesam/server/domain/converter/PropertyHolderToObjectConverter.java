package sk.seges.sesam.server.domain.converter;

import sk.seges.sesam.shared.model.api.PropertyHolder;
import sk.seges.sesam.shared.model.converter.api.DtoConverter;

public class PropertyHolderToObjectConverter implements DtoConverter<PropertyHolder, Object> {

	@Override
	public PropertyHolder convertToDto(PropertyHolder result, Object o) {
		if (o == null || result == null) {
			return result;
		}

		result.setValue(o);
		return result;
	}

	@Override
	public PropertyHolder toDto(Object o) {
		return convertToDto(new PropertyHolder(), o);
	}

	@Override
	public Object convertFromDto(Object result, PropertyHolder s) {
		if (s == null) {
			return null;
		}
		return s.getValue();
	}

	@Override
	public Object fromDto(PropertyHolder s) {
		return s.getValue();
	}

	@Override
	public boolean equals(Object o, Object s) {
		if (o == null && s == null) {
			return true;
		}
		if (o == null || s == null) {
			return false;
		}
		if (!o.getClass().equals(PropertyHolder.class)) {
			 return false;
		}

		return o.equals(s);
	}
}
