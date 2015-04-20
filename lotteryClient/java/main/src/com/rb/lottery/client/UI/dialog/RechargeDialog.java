/**
 * @文件名称: RechargeDialog.java
 * @类路径:   com.rb.lottery.UI.dialog
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-23 下午03:57:59
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rb.lottery.client.UI.listener.RechargeListener;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.manager.SocketManager;
import com.rb.lottery.domain.CapitalInfo;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemProcessor;

/**
 * @类功能说明: 充值页面
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-23 下午03:57:59
 * @版本: 1.0.0
 */

public class RechargeDialog extends BasicDialog {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 857207082376532392L;

	private static RechargeDialog rechargeDialog = null;
	private static final String FRAME_TITLE = "帐户充值";
	private static final String ACCOUNT = "充值帐户:";
	private static final String LEFT = "帐户余额：";
	private static final String AMOUNT = "充值金额：";
	private static final String RECHARGE = "充值";

	private JPanel rpanel;
	private JTextField amount;
	private JLabel cgStatus;

	private JPanel banks;

	public static RechargeDialog getInstance() {
		if (rechargeDialog == null) {
			rechargeDialog = new RechargeDialog();
		}
		return rechargeDialog;
	}

	private RechargeDialog() {
		rpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// 充值帐户
		JLabel jact = new JLabel(ACCOUNT, JLabel.RIGHT);
		jact.setPreferredSize(new Dimension(100, 25));
		rpanel.add(jact);
		JLabel jactnm = new JLabel(SystemCache.currentUser.getUsername(),
				JLabel.LEFT);
		jactnm.setPreferredSize(new Dimension(200, 20));
		jactnm.setForeground(Color.RED);
		rpanel.add(jactnm);
		// 帐户余额
		JLabel j0 = new JLabel(LEFT, JLabel.RIGHT);
		j0.setPreferredSize(new Dimension(100, 25));
		rpanel.add(j0);
		SocketManager som = SocketManager.getInstance();
		CapitalInfo capInfo = som
				.getCapitalInfoByUserId(SystemCache.currentUser.getUserid());
		JLabel j0amount = new JLabel(capInfo.getRemain() + " 元", JLabel.LEFT);
		j0amount.setPreferredSize(new Dimension(200, 20));
		rpanel.add(j0amount);
		// 充值金额
		JLabel j1 = new JLabel(AMOUNT, JLabel.RIGHT);
		j1.setPreferredSize(new Dimension(100, 25));
		rpanel.add(j1);
		amount = new JTextField();
		amount.setPreferredSize(new Dimension(160, 20));
		amount.getDocument().addDocumentListener(
				new RechargeListener(CommandConstants.CMD_RECHARGE_AMOUNT));
		rpanel.add(amount);
		cgStatus = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
		cgStatus.setPreferredSize(new Dimension(110, 20));
		cgStatus.setForeground(Color.RED);
		rpanel.add(cgStatus);
		// 登录&取消
		JLabel je = new JLabel();
		je.setPreferredSize(new Dimension(80, 25));
		rpanel.add(je);
		commit = new JButton(RECHARGE);
		commit.setEnabled(false);
		commit.setPreferredSize(new Dimension(80, 25));
		commit.addActionListener(new RechargeListener(
				CommandConstants.CMD_RECHARGE));
		rpanel.add(commit);
		cancel = new JButton(SystemConstants.CANCEL);
		cancel.setPreferredSize(new Dimension(80, 25));
		cancel.addActionListener(new RechargeListener(
				CommandConstants.CMD_CANCEL));
		rpanel.add(cancel);

		this.add(rpanel);
		// 设置表单验证模式
		this.condition_type = BOTH_CONDITION;
		condition_flag = new boolean[1];
		condition_flag[0] = false;
		this.setTitle(FRAME_TITLE);
		Image image = Toolkit.getDefaultToolkit().getImage(
				FilePathConstants.LOTTERY_IMG_FILE);
		this.setIconImage(image);
		// Point location = MainFrame.getInstance().getPasswayButtonLocation();
		this.setLocation(400, 280);
		this.setPreferredSize(new java.awt.Dimension(400, 170));
		this.setUndecorated(false);
		this.pack();
		// MainFrame.getInstance().setFrameEnable(false);
		// this.addKeyListener(new LoginListener());
		// this.getRootPane().setDefaultButton(commit);
		this.addWindowListener(new RechargeListener());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// this.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.lottery.UI.dialog.BasicDialog#destroy()
	 */
	@Override
	public void destroy() {
		this.dispose();
		rechargeDialog = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.lottery.UI.dialog.BasicDialog#changeStatus(java.lang.String,
	 * java.lang.String, java.awt.Color)
	 */
	@Override
	public void changeStatus(String component, String text, Color c) {
		if (component.equals(CommandConstants.CMD_RECHARGE_AMOUNT)) {
			cgStatus.setText(text);
			cgStatus.setForeground(c);
			return;
		}
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public String getAmount() {
		return amount.getText();
	}

}
