/**
 * @文件名称: LoginDialog.java
 * @类路径:   com.rb.lottery.UI.dialog
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-23 下午03:48:29
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.rb.lottery.client.UI.listener.LoginListener;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-23 下午03:48:29
 * @版本: 1.0.0
 */

public class LoginDialog extends BasicDialog {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -4204947822856979231L;

	private static LoginDialog loginDialog = null;
	private static final String FRAME_TITLE = "登录";

	private JPanel lpanel;
	private JTextField username;
	private JPasswordField password;

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return LoginDialog
	 * @throws
	 */
	public static LoginDialog getInstance() {
		if (loginDialog == null) {
			loginDialog = new LoginDialog();
		}
		return loginDialog;
	}

	private LoginDialog() {
		lpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// 帐号
		JLabel j1 = new JLabel(SystemConstants.USERNAME, JLabel.RIGHT);
		j1.setPreferredSize(new Dimension(80, 25));
		lpanel.add(j1);
		username = new JTextField();
		username.setPreferredSize(new Dimension(160, 20));
		lpanel.add(username);
		// 密码
		JLabel j2 = new JLabel(SystemConstants.PASSWORD, JLabel.RIGHT);
		j2.setPreferredSize(new Dimension(80, 20));
		lpanel.add(j2);
		password = new JPasswordField();
		// password.setEchoChar('*');
		password.setPreferredSize(new Dimension(160, 20));
		lpanel.add(password);
		// 登录&取消
		JLabel je = new JLabel();
		je.setPreferredSize(new Dimension(60, 25));
		lpanel.add(je);
		commit = new JButton(FRAME_TITLE);
		commit.setPreferredSize(new Dimension(80, 25));
		commit.addActionListener(new LoginListener(CommandConstants.CMD_LOGIN));
		lpanel.add(commit);
		cancel = new JButton(SystemConstants.CANCEL);
		cancel.setPreferredSize(new Dimension(80, 25));
		cancel
				.addActionListener(new LoginListener(
						CommandConstants.CMD_CANCEL));
		lpanel.add(cancel);

		this.add(lpanel);
		this.setTitle(FRAME_TITLE);
		Image image = Toolkit.getDefaultToolkit().getImage(
				FilePathConstants.LOTTERY_IMG_FILE);
		this.setIconImage(image);
		// Point location = MainFrame.getInstance().getPasswayButtonLocation();
		this.setLocation(400, 280);
		this.setPreferredSize(new java.awt.Dimension(290, 140));
		this.setUndecorated(false);
		this.pack();
		this.addWindowListener(new LoginListener());
		// MainFrame.getInstance().setFrameEnable(false);
		// this.addKeyListener(new LoginListener());
		// this.getRootPane().setDefaultButton(commit);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// this.setVisible(true);
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */

	public String getUserName() {
		return username.getText();
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public String getPassword() {
		String pwd = new String(password.getPassword());
		return pwd;
	}

	/* (non-Javadoc)
	 * @see com.rb.lottery.UI.dialog.BasicDialog#destroy()
	 */
	@Override
	public void destroy() {
		this.dispose();
		loginDialog = null;
	}

	/* (non-Javadoc)
	 * @see com.rb.lottery.UI.dialog.BasicDialog#changeStatus(java.lang.String, java.lang.String, java.awt.Color)
	 */
	@Override
	public void changeStatus(String component, String text, Color c) {
		// TODO Auto-generated method stub
		
	}
}
