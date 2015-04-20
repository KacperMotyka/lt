/**
 * @文件名称: DataTableCellRenderer.java
 * @类路径:   com.rb.lottery.UI.table
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-4 下午08:45:50
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @类功能说明: 数据表格显示控制器
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-4 下午08:45:50
 * @版本: 1.0.0
 */

public class DataTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -3936812983211466205L;

	/**
	 * 隔行换色
	 * 
	 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent
	 *      (javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (row % 2 == 0) {
			setBackground(Color.CYAN);
		} else {
			setBackground(Color.ORANGE);
		}
		return super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
	}
}
