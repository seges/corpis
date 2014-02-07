package sk.seges.sesam.shared.model.dto;

import java.io.Serializable;

public class PagedResultDTO<DTO_T> implements Serializable {
	 
	 
	public static final String PAGE = "page";
	
	private PageDTO page;
	
	public static final String RESULT = "result";
	
	private DTO_T result;
	
	public static final String TOTAL_RESULT_COUNT = "totalResultCount";
	
	private int totalResultCount;
	
	public PagedResultDTO() {}
	
	public PagedResultDTO(PageDTO page, DTO_T result, int totalResultCount) {
		this.page = page;
		this.result = result;
		this.totalResultCount = totalResultCount;
	}
	
	public PageDTO getPage() {
		return page;
	}
	
	public void setPage(PageDTO page) {
		this.page = page;
	}
	
	public DTO_T getResult() {
		return result;
	}
	
	public void setResult(DTO_T result) {
		this.result = result;
	}
	
	public int getTotalResultCount() {
		return totalResultCount;
	}
	
	public void setTotalResultCount(int totalResultCount) {
		this.totalResultCount = totalResultCount;
	}
}
