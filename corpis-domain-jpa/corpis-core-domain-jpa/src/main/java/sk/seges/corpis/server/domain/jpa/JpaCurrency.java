package sk.seges.corpis.server.domain.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import sk.seges.corpis.server.domain.server.model.base.CurrencyBase;
import sk.seges.corpis.server.domain.server.model.data.CurrencyData;

@Entity
@Table(name = "currency")
public class JpaCurrency extends CurrencyBase {
	
	private static final long serialVersionUID = -7897429807056078485L;

	public static final String CODE = "code";

	public JpaCurrency() {}

	public JpaCurrency(String code) {
		setCode(code);
	}

	@Id
	public Short getId() {
		return super.getId();
	}

	/**
	 * @return 3-letter code as defined in ISO 4217
	 */
	@Column(unique = true, length = 3)
	public String getCode() {
		return super.getCode();
	}

	@Override
	public CurrencyData clone() {
		CurrencyData newCurrency = new JpaCurrency();

		newCurrency.setCode(getCode());
		newCurrency.setId(getId());

		return newCurrency;
	}

}
