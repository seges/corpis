package sk.seges.sesam.server.domain.converter;

import sk.seges.corpis.pap.converter.hibernate.TransactionalConverter;
import sk.seges.sesam.dao.BetweenExpression;
import sk.seges.sesam.dao.Conjunction;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.shared.model.api.PropertyHolder;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;
import sk.seges.sesam.shared.model.dto.BetweenExpressionDTO;
import sk.seges.sesam.shared.model.dto.ConjunctionDTO;
import sk.seges.sesam.utils.CastUtils;

import javax.annotation.Generated;
import javax.persistence.EntityManager;
import java.io.Serializable;

@SuppressWarnings("serial")
@TransferObjectMapping(dtoClass = BetweenExpressionDTO.class,
		domainClassName = "sk.seges.sesam.dao.BetweenExpression", 
		configurationClassName = "sk.seges.sesam.server.model.dto.configuration.BetweenExpressionDTOConfiguration", 
		generateConverter = false, generateDto = false, 
		converterClassName = "sk.seges.sesam.server.model.dto.configuration.BetweenExpressionDTOConverter")
@Generated(value = "sk.seges.corpis.pap.model.hibernate.HibernateTransferObjectDataConverterProcessor")
public class BetweenExpressionDTOConverter<DOMAIN_T extends Comparable<? extends Serializable>> extends TransactionalConverter<BetweenExpressionDTO, BetweenExpression<DOMAIN_T>> {
	
	protected final EntityManager entityManager;
	
	protected final ConverterProviderContext converterProviderContext;
	 
	public BetweenExpressionDTOConverter(EntityManager entityManager, ConverterProviderContext converterProviderContext) {
		super();
		this.entityManager = entityManager;
		this.converterProviderContext = converterProviderContext;
	}
	 
	public boolean equals(Object _domainArg, Object _dtoArg) {
		if (_domainArg == null) {
			return (_dtoArg == null);
		}

		if (_dtoArg == null) {
			return false;
		}

		if (!(_domainArg instanceof BetweenExpression)) {
			return false;
		}

		BetweenExpression<DOMAIN_T> _domain = (BetweenExpression<DOMAIN_T>)_domainArg;

		if (!(_dtoArg instanceof BetweenExpressionDTO)) {
			return false;
		}

		BetweenExpressionDTO _dto = (BetweenExpressionDTO)_dtoArg;

		if (_domain.getHiValue() == null) {
			if (_dto.getHiValue() != null)
				return false;
		} else if (!_domain.getHiValue().equals(_dto.getHiValue()))
			return false;
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
	
	public BetweenExpressionDTO createDtoInstance(Serializable id) {
		BetweenExpressionDTO _result = new BetweenExpressionDTO();
		return _result;
	}
	
	public BetweenExpressionDTO toDto(BetweenExpression<DOMAIN_T> _domain) {
	
		if (_domain  == null) {
			return null;
		}
	
		BetweenExpressionDTO _result = createDtoInstance(null);
		return convertToDto(_result, _domain);
	}
	
	public BetweenExpressionDTO convertToDto(BetweenExpressionDTO _result, BetweenExpression<DOMAIN_T> _domain) {
	
		if (_domain  == null) {
			return null;
		}
	
		PropertyHolderToObjectConverter converterHiValue = getDomainPropertyHolderToObjectConverter((Object)_domain.getHiValue());
		if (converterHiValue != null) {
			_result.setHiValue(converterHiValue.toDto(CastUtils.cast((Object)_domain.getHiValue(), Object.class)));
		}
		_result.setOperation(_domain.getOperation());
		_result.setProperty(_domain.getProperty());
		PropertyHolderToObjectConverter converterValue = getDomainPropertyHolderToObjectConverter((Object)_domain.getValue());
		if (converterValue != null) {
			_result.setValue(converterValue.toDto(CastUtils.cast((Object)_domain.getValue(), Object.class)));
		}
		return _result;
	}
	
	public BetweenExpression<DOMAIN_T> createDomainInstance(Serializable id) {
		if (id != null) {
			BetweenExpression<DOMAIN_T> _result = (BetweenExpression<DOMAIN_T>)entityManager.find(BetweenExpression.class, id);
			if (_result != null) {
				return _result;
			}
		}
	
		 return new BetweenExpression<DOMAIN_T>();
	}
	
	public BetweenExpression<DOMAIN_T> fromDto(BetweenExpressionDTO _dto) {
	
		if (_dto == null) {
			return null;
		}
	
		BetweenExpression<DOMAIN_T> _result = createDomainInstance(null);
	
		return convertFromDto(_result, _dto);
	}
	
	public BetweenExpression<DOMAIN_T> convertFromDto(BetweenExpression<DOMAIN_T> _result, BetweenExpressionDTO _dto) {
	
		if (_dto  == null) {
			return null;
		}
	
		PropertyHolderToObjectConverter converterHiValue = getDtoPropertyHolderToObjectConverter((PropertyHolder)_dto.getHiValue());
		if (converterHiValue != null) {
			_result.setHiValue((DOMAIN_T)(Object)converterHiValue.fromDto((PropertyHolder)_dto.getHiValue()));
		}
		_result.setOperation(_dto.getOperation());
		_result.setProperty(_dto.getProperty());
		PropertyHolderToObjectConverter converterValue = getDtoPropertyHolderToObjectConverter((PropertyHolder)_dto.getValue());
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
