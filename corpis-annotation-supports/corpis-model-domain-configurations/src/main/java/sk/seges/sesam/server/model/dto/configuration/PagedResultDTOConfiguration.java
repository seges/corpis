package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.shared.model.dto.PagedResultDTO;
import sk.seges.sesam.server.domain.converter.PagedResultDTOConverter;
import sk.seges.sesam.dao.PagedResult;
import sk.seges.sesam.pap.model.annotation.GenerateEquals;
import sk.seges.sesam.pap.model.annotation.GenerateHashcode;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;

@TransferObjectMapping(domainClass = PagedResult.class, dtoClass = PagedResultDTO.class, converter = PagedResultDTOConverter.class)
@GenerateHashcode(generate = false)
@GenerateEquals(generate = false)
public class PagedResultDTOConfiguration {}
