/**
 * @文件名称: ExitSaveDialog.java
 * @类路径:   com.rb.lottery.UI.dialog
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-20 下午05:28:20
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.dialog;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.rb.lottery.client.common.FontConstants;
import com.rb.lottery.client.common.MessageConstants;

/**
 * @类功能说明: 系统退出保存
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-20 下午05:28:20
 * @版本: 1.0.0
 */

@SuppressWarnings("serial")
public class ExitSaveDialog extends JFrame {

	private JPanel messagePane;
	private JLabel message;

	public ExitSaveDialog() {
		messagePane = new JPanel();
		message = new JLabel(MessageConstants.EXIT_SAVING);
		message.setFont(FontConstants.EXIT_SAVE_FONT);
		message.setForeground(Color.BLUE);
		messagePane.add(message);
		this.add(messagePane);

		this.setUndecorated(true);
		this.setPreferredSize(new java.awt.Dimension(120, 35));
		this.setLocation(500, 450);
		this.pack();
		this.setVisible(false);
	}

	public void destroy() {
		this.removeAll();
		this.dispose();
	}
}
