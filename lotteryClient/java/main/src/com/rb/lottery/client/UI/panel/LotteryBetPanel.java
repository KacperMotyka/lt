/**
 * @文件名称: LotteryBetPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午12:27:06
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;


/**
 * @类功能说明: 主界面=>工作区=>投注区面板
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午12:27:06
 * @版本:       1.0.0
 */

public class LotteryBetPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -6118552721262811471L;
	
	private JPanel typePane;			// 投注类型选择区
	private JScrollPane dataPane;		// 投注比赛数据区
	private JTabbedPane OperatePane;	// 投注设置区
	private JPanel dobetPane;			// 投注操作区

	public LotteryBetPanel() {
		super();
		init();
	}
	
	public LotteryBetPanel(LayoutManager layout) {
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
		this.setPreferredSize(new java.awt.Dimension(328, 484));
		
		// 投注类型选择区
		typePane = new LotteryTypePanel(new FlowLayout());
		this.add(typePane);
		
		// 投注比赛数据区
		dataPane = new LotteryDataPanel();
		this.add(dataPane);
		
		//  投注设置区
		OperatePane = new LotteryOperatePanel();
		this.add(OperatePane);
		
		//  投注操作区
		dobetPane = new LotteryDoBetPanel(new FlowLayout(FlowLayout.LEFT));
		this.add(dobetPane);
	}
}
