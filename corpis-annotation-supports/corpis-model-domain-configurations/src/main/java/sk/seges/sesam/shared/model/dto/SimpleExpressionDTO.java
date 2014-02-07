package sk.seges.sesam.shared.model.dto;

import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.shared.model.api.PropertyHolder;
import sk.seges.sesam.shared.model.dao.HasCriterionProperty;

import javax.annotation.Generated;
import java.io.Serializable;

@SuppressWarnings("serial")
@TransferObjectMapping(dtoClass = SimpleExpressionDTO.class,
		domainClassName = "sk.seges.sesam.dao.SimpleExpression", 
		configurationClassName = "sk.seges.sesam.server.model.dto.configuration.SimpleExpressionDTOConfiguration", 
		generateConverter = false, generateDto = false, 
		converterClassName = "sk.seges.sesam.server.model.dto.configuration.SimpleExpressionDTOConverter")
@Generated(value = "sk.seges.corpis.pap.model.hibernate.HibernateTransferObjectProcessor")
public class SimpleExpressionDTO implements CriterionDTO, HasCriterionProperty, Serializable {
	 
	 
	public static final String OPERATION = "operation";
	
	private String operation;
	
	public static final String PROPERTY = "property";
	
	private String property;
	
	public static final String VALUE = "value";
	
	private PropertyHolder value;
	
	public SimpleExpressionDTO() {}

	public SimpleExpressionDTO(String operation, String property) {
		this.operation = operation;
		this.property = property;
	}

	public SimpleExpressionDTO(String operation, String property, PropertyHolder value) {
		this.operation = operation;
		this.property = property;
		this.value = value;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	public PropertyHolder getValue() {
		return value;
	}
	
	public void setValue(PropertyHolder value) {
		this.value = value;
	}
}
