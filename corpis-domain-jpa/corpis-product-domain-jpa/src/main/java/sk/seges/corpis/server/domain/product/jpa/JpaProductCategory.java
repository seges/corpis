package sk.seges.corpis.server.domain.product.jpa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.IndexColumn;

import sk.seges.corpis.server.domain.DBNamespace;
import sk.seges.corpis.server.domain.invoice.jpa.JpaName;
import sk.seges.corpis.server.domain.product.server.model.base.ProductCategoryBase;
import sk.seges.corpis.server.domain.product.server.model.data.ProductCategoryData;
import sk.seges.corpis.server.domain.product.server.model.data.ProductData;
import sk.seges.corpis.server.domain.product.server.model.data.TagData;
import sk.seges.corpis.server.domain.server.model.data.NameData;
import sk.seges.corpis.shared.domain.product.EProductCategoryType;

@Entity
@Table (name = DBNamespace.TABLE_PREFIX + "product_category", uniqueConstraints = @UniqueConstraint(columnNames = { ProductCategoryData.WEB_ID, JpaProductCategory.EXT_ID_COLUMN }))
@SequenceGenerator(name = JpaProductCategory.SEQ_PRODUCT_CATEGORY, sequenceName = DBNamespace.TABLE_PREFIX + "seq_product_categories", initialValue = 1)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JpaProductCategory extends ProductCategoryBase {

	protected static final String EXT_ID_COLUMN = "EXT_ID";

	private static final long serialVersionUID = 5291165456785090234L;

    protected static final String SEQ_PRODUCT_CATEGORY = "seqProductCategories";

	public JpaProductCategory() {
		setPrecedency(Integer.valueOf(0));
		setVisible(true);
		setContentCategory(false);
		setLoadTagsFromParent(true);
	}

	public JpaProductCategory(Long id) {
		this();
		setId(id);
	}

	@Override
	@Transient
	public String getRoleName() {
		return super.getRoleName();
	}

	@Override
    @Id
    @GeneratedValue(generator = SEQ_PRODUCT_CATEGORY)
	public Long getId() {
		return super.getId();
	}

	@Override
	@JoinColumn(name = "mapping")
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY, targetEntity = JpaProductCategory.class)
	public Set<ProductCategoryData> getChildren() {
		return super.getChildren();
	}

	@Override
	@Column(name = "description")
	public String getDescription() {
		return super.getDescription();
	}

	@Override
	@Column(name = EXT_ID_COLUMN)
	public String getExtId() {
		return super.getExtId();
	}

	@Override
	@Column(name = "level")
	public Integer getLevel() {
		return super.getLevel();
	}

	@Override
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, targetEntity = JpaName.class)
	public List<NameData> getNames() {
		return super.getNames();
	}

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = JpaTag.class)
	@OrderBy(value = TagData.PRIORITY)
	public List<TagData> getTags() {
		return super.getTags();
	};
	
	@Override
    @JoinColumn(name = "mapping")
    @ManyToOne (cascade=CascadeType.PERSIST, fetch=FetchType.LAZY, targetEntity = JpaProductCategory.class)
	public JpaProductCategory getParent() {
		return (JpaProductCategory) super.getParent();
	}

	@Override
	@Column(name = "precedency")
	public Integer getPrecedency() {
		return super.getPrecedency();
	}

	@Override
	@Column(name = "productCategoryType")
	public EProductCategoryType getProductCategoryType() {
		return super.getProductCategoryType();
	}

	@Override
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY, targetEntity = JpaProduct.class)
    @JoinTable(name = DBNamespace.TABLE_PREFIX + "product_category_product")
	@IndexColumn(name = "product_position")
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	public List<ProductData> getProducts() {
		return super.getProducts();
	}

	@Override
	@Column(nullable = false)
	public String getWebId() {
		return super.getWebId();
	}
	
	@Column(name = "visually_decorated")
	public Boolean getVisuallyDecorated() {
		return super.getVisuallyDecorated();
	};

	@Column(name = "visually_separated")
	public Boolean getVisuallySeparated() {
		return super.getVisuallySeparated();
	}

	@Column(name = "visually_interactive")
	public Boolean getVisuallyInteractive() {
		return super.getVisuallyInteractive();
	}

	@Column(name = "provide_summary")
	public Boolean getProvideSummary() {
		return super.getProvideSummary();
	}

	@Override
	@Column
	public Boolean getContentCategory() {
		return super.getContentCategory();
	}

	@Override
	@Column(name = "load_tags_from_parent")
	public boolean isLoadTagsFromParent() {
		return super.isLoadTagsFromParent();
	}
	
	@Override
	@Column
	public Boolean getVisible() {
		return super.getVisible();
	}

	@Transient
	public Long getProductsCount() {
		return super.getProductsCount();
	}

	public void addProduct(ProductData product) {
		if (getProducts() != null && getProducts().contains(product)) {
			return;
		}

		if (getProducts() == null) {
			setProducts(new ArrayList<ProductData>());
		}

		getProducts().add(product);

	}

	public void addChild(ProductCategoryData child) {
		if (null == getChildren()) {
			setChildren(new HashSet<ProductCategoryData>());
		}
		child.setParent(this);
		getChildren().add(child);
	}

	public void removeChild(ProductCategoryData child) {
		if (getChildren() == null || child == null) {
			return;
		}
		child.setParent(null);
		getChildren().remove(child);
	}

	public void removeProduct(ProductData original) {
		if (getProducts() == null || getProducts().size() == 0) {
			return;
		}

		getProducts().remove(original);
	}

	@Transient
	@Override
	public Long getIdForACL() {
		return getId();
	}

	@Override
	public ProductCategoryData clone() {
		ProductCategoryData newProductCategory = new JpaProductCategory();

		Set<ProductCategoryData> newChildren = getChildren();
		if (getChildren() != null) {
			newChildren = new HashSet<ProductCategoryData>();
			for (ProductCategoryData productCategory : getChildren()) {
				newChildren.add(productCategory.clone());
			}
		}

		List<NameData> newNames = null;
		if (getNames() != null) {
			newNames = new ArrayList<NameData>();
			for (NameData name : getNames()) {
				newNames.add(name.clone());
			}
		}

		ProductCategoryData newParent = null;
		if (getParent() != null) {
			newParent = getParent().clone();
		}

		List<ProductData> newProducts = null;
		if (getProducts() != null) {
			newProducts = new ArrayList<ProductData>();
			for (ProductData product : getProducts()) {
				newProducts.add(product.clone());
			}
		}

		List<TagData> newTags = null;
		if (getTags() != null) {
			newTags = new ArrayList<TagData>();
			for (TagData tag : getTags()) {
				newTags.add(tag.clone());
			}
		}

		newProductCategory.setChildren(newChildren);
		newProductCategory.setContentCategory(getContentCategory());
		newProductCategory.setDescription(getDescription());
		newProductCategory.setExtId(getExtId());
		newProductCategory.setId(getId());
		newProductCategory.setLevel(getLevel());
		newProductCategory.setLoadTagsFromParent(isLoadTagsFromParent());
		newProductCategory.setNames(newNames);
		newProductCategory.setParent(newParent);
		newProductCategory.setPrecedency(getPrecedency());
		newProductCategory.setProductCategoryType(getProductCategoryType());
		newProductCategory.setProducts(newProducts);
		newProductCategory.setProvideSummary(getProvideSummary());
		newProductCategory.setRoleName(getRoleName());
		newProductCategory.setTags(newTags);
		newProductCategory.setVisible(getVisible());
		newProductCategory.setVisuallyDecorated(getVisuallyDecorated());
		newProductCategory.setVisuallyInteractive(getVisuallyInteractive());
		newProductCategory.setVisuallySeparated(getVisuallySeparated());
		newProductCategory.setWebId(getWebId());

		return newProductCategory;
	}

}