package com.rb.lottery.datatable;

/**
 * 默认Group实现
 */
public class DefaultGroup implements Group {
	private int row = 0;

	private int column = 0;

	private int rowSpan = 1;

	private int columnSpan = 1;

	private Object headerValue = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.lottery.datatable.Group#getRow()
	 */
	public int getRow() {
		return this.row;
	}

	/** */
	/**
	 * @param row
	 *            要设置的 row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.lottery.datatable.Group#getColumn()
	 */
	public int getColumn() {
		return this.column;
	}

	/** */
	/**
	 * @param column
	 *            要设置的 column
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.lottery.datatable.Group#getColumnSpan()
	 */
	public int getColumnSpan() {
		return this.columnSpan;
	}

	/** */
	/**
	 * @param columnSpan
	 *            要设置的 columnSpan
	 */
	public void setColumnSpan(int columnSpan) {
		this.columnSpan = columnSpan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.lottery.datatable.Group#getRowSpan()
	 */
	public int getRowSpan() {
		return this.rowSpan;
	}

	/** */
	/**
	 * @param rowSpan
	 *            要设置的 rowSpan
	 */
	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	/** */
	/**
	 * @param row
	 *            要设置的 row
	 * @param rowSpan
	 *            要设置的 rowSpan
	 * @param column
	 *            要设置的 column
	 * @param columnSpan
	 *            要设置的 columnSpan
	 */
	public void setLocation(int row, int column, int rowSpan, int columnSpan) {
		this.row = row;
		this.column = column;
		this.rowSpan = rowSpan;
		this.columnSpan = columnSpan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.lottery.datatable.Group#getHeaderValue()
	 */
	public Object getHeaderValue() {
		return this.headerValue;
	}

	/** */
	/**
	 * @param headerValue
	 *            要设置的 headerValue
	 */
	public void setHeaderValue(Object headerValue) {
		this.headerValue = headerValue;
	}
}
