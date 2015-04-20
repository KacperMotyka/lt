/**
 * @文件名称: LotteryFilterCommonPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午05:00:52
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午05:00:52
 * @版本:       1.0.0
 */

public class LotteryFilterCommonPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 6317185696305675328L;

	private JButton commonfilterAll;
	private JButton commonbatfilter;
	private JButton commonallowerrBtn;
	private JTabbedPane filterCommonTab;
	private JPanel filterCommonA;
	private JCheckBox wins;
	private JCheckBox draws;
	private JCheckBox losses;
	private JCheckBox scores;
	private JCheckBox breaks;
	private JCheckBox continus;
	private JCheckBox hostWinContinu;
	private JCheckBox hostDrawContinu;
	private JCheckBox hostLossContinu;
	private JCheckBox winDrawContinu;
	private JCheckBox winLossContinu;
	private JCheckBox drawLossContinu;
	private JCheckBox conPartSum;
	private JCheckBox conPartCrs;
	private JCheckBox crsSum;
	private JComboBox left_wins;
	private JComboBox left_draws;
	private JComboBox left_losses;
	private JComboBox left_scores;
	private JComboBox left_breaks;
	private JComboBox left_continus;
	private JComboBox left_hostWinContinu;
	private JComboBox left_hostDrawContinu;
	private JComboBox left_hostLossContinu;
	private JComboBox left_winDrawContinu;
	private JComboBox left_winLossContinu;
	private JComboBox left_drawLossContinu;
	private JComboBox left_conPartSum;
	private JComboBox left_conPartCrs;
	private JComboBox left_crsSum;
	private JComboBox right_wins;
	private JComboBox right_draws;
	private JComboBox right_losses;
	private JComboBox right_scores;
	private JComboBox right_breaks;
	private JComboBox right_continus;
	private JComboBox right_hostWinContinu;
	private JComboBox right_hostDrawContinu;
	private JComboBox right_hostLossContinu;
	private JComboBox right_winDrawContinu;
	private JComboBox right_winLossContinu;
	private JComboBox right_drawLossContinu;
	private JComboBox right_conPartSum;
	private JComboBox right_conPartCrs;
	private JComboBox right_crsSum;
	private JCheckBox winserr;
	private JCheckBox drawserr;
	private JCheckBox losseserr;
	private JCheckBox scoreserr;
	private JCheckBox breakserr;
	private JCheckBox continuserr;
	private JCheckBox hostWinContinuerr;
	private JCheckBox hostDrawContinuerr;
	private JCheckBox hostLossContinuerr;
	private JCheckBox winDrawContinuerr;
	private JCheckBox winLossContinuerr;
	private JCheckBox drawLossContinuerr;
	private JCheckBox conPartSumerr;
	private JCheckBox conPartCrserr;
	private JCheckBox crsSumerr;
	private JPanel filterCommonB;
	private JCheckBox firstOdds;
	private JCheckBox secondOdds;
	private JCheckBox thirdOdds;
	private JCheckBox rangeRank;
	private JCheckBox probility;
	private JCheckBox expectBet;
	private JCheckBox probilitySum;
	private JCheckBox oddsSum;
	private JCheckBox oddsCrs;
	private JCheckBox winIndice;
	private JComboBox left_firstOdds;
	private JComboBox left_secondOdds;
	private JComboBox left_thirdOdds;
	private JTextField left_rangeRank;
	private JTextField left_probility;
	private JTextField left_expectBet;
	private JTextField left_probilitySum;
	private JTextField left_oddsSum;
	private JTextField left_oddsCrs;
	private JTextField left_winIndice;
	private JComboBox right_firstOdds;
	private JComboBox right_secondOdds;
	private JComboBox right_thirdOdds;
	private JTextField right_rangeRank;
	private JTextField right_probility;
	private JTextField right_expectBet;
	private JTextField right_probilitySum;
	private JTextField right_oddsSum;
	private JTextField right_oddsCrs;
	private JTextField right_winIndice;
	private JCheckBox firstOddserr;
	private JCheckBox secondOddserr;
	private JCheckBox thirdOddserr;
	private JCheckBox rangeRankerr;
	private JCheckBox probilityerr;
	private JCheckBox expectBeterr;
	private JCheckBox probilitySumerr;
	private JCheckBox oddsSumerr;
	private JCheckBox oddsCrserr;
	private JCheckBox winIndiceerr;
	
	public LotteryFilterCommonPanel() {
		super();
		init();
	}
	
	public LotteryFilterCommonPanel(LayoutManager layout) {
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
		this.setPreferredSize(new java.awt.Dimension(300, 420));

		commonfilterAll = new JButton(SystemConstants.FILTER_ALL);
		commonfilterAll.setPreferredSize(new java.awt.Dimension(40, 20));
		commonfilterAll.setBackground(Color.WHITE);
		commonfilterAll.setForeground(Color.BLUE);
		commonfilterAll.setBorder(null);
		this.add(commonfilterAll);
		commonbatfilter = new JButton(SystemConstants.BAT_FILTER_ASSIST);
		commonbatfilter.setPreferredSize(new java.awt.Dimension(80, 20));
		commonbatfilter.setBackground(Color.WHITE);
		commonbatfilter.setForeground(Color.BLUE);
		commonbatfilter.setBorder(null);
		this.add(commonbatfilter);
		JLabel keep = new JLabel(SystemConstants.KEEP_FILTER_RESULT);
		keep.setPreferredSize(new java.awt.Dimension(105, 20));
		this.add(keep);
		commonallowerrBtn = new JButton(SystemConstants.ALLOW_ERROR);
		commonallowerrBtn.setPreferredSize(new java.awt.Dimension(40, 20));
		commonallowerrBtn.setBackground(Color.WHITE);
		commonallowerrBtn.setForeground(Color.BLUE);
		commonallowerrBtn.setBorder(null);
		this.add(commonallowerrBtn);

		initFilterCommonTab();
		this.add(filterCommonTab);
	}
	
	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void initFilterCommonTab() {
		filterCommonTab = new JTabbedPane(JTabbedPane.LEFT);
		filterCommonTab.setBorder(new EtchedBorder());
		filterCommonTab.setPreferredSize(new java.awt.Dimension(300, 380));

		initFilterCommonAPane();
		filterCommonTab.add(filterCommonA);
		filterCommonTab.setTitleAt(0, SystemConstants.A);

		initFilterCommonBPane();
		filterCommonTab.add(filterCommonB);
		filterCommonTab.setTitleAt(1, SystemConstants.B);
	}
	
	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void initFilterCommonBPane() {
		filterCommonB = new JPanel(new FlowLayout(FlowLayout.LEFT));
		filterCommonB.setPreferredSize(new java.awt.Dimension(300, 380));

		// a filter condition
		firstOdds = new JCheckBox();
		firstOdds.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonB.add(firstOdds);
		left_firstOdds = new JComboBox(SystemConstants.DO_MATCHES);
		left_firstOdds.setPreferredSize(new java.awt.Dimension(42, 16));
		left_firstOdds.setEditable(false);
		left_firstOdds.setEnabled(false);
		filterCommonB.add(left_firstOdds);
		JLabel j1 = new JLabel(SystemConstants.FIRST_ODDS);
		j1.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonB.add(j1);
		right_firstOdds = new JComboBox(SystemConstants.DO_MATCHES);
		right_firstOdds.setPreferredSize(new java.awt.Dimension(42, 16));
		right_firstOdds.setEditable(false);
		right_firstOdds.setEnabled(false);
		filterCommonB.add(right_firstOdds);
		firstOddserr = new JCheckBox();
		firstOddserr.setPreferredSize(new java.awt.Dimension(20, 16));
		firstOddserr.setEnabled(false);
		filterCommonB.add(firstOddserr);
		// a filter condition
		secondOdds = new JCheckBox();
		secondOdds.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonB.add(secondOdds);
		left_secondOdds = new JComboBox(SystemConstants.DO_MATCHES);
		left_secondOdds.setPreferredSize(new java.awt.Dimension(42, 16));
		left_secondOdds.setEditable(false);
		left_secondOdds.setEnabled(false);
		filterCommonB.add(left_secondOdds);
		JLabel j2 = new JLabel(SystemConstants.SECOND_ODDS);
		j2.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonB.add(j2);
		right_secondOdds = new JComboBox(SystemConstants.DO_MATCHES);
		right_secondOdds.setPreferredSize(new java.awt.Dimension(42, 16));
		right_secondOdds.setEditable(false);
		right_secondOdds.setEnabled(false);
		filterCommonB.add(right_secondOdds);
		secondOddserr = new JCheckBox();
		secondOddserr.setPreferredSize(new java.awt.Dimension(20, 16));
		secondOddserr.setEnabled(false);
		filterCommonB.add(secondOddserr);
		// a filter condition
		thirdOdds = new JCheckBox();
		thirdOdds.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonB.add(thirdOdds);
		left_thirdOdds = new JComboBox(SystemConstants.DO_MATCHES);
		left_thirdOdds.setPreferredSize(new java.awt.Dimension(42, 16));
		left_thirdOdds.setEditable(false);
		left_thirdOdds.setEnabled(false);
		filterCommonB.add(left_thirdOdds);
		JLabel j3 = new JLabel(SystemConstants.THIRD_ODDS);
		j3.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonB.add(j3);
		right_thirdOdds = new JComboBox(SystemConstants.DO_MATCHES);
		right_thirdOdds.setPreferredSize(new java.awt.Dimension(42, 16));
		right_thirdOdds.setEditable(false);
		right_thirdOdds.setEnabled(false);
		filterCommonB.add(right_thirdOdds);
		thirdOddserr = new JCheckBox();
		thirdOddserr.setPreferredSize(new java.awt.Dimension(20, 16));
		thirdOddserr.setEnabled(false);
		filterCommonB.add(thirdOddserr);
		// a filter condition
		rangeRank = new JCheckBox();
		rangeRank.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonB.add(rangeRank);
		left_rangeRank = new JTextField();
		left_rangeRank.setPreferredSize(new java.awt.Dimension(42, 16));
		left_rangeRank.setEnabled(false);
		filterCommonB.add(left_rangeRank);
		JLabel j4 = new JLabel(SystemConstants.RANGE_RANK);
		j4.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonB.add(j4);
		right_rangeRank = new JTextField();
		right_rangeRank.setPreferredSize(new java.awt.Dimension(42, 16));
		right_rangeRank.setEnabled(false);
		filterCommonB.add(right_rangeRank);
		rangeRankerr = new JCheckBox();
		rangeRankerr.setPreferredSize(new java.awt.Dimension(20, 16));
		rangeRankerr.setEnabled(false);
		filterCommonB.add(rangeRankerr);
		// a filter condition
		probility = new JCheckBox();
		probility.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonB.add(probility);
		left_probility = new JTextField();
		left_probility.setPreferredSize(new java.awt.Dimension(42, 16));
		left_probility.setEnabled(false);
		filterCommonB.add(left_probility);
		JLabel j5 = new JLabel(SystemConstants.PROBILITY_CRS);
		j5.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonB.add(j5);
		right_probility = new JTextField();
		right_probility.setPreferredSize(new java.awt.Dimension(42, 16));
		right_probility.setEnabled(false);
		filterCommonB.add(right_probility);
		probilityerr = new JCheckBox();
		probilityerr.setPreferredSize(new java.awt.Dimension(20, 16));
		probilityerr.setEnabled(false);
		filterCommonB.add(probilityerr);
		// a filter condition
		expectBet = new JCheckBox();
		expectBet.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonB.add(expectBet);
		left_expectBet = new JTextField();
		left_expectBet.setPreferredSize(new java.awt.Dimension(42, 16));
		left_expectBet.setEnabled(false);
		filterCommonB.add(left_expectBet);
		JLabel j6 = new JLabel(SystemConstants.EXPECT_BET);
		j6.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonB.add(j6);
		right_expectBet = new JTextField();
		right_expectBet.setPreferredSize(new java.awt.Dimension(42, 16));
		right_expectBet.setEnabled(false);
		filterCommonB.add(right_expectBet);
		expectBeterr = new JCheckBox();
		expectBeterr.setPreferredSize(new java.awt.Dimension(20, 16));
		expectBeterr.setEnabled(false);
		filterCommonB.add(expectBeterr);
		// a filter condition
		probilitySum = new JCheckBox();
		probilitySum.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonB.add(probilitySum);
		left_probilitySum = new JTextField();
		left_probilitySum.setPreferredSize(new java.awt.Dimension(42, 16));
		left_probilitySum.setEnabled(false);
		filterCommonB.add(left_probilitySum);
		JLabel j7 = new JLabel(SystemConstants.PROBILITY_SUM);
		j7.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonB.add(j7);
		right_probilitySum = new JTextField();
		right_probilitySum.setPreferredSize(new java.awt.Dimension(42, 16));
		right_probilitySum.setEnabled(false);
		filterCommonB.add(right_probilitySum);
		probilitySumerr = new JCheckBox();
		probilitySumerr.setPreferredSize(new java.awt.Dimension(20, 16));
		probilitySumerr.setEnabled(false);
		filterCommonB.add(probilitySumerr);
		// a filter condition
		oddsSum = new JCheckBox();
		oddsSum.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonB.add(oddsSum);
		left_oddsSum = new JTextField();
		left_oddsSum.setPreferredSize(new java.awt.Dimension(42, 16));
		left_oddsSum.setEnabled(false);
		filterCommonB.add(left_oddsSum);
		JLabel j8 = new JLabel(SystemConstants.ODDS_SUM);
		j8.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonB.add(j8);
		right_oddsSum = new JTextField();
		right_oddsSum.setPreferredSize(new java.awt.Dimension(42, 16));
		right_oddsSum.setEnabled(false);
		filterCommonB.add(right_oddsSum);
		oddsSumerr = new JCheckBox();
		oddsSumerr.setPreferredSize(new java.awt.Dimension(20, 16));
		oddsSumerr.setEnabled(false);
		filterCommonB.add(oddsSumerr);
		// a filter condition
		oddsCrs = new JCheckBox();
		oddsCrs.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonB.add(oddsCrs);
		left_oddsCrs = new JTextField();
		left_oddsCrs.setPreferredSize(new java.awt.Dimension(42, 16));
		left_oddsCrs.setEnabled(false);
		filterCommonB.add(left_oddsCrs);
		JLabel j9 = new JLabel(SystemConstants.ODDS_CRS);
		j9.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonB.add(j9);
		right_oddsCrs = new JTextField();
		right_oddsCrs.setPreferredSize(new java.awt.Dimension(42, 16));
		right_oddsCrs.setEnabled(false);
		filterCommonB.add(right_oddsCrs);
		oddsCrserr = new JCheckBox();
		oddsCrserr.setPreferredSize(new java.awt.Dimension(20, 16));
		oddsCrserr.setEnabled(false);
		filterCommonB.add(oddsCrserr);
		// a filter condition
		winIndice = new JCheckBox();
		winIndice.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonB.add(winIndice);
		left_winIndice = new JTextField();
		left_winIndice.setPreferredSize(new java.awt.Dimension(42, 16));
		left_winIndice.setEnabled(false);
		filterCommonB.add(left_winIndice);
		JLabel j10 = new JLabel(SystemConstants.WIN_INDICE);
		j10.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonB.add(j10);
		right_winIndice = new JTextField();
		right_winIndice.setPreferredSize(new java.awt.Dimension(42, 16));
		right_winIndice.setEnabled(false);
		filterCommonB.add(right_winIndice);
		winIndiceerr = new JCheckBox();
		winIndiceerr.setPreferredSize(new java.awt.Dimension(20, 16));
		winIndiceerr.setEnabled(false);
		filterCommonB.add(winIndiceerr);
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void initFilterCommonAPane() {
		filterCommonA = new JPanel(new FlowLayout(FlowLayout.LEFT));
		filterCommonA.setPreferredSize(new java.awt.Dimension(300, 380));

		int i = 0;
		// a filter condition
		wins = new JCheckBox();
		wins.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(wins);
		left_wins = new JComboBox(SystemConstants.DO_MATCHES);
		left_wins.setPreferredSize(new java.awt.Dimension(42, 16));
		left_wins.setEditable(false);
		left_wins.setEnabled(false);
		filterCommonA.add(left_wins);
		JLabel j1 = new JLabel(SystemConstants.WINS);
		j1.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j1);
		right_wins = new JComboBox(SystemConstants.DO_MATCHES);
		right_wins.setPreferredSize(new java.awt.Dimension(42, 16));
		right_wins.setEditable(false);
		right_wins.setEnabled(false);
		filterCommonA.add(right_wins);
		winserr = new JCheckBox();
		winserr.setPreferredSize(new java.awt.Dimension(20, 16));
		winserr.setEnabled(false);
		filterCommonA.add(winserr);
		// a filter condition
		draws = new JCheckBox();
		draws.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(draws);
		left_draws = new JComboBox(SystemConstants.DO_MATCHES);
		left_draws.setPreferredSize(new java.awt.Dimension(42, 16));
		left_draws.setEditable(false);
		left_draws.setEnabled(false);
		filterCommonA.add(left_draws);
		JLabel j2 = new JLabel(SystemConstants.DRAWS);
		j2.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j2);
		right_draws = new JComboBox(SystemConstants.DO_MATCHES);
		right_draws.setPreferredSize(new java.awt.Dimension(42, 16));
		right_draws.setEditable(false);
		right_draws.setEnabled(false);
		filterCommonA.add(right_draws);
		drawserr = new JCheckBox();
		drawserr.setPreferredSize(new java.awt.Dimension(20, 16));
		drawserr.setEnabled(false);
		filterCommonA.add(drawserr);
		// a filter condition
		losses = new JCheckBox();
		losses.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(losses);
		left_losses = new JComboBox(SystemConstants.DO_MATCHES);
		left_losses.setPreferredSize(new java.awt.Dimension(42, 16));
		left_losses.setEditable(false);
		left_losses.setEnabled(false);
		filterCommonA.add(left_losses);
		JLabel j3 = new JLabel(SystemConstants.LOSSES);
		j3.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j3);
		right_losses = new JComboBox(SystemConstants.DO_MATCHES);
		right_losses.setPreferredSize(new java.awt.Dimension(42, 16));
		right_losses.setEditable(false);
		right_losses.setEnabled(false);
		filterCommonA.add(right_losses);
		losseserr = new JCheckBox();
		losseserr.setPreferredSize(new java.awt.Dimension(20, 16));
		losseserr.setEnabled(false);
		filterCommonA.add(losseserr);
		// a filter condition
		scores = new JCheckBox();
		scores.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(scores);
		left_scores = new JComboBox();
		left_scores.setPreferredSize(new java.awt.Dimension(42, 16));
		for (i = 0; i < SystemConstants.SCORES_I; i++) {
			left_scores.addItem(i);
		}
		left_scores.setEditable(false);
		left_scores.setEnabled(false);
		filterCommonA.add(left_scores);
		JLabel j4 = new JLabel(SystemConstants.SCORES);
		j4.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j4);
		right_scores = new JComboBox();
		right_scores.setPreferredSize(new java.awt.Dimension(42, 16));
		for (i = 0; i < SystemConstants.SCORES_I; i++) {
			right_scores.addItem(i);
		}
		right_scores.setEditable(false);
		right_scores.setEnabled(false);
		filterCommonA.add(right_scores);
		scoreserr = new JCheckBox();
		scoreserr.setPreferredSize(new java.awt.Dimension(20, 16));
		scoreserr.setEnabled(false);
		filterCommonA.add(scoreserr);
		// a filter condition
		breaks = new JCheckBox();
		breaks.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(breaks);
		left_breaks = new JComboBox(SystemConstants.DO_MATCHES);
		left_breaks.setPreferredSize(new java.awt.Dimension(42, 16));
		left_breaks.setEditable(false);
		left_breaks.setEnabled(false);
		filterCommonA.add(left_breaks);
		JLabel j5 = new JLabel(SystemConstants.BREAKS);
		j5.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j5);
		right_breaks = new JComboBox(SystemConstants.DO_MATCHES);
		right_breaks.setPreferredSize(new java.awt.Dimension(42, 16));
		right_breaks.setEditable(false);
		right_breaks.setEnabled(false);
		filterCommonA.add(right_breaks);
		breakserr = new JCheckBox();
		breakserr.setPreferredSize(new java.awt.Dimension(20, 16));
		breakserr.setEnabled(false);
		filterCommonA.add(breakserr);
		// a filter condition
		continus = new JCheckBox();
		continus.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(continus);
		left_continus = new JComboBox(SystemConstants.DO_MATCHES);
		left_continus.setPreferredSize(new java.awt.Dimension(42, 16));
		left_continus.setEditable(false);
		left_continus.setEnabled(false);
		filterCommonA.add(left_continus);
		JLabel j6 = new JLabel(SystemConstants.CONTINUS);
		j6.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j6);
		right_continus = new JComboBox(SystemConstants.DO_MATCHES);
		right_continus.setPreferredSize(new java.awt.Dimension(42, 16));
		right_continus.setEditable(false);
		right_continus.setEnabled(false);
		filterCommonA.add(right_continus);
		continuserr = new JCheckBox();
		continuserr.setPreferredSize(new java.awt.Dimension(20, 16));
		continuserr.setEnabled(false);
		filterCommonA.add(continuserr);
		// a filter condition
		hostWinContinu = new JCheckBox();
		hostWinContinu.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(hostWinContinu);
		left_hostWinContinu = new JComboBox(SystemConstants.DO_MATCHES);
		left_hostWinContinu.setPreferredSize(new java.awt.Dimension(42, 16));
		left_hostWinContinu.setEditable(false);
		left_hostWinContinu.setEnabled(false);
		filterCommonA.add(left_hostWinContinu);
		JLabel j7 = new JLabel(SystemConstants.HOST_WIN_CON);
		j7.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j7);
		right_hostWinContinu = new JComboBox(SystemConstants.DO_MATCHES);
		right_hostWinContinu.setPreferredSize(new java.awt.Dimension(42, 16));
		right_hostWinContinu.setEditable(false);
		right_hostWinContinu.setEnabled(false);
		filterCommonA.add(right_hostWinContinu);
		hostWinContinuerr = new JCheckBox();
		hostWinContinuerr.setPreferredSize(new java.awt.Dimension(20, 16));
		hostWinContinuerr.setEnabled(false);
		filterCommonA.add(hostWinContinuerr);
		// a filter condition
		hostDrawContinu = new JCheckBox();
		hostDrawContinu.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(hostDrawContinu);
		left_hostDrawContinu = new JComboBox(SystemConstants.DO_MATCHES);
		left_hostDrawContinu.setPreferredSize(new java.awt.Dimension(42, 16));
		left_hostDrawContinu.setEditable(false);
		left_hostDrawContinu.setEnabled(false);
		filterCommonA.add(left_hostDrawContinu);
		JLabel j8 = new JLabel(SystemConstants.HOST_DRAW_CON);
		j8.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j8);
		right_hostDrawContinu = new JComboBox(SystemConstants.DO_MATCHES);
		right_hostDrawContinu.setPreferredSize(new java.awt.Dimension(42, 16));
		right_hostDrawContinu.setEditable(false);
		right_hostDrawContinu.setEnabled(false);
		filterCommonA.add(right_hostDrawContinu);
		hostDrawContinuerr = new JCheckBox();
		hostDrawContinuerr.setPreferredSize(new java.awt.Dimension(20, 16));
		hostDrawContinuerr.setEnabled(false);
		filterCommonA.add(hostDrawContinuerr);
		// a filter condition
		hostLossContinu = new JCheckBox();
		hostLossContinu.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(hostLossContinu);
		left_hostLossContinu = new JComboBox(SystemConstants.DO_MATCHES);
		left_hostLossContinu.setPreferredSize(new java.awt.Dimension(42, 16));
		left_hostLossContinu.setEditable(false);
		left_hostLossContinu.setEnabled(false);
		filterCommonA.add(left_hostLossContinu);
		JLabel j9 = new JLabel(SystemConstants.HOST_LOSS_CON);
		j9.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j9);
		right_hostLossContinu = new JComboBox(SystemConstants.DO_MATCHES);
		right_hostLossContinu.setPreferredSize(new java.awt.Dimension(42, 16));
		right_hostLossContinu.setEditable(false);
		right_hostLossContinu.setEnabled(false);
		filterCommonA.add(right_hostLossContinu);
		hostLossContinuerr = new JCheckBox();
		hostLossContinuerr.setPreferredSize(new java.awt.Dimension(20, 16));
		hostLossContinuerr.setEnabled(false);
		filterCommonA.add(hostLossContinuerr);
		// a filter condition
		winDrawContinu = new JCheckBox();
		winDrawContinu.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(winDrawContinu);
		left_winDrawContinu = new JComboBox(SystemConstants.DO_MATCHES);
		left_winDrawContinu.setPreferredSize(new java.awt.Dimension(42, 16));
		left_winDrawContinu.setEditable(false);
		left_winDrawContinu.setEnabled(false);
		filterCommonA.add(left_winDrawContinu);
		JLabel j10 = new JLabel(SystemConstants.WIN_DRAW_CON);
		j10.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j10);
		right_winDrawContinu = new JComboBox(SystemConstants.DO_MATCHES);
		right_winDrawContinu.setPreferredSize(new java.awt.Dimension(42, 16));
		right_winDrawContinu.setEditable(false);
		right_winDrawContinu.setEnabled(false);
		filterCommonA.add(right_winDrawContinu);
		winDrawContinuerr = new JCheckBox();
		winDrawContinuerr.setPreferredSize(new java.awt.Dimension(20, 16));
		winDrawContinuerr.setEnabled(false);
		filterCommonA.add(winDrawContinuerr);
		// a filter condition
		winLossContinu = new JCheckBox();
		winLossContinu.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(winLossContinu);
		left_winLossContinu = new JComboBox(SystemConstants.DO_MATCHES);
		left_winLossContinu.setPreferredSize(new java.awt.Dimension(42, 16));
		left_winLossContinu.setEditable(false);
		left_winLossContinu.setEnabled(false);
		filterCommonA.add(left_winLossContinu);
		JLabel j11 = new JLabel(SystemConstants.WIN_LOSS_CON);
		j11.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j11);
		right_winLossContinu = new JComboBox(SystemConstants.DO_MATCHES);
		right_winLossContinu.setPreferredSize(new java.awt.Dimension(42, 16));
		right_winLossContinu.setEditable(false);
		right_winLossContinu.setEnabled(false);
		filterCommonA.add(right_winLossContinu);
		winLossContinuerr = new JCheckBox();
		winLossContinuerr.setPreferredSize(new java.awt.Dimension(20, 16));
		winLossContinuerr.setEnabled(false);
		filterCommonA.add(winLossContinuerr);
		// a filter condition
		drawLossContinu = new JCheckBox();
		drawLossContinu.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(drawLossContinu);
		left_drawLossContinu = new JComboBox(SystemConstants.DO_MATCHES);
		left_drawLossContinu.setPreferredSize(new java.awt.Dimension(42, 16));
		left_drawLossContinu.setEditable(false);
		left_drawLossContinu.setEnabled(false);
		filterCommonA.add(left_drawLossContinu);
		JLabel j12 = new JLabel(SystemConstants.DRAW_LOSS_CON);
		j12.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j12);
		right_drawLossContinu = new JComboBox(SystemConstants.DO_MATCHES);
		right_drawLossContinu.setPreferredSize(new java.awt.Dimension(42, 16));
		right_drawLossContinu.setEditable(false);
		right_drawLossContinu.setEnabled(false);
		filterCommonA.add(right_drawLossContinu);
		drawLossContinuerr = new JCheckBox();
		drawLossContinuerr.setPreferredSize(new java.awt.Dimension(20, 16));
		drawLossContinuerr.setEnabled(false);
		filterCommonA.add(drawLossContinuerr);
		// a filter condition
		conPartSum = new JCheckBox();
		conPartSum.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(conPartSum);
		left_conPartSum = new JComboBox();
		left_conPartSum.setPreferredSize(new java.awt.Dimension(42, 16));
		for (i = 0; i < SystemConstants.CON_SUM_I; i++) {
			left_conPartSum.addItem(i);
		}
		left_conPartSum.setEditable(false);
		left_conPartSum.setEnabled(false);
		filterCommonA.add(left_conPartSum);
		JLabel j13 = new JLabel(SystemConstants.CON_SUM);
		j13.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j13);
		right_conPartSum = new JComboBox();
		right_conPartSum.setPreferredSize(new java.awt.Dimension(42, 16));
		for (i = 0; i < SystemConstants.CON_SUM_I; i++) {
			right_conPartSum.addItem(i);
		}
		right_conPartSum.setEditable(false);
		right_conPartSum.setEnabled(false);
		filterCommonA.add(right_conPartSum);
		conPartSumerr = new JCheckBox();
		conPartSumerr.setPreferredSize(new java.awt.Dimension(20, 16));
		conPartSumerr.setEnabled(false);
		filterCommonA.add(conPartSumerr);
		// a filter condition
		conPartCrs = new JCheckBox();
		conPartCrs.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(conPartCrs);
		left_conPartCrs = new JComboBox();
		left_conPartCrs.setPreferredSize(new java.awt.Dimension(42, 16));
		for (i = 0; i < SystemConstants.CON_CRS_I; i++) {
			left_conPartCrs.addItem(i);
		}
		left_conPartCrs.setEditable(false);
		left_conPartCrs.setEnabled(false);
		filterCommonA.add(left_conPartCrs);
		JLabel j14 = new JLabel(SystemConstants.CON_CRS);
		j14.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j14);
		right_conPartCrs = new JComboBox();
		right_conPartCrs.setPreferredSize(new java.awt.Dimension(42, 16));
		for (i = 0; i < SystemConstants.CON_CRS_I; i++) {
			right_conPartCrs.addItem(i);
		}
		right_conPartCrs.setEditable(false);
		right_conPartCrs.setEnabled(false);
		filterCommonA.add(right_conPartCrs);
		conPartCrserr = new JCheckBox();
		conPartCrserr.setPreferredSize(new java.awt.Dimension(20, 16));
		conPartCrserr.setEnabled(false);
		filterCommonA.add(conPartCrserr);
		// a filter condition
		crsSum = new JCheckBox();
		crsSum.setPreferredSize(new java.awt.Dimension(20, 16));
		filterCommonA.add(crsSum);
		left_crsSum = new JComboBox();
		left_crsSum.setPreferredSize(new java.awt.Dimension(42, 16));
		for (i = 0; i < SystemConstants.CRS_SUM_I; i++) {
			left_crsSum.addItem(i);
		}
		left_crsSum.setEditable(false);
		left_crsSum.setEnabled(false);
		filterCommonA.add(left_crsSum);
		JLabel j15 = new JLabel(SystemConstants.CRS_SUM);
		j15.setPreferredSize(new java.awt.Dimension(100, 16));
		filterCommonA.add(j15);
		right_crsSum = new JComboBox();
		right_crsSum.setPreferredSize(new java.awt.Dimension(42, 16));
		for (i = 0; i < SystemConstants.CRS_SUM_I; i++) {
			right_crsSum.addItem(i);
		}
		right_crsSum.setEditable(false);
		right_crsSum.setEnabled(false);
		filterCommonA.add(right_crsSum);
		crsSumerr = new JCheckBox();
		crsSumerr.setPreferredSize(new java.awt.Dimension(20, 16));
		crsSumerr.setEnabled(false);
		filterCommonA.add(crsSumerr);

		// filterScroll = new JScrollPane(filterCommonA);
	}
}
