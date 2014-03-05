package sk.seges.corpis.pap.model.configuration.configuration;
import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.EntityManager;

import sk.seges.corpis.pap.converter.hibernate.TransactionalConverter;
import sk.seges.corpis.pap.model.converter.MockStringConverter;
import sk.seges.corpis.service.annotation.TransactionPropagationModel;
import sk.seges.sesam.dao.Page;
import sk.seges.sesam.shared.model.dao.SortInfo;
import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.server.model.converter.common.CollectionConverter;
import sk.seges.sesam.shared.model.converter.ConvertedInstanceCache;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;
import sk.seges.sesam.shared.model.converter.api.DtoConverter;
import sk.seges.sesam.utils.CastUtils;

@SuppressWarnings("serial")
@TransferObjectMapping(dtoClass = Page.class,
		domainClassName = "sk.seges.sesam.dao.Page",
		configurationClassName = "sk.seges.corpis.pap.model.configuration.configuration.MockPageConfiguration",
		generateConverter = false, generateDto = false,
		converterClassName = "sk.seges.corpis.pap.model.configuration.configuration.MockPageConverter")
@Generated(value = "sk.seges.corpis.pap.model.hibernate.HibernateTransferObjectConverterProcessor")
public class MockPageConverter extends TransactionalConverter<Page, Page> {

	protected final EntityManager entityManager;

	protected final ConverterProviderContext converterProviderContext;

	protected TransactionPropagationModel[] transactionPropagations;

	protected ConvertedInstanceCache cache;

	public MockPageConverter(EntityManager entityManager, ConverterProviderContext converterProviderContext) {
		super();
		this.entityManager = entityManager;
		this.converterProviderContext = converterProviderContext;
	}

	public void setTransactionPropagations(TransactionPropagationModel[] transactionPropagations) {
		this.transactionPropagations = transactionPropagations;
	}

	public void setCache(ConvertedInstanceCache cache) {
		this.cache = cache;
	}

	public boolean equals(Object _domainArg, Object _dtoArg) {

		if (_domainArg == null) {
			return (_dtoArg == null);
		}

		if (_dtoArg == null) {
			return false;
		}

		if (!(_domainArg instanceof Page)) {
			return false;
		}

		Page _domain = (Page)_domainArg;

		if (!(_dtoArg instanceof Page)) {
			return false;
		}

		Page _dto = (Page)_dtoArg;

		if (_domain.getFilterable() == null) {
			if (_dto.getFilterable() != null)
				return false;
		} else if (!_domain.getFilterable().equals(_dto.getFilterable()))
			return false;
		if (_domain.getPageSize() != _dto.getPageSize())
			return false;
		if (_domain.getProjectableResult() == null) {
			if (_dto.getProjectableResult() != null)
				return false;
		} else if (!_domain.getProjectableResult().equals(_dto.getProjectableResult()))
			return false;
		if (_domain.getProjectables() == null) {
			if (_dto.getProjectables() != null)
				return false;
		} else if (!_domain.getProjectables().equals(_dto.getProjectables()))
			return false;
		if (_domain.getSortables() == null) {
			if (_dto.getSortables() != null)
				return false;
		} else if (!_domain.getSortables().equals(_dto.getSortables()))
			return false;
		if (_domain.getStartIndex() != _dto.getStartIndex())
			return false;
		return true;
	}

	public Page createDtoInstance(Serializable id) {
		Page _result = new Page();
		return _result;
	}

	public Page toDto(Page _domain) {

		if (_domain  == null) {
			return null;
		}

		Page _result = createDtoInstance(null);
		return convertToDto(_result, _domain);
	}

	public Page convertToDto(Page _result, Page _domain) {

		if (_domain  == null) {
			return null;
		}

		_result.setFilterable(_domain.getFilterable());
		_result.setPageSize(_domain.getPageSize());
		MockStringConverter converterProjectableResult = getDomainMockStringConverter(_domain.getProjectableResult());
		if (converterProjectableResult != null) {
			_result.setProjectableResult(converterProjectableResult.toDto(CastUtils.cast((String)_domain.getProjectableResult(), String.class)));
		}
		CollectionConverter<String, String> converterProjectables = ((CollectionConverter<String, String>)(DtoConverter)converterProviderContext.getConverterForDomain(_domain.getProjectables()));
		if (converterProjectables != null) {
			_result.setProjectables(converterProjectables.toDto(CastUtils.cast((List<?>)_domain.getProjectables(), String.class), java.util.ArrayList.class));
		}
		CollectionConverter<SortInfo, SortInfo> converterSortables = ((CollectionConverter<SortInfo, SortInfo>)(DtoConverter)converterProviderContext.getConverterForDomain(_domain.getSortables()));
		if (converterSortables != null) {
			_result.setSortables(converterSortables.toDto(CastUtils.cast((List<?>)_domain.getSortables(), SortInfo.class), java.util.ArrayList.class));
		}
		_result.setStartIndex(_domain.getStartIndex());
		return _result;
	}

	public Page createDomainInstance(Serializable id) {
		if (id != null) {
			Page _result = (Page)entityManager.find(Page.class, id);
			if (_result != null) {
				return _result;
			}
		}

		return new Page();
	}

	public Page fromDto(Page _dto) {

		if (_dto == null) {
			return null;
		}

		Page _result = createDomainInstance(null);

		return convertFromDto(_result, _dto);
	}

	public Page convertFromDto(Page _result, Page _dto) {

		if (_dto  == null) {
			return null;
		}

		_result.setFilterable(_dto.getFilterable());
		_result.setPageSize(_dto.getPageSize());
		MockStringConverter converterProjectableResult = getDtoMockStringConverter(_dto.getProjectableResult());
		if (converterProjectableResult != null) {
			_result.setProjectableResult((String)converterProjectableResult.fromDto((String)_dto.getProjectableResult()));
		}
		CollectionConverter<String, String> converterProjectables = ((CollectionConverter<String, String>)(DtoConverter)converterProviderContext.getConverterForDto(_dto.getProjectables()));
		if (converterProjectables != null) {
			_result.setProjectables(CastUtils.cast((List<?>)converterProjectables.fromDto(CastUtils.cast(_dto.getProjectables(), String.class)), String.class));
		}
		CollectionConverter<SortInfo, SortInfo> converterSortables = ((CollectionConverter<SortInfo, SortInfo>)(DtoConverter)converterProviderContext.getConverterForDto(_dto.getSortables()));
		if (converterSortables != null) {
			_result.setSortables(CastUtils.cast((List<?>)converterSortables.fromDto(CastUtils.cast(_dto.getSortables(), SortInfo.class)), SortInfo.class));
		}
		_result.setStartIndex(_dto.getStartIndex());
		return _result;
	}

	protected MockStringConverter getDomainMockStringConverter(String obj){
		return new MockStringConverter(converterProviderContext);
	}

	protected MockStringConverter getDtoMockStringConverter(String obj){
		return new MockStringConverter(converterProviderContext);
	}
}
