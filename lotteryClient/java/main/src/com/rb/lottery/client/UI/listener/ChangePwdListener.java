/**
 * @文件名称: ChangePwdListener.java
 * @类路径:   com.rb.lottery.UI.listener
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-24 下午12:43:36
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

import com.rb.lottery.client.UI.dialog.ChangePwdDialog;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.manager.SocketManager;
import com.rb.lottery.client.util.Base64Coder;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.user.User;

/**
 * @类功能说明:修改密码监听
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-24 下午12:43:36
 * @版本: 1.0.0
 */

public class ChangePwdListener implements ActionListener, DocumentListener,
		WindowListener {

	private String command;

	public ChangePwdListener() {
		super();
	}

	public ChangePwdListener(String cmd) {
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
		ChangePwdDialog cgd = ChangePwdDialog.getInstance();
		if (cmd.equals(SystemConstants.EMPTY_STRING)) {
			cmd = e.getActionCommand();
		}
		if (cmd.equals(CommandConstants.CMD_CHANGEPASSWORD)) {
			ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
			int isOK = JOptionPane.showConfirmDialog(null,
					MessageConstants.CHANGEPWD_CONFIRM,
					MessageConstants.INFOMATION, JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			if (isOK == 1) {
				return;
			}
			User user = SystemCache.currentUser;
			String enUoldPwd = user.getPassword();
			String enoldPwd = Base64Coder
					.base64EncryptAct(cgd.getOldPassword());
			if (enoldPwd.equals(enUoldPwd)) { // 原密码匹配
				String ennewpwd = cgd.getNewPassword();
				user.setPassword(Base64Coder.base64EncryptAct(ennewpwd));
				user.setLastupdate(new Date());
				SocketManager som = SocketManager.getInstance();
				boolean isSuccess = som.changePassword(user);
				if (isSuccess) {
					cgd.destroy();
					JOptionPane.showMessageDialog(null, "修改帐户密码成功");
				} else {
					JOptionPane.showMessageDialog(null, "修改帐户密码失败");
				}
			} else {
				JOptionPane.showMessageDialog(null, "修改帐户密码失败,原密码错误");
				cgd.clear();
			}
			return;
		}
		if (cmd.equals(CommandConstants.CMD_CANCEL)) {
			cgd.destroy();
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.swing.event.DocumentListener#changedUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		ChangePwdDialog cgd = ChangePwdDialog.getInstance();
		if (command.equals(CommandConstants.CMD_OLD_PWD)) {
			String oldpwd = null;
			int len = e.getDocument().getLength();
			try {
				oldpwd = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (oldpwd.trim().equals(SystemConstants.EMPTY_STRING)) {
				cgd.changeStatus(CommandConstants.CMD_OLD_PWD,
						MessageConstants.ENTER_OLD_PWD, Color.RED);
				cgd.setCondition(0, false);
				return;
			} else {
				cgd.changeStatus(CommandConstants.CMD_OLD_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				cgd.setCondition(0, true);
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
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				cgd.setCondition(1, false);
			} else {
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				cgd.setCondition(1, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_NEW_PWD_CONFIRM)) {
			String newPwdConf = cgd.getNewPassword();
			String cpassword = null;
			int len = e.getDocument().getLength();
			try {
				cpassword = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (newPwdConf.trim().equals("")) {
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				cgd.setCondition(2, false);
				return;
			}
			if (!cpassword.equals(newPwdConf)) {
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.DIFFERENT_PASSWORD, Color.RED);
				cgd.setCondition(2, false);
			} else {
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.AVAILABLE, Color.GREEN);
				cgd.setCondition(2, true);
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
		ChangePwdDialog cgd = ChangePwdDialog.getInstance();
		if (command.equals(CommandConstants.CMD_OLD_PWD)) {
			String oldpwd = null;
			int len = e.getDocument().getLength();
			try {
				oldpwd = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (oldpwd.trim().equals(SystemConstants.EMPTY_STRING)) {
				cgd.changeStatus(CommandConstants.CMD_OLD_PWD,
						MessageConstants.ENTER_OLD_PWD, Color.RED);
				cgd.setCondition(0, false);
				return;
			} else {
				cgd.changeStatus(CommandConstants.CMD_OLD_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				cgd.setCondition(0, true);
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
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				cgd.setCondition(1, false);
			} else {
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				cgd.setCondition(1, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_NEW_PWD_CONFIRM)) {
			String newPwdConf = cgd.getNewPassword();
			String cpassword = null;
			int len = e.getDocument().getLength();
			try {
				cpassword = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (newPwdConf.trim().equals("")) {
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				cgd.setCondition(2, false);
				return;
			}
			if (!cpassword.equals(newPwdConf)) {
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.DIFFERENT_PASSWORD, Color.RED);
				cgd.setCondition(2, false);
			} else {
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.AVAILABLE, Color.GREEN);
				cgd.setCondition(2, true);
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
		ChangePwdDialog cgd = ChangePwdDialog.getInstance();
		if (command.equals(CommandConstants.CMD_OLD_PWD)) {
			String oldpwd = null;
			int len = e.getDocument().getLength();
			try {
				oldpwd = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (oldpwd.trim().equals(SystemConstants.EMPTY_STRING)) {
				cgd.changeStatus(CommandConstants.CMD_OLD_PWD,
						MessageConstants.ENTER_OLD_PWD, Color.RED);
				cgd.setCondition(0, false);
				return;
			} else {
				cgd.changeStatus(CommandConstants.CMD_OLD_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				cgd.setCondition(0, true);
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
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				cgd.setCondition(1, false);
			} else {
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD,
						MessageConstants.AVAILABLE, Color.GREEN);
				cgd.setCondition(1, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_NEW_PWD_CONFIRM)) {
			String newPwdConf = cgd.getNewPassword();
			String cpassword = null;
			int len = e.getDocument().getLength();
			try {
				cpassword = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (newPwdConf.trim().equals("")) {
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				cgd.setCondition(2, false);
				return;
			}
			if (!cpassword.equals(newPwdConf)) {
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.DIFFERENT_PASSWORD, Color.RED);
				cgd.setCondition(2, false);
			} else {
				cgd.changeStatus(CommandConstants.CMD_NEW_PWD_CONFIRM,
						MessageConstants.AVAILABLE, Color.GREEN);
				cgd.setCondition(2, true);
			}
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

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		ChangePwdDialog.getInstance().destroy();
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
