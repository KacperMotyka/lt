/**
 * @文件名称: LotteryFilterSetPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午05:17:30
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.rb.lottery.client.common.ColorConstants;
import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午05:17:30
 * @版本:       1.0.0
 */

public class LotteryFilterSetPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -2092074110603142533L;

	private ButtonGroup setGroup;
	private JRadioButton unionSet;
	private JRadioButton mixedSet;
	private JRadioButton minusSet;
	private JRadioButton diffSet;
	private JButton advanceSet;
	private JButton clearSet;
	private JCheckBox askType;
	
	public LotteryFilterSetPanel() {
		super();
		init();
	}
	
	public LotteryFilterSetPanel(LayoutManager layout) {
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
		this.setPreferredSize(new java.awt.Dimension(300, 420));

		JPanel top_pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		top_pane.setPreferredSize(new java.awt.Dimension(280, 80));
		setGroup = new ButtonGroup();
		JPanel setPane = new JPanel(new GridLayout(2, 2));
		setPane.setBorder(new TitledBorder(SystemConstants.FILTER_CALC_RULE));
		unionSet = new JRadioButton(SystemConstants.FILTER_UNION_SET);
		mixedSet = new JRadioButton(SystemConstants.FILTER_MIXED_SET);
		minusSet = new JRadioButton(SystemConstants.FILTER_MINUS_SET);
		diffSet = new JRadioButton(SystemConstants.FILTER_DIFF_SET);
		setGroup.add(unionSet);
		setGroup.add(mixedSet);
		setGroup.add(minusSet);
		setGroup.add(diffSet);
		setPane.add(unionSet);
		setPane.add(mixedSet);
		setPane.add(minusSet);
		setPane.add(diffSet);
		top_pane.add(setPane);
		JPanel bntPane = new JPanel(new GridLayout(3, 1));
		advanceSet = new JButton(SystemConstants.FILTER_ADVANCE_SET);
		advanceSet.setBackground(ColorConstants.BUTTON_COLOR);
		advanceSet.setPreferredSize(new java.awt.Dimension(120, 20));
		bntPane.add(advanceSet);
		clearSet = new JButton(SystemConstants.FILTER_CLEAR_SET);
		clearSet.setPreferredSize(new java.awt.Dimension(60, 20));
		clearSet.setEnabled(false);
		bntPane.add(clearSet);
		askType = new JCheckBox(SystemConstants.FILTER_ASK_TYPE);
		askType.setPreferredSize(new java.awt.Dimension(60, 20));
		bntPane.add(askType);
		top_pane.add(bntPane);
		this.add(top_pane);

		JPanel bottom_pane = new JPanel();
		bottom_pane.setPreferredSize(new java.awt.Dimension(283, 295));
		bottom_pane.setBorder(new EtchedBorder());
		this.add(bottom_pane);
	}
}
