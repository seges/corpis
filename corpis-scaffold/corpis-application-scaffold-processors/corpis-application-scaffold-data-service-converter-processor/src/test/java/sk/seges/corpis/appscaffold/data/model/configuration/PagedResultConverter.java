package sk.seges.corpis.appscaffold.data.model.configuration;

import javax.persistence.EntityManager;

import sk.seges.corpis.service.annotation.TransactionPropagationModel;
import sk.seges.sesam.dao.PagedResult;
import sk.seges.sesam.shared.model.converter.BasicCachedConverter;
import sk.seges.sesam.shared.model.converter.ConvertedInstanceCache;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;

import java.io.Serializable;

public class PagedResultConverter<DTO_T, DOMAIN_T> extends BasicCachedConverter<PagedResult<DTO_T>, PagedResult<DOMAIN_T>> {

	public PagedResultConverter(EntityManager entityManager, ConverterProviderContext context) {
		setCache(new sk.seges.sesam.shared.model.converter.MapConvertedInstanceCache());
	}

	public void setTransactionPropagations(TransactionPropagationModel[] transactionPropagations) {}

    @Override
    public PagedResult<DOMAIN_T> createDomainInstance(Serializable dtoId) {
        return null;
    }

    @Override
	public PagedResult<DTO_T> convertToDto(PagedResult<DTO_T> result, PagedResult<DOMAIN_T> domain) {
		return null;
	}

	@Override
	public PagedResult<DTO_T> toDto(PagedResult<DOMAIN_T> domain) {
		return null;
	}

	@Override
	public PagedResult<DOMAIN_T> convertFromDto(PagedResult<DOMAIN_T> result, PagedResult<DTO_T> dto) {
		return null;
	}

	@Override
	public PagedResult<DOMAIN_T> fromDto(PagedResult<DTO_T> dto) {
		return null;
	}

	@Override
	public boolean equals(Object domain, Object dto) {
		return false;
	}

	@Override
	public PagedResult<DTO_T> createDtoInstance(Serializable id) {
		return null;
	}
}