package sk.seges.corpis.pap.converter.hibernate.provider;

import sk.seges.corpis.service.annotation.TransactionPropagationModel;
import sk.seges.sesam.shared.model.converter.provider.AbstractConverterProvider;

public abstract class AbstractTransactionalConverterProvider extends AbstractConverterProvider {

	protected TransactionPropagationModel[] transactionPropagations;

	public void setTransactionPropagations(TransactionPropagationModel[] transactionPropagations) {
		this.transactionPropagations = transactionPropagations;
	}

}
