package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.dao.LikeExpression;
import sk.seges.sesam.pap.model.annotation.GenerateEquals;
import sk.seges.sesam.pap.model.annotation.GenerateHashcode;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.server.domain.converter.LikeExpressionDTOConverter;
import sk.seges.sesam.server.domain.converter.PropertyHolderToObjectConverter;
import sk.seges.sesam.shared.model.api.PropertyHolder;
import sk.seges.sesam.shared.model.dto.LikeExpressionDTO;

@TransferObjectMapping(domainClass = LikeExpression.class, dtoClass = LikeExpressionDTO.class, converter = LikeExpressionDTOConverter.class)
@GenerateHashcode(generate = false)
@GenerateEquals(generate = false)
public interface LikeExpressionDTOConfiguration {

	public static final String LIKE = "like";
	public static final String ILIKE = "ilike";

	@TransferObjectMapping(converter = PropertyHolderToObjectConverter.class)
	PropertyHolder value();

}