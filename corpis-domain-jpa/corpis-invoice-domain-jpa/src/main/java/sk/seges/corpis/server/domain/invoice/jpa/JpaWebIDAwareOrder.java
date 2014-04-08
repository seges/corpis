/**
 * 
 */
package sk.seges.corpis.server.domain.invoice.jpa;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

import sk.seges.corpis.server.domain.customer.jpa.JpaAddress;
import sk.seges.corpis.server.domain.customer.jpa.JpaBasicContact;
import sk.seges.corpis.server.domain.customer.jpa.JpaCompanyName;
import sk.seges.corpis.server.domain.customer.jpa.JpaCustomerCore;
import sk.seges.corpis.server.domain.invoice.server.model.data.AccountableItemData;
import sk.seges.corpis.server.domain.invoice.server.model.data.DeliveryPersonData;
import sk.seges.corpis.server.domain.invoice.server.model.data.LoyaltyCardData;
import sk.seges.corpis.server.domain.invoice.server.model.data.OrderData;
import sk.seges.corpis.server.domain.invoice.server.model.data.OrderItemData;
import sk.seges.corpis.server.domain.jpa.JpaPersonName;
import sk.seges.corpis.server.domain.server.model.data.AddressData;
import sk.seges.corpis.server.domain.server.model.data.BasicContactData;
import sk.seges.corpis.server.domain.server.model.data.CompanyNameData;
import sk.seges.corpis.server.domain.server.model.data.PersonNameData;
import sk.seges.corpis.shared.domain.HasWebId;
import sk.seges.corpis.shared.domain.customer.ECustomerDiscountType;
import sk.seges.sesam.security.shared.domain.ISecuredObject;

/**
 * @author eldzi
 */
@Entity
@Table(name = "webid_aware_order", uniqueConstraints = @UniqueConstraint(columnNames = { JpaWebIDAwareOrder.WEB_ID, JpaOrderCore.ORDER_ID }) )
@SequenceGenerator(name = JpaWebIDAwareOrder.SEQ_ORDERS, sequenceName = "seq_orders", initialValue = 1)
public class JpaWebIDAwareOrder extends JpaOrderCore implements HasWebId, OrderData {
	private static final long serialVersionUID = 5948016788551732181L;
	
	protected static final String SEQ_ORDERS = "seqOrders";

	public static final String WEB_ID = "webId";

	private Long id;
	private String webId;
	private List<OrderItemData> orderItems;
	private LoyaltyCardData loyaltyCard;
	private ECustomerDiscountType orderType;
	private BigDecimal discountValue;

	@Override
	@Id
	@GeneratedValue(generator = SEQ_ORDERS)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public String getWebId() {
		return webId;
	}

