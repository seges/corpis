package sk.seges.corpis.server.domain.invoice.hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Immutable;

import sk.seges.corpis.server.domain.invoice.jpa.JpaWebIDAwareOrder;

@Entity
@Table(name = "webid_aware_order")
@Immutable
public class HibernateWebIDAwareOrderView extends JpaWebIDAwareOrder{

	private String customerName;
		
	@Formula("(case when customer_companyname is not null then customer_companyname else customer_surname end)")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Override
	@Transient
	public Class<?> getSecuredClass() {
		return JpaWebIDAwareOrder.class;
	}
}
