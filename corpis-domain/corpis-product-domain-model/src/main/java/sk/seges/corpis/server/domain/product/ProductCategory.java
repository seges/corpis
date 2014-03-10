package sk.seges.corpis.server.domain.product;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.shared.domain.HasWebId;
import sk.seges.corpis.server.domain.Name;
import sk.seges.corpis.shared.domain.product.EProductCategoryType;
import sk.seges.sesam.domain.IMutableDomainObject;
import sk.seges.sesam.pap.model.annotation.ReadOnly;
import sk.seges.sesam.security.shared.domain.ISecuredObject;

import java.util.List;
import java.util.Set;

@DomainInterface
@BaseObject
public interface ProductCategory extends IMutableDomainObject<Long>, HasWebId, ISecuredObject<Long> {

    String extId();
    Integer level();
    Set<? extends ProductCategory> children();
    List<? extends Product> products();
    ProductCategory parent();
    List<? extends Name> names();
    String description();
    Integer precedency();
    EProductCategoryType productCategoryType();
	List<? extends Tag> tags();
	Boolean visuallyDecorated();
	Boolean visuallySeparated();
	Boolean visuallyInteractive();
	Boolean provideSummary();
	Boolean contentCategory();
	boolean loadTagsFromParent();
	Long productsCount();

	//TODO move to secured info
	Boolean visible();
	String roleName();

	@ReadOnly(ReadOnly.PropertyType.METHOD)
	ProductCategory clone();

	@ReadOnly(ReadOnly.PropertyType.METHOD)
	void addProduct(Product product);

	@ReadOnly(ReadOnly.PropertyType.METHOD)
	void addChild(ProductCategory child);

	@ReadOnly(ReadOnly.PropertyType.METHOD)
	void removeChild(ProductCategory child);

	@ReadOnly(ReadOnly.PropertyType.METHOD)
	void removeProduct(Product original);

}