/**
 * @文件名称: BasketListCellRenderer.java
 * @类路径:   com.rb.lottery.UI.list
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-17 上午09:36:42
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.list;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JList;

import com.rb.lottery.system.SystemCache;

/**
 * @类功能说明: 列表数据显示控制器
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-17 上午09:36:42
 * @版本: 1.0.0
 */

public class BasketListCellRenderer extends DefaultListCellRenderer {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 5743261510958708074L;

	private JCheckBox basket;

	public BasketListCellRenderer() {
		super();
		String name = SystemCache.getCurrentBasket().getName();
		setBasket(new JCheckBox(name));
		basket.setSelected(true);
	}

	public BasketListCellRenderer(JCheckBox newbasket) {
		super();
		setBasket(newbasket);
	}

	/**
	 * @param basket
	 *            basket
	 */
	public void setBasket(JCheckBox basket) {
		this.basket = basket;
	}

	/**
	 * @return basket
	 */
	public JCheckBox getBasket() {
		return basket;
	}

	/**
	 * 隔行换色 (non-Javadoc)
	 * 
	 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList,
	 *      java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (index % 2 == 0) {
			setBackground(Color.CYAN);
		} else {
			setBackground(Color.ORANGE);
		}
		// return super.getListCellRendererComponent(list, value, index,
		// isSelected, cellHasFocus);
		return basket;
	}
}
