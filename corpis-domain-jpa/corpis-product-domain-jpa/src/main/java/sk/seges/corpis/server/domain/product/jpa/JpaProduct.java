package sk.seges.corpis.server.domain.product.jpa;

import org.hibernate.annotations.Type;
import sk.seges.corpis.server.domain.DBNamespace;
import sk.seges.corpis.server.domain.customer.jpa.JpaCustomerCore;
import sk.seges.corpis.server.domain.customer.server.model.data.CustomerCoreData;
import sk.seges.corpis.server.domain.invoice.jpa.JpaDescription;
import sk.seges.corpis.server.domain.invoice.jpa.JpaName;
import sk.seges.corpis.server.domain.invoice.jpa.JpaVat;
import sk.seges.corpis.server.domain.product.server.model.base.ProductBase;
import sk.seges.corpis.server.domain.product.server.model.data.ProductCategoryData;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;
import sk.seges.corpis.server.domain.product.server.model.data.ProductPriceData;
import sk.seges.corpis.server.domain.product.server.model.data.TagData;
import sk.seges.corpis.server.domain.search.jpa.JpaSupValue;
import sk.seges.corpis.server.domain.search.server.model.data.SupValueData;
import sk.seges.corpis.server.domain.server.model.data.DescriptionData;
import sk.seges.corpis.server.domain.server.model.data.NameData;
import sk.seges.corpis.server.domain.server.model.data.VatData;
import sk.seges.sesam.security.shared.domain.ISecuredObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@SuppressWarnings("serial")
@Entity
@Table(name = DBNamespace.TABLE_PREFIX + "product", uniqueConstraints = { @UniqueConstraint(columnNames = { ProductData.WEB_ID,
		ProductData.EXT_ID }) })
@SequenceGenerator(name = JpaProduct.SEQ_PRODUCT, sequenceName = "seq_products", initialValue = 1)
public class JpaProduct extends ProductBase {

	protected static final String SEQ_PRODUCT = "seqProduct";

	protected static final int EXT_ID_MIN_LENGTH = 1;
	protected static final int EXT_ID_MAX_LENGTH = 30;

	public JpaProduct() {
		setVisible(true);
	}

	@Override
	@Id
	@GeneratedValue(generator = SEQ_PRODUCT)
	public Long getId() {
		return super.getId();
	}

	@OneToMany(targetEntity = JpaProduct.class, mappedBy = "variant", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
	public List<ProductData> getChildVariants() {
		return super.getChildVariants();
	}

	@Override
	@NotNull
	@Size(min = EXT_ID_MIN_LENGTH, max = EXT_ID_MAX_LENGTH)
	@Column(name = ProductData.EXT_ID, nullable = false, length = EXT_ID_MAX_LENGTH)
	public String getExtId() {
		return super.getExtId();
	}

	@Override
	@Size(min = EXT_ID_MIN_LENGTH, max = EXT_ID_MAX_LENGTH)
	@Column(name = ProductData.EXTERNAL_ID, nullable = true, length = EXT_ID_MAX_LENGTH)
	public String getExternalId() {
		return super.getExternalId();
	}
	
	@Override
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, targetEntity = JpaName.class)
	public List<NameData> getNames() {
		return super.getNames();
	}

	@Override
	@Column(name = "image_path")
	public String getImagePath() {
		return super.getImagePath();
	}

