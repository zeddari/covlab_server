package com.axilog.cov.util.paging;

public class RangeCriteria {

	private int offset;
	private int limit;

	public static RangeCriteria fromPageCriteria(int page, int size) {
		return new RangeCriteria(page, size);
	}

	private RangeCriteria(int page, int size) {
		this.offset = Math.max(page * size, 0);
		this.limit = Math.max(size, 0);
	}

	public int offset() {
		return this.offset;
	}

	public int limit() {
		return this.limit;
	}
}
