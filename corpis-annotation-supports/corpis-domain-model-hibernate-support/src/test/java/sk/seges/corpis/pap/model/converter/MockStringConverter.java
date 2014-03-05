package sk.seges.corpis.pap.model.converter;

import sk.seges.sesam.shared.model.converter.ConverterProviderContext;
import sk.seges.sesam.shared.model.converter.api.DtoConverter;

public class MockStringConverter implements DtoConverter<String, String> {

	public MockStringConverter(ConverterProviderContext converterProviderContext) {
	}

	@Override
	public String convertToDto(String result, String s) {
		return null;
	}

	@Override
	public String toDto(String s) {
		return null;
	}

	@Override
	public String convertFromDto(String result, String s) {
		return null;
	}

	@Override
	public String fromDto(String s) {
		return null;
	}

	@Override
	public boolean equals(Object s, Object s2) {
		return false;
	}
}
