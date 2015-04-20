/**
 * @文件名称: SystemConfHandler.java
 * @类路径:   com.rb.lottery.handler
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-31 下午02:18:35
 * @版本:     1.0.0
 */
package com.rb.lottery.client.handler;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.rb.lottery.client.UI.main.MainFrame;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.exception.LotteryException;
import com.rb.lottery.client.manager.IOManager;
import com.rb.lottery.client.manager.SocketManager;
import com.rb.lottery.client.manager.SystemConfManager;
import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.system.SysConfig;
import com.rb.lottery.system.SystemProcessor;
import com.rb.lottery.system.UserConfig;

/**
 * @类功能说明: 系统配置处理类
 * @类修改者: robin
 * @修改日期: 2012-3-7
 * @修改说明: 添加自动开奖功能
 * @作者: robin
 * @创建时间: 2011-10-31 下午02:18:35
 * @版本: 1.0.0
 */

public class SystemConfHandler extends ActionHandler {

	private static final Logger log = Logger.getLogger(SystemConfHandler.class);

	public static SystemConfManager sysManager = SystemConfManager
			.getInstance();

	public SystemConfHandler() {
		super();
	}

	public SystemConfHandler(String cmd) {
		super(cmd);
	}

	/**
	 * @方法说明: 初始化界面数据
	 * @参数:
	 * @return void
	 * @throws
	 */
	public static Map<String, List<BetMatch>> getGUIData() {
		Map<String, List<BetMatch>> map = new HashMap<String, List<BetMatch>>();
		SocketManager sm = SocketManager.getInstance();
		try {
			int type = UserConfig.getInstance().getTzlx();
			String userQihao = UserConfig.getInstance().getQihao();
			String sysQihao = SystemConstants.EMPTY_STRING;
			if (type < 0 || type > 3) {
				throw (new LotteryException(16));
			} else if (type < 2) {
				sysQihao = SysConfig.getInstance().getSfcQihao();
			} else if (type == 2) {
				sysQihao = SysConfig.getInstance().getBjdcQihao();
			} else {
				sysQihao = SysConfig.getInstance().getJczqPeriod();
			}
			// 是否过期
			if (!userQihao.equals(sysQihao)) {
				ImageIcon icon = new ImageIcon(
						FilePathConstants.DATATABLE_IMG_FILE);
				int isOK = JOptionPane.showConfirmDialog(null,
						MessageConstants.OUTOFDATE_INFO,
						MessageConstants.INFOMATION, JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, icon);
				log.debug(isOK);
				if (isOK == 0) // YES
				{
					map = sm.getGUIDataFromServer(type, sysQihao);
				} else // NO
				{
					map = sm.getGUIDataFromServer(type, userQihao);
				}
			}
		} catch (LotteryException e) {
			e.printStackTrace();
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.lottery.handler.ActionHandler#actionPerformed(java.awt
	 * .event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// new Runnable(){public void run(){}};
		String cmd = command;
		Object src = e.getSource();
		if (cmd.equals(SystemConstants.EMPTY_STRING)) {
			cmd = e.getActionCommand();
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_EXIT)) {
			boolean isSave = SystemProcessor.isExitSave();
			MainFrame.getInstance().setVisible(false);
			if (isSave) {
				SystemProcessor.saveAndExit();
			} else {
				SystemProcessor.saveUserConfig();
				System.exit(0);
			}
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_NOTEPAD)) {
			SystemConfManager.getInstance().callNotepad();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_CALC)) {
			SystemConfManager.getInstance().callCalc();
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
