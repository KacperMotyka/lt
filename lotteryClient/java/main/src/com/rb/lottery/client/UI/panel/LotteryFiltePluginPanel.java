/**
 * @文件名称: LotteryFiltePluginPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午05:14:29
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午05:14:29
 * @版本:       1.0.0
 */

public class LotteryFiltePluginPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -5441289116221704892L;
	
	private JTabbedPane filterPluginTab;
	private JButton pluginfilterAll;
	private JButton pluginallowerrBtn;
	private JPanel filterPluginA;
	private JCheckBox pluginA1;
	private JTextField pluginAText1;
	private JButton pluginABtn1;
	private JCheckBox pluginAerr1;

	public LotteryFiltePluginPanel() {
		super();
		init();
	}
	
	public LotteryFiltePluginPanel(LayoutManager layout) {
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

		pluginfilterAll = new JButton(SystemConstants.FILTER_ALL);
		pluginfilterAll.setPreferredSize(new java.awt.Dimension(40, 20));
		pluginfilterAll.setBackground(Color.WHITE);
		pluginfilterAll.setForeground(Color.BLUE);
		pluginfilterAll.setBorder(null);
		this.add(pluginfilterAll);
		JLabel keep = new JLabel(SystemConstants.EMPTY_STRING);
		keep.setPreferredSize(new java.awt.Dimension(185, 20));
		this.add(keep);
		pluginallowerrBtn = new JButton(SystemConstants.ALLOW_ERROR);
		pluginallowerrBtn.setPreferredSize(new java.awt.Dimension(40, 20));
		pluginallowerrBtn.setBackground(Color.WHITE);
		pluginallowerrBtn.setForeground(Color.BLUE);
		pluginallowerrBtn.setBorder(null);
		this.add(pluginallowerrBtn);

		initFilterPluginTab();
		this.add(filterPluginTab);
	}
	
	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void initFilterPluginTab() {
		filterPluginTab = new JTabbedPane(JTabbedPane.LEFT);
		filterPluginTab.setBorder(new EtchedBorder());
		filterPluginTab.setPreferredSize(new java.awt.Dimension(300, 380));

		initFilterPluginAPane();
		filterPluginTab.add(filterPluginA);
		filterPluginTab.setTitleAt(0, SystemConstants.A);
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void initFilterPluginAPane() {
		filterPluginA = new JPanel(new FlowLayout(FlowLayout.LEFT));
		filterPluginA.setPreferredSize(new java.awt.Dimension(300, 380));

		// a filter condition
		pluginA1 = new JCheckBox(SystemConstants.FILTER_PLUGINA1);
		pluginA1.setPreferredSize(new java.awt.Dimension(78, 16));
		filterPluginA.add(pluginA1);
		pluginAText1 = new JTextField();
		pluginAText1.setPreferredSize(new java.awt.Dimension(106, 16));
		pluginAText1.setEditable(false);
		pluginAText1.setEnabled(false);
		filterPluginA.add(pluginAText1);
		pluginABtn1 = new JButton(
				new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
		pluginABtn1.setPreferredSize(new java.awt.Dimension(20, 16));
		filterPluginA.add(pluginABtn1);
		pluginAerr1 = new JCheckBox();
		pluginAerr1.setPreferredSize(new java.awt.Dimension(20, 16));
		pluginAerr1.setEnabled(false);
		filterPluginA.add(pluginAerr1);
	}
}
