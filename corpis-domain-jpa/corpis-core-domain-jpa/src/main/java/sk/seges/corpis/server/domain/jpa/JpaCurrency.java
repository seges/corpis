package sk.seges.corpis.server.domain.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import sk.seges.corpis.server.domain.server.model.base.CurrencyBase;
import sk.seges.corpis.server.domain.server.model.data.CurrencyData;

@Entity
@Table(name = "currency", uniqueConstraints = @UniqueConstraint(columnNames = {CurrencyData.CODE, CurrencyData.WEB_ID}))
public class JpaCurrency extends CurrencyBase {
	
	private static final long serialVersionUID = -7897429807056078485L;

	public static final String CODE = "code";

	public JpaCurrency() {}

	public JpaCurrency(String code, String webId) {
		setCode(code);
		setWebId(webId);
	}

	@Override
	@Id
	public Short getId() {
		return super.getId();
	}

	/**
	 * @return 3-letter code as defined in ISO 4217
	 */
	@Override
	@Column(length = 3)
	public String getCode() {
		return super.getCode();
	}
	
	@Override
	@Column
	public String getWebId() {
		return super.getWebId();
	}

	@Override
	public CurrencyData clone() {
		CurrencyData newCurrency = new JpaCurrency();

		newCurrency.setCode(getCode());
		newCurrency.setId(getId());
		newCurrency.setWebId(getWebId());

		return newCurrency;
	}

}