	public void setWebId(String webId) {
		this.webId = webId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order", targetEntity = JpaWebIDAwareOrderItem.class)
	public List<OrderItemData> getOrderItems() {
		return orderItems;
	}

	@Override
	public void setOrderItems(List<? extends OrderItemData> orderItems) {
		this.orderItems = (List<OrderItemData>) orderItems;
	}

	@ManyToOne(targetEntity = JpaLoyaltyCard.class)
	@JoinColumn(name = "loyalty_card_fk")
	public LoyaltyCardData getLoyaltyCard() {
		return loyaltyCard;
	}
	
	public void setLoyaltyCard(LoyaltyCardData loyaltyCard) {
		this.loyaltyCard = loyaltyCard;
	}
	
	@Column
	@Enumerated(EnumType.STRING)
	public ECustomerDiscountType getOrderType() {
		return orderType;
	}
	
	public void setOrderType(ECustomerDiscountType orderType) {
		this.orderType = orderType;
	}
	
	@Column
	public BigDecimal getDiscountValue() {
		return discountValue;
	}
	
	public void setDiscountValue(BigDecimal discountValue) {
		this.discountValue = discountValue;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((webId == null) ? 0 : webId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaWebIDAwareOrder other = (JpaWebIDAwareOrder) obj;
		if (webId == null) {
			if (other.webId != null)
				return false;
		} else if (!webId.equals(other.webId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WebIDAwareOrder [id=" + id + ", webId=" + webId + ", toString()=" + super.toString() + "]";
	}

	@Override
	@Transient
	public Long getIdForACL() {
		return getId();
	}

	@Override
	@Transient
	public ISecuredObject<?> getParent() {
		return null;
	}

	@Override
	@Transient
	public Class<?> getSecuredClass() {
		return getClass();
	}

	@Override
	public List<? extends AccountableItemData> fetchAccountableItems() {
		return orderItems;
	}

	@Embedded
	@AttributeOverrides( {
			@AttributeOverride(name = AddressData.STREET, column = @Column(name = JpaDeliveryPerson.TABLE_PREFIX + AddressData.STREET)),
			@AttributeOverride(name = AddressData.CITY, column = @Column(name = JpaDeliveryPerson.TABLE_PREFIX + AddressData.CITY)),
			@AttributeOverride(name = AddressData.COUNTRY, column = @Column(name = JpaDeliveryPerson.TABLE_PREFIX + AddressData.COUNTRY)),
			@AttributeOverride(name = AddressData.STATE, column = @Column(name = JpaDeliveryPerson.TABLE_PREFIX + AddressData.STATE)),
			@AttributeOverride(name = AddressData.ZIP, column = @Column(name = JpaDeliveryPerson.TABLE_PREFIX + AddressData.ZIP)) })
	public JpaAddress getDeliveryAddress() {
		return (JpaAddress) super.getDeliveryAddress();
	}

	@Embedded
	@Valid
	@AttributeOverrides( {
			@AttributeOverride(name = AddressData.STREET, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + AddressData.STREET)),
			@AttributeOverride(name = AddressData.CITY, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + AddressData.CITY)),
			@AttributeOverride(name = AddressData.STATE, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + AddressData.STATE)),
			@AttributeOverride(name = AddressData.ZIP, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + AddressData.ZIP)),
			@AttributeOverride(name = AddressData.COUNTRY, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + AddressData.COUNTRY))})
	@AssociationOverride(name = AddressData.COUNTRY, joinColumns = @JoinColumn(name = JpaCustomerCore.TABLE_PREFIX
			+ AddressData.COUNTRY + "_id"))
	public JpaAddress getCustomerAddress() {
		return (JpaAddress) super.getCustomerAddress();
	}

	@Embedded
	@AttributeOverrides( {
			@AttributeOverride(name = BasicContactData.PHONE, column = @Column(name = JpaDeliveryPerson.TABLE_PREFIX + BasicContactData.PHONE)),
			@AttributeOverride(name = BasicContactData.FAX, column = @Column(name = JpaDeliveryPerson.TABLE_PREFIX + BasicContactData.FAX)),
			@AttributeOverride(name = BasicContactData.EMAIL, column = @Column(name = JpaDeliveryPerson.TABLE_PREFIX + BasicContactData.EMAIL)),
			@AttributeOverride(name = BasicContactData.MOBILE, column = @Column(name = JpaDeliveryPerson.TABLE_PREFIX + BasicContactData.MOBILE)),
			@AttributeOverride(name = BasicContactData.WEB, column = @Column(name = JpaDeliveryPerson.TABLE_PREFIX + BasicContactData.WEB)) })
	public JpaBasicContact getDeliveryContact() {
		return (JpaBasicContact) super.getDeliveryContact();
	}

	@Embedded
	@Valid
	@AttributeOverrides( {
			@AttributeOverride(name = BasicContactData.EMAIL, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + BasicContactData.EMAIL)),
			@AttributeOverride(name = BasicContactData.MOBILE, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + BasicContactData.MOBILE)),
			@AttributeOverride(name = BasicContactData.WEB, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + BasicContactData.WEB)) })
	public JpaBasicContact getCustomerContact() {
		return (JpaBasicContact) super.getCustomerContact();
	}

	@Embedded
	@AttributeOverrides( {
			@AttributeOverride(name = DeliveryPersonData.COMPANY, column = @Column(name = JpaDeliveryPerson.TABLE_PREFIX + DeliveryPersonData.COMPANY)),
			@AttributeOverride(name = DeliveryPersonData.PERSON, column = @Column(name = JpaDeliveryPerson.TABLE_PREFIX + DeliveryPersonData.PERSON)) })
	public JpaDeliveryPerson getDeliveryPerson() {
		return (JpaDeliveryPerson) super.getDeliveryPerson();
	}

	@Embedded
	@Valid
	@AttributeOverrides({
			@AttributeOverride(name = PersonNameData.SALUTATION, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + PersonNameData.SALUTATION)),
			@AttributeOverride(name = PersonNameData.FIRST_NAME, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + PersonNameData.FIRST_NAME)),
			@AttributeOverride(name = PersonNameData.SURNAME, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + PersonNameData.SURNAME)) })
	public JpaPersonName getCustomerPersonName() {
		return (JpaPersonName) super.getCustomerPersonName();
	}

	@Embedded
	@Valid
	@AttributeOverrides({
			@AttributeOverride(name = CompanyNameData.COMPANY_NAME, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + CompanyNameData.COMPANY_NAME)),
			@AttributeOverride(name = CompanyNameData.DEPARTMENT, column = @Column(name = JpaCustomerCore.TABLE_PREFIX + CompanyNameData.DEPARTMENT)) })
	public JpaCompanyName getCustomerCompanyName() {
		return (JpaCompanyName) super.getCustomerCompanyName();
	}
}