package sk.seges.sesam.server.domain.converter;

import sk.seges.corpis.pap.converter.hibernate.TransactionalConverter;
import sk.seges.sesam.dao.Page;
import sk.seges.sesam.dao.PagedResult;
import sk.seges.sesam.server.model.dto.configuration.PageDTOConverter;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;
import sk.seges.sesam.shared.model.converter.api.DtoConverter;
import sk.seges.sesam.shared.model.converter.api.InstantiableDtoConverter;
import sk.seges.sesam.shared.model.dto.PageDTO;
import sk.seges.sesam.shared.model.dto.PagedResultDTO;
import sk.seges.sesam.utils.CastUtils;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class PagedResultDTOConverter<DTO_T, DOMAIN_T> extends TransactionalConverter<PagedResultDTO<DTO_T>, PagedResult<DOMAIN_T>> {
	
	protected final EntityManager entityManager;
	
	protected final ConverterProviderContext converterProviderContext;
	 
	public PagedResultDTOConverter(EntityManager entityManager, ConverterProviderContext converterProviderContext) {
		super();
		this.entityManager = entityManager;
		this.converterProviderContext = converterProviderContext;
	}
	 
	public boolean equals(PagedResult<DOMAIN_T> _domain, PagedResultDTO<DTO_T> _dto) {
		if (_domain.getPage() == null) {
			if (_dto.getPage() != null)
				return false;
		} else if (!_domain.getPage().equals(_dto.getPage()))
			return false;
		DtoConverter<DTO_T, DOMAIN_T> converterResult = ((DtoConverter<DTO_T, DOMAIN_T>)(DtoConverter)converterProviderContext.getConverterForDomain(_domain.getResult()));
		if (converterResult != null && !converterResult.equals(_domain.getResult(),_dto.getResult()))
			return false;
		if (_domain.getTotalResultCount() != _dto.getTotalResultCount())
			return false;
		return true;
	}
	
	public PagedResultDTO<DTO_T> createDtoInstance(Serializable id) {
		PagedResultDTO<DTO_T> _result = new PagedResultDTO<DTO_T>();
		return _result;
	}
	
	public PagedResultDTO<DTO_T> toDto(PagedResult<DOMAIN_T> _domain) {
	
		if (_domain  == null) {
			return null;
		}
	
		PagedResultDTO<DTO_T> _result = createDtoInstance(null);
		return convertToDto(_result, _domain);
	}
	
	public PagedResultDTO<DTO_T> convertToDto(PagedResultDTO<DTO_T> _result, PagedResult<DOMAIN_T> _domain) {
	
		if (_domain  == null) {
			return null;
		}
	
		InstantiableDtoConverter<PageDTO, Page> converterPage = ((PageDTOConverter)(DtoConverter)converterProviderContext.getConverterForDomain(_domain.getPage()));
		if (converterPage != null) {
			_result.setPage(converterPage.toDto(CastUtils.cast((Page)_domain.getPage(), Page.class)));
		}
		DtoConverter<DTO_T, DOMAIN_T> converterResult = ((DtoConverter<DTO_T, DOMAIN_T>)(DtoConverter)converterProviderContext.getConverterForDomain(_domain.getResult()));
		if (converterResult != null) {
			_result.setResult(converterResult.toDto(_domain.getResult()));
		} else {
			_result.setResult((DTO_T)_domain.getResult());
		}
		_result.setTotalResultCount(_domain.getTotalResultCount());
		return _result;
	}
	
	public PagedResult<DOMAIN_T> createDomainInstance(Serializable id) {
		if (id != null) {
			PagedResult<DOMAIN_T> _result = (PagedResult<DOMAIN_T>)entityManager.find(PagedResult.class, id);
			if (_result != null) {
				return _result;
			}
		}
	
		 return new PagedResult<DOMAIN_T>();
	}
	
	public PagedResult<DOMAIN_T> fromDto(PagedResultDTO<DTO_T> _dto) {
	
		if (_dto == null) {
			return null;
		}
	
		PagedResult<DOMAIN_T> _result = createDomainInstance(null);
	
		return convertFromDto(_result, _dto);
	}
	
	public PagedResult<DOMAIN_T> convertFromDto(PagedResult<DOMAIN_T> _result, PagedResultDTO<DTO_T> _dto) {
	
		if (_dto  == null) {
			return null;
		}
	
		PageDTOConverter converterPage = ((PageDTOConverter)(DtoConverter)converterProviderContext.getConverterForDto(_dto.getPage()));
		if (converterPage != null) {
			_result.setPage((Page)converterPage.fromDto((PageDTO)_dto.getPage()));
		}
		DtoConverter<DTO_T, DOMAIN_T> converterResult = ((DtoConverter<DTO_T, DOMAIN_T>)(DtoConverter)converterProviderContext.getConverterForDto(_dto.getResult()));
		if (converterResult != null) {
			_result.setResult(converterResult.fromDto(_dto.getResult()));
		} else {
			_result.setResult((DOMAIN_T)_dto.getResult());
		}
		_result.setTotalResultCount(_dto.getTotalResultCount());
		return _result;
	}
}
