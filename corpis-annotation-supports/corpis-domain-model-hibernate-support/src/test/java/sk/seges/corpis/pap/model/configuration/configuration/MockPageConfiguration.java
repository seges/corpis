package sk.seges.corpis.pap.model.configuration.configuration;

import sk.seges.corpis.pap.model.converter.MockStringConverter;
import sk.seges.sesam.dao.Page;
import sk.seges.sesam.pap.model.annotation.GenerateEquals;
import sk.seges.sesam.pap.model.annotation.GenerateHashcode;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;

@TransferObjectMapping(domainClass = Page.class, dtoClass = Page.class)
@GenerateEquals(generate = false)
@GenerateHashcode(generate = false)
public interface MockPageConfiguration {

	@TransferObjectMapping(converter = MockStringConverter.class)
	String projectableResult();

}
