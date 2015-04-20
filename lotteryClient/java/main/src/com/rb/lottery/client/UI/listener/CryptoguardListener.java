/**
 * @文件名称: CryptoguardListener.java
 * @类路径:   com.rb.lottery.UI.listener
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-24 下午03:50:45
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.rb.lottery.client.UI.dialog.CryptoguardDialog;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.manager.SocketManager;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.user.UserSfQa;

/**
 * @类功能说明: 密保问题设置窗口监听
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-24 下午03:50:45
 * @版本: 1.0.0
 */

public class CryptoguardListener implements ActionListener, ItemListener,
		DocumentListener, WindowListener {

	private String command;

	public CryptoguardListener() {
		super();
	}

	public CryptoguardListener(String cmd) {
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
		CryptoguardDialog cpdDialog = CryptoguardDialog.getInstance();
		if (cmd.equals(SystemConstants.EMPTY_STRING)) {
			cmd = e.getActionCommand();
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_COMMIT)) {
			ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
			int isOK = JOptionPane.showConfirmDialog(null,
					MessageConstants.CPD_CONFIRM, MessageConstants.INFOMATION,
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
					icon);
			if (isOK == 1) {
				return;
			}
			List<UserSfQa> qas = new ArrayList<UserSfQa>();
			// QA1
			String question1 = cpdDialog.getSelectText1();
			String answer1 = cpdDialog.getAnswer(1);
			if (question1.equals("自定义问题...")) {
				question1 = cpdDialog.getOwnQuestion1();
			} else if (question1.equals("请选择")) {
				question1 = "";
			}
			if ((!question1.equals("")) && (!answer1.equals(""))) {
				UserSfQa sfqa = new UserSfQa();
				sfqa.setUser(SystemCache.currentUser);
				sfqa.setQuestion(question1);
				sfqa.setAnswer(answer1);
				qas.add(sfqa);
			}
			// QA2
			String question2 = cpdDialog.getSelectText2();
			String answer2 = cpdDialog.getAnswer(2);
			if (question2.equals("自定义问题...")) {
				question2 = cpdDialog.getOwnQuestion2();
			} else if (question2.equals("请选择")) {
				question2 = "";
			}
			if ((!question2.equals("")) && (!answer2.equals(""))) {
				UserSfQa sfqa = new UserSfQa();
				sfqa.setUser(SystemCache.currentUser);
				sfqa.setQuestion(question2);
				sfqa.setAnswer(answer2);
				qas.add(sfqa);
			}
			// QA3
			String question3 = cpdDialog.getSelectText3();
			String answer3 = cpdDialog.getAnswer(3);
			if (question3.equals("自定义问题...")) {
				question3 = cpdDialog.getOwnQuestion3();
			} else if (question3.equals("请选择")) {
				question3 = "";
			}
			if ((!question3.equals("")) && (!answer3.equals(""))) {
				UserSfQa sfqa = new UserSfQa();
				sfqa.setUser(SystemCache.currentUser);
				sfqa.setQuestion(question3);
				sfqa.setAnswer(answer3);
				qas.add(sfqa);
			}
			// 更新用户密保问题，先删除原密保问题
			SocketManager som = SocketManager.getInstance();
			boolean status = som.updateUserSfQa(qas);
			if (status) {
				cpdDialog.destroy();
				// MainFrame.getInstance().setFrameEnable(true);
				JOptionPane.showMessageDialog(null, "密保问题设置成功");
			} else {
				JOptionPane.showMessageDialog(null, "密保问题设置失败");
			}
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_CANCEL)) {
			cpdDialog.destroy();
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
		// origin
		// String item = e.getItem().toString();
		// 选项改变分2步,A选择取消，B选择选中
		// 选择选中事件
		if (e.getStateChange() == ItemEvent.SELECTED) {
			CryptoguardDialog.getInstance().checkSelectOwn(command);
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
		CryptoguardDialog cpdDialog = CryptoguardDialog.getInstance();
		int cmdlen = command.length();
		String dcmd = command.substring(0, cmdlen - 1);
		int index = Integer.parseInt(command.substring(cmdlen - 1));
		if (dcmd.equals(CommandConstants.CMD_QUESTION)
				|| dcmd.equals(CommandConstants.CMD_ANSWER)) {
			String question = cpdDialog.getQuestion(index);
			String answer = cpdDialog.getAnswer(index);
			if (question.equals(SystemConstants.EMPTY_STRING)
					|| answer.equals(SystemConstants.EMPTY_STRING)) {
				cpdDialog.setCondition(index - 1, false);
				return;
			} else {
				cpdDialog.setCondition(index - 1, true);
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
		CryptoguardDialog cpdDialog = CryptoguardDialog.getInstance();
		int cmdlen = command.length();
		String dcmd = command.substring(0, cmdlen - 1);
		int index = Integer.parseInt(command.substring(cmdlen - 1));
		if (dcmd.equals(CommandConstants.CMD_QUESTION)
				|| dcmd.equals(CommandConstants.CMD_ANSWER)) {
			String question = cpdDialog.getQuestion(index);
			String answer = cpdDialog.getAnswer(index);
			if (question.equals(SystemConstants.EMPTY_STRING)
					|| answer.equals(SystemConstants.EMPTY_STRING)) {
				cpdDialog.setCondition(index - 1, false);
				return;
			} else {
				cpdDialog.setCondition(index - 1, true);
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
		CryptoguardDialog cpdDialog = CryptoguardDialog.getInstance();
		int cmdlen = command.length();
		String dcmd = command.substring(0, cmdlen - 1);
		int index = Integer.parseInt(command.substring(cmdlen - 1));
		if (dcmd.equals(CommandConstants.CMD_QUESTION)
				|| dcmd.equals(CommandConstants.CMD_ANSWER)) {
			String question = cpdDialog.getQuestion(index);
			String answer = cpdDialog.getAnswer(index);
			if (question.equals(SystemConstants.EMPTY_STRING)
					|| answer.equals(SystemConstants.EMPTY_STRING)) {
				cpdDialog.setCondition(index - 1, false);
				return;
			} else {
				cpdDialog.setCondition(index - 1, true);
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
		CryptoguardDialog.getInstance().destroy();
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
