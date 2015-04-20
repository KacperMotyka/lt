/**
 * @文件名称: DataTableModel.java
 * @类路径:   com.rb.lottery.UI.table
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-4 下午08:38:09
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.table;

import javax.swing.table.DefaultTableModel;

import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明: 数据表格控制器
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-4 下午08:38:09
 * @版本: 1.0.0
 */

public class DataTableModel extends DefaultTableModel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 8057200756075547357L;

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col < 7) {
			return false;
		}
		return true;
	}

	// 实现了boolean自动转成JCheckbox
	/*
	 * jtable自动支持Jcheckbox， 覆盖tablemodel的getColumnClass返回一个boolean的class
	 * jtable自动画一个Jcheckbox value是true还是false直接读cell的值
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Class getColumnClass(int col) {
		/*
		 * int row = 0; int rowCount = this.getRowCount(); String value =
		 * (String)this.getValueAt(row, 0);
		 * while(value.equals(SystemConstants.EMPTY_STRING)) { row++; if(row ==
		 * rowCount) { row = 0; break; } value = (String)this.getValueAt(row,
		 * 0); }
		 */
		return getValueAt(0, col).getClass();
	}

	@Override
	public int getColumnCount() {
		return 12;
	}

	public void clear() {
		int row = this.getRowCount() - 1;
		while(row >= 0) {
			this.removeRow(row);
			row = this.getRowCount() - 1;
		}		
	}
}
