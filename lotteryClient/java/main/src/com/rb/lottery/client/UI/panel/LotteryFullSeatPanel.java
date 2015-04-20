/**
 * @文件名称: LotteryFullSeatPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午03:53:09
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;

import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.handler.FilterHandler;


/**
 * @类功能说明: 主界面=>工作区=>投注区面板=>投注设置面板=>全排页
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午03:53:09
 * @版本:       1.0.0
 */

public class LotteryFullSeatPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 4747145192395451884L;
	
	private ButtonGroup fullSeatGroup;
	private JRadioButton op_single;
	private JRadioButton op_small_multi;
	private JComboBox betnumlimit;

	public LotteryFullSeatPanel() {
		super();
		init();
	}
	
	public LotteryFullSeatPanel(LayoutManager layout) {
		super(layout);
		init();
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void init() {
		this.setBorder(new EtchedBorder());
		this.setPreferredSize(new java.awt.Dimension(314, 85));
		
		fullSeatGroup = new ButtonGroup();
		JPanel rpn = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// rpn.setBackground(SystemConstants.SYSTEM_DEFAULT_COLOR);
		op_single = new JRadioButton(SystemConstants.RADIO_SINGLE);
		op_single.setSelected(true);
		// op_single.setBackground(SystemConstants.SYSTEM_DEFAULT_COLOR);
		op_single.addActionListener(new FilterHandler(
				CommandConstants.CMD_SINGLE));
		fullSeatGroup.add(op_single);
		rpn.add(op_single);
		this.add(rpn);

		JPanel lpn = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// lpn.setBackground(SystemConstants.SYSTEM_DEFAULT_COLOR);
		op_small_multi = new JRadioButton(SystemConstants.RADIO_SML_MULTI);
		op_small_multi.addActionListener(new FilterHandler(
				CommandConstants.CMD_SML_MULTI));
		fullSeatGroup.add(op_small_multi);
		betnumlimit = new JComboBox(SystemConstants.COMBO_BET_NUM);
		betnumlimit.setEditable(false);
		betnumlimit.setEnabled(false);
		// op_small_multi.setBackground(SystemConstants.SYSTEM_DEFAULT_COLOR);
		// betnumlimit.setBackground(SystemConstants.SYSTEM_DEFAULT_COLOR);
		lpn.add(op_small_multi);
		lpn.add(betnumlimit);
		this.add(lpn);
	}
	
	/**
	 * @方法说明: set combobox betnumlimit to enable
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void setBNLEnable(boolean flag) {
		betnumlimit.setEnabled(flag);
	}
}
