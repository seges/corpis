/**
 * 
 */
package sk.seges.sesam.client.collection.pageable;

import sk.seges.sesam.shared.model.dto.PagedResultDTO;
import sk.seges.sesam.handler.ValueChangeHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Asynchronous paged list observable for paged result changes (e.g. new page was fetched...).
 * 
 * @param <E> Type of list
 * @author eldzi
 */
public class AsyncObservablePagedDTOList<E> extends AsyncPagedDTOList<E> {

	private List<ValueChangeHandler<PagedResultDTO<?>>> valueChangeHandlers = new ArrayList<ValueChangeHandler<PagedResultDTO<?>>>();
	
	@Override
	protected void setPagedResult(PagedResultDTO<List<E>> pagedResult) {
		PagedResultDTO<List<E>> oldValue = this.pagedResult;
		super.setPagedResult(pagedResult);
		
		for (ValueChangeHandler<PagedResultDTO<?>> valueChangeHandler: valueChangeHandlers) {
			valueChangeHandler.onValueChanged(oldValue, pagedResult);
		}
		
	}

	/* (non-Javadoc)
	 * @see sk.seges.sesam.domain.IObservableObject#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(ValueChangeHandler<PagedResultDTO<?>> listener) {
		valueChangeHandlers.add(listener);
	}

	/* (non-Javadoc)
	 * @see sk.seges.sesam.domain.IObservableObject#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(ValueChangeHandler<PagedResultDTO<?>> listener) {
		valueChangeHandlers.remove(listener);
	}
}