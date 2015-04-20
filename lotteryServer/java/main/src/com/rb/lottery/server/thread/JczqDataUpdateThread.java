/**
 * @文件名称: JczqDataUpdateThread.java
 * @类路径:   com.rb.lottery.thread
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-5 下午02:11:27
 * @版本:     1.0.0
 */
package com.rb.lottery.server.thread;

import info.informatica.doc.DocumentException;
import info.informatica.doc.DocumentFragment;
import info.informatica.html.HTMLDoc;
import info.informatica.html.HTMLFragment;
import info.informatica.html.NameTagFinder;
import info.informatica.html.tag.HTMLTag;
import info.informatica.html.tag.TagParsingException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.rb.lottery.domain.JczqMatch;
import com.rb.lottery.domain.MatchStatus;
import com.rb.lottery.server.common.EncodingConstants;
import com.rb.lottery.server.common.HtmlTagConstants;
import com.rb.lottery.server.common.MessageConstants;
import com.rb.lottery.server.common.RegexConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.server.common.SystemFunctions;
import com.rb.lottery.server.dao.JczqDAO;
import com.rb.lottery.server.exception.LotteryException;
import com.rb.lottery.server.manager.IOManager;
import com.rb.lottery.server.http.DataHttpClient;
import com.rb.lottery.system.SysConfig;
import com.rb.lottery.system.SystemEnvironment;

/**
 * @类功能说明:竞彩足球数据更新线程
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-5 下午02:11:27
 * @版本: 1.0.0
 */

public class JczqDataUpdateThread extends DataUpdateThread {

	private static final Logger log = Logger
			.getLogger(JczqDataUpdateThread.class);

	private static final String JCZQ_THREAD = "竞彩足球数据更新线程";

	// 当前最新期
	private String period;

	/**
	 * @类名: JczqDataUpdateThread.java
	 * @描述: TODO
	 * @param group
	 * @param jczqSite
	 */
	public JczqDataUpdateThread(ThreadGroup group, String site) {
		super(group, 1, site);
		this.setName(JCZQ_THREAD);
	}

	public JczqDataUpdateThread(ThreadGroup group, String site, String period) {
		super(group, 1, site);
		this.period = period;
		this.setName(JCZQ_THREAD);
	}

