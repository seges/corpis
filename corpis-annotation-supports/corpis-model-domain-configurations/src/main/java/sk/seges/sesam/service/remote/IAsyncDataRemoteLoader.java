package sk.seges.sesam.service.remote;

import sk.seges.sesam.shared.model.dto.PageDTO;
import sk.seges.sesam.shared.model.dto.PagedResultDTO;
import sk.seges.sesam.shared.model.dao.ICallback;

/**
 * Interface defining how to load data in common way. Usually used for loading
 * data to constrained models of data (i.e. asynchronous paged list).
 * 
 * Implementing class may provide additional criteria used to constrain
 * returning results. These constrains should be immutable (except value holders
 * or models if provided), only page is changing.
 * 
 * @author ladislav.gazo
 * 
 * @param <T>
 *            Type of paged result loaded by loader.
 */
public interface IAsyncDataRemoteLoader<T> {
	/**
	 * Load data constrained by a page definition. After loading call has
	 * finished use callback to return results.
	 * 
	 * @param page
	 *            Constraining page definition.
	 * @param callback
	 *            Callback called after a call is finished.
	 */
	void load(PageDTO page, ICallback<PagedResultDTO<T>> callback);
}
