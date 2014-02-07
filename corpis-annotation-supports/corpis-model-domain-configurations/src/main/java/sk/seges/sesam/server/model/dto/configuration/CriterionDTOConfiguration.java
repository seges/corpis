package sk.seges.sesam.server.model.dto.configuration;

import sk.seges.sesam.shared.model.dto.CriterionDTO;
import sk.seges.sesam.dao.Criterion;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;

@TransferObjectMapping(domainInterface = Criterion.class, dtoInterface = CriterionDTO.class)
public interface CriterionDTOConfiguration {}
