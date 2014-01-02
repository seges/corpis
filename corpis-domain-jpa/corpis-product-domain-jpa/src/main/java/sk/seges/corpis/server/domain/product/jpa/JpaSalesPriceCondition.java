package sk.seges.corpis.server.domain.product.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import sk.seges.corpis.server.domain.customer.server.model.data.CustomerCoreData;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;
import sk.seges.corpis.server.domain.product.server.model.data.SalesPriceConditionData;
import sk.seges.corpis.shared.domain.price.api.PriceConditionContext;

@Entity
@DiscriminatorValue("4")
@Table( uniqueConstraints = { @UniqueConstraint(columnNames = { SalesPriceConditionData.EXT_ID, SalesPriceConditionData.PRODUCT })})
public class JpaSalesPriceCondition extends JpaPriceCondition implements SalesPriceConditionData {
	
	private static final long serialVersionUID = -3413508898223159392L;

	public static final String DEFAUL_SALES_COLOR = "#FF6464";

	private Date validFrom;
	private Date validTo;
	private String salesName;
	private String color;
	private Boolean active;
	private Boolean activeForWeb;
	private Boolean deleted;
	private Long extId;
	private String productExtId;

	public JpaSalesPriceCondition() {}

	public JpaSalesPriceCondition(Date validFrom, Date validTo, String salesName, String color, Boolean active, Boolean activeForWeb, Boolean deleted,
							   Long extId, String productExtId) {
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.salesName = salesName;
		this.color = color;
		this.active = active;
		this.activeForWeb = activeForWeb;
		this.deleted = deleted;
		this.extId = extId;
		this.productExtId = productExtId;
	}

	@Column(name = VALID_FROM)
	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	@Column(name = VALID_TO)
	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	@Column(name = SALES_NAME)
	public String getSalesName() {
		return salesName;
	}
	
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	@Column(name = COLOR)
	public String getColor() {
		if (color == null) {
			return DEFAUL_SALES_COLOR;
		}
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name = ACTIVE)
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name = ACTIVE_FOR_WEB)
	public Boolean getActiveForWeb() {
		return activeForWeb;
	}

	public void setActiveForWeb(Boolean activeForWeb) {
		this.activeForWeb = activeForWeb;
	}
	
	@Column(name = DELETED) 
	public Boolean getDeleted() {
		return deleted;
	}
	
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@NotNull
	@Column(name = EXT_ID)
	public Long getExtId() {
		return extId;
	}
	
	public void setExtId(Long extId) {
		this.extId = extId;
	}
	
	@Override
	@Transient
	public String getName() {
		return getSalesName();
	}
	
	@Transient
	public String getProductExtId() {
		return productExtId;
	}
	
	public void setProductExtId(String productExtId) {
		this.productExtId = productExtId;
	}

	@Override
	public boolean applies(PriceConditionContext context, String webId, CustomerCoreData customer, ProductData product) {
		return super.applies(context, webId, customer, product) && ((getActiveForWeb() == null && getActive()) || getActiveForWeb());
	}
}