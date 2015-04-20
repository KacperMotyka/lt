/**
 * @文件名称: CheckCptListener.java
 * @类路径:   com.rb.lottery.UI.listener
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-24 下午11:18:08
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import com.rb.lottery.client.UI.dialog.ChangePwdDialog;
import com.rb.lottery.client.UI.dialog.CheckCptDialog;
import com.rb.lottery.client.UI.dialog.CryptoguardDialog;
import com.rb.lottery.client.UI.dialog.PayPasswordDialog;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.manager.SocketManager;
import com.rb.lottery.user.UserSfQa;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-24 下午11:18:08
 * @版本: 1.0.0
 */

public class CheckCptListener implements ActionListener, DocumentListener,
		WindowListener {

	private String command;

	public CheckCptListener() {
		super();
	}

	public CheckCptListener(String cmd) {
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
		CheckCptDialog cpdDialog = CheckCptDialog.getInstance();
		if (cmd.equals(SystemConstants.EMPTY_STRING)) {
			cmd = e.getActionCommand();
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_REFRESH)) {
			cpdDialog.refreshQuestion();
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_NEXT)) {
			List<UserSfQa> sfqas = cpdDialog.getSafeQas();
			SocketManager som = SocketManager.getInstance();	
			boolean isPass = som.checkSfQa(sfqas);
			if (isPass) {
				String nextTo = cpdDialog.getNextTo();
				cpdDialog.destroy();
				if (nextTo.equals(SystemConstants.NEXT_CHANGEPWD)) {
					ChangePwdDialog chgpwdDialog = ChangePwdDialog
							.getInstance();
					chgpwdDialog.setVisible(true);
				} else if (nextTo.equals(SystemConstants.NEXT_CHANGEPAYPWD)) {
					PayPasswordDialog ppdDialog = PayPasswordDialog
							.getInstance();
					ppdDialog.setVisible(true);
				} else if (nextTo.equals(SystemConstants.NEXT_CHANGECPD)) {
					CryptoguardDialog cryDialog = CryptoguardDialog
							.getInstance();
					cryDialog.setVisible(true);
				}

			} else {
				JOptionPane.showMessageDialog(null, "密保验证失败");
				cpdDialog.refreshQuestion();
			}
			return;
		}
		if (cmd.equalsIgnoreCase(CommandConstants.CMD_CANCEL)) {
			cpdDialog.destroy();
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
		CheckCptDialog ccptDialog = CheckCptDialog.getInstance();
		int cmdlen = command.length();
		String dcmd = command.substring(0, cmdlen - 1);
		int index = Integer.parseInt(command.substring(cmdlen - 1));
		if (dcmd.equals("question")) {
			String question = null;
			int len = e.getDocument().getLength();
			try {
				question = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (question.equals(SystemConstants.EMPTY_STRING)) {
				ccptDialog.changeStatus(command, MessageConstants.ENTER_ANSWER,
						Color.RED);
				ccptDialog.setCondition(index, false);
			} else {
				ccptDialog.changeStatus(command, MessageConstants.AVAILABLE,
						Color.GREEN);
				ccptDialog.setCondition(index, true);
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
		CheckCptDialog ccptDialog = CheckCptDialog.getInstance();
		int cmdlen = command.length();
		String dcmd = command.substring(0, cmdlen - 1);
		int index = Integer.parseInt(command.substring(cmdlen - 1));
		if (dcmd.equals("question")) {
			String question = null;
			int len = e.getDocument().getLength();
			try {
				question = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (question.equals(SystemConstants.EMPTY_STRING)) {
				ccptDialog.changeStatus(command, MessageConstants.ENTER_ANSWER,
						Color.RED);
				ccptDialog.setCondition(index, false);
			} else {
				ccptDialog.changeStatus(command, MessageConstants.AVAILABLE,
						Color.GREEN);
				ccptDialog.setCondition(index, true);
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
		CheckCptDialog ccptDialog = CheckCptDialog.getInstance();
		int cmdlen = command.length();
		String dcmd = command.substring(0, cmdlen - 1);
		int index = Integer.parseInt(command.substring(cmdlen - 1));
		if (dcmd.equals("question")) {
			String question = null;
			int len = e.getDocument().getLength();
			try {
				question = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			if (question.equals(SystemConstants.EMPTY_STRING)) {
				ccptDialog.changeStatus(command, MessageConstants.ENTER_ANSWER,
						Color.RED);
				ccptDialog.setCondition(index, false);
			} else {
				ccptDialog.changeStatus(command, MessageConstants.AVAILABLE,
						Color.GREEN);
				ccptDialog.setCondition(index, true);
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
		CheckCptDialog.getInstance().destroy();
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
