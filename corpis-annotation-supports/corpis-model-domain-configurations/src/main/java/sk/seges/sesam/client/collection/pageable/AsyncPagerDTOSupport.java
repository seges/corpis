/**
 * 
 */
package sk.seges.sesam.client.collection.pageable;

import sk.seges.sesam.shared.model.dto.PageDTO;
import sk.seges.sesam.shared.model.dao.ICallback;

/**
 * Pager support for asynchronous paging using asynchronous paged list.
 * @param <E> Type of list pager support is fetching
 * @author eldzi
 */
public class AsyncPagerDTOSupport<E> extends AbstractPagerDTOSupport {
	private final ICallback<E> callback;
	private final AsyncPagedDTOList<E> list;

	public AsyncPagerDTOSupport(AsyncPagedDTOList<E> list, ICallback<E> callback) {
		this.list = list;
		this.callback = callback;
		
		
	}

	/* (non-Javadoc)
	 * @see sk.seges.sesam.collection.pageable.AbstractPagerSupport#fetch(Page)
	 */
	@Override
	protected void fetch(PageDTO page) {
		list.get(page.getStartIndex(), callback);
	}

}
