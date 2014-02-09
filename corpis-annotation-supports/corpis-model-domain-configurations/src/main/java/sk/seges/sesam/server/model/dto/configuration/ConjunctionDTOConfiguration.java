package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.dao.Conjunction;
import sk.seges.sesam.pap.model.annotation.Copy;
import sk.seges.sesam.pap.model.annotation.GenerateEquals;
import sk.seges.sesam.pap.model.annotation.GenerateHashcode;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.shared.model.dto.CriterionDTO;

import java.util.List;

@TransferObjectMapping(domainClass = Conjunction.class)
@GenerateHashcode(generate = false)
@GenerateEquals(generate = false)
public interface ConjunctionDTOConfiguration {

	@Copy(methodBody = true)
	void and();

	@Copy(methodBody = true)
	void getOperation();
}