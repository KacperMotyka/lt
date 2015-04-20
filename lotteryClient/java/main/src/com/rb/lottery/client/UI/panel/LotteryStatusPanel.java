/**
 * @文件名称: LotteryStatusPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午12:21:33
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.LayoutManager;
import java.util.Timer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.timer.IdleMemoryTimer;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemProcessor;

/**
 * @类功能说明: 主界面=>状态栏面板
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-13 下午12:21:33
 * @版本: 1.0.0
 */

public class LotteryStatusPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -3521220485535009919L;

	private JLabel betstatus;
	private JLabel filterstatus;
	private JLabel cachestatu;
	private JLabel betNum;

	public LotteryStatusPanel() {
		super();
		init();
	}

	public LotteryStatusPanel(LayoutManager layout) {
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
		this.setPreferredSize(new java.awt.Dimension(942, 50));
		betstatus = new JLabel(SystemConstants.BETSTATUS);
		// 初始化投注状态栏
		betstatus.setText(SystemProcessor.getBetStatu());
		betstatus.setBorder(new EtchedBorder());
		betstatus.setPreferredSize(new java.awt.Dimension(314, 30));
		this.add(betstatus);
		filterstatus = new JLabel(SystemConstants.FILTERSTATUS);
		filterstatus.setBorder(new EtchedBorder());
		filterstatus.setPreferredSize(new java.awt.Dimension(314, 30));
		this.add(filterstatus);

		betNum = new JLabel(SystemConstants.BETCODE
				+ SystemConstants.ZERO_STRING + SystemConstants.BLANK_STRING
				+ SystemConstants.BET);
		betNum.setBorder(new EtchedBorder());
		betNum.setPreferredSize(new java.awt.Dimension(145, 30));
		this.add(betNum);

		cachestatu = new JLabel(SystemConstants.EMPTY_STRING);
		IdleMemoryTimer idleTimer = new IdleMemoryTimer();
		Timer timer = new Timer();
		timer.schedule(idleTimer, 2000, 5000);
		cachestatu.setBorder(new EtchedBorder());
		cachestatu.setPreferredSize(new java.awt.Dimension(145, 30));
		this.add(cachestatu);
	}

	/**
	 * @方法说明: 更新状态栏
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void updateBetStatu() {
		betstatus.setText(SystemProcessor.getBetStatu());
	}

	/**
	 * @方法说明: 更新状态栏
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void updateCacheStatu() {
		cachestatu
				.setText(SystemConstants.CACHESTATU + SystemCache.free_memory);
	}

}
