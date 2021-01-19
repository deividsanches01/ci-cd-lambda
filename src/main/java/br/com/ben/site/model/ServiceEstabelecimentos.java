package br.com.ben.site.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceEstabelecimentos {

	@SerializedName("content")
	@Expose
	private List<Estabelecimentos> content = null;
	@SerializedName("first")
	@Expose
	private boolean first;
	@SerializedName("hasContent")
	@Expose
	private boolean hasContent;
	@SerializedName("last")
	@Expose
	private boolean last;
	@SerializedName("nextPage")
	@Expose
	private long nextPage;
	@SerializedName("number")
	@Expose
	private long number;
	@SerializedName("numberOfElements")
	@Expose
	private long numberOfElements;
	@SerializedName("previousPage")
	@Expose
	private long previousPage;
	@SerializedName("size")
	@Expose
	private long size;
	@SerializedName("totalElements")
	@Expose
	private long totalElements;
	@SerializedName("totalPages")
	@Expose
	private long totalPages;
	
	private ErrorHandler error = null;

	public ErrorHandler getError() {
		return error;
	}

	public void setError(ErrorHandler error) {
		this.error = error;
	}

	public List<Estabelecimentos> getContent() {
		return content;
	}

	public void setContent(List<Estabelecimentos> content) {
		this.content = content;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isHasContent() {
		return hasContent;
	}

	public void setHasContent(boolean hasContent) {
		this.hasContent = hasContent;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public long getNextPage() {
		return nextPage;
	}

	public void setNextPage(long nextPage) {
		this.nextPage = nextPage;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public long getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(long numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public long getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(long previousPage) {
		this.previousPage = previousPage;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
}
