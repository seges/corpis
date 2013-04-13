package sk.seges.corpis.server.domain.news;

import java.util.Date;

import sk.seges.corpis.appscaffold.shared.annotation.BaseObject;
import sk.seges.corpis.appscaffold.shared.annotation.DomainInterface;
import sk.seges.sesam.domain.IMutableDomainObject;

@DomainInterface
@BaseObject
interface NewsItem extends IMutableDomainObject<NewsItemPK> {

	Date created();

	String subject();

	String body();
}