package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.dao.Disjunction;
import sk.seges.sesam.pap.model.annotation.Copy;
import sk.seges.sesam.pap.model.annotation.GenerateEquals;
import sk.seges.sesam.pap.model.annotation.GenerateHashcode;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;


@TransferObjectMapping(domainClass = Disjunction.class)
@GenerateHashcode(generate = false)
@GenerateEquals(generate = false)
public interface DisjunctionDTOConfiguration {

	@Copy(methodBody = true)
	void or();

	@Copy(methodBody = true)
	void getOperation();
}
