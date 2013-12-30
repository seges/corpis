package sk.seges.corpis.server.domain.product;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.corpis.server.domain.Description;
import sk.seges.corpis.server.domain.Name;
import sk.seges.corpis.server.domain.Vat;
import sk.seges.corpis.server.domain.customer.CustomerCore;
import sk.seges.corpis.server.domain.search.SupValue;
import sk.seges.sesam.domain.IMutableDomainObject;
import sk.seges.sesam.pap.model.annotation.ReadOnly;
import sk.seges.sesam.security.shared.domain.ISecuredObject;

import java.util.Date;
import java.util.List;
import java.util.Set;

@DomainInterface
@BaseObject
public interface Product extends IMutableDomainObject<Long>, ISecuredObject<Long> {

	String extId();
	String externalId();
	List<? extends Name> names();
	List<? extends Description> descriptions();
	String description();
	Float weight();
	Integer unitsPerPackage();
	List<? extends ProductPrice> prices();
	Set<? extends ProductPrice> fees();
	Vat vat();
	List<? extends Product> childVariants();
	List<? extends ProductCategory> categories();
	Set<? extends Tag> tags();
	String webId();
	Set<? extends SupValue> sups();
	CustomerCore manufacturer();
	CustomerCore seller();
	Product variant();
	List<? extends Product> relatedProducts();
	Integer count();
	String thumbnailPath();
	Date importedDate();
	Long priority();
	Boolean deleted();
	String imagePath();

	@ReadOnly(ReadOnly.PropertyType.METHOD)
	Product clone();

	//TODO move to secured info
	Boolean visible();
	String roleName();

}