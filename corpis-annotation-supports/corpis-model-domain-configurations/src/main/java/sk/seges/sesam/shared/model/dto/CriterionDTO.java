package sk.seges.sesam.shared.model.dto;

import java.io.Serializable;

public interface CriterionDTO extends Serializable {

	/** Hibernate's Restrictions method name counterpart. */
	String getOperation();
}