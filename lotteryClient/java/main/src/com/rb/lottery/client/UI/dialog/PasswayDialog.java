/**
 * @文件名称: PasswayFrame.java
 * @类路径:   com.rb.lottery.UI.dialog
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-11 上午10:49:43
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.dialog;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.rb.lottery.client.UI.main.MainFrame;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.common.SystemFunctions;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemProcessor;

/**
 * @类功能说明: 过关方式选择框
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-11 上午10:49:43
 * @版本: 1.0.0
 */

public class PasswayDialog extends JFrame implements ActionListener {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 973477428003786315L;

	private static final int BOX_PER_ROW = 5;

	private List<String> oldpassway = SystemProcessor.getLottery(
			SystemCache.currentLotteryId).getPassway();
	private List<String> newpassway = new ArrayList<String>(oldpassway);

	public PasswayDialog(int count) {
		List<String> chuans = SystemFunctions.getChuanByMatchCount(count);
		int size = 2;
		if (chuans != null) {
			size += chuans.size();
		}
		int left = (size % BOX_PER_ROW == 0) ? 0 : 1;
		int rows = size / BOX_PER_ROW + left;
		JPanel panel = new JPanel(new GridLayout(rows, BOX_PER_ROW));
		for (String chuan : chuans) {
			JCheckBox jcb = new JCheckBox(chuan);
			if (oldpassway.contains(chuan)) {
				jcb.setSelected(true);
			}
			jcb.addActionListener(this);
			panel.add(jcb);
		}
		JButton ok = new JButton(SystemConstants.CONFIRM);
		ok.addActionListener(this);
		JButton cancel = new JButton(SystemConstants.CANCEL);
		cancel.addActionListener(this);
		panel.add(ok);
		panel.add(cancel);
		this.add(panel);
		this.setTitle(MessageConstants.SELECT_PASSWAY);
		Image image = Toolkit.getDefaultToolkit().getImage(
				FilePathConstants.LOTTERY_IMG_FILE);
		this.setIconImage(image);
		// Point location = MainFrame.getInstance().getPasswayButtonLocation();
		this.setLocation(500, 400);
		this.setPreferredSize(new java.awt.Dimension(342, 171));
		this.setUndecorated(true);
		this.pack();
		MainFrame.getInstance().setFrameEnable(false);
		// this.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals(SystemConstants.CANCEL)) {
			this.dispose();
			MainFrame.getInstance().setFrameEnable(true);
			// MainFrame.getInstance().setFrameFocus(true);
		} else if (cmd.equals(SystemConstants.CONFIRM)) {
			SystemProcessor.setLotteryPassway(SystemCache.currentLotteryId,
					newpassway);
			this.dispose();
			MainFrame.getInstance().setFrameEnable(true);
			MainFrame.getInstance().updateBetStatu();
			// MainFrame.getInstance().setFrameFocus(true);
		} else {
			JCheckBox src = (JCheckBox) e.getSource();
			boolean value = src.isSelected();
			if (value) {
				newpassway.add(cmd);
			} else {
				newpassway.remove(cmd);
			}
		}
	}
}
