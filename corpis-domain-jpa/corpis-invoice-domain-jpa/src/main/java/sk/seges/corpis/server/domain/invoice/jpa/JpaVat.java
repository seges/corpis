/**
 * 
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import sk.seges.corpis.server.domain.server.model.base.VatBase;
import sk.seges.sesam.domain.IDomainObject;

/**
 * @author eldzi
 * @since Apr 15, 2007
 */
@Entity
@Table(name = "vat")//$NON-NLS-1$
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.INTEGER)
@DiscriminatorValue(value="1")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "vat")
public class JpaVat extends VatBase implements IDomainObject<Short> {

	private static final long serialVersionUID = 4895994133127568395L;
	
	public JpaVat() {
		setVat((short) 0);
		setValidFrom(new Date());
	}

	@Override
	public Short getId() {
		return getVat();
	}
		
	@Id
	public Short getVat() {
		return super.getVat();
	}

	@Column(name="valid_from")
	public Date getValidFrom() {
		return super.getValidFrom();
	}
	
	@Override
	@Column(name="default_vat")
	public Boolean getDefaultVat() {
		return super.getDefaultVat();
	}
}