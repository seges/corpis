package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.dao.Criterion;
import sk.seges.sesam.dao.NotNullExpression;
import sk.seges.sesam.dao.NullExpression;
import sk.seges.sesam.pap.model.annotation.Copy;
import sk.seges.sesam.pap.model.annotation.GenerateEquals;
import sk.seges.sesam.pap.model.annotation.GenerateHashcode;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.shared.model.dao.HasCriterionProperty;

@TransferObjectMapping(domainClass = NullExpression.class)
@GenerateHashcode(generate = false)
@GenerateEquals(generate = false)
public interface NullExpressionDTOConfiguration extends Criterion, HasCriterionProperty {

	public static final String operation = "isNull";

	@Copy(methodBody = true)
	String getOperation();

}
