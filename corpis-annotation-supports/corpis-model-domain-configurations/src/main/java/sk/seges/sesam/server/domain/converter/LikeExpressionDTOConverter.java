package sk.seges.sesam.server.domain.converter;

import sk.seges.corpis.pap.converter.hibernate.TransactionalConverter;
import sk.seges.sesam.dao.LikeExpression;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.shared.model.api.PropertyHolder;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;
import sk.seges.sesam.shared.model.dto.LikeExpressionDTO;
import sk.seges.sesam.shared.model.dto.SimpleExpressionDTO;
import sk.seges.sesam.utils.CastUtils;

import javax.annotation.Generated;
import javax.persistence.EntityManager;
import java.io.Serializable;

@SuppressWarnings("serial")
@TransferObjectMapping(dtoClass = LikeExpressionDTO.class,
		domainClassName = "sk.seges.sesam.dao.LikeExpression", 
		configurationClassName = "sk.seges.sesam.server.model.dto.configuration.LikeExpressionDTOConfiguration", 
		generateConverter = false, generateDto = false, 
		converterClassName = "sk.seges.sesam.server.model.dto.configuration.LikeExpressionDTOConverter")
@Generated(value = "sk.seges.corpis.pap.model.hibernate.HibernateTransferObjectDataConverterProcessor")
public class LikeExpressionDTOConverter<DOMAIN_T extends Comparable<? extends Serializable>> extends TransactionalConverter<LikeExpressionDTO, LikeExpression<DOMAIN_T>> {
	
	protected final EntityManager entityManager;
	
	protected final ConverterProviderContext converterProviderContext;
	 
	public LikeExpressionDTOConverter(EntityManager entityManager, ConverterProviderContext converterProviderContext) {
		super();
		this.entityManager = entityManager;
		this.converterProviderContext = converterProviderContext;
	}
	 
	public boolean equals(LikeExpression<DOMAIN_T> _domain, LikeExpressionDTO _dto) {
		if (_domain.isCaseSensitive() != _dto.isCaseSensitive())
			return false;
		if (_domain.getMode() != _dto.getMode())
			return false;
		if (_domain.getValue() == null) {
			if (_dto.getValue() != null)
				return false;
		} else if (!_domain.getValue().equals(_dto.getValue()))
			return false;
		return true;
	}
	
	public LikeExpressionDTO createDtoInstance(Serializable id) {
		LikeExpressionDTO _result = new LikeExpressionDTO();
		return _result;
	}
	
	public LikeExpressionDTO toDto(LikeExpression<DOMAIN_T> _domain) {
	
		if (_domain  == null) {
			return null;
		}
	
		LikeExpressionDTO _result = createDtoInstance(null);
		return convertToDto(_result, _domain);
	}
	
	public LikeExpressionDTO convertToDto(LikeExpressionDTO _result, LikeExpression<DOMAIN_T> _domain) {
	
		if (_domain  == null) {
			return null;
		}
	
		converterProviderContext.getConverterForDto(SimpleExpressionDTO.class).convertToDto(_result, _domain);
	
		_result.setCaseSensitive(_domain.isCaseSensitive());
		_result.setMode(_domain.getMode());
		PropertyHolderToObjectConverter converterValue = getDomainPropertyHolderToObjectConverter((Object)_domain.getValue());
		if (converterValue != null) {
			_result.setValue(converterValue.toDto(CastUtils.cast((Object)_domain.getValue(), Object.class)));
		}
		return _result;
	}
	
	public LikeExpression<DOMAIN_T> createDomainInstance(Serializable id) {
		if (id != null) {
			LikeExpression<DOMAIN_T> _result = (LikeExpression<DOMAIN_T>)entityManager.find(LikeExpression.class, id);
			if (_result != null) {
				return _result;
			}
		}
	
		 return new LikeExpression<DOMAIN_T>();
	}
	
	public LikeExpression<DOMAIN_T> fromDto(LikeExpressionDTO _dto) {
	
		if (_dto == null) {
			return null;
		}
	
		LikeExpression<DOMAIN_T> _result = createDomainInstance(null);
	
		return convertFromDto(_result, _dto);
	}
	
	public LikeExpression<DOMAIN_T> convertFromDto(LikeExpression<DOMAIN_T> _result, LikeExpressionDTO _dto) {
	
		if (_dto  == null) {
			return null;
		}
	
		converterProviderContext.getConverterForDto(SimpleExpressionDTO.class).convertFromDto(_result, _dto);

		_result.setCaseSensitive(_dto.isCaseSensitive());
		_result.setMode(_dto.getMode());
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
