/**
 * @文件名称: LotteryShrinkPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午04:09:46
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
 * @类功能说明: 主界面=>工作区=>投注区面板=>投注设置面板=>缩水页
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午04:09:46
 * @版本:       1.0.0
 */

public class LotteryShrinkPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -75895521812878762L;

	private ButtonGroup shrinkGroup;
	private JRadioButton standarshrink;
	private JRadioButton danshrink;
	private JComboBox standarshrinkComb;
	private JComboBox danshrinkComb;
	private JTextField danshrinkText;
	private JButton danshrinkBtn;
	
	/**
	 * @类名: LotteryShrinkPanel.java
	 * @描述: TODO
	 */
	public LotteryShrinkPanel() {
		super();
		init();
	}

	/**
	 * @类名: LotteryShrinkPanel.java
	 * @描述: TODO
	 * @param layout
	 */
	public LotteryShrinkPanel(LayoutManager layout) {
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
		
		shrinkGroup = new ButtonGroup();
		standarshrink = new JRadioButton(SystemConstants.STANDAR_SHRINK);
		standarshrink.setPreferredSize(new java.awt.Dimension(80, 20));
		danshrink = new JRadioButton(SystemConstants.DAN_SHRINK);
		danshrink.setPreferredSize(new java.awt.Dimension(80, 20));
		shrinkGroup.add(standarshrink);
		shrinkGroup.add(danshrink);
		standarshrinkComb = new JComboBox(SystemConstants.SHRINK_COMB);
		standarshrinkComb.setPreferredSize(new java.awt.Dimension(85, 20));
		standarshrinkComb.setEnabled(false);
		standarshrinkComb.setEditable(false);
		danshrinkComb = new JComboBox(SystemConstants.SHRINK_COMB);
		danshrinkComb.setPreferredSize(new java.awt.Dimension(85, 20));
		danshrinkComb.setEnabled(false);
		danshrinkComb.setEditable(false);
		danshrinkText = new JTextField();
		danshrinkText.setPreferredSize(new java.awt.Dimension(95, 20));
		danshrinkText.setEnabled(false);
		danshrinkText.setEditable(false);
		danshrinkBtn = new JButton(new ImageIcon(
				FilePathConstants.EDIT_BUTTON_IMG));
		danshrinkBtn.setPreferredSize(new java.awt.Dimension(20, 20));

		this.add(standarshrink);
		this.add(standarshrinkComb);
		JLabel tmp = new JLabel(SystemConstants.EMPTY_STRING);
		tmp.setPreferredSize(new java.awt.Dimension(80, 20));
		this.add(tmp);
		this.add(danshrink);
		this.add(danshrinkComb);
		this.add(danshrinkText);
		this.add(danshrinkBtn);
	}

}
