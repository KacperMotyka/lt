/**
 * @文件名称: RegisterListener.java
 * @类路径:   com.rb.lottery.UI.listener
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-22 下午03:50:09
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import com.rb.lottery.client.UI.dialog.RegisterDialog;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.RegexConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.manager.SocketManager;
import com.rb.lottery.client.util.Base64Coder;
import com.rb.lottery.user.User;
import com.rb.lottery.user.UserInfo;

/**
 * @类功能说明: 注册窗口监听器
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-22 下午03:50:09
 * @版本: 1.0.0
 */

public class RegisterListener implements ActionListener, ItemListener,
		WindowListener, DocumentListener, FocusListener {

	private String command;

	public RegisterListener() {
		super();
	}

	public RegisterListener(String cmd) {
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
		RegisterDialog rgd = RegisterDialog.getInstance();
		if (cmd.equals(SystemConstants.EMPTY_STRING)) {
			cmd = e.getActionCommand();
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_OBEY)) {
			if (rgd.isObey()) {
				rgd.setCondition(18, true);
			} else {
				rgd.setCondition(18, false);
			}
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_REGISTER)) {
			ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
			int isOK = JOptionPane.showConfirmDialog(null,
					MessageConstants.REGISTER_CONFIRM,
					MessageConstants.INFOMATION, JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			if (isOK == 1) {
				return;
			}
			// 帐号
			User user = new User();
			String uid = UUID.randomUUID().toString();
			String username = rgd.getUserName();
			String password = rgd.getPassword();
			user.setUserid(uid);
			user.setUsername(username);
			user.setPassword(Base64Coder.base64EncryptAct(password));
			// 用户信息
			String cname = rgd.getChineseName();
			String ename = rgd.getEnglishName();
			Long sex = rgd.getSex();
			Long age = rgd.getAge();
			Date birthday = rgd.getBirthday();
			String birthplace = rgd.getBirthPlace();
			String address = rgd.getAddress();
			String post = rgd.getPost();
			String email = rgd.getEmail();
			String qq = rgd.getQQ();
			String phone = rgd.getPhone();
			String mobile = rgd.getMobile();
			String bloodtype = rgd.getBloodType();
			Double height = rgd.getUHeight();
			Double weight = rgd.getUWeight();
			UserInfo userInfo = new UserInfo(uid, user, cname, ename, sex, age,
					birthday, birthplace, address, post, email, qq, phone,
					mobile, bloodtype, height, weight);
			// TODO
			boolean register = false; // 注册帐号，与填写用户信息关联
			SocketManager som = SocketManager.getInstance();
			register = som.register(user, userInfo);
			if (register) {
				rgd.destroy();
				// MainFrame.getInstance().setFrameEnable(true);
				JOptionPane.showMessageDialog(null, "用户【" + username
						+ "】注册成功,请激活帐号!");
			} else {
				JOptionPane.showMessageDialog(null, "用户【" + username
						+ "】注册失败,请检查填写信息!");
			}
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_CANCEL)) {
			rgd.destroy();
			// MainFrame.getInstance().setFrameEnable(true);
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

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
		RegisterDialog.getInstance().destroy();
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
		RegisterDialog rgd = RegisterDialog.getInstance();
		if (command.equals(CommandConstants.CMD_USERNAME)) {
			String username = null;
			int len = e.getDocument().getLength();
			try {
				username = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			// String username = UserManager.registerDialog.getUserName();
			username = username.trim();
			if (username.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_USERNAME,
						MessageConstants.ENTER_USERNAME, Color.RED);
				rgd.setCondition(0, false);
				return;
			}
			boolean exist = SocketManager.getInstance().isUserExist(username);
			if (exist) {
				rgd.changeStatus(CommandConstants.CMD_USERNAME,
						MessageConstants.EXIST_USERNAME, Color.RED);
				rgd.setCondition(0, false);
			} else {
				rgd.changeStatus(CommandConstants.CMD_USERNAME,
						MessageConstants.AVAILABLE, Color.GREEN);
				rgd.setCondition(0, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_PASSWORD)) {
			String password = null;
			int len = e.getDocument().getLength();
			try {
				password = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			password = password.trim();
			if (password.length() < SystemConstants.PASSWORD_MIN_LENGTH) {
				rgd.changeStatus(CommandConstants.CMD_PASSWORD,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				rgd.setCondition(1, false);
			} else {
				rgd.changeStatus(CommandConstants.CMD_PASSWORD,
						MessageConstants.AVAILABLE, Color.GREEN);
				rgd.setCondition(1, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_PASSWORD_CONFIRM)) {
			String password = rgd.getPassword();
			String cpassword = null;
			int len = e.getDocument().getLength();
			try {
				cpassword = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (!cpassword.equals(password)) {
				rgd.changeStatus(CommandConstants.CMD_PASSWORD_CONFIRM,
						MessageConstants.DIFFERENT_PASSWORD, Color.RED);
				rgd.setCondition(2, false);
			} else {
				rgd.changeStatus(CommandConstants.CMD_PASSWORD_CONFIRM,
						MessageConstants.AVAILABLE, Color.GREEN);
				rgd.setCondition(2, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_CHINESE_NAME)) {
			String cname = null;
			int len = e.getDocument().getLength();
			try {
				cname = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			cname = cname.trim();
			if (cname.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_CHINESE_NAME,
						MessageConstants.ENTER_CNAME, Color.RED);
				rgd.setCondition(3, false);
				return;
			}
			if (cname.getBytes().length == 3 * cname.length()) {
				rgd.changeStatus(CommandConstants.CMD_CHINESE_NAME,
						MessageConstants.AVAILABLE, Color.GREEN);
				rgd.setCondition(3, true);
			} else {
				rgd.changeStatus(CommandConstants.CMD_CHINESE_NAME,
						MessageConstants.INVALID_CHINESE, Color.RED);
				rgd.setCondition(3, false);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_HEIGHT)) {
			String height = null;
			int len = e.getDocument().getLength();
			try {
				height = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			height = height.trim();
			if (height.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_HEIGHT,
						SystemConstants.PICK_FLAG, Color.BLACK);
				rgd.setCondition(16, true);
				return;
			}
			if (height.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				rgd.changeStatus(CommandConstants.CMD_HEIGHT,
						SystemConstants.PICK_FLAG, Color.BLACK);
				rgd.setCondition(16, true);
			} else {
				rgd.changeStatus(CommandConstants.CMD_HEIGHT,
						MessageConstants.INVALID_NUMBER, Color.RED);
				rgd.setCondition(16, false);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_WEIGHT)) {
			String weight = null;
			int len = e.getDocument().getLength();
			try {
				weight = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			weight = weight.trim();
			if (weight.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_WEIGHT,
						SystemConstants.PICK_FLAG, Color.BLACK);
				rgd.setCondition(17, true);
				return;
			}
			if (weight.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				rgd.changeStatus(CommandConstants.CMD_WEIGHT,
						SystemConstants.PICK_FLAG, Color.BLACK);
				rgd.setCondition(17, true);
			} else {
				rgd.changeStatus(CommandConstants.CMD_WEIGHT,
						MessageConstants.INVALID_NUMBER, Color.RED);
				rgd.setCondition(17, false);
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
		RegisterDialog rgd = RegisterDialog.getInstance();
		if (command.equals(CommandConstants.CMD_USERNAME)) {
			String username = null;
			int len = e.getDocument().getLength();
			try {
				username = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			// String username = UserManager.registerDialog.getUserName();
			username = username.trim();
			if (username.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_USERNAME,
						MessageConstants.ENTER_USERNAME, Color.RED);
				rgd.setCondition(0, false);
				return;
			}
			boolean exist = SocketManager.getInstance().isUserExist(username);
			if (exist) {
				rgd.changeStatus(CommandConstants.CMD_USERNAME,
						MessageConstants.EXIST_USERNAME, Color.RED);
				rgd.setCondition(0, false);
			} else {
				rgd.changeStatus(CommandConstants.CMD_USERNAME,
						MessageConstants.AVAILABLE, Color.GREEN);
				rgd.setCondition(0, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_PASSWORD)) {
			String password = null;
			int len = e.getDocument().getLength();
			try {
				password = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			password = password.trim();
			if (password.length() < SystemConstants.PASSWORD_MIN_LENGTH) {
				rgd.changeStatus(CommandConstants.CMD_PASSWORD,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				rgd.setCondition(1, false);
			} else {
				rgd.changeStatus(CommandConstants.CMD_PASSWORD,
						MessageConstants.AVAILABLE, Color.GREEN);
				rgd.setCondition(1, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_PASSWORD_CONFIRM)) {
			String password = rgd.getPassword();
			String cpassword = null;
			int len = e.getDocument().getLength();
			try {
				cpassword = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

			if (!cpassword.equals(password)) {
				rgd.changeStatus(CommandConstants.CMD_PASSWORD_CONFIRM,
						MessageConstants.DIFFERENT_PASSWORD, Color.RED);
				rgd.setCondition(2, false);
			} else {
				rgd.changeStatus(CommandConstants.CMD_PASSWORD_CONFIRM,
						MessageConstants.AVAILABLE, Color.GREEN);
				rgd.setCondition(2, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_CHINESE_NAME)) {
			String cname = null;
			int len = e.getDocument().getLength();
			try {
				cname = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			cname = cname.trim();
			if (cname.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_CHINESE_NAME,
						MessageConstants.ENTER_CNAME, Color.RED);
				rgd.setCondition(3, false);
				return;
			}
			if (cname.getBytes().length == 3 * cname.length()) {
				rgd.changeStatus(CommandConstants.CMD_CHINESE_NAME,
						MessageConstants.AVAILABLE, Color.GREEN);
				rgd.setCondition(3, true);
			} else {
				rgd.changeStatus(CommandConstants.CMD_CHINESE_NAME,
						MessageConstants.INVALID_CHINESE, Color.RED);
				rgd.setCondition(3, false);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_HEIGHT)) {
			String height = null;
			int len = e.getDocument().getLength();
			try {
				height = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			height = height.trim();
			if (height.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_HEIGHT,
						SystemConstants.PICK_FLAG, Color.BLACK);
				rgd.setCondition(16, true);
				return;
			}
			if (height.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				rgd.changeStatus(CommandConstants.CMD_HEIGHT,
						SystemConstants.PICK_FLAG, Color.BLACK);
				rgd.setCondition(16, true);
			} else {
				rgd.changeStatus(CommandConstants.CMD_HEIGHT,
						MessageConstants.INVALID_NUMBER, Color.RED);
				rgd.setCondition(16, false);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_WEIGHT)) {
			String weight = null;
			int len = e.getDocument().getLength();
			try {
				weight = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			weight = weight.trim();
			if (weight.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_WEIGHT,
						SystemConstants.PICK_FLAG, Color.BLACK);
				rgd.setCondition(17, true);
				return;
			}
			if (weight.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				rgd.changeStatus(CommandConstants.CMD_WEIGHT,
						SystemConstants.PICK_FLAG, Color.BLACK);
				rgd.setCondition(17, true);
			} else {
				rgd.changeStatus(CommandConstants.CMD_WEIGHT,
						MessageConstants.INVALID_NUMBER, Color.RED);
				rgd.setCondition(17, false);
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
		RegisterDialog rgd = RegisterDialog.getInstance();
		if (command.equals(CommandConstants.CMD_USERNAME)) {
			String username = null;
			int len = e.getDocument().getLength();
			try {
				username = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			// String username = UserManager.registerDialog.getUserName();
			username = username.trim();
			if (username.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_USERNAME,
						MessageConstants.ENTER_USERNAME, Color.RED);
				rgd.setCondition(0, false);
				return;
			}
			boolean exist = SocketManager.getInstance().isUserExist(username);
			if (exist) {
				rgd.changeStatus(CommandConstants.CMD_USERNAME,
						MessageConstants.EXIST_USERNAME, Color.RED);
				rgd.setCondition(0, false);
			} else {
				rgd.changeStatus(CommandConstants.CMD_USERNAME,
						MessageConstants.AVAILABLE, Color.GREEN);
				rgd.setCondition(0, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_PASSWORD)) {
			String password = null;
			int len = e.getDocument().getLength();
			try {
				password = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			password = password.trim();
			if (password.length() < SystemConstants.PASSWORD_MIN_LENGTH) {
				rgd.changeStatus(CommandConstants.CMD_PASSWORD,
						MessageConstants.SHORT_PASSWORD, Color.RED);
				rgd.setCondition(1, false);
			} else {
				rgd.changeStatus(CommandConstants.CMD_PASSWORD,
						MessageConstants.AVAILABLE, Color.GREEN);
				rgd.setCondition(1, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_PASSWORD_CONFIRM)) {
			String password = rgd.getPassword();
			String cpassword = null;
			int len = e.getDocument().getLength();
			try {
				cpassword = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

			if (!cpassword.equals(password)) {
				rgd.changeStatus(CommandConstants.CMD_PASSWORD_CONFIRM,
						MessageConstants.DIFFERENT_PASSWORD, Color.RED);
				rgd.setCondition(2, false);
			} else {
				rgd.changeStatus(CommandConstants.CMD_PASSWORD_CONFIRM,
						MessageConstants.AVAILABLE, Color.GREEN);
				rgd.setCondition(2, true);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_CHINESE_NAME)) {
			String cname = null;
			int len = e.getDocument().getLength();
			try {
				cname = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			cname = cname.trim();
			if (cname.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_CHINESE_NAME,
						MessageConstants.ENTER_CNAME, Color.RED);
				rgd.setCondition(3, false);
				return;
			}
			if (cname.getBytes().length == 3 * cname.length()) {
				rgd.changeStatus(CommandConstants.CMD_CHINESE_NAME,
						MessageConstants.AVAILABLE, Color.GREEN);
				rgd.setCondition(3, true);
			} else {
				rgd.changeStatus(CommandConstants.CMD_CHINESE_NAME,
						MessageConstants.INVALID_CHINESE, Color.RED);
				rgd.setCondition(3, false);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_HEIGHT)) {
			String height = null;
			int len = e.getDocument().getLength();
			try {
				height = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			height = height.trim();
			if (height.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_HEIGHT,
						SystemConstants.PICK_FLAG, Color.BLACK);
				rgd.setCondition(16, true);
				return;
			}
			if (height.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				rgd.changeStatus(CommandConstants.CMD_HEIGHT,
						SystemConstants.PICK_FLAG, Color.BLACK);
				rgd.setCondition(16, true);
			} else {
				rgd.changeStatus(CommandConstants.CMD_HEIGHT,
						MessageConstants.INVALID_NUMBER, Color.RED);
				rgd.setCondition(16, false);
			}
			return;
		}
		if (command.equals(CommandConstants.CMD_WEIGHT)) {
			String weight = null;
			int len = e.getDocument().getLength();
			try {
				weight = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			weight = weight.trim();
			if (weight.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_WEIGHT,
						SystemConstants.PICK_FLAG, Color.BLACK);
				rgd.setCondition(17, true);
				return;
			}
			if (weight.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				rgd.changeStatus(CommandConstants.CMD_WEIGHT,
						SystemConstants.PICK_FLAG, Color.BLACK);
				rgd.setCondition(17, true);
			} else {
				rgd.changeStatus(CommandConstants.CMD_WEIGHT,
						MessageConstants.INVALID_NUMBER, Color.RED);
				rgd.setCondition(17, false);
			}
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if (command.equals(CommandConstants.CMD_BIRTHDAY)) {
			RegisterDialog.getInstance().openDateChooser();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if (command.equals(CommandConstants.CMD_BIRTHDAY)) {
			RegisterDialog.getInstance().closeDateChooser();
		}
	}

}
