/**
 * @文件名称: LotteryDantuoPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午04:15:56
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.LayoutManager;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 主界面=>工作区=>投注区面板=>投注设置面板=>胆拖页
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午04:15:56
 * @版本:       1.0.0
 */

public class LotteryDantuoPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -3338275139218034265L;

	private ButtonGroup danGroup;
	private JRadioButton commondan;
	private JRadioButton groupdan;
	private JRadioButton groupaddan;
	private JTextField commondanText;
	private JTextField groupdanText;
	private JTextField groupaddanText;
	private JButton commondanBtn;
	private JButton groupdanBtn;
	private JButton groupaddanBtn;
	
	public LotteryDantuoPanel() {
		super();
		init();
	}

	public LotteryDantuoPanel(LayoutManager layout) {
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

		danGroup = new ButtonGroup();
		commondan = new JRadioButton(SystemConstants.COMMON_DAN);
		commondan.setPreferredSize(new java.awt.Dimension(80, 13));
		groupdan = new JRadioButton(SystemConstants.GROUP_DAN);
		groupdan.setPreferredSize(new java.awt.Dimension(80, 13));
		groupaddan = new JRadioButton(SystemConstants.GROUP_AD_DAN);
		groupaddan.setPreferredSize(new java.awt.Dimension(80, 13));
		commondanText = new JTextField();
		commondanText.setPreferredSize(new java.awt.Dimension(180, 13));
		commondanText.setEditable(false);
		commondanText.setEnabled(false);
		groupdanText = new JTextField();
		groupdanText.setPreferredSize(new java.awt.Dimension(180, 13));
		groupdanText.setEditable(false);
		groupdanText.setEnabled(false);
		groupaddanText = new JTextField();
		groupaddanText.setPreferredSize(new java.awt.Dimension(180, 13));
		groupaddanText.setEditable(false);
		groupaddanText.setEnabled(false);
		commondanBtn = new JButton(new ImageIcon(
				FilePathConstants.EDIT_BUTTON_IMG));
		commondanBtn.setPreferredSize(new java.awt.Dimension(20, 13));
		groupdanBtn = new JButton(
				new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
		groupdanBtn.setPreferredSize(new java.awt.Dimension(20, 13));
		groupaddanBtn = new JButton(new ImageIcon(
				FilePathConstants.EDIT_BUTTON_IMG));
		groupaddanBtn.setPreferredSize(new java.awt.Dimension(20, 13));

		danGroup.add(commondan);
		danGroup.add(groupdan);
		danGroup.add(groupaddan);
		this.add(commondan);
		this.add(commondanText);
		this.add(commondanBtn);
		this.add(groupdan);
		this.add(groupdanText);
		this.add(groupdanBtn);
		this.add(groupaddan);
		this.add(groupaddanText);
		this.add(groupaddanBtn);
	}
}
