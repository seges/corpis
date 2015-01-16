package sk.seges.corpis.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;
import org.hibernate.impl.CriteriaImpl.Subcriteria;
import org.hibernate.property.Getter;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.springframework.transaction.annotation.Transactional;

import sk.seges.corpis.dao.AbstractJPADAO;
import sk.seges.sesam.dao.BetweenExpression;
import sk.seges.sesam.dao.Junction;
import sk.seges.sesam.dao.LikeExpression;
import sk.seges.sesam.dao.NotExpression;
import sk.seges.sesam.dao.NotNullExpression;
import sk.seges.sesam.dao.NullExpression;
import sk.seges.sesam.dao.Page;
import sk.seges.sesam.dao.PagedResult;
import sk.seges.sesam.dao.SimpleExpression;
import sk.seges.sesam.domain.IDomainObject;
import sk.seges.sesam.shared.model.dao.SortInfo;

public abstract class AbstractHibernateCRUD<T extends IDomainObject<?>> extends AbstractJPADAO<T> implements
		IHibernateFinderDAO<T> {

	public static final String ALIAS_CHAIN_DELIM = "_";
	public static final String FIELD_DELIM = ".";
	public static final String EMBEDDED_FIELD_DELIM = "-";
	public static final String COLON = ":";
	private Map<String, Method> preparedCriterions = null;

	private Set<Class<?>> embedableClassSet;

	protected AbstractHibernateCRUD(Class<? extends T> clazz) {
		super(clazz);
	}

	protected DetachedCriteria createCriteria() {
		return DetachedCriteria.forClass(clazz);
	}

	/**
	 * @return the preparedCriterions
	 */
	public Map<String, Method> getPreparedCriterions() {
		if (preparedCriterions == null) {
			preparedCriterions = new HashMap<String, Method>();
		}
		return preparedCriterions;
	}

	@Override
	public List<T> findByCriteria(DetachedCriteria criteria, Page page) {
		return findByCriteria(criteria, page, false);
	}

	public List<T> findByCriteria(DetachedCriteria criteria, Page page, boolean cacheable) {
		return findByCriteria(criteria, page, null, cacheable);
	}

	@Override
	public List<T> findByCriteria(DetachedCriteria criteria, Page page, Set<String> existingAliases) {
		return findByCriteria(criteria, page, existingAliases, false);
	}

	public List<T> findByCriteria(DetachedCriteria criteria, Page page, Set<String> existingAliases, boolean cacheable) {
		return doFindByCriteria(criteria, page, existingAliases, true, cacheable);
	}

	@Override
	@Transactional
	public T persist(T entity) {
		return super.persist(entity);
	}

	@Override
	@Transactional
	public T merge(T entity) {
		return super.merge(entity);
	}

	@SuppressWarnings("unchecked")
	private List<T> doFindByCriteria(DetachedCriteria criteria, Page page, Set<String> existingAliases,
			boolean addFilterables, boolean cacheable) {
		Criteria executable = criteria.getExecutableCriteria((Session) entityManager.getDelegate());

		if (existingAliases != null) {
			if (addFilterables) {
				enrichCriteriaWithFilterables(page, executable, existingAliases);
			}
			// projectables are needed only forexecutable final select, not for
			// count
			enrichCriteriaWithProjectables(page, executable, existingAliases);
		}

		enrichCriteriaWithSortables(page, executable, existingAliases);

		executable.setFirstResult(page.getStartIndex());
		if (!retrieveAllResults(page)) {
			// Restrict the selection only when page size has meaningful value.
			executable.setMaxResults(page.getPageSize());
		}

		executable.setCacheable(cacheable);
		if (cacheable) {
			executable.setCacheMode(CacheMode.NORMAL);
		}
		List<T> list = setSpecialSortCriteria(executable);
		if (list != null){
			return list;
		}
		return executable.list();
	}
	
	protected List<T> setSpecialSortCriteria(Criteria executable){
		return null;
	}

	@Override
	public Integer getCountByCriteria(DetachedCriteria criteria) {
		return getCountByCriteria(criteria, null, null);
	}

	@Override
	public Integer getCountByCriteria(DetachedCriteria criteria, Page requestedPage, Set<String> existingAliases) {
		return doGetCountByCriteria(criteria, requestedPage, existingAliases, null);
	}

	@SuppressWarnings("unchecked")
	private Integer doGetCountByCriteria(DetachedCriteria criteria, Page requestedPage, Set<String> existingAliases,
			MutableBoolean addedFilterables) {
		
		List<Long> resultList;
		setProjectionsForCountCriteria(criteria);
		Criteria executable = criteria.getExecutableCriteria((Session) entityManager.getDelegate());
		
		//remove orderings from criteria
		List<Order> removedOrderings = new ArrayList<Order>();
		removedOrderings = removeOrderingFromCriteria(executable);
		
		if (requestedPage != null && existingAliases != null) {
			enrichCriteriaWithFilterables(requestedPage, executable, existingAliases);
			if (null != addedFilterables) {
				addedFilterables.setValue(true);
			}
		}
		resultList = executable.list();
		
		//get back orderings from criteria
		addOrderingsToCriteria(removedOrderings, criteria);

		if (resultList == null || resultList.size() == 0) {
			return 0;
		}
		// FIXME: we cast it to Integer, changing it to long would imply changes
		// to PagedResult and lot of code..
		return resultList.get(0).intValue();
	}
	protected void setProjectionsForCountCriteria(DetachedCriteria criteria){
		criteria.setProjection(Projections.rowCount());
	}
	
	protected List<Order> removeOrderingFromCriteria(Criteria executable){
		List<Order> removedOrderings = new ArrayList<Order>();
		if(executable instanceof CriteriaImpl){
			CriteriaImpl criteriaImpl = (CriteriaImpl)executable;			
			Iterator orderIterator = criteriaImpl.iterateOrderings();
			while (orderIterator.hasNext()){
				OrderEntry orderEntry = (OrderEntry) orderIterator.next();
				removedOrderings.add(orderEntry.getOrder());
				orderIterator.remove();
			}			
		}
		return removedOrderings;
	}
	protected void addOrderingsToCriteria(List<Order> orderings, DetachedCriteria criteria){
		for(Order order : orderings){
			criteria.addOrder(order);
		}
	}
	
	protected void copyOrdersAndTheirSubcriteriaFromCriteriaToAnother(Criteria executable, DetachedCriteria criteria){
		Map<Subcriteria, Order> aliasToOrderMap = getSortSubcriteriaFromCriteria(executable);
		for(Subcriteria subcriteria : aliasToOrderMap.keySet()){
			criteria.createAlias(subcriteria.getPath(), subcriteria.getAlias(), subcriteria.getJoinType());
		}
		for(Order order : aliasToOrderMap.values()){
			criteria.addOrder(order);
		}
	}
	
	protected Map<Subcriteria, Order> getSortSubcriteriaFromCriteria(Criteria executable){
		List<Subcriteria> subcriteriaList = getCriteriaSubcriteria(executable);
		List<Order> orderings = getOrderingFromCriteria(executable);
		Map<Subcriteria, Order> aliasToOrderMap = new HashMap<Subcriteria, Order>();
		for(Order order : orderings){
			for(Subcriteria subcriteria : subcriteriaList){
				if(order.toString().startsWith(subcriteria.getAlias()+ ".")){
					aliasToOrderMap.put(subcriteria, order);
				}
			}
		}
		return aliasToOrderMap;
	}
	protected List<Subcriteria> getCriteriaSubcriteria(Criteria executable){
		List<Subcriteria> subcriteriaList = new ArrayList<CriteriaImpl.Subcriteria>();
		if(executable instanceof CriteriaImpl){
			CriteriaImpl criteriaImpl = (CriteriaImpl)executable;			
			Iterator subcriteriaIterator = criteriaImpl.iterateSubcriteria();
			while (subcriteriaIterator.hasNext()){
				Subcriteria subcriteria = (Subcriteria) subcriteriaIterator.next();
				subcriteriaList.add(subcriteria);
				}
		}
		return subcriteriaList;
	}
	protected List<Order> getOrderingFromCriteria(Criteria executable){
		List<Order> orderings = new ArrayList<Order>();
		if(executable instanceof CriteriaImpl){
			CriteriaImpl criteriaImpl = (CriteriaImpl)executable;			
			Iterator orderIterator = criteriaImpl.iterateOrderings();
			while (orderIterator.hasNext()){
				OrderEntry orderEntry = (OrderEntry) orderIterator.next();
				orderings.add(orderEntry.getOrder());
			}			
		}
		return orderings;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public T findUniqueResultByCriteria(DetachedCriteria criteria) {
		return (T) criteria.getExecutableCriteria((Session) entityManager.getDelegate()).uniqueResult();
	}

	@Transactional
	@Override
	public PagedResult<List<T>> findAll(Page requestedPage) {
		return findPagedResultByCriteria(createCriteria(), requestedPage);
	}

	@Transactional
	@Override
	public T findUnique(Page requestedPage) {
		PagedResult<List<T>> resultList = findAll(requestedPage);
		if (resultList == null || resultList.getResult() == null || resultList.getResult().size() != 1) {
			return null;
		}
		return resultList.getResult().get(0);
	}

	public PagedResult<List<T>> findPagedResultByCriteria(DetachedCriteria criteria, Page requestedPage,
			boolean cacheable) {
		Integer totalCount = null;
		// so we don't repeat aliases in one query - it is disallowed by
		// criteria definition
		Set<String> existingAliases = new HashSet<String>();
		MutableBoolean addedFilterables = new MutableBoolean();
		
		if (!retrieveAllResults(requestedPage)) {
			// select count only when paging
			totalCount = doGetCountByCriteria(criteria, requestedPage, existingAliases, addedFilterables);

			// clear count from criteria
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		}
		List<T> list = doFindByCriteria(criteria, requestedPage, existingAliases, !addedFilterables.value, cacheable);

		if (retrieveAllResults(requestedPage)) {
			// we are not paging results, get total count from fetched list
			totalCount = list.size();
		}
		return new PagedResult<List<T>>(requestedPage, list, totalCount);
	}

	public PagedResult<List<T>> findPagedResultByCriteria(DetachedCriteria criteria, Page requestedPage) {
		return findPagedResultByCriteria(criteria, requestedPage, false);
	}
	
	public PagedResult<List<T>> findDistinctPagedResultByCriteria(DetachedCriteria criteria, Page requestedPage){		
		criteria.setProjection(Projections.distinct(Projections
				.property(IDomainObject.ID)));
		Criteria executable = criteria.getExecutableCriteria(
				(Session) entityManager.getDelegate());
		List<Order> orderings = removeOrderingFromCriteria(executable);
		
		List<Long> ids = executable.list();
		if (ids == null || ids.isEmpty()) {
			return new PagedResult<List<T>>(requestedPage, new ArrayList<T>(), 0);
		}		

		DetachedCriteria criteriaFromIds = createCriteria();
		criteriaFromIds.add(Restrictions.in(IDomainObject.ID, ids));
		addOrderingsToCriteria(orderings, criteria);
		copyOrdersAndTheirSubcriteriaFromCriteriaToAnother(criteria.getExecutableCriteria(
				(Session) entityManager.getDelegate()), criteriaFromIds);
		
		criteriaFromIds.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		PagedResult<List<T>> result = null;
		if(requestedPage.getPageSize() >= ids.size()) {
			Page pageForCriteria = new Page(requestedPage.getStartIndex(), requestedPage.getPageSize());
			pageForCriteria.setProjectableResult(requestedPage.getProjectableResult());
			pageForCriteria.setSortables(requestedPage.getSortables());
			result = findPagedResultByCriteria(criteriaFromIds, pageForCriteria);
			result.setPage(requestedPage);
		} else {
			result = findPagedResultByCriteria(criteriaFromIds, requestedPage);
		}

		return result;
	}

	private boolean retrieveAllResults(Page requestedPage) {
		return requestedPage.getPageSize() == Page.ALL_RESULTS;
	}

	/**
	 * @param requestedPage
	 * @param criteria
	 * @param existingAliases
	 */
	private void enrichCriteriaWithProjectables(Page requestedPage, Criteria criteria, Set<String> existingAliases) {
		if (requestedPage.getProjectables() == null) {
			return;
		}
		final ProjectionList list = Projections.projectionList();

		Class<?> projectableResultClass;
		try {
			projectableResultClass = Class.forName(requestedPage.getProjectableResult());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to recreate class with string " + requestedPage.getProjectableResult()
					+ " for projectables.");
		}

		String[] directFields = createAliasesForProjectables(list, criteria,
				enhanceProjectablesWithEmbeddables(requestedPage.getProjectables(), projectableResultClass),
				existingAliases);
		criteria.setProjection(list).setResultTransformer(
				new ChainedFieldsTransformer(projectableResultClass, directFields));
	}

	private void findEmbeddableClasses() {
		Metamodel metamodel = entityManager.getEntityManagerFactory().getMetamodel();
		Set<EmbeddableType<?>> embeddableTypeSet = metamodel.getEmbeddables();
		embedableClassSet = new HashSet<Class<?>>();
		for (EmbeddableType<?> embeddableType : embeddableTypeSet) {
			embedableClassSet.add(embeddableType.getJavaType());
		}
	}

	/**
	 * For all embedded fields change their delimiters from "."
	 * {@link #FIELD_DELIM} to "-" {@link #EMBEDDED_FIELD_DELIM}
	 * <p>
	 * Example for one projectable where <strong>mailTemplate</strong> and
	 * <strong>commonStuff</strong> are embedded fields: <br>
	 * <code>user.mailTemplate.toUser.mailTemplate.commonStuff.mailAddress.city</code>
	 * -->
	 * <code>user.mailTemplate-toUser.mailTemplate-commonStuff-mailAddress.city</code>
	 * 
	 * @param projectableList
	 * @param projectableResultClass
	 * @return
	 */
	private List<String> enhanceProjectablesWithEmbeddables(List<String> projectableList,
			Class<?> projectableResultClass) {
		PropertyAccessor propertyAccessor = PropertyAccessorFactory.getPropertyAccessor("property");

		List<String> projectables = new ArrayList<String>();
		for (String projectable : projectableList) {
			if (projectable != null) {
				projectables.add(addEmbeddedDelimsToProperty(projectable, projectableResultClass, propertyAccessor));
			}
		}
		return projectables;
	}

	private String addEmbeddedDelimsToProperty(String property, Class<?> resultClass, PropertyAccessor propertyAccessor) {
		if (embedableClassSet == null) {
			findEmbeddableClasses();
		}

		Set<Class<?>> embedableClassCopySet = new HashSet<Class<?>>(embedableClassSet);
		
		Class<?> clazz = resultClass;
		StringBuilder newProperty = new StringBuilder(property.length());
		int fieldIndex = property.indexOf(FIELD_DELIM);
		String fieldName;
		Getter getter;
		while (fieldIndex > -1) {
			fieldName = property.substring(0, fieldIndex);
			getter = propertyAccessor.getGetter(clazz, fieldName);
			clazz = getter.getReturnType();

			boolean assignable = false;
			for (Class<?> clazzz : embedableClassCopySet) {
				if (clazz.isAssignableFrom(clazzz)) {
					assignable = true;
					break;
				}
			}
			
			if (assignable || embedableClassCopySet.contains(clazz)) {
				newProperty.append(fieldName + EMBEDDED_FIELD_DELIM);
			} else {
				newProperty.append(fieldName + FIELD_DELIM);
			}
			property = property.substring(fieldIndex + 1);
			fieldIndex = property.indexOf(FIELD_DELIM);
		}
		newProperty.append(property);
		return newProperty.toString();
	}

	/**
	 * Responsible for creating aliases projectables that are referencing
	 * many-to-one fields or chain of that fields. For simple fields just create
	 * simple field name.
	 * 
	 * Example:
	 * 
	 * Let's have this projectable = user.birthplace.street.number
	 * 
	 * Aliases for it will be = user, user__birthplace, user__birthplace__street
	 * 
	 * Projection will be = user__birthplace__street.number
	 * 
	 * <p>
	 * Because bug in hibernate HHH-817 alias can't have the same name as
	 * property
	 * 
	 * 
	 * 
	 * @param list
	 * @param criteria
	 * @param projectables
	 */
	private String[] createAliasesForProjectables(ProjectionList list, Criteria criteria, List<String> projectables,
			Set<String> existingAliases) {
		// if there are direct fields we need to store them in exact order for
		// chained transformation to replace null alias
		String[] directFields = new String[projectables.size()];
		int i = 0;
		for (String projectable : projectables) {
			int index = projectable.lastIndexOf(FIELD_DELIM);
			if (index == -1) {
				directFields[i] = projectable;
				list.add(Projections.property(replaceAllEmbeddedFieldDelimsWithFieldDelims(projectable)));
			} else {
				String alias = createAliases(criteria, projectable, existingAliases);
				String property;
				int indexOfLastEmbeddedField = findIndexOfLastEmbeddedField(alias);
				if (indexOfLastEmbeddedField > -1) {
					String lastEmbeddedField = alias.substring(indexOfLastEmbeddedField);
					property = createEmbeddedProperty(alias, indexOfLastEmbeddedField, lastEmbeddedField);
					alias = createEmbeddedAlias(alias, indexOfLastEmbeddedField, lastEmbeddedField);
				} else {
					property = alias;
				}

				list.add(Projections.property(property), alias + COLON);
			}
			i++;
		}
		return directFields;
	}

	/**
	 * @param alias
	 * @return -1 if last field is not embedded
	 */
	private int findIndexOfLastEmbeddedField(String alias) {
		int indexOfLastEmbeddedField = alias.lastIndexOf(FIELD_DELIM);
		if (alias.substring(indexOfLastEmbeddedField).contains(EMBEDDED_FIELD_DELIM)) {
			return indexOfLastEmbeddedField;
		} else {
			return -1;
		}
	}

	/**
	 * Replace last "-" {@link #EMBEDDED_FIELD_DELIM} with "."
	 * {@link #FIELD_DELIM}
	 * <p>
	 * Example <br>
	 * <code>user_mailTemplate-toUser.mailTemplate-subject</code> -->
	 * <code>user_mailTemplate-toUser.mailTemplate.subject</code>
	 * 
	 * @param alias
	 * @param indexOfLastEmbeddedField
	 * @param lastEmbeddedField
	 * @return
	 */
	private String createEmbeddedProperty(String alias, int indexOfLastEmbeddedField, String lastEmbeddedField) {
		return alias.substring(0, indexOfLastEmbeddedField)
				+ lastEmbeddedField.replaceAll(EMBEDDED_FIELD_DELIM, "\\" + FIELD_DELIM);
	}

	/**
	 * Replace last "." {@link #FIELD_DELIM} with "_" {@link #ALIAS_CHAIN_DELIM}
	 * <p>
	 * Example <br>
	 * <code>user_mailTemplate-toUser.mailTemplate-subject</code> -->
	 * <code>user_mailTemplate-toUser_mailTemplate-subject</code>
	 * 
	 * @param alias
	 * @param indexOfLastEmbeddedField
	 * @param lastEmbeddedField
	 * @return
	 */
	private String createEmbeddedAlias(String alias, int indexOfLastEmbeddedField, String lastEmbeddedField) {
		return alias.substring(0, indexOfLastEmbeddedField) + ALIAS_CHAIN_DELIM + lastEmbeddedField.substring(1);
	}

	private String replaceAllEmbeddedFieldDelimsWithFieldDelims(String source) {
		if (source.contains(EMBEDDED_FIELD_DELIM)) {
			return source.replaceAll(EMBEDDED_FIELD_DELIM, "\\" + FIELD_DELIM);
		} else {
			return source;
		}
	}

	private String createAliases(Criteria criteria, String field, Set<String> existingAliases) {
		int index = field.lastIndexOf(FIELD_DELIM);
		if (index == -1) {
			// no need to create alias, there is no chain of referencing
			// fields
			return field;
		}
		String lastField = field.substring(index + 1);

		// for the chain we need everything except last field, there must be
		// field delimiter
		// chain is e.g user.birthplace.street
		String chain = field.substring(0, index);
		// aliased is e.g user_birthplace_street
		String aliasedChain = chain.replaceAll("\\" + FIELD_DELIM, ALIAS_CHAIN_DELIM);

		int lastIndex = 0;
		String orig;
		String aliased = null;
		while (lastIndex < aliasedChain.length()) {
			index = aliasedChain.indexOf(ALIAS_CHAIN_DELIM, lastIndex);
			if (index == -1) {
				index = aliasedChain.length();
			}
			// we will cut by parts from beginning in parallel original and
			// alias
			aliased = aliasedChain.substring(0, index);
			if (existingAliases.contains(aliased)) {
				// don't add alias twice for criteria
				lastIndex = index + 1;
				continue;
			}
			orig = chain.substring(0, index);
			aliased = aliased.replaceAll(EMBEDDED_FIELD_DELIM, "");
			if(!existingAliases.contains(aliased)){
				criteria.createAlias(replaceAllEmbeddedFieldDelimsWithFieldDelims(orig), aliased, Criteria.LEFT_JOIN);
				existingAliases.add(aliased);
			}
			lastIndex = index + 1;
		}
		return aliased + FIELD_DELIM + lastField;
	}

	/**
	 * @param requestedPage
	 * @param criteria
	 * @param existingAliases
	 */
	private void enrichCriteriaWithFilterables(Page requestedPage, Criteria criteria, Set<String> existingAliases) {
		sk.seges.sesam.dao.Criterion filterable = requestedPage.getFilterable();
		if (filterable == null) {
			return;
		}
		Class<?> resultClass = null;
		String className = null;
		try {
			if (criteria instanceof CriteriaImpl) {
				className = ((CriteriaImpl) criteria).getEntityOrClassName();
				resultClass = Class.forName(className);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to recreate class with string " + className + " for filterables.");
		}

		criteria.add(retrieveRestriction(filterable, criteria, existingAliases, resultClass));
	}

	/**
	 * @param existingAliases
	 * @param resultClass
	 * @param filterable
	 * @return
	 */
	private Criterion retrieveRestriction(sk.seges.sesam.dao.Criterion filterable1, Criteria criteria,
			Set<String> existingAliases, Class<?> resultClass) {
		Class<?>[] parameterTypes = null;
		Object[] parameters = null;

		PropertyAccessor propertyAccessor = PropertyAccessorFactory.getPropertyAccessor("property");

		if (filterable1 instanceof BetweenExpression<?>) {
			BetweenExpression<?> filterable = (BetweenExpression<?>) filterable1;
			parameterTypes = new Class[] { String.class, Object.class, Object.class };

			String alias = createAliases(criteria,
					addEmbeddedDelimsToProperty(filterable.getProperty(), resultClass, propertyAccessor),
					existingAliases);
			alias = replaceAllEmbeddedFieldDelimsWithFieldDelims(alias);
			parameters = new Object[] { alias, filterable.getLoValue(), filterable.getHiValue() };
		} else if (filterable1 instanceof LikeExpression<?>) {
			LikeExpression<?> filterable = (LikeExpression<?>) filterable1;
			Comparable<? extends Serializable> value = filterable.getValue();
			MatchMode mode;
			try {
				mode = (MatchMode) MatchMode.class.getField(filterable.getMode().name()).get(null);
			} catch (Exception e) {
				throw new RuntimeException(
						"Unable to get hibernate match mode based on mode = " + filterable.getMode(), e);
			}
			String alias = createAliases(criteria,
					addEmbeddedDelimsToProperty(filterable.getProperty(), resultClass, propertyAccessor),
					existingAliases);
			alias = replaceAllEmbeddedFieldDelimsWithFieldDelims(alias);
			if (value instanceof String) {
				parameterTypes = new Class[] { String.class, String.class, MatchMode.class };
				parameters = new Object[] { alias, (String) value, mode };
			} else {
				parameterTypes = new Class[] { String.class, Object.class };
				parameters = new Object[] { alias, value };
			}

			parameters = new Object[] { alias, value, mode };
		} else if (filterable1 instanceof SimpleExpression<?>) {
			SimpleExpression<?> filterable = (SimpleExpression<?>) filterable1;
			parameterTypes = new Class[] { String.class, Object.class };

			Comparable<? extends Serializable> value = filterable.getValue();
			String alias = createAliases(criteria,
					addEmbeddedDelimsToProperty(filterable.getProperty(), resultClass, propertyAccessor),
					existingAliases);
			alias = replaceAllEmbeddedFieldDelimsWithFieldDelims(alias);
			parameters = new Object[] { alias, value };
		} else if (filterable1 instanceof NotExpression) {
			NotExpression filterable = (NotExpression) filterable1;
			parameterTypes = new Class[] { Criterion.class };

			parameters = new Object[] { retrieveRestriction(filterable.getCriterion(), criteria, existingAliases,
					resultClass) };
		} else if (filterable1 instanceof NullExpression) {
			NullExpression filterable = (NullExpression) filterable1;
			parameterTypes = new Class[] { String.class };

			String alias = createAliases(criteria,
					addEmbeddedDelimsToProperty(filterable.getProperty(), resultClass, propertyAccessor),
					existingAliases);
			alias = replaceAllEmbeddedFieldDelimsWithFieldDelims(alias);
			parameters = new Object[] { alias };
		} else if (filterable1 instanceof NotNullExpression) {
			NotNullExpression filterable = (NotNullExpression) filterable1;
			parameterTypes = new Class[] { String.class };

			String alias = createAliases(criteria,
					addEmbeddedDelimsToProperty(filterable.getProperty(), resultClass, propertyAccessor),
					existingAliases);
			alias = replaceAllEmbeddedFieldDelimsWithFieldDelims(alias);
			parameters = new Object[] { alias };
		}

		try {
			Method criterion = extractCriterionMethod(filterable1, parameterTypes);
			Criterion criterionInst = (Criterion) criterion.invoke(null, parameters);
			if (filterable1 instanceof Junction) {
				for (sk.seges.sesam.dao.Criterion juncted : ((Junction) filterable1).getJunctions()) {
					((org.hibernate.criterion.Junction) criterionInst).add(retrieveRestriction(juncted, criteria,
							existingAliases, resultClass));
				}
			}
			return criterionInst;
		} catch (Exception e) {
			throw new HibernateException("Unable to create criterion for filterable = " + filterable1, e);
		}
	}

	private Method extractCriterionMethod(sk.seges.sesam.dao.Criterion filterable, Class<?>[] parameterTypes) {
		Method criterion = getPreparedCriterions().get(filterable.getOperation());
		if (criterion == null) {
			// create new criterion and add
			try {
				criterion = Restrictions.class.getMethod(filterable.getOperation(), parameterTypes);
			} catch (Exception e) {
				throw new HibernateException("Unexpected operation in filterable = " + filterable, e);
			}
			getPreparedCriterions().put(filterable.getOperation(), criterion);
		}
		return criterion;
	}

	/**
	 * adds sortables to criteria, new aliases are added as well
	 * 
	 * @param requestedPage
	 * @param criteria
	 */
	private void enrichCriteriaWithSortables(Page requestedPage, Criteria criteria, Set<String> existingAliases) {
		List<SortInfo> sortables = requestedPage.getSortables();
		if (sortables == null || sortables.isEmpty()) {
			return;
		}

		PropertyAccessor propertyAccessor = PropertyAccessorFactory.getPropertyAccessor("field");
		Class<?> projectableResultClass;
		try {
			projectableResultClass = Class.forName(requestedPage.getProjectableResult());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to recreate class with string " + requestedPage.getProjectableResult()
					+ " for projectables.");
		}

		if (existingAliases == null) {
			existingAliases = new HashSet<String>();
		}
		for (SortInfo sortable : sortables) {
			String sort = sortable.getColumn();
			String sortWithEmbedded = addEmbeddedDelimsToProperty(sort, projectableResultClass, propertyAccessor);
			
			String alias = createAliases(criteria, sortWithEmbedded, existingAliases);
			if (alias.lastIndexOf(EMBEDDED_FIELD_DELIM) > alias.lastIndexOf(FIELD_DELIM)) {
				int index = alias.lastIndexOf(EMBEDDED_FIELD_DELIM);
				alias = alias.substring(0, index) + FIELD_DELIM + alias.substring(index+1);
			}
			criteria.addOrder(sortable.isAscending() ? Order.asc(alias) : Order.desc(alias));
		}
	}

	private static class MutableBoolean {
		private boolean value = false;

		public boolean isValue() {
			return value;
		}

		public void setValue(boolean value) {
			this.value = value;
		}
	}
}
