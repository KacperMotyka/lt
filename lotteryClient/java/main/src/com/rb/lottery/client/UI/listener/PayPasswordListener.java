/**
 * @文件名称: PayPasswordListener.java
 * @类路径:   com.rb.lottery.UI.listener
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-26 上午12:12:59
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import com.rb.lottery.client.UI.dialog.PayPasswordDialog;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.manager.SocketManager;
import com.rb.lottery.client.util.Base64Coder;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemProcessor;
import com.rb.lottery.user.User;

/**
 * @类功能说明: 支付密码修改监听
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-26 上午12:12:59
 * @版本: 1.0.0
 */

public class PayPasswordListener implements ActionListener, WindowListener,
		DocumentListener {

	private String command;

	public PayPasswordListener() {
		super();
	}

	public PayPasswordListener(String cmd) {
		super();
		command = cmd;
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
		PayPasswordDialog ppd = PayPasswordDialog.getInstance();
		if (cmd.equals(SystemConstants.EMPTY_STRING)) {
			cmd = e.getActionCommand();
		}
		if (cmd.equals(CommandConstants.CMD_CHANGE_PAYPASSWORD)) {
			ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
			int isOK = JOptionPane.showConfirmDialog(null,
					MessageConstants.CHANGEPPWD_CONFIRM,
					MessageConstants.INFOMATION, JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			if (isOK == 1) {
				return;
			}
			User user = SystemCache.currentUser;
			String enUactPwd = user.getPassword();
			String enactPwd = Base64Coder
					.base64EncryptAct(ppd.getActPassword());
			if (enactPwd.equals(enUactPwd)) { // 原帐号密码匹配
				boolean payPass = true;
				if (SystemProcessor.hasPayPassword()) {
					String enUoldPwd = user.getPaypassword();
					String enoldPwd = Base64Coder.base64EncryptPay(ppd
							.getOldPassword());
					if (!enoldPwd.equals(enUoldPwd)) {// 原支付密码不匹配
						payPass = false;
					}
				}
				if (payPass) {
					String ennewpwd = Base64Coder.base64EncryptPay(ppd
							.getNewPassword());
					user.setPaypassword(ennewpwd);
					user.setLastupdate(new Date());
					SocketManager som = SocketManager.getInstance();
					boolean isSuccess = som.changePayPassword(user);
					if (isSuccess) {
						ppd.destroy();
						JOptionPane.showMessageDialog(null, "设置支付密码成功");
					} else {
						JOptionPane.showMessageDialog(null, "设置支付密码失败");
					}
				} else {
					JOptionPane.showMessageDialog(null, "设置支付密码失败,原支付密码错误");
					ppd.clear();
				}
			} else {
				JOptionPane.showMessageDialog(null, "设置支付密码失败,原帐户密码错误");
				ppd.clear();
			}
			return;
		}
		if (cmd.equals(CommandConstants.CMD_CANCEL)) {
			ppd.destroy();
			return;
		}
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
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		PayPasswordDialog.getInstance().destroy();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.swing.event.DocumentListener#changedUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		PayPasswordDialog ppd = PayPasswordDialog.getInstance();
		if (command.equals(CommandConstants.CMD_ACT_PWD)) {
			String actpwd = null;
			int len = e.getDocument().getLength();
			try {
				actpwd = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (actpwd.trim().equals(SystemConstants.EMPTY_STRING)) {
				ppd.changeStatus(CommandConstants.CMD_ACT_PWD,
						MessageConstants.ENTER_OLD_PWD, Color.RED);
				ppd.setCondition(0, false);
				return;
			} else {
				ppd.changeStatus(CommandConstants.CMD_ACT_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				ppd.setCondition(0, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_OLD_PWD)) {
			String oldpwd = null;
			int len = e.getDocument().getLength();
			try {
				oldpwd = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (oldpwd.trim().equals(SystemConstants.EMPTY_STRING)) {
				ppd.changeStatus(CommandConstants.CMD_OLD_PWD,
						MessageConstants.ENTER_OLD_PWD, Color.RED);
				ppd.setCondition(1, false);
				return;
			} else {
				ppd.changeStatus(CommandConstants.CMD_OLD_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				ppd.setCondition(1, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_NEW_PWD)) {
			String newpwd = null;
			int len = e.getDocument().getLength();
			try {
				newpwd = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			newpwd = newpwd.trim();
			if (newpwd.length() < SystemConstants.PASSWORD_MIN_LENGTH) {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				ppd.setCondition(2, false);
			} else {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				ppd.setCondition(2, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_NEW_PWD_CONFIRM)) {
			String newPwdConf = ppd.getNewPassword();
			String cpassword = null;
			int len = e.getDocument().getLength();
			try {
				cpassword = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (newPwdConf.trim().equals("")) {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				ppd.setCondition(3, false);
				return;
			}
			if (!cpassword.equals(newPwdConf)) {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.DIFFERENT_PASSWORD, Color.RED);
				ppd.setCondition(3, false);
			} else {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.AVAILABLE, Color.GREEN);
				ppd.setCondition(3, true);
			}
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.swing.event.DocumentListener#insertUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		PayPasswordDialog ppd = PayPasswordDialog.getInstance();
		if (command.equals(CommandConstants.CMD_ACT_PWD)) {
			String actpwd = null;
			int len = e.getDocument().getLength();
			try {
				actpwd = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (actpwd.trim().equals(SystemConstants.EMPTY_STRING)) {
				ppd.changeStatus(CommandConstants.CMD_ACT_PWD,
						MessageConstants.ENTER_OLD_PWD, Color.RED);
				ppd.setCondition(0, false);
				return;
			} else {
				ppd.changeStatus(CommandConstants.CMD_ACT_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				ppd.setCondition(0, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_OLD_PWD)) {
			String oldpwd = null;
			int len = e.getDocument().getLength();
			try {
				oldpwd = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (oldpwd.trim().equals(SystemConstants.EMPTY_STRING)) {
				ppd.changeStatus(CommandConstants.CMD_OLD_PWD,
						MessageConstants.ENTER_OLD_PWD, Color.RED);
				ppd.setCondition(1, false);
				return;
			} else {
				ppd.changeStatus(CommandConstants.CMD_OLD_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				ppd.setCondition(1, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_NEW_PWD)) {
			String newpwd = null;
			int len = e.getDocument().getLength();
			try {
				newpwd = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			newpwd = newpwd.trim();
			if (newpwd.length() < SystemConstants.PASSWORD_MIN_LENGTH) {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				ppd.setCondition(2, false);
			} else {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				ppd.setCondition(2, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_NEW_PWD_CONFIRM)) {
			String newPwdConf = ppd.getNewPassword();
			String cpassword = null;
			int len = e.getDocument().getLength();
			try {
				cpassword = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (newPwdConf.trim().equals("")) {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				ppd.setCondition(3, false);
				return;
			}
			if (!cpassword.equals(newPwdConf)) {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.DIFFERENT_PASSWORD, Color.RED);
				ppd.setCondition(3, false);
			} else {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.AVAILABLE, Color.GREEN);
				ppd.setCondition(3, true);
			}
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.swing.event.DocumentListener#removeUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		PayPasswordDialog ppd = PayPasswordDialog.getInstance();
		if (command.equals(CommandConstants.CMD_ACT_PWD)) {
			String actpwd = null;
			int len = e.getDocument().getLength();
			try {
				actpwd = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (actpwd.trim().equals(SystemConstants.EMPTY_STRING)) {
				ppd.changeStatus(CommandConstants.CMD_ACT_PWD,
						MessageConstants.ENTER_OLD_PWD, Color.RED);
				ppd.setCondition(0, false);
				return;
			} else {
				ppd.changeStatus(CommandConstants.CMD_ACT_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				ppd.setCondition(0, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_OLD_PWD)) {
			String oldpwd = null;
			int len = e.getDocument().getLength();
			try {
				oldpwd = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (oldpwd.trim().equals(SystemConstants.EMPTY_STRING)) {
				ppd.changeStatus(CommandConstants.CMD_OLD_PWD,
						MessageConstants.ENTER_OLD_PWD, Color.RED);
				ppd.setCondition(1, false);
				return;
			} else {
				ppd.changeStatus(CommandConstants.CMD_OLD_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				ppd.setCondition(1, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_NEW_PWD)) {
			String newpwd = null;
			int len = e.getDocument().getLength();
			try {
				newpwd = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			newpwd = newpwd.trim();
			if (newpwd.length() < SystemConstants.PASSWORD_MIN_LENGTH) {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				ppd.setCondition(2, false);
			} else {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				ppd.setCondition(2, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_NEW_PWD_CONFIRM)) {
			String newPwdConf = ppd.getNewPassword();
			String cpassword = null;
			int len = e.getDocument().getLength();
			try {
				cpassword = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (newPwdConf.trim().equals("")) {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				ppd.setCondition(3, false);
				return;
			}
			if (!cpassword.equals(newPwdConf)) {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.DIFFERENT_PASSWORD, Color.RED);
				ppd.setCondition(3, false);
			} else {
				ppd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.AVAILABLE, Color.GREEN);
				ppd.setCondition(3, true);
			}
			return;
		}
	}

}
