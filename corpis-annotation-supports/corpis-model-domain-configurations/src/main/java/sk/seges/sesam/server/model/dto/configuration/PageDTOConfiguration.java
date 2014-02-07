package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.dao.Page;
import sk.seges.sesam.pap.model.annotation.*;
import sk.seges.sesam.server.domain.converter.ProjectableResultConverter;

@TransferObjectMapping(domainClass = Page.class)
@GenerateHashcode(generate = false)
@GenerateEquals(generate = false)
@Mapping(Mapping.MappingType.EXPLICIT)
public interface PageDTOConfiguration {

	@TransferObjectMapping(converter = ProjectableResultConverter.class)
	String projectableResult();

	@ConstructorParameter(0) void startIndex();
	@ConstructorParameter(1) void pageSize();

	void sortables();
	void projectables();

	void filterable();
}
