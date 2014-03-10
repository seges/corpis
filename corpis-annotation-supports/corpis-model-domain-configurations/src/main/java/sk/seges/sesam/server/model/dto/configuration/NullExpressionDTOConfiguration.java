package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.dao.Criterion;
import sk.seges.sesam.dao.NotNullExpression;
import sk.seges.sesam.dao.NullExpression;
import sk.seges.sesam.pap.model.annotation.*;
import sk.seges.sesam.shared.model.dao.HasCriterionProperty;
import sk.seges.sesam.shared.model.dto.CriterionDTO;

@TransferObjectMapping(domainClass = NullExpression.class)
@GenerateHashcode(generate = true, type = TraversalType.DEFAULT)
@GenerateEquals(generate = true, type = TraversalType.DEFAULT)
public interface NullExpressionDTOConfiguration extends CriterionDTO, HasCriterionProperty {

	public static final String operation = "isNull";

	@Copy(methodBody = true)
	String getOperation();

}
