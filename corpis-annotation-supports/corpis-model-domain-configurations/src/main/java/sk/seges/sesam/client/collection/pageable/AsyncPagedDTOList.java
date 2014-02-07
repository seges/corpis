/**
 * 
 */
package sk.seges.sesam.client.collection.pageable;

import sk.seges.sesam.shared.model.dto.PageDTO;
import sk.seges.sesam.shared.model.dto.PagedResultDTO;
import sk.seges.sesam.shared.model.dao.ICallback;
import sk.seges.sesam.service.remote.IAsyncDataRemoteLoader;

import java.util.Iterator;
import java.util.List;

/**
 * Asynchronous paged list with synchronous access to cached paged result.
 * 
 * @param <E> Type of list
 * 
 * @author eldzi
 */
public class AsyncPagedDTOList<E> extends AbstractPagedDTOList<E> {
	private static final String MSG_NOT_IMPLEMENTED_YET = "Not implemented yet";

	private IAsyncDataRemoteLoader<List<E>> dataLoader;

	public AsyncPagedDTOList() {
	}

	public void setDataLoader(IAsyncDataRemoteLoader<List<E>> dataLoader) {
		this.dataLoader = dataLoader;
	}

	public void getPagedResult(final ICallback<PagedResultDTO<List<E>>> callback) {
		if (pagedResult == null) {

			PageDTO page = new PageDTO();
			page.setStartIndex(0);
			page.setPageSize(defaultPageSize);

			fetchPage(page, new ICallback<Void>() {

					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
					}

					public void onSuccess(Void result) {
						callback.onSuccess(pagedResult);
					}

				});
		} else {
			callback.onSuccess(pagedResult);
		}
	}
	
	/**
	 * Current cached paged result.
	 * @return
	 */
	public PagedResultDTO<List<E>> getPagedResult() {
		return pagedResult;
	}

	/**
	 * This method is abstraction of fetching. If needed we can create an
	 * abstract parent class with this method as abstract and ResultHandlerModel
	 * will be DAOResultHandlerModel that will implement only this method and
	 * corresponding DAO injection.
	 * 
	 * In case of need of this higher abstraction we can easily divide this
	 * class.
	 * 
	 * @param page
	 *            A page that should be fetched.
	 * @param callback A callback indicating if fetching was successful.
	 */
	private void fetchPage(PageDTO page, final ICallback<Void> callback) {
		dataLoader.load(page, new ICallback<PagedResultDTO<List<E>>>() {

			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}

			public void onSuccess(PagedResultDTO<List<E>> result) {
				setPagedResult(result);
				callback.onSuccess(null);
			}
		});
	}

	/**
	 * Returns element directly if it is within fetched (cached) page, else throw exception.
	 * 
	 * @see java.util.List#get(int)
	 */
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0)
			throw new IndexOutOfBoundsException("Index (" + index
					+ ") shouldn't be less than 0");

		if (isOutOfCachedPagedResult(index)) {
			throw new IndexOutOfBoundsException(
					"Index should be within range <"
							+ getPagedResult().getPage().getStartIndex()
							+ " ; " + getEndIndex()
							+ ">");
		}

		int fetchIndex = index % getPagedResult().getPage().getPageSize();
//		System.out.println("get " + index);
		return getPagedResult().getResult().get(fetchIndex);
	}

	public void get(final int index, final ICallback<E> callback) throws IndexOutOfBoundsException {
		if (index < 0)
			callback.onFailure(new IndexOutOfBoundsException("Index (" + index
					+ ") shouldn't be less than 0"));

		if (isOutOfCachedPagedResult(index)) {
			int nearestStartIndex = getNearestIndexToPageSize(index, defaultPageSize);

			PageDTO page = new PageDTO();
			page.setStartIndex(nearestStartIndex);
			page.setPageSize(defaultPageSize);

			fetchPage(page, new ICallback<Void>() {

				public void onFailure(Throwable caught) {
					callback.onFailure(caught);
				}

				public void onSuccess(Void result) {
					callback.onSuccess(getFromLoadedPageResult(index));
				}
			});
		}

		if (index >= getPagedResult().getTotalResultCount())
			callback.onFailure(new IndexOutOfBoundsException("Index (" + index
					+ ") shouldn't be greater than total number of elements."));
		
		callback.onSuccess(getFromLoadedPageResult(index));		
	}
	
	private E getFromLoadedPageResult(int index) {
		int fetchIndex = index % getPagedResult().getPage().getPageSize();
//		System.out.println("get " + index);
		return getPagedResult().getResult().get(fetchIndex);
	}

	/**
	 * Forces list to invalidate its current fetched page and fetch again.
	 */
	public void reload(final ICallback<Void> callback) {

		PageDTO page = new PageDTO();
		page.setStartIndex(0);
		page.setPageSize(defaultPageSize);

		page = (pagedResult == null ? page : pagedResult.getPage());
		fetchPage(page, callback);
	}
	
	/**
	 * Forces list to invalidate based on provided page and fetch again.
	 */
	public void reload(final PageDTO page, final ICallback<Void> callback) {
		fetchPage(page, callback);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {
		throw new RuntimeException(MSG_NOT_IMPLEMENTED_YET);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#toArray(T[])
	 */
	public <T> T[] toArray(T[] a) {
		throw new RuntimeException(MSG_NOT_IMPLEMENTED_YET);
	}

	public Iterator<E> iterator() {
		throw new RuntimeException(MSG_NOT_IMPLEMENTED_YET);
	}
}
