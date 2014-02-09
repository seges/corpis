package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.shared.model.dto.CriterionDTO;
import sk.seges.sesam.dao.Junction;
import sk.seges.sesam.pap.model.annotation.Copy;
import sk.seges.sesam.pap.model.annotation.GenerateEquals;
import sk.seges.sesam.pap.model.annotation.GenerateHashcode;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;

import java.util.List;

@TransferObjectMapping(domainClass = Junction.class)
@GenerateHashcode(generate = true)
@GenerateEquals(generate = true)
public interface JunctionDTOConfiguration extends CriterionDTO {

	@Copy(methodBody = true)
	void add();
}
