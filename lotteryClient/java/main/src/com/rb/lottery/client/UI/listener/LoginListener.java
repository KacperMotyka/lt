/**
 * @文件名称: LoginListener.java
 * @类路径:   com.rb.lottery.UI.listener
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-23 下午04:40:44
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.rb.lottery.client.UI.dialog.LoginDialog;
import com.rb.lottery.client.UI.main.MainFrame;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.manager.SocketManager;
import com.rb.lottery.client.util.Base64Coder;
import com.rb.lottery.domain.CapitalInfo;
import com.rb.lottery.domain.LoginFlag;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;
import com.rb.lottery.user.User;

/**
 * @类功能说明: 登录监听
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-23 下午04:40:44
 * @版本: 1.0.0
 */

public class LoginListener implements ActionListener, KeyListener,
		WindowListener {

	private String command;

	public LoginListener() {
		super();
	}

	public LoginListener(String cmd) {
		super();
		command = cmd;
	}

	private int doLogin(String username, String password) {
		SocketManager som = SocketManager.getInstance();
		LoginFlag loginFlag = new LoginFlag();
		User user = som.doLogin(username, password, loginFlag);
		// 登录成功
		int login = loginFlag.getLoginFlag();
		if (0 == login) {
			SystemCache.isLogin = true;
			SystemCache.currentUser = user;
			CapitalInfo cpi = som.getCapitalInfoByUserId(user.getUserid());
			SystemCache.availableMoney = cpi.getAvailable();
			SystemCache.recharge = SystemEnvironment.getInstance().max_recharge
					- cpi.getAlladd();
		}
		return login;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = command;
		Object src = e.getSource();
		LoginDialog lgd = LoginDialog.getInstance();
		if (cmd.equals(SystemConstants.EMPTY_STRING)) {
			cmd = e.getActionCommand();
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_LOGIN)) {
			if (SystemCache.isLogin) {
				ImageIcon icon = new ImageIcon(
						FilePathConstants.DATATABLE_IMG_FILE);
				int isOK = JOptionPane.showConfirmDialog(null,
						MessageConstants.ALREADY_LOGIN,
						MessageConstants.INFOMATION, JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, icon);
				if (isOK == 1) {
					return;
				}
			}
			String username = lgd.getUserName();
			String password = lgd.getPassword();
			int login = doLogin(username, Base64Coder
					.base64EncryptAct(password));
			if (0 == login) {
				lgd.destroy();
				MainFrame.getInstance().loginSuccess(username);
				JOptionPane.showMessageDialog(null, "登录成功");
			} else if (1 == login) {
				JOptionPane.showMessageDialog(null, "登录失败，密码错误");
			} else if (2 == login) {
				JOptionPane.showMessageDialog(null, "登录失败，用户不存在");
			} else if (3 == login) {
				JOptionPane.showMessageDialog(null, "登录失败，无法连接服务器");
			}
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_CANCEL)) {
			lgd.destroy();
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		LoginDialog lgd = LoginDialog.getInstance();
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (SystemCache.isLogin) {
				ImageIcon icon = new ImageIcon(
						FilePathConstants.DATATABLE_IMG_FILE);
				int isOK = JOptionPane.showConfirmDialog(null,
						MessageConstants.ALREADY_LOGIN,
						MessageConstants.INFOMATION, JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, icon);
				if (isOK == 1) {
					return;
				}
			}
			String username = lgd.getUserName();
			String password = lgd.getPassword();
			int login = doLogin(username, password);
			if (0 == login) {
				lgd.destroy();
				MainFrame.getInstance().loginSuccess(username);
				JOptionPane.showMessageDialog(null, "登录成功");
			} else if (1 == login) {
				JOptionPane.showMessageDialog(null, "登录失败，密码错误");
			} else if (2 == login) {
				JOptionPane.showMessageDialog(null, "登录失败，用户不存在");
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		LoginDialog.getInstance().destroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent
	 * )
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent
	 * )
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
