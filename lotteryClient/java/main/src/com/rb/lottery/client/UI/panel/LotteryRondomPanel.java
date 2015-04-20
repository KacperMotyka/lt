/**
 * @文件名称: LotteryRondomPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午04:25:20
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.LayoutManager;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 主界面=>工作区=>投注区面板=>投注设置面板=>随机页
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午04:25:20
 * @版本:       1.0.0
 */

public class LotteryRondomPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -6769781110742463471L;

	private JCheckBox refOdds;
	private JCheckBox rondomTimes;
	private JTextField rondomTimesText;
	private JTextField rondomGetText;
	private JTextField rondomdanGetText;
	private JTextField danGetText;
	private ButtonGroup rondomGroup;
	private JRadioButton rondomGet;
	private JRadioButton danGet;
	private JButton danGetBtn;
	
	public LotteryRondomPanel() {
		super();
		init();
	}

	public LotteryRondomPanel(LayoutManager layout) {
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
		this.setBorder(new EtchedBorder());
		this.setPreferredSize(new java.awt.Dimension(314, 85));

		refOdds = new JCheckBox(SystemConstants.REF_ODDS);
		refOdds.setPreferredSize(new java.awt.Dimension(140, 13));
		rondomTimes = new JCheckBox(SystemConstants.RONDOM_TIMES);
		rondomTimes.setPreferredSize(new java.awt.Dimension(80, 13));
		rondomTimesText = new JTextField();
		rondomTimesText.setPreferredSize(new java.awt.Dimension(40, 13));
		rondomTimesText.setEditable(true);
		rondomTimesText.setEnabled(false);
		rondomGetText = new JTextField();
		rondomGetText.setPreferredSize(new java.awt.Dimension(185, 13));
		rondomGetText.setEditable(true);
		rondomGetText.setEnabled(false);
		rondomdanGetText = new JTextField();
		rondomdanGetText.setPreferredSize(new java.awt.Dimension(50, 13));
		rondomdanGetText.setEditable(true);
		rondomdanGetText.setEnabled(false);
		danGetText = new JTextField();
		danGetText.setPreferredSize(new java.awt.Dimension(110, 13));
		danGetText.setEditable(false);
		danGetText.setEnabled(false);
		rondomGroup = new ButtonGroup();
		rondomGet = new JRadioButton(SystemConstants.RONDOM_GET);
		rondomGet.setPreferredSize(new java.awt.Dimension(80, 13));
		danGet = new JRadioButton(SystemConstants.RONDOM_DAN_GET);
		danGet.setPreferredSize(new java.awt.Dimension(80, 13));
		danGetBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
		danGetBtn.setPreferredSize(new java.awt.Dimension(20, 13));

		rondomGroup.add(rondomGet);
		rondomGroup.add(danGet);
		this.add(refOdds);
		this.add(rondomTimes);
		this.add(rondomTimesText);
		this.add(rondomGet);
		this.add(rondomGetText);
		JLabel zhu1 = new JLabel(SystemConstants.BET);
		zhu1.setPreferredSize(new java.awt.Dimension(15, 13));
		this.add(zhu1);
		this.add(danGet);
		this.add(rondomdanGetText);
		JLabel zhu2 = new JLabel(SystemConstants.BET);
		zhu2.setPreferredSize(new java.awt.Dimension(15, 13));
		this.add(zhu2);
		this.add(danGetText);
		this.add(danGetBtn);
	}
}
