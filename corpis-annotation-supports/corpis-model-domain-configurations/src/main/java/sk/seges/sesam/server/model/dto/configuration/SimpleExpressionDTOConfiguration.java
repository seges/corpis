package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.dao.SimpleExpression;
import sk.seges.sesam.pap.model.annotation.*;
import sk.seges.sesam.server.domain.converter.PropertyHolderToObjectConverter;
import sk.seges.sesam.server.domain.converter.SimpleExpressionDTOConverter;
import sk.seges.sesam.shared.model.api.PropertyHolder;
import sk.seges.sesam.shared.model.dao.HasCriterionProperty;
import sk.seges.sesam.shared.model.dto.CriterionDTO;
import sk.seges.sesam.shared.model.dto.SimpleExpressionDTO;

@TransferObjectMapping(domainClass = SimpleExpression.class, dtoClass = SimpleExpressionDTO.class, converter = SimpleExpressionDTOConverter.class)
@GenerateHashcode(generate = true, type = TraversalType.DEFAULT)
@GenerateEquals(generate = true, type = TraversalType.DEFAULT)
public interface SimpleExpressionDTOConfiguration extends CriterionDTO, HasCriterionProperty {

	@TransferObjectMapping(converter = PropertyHolderToObjectConverter.class)
	PropertyHolder value();

	@ConstructorParameter(1)
	void operation();

	@ConstructorParameter(2)
	void property();

}
