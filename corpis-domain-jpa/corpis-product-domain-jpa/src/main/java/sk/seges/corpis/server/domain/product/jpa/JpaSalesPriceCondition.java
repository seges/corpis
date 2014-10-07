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
	public static final String DEFAUL_NAME = "SalesPriceCondition_product_discount";

	private Date validFrom;
	private Date validTo;
	private String color;
	private Boolean active;
	private Boolean activeForWeb;
	private Boolean deleted;
	private Long extId;
	private String productExtId;
	private String productExternalId;

	public JpaSalesPriceCondition() {}

	public JpaSalesPriceCondition(Date validFrom, Date validTo, String color, Boolean active, Boolean activeForWeb, Boolean deleted, Long extId, String productExtId) {
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.color = color;
		this.active = active;
		this.activeForWeb = activeForWeb;
		this.deleted = deleted;
		this.extId = extId;
		this.productExtId = productExtId;
	}

	@Override
	@Column(name = VALID_FROM)
	public Date getValidFrom() {
		return validFrom;
	}

	@Override
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	@Override
	@Column(name = VALID_TO)
	public Date getValidTo() {
		return validTo;
	}

	@Override
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	@Override
	@Column(name = COLOR)
	public String getColor() {
		if (color == null) {
			return DEFAUL_SALES_COLOR;
		}
		return color;
	}

	@Override
	public void setColor(String color) {
		this.color = color;
	}

	@Override
	@Column(name = ACTIVE)
	public Boolean getActive() {
		return active;
	}

	@Override
	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	@Column(name = ACTIVE_FOR_WEB)
	public Boolean getActiveForWeb() {
		return activeForWeb;
	}

	@Override
	public void setActiveForWeb(Boolean activeForWeb) {
		this.activeForWeb = activeForWeb;
	}

	@Override
	@Column(name = DELETED)
	public Boolean getDeleted() {
		return deleted;
	}
	
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	@NotNull
	@Column(name = EXT_ID)
	public Long getExtId() {
		return extId;
	}

	@Override
	public void setExtId(Long extId) {
		this.extId = extId;
	}

	@Override
	@Transient
	public String getProductExtId() {
		return productExtId;
	}
	
	@Override
	public void setProductExtId(String productExtId) {
		this.productExtId = productExtId;
	}

	@Override
	@Transient
	public String getDefaultName() {
		return DEFAUL_NAME;
	}

	@Override
	public boolean applies(PriceConditionContext context, String webId, CustomerCoreData customer, ProductData product) {
		return super.applies(context, webId, customer, product) && ((getActiveForWeb() == null && getActive() != null && getActive()) || (getActiveForWeb() != null && getActiveForWeb()));
	}

	@Override
	@Transient
	public String getProductExternalId() {		
		return productExternalId;
	}

	@Override
	public void setProductExternalId(String productExternalId) {
		this.productExternalId = productExternalId;		
	}
}