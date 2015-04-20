/**
 * @文件名称: BetTypeListener.java
 * @类路径:   com.rb.lottery.UI.listener
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-22 下午04:45:42
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;

import com.rb.lottery.client.UI.main.MainFrame;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-22 下午04:45:42
 * @版本: 1.0.0
 */

public class BetTypeListener implements ItemListener {

	public BetTypeListener() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		// origin
		// String item = e.getItem().toString();
		// 选项改变分2步,A选择取消，B选择选中
		// 选择选中事件
		if (e.getStateChange() == ItemEvent.SELECTED) {
			MainFrame.getInstance().betTypeChange();
		}
	}

}
