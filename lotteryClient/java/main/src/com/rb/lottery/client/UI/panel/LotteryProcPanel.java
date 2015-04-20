/**
 * @文件名称: LotteryProcPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午05:29:54
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午05:29:54
 * @版本:       1.0.0
 */

public class LotteryProcPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 251849887425312254L;

	private JMenuBar procmbar;
	private JMenu netbet;
	private JMenuItem arena2048;
	private JMenuItem arena128;
	private JMenuItem mitilbuy90;
	private JMenuItem mitilbuym70;
	private JMenuItem mitilbuyl70;
	private JMenuItem mitilbuyown;
	private JButton addproc;
	private JButton dropproc;
	private JMenu betBascket;
	private JMenuItem bs_item1;
	private JMenuItem bs_item2;
	private JMenuItem bs_item3;
	private JMenuItem bs_item4;
	private JMenuItem bs_item5;
	private JMenuItem bs_item6;
	private JMenuItem bs_item7;
	private JMenuItem bs_item8;
	private JMenuItem bs_item9;
	private JMenuItem bs_item10;
	private JMenuItem bs_item11;
	private JMenuItem bs_item12;
	private JMenuItem bs_item13;
	private JMenuItem bs_item14;
	private JMenuItem bs_item15;
	private JMenuItem bs_item16;
	private JMenu resultProc;
	private JMenuItem rp_item1;
	private JMenuItem rp_item2;
	private JMenuItem rp_item3;
	private JMenuItem rp_item4;
	private JMenuItem rp_item5;
	private JRadioButtonMenuItem rp_item6;
	private JRadioButtonMenuItem rp_item7;
	private JMenuItem rp_item8;
	private JMenuItem rp_item9;
	private JMenuItem rp_item10;
	private JMenuItem rp_item11;
	private JMenuItem rp_item12;
	private JMenuItem rp_item13;
	private JMenu singleExpand;
	private JMenuItem se_item1;
	private JMenuItem se_item2;
	private JMenuItem se_item3;
	private JMenu betShrink;
	private JMenuItem bts_item1;
	private JMenuItem bts_item2;
	private JMenuItem bts_item3;
	private JMenuItem bts_item4;
	private JMenuItem bts_item5;
	private JMenuItem bts_item6;
	private JMenuItem bts_item7;
	private JMenuItem bts_item8;
	private JMenuItem bts_item9;
	private JMenuItem bts_item10;
	private JMenu betProtect;
	private JMenuItem bp_item1;
	private JMenuItem bp_item2;
	private JMenuItem bp_item3;
	private JMenuItem bp_item4;
	private JMenuItem bp_item5;
	private JMenuItem bp_item6;
	private JMenuItem bp_item7;
	private JMenuItem bp_item8;
	private JMenuItem bp_item9;
	private JMenuItem bp_item10;
	private JMenu betSort;
	private JMenuItem bst_item1;
	private JMenuItem bst_item2;
	private JMenuItem bst_item3;
	private JMenuItem bst_item4;
	private JMenuItem bst_item5;
	private JMenuItem bst_item6;
	private JMenuItem bst_item7;
	private JMenuItem bst_item8;
	private JMenuItem bst_item9;
	private JMenuItem bst_item10;
	private JMenuItem bst_item11;
	private JMenuItem bst_item12;
	private JMenuItem bst_item13;
	private JMenu betSet;
	private JMenu betSet1;
	private JMenuItem bsts_item1;
	private JMenu betSet2;
	private JMenuItem bsts_item2;
	private JMenu betSet3;
	private JMenuItem bsts_item3;
	private JMenu betSet4;
	private JMenuItem bsts_item4;
	private JMenu betData;
	private JMenuItem bd_item1;
	private JMenuItem bd_item2;
	private JMenuItem bd_item3;
	private JMenuItem bd_item4;
	private JScrollPane procResult;
	private JPanel procRet;
	
	public LotteryProcPanel() {
		super();
		init();
	}
	
	public LotteryProcPanel(LayoutManager layout) {
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
		this.setPreferredSize(new java.awt.Dimension(300, 120));
		this.setBorder(new EtchedBorder());

		procmbar = new JMenuBar();
		netbet = new JMenu();
		netbet.setPreferredSize(new java.awt.Dimension(24, 24));
		netbet.setIcon(new ImageIcon(FilePathConstants.BET_NET_IMG));
		arena2048 = new JMenuItem(SystemConstants.ARENA2048);
		arena128 = new JMenuItem(SystemConstants.ARENA128);
		mitilbuy90 = new JMenuItem(SystemConstants.MULTI_BUY90);
		mitilbuym70 = new JMenuItem(SystemConstants.MULTI_BUYM70);
		mitilbuyl70 = new JMenuItem(SystemConstants.MULTI_BUYL70);
		mitilbuyown = new JMenuItem(SystemConstants.MULTI_BUYOWN);
		JLabel top = new JLabel(SystemConstants.DOWNLOAD_TO_BASKET,
				JLabel.CENTER);
		top.setForeground(Color.RED);
		netbet.add(top);
		netbet.addSeparator();
		netbet.add(arena2048);
		netbet.add(arena128);
		netbet.add(mitilbuy90);
		netbet.add(mitilbuym70);
		netbet.add(mitilbuyl70);
		netbet.add(mitilbuyown);
		netbet.addSeparator();
		JLabel botton = new JLabel(SystemConstants.REALTIME_DATA, JLabel.CENTER);
		botton.setForeground(Color.RED);
		netbet.add(botton);
		procmbar.add(netbet);
		this.add(procmbar);
		addproc = new JButton(new ImageIcon(FilePathConstants.ADD_PROC_IMG));
		addproc.setPreferredSize(new java.awt.Dimension(24, 24));
		this.add(addproc);
		dropproc = new JButton(new ImageIcon(FilePathConstants.DROP_PROC_IMG));
		dropproc.setPreferredSize(new java.awt.Dimension(24, 24));
		this.add(dropproc);
		betBascket = new JMenu(SystemConstants.BET_BASCKET);
		betBascket.setPreferredSize(new java.awt.Dimension(60, 24));
		bs_item1 = new JMenuItem(SystemConstants.BET_BASCKET1);
		betBascket.add(bs_item1);
		bs_item2 = new JMenuItem(SystemConstants.BET_BASCKET2);
		betBascket.add(bs_item2);
		betBascket.addSeparator();
		bs_item3 = new JMenuItem(SystemConstants.BET_BASCKET3);
		bs_item3.setEnabled(false);
		betBascket.add(bs_item3);
		bs_item4 = new JMenuItem(SystemConstants.BET_BASCKET4);
		bs_item4.setEnabled(false);
		betBascket.add(bs_item4);
		betBascket.addSeparator();
		bs_item5 = new JMenuItem(SystemConstants.BET_BASCKET5);
		betBascket.add(bs_item5);
		bs_item6 = new JMenuItem(SystemConstants.BET_BASCKET6);
		bs_item6.setEnabled(false);
		betBascket.add(bs_item6);
		bs_item7 = new JMenuItem(SystemConstants.BET_BASCKET7);
		betBascket.add(bs_item7);
		betBascket.addSeparator();
		bs_item8 = new JMenuItem(SystemConstants.BET_BASCKET8);
		betBascket.add(bs_item8);
		bs_item9 = new JMenuItem(SystemConstants.BET_BASCKET9);
		bs_item9.setEnabled(false);
		betBascket.add(bs_item9);
		bs_item10 = new JMenuItem(SystemConstants.BET_BASCKET10);
		bs_item10.setEnabled(false);
		betBascket.add(bs_item10);
		betBascket.addSeparator();
		bs_item11 = new JMenuItem(SystemConstants.BET_BASCKET11);
		bs_item11.setEnabled(false);
		betBascket.add(bs_item11);
		bs_item12 = new JMenuItem(SystemConstants.BET_BASCKET12);
		bs_item12.setEnabled(false);
		betBascket.add(bs_item12);
		betBascket.addSeparator();
		bs_item13 = new JMenuItem(SystemConstants.BET_BASCKET13);
		betBascket.add(bs_item13);
		bs_item14 = new JMenuItem(SystemConstants.BET_BASCKET14);
		betBascket.add(bs_item14);
		bs_item15 = new JMenuItem(SystemConstants.BET_BASCKET15);
		betBascket.add(bs_item15);
		betBascket.addSeparator();
		bs_item16 = new JMenuItem(SystemConstants.BET_BASCKET16);
		betBascket.add(bs_item16);
		JMenuBar jmb = new JMenuBar();
		jmb.setPreferredSize(new java.awt.Dimension(150, 24));
		jmb.add(betBascket);
		resultProc = new JMenu(SystemConstants.RESULT_PROGRESS);
		resultProc.setPreferredSize(new java.awt.Dimension(70, 24));
		JLabel tj = new JLabel(SystemConstants.DEFAULT_BASCKET);
		tj.setForeground(Color.RED);
		resultProc.add(tj);
		resultProc.addSeparator();
		rp_item1 = new JMenuItem(SystemConstants.RESULT_PROGRESS1);
		resultProc.add(rp_item1);
		rp_item2 = new JMenuItem(SystemConstants.RESULT_PROGRESS2);
		resultProc.add(rp_item2);
		resultProc.addSeparator();
		rp_item3 = new JMenuItem(SystemConstants.RESULT_PROGRESS3);
		resultProc.add(rp_item3);
		rp_item4 = new JMenuItem(SystemConstants.RESULT_PROGRESS4);
		resultProc.add(rp_item4);
		resultProc.addSeparator();
		singleExpand = new JMenu(SystemConstants.SINGLE_EXPAND);
		singleExpand.setEnabled(false);
		se_item1 = new JMenuItem(SystemConstants.SINGLE_EXPAND1);
		singleExpand.add(se_item1);
		se_item2 = new JMenuItem(SystemConstants.SINGLE_EXPAND2);
		singleExpand.add(se_item2);
		se_item3 = new JMenuItem(SystemConstants.SINGLE_EXPAND3);
		singleExpand.add(se_item3);
		resultProc.add(singleExpand);
		betShrink = new JMenu(SystemConstants.BET_SHRINK);
		betShrink.setEnabled(false);
		bts_item1 = new JMenuItem(SystemConstants.BET_SHRINK1);
		betShrink.add(bts_item1);
		bts_item2 = new JMenuItem(SystemConstants.BET_SHRINK2);
		betShrink.add(bts_item2);
		bts_item3 = new JMenuItem(SystemConstants.BET_SHRINK3);
		betShrink.add(bts_item3);
		bts_item4 = new JMenuItem(SystemConstants.BET_SHRINK4);
		betShrink.add(bts_item4);
		bts_item5 = new JMenuItem(SystemConstants.BET_SHRINK5);
		betShrink.add(bts_item5);
		bts_item6 = new JMenuItem(SystemConstants.BET_SHRINK6);
		betShrink.add(bts_item6);
		bts_item7 = new JMenuItem(SystemConstants.BET_SHRINK7);
		betShrink.add(bts_item7);
		bts_item8 = new JMenuItem(SystemConstants.BET_SHRINK8);
		betShrink.add(bts_item8);
		bts_item9 = new JMenuItem(SystemConstants.BET_SHRINK9);
		betShrink.add(bts_item9);
		bts_item10 = new JMenuItem(SystemConstants.BET_SHRINK10);
		betShrink.add(bts_item10);
		resultProc.add(betShrink);
		rp_item5 = new JMenuItem(SystemConstants.RESULT_PROGRESS5);
		rp_item5.setEnabled(false);
		resultProc.add(rp_item5);
		ButtonGroup rpgp = new ButtonGroup();
		rp_item6 = new JRadioButtonMenuItem(SystemConstants.RESULT_PROGRESS6);
		rp_item6.setEnabled(false);
		rp_item7 = new JRadioButtonMenuItem(SystemConstants.RESULT_PROGRESS7);
		rp_item7.setEnabled(false);
		rpgp.add(rp_item6);
		rpgp.add(rp_item7);
		resultProc.add(rp_item6);
		resultProc.add(rp_item7);
		rp_item8 = new JMenuItem(SystemConstants.RESULT_PROGRESS8);
		rp_item8.setEnabled(false);
		resultProc.add(rp_item8);
		rp_item9 = new JMenuItem(SystemConstants.RESULT_PROGRESS9);
		rp_item9.setEnabled(false);
		resultProc.add(rp_item9);
		resultProc.addSeparator();
		betProtect = new JMenu(SystemConstants.BET_PROTECT);
		bp_item1 = new JMenuItem(SystemConstants.BET_PROTECT1);
		betProtect.add(bp_item1);
		bp_item2 = new JMenuItem(SystemConstants.BET_PROTECT2);
		betProtect.add(bp_item2);
		bp_item3 = new JMenuItem(SystemConstants.BET_PROTECT3);
		betProtect.add(bp_item3);
		betProtect.addSeparator();
		bp_item4 = new JMenuItem(SystemConstants.BET_PROTECT4);
		betProtect.add(bp_item4);
		bp_item5 = new JMenuItem(SystemConstants.BET_PROTECT5);
		bp_item5.setEnabled(false);
		betProtect.add(bp_item5);
		betProtect.addSeparator();
		bp_item6 = new JMenuItem(SystemConstants.BET_PROTECT6);
		betProtect.add(bp_item6);
		bp_item7 = new JMenuItem(SystemConstants.BET_PROTECT7);
		betProtect.add(bp_item7);
		betProtect.addSeparator();
		bp_item8 = new JMenuItem(SystemConstants.BET_PROTECT8);
		betProtect.add(bp_item8);
		bp_item9 = new JMenuItem(SystemConstants.BET_PROTECT9);
		betProtect.add(bp_item9);
		betProtect.addSeparator();
		bp_item10 = new JMenuItem(SystemConstants.BET_PROTECT10);
		betProtect.add(bp_item10);
		resultProc.add(betProtect);
		betSort = new JMenu(SystemConstants.BET_SORT);
		betSort.setEnabled(false);
		bst_item1 = new JMenuItem(SystemConstants.BET_SORT1);
		betSort.add(bst_item1);
		betSort.addSeparator();
		bst_item2 = new JMenuItem(SystemConstants.BET_SORT2);
		betSort.add(bst_item2);
		betSort.addSeparator();
		bst_item3 = new JMenuItem(SystemConstants.BET_SORT3);
		bst_item3.setEnabled(false);
		betSort.add(bst_item3);
		bst_item4 = new JMenuItem(SystemConstants.BET_SORT4);
		bst_item4.setEnabled(false);
		betSort.add(bst_item4);
		betSort.addSeparator();
		bst_item5 = new JMenuItem(SystemConstants.BET_SORT5);
		betSort.add(bst_item5);
		bst_item6 = new JMenuItem(SystemConstants.BET_SORT6);
		betSort.add(bst_item6);
		betSort.addSeparator();
		bst_item7 = new JMenuItem(SystemConstants.BET_SORT7);
		betSort.add(bst_item7);
		bst_item8 = new JMenuItem(SystemConstants.BET_SORT8);
		betSort.add(bst_item8);
		betSort.addSeparator();
		bst_item9 = new JMenuItem(SystemConstants.BET_SORT9);
		betSort.add(bst_item9);
		bst_item10 = new JMenuItem(SystemConstants.BET_SORT10);
		betSort.add(bst_item10);
		betSort.addSeparator();
		bst_item11 = new JMenuItem(SystemConstants.BET_SORT11);
		betSort.add(bst_item11);
		bst_item12 = new JMenuItem(SystemConstants.BET_SORT12);
		betSort.add(bst_item12);
		betSort.addSeparator();
		bst_item13 = new JMenuItem(SystemConstants.BET_SORT13);
		betSort.add(bst_item13);
		resultProc.add(betSort);
		resultProc.addSeparator();
		betSet = new JMenu(SystemConstants.BET_SET);
		betSet.setEnabled(false);
		betSet1 = new JMenu(SystemConstants.FILTER_UNION_SET);
		betSet.add(betSet1);
		bsts_item1 = new JMenuItem(SystemConstants.DEFAULT_BASCKET);
		betSet1.add(bsts_item1);
		betSet2 = new JMenu(SystemConstants.FILTER_MIXED_SET);
		betSet.add(betSet2);
		bsts_item2 = new JMenuItem(SystemConstants.DEFAULT_BASCKET);
		betSet2.add(bsts_item2);
		betSet3 = new JMenu(SystemConstants.FILTER_MINUS_SET);
		betSet.add(betSet3);
		bsts_item3 = new JMenuItem(SystemConstants.DEFAULT_BASCKET);
		betSet3.add(bsts_item3);
		betSet4 = new JMenu(SystemConstants.FILTER_DIFF_SET);
		betSet.add(betSet4);
		bsts_item4 = new JMenuItem(SystemConstants.DEFAULT_BASCKET);
		betSet4.add(bsts_item4);
		resultProc.add(betSet);
		betData = new JMenu(SystemConstants.BET_PRO_DT);
		betData.setEnabled(false);
		bd_item1 = new JMenuItem(SystemConstants.FILTER_UNION_SET);
		betData.add(bd_item1);
		bd_item2 = new JMenuItem(SystemConstants.FILTER_MIXED_SET);
		betData.add(bd_item2);
		bd_item3 = new JMenuItem(SystemConstants.FILTER_MINUS_SET);
		betData.add(bd_item3);
		bd_item4 = new JMenuItem(SystemConstants.FILTER_DIFF_SET);
		betData.add(bd_item4);
		resultProc.add(betData);
		resultProc.addSeparator();
		rp_item10 = new JMenuItem(SystemConstants.RESULT_PROGRESS10);
		rp_item10.setEnabled(false);
		resultProc.add(rp_item10);
		rp_item11 = new JMenuItem(SystemConstants.RESULT_PROGRESS11);
		rp_item11.setEnabled(false);
		resultProc.add(rp_item11);
		resultProc.addSeparator();
		rp_item12 = new JMenuItem(SystemConstants.RESULT_PROGRESS12);
		rp_item12.setEnabled(false);
		resultProc.add(rp_item12);
		rp_item13 = new JMenuItem(SystemConstants.RESULT_PROGRESS13);
		rp_item13.setEnabled(false);
		resultProc.add(rp_item13);
		jmb.add(resultProc);
		this.add(jmb);

		procRet = new JPanel();
		procRet.setBackground(Color.WHITE);
		procResult = new JScrollPane(procRet);
		procResult.setPreferredSize(new java.awt.Dimension(288, 80));
		this.add(procResult);
	}
}
