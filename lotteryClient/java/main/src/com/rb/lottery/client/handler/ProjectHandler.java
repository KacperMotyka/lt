/**
 * @文件名称: ProjectHandler.java
 * @类路径:   com.rb.lottery.handler
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-31 下午02:33:38
 * @版本:     1.0.0
 */
package com.rb.lottery.client.handler;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.rb.lottery.client.UI.main.MainFrame;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.RegexConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.manager.ProjectManager;
import com.rb.lottery.client.manager.UserManager;
import com.rb.lottery.domain.BetBasket;
import com.rb.lottery.domain.Lottery;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;
import com.rb.lottery.system.SystemProcessor;

/**
 * @类功能说明:投注方案处理
 * @类修改者: robin
 * @修改日期:2011-11-17
 * @修改说明:添加新的事件监听
 * @作者: robin
 * @创建时间: 2011-10-31 下午02:33:38
 * @版本: 1.0.0
 */

public class ProjectHandler extends ActionHandler {

	/**
	 * @类名: ProjectHandler.java
	 * @描述: TODO
	 */
	public ProjectHandler() {
		super();
	}

	/**
	 * @类名: ProjectHandler.java
	 * @描述: TODO
	 * @param command
	 */
	public ProjectHandler(String command) {
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
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_UPLOAD)) {
			if (!SystemCache.isLogin) {
				JOptionPane.showMessageDialog(null, MessageConstants.NO_LOGIN);
				return;
			}
			ProjectManager.getInstance().uploadProject();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_NEWPRO)) {
			ProjectManager.getInstance().newProject();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_SAVEPRO)) {
			ProjectManager.getInstance().saveProject();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_OPENPRO)) {
			ProjectManager.getInstance().openProject();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_CHOOSE_PASSWAY)) {
			ProjectManager.getInstance().choosePassway();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_DO_PART)) {
			boolean toNewBasket = MainFrame.getInstance().isNewBasket();
			ProjectManager.getInstance().doPart(toNewBasket);
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_DO_BET)) {
			if (!SystemCache.isLogin) {
				JOptionPane.showMessageDialog(null, MessageConstants.NO_LOGIN);
				return;
			}
			String multi = MainFrame.getInstance().getMulti();
			multi = multi.trim();
			int mul = 1;
			if (multi.matches(RegexConstants.POS_INTEGER_REGEX)) {
				mul = Integer.parseInt(multi);
			} else if (!multi.equals("")) {
				JOptionPane.showMessageDialog(null,
						MessageConstants.INVALID_MULTI);
				return;
			}
			Lottery lt = SystemCache.getLottery(SystemCache.currentLotteryId);
			if (lt == null) {
				JOptionPane.showMessageDialog(null, "无投注信息!");
				return;
			}
			// 比赛场次
			Map<String, String> matches = lt.getMulMatches();
			int mtsz = matches.entrySet().size();
			if (mtsz == 0) {
				JOptionPane.showMessageDialog(null,
						MessageConstants.NO_MATCH_SELECT);
				return;
			}
			// 过关方式
			List<String> passway = lt.getPassway();
			int pwsz = passway.size();
			if (pwsz == 0) {
				JOptionPane.showMessageDialog(null,
						MessageConstants.NO_PASSWAYBET);
				return;
			}
			BetBasket basket = SystemProcessor
					.generateCurrentBets(SystemCache.currentLotteryId);
			int betCount = basket.getSingleBetCount();
			ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
			int isOK = JOptionPane.showConfirmDialog(null, "选择场次：" + mtsz
					+ "\n过关方式：" + passway.toString() + "\n单式注数：" + betCount
					+ "\n投注倍数：" + mul + "\n投注金额："
					+ (betCount * mul * SystemEnvironment.getInstance().money_per_bet)
					+ "\n   确定投注?", MessageConstants.INFOMATION,
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
					icon);
			if (isOK == 1) {
				return;
			}
			lt.setMulti(mul);
			boolean toNewBasket = MainFrame.getInstance().isNewBasket();
			boolean isSingleDisplay = MainFrame.getInstance().isSingleDispaly();
			ProjectManager.getInstance().displayBets(toNewBasket,
					isSingleDisplay, basket);
			if (isSingleDisplay) {
				UserManager.getInstance().doBet(0);
			} else {
				UserManager.getInstance().doBet(1);
			}
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_SAVERET)) {
			ProjectManager.getInstance().saveResult();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_OPENRET)) {
			ProjectManager.getInstance().openResult();
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
