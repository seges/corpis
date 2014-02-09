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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SimpleExpressionDTO)) return false;

		SimpleExpressionDTO that = (SimpleExpressionDTO) o;

		if (operation != null ? !operation.equals(that.operation) : that.operation != null) return false;
		if (property != null ? !property.equals(that.property) : that.property != null) return false;
		if (value != null ? !value.equals(that.value) : that.value != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = operation != null ? operation.hashCode() : 0;
		result = 31 * result + (property != null ? property.hashCode() : 0);
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
}
