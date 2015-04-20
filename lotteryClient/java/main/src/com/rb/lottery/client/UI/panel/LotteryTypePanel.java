/**
 * @文件名称: LotteryTypePanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午02:03:12
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.handler.IOHandler;
import com.rb.lottery.system.SysConfig;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemProcessor;


/**
 * @类功能说明: 主界面=>工作区=>投注区面板=>投注类型选择面板
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午02:03:12
 * @版本:       1.0.0
 */

public class LotteryTypePanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 9050720058016526598L;
	
	private JLabel betTylab;
	private JLabel prdlab;
	private JComboBox betType;
	private JComboBox period;

	public LotteryTypePanel() {
		super();
		init();
	}
	
	public LotteryTypePanel(LayoutManager layout) {
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
		this.setPreferredSize(new java.awt.Dimension(314, 47));
		
		betTylab = new JLabel(SystemConstants.LABEL_BETTYPE);
		betTylab.setForeground(Color.BLUE);
		this.add(betTylab);
		betType = new JComboBox(SystemConstants.BET_TYPES);
		betType.setSelectedIndex(SystemCache.currentType);
		betType.addActionListener(new IOHandler());
		this.add(betType);
		
		prdlab = new JLabel(SystemConstants.LABEL_PERIOD);
		prdlab.setForeground(Color.BLUE);
		this.add(prdlab);

		int maxQihaoCount = SysConfig.getInstance().getMaxQihaoCount();
		String[] qihaos = new String[maxQihaoCount];
		if (maxQihaoCount > 0) {
			qihaos = SystemProcessor.generateQihaos(maxQihaoCount);
		}
		period = new JComboBox(qihaos);
		period.addActionListener(new IOHandler());
		this.add(period);
	}
}
