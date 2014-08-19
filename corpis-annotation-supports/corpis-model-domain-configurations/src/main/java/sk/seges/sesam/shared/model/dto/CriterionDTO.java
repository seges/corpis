package sk.seges.sesam.shared.model.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = As.PROPERTY, property = "className")
@JsonSubTypes({ @Type(value = SimpleExpressionDTO.class, name = "simple"), @Type(value = JunctionDTO.class, name = "junction") })
public interface CriterionDTO extends Serializable {

	/** Hibernate's Restrictions method name counterpart. */
	String getOperation();
}