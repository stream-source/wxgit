package com.itwx.entry;

public class PageBean {

	private int page;//第几页
	private int rows;//记录每页行数
	private int start;//记录起始页
	
	public PageBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PageBean(int page, int rows) {
		super();
		this.page = page;
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getStart() {
		return (page-1)*rows;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
}
