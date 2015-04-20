/**
 * @文件名称: QihaoListener.java
 * @类路径:   com.rb.lottery.UI.listener
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-23 下午12:26:43
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.rb.lottery.client.UI.main.MainFrame;


/**
 * @类功能说明: 
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-23 下午12:26:43
 * @版本:       1.0.0
 */

public class QihaoListener implements ItemListener {
	
	private Object deselected;

	/**
	 * @return deselected
	 */
	public Object getDeselected() {
		return deselected;
	}

	/**
	 * @param deselected deselected
	 */
	public void setDeselected(Object deselected) {
		this.deselected = deselected;
	}

	public QihaoListener() {
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
		// 取消选中事件
		if (e.getStateChange() == ItemEvent.DESELECTED) {
			deselected = e.getItem();
		}
		// 选中事件
		if (e.getStateChange() == ItemEvent.SELECTED) {
			MainFrame.getInstance().qihaoChange(deselected);
		}
	}

}
