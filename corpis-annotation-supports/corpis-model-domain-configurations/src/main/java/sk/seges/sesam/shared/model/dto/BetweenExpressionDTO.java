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
}
