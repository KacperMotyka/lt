/**
 * @文件名称: PageHandler.java
 * @类路径:   com.rb.lottery.handler
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-18 上午09:49:47
 * @版本:     1.0.0
 */
package com.rb.lottery.client.handler;

import java.awt.event.ActionEvent;

import com.rb.lottery.client.UI.main.MainFrame;
import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 分页处理
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-18 上午09:49:47
 * @版本:       1.0.0
 */

public class PageHandler extends ActionHandler {

	/**
	 * @类名: PageHandler.java
	 * @描述: TODO
	 */
	public PageHandler() {
		super();
	}

	/**
	 * @类名: PageHandler.java
	 * @描述: TODO
	 * @param command
	 */
	public PageHandler(String command) {
		super(command);
	}

	/* (non-Javadoc)
	 * @see com.rb.lottery.handler.ActionHandler#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = command;
		Object src = e.getSource();
		if (cmd.equals(SystemConstants.EMPTY_STRING)) {
			cmd = e.getActionCommand();
		}
		if (cmd.equalsIgnoreCase(SystemConstants.FIRST_PAGE)) {
			MainFrame.getInstance().firstPage();
			return;
		}
		if (cmd.equalsIgnoreCase(SystemConstants.PREVIOUS_PAGE)) {
			MainFrame.getInstance().previousPage();
			return;
		}
		if (cmd.equalsIgnoreCase(SystemConstants.NEXT_PAGE)) {
			MainFrame.getInstance().nextPage();
			return;
		}
		if (cmd.equalsIgnoreCase(SystemConstants.LAST_PAGE)) {
			MainFrame.getInstance().lastPage();
			return;
		}
		if (cmd.equalsIgnoreCase(SystemConstants.GOTO)) {
			MainFrame.getInstance().gotoPage();
			return;
		}
	}

	/* (non-Javadoc)
	 * @see com.rb.lottery.handler.ActionHandler#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
