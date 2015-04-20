/**
 * @文件名称: LotteryFilterMenuBar.java
 * @类路径:   com.rb.lottery.UI.menubar
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午04:47:22
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.menubar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午04:47:22
 * @版本:       1.0.0
 */

public class LotteryFilterMenuBar extends JMenuBar {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 7441189907874003866L;

	private JMenu filterOperation;
	private JMenuItem openFilter;
	private JMenuItem saveFilter;
	private JMenuItem clearFilter;
	private JMenuItem singleVerify;
	private JMenuItem verifybefFilter;
	private JMenuItem filterVerify;
	private JMenuItem historyVerify;
	private JMenuItem filterRecycle;
	private JMenuItem keepFilter;
	private JMenuItem pluginMana;
	private JMenuItem showFilterProg;
	private JMenuItem batFilter;
	
	public LotteryFilterMenuBar() {
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
		this.setPreferredSize(new java.awt.Dimension(70, 15));
		
		filterOperation = new JMenu(SystemConstants.FILTER_OPTION);
		openFilter = new JMenuItem(SystemConstants.FILTER_OPEN);
		filterOperation.add(openFilter);
		saveFilter = new JMenuItem(SystemConstants.FILTER_SAVE);
		filterOperation.add(saveFilter);
		clearFilter = new JMenuItem(SystemConstants.FILTER_CLEAR);
		filterOperation.add(clearFilter);
		filterOperation.addSeparator();
		singleVerify = new JMenuItem(SystemConstants.FILTER_SINGLE_VERIFY);
		filterOperation.add(singleVerify);
		verifybefFilter = new JMenuItem(SystemConstants.VERIFY_BEFORE_FILTER);
		verifybefFilter.setEnabled(false);
		filterOperation.add(verifybefFilter);
		filterVerify = new JMenuItem(SystemConstants.FILTER_VERIFY);
		filterVerify.setEnabled(false);
		filterOperation.add(filterVerify);
		historyVerify = new JMenuItem(SystemConstants.FILTER_HISTORY_VERIFY);
		filterOperation.add(historyVerify);
		filterOperation.addSeparator();
		filterRecycle = new JMenuItem(SystemConstants.FILTER_RECYCLE);
		filterRecycle.setEnabled(false);
		filterOperation.add(filterRecycle);
		filterOperation.addSeparator();
		keepFilter = new JMenuItem(SystemConstants.KEEP_FILTER);
		filterOperation.add(keepFilter);
		pluginMana = new JMenuItem(SystemConstants.PLUGIN_MANAGER);
		filterOperation.add(pluginMana);
		filterOperation.addSeparator();
		showFilterProg = new JMenuItem(SystemConstants.SHOW_FILTER_PROGRESS);
		filterOperation.add(showFilterProg);
		batFilter = new JMenuItem(SystemConstants.BAT_FILTER);
		filterOperation.add(batFilter);
		this.add(filterOperation);
	}

}
