/**
 * @文件名称: LotteryOperatePanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午03:43:39
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明: 主界面=>工作区=>投注区面板=>投注设置面板
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-13 下午03:43:39
 * @版本: 1.0.0
 */

public class LotteryOperatePanel extends JTabbedPane {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -3940122651852451131L;

	private JPanel fullSeat; // 全排页
	private JPanel region; // 区间页
	private JPanel shrink; // 缩水页
	private JPanel dantuo; // 胆拖页
	private JPanel waterflood; // 注水页
	private JPanel rondom; // 随机页

	public LotteryOperatePanel() {
		super();
		init();
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void init() {
		this.setBorder(null);
		this.setPreferredSize(new java.awt.Dimension(314, 85));

		// 全排页
		fullSeat = new LotteryFullSeatPanel(new GridLayout(2, 1));
		this.add(fullSeat);
		this.setTitleAt(0, SystemConstants.FULL_SEAT);

		// 区间页
		region = new LotteryRegionPanel(new FlowLayout(FlowLayout.LEFT));
		this.add(region);
		this.setTitleAt(1, SystemConstants.REGION);

		// 缩水页
		shrink = new LotteryShrinkPanel(new FlowLayout(FlowLayout.LEFT));
		this.add(shrink);
		this.setTitleAt(2, SystemConstants.SHRINK);

		// 胆拖页
		dantuo = new LotteryDantuoPanel(new FlowLayout(FlowLayout.LEFT));
		this.add(dantuo);
		this.setTitleAt(3, SystemConstants.DANTUO);
		
		// 注水页
		waterflood = new LotteryWaterFloodPanel(new FlowLayout(FlowLayout.LEFT));
		this.add(waterflood);
		this.setTitleAt(4, SystemConstants.WATER_FLOOD);
		
		// 随机页
		rondom = new LotteryRondomPanel(new FlowLayout(FlowLayout.LEFT));
		this.add(rondom);
		this.setTitleAt(5, SystemConstants.RONDOM);
	}
}