	/**
	 * @return period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period
	 *            period
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * @方法说明: 从指定网站得到数据进行解析
	 * @参数: @param dataSite
	 * @return void
	 * @throws
	 */
	@Override
	public int updateFromSite() {
		log.info(JCZQ_THREAD + SystemConstants.START);
		DateFormat format = SystemEnvironment.getInstance().dateformat;
		Date current = null;
		Calendar cal = Calendar.getInstance();
		try {
			if(period.indexOf(":") < 0) {
				period += " 00:00";
			}
			current = format.parse(period);
			cal.setTime(current);
			// 更新JCZQ_DATE_BEFORE天前的信息
			cal.add(Calendar.DATE,
					-SystemEnvironment.getInstance().jczq_update_ahead);
			current = cal.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar tcal = Calendar.getInstance();
		Date today = new Date();
		while (!current.after(today)) {
			String curr_str = format.format(current);
			String qh = curr_str.replaceAll(SystemConstants.PART,
					SystemConstants.EMPTY_STRING);
			if (qh.matches(RegexConstants.INTEGER_REGEX)) {
				qihao = Integer.parseInt(qh);
			}
			boolean ud = update(curr_str);
			// 网站维护或其他原因
			if (!ud) {
				log.error(curr_str + MessageConstants.NO_DATA);
				// TODO 从备用网站更新
			}
			cal.setTime(current);
			cal.add(Calendar.DATE, 1);
			current = cal.getTime();
		}
		String ty_str = format.format(today).trim();
		ty_str = ty_str.replaceAll(SystemConstants.PART,
				SystemConstants.EMPTY_STRING);
		int pd = 0;
		if (ty_str.matches(RegexConstants.INTEGER_REGEX)) {
			pd = Integer.parseInt(ty_str);
		}
		log.info(JCZQ_THREAD + SystemConstants.END);
		// 更新系统配置
		String today_str = format.format(today);
		SysConfig.getInstance().setJczqPeriod(today_str);
		return pd;
	}

	/**
	 * @方法说明:
	 * @参数: @param current
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	private boolean update(String current_date) {
		String site = dataSite;
		site += SystemConstants.JCZQ_UPDATE_PARAMETER + current_date;
		String srcData = DataHttpClient.getGetResponse(site,
				EncodingConstants.UTF8_ENCODING);
		if (srcData == null || srcData.equals(SystemConstants.EMPTY_STRING)) {
			JOptionPane.showMessageDialog(null, MessageConstants.CONNECT_ERROR);
			return false;
		}
		int valid = doHtmlAnalyzing(srcData);
		return (valid != 0);
	}

	/**
	 * @方法说明: html源文件解析
	 * @参数: @param srcData html源文件
	 * @return int 返回期号
	 * @throws
	 */
	@Override
	public int doHtmlAnalyzing(String srcData) {
		int qh = doJczqHtmlAnalyzing(srcData);
		return qh;
	}

	/**
	 * @方法说明: JCZQ html解析
	 * @参数: @param srcData
	 * @参数: @return
	 * @return int 返回期号
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private int doJczqHtmlAnalyzing(String srcData) {
		JczqDAO dao = new JczqDAO();
		int qh = 0;
		HTMLDoc doc = new HTMLDoc();
		doc.load(srcData);
		// Get the HTML
		HTMLFragment html = doc.getHTML();
		Map<String, List<JczqMatch>> bmMap = new HashMap<String, List<JczqMatch>>();
		HTMLTag table_tag = html.getNameFinder(HtmlTagConstants.HTML_TABLE)
				.getTagBlock();
		while (table_tag != null) {
			try {
				String attr_cla = table_tag.getAttributes().getAttribute(
						HtmlTagConstants.HTML_CLASS);
				if (attr_cla != null
						&& attr_cla
								.equalsIgnoreCase(HtmlTagConstants.HTML_DC_TABLE)) {
					String period_id = table_tag.getAttributes().getAttribute(
							HtmlTagConstants.HTML_ID);
					if (period_id != null) {
						period_id = period_id.substring(2);
						String ty_str = period_id.replaceAll(
								SystemConstants.PART,
								SystemConstants.EMPTY_STRING);
						if (ty_str.matches(RegexConstants.INTEGER_REGEX)) {
							qh = Integer.parseInt(ty_str);
						}
						if (qh == 0) {
							qh = qihao;
						}
						List<JczqMatch> match = getBetMatchFromJczqHtmlTag(
								table_tag, qh, period_id);
						if (match != null && match.size() > 0) {
							if (SAVE_MODE != 1) {
								dao.saveOrUpdateAll(match);
								log.info("Save or update " + period_id
										+ " jczq " + match.size()
										+ " record to database.");
							}
							bmMap.put(period_id, match);
						}
					}
				}
				html.removeBlock(table_tag);
				NameTagFinder tag_finder = html
						.getNameFinder(HtmlTagConstants.HTML_TABLE);
				if (tag_finder == null) {
					break;
				} else {
					table_tag = tag_finder.getTagBlock();
				}
			} catch (TagParsingException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		// 更新数据文件
		if (SAVE_MODE != 2) {
			boolean status = IOManager.getInstance().updateJczqFiles(bmMap);
			if (!status) {
				log.error("Update " + qh + " jczq file failed.");
				try {
					throw (new LotteryException(15));
				} catch (LotteryException e) {
					e.printStackTrace();
				}
			}
		}
		return qh;
	}

	/**
	 * @方法说明: 将对应HTMLTag数据解析为竞彩足球比赛投注信息
	 * @参数: @param tag HTMLTag数据
	 * @参数: @param qihao 期号
	 * @参数: @param period 投注日期
	 * @return BetMatch 比赛投注信息
	 * @throws
	 */
	private List<JczqMatch> getBetMatchFromJczqHtmlTag(HTMLTag tag, int qihao,
			String period) {
		List<JczqMatch> matches = new ArrayList<JczqMatch>();
		HTMLDoc doc = new HTMLDoc();
		doc.load(tag.toString());
		HTMLFragment html = doc.getHTML();
		HTMLTag currentTag = html.getNameFinder(HtmlTagConstants.HTML_TR)
				.getTagBlock();
		try {
			while (currentTag != null) {
				JczqMatch match = getBetMatchFromMatchTag(currentTag, qihao,
						period);
				matches.add(match);
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TR)
						.getTagBlock();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return matches;
	}

	private JczqMatch getBetMatchFromMatchTag(HTMLTag tag, int qihao,
			String period) {
		JczqMatch match = new JczqMatch();
		HTMLDoc doc = new HTMLDoc();
		doc.load(tag.toString());
		HTMLFragment html = doc.getHTML();
		match.setType(2);
		// 竞彩无期号
		match.setQihao(qihao + SystemConstants.EMPTY_STRING);
		match.setPeriod(period);
		match.setStatus(new MatchStatus((long) 0));
		try {
			// 处理id
			HTMLTag currentTag = html
					.getNameFinder(HtmlTagConstants.HTML_LABEL).getTagBlock();
			String id = currentTag.getTagData().toString().trim();
			if (id != null) {
				int indx = id.indexOf(SystemConstants.RIGHT_ANGLE);
				id = id.substring(indx + 1);
				match.setMatchId(id);
			}
			// 处理competition
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_A)
					.getTagBlock();
			if (currentTag.getTagData() != null) {
				String competition = currentTag.getTagData().toString().trim();
				match.setCompetition(competition);
			}
			// 处理time
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String time = currentTag.getTagData().toString().trim();
			/*
			 * DateFormat df = new SimpleDateFormat(MATCH_DATEFORMAT); Date time
			 * = null; try { time = df.parse(t); } catch (ParseException e) {
			 * e.printStackTrace(); }
			 */
			if (time != null) {
				match.setTime(time);
			}
			// 处理VS
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			if (currentTag != null) {
				DocumentFragment hk = currentTag.getTagData();
				if (hk != null) {
					String homeRank = hk.toString().trim();
					match.setHomeRank(homeRank);
				}
				html.removeBlock(currentTag);
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_A)
					.getTagBlock();
			if (currentTag.getTagData() != null) {
				String home = currentTag.getTagData().toString().trim();

				home = home.replaceAll(HtmlTagConstants.HTML_BLANK,
						SystemConstants.BLANK_STRING);
				match.setHome(home);
			}
			// 处理concede
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			String ccd = currentTag.getTagData().toString().trim();
			if (ccd.indexOf(HtmlTagConstants.HTML_STRONG) >= 0) {
				int stt = ccd.indexOf(HtmlTagConstants.HTML_SEND_TAG) + 1;
				int ent = ccd.indexOf(HtmlTagConstants.HTML_EEND_TAG);
				ccd = ccd.substring(stt, ent);
			}
			ccd = ccd.replace(SystemConstants.ADD, "");
			int concede = 0;
			if (ccd != null) {
				if (ccd.matches(RegexConstants.INTEGER_REGEX)) {
					concede = Integer.parseInt(ccd);
					match.setConcede(concede);
				} else {
					HTMLDoc tmp = new HTMLDoc();
					tmp.load(ccd);
					HTMLFragment tmpHtml = tmp.getHTML();
					HTMLTag tmpTag = tmpHtml.getNameFinder(
							HtmlTagConstants.HTML_SPAN).getTagBlock();
					ccd = tmpTag.getTagData().toString().trim();
					if (ccd.matches(RegexConstants.INTEGER_REGEX)) {
						concede = Integer.parseInt(ccd);
						match.setConcede(concede);
					}
				}
			}
			// 处理VS
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_A)
					.getTagBlock();
			if (currentTag.getTagData() != null) {
				String visitor = currentTag.getTagData().toString().trim();
				visitor = visitor.replaceAll(HtmlTagConstants.HTML_BLANK,
						SystemConstants.BLANK_STRING);
				match.setVisitor(visitor);
			}
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			if (currentTag != null) {
				DocumentFragment vk = currentTag.getTagData();
				if (vk != null) {
					String vistRank = vk.toString().trim();
					match.setVistRank(vistRank);
				}
			}
			// 处理Odds
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			if (currentTag.getTagData() != null) {
				String wos = currentTag.getTagData().toString().trim();
				if (wos.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
					double winOdds = Double.parseDouble(wos);
					match.setWinOdds(winOdds);
				}
			}
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			if (currentTag.getTagData() != null) {
				String dos = currentTag.getTagData().toString().trim();
				if (dos.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
					double drawOdds = Double.parseDouble(dos);
					match.setDrawOdds(drawOdds);
				}
			}
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			if (currentTag.getTagData() != null) {
				String los = currentTag.getTagData().toString().trim();
				if (los.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
					double lossOdds = Double.parseDouble(los);
					match.setLossOdds(lossOdds);
				}
			}
			// 处理Rates
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String wrs = currentTag.getTagData().toString().trim();
			if (wrs.matches(RegexConstants.PERCENT_FLOAT_REGEX)) {
				double winRate = SystemFunctions.percentToDouble(wrs);
				match.setWinRate(winRate);
			}
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String drs = currentTag.getTagData().toString().trim();
			if (drs.matches(RegexConstants.PERCENT_FLOAT_REGEX)) {
				double drawRate = SystemFunctions.percentToDouble(drs);
				match.setDrawRate(drawRate);
			}
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String lrs = currentTag.getTagData().toString().trim();
			if (lrs.matches(RegexConstants.PERCENT_FLOAT_REGEX)) {
				double lossRate = SystemFunctions.percentToDouble(lrs);
				match.setLossRate(lossRate);
			}
			// 处理赛果、即时SP和开奖SP
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			String sre = currentTag.toString().trim();
			if (sre.indexOf(HtmlTagConstants.HTML_SPAN) >= 0) {
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
						.getTagBlock();
				String score = currentTag.getTagData().toString().trim();
				if (score.indexOf(SystemConstants.COLON) > 0) {
					String[] sco = score.split(SystemConstants.COLON);
					if (sco[0].matches(RegexConstants.INTEGER_REGEX)) {
						match.setHomeGoal(Integer.parseInt(sco[0]));
					}
					if (sco[1].matches(RegexConstants.INTEGER_REGEX)) {
						match.setVistGoal(Integer.parseInt(sco[1]));
					}
					match.setMStatus((long) 3);
					match.generateKjNumber();
				} else if (score.equals(SystemConstants.DELAY)) {
					match.setMStatus((long) 41);
				} else if (score.equals(SystemConstants.CUTTING)) {
					match.setMStatus((long) 42);
				} else {
					// TODO check match status according to the time
					match.setMStatus((long) 0);
				}
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			MatchStatus status = match.getStatus();
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			if (currentTag == null) {
				return match;
			}
			String isSP = currentTag.getTagData().toString();
			if (isSP.indexOf(HtmlTagConstants.HTML_SPAN) >= 0) {
				html.removePair(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
						.getTagBlock();
				String jsWinSP = currentTag.getTagData().toString().trim();
				if (jsWinSP.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
					double sp = Double.parseDouble(jsWinSP);
					match.setJsWinSP(sp);
					if (3 == status.getSid()) {
						match.setSP(sp);
					} else {
						match.setSP(1.0);
					}
				}
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			isSP = currentTag.getTagData().toString();
			if (isSP.indexOf(HtmlTagConstants.HTML_SPAN) >= 0) {
				html.removePair(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
						.getTagBlock();
				String jsDrawSP = currentTag.getTagData().toString().trim();
				if (jsDrawSP.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
					double sp = Double.parseDouble(jsDrawSP);
					match.setJsDrawSP(sp);
					if (3 == status.getSid()) {
						match.setSP(sp);
					} else {
						match.setSP(1.0);
					}
				}
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			isSP = currentTag.getTagData().toString();
			if (isSP.indexOf(HtmlTagConstants.HTML_SPAN) >= 0) {
				html.removePair(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
						.getTagBlock();
				String jsLossSP = currentTag.getTagData().toString().trim();
				if (jsLossSP.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
					double sp = Double.parseDouble(jsLossSP);
					match.setJsLossSP(sp);
					if (3 == status.getSid()) {
						match.setSP(sp);
					} else {
						match.setSP(1.0);
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return match;
	}
}
