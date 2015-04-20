/**
 * @文件名称: LotteryMenuBar.java
 * @类路径:   com.rb.lottery.UI.menubar
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 上午11:49:27
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.menubar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.rb.lottery.client.common.ColorConstants;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.handler.AnalysisHandler;
import com.rb.lottery.client.handler.FilterHandler;
import com.rb.lottery.client.handler.HelpHandler;
import com.rb.lottery.client.handler.ProjectHandler;
import com.rb.lottery.client.handler.QueryHandler;
import com.rb.lottery.client.handler.SystemConfHandler;

/**
 * @类功能说明: 菜单栏
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-13 上午11:49:27
 * @版本: 1.0.0
 */

public class LotteryMenuBar extends JMenuBar {

	/**
	 * @Fields serialVersionUID: TODO
	 */

	private JMenu file;
	private JMenu data;
	private JMenu betret;
	private JMenu table;
	private JMenu system;
	private JMenu help;
	private JMenuItem new_item;
	private JMenuItem openpro_item;
	private JMenuItem savepro_item;
	private JMenuItem openret_item;
	private JMenuItem saveret_item;
	private JMenuItem exit_item;
	private JMenuItem study_item;
	private JMenuItem compare_item;
	private JMenuItem mediaforcast_item;
	private JMenuItem survey_item;
	private JMenuItem mulsta_item;
	private JMenuItem mulana_item;
	private JMenuItem tzfesta_item;
	private JMenuItem tzfeana_item;
	private JMenuItem otesta_item;
	private JMenuItem oteana_item;
	private JMenuItem winquery_item;
	private JMenu betscat_item;
	private JMenuItem betretsta_item;
	private JMenuItem betscatsta_item;
	private JMenuItem batprofilter_item;
	private JMenuItem batmedfilter_item;
	private JMenuItem mergebet_item;
	private JMenuItem betundo_item;
	private JMenuItem betredo_item;
	private JMenuItem seturdo_item;
	private JMenuItem filrecycle_item;
	private JMenuItem datastatable_item;
	private JMenuItem hismedsta_item;
	private JMenuItem hisoddsta_item;
	private JMenuItem retprot_item;
	private JMenuItem oddprot_item;
	private JMenuItem medprot_item;
	private JMenuItem dataupdate_item;
	private JMenuItem datainit_item;
	private JMenuItem filman_item;
	private JMenuItem softupdate_item;
	private JMenuItem betedit_item;
	private JMenuItem notepad_item;
	private JMenuItem calc_item;
	private JMenuItem systemset_item;
	private JMenuItem usehelp_item;
	private JMenuItem olservice_item;
	private JMenuItem nowolservice_item;
	private JMenuItem homepage_item;
	private JMenuItem forum_item;
	private JMenuItem about_item;

	private static final long serialVersionUID = 2391919179135150608L;

	public LotteryMenuBar() {
		super();
		init();
	}

