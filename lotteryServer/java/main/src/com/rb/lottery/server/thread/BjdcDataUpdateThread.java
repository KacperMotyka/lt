/**
 * @文件名称: BjdcDataUpdateThread.java
 * @类路径:   com.rb.lottery.thread
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-5 下午02:10:47
 * @版本:     1.0.0
 */
package com.rb.lottery.server.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import info.informatica.doc.DocumentException;
import info.informatica.doc.DocumentFragment;
import info.informatica.html.HTMLDoc;
import info.informatica.html.HTMLFragment;
import info.informatica.html.NameTagFinder;
import info.informatica.html.tag.HTMLTag;
import info.informatica.html.tag.TagParsingException;

import com.rb.lottery.domain.BjdcMatch;
import com.rb.lottery.domain.MatchStatus;
import com.rb.lottery.server.common.EncodingConstants;
import com.rb.lottery.server.common.HtmlTagConstants;
import com.rb.lottery.server.common.MessageConstants;
import com.rb.lottery.server.common.RegexConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.server.common.SystemFunctions;
import com.rb.lottery.server.dao.BjdcDAO;
import com.rb.lottery.server.exception.LotteryException;
import com.rb.lottery.server.http.DataHttpClient;
import com.rb.lottery.server.manager.IOManager;
import com.rb.lottery.system.SysConfig;

/**
 * @类功能说明:北京单场数据更新线程
 * @类修改者:robin
 * @修改日期:2011-12-8
 * @修改说明:添加北京单场开奖数据更新
 * @作者: robin
 * @创建时间: 2011-12-5 下午02:10:47
 * @版本: 1.0.0
 */

public class BjdcDataUpdateThread extends DataUpdateThread {

	private static final Logger log = Logger
			.getLogger(BjdcDataUpdateThread.class);
	
	private static final String BJDC_THREAD = "北京单场数据更新线程";

	// 当前最新期号
	private int lastQihao;

	/**
	 * @return lastQihao
	 */
	public int getLastQihao() {
		return lastQihao;
	}

	/**
	 * @param lastQihao
	 *            lastQihao
	 */
	public void setLastQihao(int lastQihao) {
		this.lastQihao = lastQihao;
	}

	public BjdcDataUpdateThread(ThreadGroup group, String site, int qihao) {
		super(group, 1, site, qihao);
		this.setName(BJDC_THREAD);
	}

