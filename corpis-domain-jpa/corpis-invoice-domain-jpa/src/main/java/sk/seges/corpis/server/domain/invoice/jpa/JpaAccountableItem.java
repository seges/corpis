/**
 * 
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import sk.seges.corpis.server.domain.invoice.server.model.base.AccountableItemBase;
import sk.seges.corpis.server.domain.jpa.JpaPrice;
import sk.seges.corpis.shared.domain.price.api.ProductUnit;

/**
 * @author eldzi
 */
@MappedSuperclass
public abstract class JpaAccountableItem extends AccountableItemBase {
	
	private static final long serialVersionUID = 366655167960921699L;

	private static final short DESCRIPTION_LENGTH = 250;
	
	public JpaAccountableItem() {
		setBasePrice(new JpaPrice());
	}

	@Override
	@Embedded
	@Column(nullable = false)
	public JpaPrice getBasePrice() {
		return (JpaPrice)super.getBasePrice();
	}

	@Override
	@Column(length = DESCRIPTION_LENGTH)
	public String getDescription() {
		return super.getDescription();
	}

	@Override
	@Column(nullable = false)
	public Float getAmount() {
		return super.getAmount();
	}

	@Override
	@Column
	public Float getWeight() {
		return super.getWeight();
	}

	@Override
	@Column
	@Enumerated(EnumType.STRING)
	public ProductUnit getUnit() {
		return super.getUnit();
	}

	@Override
	@OneToOne
	public JpaVat getVat() {
		return (JpaVat) super.getVat();
	}

	@Override
	@Column
	public String getExtId() {
		return super.getExtId();
	}
}