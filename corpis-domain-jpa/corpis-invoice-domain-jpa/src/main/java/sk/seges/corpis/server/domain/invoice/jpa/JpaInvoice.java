package sk.seges.corpis.server.domain.invoice.jpa;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import sk.seges.corpis.server.domain.invoice.server.model.base.InvoiceBase;
import sk.seges.corpis.server.domain.invoice.server.model.data.InvoiceItemData;
import sk.seges.corpis.server.domain.invoice.server.model.data.RemittanceData;
import sk.seges.corpis.server.domain.jpa.JpaPersonCore;
import sk.seges.corpis.shared.domain.invoice.RemittanceType;
import sk.seges.corpis.shared.domain.invoice.TransportType;

@Entity
@SequenceGenerator(name = "seqInvoices", sequenceName = "SEQ_INVOICES", initialValue = 1)//$NON-NLS-1$ //$NON-NLS-2$
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.INTEGER)
@DiscriminatorValue(value="1")
@Table(name = "invoice"/*, uniqueConstraints = {@UniqueConstraint(columnNames = {"invoiceId","prepaid","incomingInvoiceType"})}*/)//$NON-NLS-1$
public class JpaInvoice extends InvoiceBase {

	private static final long serialVersionUID = 7242853578333348764L;

	public JpaInvoice() {
		setInvoiceItems(new HashSet<InvoiceItemData>());
		setRemittances(new HashSet<RemittanceData>());
	}

	@Override
	@Transient
	public Double getFinalPrice() {
		return super.getFinalPrice();
	}

	@Override
	@Column(name = "tax_date")//$NON-NLS-1$
	public Date getTaxDate() {
		return super.getTaxDate();
	}

	@Override
	@Column(name = "payback_date")//$NON-NLS-1$
	public Date getPaybackDate() {
		return super.getPaybackDate();
	}

	@Override
	@Column(name = "creation_date")//$NON-NLS-1$
	public Date getCreationDate() {
		return super.getCreationDate();
	}

	@Override
	@Column(name = "invoice_id", insertable = true, updatable = true) //$NON-NLS-1$
	public String getInvoiceId() {
		return super.getInvoiceId();
	}

	// @ManyToOne
	// public JpaCustomerCore getCustomer() {
	// return (JpaCustomerCore) super.getCustomer();
	// }

	@Override
	@Id
	@GeneratedValue(generator = "seqInvoices")//$NON-NLS-1$
	public Long getId() {
		return super.getId();
	}

	@Override
	@Column(name = "csymbol")//$NON-NLS-1$
	public String getCsymbol() {
		return super.getCsymbol();
	}

	@Override
	@Column(name = "ssymbol")//$NON-NLS-1$
	public String getSsymbol() {
		return super.getSsymbol();
	}

	@Override
	@Column(name = "vsymbol")//$NON-NLS-1$
	public String getVsymbol() {
		return super.getVsymbol();
	}

	@Override
	@Column(name = "prepaid")//$NON-NLS-1$
	public Boolean getPrepaid() {
		return super.getPrepaid();
	}

	@Override
	@Column(name = "incomming_invoice_type")//$NON-NLS-1$
	public Boolean getIncomingInvoiceType() {
		return super.getIncomingInvoiceType();
	}

	@Override
	@OneToMany(mappedBy = "invoice", cascade = { CascadeType.PERSIST }, targetEntity = JpaInvoiceItem.class)//$NON-NLS-1$
	public Set<InvoiceItemData> getInvoiceItems() {
		return super.getInvoiceItems();
	}

	@Override
	@Column(name="penny_balance")
	public Double getPennyBalance() {
		return super.getPennyBalance();
	}

	@Override
	@Version
	public Integer getVersion() {
		return super.getVersion();
	}

	@Override
	@OneToMany(mappedBy = "invoice", cascade = { CascadeType.PERSIST }, targetEntity = JpaRemittanceBase.class) //$NON-NLS-1$
	public Set<RemittanceData> getRemittances() {
		return super.getRemittances();
	}

	@Override
	@ManyToOne
	public JpaPersonCore getCreator() {
		return (JpaPersonCore) super.getCreator();
	}

	@Override
	@Column(name="text")
	public String getInvoiceText() {
		return super.getInvoiceText();
	}

	@Override
	@Column(name = "remittance_type")
	public RemittanceType getRemittanceType() {
		return super.getRemittanceType();
	}

	@Override
	@Column(name = "transport_type")
	public TransportType getTransportType() {
		return super.getTransportType();
	}

	@Override
	@Column(name="add_vat")
	public Boolean getAddVAT() {
		return super.getAddVAT();
	}
	
	@Override
	@Column(name = "payment_date")
	public Date getPaymentDate(){
		return super.getPaymentDate();
	}
}