	@Override
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, targetEntity = JpaDescription.class)
	public List<DescriptionData> getDescriptions() {
		return super.getDescriptions();
	}
	
	@Override
	@Type(type = "text")
	@Column(name = "description")
	public String getDescription() {
		return super.getDescription();
	}

	@Override
	@Column(name = "weight")
	public Float getWeight() {
		return super.getWeight();
	}

	@Override
	@Column(nullable = false)
	public String getWebId() {
		return super.getWebId();
	}
	
	@Override
	@Column(name = "imported_date")
	public Date getImportedDate() {
		return super.getImportedDate();
	}
	
	@Override
	@Column(name = "units_per_package")
	public Integer getUnitsPerPackage() {
		return super.getUnitsPerPackage();
	}

	@Override
	@OneToMany(cascade = { CascadeType.ALL }, targetEntity = JpaProductPrice.class)
	@OrderBy(ProductPriceData.PRIORITY)
	@JoinTable(name = DBNamespace.TABLE_PREFIX + "product_product_prices")
	public List<ProductPriceData> getPrices() {
		return super.getPrices();
	}

	@Override
	@OneToMany(cascade = { CascadeType.ALL }, targetEntity = JpaProductPrice.class)
	@OrderBy(value = ProductPriceData.PRIORITY)
	@JoinTable(name = DBNamespace.TABLE_PREFIX + "product_product_fees")
	public Set<ProductPriceData> getFees() {
		return super.getFees();
	}

	@Override
	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE }, targetEntity = JpaVat.class)
	public VatData getVat() {
		return super.getVat();
	}

	@Override
	@Column(name = "count")
	public Integer getCount() {
		return super.getCount();
	}

	@Override
	@Column
	public Boolean getDeleted() {
		return super.getDeleted();
	}
	
	@Override
	@Column
	public Long getPriority() {
		return super.getPriority();
	}
	
	@Override
	@Transient
	public List<ProductData> getRelatedProducts() {
		return super.getRelatedProducts();
	}
	
	@Override
	@ManyToMany(mappedBy = "products", targetEntity = JpaProductCategory.class)
	public List<ProductCategoryData> getCategories() {
		return super.getCategories();
	}

	@Override
	@Column(name = "thumbnail_path")
	public String getThumbnailPath() {
		return super.getThumbnailPath();
	}
	
	@Override
	@ManyToMany(cascade = { CascadeType.MERGE}, targetEntity = JpaTag.class)
	public Set<TagData> getTags() {
		return super.getTags();
	}

	@Override
	@Transient
	public String getRoleName() {
		return super.getRoleName();
	}

	@OneToMany(cascade = { CascadeType.ALL }, targetEntity = JpaSupValue.class, orphanRemoval=true)
	@JoinColumn(name="product_id")
	public Set<SupValueData> getSups() {
		return super.getSups();
	};

	@Override
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = JpaCustomerCore.class)
	public CustomerCoreData getManufacturer() {
		return super.getManufacturer();
	}

	@Override
	@Transient
	public Boolean getVisible() {
		return super.getVisible();
	}

	@Override
	@ManyToOne(targetEntity = JpaCustomerCore.class)
	public CustomerCoreData getSeller() {
		return super.getSeller();
	}

	@Override
	@ManyToOne(targetEntity = JpaProduct.class)
	public ProductData getVariant() {
		return super.getVariant();
	}

	public ProductData clone() {
		List<ProductCategoryData> newCategories = null;
		if (getCategories() != null) {
			newCategories = new ArrayList<ProductCategoryData>();
			for (ProductCategoryData productCategory : getCategories()) {
				newCategories.add(productCategory.clone());
			}
		}

		List<DescriptionData> newDescriptions = null;
		if (getDescriptions() != null) {
			newDescriptions = new ArrayList<DescriptionData>();
			for (DescriptionData description : getDescriptions()) {
				newDescriptions.add(description.clone());
			}
		}

		Set<ProductPriceData> newFees = null;
		if (getFees() != null) {
			newFees = new HashSet<ProductPriceData>();
			for (ProductPriceData productPrice : getFees()) {
				newFees.add(productPrice.clone());
			}
		}

		List<NameData> newNames = null;
		if (getNames() != null) {
			newNames = new ArrayList<NameData>();
			for (NameData name : getNames()) {
				newNames.add(name.clone());
			}
		}

		List<ProductPriceData> newPrices = null;
		if (getPrices() != null) {
			newPrices = new ArrayList<ProductPriceData>();
			for (ProductPriceData productPrice : getPrices()) {
				newPrices.add(productPrice.clone());
			}
		}

		List<ProductData> newRelatedProducts = null;
		if (getRelatedProducts() != null) {
			newRelatedProducts = new ArrayList<ProductData>();
			for (ProductData product : getRelatedProducts()) {
				newRelatedProducts.add(product.clone());
			}
		}

		Set<TagData> newTags = null;
		if (getTags() != null) {
			newTags = new HashSet<TagData>();
			for (TagData tag : getTags()) {
				newTags.add(tag.clone());
			}
		}

		ProductData newProduct = new JpaProduct();
		newProduct.setCategories(newCategories);
		newProduct.setCount(getCount());
		newProduct.setDeleted(getDeleted());
		newProduct.setDescription(getDescription());
		newProduct.setDescriptions(newDescriptions);
		newProduct.setExtId(getExtId());
		newProduct.setFees(newFees);
		newProduct.setId(getId());
		newProduct.setManufacturer(getManufacturer());
		newProduct.setNames(newNames);
		newProduct.setPrices(newPrices);
		newProduct.setRelatedProducts(newRelatedProducts);
		newProduct.setRoleName(getRoleName());
		newProduct.setSeller(getSeller());
		newProduct.setTags(newTags);
		newProduct.setThumbnailPath(getThumbnailPath());
		newProduct.setUnitsPerPackage(getUnitsPerPackage());
		newProduct.setVariant(getVariant());
		newProduct.setVat(getVat());
		newProduct.setVisible(getVisible());
		newProduct.setWebId(getWebId());
		newProduct.setWeight(getWeight());
		newProduct.setPriority(getPriority());
		newProduct.setImportedDate(getImportedDate());
		newProduct.setExternalId(getExternalId());

		return newProduct;
	}

	@Override
	public Long getIdForACL() {
		return getId();
	}

	@Override
	public ISecuredObject<?> getParent() {
		return null;
	}

}