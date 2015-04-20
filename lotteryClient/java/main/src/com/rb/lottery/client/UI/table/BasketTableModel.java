/**
 * @文件名称: BasketTableModel.java
 * @类路径:   com.rb.lottery.UI.table
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-15 下午02:39:54
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.table;

import javax.swing.table.DefaultTableModel;


/**
 * @类功能说明: 号码篮列表控制器
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-15 下午02:39:54
 * @版本:       1.0.0
 */

public class BasketTableModel extends DefaultTableModel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -6841653984048623360L;

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	public void clear() {
		int row = this.getRowCount() - 1;
		while(row >= 0) {
			this.removeRow(row);
			row = this.getRowCount() - 1;
		}		
	}
}