	/**
	 * @方法说明: 从指定网站得到数据进行解析
	 * @参数: @param dataSite
	 * @return void
	 * @throws
	 */
	@Override
	public int updateFromSite() {
		log.info(BJDC_THREAD + SystemConstants.START);
		lastQihao = getLastQihaoFromSite();
		int lastYM = lastQihao / 100;
		int currYM = qihao / 100;
		while (qihao <= lastQihao) {
			currYM = qihao / 100;
			int ud = update(qihao);
			if (ud == 0) {
				log.error(qihao + MessageConstants.UNKNOWN_ERROR);
				// return qihao;
			} else if (ud == 1) {
				if (currYM < lastYM) {
					SysConfig.getInstance().setLastBjdcMonth(currYM % 100);
					SysConfig.getInstance().setLastBjdcCount(qihao % 100 - 1);
					qihao = lastYM * 100;
				}
			}
			qihao++;
		}
		log.info(BJDC_THREAD + SystemConstants.END);
		// 更新系统配置
		SysConfig.getInstance().setBjdcQihao(lastQihao + "");
		return lastQihao;
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	public int getLastQihaoFromSite() {
		int lastQh = 0;
		String site = dataSite;
		String srcData = DataHttpClient.getGetResponse(site,
				EncodingConstants.UTF8_ENCODING);
		HTMLDoc doc = new HTMLDoc();
		doc.load(srcData);
		// Get the HTML
		HTMLFragment html = doc.getHTML();
		NameTagFinder tagFinder = html
				.getNameFinder(HtmlTagConstants.HTML_OPTION);
		HTMLTag tag = tagFinder.getTagBlock();
		try {
			while (tag != null) {
				String expect = tag.getTagData().toString().trim();
				String[] tmp = expect.split(SystemConstants.BLANK_STRING);
				if (tmp.length > 1
						&& tmp[1].equals(SystemConstants.CURRENT_PERIOD)) {
					if (tmp[0].matches(RegexConstants.INTEGER_REGEX)) {
						lastQh = Integer.parseInt(tmp[0]);
					}
					break;
				}
				html.removeBlock(tag);
				tag = tagFinder.getTagBlock();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return lastQh;
	}

	public int update(int qihao) {
		String site = dataSite;
		site += SystemConstants.UPDATE_PARAMETER + qihao;
		String srcData = DataHttpClient.getGetResponse(site,
				EncodingConstants.UTF8_ENCODING);
		if (srcData == null || srcData.equals(SystemConstants.EMPTY_STRING)) {
			JOptionPane.showMessageDialog(null, MessageConstants.CONNECT_ERROR);
			return 0;
		}
		if (srcData.indexOf(MessageConstants.INVALID_QIHAO_ERROR) >= 0) {
			return 1;
		}
		int valid = doHtmlAnalyzing(srcData);
		return (valid == 0 ? 0 : 2);
	}

	/**
	 * @方法说明: html源文件解析
	 * @参数: @param srcData html源文件
	 * @return int 返回期号
	 * @throws
	 */
	@Override
	public int doHtmlAnalyzing(String srcData) {
		qihao = doBjdcHtmlAnalyzing(srcData);
		return qihao;
	}

	/**
	 * @方法说明: BJDC html解析
	 * @参数: @param srcData
	 * @参数: @return
	 * @return int 返回期号
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private int doBjdcHtmlAnalyzing(String srcData) {
		BjdcDAO dao = new BjdcDAO();
		int qh = 0;
		HTMLDoc doc = new HTMLDoc();
		doc.load(srcData);
		// Get the HTML
		HTMLFragment html = doc.getHTML();
		NameTagFinder tagFinder = html
				.getNameFinder(HtmlTagConstants.HTML_OPTION);
		HTMLTag tag = tagFinder.getTagBlock();
		try {
			while (tag != null) {
				String expect = tag.toString().trim();
				if (expect.indexOf(HtmlTagConstants.HTML_SELECTED) >= 0) {
					expect = tag.getTagData().toString().trim();
					String[] tmp = expect.split(SystemConstants.BLANK_STRING);
					if (tmp[0].matches(RegexConstants.INTEGER_REGEX)) {
						qh = Integer.parseInt(tmp[0]);
					}
					break;
				}
				html.removeBlock(tag);
				tag = html.getNameFinder(HtmlTagConstants.HTML_OPTION)
						.getTagBlock();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		if (qh == 0) {
			qh = qihao;
		}
		Map<String, List<BjdcMatch>> bmMap = new HashMap<String, List<BjdcMatch>>();
		HTMLTag tbody_tag = html.getNameFinder(HtmlTagConstants.HTML_TBODY)
				.getTagBlock();
		try {
			while (tbody_tag != null) {
				String period = tbody_tag.getAttributes().getAttribute(
						HtmlTagConstants.HTML_ID);
				if (period != null && period.matches(RegexConstants.DATE_REGEX)) {
					List<BjdcMatch> match = getBetMatchFromBjdcHtmlTag(
							tbody_tag, qh, period);
					if (match != null && match.size() > 0) {
						if (SAVE_MODE != 1) {
							dao.saveOrUpdateAll(match);
							log.info("Save or update " + qh + " bjdc "
									+ match.size() + " record to database.");
						}
						bmMap.put(period, match);
					}
				}
				html.removeBlock(tbody_tag);
				NameTagFinder tag_finder = html
						.getNameFinder(HtmlTagConstants.HTML_TBODY);
				if (tag_finder == null) {
					break;
				} else {
					tbody_tag = tag_finder.getTagBlock();
				}
			}
		} catch (TagParsingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// 更新数据文件
		if (SAVE_MODE != 2) {
			boolean status = IOManager.getInstance().updateBjdcFiles(qihao,
					bmMap);
			if (!status) {
				log.error("Update " + qh + " bjdc file failed.");
				try {
					throw (new LotteryException(14));
				} catch (LotteryException e) {
					e.printStackTrace();
				}
			}
		}
		return qh;
	}

	/**
	 * @方法说明: 将对应HTMLTag数据解析为北京单场比赛投注信息
	 * @参数: @param tag HTMLTag数据
	 * @参数: @param qihao 期号
	 * @参数: @param period 投注日期
	 * @return BetMatch 比赛投注信息
	 * @throws
	 */
	private List<BjdcMatch> getBetMatchFromBjdcHtmlTag(HTMLTag tag, int qihao,
			String period) {
		List<BjdcMatch> matches = new ArrayList<BjdcMatch>();
		HTMLDoc doc = new HTMLDoc();
		doc.load(tag.toString());
		HTMLFragment html = doc.getHTML();
		HTMLTag currentTag = html.getNameFinder(HtmlTagConstants.HTML_TR)
				.getTagBlock();
		try {
			while (currentTag != null) {
				BjdcMatch match = getBetMatchFromMatchTag(currentTag, qihao,
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

	private BjdcMatch getBetMatchFromMatchTag(HTMLTag tag, int qihao,
			String period) {
		HTMLDoc doc = new HTMLDoc();
		doc.load(tag.toString());
		HTMLFragment html = doc.getHTML();
		int len = 0;
		int stdLen = 3;
		BjdcMatch match = new BjdcMatch();
		match.setType(1);
		match.setQihao(qihao + SystemConstants.EMPTY_STRING);
		match.setPeriod(period);
		match.setStatus(new MatchStatus((long) 0));
		try {
			// 处理id
			HTMLTag currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String id = currentTag.getTagData().toString().trim();
			if (id.matches(RegexConstants.INTEGER_REGEX)) {
				len = id.length();
				id = SystemFunctions.polishing(id, SystemConstants.ZERO_STRING,
						stdLen - len, 0);
				match.setMatchId(id);
			}
			// 处理competition
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_A)
					.getTagBlock();
			String competition = currentTag.getTagData().toString().trim();
			if (competition != null) {
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
			String home = currentTag.getTagData().toString().trim();
			if (home != null) {
				home = home.replaceAll(HtmlTagConstants.HTML_BLANK,
						SystemConstants.BLANK_STRING);
				match.setHome(home);
			}
			// 处理concede
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_STRONG)
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
							HtmlTagConstants.HTML_STRONG).getTagBlock();
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
			String visitor = currentTag.getTagData().toString().trim();
			if (visitor != null) {
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
			String wos = currentTag.getTagData().toString().trim();
			if (wos.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				double winOdds = Double.parseDouble(wos);
				match.setWinOdds(winOdds);
			}
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String dos = currentTag.getTagData().toString().trim();
			if (dos.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				double drawOdds = Double.parseDouble(dos);
				match.setDrawOdds(drawOdds);
			}
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String los = currentTag.getTagData().toString().trim();
			if (los.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				double lossOdds = Double.parseDouble(los);
				match.setLossOdds(lossOdds);
			}
			// 处理Rates
			html.removeBlock(currentTag);
			NameTagFinder nf = html.getNameFinder(HtmlTagConstants.HTML_SPAN);
			if (nf != null) {
				currentTag = nf.getTagBlock();
			}
			// currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
			// .getTagBlock();
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
			if (currentTag != null) {
				html.removeBlock(currentTag);
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String lrs = currentTag.getTagData().toString().trim();
			if (lrs.matches(RegexConstants.PERCENT_FLOAT_REGEX)) {
				double lossRate = SystemFunctions.percentToDouble(lrs);
				match.setLossRate(lossRate);
			}
			// 处理盘口
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String pk = SystemConstants.EMPTY_STRING;
			if (currentTag.getTagData() != null) {
				pk = currentTag.getTagData().toString().trim();
			}
			match.setPankou(pk);
			// 处理赛果、即时SP和开奖SP
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			if (currentTag != null) {
				html.removeBlock(currentTag);
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_A)
					.getTagBlock();
			if (currentTag != null) {
				String score = currentTag.getTagData().toString().trim();
				if (score.indexOf(SystemConstants.COLON) > 0) {
					String[] sco = score.split(SystemConstants.COLON);
					if (sco[0].matches(RegexConstants.INTEGER_REGEX)) {
						match.setHomeGoal(Integer.parseInt(sco[0]));
					}
					if (sco[1].matches(RegexConstants.INTEGER_REGEX)) {
						match.setVistGoal(Integer.parseInt(sco[1]));
					}
					match.setMStatus((long) 3); // 正常结束
					match.generateKjNumber();
				} else if (score.equals(SystemConstants.DELAY)) {
					match.setMStatus((long) 41); // 延期
				} else if (score.equals(SystemConstants.CUTTING)) {
					match.setMStatus((long) 42); // 腰斩
				} else {
					// TODO check match status according to the time
					match.setMStatus((long) 0); // 投注中
				}
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			MatchStatus status = match.getStatus();
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			String isSP = currentTag.toString();
			if (isSP.indexOf(HtmlTagConstants.HTML_SPAN) >= 0) {
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
						.getTagBlock();
				String jsWinSP = currentTag.getTagData().toString().trim();
				if (jsWinSP.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
					double sp = Double.parseDouble(jsWinSP);
					match.setJsWinSP(sp);
					// 正常结束
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
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			isSP = currentTag.toString();
			if (isSP.indexOf(HtmlTagConstants.HTML_SPAN) >= 0) {
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
						.getTagBlock();
				String jsDrawSP = currentTag.getTagData().toString().trim();
				if (jsDrawSP.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
					double sp = Double.parseDouble(jsDrawSP);
					match.setJsDrawSP(sp);
					// 正常结束
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
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			isSP = currentTag.toString();
			if (isSP.indexOf(HtmlTagConstants.HTML_SPAN) >= 0) {
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
						.getTagBlock();
				String jsLossSP = currentTag.getTagData().toString().trim();
				if (jsLossSP.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
					double sp = Double.parseDouble(jsLossSP);
					match.setJsLossSP(sp);
					// 正常结束
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
