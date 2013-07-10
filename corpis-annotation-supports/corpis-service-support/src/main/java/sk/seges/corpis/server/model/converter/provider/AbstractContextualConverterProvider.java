package sk.seges.corpis.server.model.converter.provider;

import sk.seges.corpis.service.annotation.TransactionPropagationModel;
import sk.seges.sesam.shared.model.converter.ConverterProviderContext;
import sk.seges.sesam.shared.model.converter.provider.AbstractConverterProvider;

public abstract class AbstractContextualConverterProvider extends ConverterProviderContext {

    protected TransactionPropagationModel[] transactionPropagations;

    public void setTransactionPropagations(TransactionPropagationModel[] transactionPropagations) {
        this.transactionPropagations = transactionPropagations;
    }
}
