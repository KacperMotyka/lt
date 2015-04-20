/**
 * @文件名称: MainPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 上午11:58:50
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;


/**
 * @类功能说明: 主界面面板
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 上午11:58:50
 * @版本:       1.0.0
 */

public class MainPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -6846749903446920685L;

	private JPanel quickMenuPane; 	// 快速菜单栏
	private JPanel workPane; 	// 工作区
	private LotteryStatusPanel statuPane; 	// 状态栏
	
	public MainPanel() {
		super();
		init();
	}
	
	public MainPanel(LayoutManager layout) {
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
		this.setBorder(null);
		this.setPreferredSize(new java.awt.Dimension(942, 588));
		
		// 快速菜单栏
		quickMenuPane = new LotteryQuickMenuPanel(new FlowLayout(FlowLayout.LEFT + 25, 25, 5));
		this.add(quickMenuPane);
		
		// 工作区
		workPane = new LotteryWorkPanel(new GridLayout(1, 3));
		this.add(workPane);
		
		//  状态栏
		statuPane = new LotteryStatusPanel(new FlowLayout(FlowLayout.LEFT));
		this.add(statuPane);
	}

	/**
	 * @方法说明: 
	 * @参数:     
	 * @return    void
	 * @throws
	 */
	public void updateCacheStatu() {
		statuPane.updateCacheStatu();
	}
}
