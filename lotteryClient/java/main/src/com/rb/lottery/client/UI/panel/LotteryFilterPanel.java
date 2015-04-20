/**
 * @文件名称: LotteryFilterPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午12:29:23
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;

import com.rb.lottery.client.UI.menubar.LotteryFilterMenuBar;


/**
 * @类功能说明: 主界面=>工作区=>过滤区面板
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午12:29:23
 * @版本:       1.0.0
 */

public class LotteryFilterPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -6838248195971779387L;
	
	private JMenuBar filterBar;
	private JTabbedPane filterTab;
	private JPanel filterbetPane;
	
	public LotteryFilterPanel() {
		super();
		init();
	}
	
	public LotteryFilterPanel(LayoutManager layout) {
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
		this.setPreferredSize(new java.awt.Dimension(300, 484));
		
		filterBar = new LotteryFilterMenuBar();
		this.add(filterBar);
		
		filterTab = new LotteryFilterTabPanel();
		this.add(filterTab);
		
		filterbetPane = new LotteryFilterBetPanel(new FlowLayout(FlowLayout.LEFT));
		this.add(filterbetPane);
		
	}

}
