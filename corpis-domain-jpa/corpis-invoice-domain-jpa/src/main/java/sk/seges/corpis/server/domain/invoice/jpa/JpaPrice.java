/**
 * 
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import sk.seges.corpis.server.domain.invoice.server.model.base.PriceBase;
import sk.seges.corpis.server.domain.invoice.server.model.data.CurrencyData;

/**
 * @author eldzi
 */
@Embeddable
public class JpaPrice extends PriceBase {
	private static final long serialVersionUID = -1630656583722890660L;
	
	@Column(nullable = false)
	public BigDecimal getValue() {
		return super.getValue();
	}

	@ManyToOne(targetEntity = JpaCurrency.class)
	public CurrencyData getCurrency() {
		return super.getCurrency();
	}	
}
