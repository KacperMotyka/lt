/**
 * @文件名称: FilterHandler.java
 * @类路径:   com.rb.lottery.handler
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-31 下午02:27:30
 * @版本:     1.0.0
 */
package com.rb.lottery.client.handler;

import java.awt.event.ActionEvent;

import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.manager.FilterManager;

/**
 * @类功能说明: 过滤处理类
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-10-31 下午02:27:30
 * @版本: 1.0.0
 */

public class FilterHandler extends ActionHandler {

	/**
	 * @类名: FilterHandler.java
	 * @描述: TODO
	 */
	public FilterHandler() {
		super();
	}

	/**
	 * @类名: FilterHandler.java
	 * @描述: TODO
	 * @param command
	 */
	public FilterHandler(String command) {
		super(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rb.lottery.handler.ActionHandler#actionPerformed(java.awt
	 * .event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = command;
		Object src = e.getSource();
		if (cmd.equals(SystemConstants.EMPTY_STRING)) {
			cmd = e.getActionCommand();
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_SINGLE)) {
			FilterManager.getInstance().selectSmlMulti(false);
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_SML_MULTI)) {
			FilterManager.getInstance().selectSmlMulti(true);
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_DO_FILTER)) {
			FilterManager.getInstance().doFilter();
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.lottery.handler.ActionHandler#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
