/**
 * @文件名称: LotteryFilterTabPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午04:53:02
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午04:53:02
 * @版本:       1.0.0
 */

public class LotteryFilterTabPanel extends JTabbedPane {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -323231442546017153L;

	private JPanel filterCommon;
	private JPanel filterAdvance;
	private JPanel filterPlugin;
	private JPanel filterSet;
	
	public LotteryFilterTabPanel() {
		super();
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
		this.setPreferredSize(new java.awt.Dimension(300, 420));
		
		filterCommon = new LotteryFilterCommonPanel(new FlowLayout(FlowLayout.LEFT));
		this.add(filterCommon);
		this.setTitleAt(0, SystemConstants.FILTER_COMMON);
		
		filterAdvance = new LotteryFilterAdvancePanel(new FlowLayout(FlowLayout.LEFT));
		this.add(filterAdvance);
		this.setTitleAt(1, SystemConstants.FILTER_ADVANCE);
		
		filterPlugin = new LotteryFiltePluginPanel(new FlowLayout(FlowLayout.LEFT));
		this.add(filterPlugin);
		this.setTitleAt(2, SystemConstants.FILTER_PLUGIN);
		
		filterSet = new LotteryFilterSetPanel(new FlowLayout(FlowLayout.LEFT));
		this.add(filterSet);
		this.setTitleAt(3, SystemConstants.FILTER_SET);	
	}
	
}
