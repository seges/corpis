package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.dao.BetweenExpression;
import sk.seges.sesam.pap.model.annotation.*;
import sk.seges.sesam.server.domain.converter.BetweenExpressionDTOConverter;
import sk.seges.sesam.server.domain.converter.PropertyHolderToObjectConverter;
import sk.seges.sesam.shared.model.api.PropertyHolder;
import sk.seges.sesam.shared.model.dao.HasCriterionProperty;
import sk.seges.sesam.shared.model.dto.BetweenExpressionDTO;
import sk.seges.sesam.shared.model.dto.CriterionDTO;

@TransferObjectMapping(domainClass = BetweenExpression.class, dtoClass = BetweenExpressionDTO.class, converter = BetweenExpressionDTOConverter.class)
@GenerateHashcode(generate = true, type = TraversalType.DEFAULT)
@GenerateEquals(generate = true, type = TraversalType.DEFAULT)
public interface BetweenExpressionDTOConfiguration extends CriterionDTO, HasCriterionProperty {

	@ConstructorParameter(1)
	void property();
	@ConstructorParameter(2)
	void operation();

	@ConstructorParameter(3)
	@TransferObjectMapping(converter = PropertyHolderToObjectConverter.class)
	PropertyHolder value();

	@ConstructorParameter(4)
	@TransferObjectMapping(converter = PropertyHolderToObjectConverter.class)
	PropertyHolder hiValue();

	@Ignore
	@Copy(methodBody = true)
	void setLoValue(PropertyHolder loValue);

	@Ignore
	@Copy(methodBody = true)
	PropertyHolder getLoValue();

}
