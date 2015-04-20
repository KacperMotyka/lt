/**
 * @文件名称: LotteryWaterFloodPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午04:21:47
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.LayoutManager;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 主界面=>工作区=>投注区面板=>投注设置面板=>注水页
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午04:21:47
 * @版本:       1.0.0
 */

public class LotteryWaterFloodPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -5410338910973808743L;
	
	private ButtonGroup wfGroup;
	private JRadioButton standarwf;
	private JRadioButton coldwf;
	private JRadioButton expandwf;
	private JComboBox leftwfComb;
	private JComboBox rightwfComb;
	private JComboBox expandwfComb;
	private JLabel multiOn;
	private JLabel onexpand;
	private JTextField coldwfText;
	private JButton coldwfBtn;

	public LotteryWaterFloodPanel() {
		super();
		init();
	}

	public LotteryWaterFloodPanel(LayoutManager layout) {
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

		wfGroup = new ButtonGroup();
		standarwf = new JRadioButton(SystemConstants.STANDAR_WATER_FLOOD);
		standarwf.setPreferredSize(new java.awt.Dimension(80, 13));
		coldwf = new JRadioButton(SystemConstants.COLD_WATER_FLOOD);
		coldwf.setPreferredSize(new java.awt.Dimension(80, 13));
		expandwf = new JRadioButton(SystemConstants.EXPAND_WATER_FLOOD);
		expandwf.setPreferredSize(new java.awt.Dimension(80, 13));
		leftwfComb = new JComboBox(SystemConstants.DO_MATCHES);
		leftwfComb.setPreferredSize(new java.awt.Dimension(60, 13));
		leftwfComb.setEditable(false);
		leftwfComb.setEnabled(false);
		rightwfComb = new JComboBox(SystemConstants.DO_MATCHES);
		rightwfComb.setPreferredSize(new java.awt.Dimension(60, 13));
		rightwfComb.setEditable(false);
		rightwfComb.setEnabled(false);
		expandwfComb = new JComboBox(SystemConstants.DO_MATCHES);
		expandwfComb.setPreferredSize(new java.awt.Dimension(50, 13));
		expandwfComb.setEditable(false);
		expandwfComb.setEnabled(false);
		multiOn = new JLabel(SystemConstants.MULTI_ON);
		multiOn.setPreferredSize(new java.awt.Dimension(80, 13));
		onexpand = new JLabel(SystemConstants.ON_ADD_MULTI);
		onexpand.setPreferredSize(new java.awt.Dimension(150, 13));
		coldwfText = new JTextField();
		coldwfText.setPreferredSize(new java.awt.Dimension(180, 13));
		coldwfText.setEditable(false);
		coldwfText.setEnabled(false);
		coldwfBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
		coldwfBtn.setPreferredSize(new java.awt.Dimension(20, 13));

		wfGroup.add(standarwf);
		wfGroup.add(coldwf);
		wfGroup.add(expandwf);
		this.add(standarwf);
		this.add(leftwfComb);
		this.add(multiOn);
		this.add(rightwfComb);
		this.add(coldwf);
		this.add(coldwfText);
		this.add(coldwfBtn);
		this.add(expandwf);
		this.add(onexpand);
		this.add(expandwfComb);
	}
}
