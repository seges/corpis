package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.dao.Criterion;
import sk.seges.sesam.dao.NotNullExpression;
import sk.seges.sesam.pap.model.annotation.Copy;
import sk.seges.sesam.pap.model.annotation.GenerateEquals;
import sk.seges.sesam.pap.model.annotation.GenerateHashcode;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.shared.model.dao.HasCriterionProperty;
import sk.seges.sesam.shared.model.dto.CriterionDTO;

@TransferObjectMapping(domainClass = NotNullExpression.class)
@GenerateHashcode(generate = false)
@GenerateEquals(generate = false)
public interface NotNullExpressionDTOConfiguration extends CriterionDTO, HasCriterionProperty {

	public static final String operation = "isNotNull";

	@Copy(methodBody = true)
	String getOperation();

}
