/**
 *
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sk.seges.corpis.server.domain.invoice.server.model.base.RemittanceBase;
import sk.seges.corpis.server.domain.jpa.JpaPrice;
import sk.seges.corpis.server.domain.server.model.data.PriceData;

/**
 * @author eldzi
 * @since Jul 1, 2007
 */
@Entity
@Table(name = "remittance")//$NON-NLS-1$
@SequenceGenerator(name = "seqRemittances", sequenceName = "SEQ_REMITTANCES", initialValue = 1)//$NON-NLS-1$ //$NON-NLS-2$
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.INTEGER)
@DiscriminatorValue(value="1")
public class JpaRemittanceBase extends RemittanceBase {
	private static final long serialVersionUID = 393743153284623144L;

	private static final byte NAME_LENGTH = 100;

	public JpaRemittanceBase() {
		setDateReceived(new Date());
		setPrepaid(Boolean.FALSE);
	}

	@Column(name="date_received")
	public Date getDateReceived() {
		return super.getDateReceived();
	}

	@Id
	@GeneratedValue(generator = "seqRemittances")//$NON-NLS-1$
	public Long getId() {
		return super.getId();
	}

	@Column(name="name", length = NAME_LENGTH)
	public String getName() {
		return super.getName();
	}

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = PriceData.VALUE, column = @Column(name = "price")),
		@AttributeOverride(name = PriceData.CURRENCY, column = @Column(name = "currency", nullable = false)) })
	public JpaPrice getPrice() {
		return (JpaPrice) super.getPrice();
	}

	@ManyToOne
	public JpaInvoice getInvoice() {
		return (JpaInvoice) super.getInvoice();
	}

	@Column(name="prepaid")
	public Boolean getPrepaid() {
		return super.getPrepaid();
	}
}