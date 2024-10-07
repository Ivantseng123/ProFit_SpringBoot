package com.ProFit.model.dto.majorsDTO;

import java.util.List;

import org.springframework.data.domain.Page;

// PageResponse<T> 用於封裝分頁信息和實際數據, 可簡化controller的操作並統一pageAPI的response格式
// 客戶端更容易處理分頁數據
public class PageResponse<T> {

	private List<T> content; // 當前頁的數據
	private int pageNo; // 當前頁碼
	private int pageSize; // 每頁大小
	private long totalElements; // 總記錄數
	private int totalPages; // 總頁數
	private boolean last; // 是否為最後一頁
	private boolean first; // 是否為第一頁

	// 構造函數
	public PageResponse() {
	}

	// 全參數構造函數
	public PageResponse(List<T> content, int pageNo, int pageSize, long totalElements, int totalPages, boolean last,
			boolean first) {
		this.content = content;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.last = last;
		this.first = first;
	}

	// 靜態工廠方法，從 Spring Data 的 Page 對象創建 PageResponse
	public static <T> PageResponse<T> of(Page<T> page) {
		PageResponse<T> response = new PageResponse<>();
		response.setContent(page.getContent());
		response.setPageNo(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalElements(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setLast(page.isLast());
		response.setFirst(page.isFirst());
		return response;
	}

	// 靜態工廠方法，從 Spring Data 的 Page 對象創建 PageResponse
	// 可以用DTO創建, 把 page物件 的 content(原本是beanList) 換成 dtoList
	public static <T, U> PageResponse<T> ofDTO(List<T> dtoList, Page<U> page) {
		PageResponse<T> response = new PageResponse<>();
		response.setContent(dtoList); // dtoList 取代 page.getContent()
		response.setPageNo(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalElements(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setLast(page.isLast());
		response.setFirst(page.isFirst());
		return response;
	}

	// Getter 和 Setter 方法
	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

}