	public void init() {
		this.setBackground(ColorConstants.MENUBAR_BG);

		file = new JMenu(SystemConstants.MENU_FILE);
		new_item = new JMenuItem(SystemConstants.MENU_NEWPRO);
		new_item.addActionListener(new ProjectHandler(
				CommandConstants.CMD_NEWPRO));
		file.add(new_item);

		file.addSeparator();

		openpro_item = new JMenuItem(SystemConstants.MENU_OPENPRO);
		openpro_item.addActionListener(new ProjectHandler(
				CommandConstants.CMD_OPENPRO));
		file.add(openpro_item);

		savepro_item = new JMenuItem(SystemConstants.MENU_SAVEPRO);
		savepro_item.addActionListener(new ProjectHandler(
				CommandConstants.CMD_SAVEPRO));
		file.add(savepro_item);

		file.addSeparator();

		openret_item = new JMenuItem(SystemConstants.MENU_OPENRET);
		openret_item.addActionListener(new ProjectHandler(
				CommandConstants.CMD_OPENRET));
		file.add(openret_item);

		saveret_item = new JMenuItem(SystemConstants.MENU_SAVERET);
		saveret_item.addActionListener(new ProjectHandler(
				SystemConstants.MENU_SAVERET));
		file.add(saveret_item);

		file.addSeparator();

		exit_item = new JMenuItem(SystemConstants.MENU_EXIT);
		exit_item.addActionListener(new SystemConfHandler(
				CommandConstants.CMD_EXIT));
		file.add(exit_item);
		this.add(file);

		data = new JMenu(SystemConstants.MENU_PROFDATA);
		study_item = new JMenuItem(SystemConstants.MENU_STUDY);
		study_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_STUDY));
		data.add(study_item);
		data.addSeparator();
		compare_item = new JMenuItem(SystemConstants.MENU_COMPARE);
		compare_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_COMPARE));
		data.add(compare_item);
		data.addSeparator();
		mediaforcast_item = new JMenuItem(SystemConstants.MENU_MEDIAFORCAST);
		mediaforcast_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_MEDIAFORCAST));
		data.add(mediaforcast_item);
		survey_item = new JMenuItem(SystemConstants.MENU_SURVEY);
		survey_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_SURVEY));
		data.add(survey_item);
		data.addSeparator();
		mulsta_item = new JMenuItem(SystemConstants.MENU_MULSTA);
		mulsta_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_MULSTA));
		data.add(mulsta_item);
		mulana_item = new JMenuItem(SystemConstants.MENU_MULANA);
		mulana_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_MULANA));
		data.add(mulana_item);
		data.addSeparator();
		tzfesta_item = new JMenuItem(SystemConstants.MENU_2048STA);
		tzfesta_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_2048STA));
		data.add(tzfesta_item);
		tzfeana_item = new JMenuItem(SystemConstants.MENU_2048ANA);
		tzfeana_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_2048ANA));
		data.add(tzfeana_item);
		data.addSeparator();
		otesta_item = new JMenuItem(SystemConstants.MENU_128STA);
		otesta_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_128STA));
		data.add(otesta_item);
		oteana_item = new JMenuItem(SystemConstants.MENU_128ANA);
		oteana_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_128ANA));
		data.add(oteana_item);
		this.add(data);

		betret = new JMenu(SystemConstants.MENU_BETRET);
		winquery_item = new JMenuItem(SystemConstants.MENU_WINQUERY);
		winquery_item.addActionListener(new QueryHandler(
				CommandConstants.CMD_WINQUERY));
		betret.add(winquery_item);

		betscat_item = new JMenu(SystemConstants.MENU_BETSCAT);
		// betscat_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,KeyEvent.CTRL_MASK,false));
		// betscat_item.addActionListener(new ActionHandler());
		betretsta_item = new JMenuItem(SystemConstants.MENU_BETRETSTA);
		betretsta_item.addActionListener(new FilterHandler(
				CommandConstants.CMD_BETRETSTA));
		betscat_item.add(betretsta_item);
		betscatsta_item = new JMenuItem(SystemConstants.MENU_BETSCATSTA);
		betscatsta_item.addActionListener(new FilterHandler(
				CommandConstants.CMD_BETSCATSTA));
		betscat_item.add(betscatsta_item);
		betret.add(betscat_item);
		betret.addSeparator();
		batprofilter_item = new JMenuItem(SystemConstants.MENU_BATPROFILTER);
		batprofilter_item.addActionListener(new FilterHandler(
				CommandConstants.CMD_BATPROFILTER));
		betret.add(batprofilter_item);
		batmedfilter_item = new JMenuItem(SystemConstants.MENU_BATMEDFILTER);
		batmedfilter_item.addActionListener(new FilterHandler(
				CommandConstants.CMD_BATMEDFILTER));
		betret.add(batmedfilter_item);
		mergebet_item = new JMenuItem(SystemConstants.MENU_MERGEBET);
		mergebet_item.addActionListener(new FilterHandler(
				CommandConstants.CMD_MERGEBET));
		betret.add(mergebet_item);
		betret.addSeparator();
		betundo_item = new JMenuItem(SystemConstants.MENU_BETUNDO);
		betundo_item.addActionListener(new FilterHandler(
				CommandConstants.CMD_BETUNDO));
		betundo_item.setEnabled(false);
		betret.add(betundo_item);
		betredo_item = new JMenuItem(SystemConstants.MENU_BETREDO);
		betredo_item.addActionListener(new FilterHandler(
				CommandConstants.CMD_BETREDO));
		betredo_item.setEnabled(false);
		betret.add(betredo_item);
		seturdo_item = new JMenuItem(SystemConstants.MENU_SETURDO);
		seturdo_item.addActionListener(new FilterHandler(
				CommandConstants.CMD_SETURDO));
		betret.add(seturdo_item);
		betret.addSeparator();
		filrecycle_item = new JMenuItem(SystemConstants.MENU_FILRECYCLE);
		filrecycle_item.addActionListener(new FilterHandler(
				CommandConstants.CMD_FILRECYCLE));
		filrecycle_item.setEnabled(false);
		betret.add(filrecycle_item);
		this.add(betret);

		table = new JMenu(SystemConstants.MENU_TABLE);
		datastatable_item = new JMenuItem(SystemConstants.MENU_DATASTATABLE);
		datastatable_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_DATASTATABLE));
		table.add(datastatable_item);
		table.addSeparator();
		hismedsta_item = new JMenuItem(SystemConstants.MENU_HISMEDSTA);
		hismedsta_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_HISMEDSTA));
		table.add(hismedsta_item);
		hisoddsta_item = new JMenuItem(SystemConstants.MENU_HISODDSTA);
		hisoddsta_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_HISODDSTA));
		table.add(hisoddsta_item);
		table.addSeparator();
		retprot_item = new JMenuItem(SystemConstants.MENU_RETPROT);
		retprot_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_RETPROT));
		table.add(retprot_item);
		oddprot_item = new JMenuItem(SystemConstants.MENU_ODDPROT);
		oddprot_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_ODDPROT));
		table.add(oddprot_item);
		medprot_item = new JMenuItem(SystemConstants.MENU_MEDPROT);
		medprot_item.addActionListener(new AnalysisHandler(
				CommandConstants.CMD_MEDPROT));
		table.add(medprot_item);
		this.add(table);

		system = new JMenu(SystemConstants.MENU_SYSTEM);
		dataupdate_item = new JMenuItem(SystemConstants.MENU_DATAUPDATE);
		dataupdate_item.addActionListener(new SystemConfHandler(
				CommandConstants.CMD_DATAUPDATE));
		system.add(dataupdate_item);
		datainit_item = new JMenuItem(SystemConstants.MENU_DATAINIT);
		datainit_item.addActionListener(new SystemConfHandler(
				CommandConstants.CMD_DATAINIT));
		system.add(datainit_item);
		filman_item = new JMenuItem(SystemConstants.MENU_FILMAN);
		filman_item.addActionListener(new SystemConfHandler(
				CommandConstants.CMD_FILMAN));
		system.add(filman_item);
		softupdate_item = new JMenuItem(SystemConstants.MENU_SOFTUPDATE);
		softupdate_item.addActionListener(new SystemConfHandler(
				CommandConstants.CMD_SOFTUPDATE));
		system.add(softupdate_item);
		system.addSeparator();
		betedit_item = new JMenuItem(SystemConstants.MENU_BETEDIT);
		betedit_item.addActionListener(new SystemConfHandler(
				CommandConstants.CMD_BETEDIT));
		system.add(betedit_item);
		notepad_item = new JMenuItem(SystemConstants.MENU_NOTEPAD);
		notepad_item.addActionListener(new SystemConfHandler(
				CommandConstants.CMD_NOTEPAD));
		system.add(notepad_item);
		calc_item = new JMenuItem(SystemConstants.MENU_CALC);
		calc_item.addActionListener(new SystemConfHandler(
				CommandConstants.CMD_CALC));
		system.add(calc_item);
		system.addSeparator();
		systemset_item = new JMenuItem(SystemConstants.MENU_SYSTEMSET);
		systemset_item.addActionListener(new SystemConfHandler(
				CommandConstants.CMD_SYSTEMSET));
		system.add(systemset_item);
		this.add(system);

		help = new JMenu(SystemConstants.MENU_HELP);
		usehelp_item = new JMenuItem(SystemConstants.MENU_USEHELP);
		usehelp_item.addActionListener(new HelpHandler(
				CommandConstants.CMD_USEHELP));
		help.add(usehelp_item);
		olservice_item = new JMenuItem(SystemConstants.MENU_OLSERVICE);
		olservice_item.addActionListener(new HelpHandler(
				CommandConstants.CMD_OLSERVICE));
		help.add(olservice_item);
		nowolservice_item = new JMenuItem(SystemConstants.MENU_NOWOLSERVICE);
		nowolservice_item.addActionListener(new HelpHandler(
				CommandConstants.CMD_NOWOLSERVICE));
		help.add(nowolservice_item);
		help.addSeparator();
		homepage_item = new JMenuItem(SystemConstants.MENU_HOMEPAGE);
		homepage_item.addActionListener(new HelpHandler(
				CommandConstants.CMD_HOMEPAGE));
		help.add(homepage_item);
		forum_item = new JMenuItem(SystemConstants.MENU_FORUM);
		forum_item
				.addActionListener(new HelpHandler(CommandConstants.CMD_FORUM));
		help.add(forum_item);
		help.addSeparator();
		about_item = new JMenuItem(SystemConstants.MENU_ABOUT);
		about_item
				.addActionListener(new HelpHandler(CommandConstants.CMD_ABOUT));
		help.add(about_item);
		this.add(help);
	}
}
