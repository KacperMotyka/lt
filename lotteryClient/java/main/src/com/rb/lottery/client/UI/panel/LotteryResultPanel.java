/**
 * @文件名称: LotteryResultPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午12:33:47
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


/**
 * @类功能说明: 主界面=>工作区=>投注结果区面板
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午12:33:47
 * @版本:       1.0.0
 */

public class LotteryResultPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 2227756042218311360L;
	
	private JPanel procPane;
	private JPanel basketPane;

	public LotteryResultPanel() {
		super();
		init();
	}
	
	public LotteryResultPanel(LayoutManager layout) {
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
		
		procPane = new LotteryProcPanel(new FlowLayout(FlowLayout.LEFT + 25));
		this.add(procPane);
		
		basketPane = new LotteryBasketPanel(new FlowLayout());
		this.add(basketPane);
	}
}
