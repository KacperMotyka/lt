/**
 * @文件名称: UserHandler.java
 * @类路径:   com.rb.lottery.handler
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-02-21 下午05:12:38
 * @版本:     1.0.0
 */
package com.rb.lottery.client.handler;

import java.awt.event.ActionEvent;

import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.manager.UserManager;

/**
 * @类功能说明:用户事件处理
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-02-21 下午05:12:38
 * @版本: 1.0.0
 */

public class UserHandler extends ActionHandler {

	/**
	 * @类名: ProjectHandler.java
	 * @描述: TODO
	 */
	public UserHandler() {
		super();
	}

	/**
	 * @类名: ProjectHandler.java
	 * @描述: TODO
	 * @param command
	 */
	public UserHandler(String command) {
		super(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.lottery.handler.ActionHandler#actionPerformed(java.awt
	 * .event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = command;
		Object src = e.getSource();
		if (cmd.equals(SystemConstants.EMPTY_STRING)) {
			cmd = e.getActionCommand();
		}
		UserManager um = UserManager.getInstance();
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_REGISTER)) {
			um.register();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_LOGIN)) {
			um.login();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_CHANGEPASSWORD)) {
			um.changePassword();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_CPD_SETTING)) {
			um.cryptoguardSetting();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_PPD_SETTING)) {
			um.payPasswordSetting();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_RECHARGE)) {
			um.recharge();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_TOVIP)) {
			um.tovip();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_LOGOUT)) {
			um.logout();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_USERINFO_CK)) {
			um.userInfoCk();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_BETINFO_CK)) {
			um.betInfoCk();
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
	}
}
