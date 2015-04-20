/**
 * @文件名称: SfcDataUpdateThread.java
 * @类路径:   com.rb.lottery.thread
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-5 下午02:09:13
 * @版本:     1.0.0
 */
package com.rb.lottery.server.thread;

import info.informatica.doc.DocumentException;
import info.informatica.html.HTMLDoc;
import info.informatica.html.HTMLFragment;
import info.informatica.html.tag.Attributes;
import info.informatica.html.tag.HTMLTag;
import info.informatica.html.tag.TagParsingException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.rb.lottery.domain.MatchStatus;
import com.rb.lottery.domain.SfcMatch;
import com.rb.lottery.server.common.EncodingConstants;
import com.rb.lottery.server.common.HtmlTagConstants;
import com.rb.lottery.server.common.MessageConstants;
import com.rb.lottery.server.common.RegexConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.server.common.SystemFunctions;
import com.rb.lottery.server.dao.SfcDAO;
import com.rb.lottery.server.exception.LotteryException;
import com.rb.lottery.server.manager.IOManager;
import com.rb.lottery.server.http.DataHttpClient;
import com.rb.lottery.server.http.EncodeDataHttpClient;
import com.rb.lottery.system.SysConfig;

/**
 * @类功能说明:胜负彩数据更新线程
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-5 下午02:09:13
 * @版本: 1.0.0
 */

public class SfcDataUpdateThread extends DataUpdateThread {

	private static final Logger log = Logger
			.getLogger(SfcDataUpdateThread.class);
	
	private static final String SFC_THREAD = "胜负彩数据更新线程";	

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

	/**
	 * @类名: SfcDataUpdateThread.java
	 * @描述: TODO
	 * @param group
	 * @param sfcSite
	 * @param sfcQh
	 */
	public SfcDataUpdateThread(ThreadGroup group, String site, int qihao) {
		super(group, 1, site, qihao);
		this.setName(SFC_THREAD);
	}

	/**
	 * @方法说明: 从指定网站得到数据进行解析
	 * @参数: @param dataSite
	 * @return void
	 * @throws
	 */
	@Override
	public int updateFromSite() {
		log.info(SFC_THREAD + SystemConstants.START);
		boolean useBackupSite = false;
		int qh = 0;
		// 当前最新期号
		lastQihao = getLastQihaoFromSite();
		// 网站维护或其他原因
		if (0 == lastQihao) {
			useBackupSite = true;
			dataSite = SysConfig.getInstance().getBackupSite(
					SystemConstants.SFC_SITE_LIST, 0);
			lastQihao = getLastQihaoFromBackupSite();
		}
		// 备用网站维护或其他原因
		if (0 == lastQihao) {
			log.info("胜负彩网站维护或其他原因，数据更新失败");
			qh = this.qihao;
			return qh;
		}
		int lastYear = lastQihao / 1000;
		int currYear = qihao / 1000;
		if (lastQihao - qihao > 1) {
			useBackupSite = true;
		}
		if (useBackupSite) {
			dataSite = SysConfig.getInstance().getBackupSite(
					SystemConstants.SFC_SITE_LIST, 0);
			lastYear = lastQihao / 1000;
			currYear = qihao / 1000;
			while (qihao <= lastQihao) {
				currYear = qihao / 1000;
				int ud = backupUpdate(qihao);
				if (ud == 0) {
					log.error(qihao + MessageConstants.UNKNOWN_ERROR);
					// return qihao;
				} else if (ud == 1) {
					if (currYear < lastYear) {
						SysConfig.getInstance().setLastSfcYear(currYear + 2000);
						SysConfig.getInstance().setLastSfcCount(
								qihao % 1000 - 1);
						qihao = lastYear * 1000;
					}
				}
				qihao++;
			}
		} else {
			qh = update();
			if (qh == SystemConstants.NONE) {
				return qh;
			}
		}
		log.info(SFC_THREAD + SystemConstants.END);
		// 更新系统配置
		SysConfig.getInstance().setSfcQihao(lastQihao + "");
		return qh;
	}

