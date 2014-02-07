package sk.seges.sesam.server.domain.converter;
import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.EntityManager;

import sk.seges.corpis.pap.converter.hibernate.TransactionalConverter;
import sk.seges.sesam.dao.SimpleExpression;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.server.domain.converter.PropertyHolderToObjectConverter;
import sk.seges.sesam.shared.model.api.PropertyHolder;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;
import sk.seges.sesam.shared.model.dto.SimpleExpressionDTO;
import sk.seges.sesam.utils.CastUtils;

@SuppressWarnings("serial")
@TransferObjectMapping(dtoClass = SimpleExpressionDTO.class,
		domainClassName = "sk.seges.sesam.dao.SimpleExpression", 
		configurationClassName = "sk.seges.sesam.server.model.dto.configuration.SimpleExpressionDTOConfiguration", 
		generateConverter = false, generateDto = false, 
		converterClassName = "sk.seges.sesam.server.model.dto.configuration.SimpleExpressionDTOConverter")
@Generated(value = "sk.seges.corpis.pap.model.hibernate.HibernateTransferObjectDataConverterProcessor")
public class SimpleExpressionDTOConverter<DOMAIN_T extends Comparable<? extends Serializable>> extends TransactionalConverter<SimpleExpressionDTO, SimpleExpression<DOMAIN_T>> {
	
	protected final EntityManager entityManager;
	
	protected final ConverterProviderContext converterProviderContext;
	 
	public SimpleExpressionDTOConverter(EntityManager entityManager, ConverterProviderContext converterProviderContext) {
		super();
		this.entityManager = entityManager;
		this.converterProviderContext = converterProviderContext;
	}
	 
	public boolean equals(SimpleExpression<DOMAIN_T> _domain, SimpleExpressionDTO _dto) {
		if (_domain.getOperation() == null) {
			if (_dto.getOperation() != null)
				return false;
		} else if (!_domain.getOperation().equals(_dto.getOperation()))
			return false;
		if (_domain.getProperty() == null) {
			if (_dto.getProperty() != null)
				return false;
		} else if (!_domain.getProperty().equals(_dto.getProperty()))
			return false;
		if (_domain.getValue() == null) {
			if (_dto.getValue() != null)
				return false;
		} else if (!_domain.getValue().equals(_dto.getValue()))
			return false;
		return true;
	}
	
	public SimpleExpressionDTO createDtoInstance(Serializable id) {
		SimpleExpressionDTO _result = new SimpleExpressionDTO();
		return _result;
	}
	
	public SimpleExpressionDTO toDto(SimpleExpression<DOMAIN_T> _domain) {
	
		if (_domain  == null) {
			return null;
		}
	
		SimpleExpressionDTO _result = createDtoInstance(null);
		return convertToDto(_result, _domain);
	}
	
	public SimpleExpressionDTO convertToDto(SimpleExpressionDTO _result, SimpleExpression<DOMAIN_T> _domain) {
	
		if (_domain  == null) {
			return null;
		}
	
		_result.setOperation(_domain.getOperation());
		_result.setProperty(_domain.getProperty());
		PropertyHolderToObjectConverter converterValue = getDomainPropertyHolderToObjectConverter((Object)_domain.getValue());
		if (converterValue != null) {
			_result.setValue(converterValue.toDto(CastUtils.cast((Object)_domain.getValue(), Object.class)));
		}
		return _result;
	}
	
	public SimpleExpression<DOMAIN_T> createDomainInstance(Serializable id) {
		if (id != null) {
			SimpleExpression<DOMAIN_T> _result = (SimpleExpression<DOMAIN_T>)entityManager.find(SimpleExpression.class, id);
			if (_result != null) {
				return _result;
			}
		}
	
		 return new SimpleExpression<DOMAIN_T>();
	}
	
	public SimpleExpression<DOMAIN_T> fromDto(SimpleExpressionDTO _dto) {
	
		if (_dto == null) {
			return null;
		}
	
		SimpleExpression<DOMAIN_T> _result = createDomainInstance(null);
	
		return convertFromDto(_result, _dto);
	}
	
	public SimpleExpression<DOMAIN_T> convertFromDto(SimpleExpression<DOMAIN_T> _result, SimpleExpressionDTO _dto) {
	
		if (_dto  == null) {
			return null;
		}
	
		_result.setOperation(_dto.getOperation());
		_result.setProperty(_dto.getProperty());
		PropertyHolderToObjectConverter converterValue = getDtoPropertyHolderToObjectConverter((sk.seges.sesam.shared.model.api.PropertyHolder)_dto.getValue());
		if (converterValue != null) {
			_result.setValue((DOMAIN_T)(Object)converterValue.fromDto((PropertyHolder)_dto.getValue()));
		}
		return _result;
	}
	
	protected PropertyHolderToObjectConverter getDomainPropertyHolderToObjectConverter(Object obj){
		PropertyHolderToObjectConverter result = new PropertyHolderToObjectConverter();
		return result;
	}
	
	protected PropertyHolderToObjectConverter getDtoPropertyHolderToObjectConverter(PropertyHolder obj){
		PropertyHolderToObjectConverter result = new PropertyHolderToObjectConverter();
		return result;
	}
}
