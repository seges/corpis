package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.dao.Criterion;
import sk.seges.sesam.dao.NotNullExpression;
import sk.seges.sesam.dao.NullExpression;
import sk.seges.sesam.pap.model.annotation.*;
import sk.seges.sesam.shared.model.dao.HasCriterionProperty;

@TransferObjectMapping(domainClass = NullExpression.class)
@GenerateHashcode(generate = true, type = TraversalType.DEFAULT)
@GenerateEquals(generate = true, type = TraversalType.DEFAULT)
public interface NullExpressionDTOConfiguration extends Criterion, HasCriterionProperty {

	public static final String operation = "isNull";

	@Copy(methodBody = true)
	String getOperation();

}
