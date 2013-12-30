/**
 * 
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import java.util.Date;

import javax.persistence.*;

import sk.seges.corpis.server.domain.customer.jpa.JpaCustomerCore;
import sk.seges.corpis.server.domain.invoice.server.model.base.AccountableBase;
import sk.seges.corpis.server.domain.jpa.JpaCurrency;
import sk.seges.sesam.security.shared.domain.ISecuredObject;

/**
 * @author eldzi
 */
@MappedSuperclass
public abstract class JpaAccountable extends AccountableBase {

	private static final long serialVersionUID = 7608869409434126440L;

	@ManyToOne(fetch = FetchType.LAZY)
	public JpaCustomerCore getCustomer() {
		return (JpaCustomerCore) super.getCustomer();
	}

	@Column(name = "creation_date")
	public Date getCreationDate() {
		return super.getCreationDate();
	}

	@ManyToOne
	public JpaCurrency getCurrency() {
		return (JpaCurrency) super.getCurrency();
	}

	@Column
	@Override
	public String getProcessId() {
		return super.getProcessId();
	}

	@Override
	@Transient
	public Long getIdForACL() {
		return getId();
	}

	@Override
	@Transient
	public ISecuredObject<?> getParent() {
		return null;
	}
}