package sk.seges.sesam.service.remote;


import sk.seges.sesam.shared.model.dto.PageDTO;
import sk.seges.sesam.shared.model.dto.PagedResultDTO;

public interface IDataRemoteLoader<T> {
	PagedResultDTO<T> load(PageDTO page);
}
