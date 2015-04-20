/**
 * @文件名称: ActionHandler.java
 * @类路径:   com.rb.lottery.handler
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-9-30 上午11:11:26
 * @版本:     1.0.0
 */
package com.rb.lottery.client.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明: 实现Action
 * @类修改者: robin
 * @修改日期: 2011-10-08
 * @修改说明: 添加处理
 * @作者: robin
 * @创建时间: 2011-9-30 上午11:11:26
 * @版本: 1.0.0
 */

public abstract class ActionHandler implements Runnable, ActionListener {

	public String command;

	public ActionHandler() {
		command = SystemConstants.EMPTY_STRING;
	}

	public ActionHandler(String command) {
		this.command = command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public abstract void actionPerformed(ActionEvent e);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public abstract void run();
}
