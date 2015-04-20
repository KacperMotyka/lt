/**
 * @文件名称: LotteryRegionPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午04:03:34
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 主界面=>工作区=>投注区面板=>投注设置面板=>区间页
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午04:03:34
 * @版本:       1.0.0
 */

public class LotteryRegionPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 7986788756479956348L;
	
	private JCheckBox range;
	private JCheckBox winexp;
	private JCheckBox dataexp;
	private JTextField rangeText;
	private JTextField winexpText;
	private JTextField dataexpText;
	private JButton rangeBtn;
	private JButton winexpBtn;
	private JButton dataexpBtn;

	public LotteryRegionPanel() {
		super();
		init();
	}
	
	public LotteryRegionPanel(LayoutManager layout) {
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
		
		range = new JCheckBox(SystemConstants.RANGE);
		range.setPreferredSize(new java.awt.Dimension(80, 13));
		winexp = new JCheckBox(SystemConstants.WIN_EXPECT);
		winexp.setPreferredSize(new java.awt.Dimension(80, 13));
		dataexp = new JCheckBox(SystemConstants.DATA_EXPECT);
		dataexp.setPreferredSize(new java.awt.Dimension(80, 13));
		rangeText = new JTextField();
		rangeText.setPreferredSize(new java.awt.Dimension(180, 13));
		rangeText.setEditable(false);
		rangeText.setEnabled(false);
		winexpText = new JTextField();
		winexpText.setPreferredSize(new java.awt.Dimension(180, 13));
		winexpText.setEditable(false);
		winexpText.setEnabled(false);
		dataexpText = new JTextField();
		dataexpText.setPreferredSize(new java.awt.Dimension(180, 13));
		dataexpText.setEditable(false);
		dataexpText.setEditable(false);
		rangeBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
		rangeBtn.setPreferredSize(new java.awt.Dimension(20, 13));
		// rangeBtn.setBorder(null);
		winexpBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
		winexpBtn.setPreferredSize(new java.awt.Dimension(20, 13));
		// winexpBtn.setBorder(null);
		dataexpBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
		dataexpBtn.setPreferredSize(new java.awt.Dimension(20, 13));
		// dataexpBtn.setBorder(null);

		this.add(range);
		this.add(rangeText);
		this.add(rangeBtn);
		this.add(winexp);
		this.add(winexpText);
		this.add(winexpBtn);
		this.add(dataexp);
		this.add(dataexpText);
		this.add(dataexpBtn);
	}
}
