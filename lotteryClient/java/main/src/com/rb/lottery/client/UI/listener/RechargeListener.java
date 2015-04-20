/**
 * @文件名称: RechargeListener.java
 * @类路径:   com.rb.lottery.UI.listener
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-26 下午08:34:08
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import org.apache.log4j.Logger;

import com.rb.lottery.client.UI.dialog.RechargeDialog;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.RegexConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.manager.SocketManager;
import com.rb.lottery.domain.CapitalInfo;
import com.rb.lottery.domain.TradeInfo;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;
import com.rb.lottery.user.User;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-26 下午08:34:08
 * @版本: 1.0.0
 */

public class RechargeListener implements ActionListener, DocumentListener,
		WindowListener {

	private static final Logger log = Logger.getLogger(RechargeListener.class);

	private String command;

	public RechargeListener() {
		super();
	}

	public RechargeListener(String cmd) {
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
		RechargeDialog rgd = RechargeDialog.getInstance();
		if (cmd.equals(SystemConstants.EMPTY_STRING)) {
			cmd = e.getActionCommand();
		}
		if (cmd.equals(CommandConstants.CMD_RECHARGE)) {
			ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
			int isOK = JOptionPane.showConfirmDialog(null,
					MessageConstants.RECHARGE_CONFIRM,
					MessageConstants.INFOMATION, JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			if (isOK == 1) {
				return;
			}
			SocketManager som = SocketManager.getInstance();
			double amount = Double.parseDouble(rgd.getAmount());
			User user = SystemCache.currentUser;
			CapitalInfo cap = som.getCapitalInfoByUserId(user.getUserid());
			double remain = cap.getRemain() + amount;
			cap.setRemain(remain);
			double available = cap.getAvailable() + amount;
			cap.setAvailable(available);
			cap.setAlladd(cap.getAlladd() + amount);
			cap.setPoints(cap.getPoints()
					+ Math.round(amount
							/ SystemEnvironment.getInstance().recharge_factor));
			boolean isSuccess = som.updateCapitalInfo(cap);
			TradeInfo trade = new TradeInfo();
			trade.setUser(SystemCache.currentUser);
			trade.setAmount(amount);
			trade.setInout(SystemConstants.TRADE_IN);
			trade.setType(Long.valueOf(3)); // 充值
			String message = null;
			if (isSuccess) {
				SystemCache.availableMoney += amount;
				SystemCache.recharge -= amount;
				trade.setStatus(Long.valueOf(1)); // 成功
				trade.setRemain(remain);

				message = "充值" + amount + "元成功";
				rgd.destroy();
			} else {
				trade.setStatus(Long.valueOf(0)); // 失败
				trade.setRemain(remain - amount);
				message = "充值" + amount + "元失败";
			}
			trade.setRemark(message);
			// 添加交易记录
			boolean isSucess = som.addTradeInfo(trade);
			if (!isSucess) {
				log.info("添加充值交易记录失败!");
			}
			JOptionPane.showMessageDialog(null, message);
			return;
		}
		if (cmd.equals(CommandConstants.CMD_CANCEL)) {
			rgd.destroy();
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
		RechargeDialog rgd = RechargeDialog.getInstance();
		if (command.equals(CommandConstants.CMD_RECHARGE_AMOUNT)) {
			String amount = null;
			int len = e.getDocument().getLength();
			try {
				amount = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			amount = amount.trim();
			if (amount.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_RECHARGE_AMOUNT,
						MessageConstants.ENTER_RECHARGE_AMOUNT, Color.RED);
				rgd.setCondition(0, false);
				return;
			} else {
				if (amount.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
					double d = Double.parseDouble(amount);
					// 非超级用户，且大于可充值额
					if (SystemCache.currentUser.getType() > 0
							&& d > SystemCache.recharge) {
						rgd.changeStatus(CommandConstants.CMD_RECHARGE_AMOUNT,
								SystemCache.recharge + "充值可用", Color.RED);
						rgd.setCondition(0, false);
					} else {
						rgd.changeStatus(CommandConstants.CMD_RECHARGE_AMOUNT,
								MessageConstants.AVAILABLE, Color.GREEN);
						rgd.setCondition(0, true);
					}

				} else {
					rgd.changeStatus(CommandConstants.CMD_RECHARGE_AMOUNT,
							MessageConstants.INVALID_NUMBER, Color.RED);
					rgd.setCondition(0, false);
				}
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
		RechargeDialog rgd = RechargeDialog.getInstance();
		if (command.equals(CommandConstants.CMD_RECHARGE_AMOUNT)) {
			String amount = null;
			int len = e.getDocument().getLength();
			try {
				amount = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			amount = amount.trim();
			if (amount.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_RECHARGE_AMOUNT,
						MessageConstants.ENTER_RECHARGE_AMOUNT, Color.RED);
				rgd.setCondition(0, false);
				return;
			} else {
				if (amount.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
					double d = Double.parseDouble(amount);
					// 非超级用户，且大于可充值额
					if (SystemCache.currentUser.getType() > 0
							&& d > SystemCache.recharge) {
						rgd.changeStatus(CommandConstants.CMD_RECHARGE_AMOUNT,
								SystemCache.recharge + "充值可用", Color.RED);
						rgd.setCondition(0, false);
					} else {
						rgd.changeStatus(CommandConstants.CMD_RECHARGE_AMOUNT,
								MessageConstants.AVAILABLE, Color.GREEN);
						rgd.setCondition(0, true);
					}

				} else {
					rgd.changeStatus(CommandConstants.CMD_RECHARGE_AMOUNT,
							MessageConstants.INVALID_NUMBER, Color.RED);
					rgd.setCondition(0, false);
				}
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
		RechargeDialog rgd = RechargeDialog.getInstance();
		if (command.equals(CommandConstants.CMD_RECHARGE_AMOUNT)) {
			String amount = null;
			int len = e.getDocument().getLength();
			try {
				amount = e.getDocument().getText(0, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			amount = amount.trim();
			if (amount.equals(SystemConstants.EMPTY_STRING)) {
				rgd.changeStatus(CommandConstants.CMD_RECHARGE_AMOUNT,
						MessageConstants.ENTER_RECHARGE_AMOUNT, Color.RED);
				rgd.setCondition(0, false);
				return;
			} else {
				if (amount.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
					double d = Double.parseDouble(amount);
					// 非超级用户，且大于可充值额
					if (SystemCache.currentUser.getType() > 0
							&& d > SystemCache.recharge) {
						rgd.changeStatus(CommandConstants.CMD_RECHARGE_AMOUNT,
								SystemCache.recharge + "充值可用", Color.RED);
						rgd.setCondition(0, false);
					} else {
						rgd.changeStatus(CommandConstants.CMD_RECHARGE_AMOUNT,
								MessageConstants.AVAILABLE, Color.GREEN);
						rgd.setCondition(0, true);
					}

				} else {
					rgd.changeStatus(CommandConstants.CMD_RECHARGE_AMOUNT,
							MessageConstants.INVALID_NUMBER, Color.RED);
					rgd.setCondition(0, false);
				}
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
		RechargeDialog.getInstance().destroy();
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
