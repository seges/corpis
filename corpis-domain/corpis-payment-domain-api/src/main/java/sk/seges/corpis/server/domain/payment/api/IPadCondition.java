package sk.seges.corpis.server.domain.payment.api;

import sk.seges.corpis.server.domain.invoice.server.model.data.OrderData;

public interface IPadCondition {

	boolean apply(OrderData order);

}
