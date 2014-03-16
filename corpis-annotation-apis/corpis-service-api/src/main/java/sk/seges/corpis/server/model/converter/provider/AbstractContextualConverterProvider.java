package sk.seges.corpis.server.model.converter.provider;

import sk.seges.corpis.pap.converter.hibernate.TransactionalConverter;
import sk.seges.corpis.service.annotation.TransactionPropagationModel;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;
import sk.seges.sesam.shared.model.converter.api.DtoConverter;

public abstract class AbstractContextualConverterProvider extends ConverterProviderContext {

    protected TransactionPropagationModel[] transactionPropagations;

    public void setTransactionPropagations(TransactionPropagationModel[] transactionPropagations) {
        this.transactionPropagations = transactionPropagations;
    }

	@Override
	protected <DTO, DOMAIN> DtoConverter<DTO, DOMAIN> initializeConverter(DtoConverter<DTO, DOMAIN> converter) {

		super.initializeConverter(converter);

		if (converter instanceof TransactionalConverter) {
			((TransactionalConverter)converter).setTransactionPropagations(transactionPropagations);
		}

		return converter;
	}

	public abstract AbstractContextualConverterProvider get();
}
