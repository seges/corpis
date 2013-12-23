package sk.seges.corpis.pap.converter.hibernate;

import sk.seges.corpis.service.annotation.TransactionPropagationModel;
import sk.seges.sesam.shared.model.converter.BasicCachedConverter;

public abstract class TransactionalConverter<DTO, DOMAIN> extends BasicCachedConverter<DTO, DOMAIN> {

	protected TransactionPropagationModel[] transactionPropagations;

	public void setTransactionPropagations(TransactionPropagationModel[] transactionPropagations) {
		this.transactionPropagations = transactionPropagations;
	}
}