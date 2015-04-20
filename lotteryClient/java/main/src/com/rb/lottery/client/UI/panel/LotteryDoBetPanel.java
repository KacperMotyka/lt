/**
 * @文件名称: LotteryDoBetPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午04:29:40
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.LayoutManager;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.rb.lottery.client.common.ColorConstants;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FontConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.handler.ProjectHandler;
import com.rb.lottery.system.SystemCache;


/**
 * @类功能说明: 主界面=>工作区=>投注区面板=>投注操作区
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午04:29:40
 * @版本:       1.0.0
 */

public class LotteryDoBetPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 8883406428882459729L;
	
	private JCheckBox newBascket;
	private JButton passway;
	private JButton dopart;
	private JButton dobet;

	public LotteryDoBetPanel() {
		super();
		init();
	}
	
	public LotteryDoBetPanel(LayoutManager layout) {
		super(layout);
		init();
	}

	/**
	 * @方法说明: 
	 * @参数:     
	 * @return    void
	 * @throws
	 */
	private void init() {
		this.setPreferredSize(new java.awt.Dimension(314, 35));
		this.setBorder(null);

		newBascket = new JCheckBox(SystemConstants.NEW_BASCKET);
		newBascket.setFont(FontConstants.DEFAULT_FONT);
		// newBascket.setPreferredSize(new java.awt.Dimension(90, 25));
		this.add(newBascket);
		passway = new JButton(SystemConstants.PASSWAY);
		passway.setBackground(ColorConstants.BUTTON_COLOR);
		passway.setFont(FontConstants.DEFAULT_FONT);
		passway.addActionListener(new ProjectHandler(
				CommandConstants.CMD_CHOOSE_PASSWAY));
		int type = SystemCache.currentType;
		if (type < 2) {
			passway.setVisible(false);
		}
		// passway.setPreferredSize(new java.awt.Dimension(75, 25));
		this.add(passway);
		dopart = new JButton(SystemConstants.DATA_PART);
		// dataEvl.setPreferredSize(new java.awt.Dimension(75, 25));
		dopart.setBackground(ColorConstants.BUTTON_COLOR);
		dopart.setFont(FontConstants.DEFAULT_FONT);
		this.add(dopart);
		dobet = new JButton(SystemConstants.DO_BET);
		// dobet.setPreferredSize(new java.awt.Dimension(60, 25));
		dobet.setBackground(ColorConstants.BUTTON_COLOR);
		dobet.setFont(FontConstants.DEFAULT_FONT);
		this.add(dobet);
	}
	
	/**
	 * @方法说明: 获取按钮位置
	 * @参数: @return
	 * @return Point
	 * @throws
	 */
	public Point getPasswayButtonLocation() {
		return passway.getLocation();
	}
}
