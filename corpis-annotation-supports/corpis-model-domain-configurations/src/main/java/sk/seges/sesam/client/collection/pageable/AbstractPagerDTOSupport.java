/**
 * 
 */
package sk.seges.sesam.client.collection.pageable;

import sk.seges.sesam.shared.model.dto.PageDTO;

/**
 * Support class for various pager implementations. All logic related to paging
 * and paged results has to be located here.
 * 
 * @author eldzi
 */
public abstract class AbstractPagerDTOSupport {
	protected PageDTO page;
	protected int resultSize;
	protected int totalCount;

	public boolean isPageSet() {
		return page != null;
	}
	
	public void setValue(PageDTO page, int resultSize, int totalCount) {
		this.page = page;
		this.resultSize = resultSize;
		this.totalCount = totalCount;
	}
	
	/**
	 * Method responsible for fetching specified index. Usually a get method
	 * with callback is a default implementation.
	 */
	protected abstract void fetch(PageDTO page);

	/**
	 * Go to next page.
	 */
	public void next() {
		int currentIndex = page.getStartIndex();
		PageDTO page = new PageDTO();
		page.setStartIndex(currentIndex + resultSize);
		page.setPageSize(page.getPageSize());
		fetch(page);
	}

	/**
	 * Go to previous page.
	 */
	public void previous() {
		int currentIndex = page.getStartIndex();
		currentIndex -= page.getPageSize();
		currentIndex = (currentIndex < 0 ? 0 : currentIndex);

		PageDTO page = new PageDTO();
		page.setStartIndex(currentIndex);
		page.setPageSize(page.getPageSize());

		fetch(page);
	}

	/**
	 * Go to first page.
	 */
	public void first() {
		PageDTO page = new PageDTO();
		page.setStartIndex(0);
		page.setPageSize(page.getPageSize());
		fetch(page);
	}

	/**
	 * Go to last page.
	 */
	public void last() {
		int pageSize = page.getPageSize();
		int lastIndex = totalCount - 1;
		lastIndex = PagedDTOList.getNearestIndexToPageSize(lastIndex, pageSize);

		PageDTO page = new PageDTO();
		page.setStartIndex(lastIndex);
		page.setPageSize(page.getPageSize());

		fetch(page);
	}

	/**
	 * @return True if results are from the last available page.
	 */
	public boolean isOnLastPage() {
		int pageSize = page.getPageSize();
		int lastIndex = totalCount - 1;
		return PagedDTOList.getNearestIndexToPageSize(lastIndex, pageSize) == page.getStartIndex();
	}

	/**
	 * @return True if results are from the first available page.
	 */
	public boolean isOnFirstPage() {
		return page.getStartIndex() == 0;
	}
	
	public int getCurrentPageNumber() {
		return PagedDTOList.getCurrentPageNumber(page);
	}
	
	public int getLastPageNumber() {
		return PagedDTOList.getLastPageNumber(totalCount, page.getPageSize());
	}
}