	/**
	 * @方法说明:
	 * @参数: @param qihao
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	private int backupUpdate(int qihao) {
		String site = dataSite;
		site += qihao;
		String srcData = null;
		try {
			EncodeDataHttpClient edhc = new EncodeDataHttpClient();
			srcData = edhc.doGet(site, EncodingConstants.GBK_ENCODING);
			if (srcData == null || srcData.equals(SystemConstants.EMPTY_STRING)) {
				JOptionPane.showMessageDialog(null,
						MessageConstants.CONNECT_ERROR);
				return SystemConstants.NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int valid = doBackupHtmlAnalyzing(srcData);
		return (valid == 0 ? 1 : valid);
	}

	/**
	 * @方法说明:
	 * @参数: @param srcData
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	private int doBackupHtmlAnalyzing(String srcData) {
		HTMLDoc doc = new HTMLDoc();
		doc.load(srcData);
		// Get the HTML
		HTMLFragment html = doc.getHTML();
		// Get a tag block
		HTMLTag form = html.getNameFinder(HtmlTagConstants.HTML_FORM)
				.getTagBlock();
		String data = SystemConstants.EMPTY_STRING;
		if (form != null) {
			data = form.toString().trim();
		}
		return doBackupSfcHtmlAnalyzing(data);
	}

	/**
	 * @方法说明:
	 * @参数: @param srcData
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private int doBackupSfcHtmlAnalyzing(String srcData) {
		int qh = qihao;
		SfcDAO dao = new SfcDAO();
		HTMLDoc doc = new HTMLDoc();
		doc.load(srcData);
		// Get the HTML
		HTMLFragment html = doc.getHTML();
		List<SfcMatch> bmList = new ArrayList<SfcMatch>();
		HTMLTag tr_tag = html.getNameFinder(HtmlTagConstants.HTML_TR)
				.getTagBlock();
		int count = 0;
		while (tr_tag != null) {
			try {
				String bg = tr_tag.getAttributes().getAttribute("bgcolor");
				if (bg != null && bg.equalsIgnoreCase("#ffffff")) {
					break;
				}
				count++;
				if (count < 15) {
					SfcMatch match = getBetMatchFromBackupSfcHtmlTag(tr_tag,
							qihao);
					if (match != null) {
						if (SAVE_MODE != 1) {
							dao.saveOrUpdate(match);
							log.info("Save or update " + match.getQihao() + "-"
									+ match.getMatchId() + " sfc to database.");
						}
						bmList.add(match);
					}
				}
				html.removeBlock(tr_tag);
				tr_tag = html.getNameFinder(HtmlTagConstants.HTML_TR)
						.getTagBlock();
			} catch (TagParsingException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		if (count < 14) {
			qh = 0;
			return qh;
		}
		// 更新数据文件
		if (SAVE_MODE != 2) {
			boolean status = IOManager.getInstance()
					.updateBackupSfcFile(bmList);
			if (!status) {
				log.error("Update " + qihao + " sfc file failed.");
				try {
					throw (new LotteryException(13));
				} catch (LotteryException e) {
					e.printStackTrace();
				}
			}
		}
		return qh;
	}

	/**
	 * @方法说明:
	 * @参数: @param trTag
	 * @参数: @param qihao
	 * @参数: @return
	 * @return BetMatch
	 * @throws
	 */
	private SfcMatch getBetMatchFromBackupSfcHtmlTag(HTMLTag tag, int qihao) {
		SfcMatch match = new SfcMatch();
		match.setType(0);
		match.setQihao(qihao + SystemConstants.EMPTY_STRING);
		match.setConcede(SystemConstants.NONE);
		match.setStatus(new MatchStatus((long) 0)); // unchecked
		int len = 0;
		int stdLen = 2;
		try {
			HTMLDoc doc = new HTMLDoc();
			doc.load(tag.toString());
			HTMLFragment html = doc.getHTML();
			HTMLTag currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			// 处理id
			String id = currentTag.getTagData().toString().trim();
			if (id.matches(RegexConstants.INTEGER_REGEX)) {
				len = id.length();
				id = SystemFunctions.polishing(id, SystemConstants.ZERO_STRING,
						stdLen - len, 0);
				match.setMatchId(id);
			}
			// 处理homeTeam
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_A)
					.getTagBlock();
			if (currentTag != null) {
				String homeT = currentTag.getTagData().toString().trim();
				match.setHome(homeT);
				html.removeBlock(currentTag);
			}
			// 处理winOdds
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String winO = currentTag.getTagData().toString().trim();
			winO = winO.replace(SystemConstants.UP,
					SystemConstants.EMPTY_STRING);
			winO = winO.replace(SystemConstants.ZOU,
					SystemConstants.EMPTY_STRING);
			winO = winO.replace(SystemConstants.DOWN,
					SystemConstants.EMPTY_STRING);
			winO = winO.replace(SystemConstants.LEFT_BRACKET_STRING,
					SystemConstants.EMPTY_STRING);
			winO = winO.replace(SystemConstants.RIGHT_BRACKET_STRING,
					SystemConstants.EMPTY_STRING);
			if (winO.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				match.setWinOdds(Double.parseDouble(winO));
			}
			html.removeBlock(currentTag);
			// 处理drawOdds
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String drawO = currentTag.getTagData().toString().trim();
			drawO = drawO.replace(SystemConstants.UP,
					SystemConstants.EMPTY_STRING);
			drawO = drawO.replace(SystemConstants.ZOU,
					SystemConstants.EMPTY_STRING);
			drawO = drawO.replace(SystemConstants.DOWN,
					SystemConstants.EMPTY_STRING);
			drawO = drawO.replace(SystemConstants.LEFT_BRACKET_STRING,
					SystemConstants.EMPTY_STRING);
			drawO = drawO.replace(SystemConstants.RIGHT_BRACKET_STRING,
					SystemConstants.EMPTY_STRING);
			if (drawO.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				match.setDrawOdds(Double.parseDouble(drawO));
			}
			html.removeBlock(currentTag);
			// 处理lossOdds
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String lossO = currentTag.getTagData().toString().trim();
			lossO = lossO.replace(SystemConstants.UP,
					SystemConstants.EMPTY_STRING);
			lossO = lossO.replace(SystemConstants.ZOU,
					SystemConstants.EMPTY_STRING);
			lossO = lossO.replace(SystemConstants.DOWN,
					SystemConstants.EMPTY_STRING);
			lossO = lossO.replace(SystemConstants.LEFT_BRACKET_STRING,
					SystemConstants.EMPTY_STRING);
			lossO = lossO.replace(SystemConstants.RIGHT_BRACKET_STRING,
					SystemConstants.EMPTY_STRING);
			if (lossO.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				match.setLossOdds(Double.parseDouble(lossO));
			}
			// 处理score
			String score = tag.toString();
			int ldex = score.indexOf(HtmlTagConstants.HTML_VIST_TAG) + 7;
			score = score.substring(ldex);
			ldex = score.indexOf(HtmlTagConstants.HTML_VIST_TAG) + 7;
			score = score.substring(ldex);
			int edex = score.indexOf(HtmlTagConstants.HTML_BR1);
			score = score.substring(0, edex);
			String[] gls = score.split(RegexConstants.COMMON);
			if (gls[0].indexOf(SystemConstants.PART) < 0) {
				if (gls[0].matches(RegexConstants.INTEGER_REGEX)) {
					match.setHomeGoal(Integer.parseInt(gls[0]));
				}
				if (gls[1].matches(RegexConstants.INTEGER_REGEX)) {
					match.setVistGoal(Integer.parseInt(gls[1]));
				}
				match.setMStatus((long) 3);
				match.generateKjNumber();
			}
			// 处理visitorTeam
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			String visitorT = currentTag.getTagData().toString().trim();
			if (visitorT != null) {
				int vidx = visitorT.indexOf(HtmlTagConstants.HTML_VIST_TAG) + 7;
				visitorT = visitorT.substring(vidx);
				int vedx = visitorT.indexOf(HtmlTagConstants.HTML_ENDA_TAG);
				visitorT = visitorT.substring(0, vedx);
				match.setVisitor(visitorT);
			}
			// 处理competition和time
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			String tydt = currentTag.getTagData().toString().trim();
			String[] tsd = tydt.split(HtmlTagConstants.HTML_BR1);
			match.setCompetition(tsd[0].trim());
			match.setTime(tsd[1].trim());
			// 处理popularity
			for (int i = 0; i < 3; i++) {
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
			}
			String winRQ = currentTag.getTagData().toString().trim();
			int wdx = winRQ.indexOf(HtmlTagConstants.HTML_BR1) + 6;
			winRQ = winRQ.substring(wdx);
			if (winRQ.matches(RegexConstants.INTEGER_REGEX)) {
				match.setWinRQ(Integer.parseInt(winRQ));
			}
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			String drawRQ = currentTag.getTagData().toString().trim();
			int ddx = drawRQ.indexOf(HtmlTagConstants.HTML_BR1) + 6;
			drawRQ = drawRQ.substring(ddx);
			if (drawRQ.matches(RegexConstants.INTEGER_REGEX)) {
				match.setDrawRQ(Integer.parseInt(drawRQ));
			}
			html.removeBlock(currentTag);
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			String lossRQ = currentTag.getTagData().toString().trim();
			int ldx = lossRQ.indexOf(HtmlTagConstants.HTML_BR1) + 6;
			lossRQ = lossRQ.substring(ldx);
			if (lossRQ.matches(RegexConstants.INTEGER_REGEX)) {
				match.setLossRQ(Integer.parseInt(lossRQ));
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return match;
	}

	/**
	 * @方法说明:获取当前期
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	private int getLastQihaoFromSite() {
		int lastQh = 0;
		String site = dataSite;
		try {
			HTMLDoc doc = new HTMLDoc();
			EncodeDataHttpClient edhc = new EncodeDataHttpClient();
			String srcData = edhc.doGet(site, EncodingConstants.GBK_ENCODING);
			doc.load(srcData);
			// Get the HTML
			HTMLFragment html = doc.getHTML();
			// Get a tag block with id="expect_tab"
			HTMLTag expect_tag = html.getIdFinder(
					HtmlTagConstants.HTML_SFC_EXPECT).getTagBlock();
			// 网站维护或其他原因
			if (expect_tag == null) {
				return lastQh;
			}
			doc.load(expect_tag.toString());
			html = doc.getHTML();
			expect_tag = html.getNameFinder(HtmlTagConstants.HTML_A)
					.getTagBlock();
			// Get Attributes
			Attributes attrs = expect_tag.getAttributes();
			String expect = attrs
					.getAttribute(HtmlTagConstants.HTML_SFC_EXPECT_VALUE);
			if (expect.matches(RegexConstants.INTEGER_REGEX)) {
				lastQh = Integer.parseInt(expect);
			}
		} catch (TagParsingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastQh;
	}

	/**
	 * @方法说明:获取当前期
	 * @参数: @return
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unused")
	private int getLastQihaoFromBackupSite() {
		int lastQh = 0;
		String site = dataSite;
		try {
			EncodeDataHttpClient edhc = new EncodeDataHttpClient();
			String srcData = edhc.doGet(site, EncodingConstants.GBK_ENCODING);
			HTMLDoc doc = new HTMLDoc();
			doc.load(srcData);
			// Get the HTML
			HTMLFragment html = doc.getHTML();
			HTMLTag tag = html.getNameFinder(HtmlTagConstants.HTML_OPTION)
					.getTagBlock();

			while (tag != null) {
				Attributes attrs = tag.getAttributes();
				if (attrs != null) {
					String expect = attrs
							.getAttribute(HtmlTagConstants.HTML_SELECTED);
					if (expect != null
							&& expect.equals(HtmlTagConstants.HTML_SELECTED)) {
						String data = tag.getTagData().toString().trim();
						data = data.replaceAll(HtmlTagConstants.HTML_BLANK,
								SystemConstants.EMPTY_STRING);
						data = data.replace(SystemConstants.CURRENT_SFC,
								SystemConstants.EMPTY_STRING);
						if (data.matches(RegexConstants.INTEGER_REGEX)) {
							lastQh = Integer.parseInt(data);
						}
						break;
					}
				}
				html.removeBlock(tag);
				tag = html.getNameFinder(HtmlTagConstants.HTML_OPTION)
						.getTagBlock();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastQh;
	}

	private int update() {
		String site = dataSite;
		site += SystemConstants.UPDATE_PARAMETER + qihao;
		String srcData = DataHttpClient.getGetResponse(site,
				EncodingConstants.UTF8_ENCODING);
		if (srcData == null || srcData.equals(SystemConstants.EMPTY_STRING)) {
			JOptionPane.showMessageDialog(null, MessageConstants.CONNECT_ERROR);
			return SystemConstants.NONE;
		}
		if (srcData.indexOf(SystemConstants.INVAILID_QIHAO) >= 0) {
			qihao++;
			update();
		} else {
			/*
			 * int qh = doHtmlAnalyzing(srcData); if (type == 0) { qh = qihao; }
			 * return qh;
			 */
			qihao = doHtmlAnalyzing(srcData);
		}
		return qihao;
	}

	/**
	 * @方法说明: html源文件解析
	 * @参数: @param type 投注类型 0-SFC 1-BJDC 2-JCZQ
	 * @参数: @param srcData html源文件
	 * @return int 返回期号
	 * @throws
	 */
	@Override
	public int doHtmlAnalyzing(String srcData) {
		qihao = doSfcHtmlAnalyzing(srcData);
		return qihao;
	}

	/**
	 * @方法说明: SFC html解析
	 * @参数: @param srcData
	 * @参数: @return
	 * @return int 返回期号
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private int doSfcHtmlAnalyzing(String srcData) {
		SfcDAO dao = new SfcDAO();
		HTMLDoc doc = new HTMLDoc();
		doc.load(srcData);
		// Get the HTML
		HTMLFragment html = doc.getHTML();
		// Get a tag block with id="expect_tab"
		HTMLTag expect_tag = html.getIdFinder(HtmlTagConstants.HTML_SFC_EXPECT)
				.getTagBlock();
		doc.load(expect_tag.toString());
		html = doc.getHTML();
		expect_tag = html.getNameFinder(HtmlTagConstants.HTML_A).getTagBlock();
		// Get Attributes
		Attributes attrs = expect_tag.getAttributes();
		int qihao = 0;
		try {
			String expect = attrs
					.getAttribute(HtmlTagConstants.HTML_SFC_EXPECT_VALUE);
			if (expect.matches(RegexConstants.INTEGER_REGEX)) {
				qihao = Integer.parseInt(expect);
			}
		} catch (TagParsingException e) {
			e.printStackTrace();
		}
		doc.load(srcData);
		html = doc.getHTML();
		List<SfcMatch> bmList = new ArrayList<SfcMatch>();
		HTMLTag tr_tag = html.getNameFinder(HtmlTagConstants.HTML_TR)
				.getTagBlock();
		while (tr_tag != null) {
			try {
				String vs = tr_tag.getAttributes().getAttribute(
						HtmlTagConstants.HTML_VS);
				if (vs != null) {
					SfcMatch match = getBetMatchFromSfcHtmlTag(tr_tag, qihao);
					if (match != null) {
						if (SAVE_MODE != 1) {
							dao.saveOrUpdate(match);
							log.info("Save or update " + match.getQihao() + "-"
									+ match.getMatchId() + " sfc to database.");
						}
						bmList.add(match);
					}
				}
				html.removeBlock(tr_tag);
				tr_tag = html.getNameFinder(HtmlTagConstants.HTML_TR)
						.getTagBlock();
			} catch (TagParsingException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		// 更新数据文件
		if (SAVE_MODE != 2) {
			boolean status = IOManager.getInstance().updateSfcFile(bmList);
			if (!status) {
				log.error("Update " + qihao + " sfc file failed.");
				try {
					throw (new LotteryException(13));
				} catch (LotteryException e) {
					e.printStackTrace();
				}
			}
		}
		return qihao;
	}

	/**
	 * @方法说明: 将对应HTMLTag数据解析为胜负彩比赛投注信息
	 * @参数: @param tag HTMLTag数据
	 * @参数: @param qihao 期号
	 * @return BetMatch 比赛投注信息
	 * @throws
	 */
	private SfcMatch getBetMatchFromSfcHtmlTag(HTMLTag tag, int qihao) {
		SfcMatch match = new SfcMatch();
		match.setType(0);
		match.setQihao(qihao + SystemConstants.EMPTY_STRING);
		match.setConcede(SystemConstants.NONE);
		match.setStatus(new MatchStatus((long) 0)); // unchecked
		int len = 0;
		int stdLen = 2;
		try {
			HTMLDoc doc = new HTMLDoc();
			doc.load(tag.toString());
			HTMLFragment html = doc.getHTML();
			HTMLTag currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
					.getTagBlock();
			// 处理id
			String id = currentTag.getTagData().toString().trim();
			if (id.matches(RegexConstants.INTEGER_REGEX)) {
				len = id.length();
				id = SystemFunctions.polishing(id, SystemConstants.ZERO_STRING,
						stdLen - len, 0);
				match.setMatchId(id);
			}
			// 处理competition

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
				String homeRank = SystemConstants.EMPTY_STRING;
				if (currentTag.getTagData() != null) {
					homeRank = currentTag.getTagData().toString().trim();
				}
				match.setHomeRank(homeRank);
				html.removeBlock(currentTag);
			}

			currentTag = html.getNameFinder(HtmlTagConstants.HTML_A)
					.getTagBlock();
			if (currentTag != null) {
				String home = currentTag.getTagData().toString().trim();
				home = home.replaceAll(HtmlTagConstants.HTML_BLANK,
						SystemConstants.BLANK_STRING);
				match.setHome(home);
			}
			for (int i = 0; i < 2; i++) {
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
						.getTagBlock();
				if (currentTag != null) {
					html.removeBlock(currentTag);
				}
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_A)
					.getTagBlock();
			if (currentTag != null) {
				String visitor = currentTag.getTagData().toString().trim();
				visitor = visitor.replaceAll(HtmlTagConstants.HTML_BLANK,
						SystemConstants.BLANK_STRING);
				match.setVisitor(visitor);
			}

			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			if (currentTag != null) {
				html.removeBlock(currentTag);
			}

			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			if (currentTag != null) {
				String vistRank = SystemConstants.EMPTY_STRING;
				if (currentTag.getTagData() != null) {
					vistRank = currentTag.getTagData().toString().trim();
				}
				match.setVistRank(vistRank);
			}
			// 处理Odds
			for (int i = 0; i < 2; i++) {
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
				if (currentTag != null) {
					html.removeBlock(currentTag);
				}
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String wos = currentTag.getTagData().toString().trim();
			if (wos.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				double winOdds = Double.parseDouble(wos);
				match.setWinOdds(winOdds);
			}
			try {
				html.removeBlock(currentTag);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String dos = currentTag.getTagData().toString().trim();
			if (dos.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				double drawOdds = Double.parseDouble(dos);
				match.setDrawOdds(drawOdds);
			}
			try {
				html.removeBlock(currentTag);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String los = currentTag.getTagData().toString().trim();
			if (los.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
				double lossOdds = Double.parseDouble(los);
				match.setLossOdds(lossOdds);
			}
			// 处理Rates
			for (int i = 0; i < 5; i++) {
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
				try {
					if (currentTag != null) {
						String cls = currentTag.getAttributes().getAttribute(
								HtmlTagConstants.HTML_CLASS);
						if (cls != null
								&& cls
										.equalsIgnoreCase(HtmlTagConstants.HTML_LAST_TD)) {
							continue;
						}
					}
					html.removeBlock(currentTag);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String wrs = currentTag.getTagData().toString().trim();
			if (wrs.matches(RegexConstants.PERCENT_FLOAT_REGEX)) {
				double winRate = SystemFunctions.percentToDouble(wrs);
				match.setWinRate(winRate);
			}
			try {
				html.removeBlock(currentTag);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String drs = currentTag.getTagData().toString().trim();
			if (drs.matches(RegexConstants.PERCENT_FLOAT_REGEX)) {
				double drawRate = SystemFunctions.percentToDouble(drs);
				match.setDrawRate(drawRate);
			}
			try {
				html.removeBlock(currentTag);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
					.getTagBlock();
			String lrs = currentTag.getTagData().toString().trim();
			if (lrs.matches(RegexConstants.PERCENT_FLOAT_REGEX)) {
				double lossRate = SystemFunctions.percentToDouble(lrs);
				match.setLossRate(lossRate);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return match;
	}
}
