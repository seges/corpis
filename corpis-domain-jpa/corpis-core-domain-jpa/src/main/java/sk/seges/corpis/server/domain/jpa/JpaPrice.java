/**
 * 
 */
package sk.seges.corpis.server.domain.jpa;

import sk.seges.corpis.server.domain.server.model.base.PriceBase;
import sk.seges.corpis.server.domain.server.model.data.CurrencyData;
import sk.seges.corpis.server.domain.server.model.data.PriceData;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author eldzi
 */
@Embeddable
public class JpaPrice extends PriceBase {
	private static final long serialVersionUID = -1630656583722890660L;
	
	public JpaPrice() {
		setValue(new BigDecimal(0));
	}
	
	public JpaPrice(BigDecimal value) {
		setValue(value);
	}
	
	@NotNull
	@Column(nullable = false, precision = 50, scale = 20)
	public BigDecimal getValue() {
		return super.getValue();
	}

	@NotNull
	@ManyToOne(targetEntity = JpaCurrency.class)
	public CurrencyData getCurrency() {
		return super.getCurrency();
	}

	@Override
	public PriceData clone() {
		PriceData newPrice = new JpaPrice();

		newPrice.setCurrency(getCurrency());
		newPrice.setValue(getValue());

		return newPrice;
	}
}
