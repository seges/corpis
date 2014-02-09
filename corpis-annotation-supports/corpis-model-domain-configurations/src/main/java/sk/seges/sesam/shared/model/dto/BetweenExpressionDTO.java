package sk.seges.sesam.shared.model.dto;

import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.shared.model.api.PropertyHolder;
import sk.seges.sesam.shared.model.dao.HasCriterionProperty;

import javax.annotation.Generated;
import java.io.Serializable;

@SuppressWarnings("serial")
@TransferObjectMapping(dtoClass = BetweenExpressionDTO.class,
		domainClassName = "sk.seges.sesam.dao.BetweenExpression", 
		configurationClassName = "sk.seges.sesam.server.model.dto.configuration.BetweenExpressionDTOConfiguration", 
		generateConverter = false, generateDto = false, 
		converterClassName = "sk.seges.sesam.server.model.dto.configuration.BetweenExpressionDTOConverter")
@Generated(value = "sk.seges.corpis.pap.model.hibernate.HibernateTransferObjectProcessor")
public class BetweenExpressionDTO extends SimpleExpressionDTO implements CriterionDTO, HasCriterionProperty, Serializable {
	 
	public static final String HI_VALUE = "hiValue";
	
	private PropertyHolder hiValue;
	
	public static final String OPERATION = "operation";

	public BetweenExpressionDTO() {}
	
	public BetweenExpressionDTO(String property, String operation, PropertyHolder value, PropertyHolder hiValue) {
		this.hiValue = hiValue;
		setOperation(operation);
		setProperty(property);
		setValue(value);
	}
	
	public BetweenExpressionDTO(PropertyHolder hiValue, String operation, String property, PropertyHolder value) {
		this.hiValue = hiValue;
		setOperation(operation);
		setProperty(property);
		setValue(value);
	}

	public PropertyHolder getHiValue() {
		return hiValue;
	}
	
	public void setHiValue(PropertyHolder hiValue) {
		this.hiValue = hiValue;
	}

	public BetweenExpressionDTO setLoValue(PropertyHolder loValue) {
		setValue(loValue);
		return this;
	}

	public PropertyHolder getLoValue() {
		return getValue();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BetweenExpressionDTO)) return false;
		if (!super.equals(o)) return false;

		BetweenExpressionDTO that = (BetweenExpressionDTO) o;

		if (getOperation() != null ? !getOperation().equals(that.getOperation()) : that.getOperation() != null) return false;
		if (getProperty() != null ? !getProperty().equals(that.getProperty()) : that.getProperty() != null) return false;
		if (getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) return false;
		if (hiValue != null ? !hiValue.equals(that.hiValue) : that.hiValue != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (hiValue != null ? hiValue.hashCode() : 0);
		result = getOperation() != null ? getOperation().hashCode() : 0;
		result = 31 * result + (getProperty() != null ? getProperty().hashCode() : 0);
		result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
		return result;
	}

}
