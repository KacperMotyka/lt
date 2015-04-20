/**
 * @文件名称: LotteryWorkPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午12:16:25
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


/**
 * @类功能说明: 主界面=>工作区面板
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午12:16:25
 * @版本:       1.0.0
 */

public class LotteryWorkPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 6942155990659183356L;
	
	private JPanel betPane; 	// 投注区
	private JPanel filterPane; 	// 过滤区
	private JPanel resultPane;	// 投注结果区

	public LotteryWorkPanel() {
		super();
		init();
	}
	
	public LotteryWorkPanel(LayoutManager layout) {
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
		this.setPreferredSize(new java.awt.Dimension(942, 484));
		
		// 投注区
		betPane = new LotteryBetPanel(new FlowLayout());
		this.add(betPane);
		
		// 过滤区
		filterPane = new LotteryFilterPanel(new FlowLayout(FlowLayout.RIGHT));
		this.add(filterPane);
		
		// 投注结果区
		resultPane = new LotteryResultPanel(new FlowLayout());
		this.add(resultPane);
	}
}
