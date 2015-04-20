/**
 * @文件名称: SfcWnDataUpdateThread.java
 * @类路径:   com.rb.lottery.thread
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-5 下午02:10:07
 * @版本:     1.0.0
 */
package com.rb.lottery.server.thread;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import info.informatica.doc.DocumentException;
import info.informatica.html.HTMLDoc;
import info.informatica.html.HTMLFragment;
import info.informatica.html.NameTagFinder;
import info.informatica.html.tag.HTMLTag;
import info.informatica.html.tag.TagParsingException;

import com.rb.lottery.domain.SfcKj;
import com.rb.lottery.server.common.EncodingConstants;
import com.rb.lottery.server.common.FilePathConstants;
import com.rb.lottery.server.common.HtmlTagConstants;
import com.rb.lottery.server.common.MessageConstants;
import com.rb.lottery.server.common.RegexConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.server.dao.SfcDAO;
import com.rb.lottery.server.dao.SfcKjDAO;
import com.rb.lottery.server.manager.IOManager;
import com.rb.lottery.server.http.DataHttpClient;
import com.rb.lottery.server.http.EncodeDataHttpClient;

/**
 * @类功能说明:胜负彩开奖数据更新线程
 * @类修改者:robin
 * @修改日期:2011-12-8
 * @修改说明:添加文件更新功能
 * @作者: robin
 * @创建时间: 2011-12-5 下午02:10:07
 * @版本: 1.0.0
 */

public class SfcKjDataUpdateThread extends DataUpdateThread {

	private static final Logger log = Logger
			.getLogger(SfcKjDataUpdateThread.class);
	
	private static final String SFCKJ_THREAD = "胜负彩开奖数据更新线程";

	/**
	 * @类名: SfcKjDataUpdateThread.java
	 * @描述: TODO
	 * @param group
	 * @param sfcKjSite
	 * @param sfcQh
	 */
	public SfcKjDataUpdateThread(ThreadGroup group, String site, int qihao) {
		super(group, 1, site, qihao);
		this.setName(SFCKJ_THREAD);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.lottery.thread.DataUpdateThread#updateFromSite()
	 */
	@Override
	public int updateFromSite() {
		log.info(SFCKJ_THREAD + SystemConstants.START);
		int lastKjQihao = getLastQihaoFromSite();
		String site = dataSite;
		int py = qihao / 1000;
		int ly = lastKjQihao / 1000;
		boolean nextYear = (ly > py);
		qihao--; // 由于开奖信息比投注信息晚一期
		while (qihao <= lastKjQihao) {
			site = dataSite + qihao + FilePathConstants.SHTML_POSTFIX;
			// site = dataSite + SystemConstants.UPDATE_PARAMETER + qihao;
			String srcData = null;
			EncodeDataHttpClient kdhc = new EncodeDataHttpClient();
			try {
				srcData = kdhc.doGet(site, EncodingConstants.GBK_ENCODING);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// srcData = DataHttpClient.getGetResponse(site,
			// SystemConstants.UTF8_ENCODING);
			/*
			 * PostMethod postMethod = new PostMethod(site);
			 * postMethod.getParams().setParameter(
			 * HttpMethodParams.HTTP_CONTENT_CHARSET,
			 * SystemConstants.UTF8_ENCODING); try { srcData =
			 * postMethod.getResponseBodyAsString(); } catch (IOException e) {
			 * e.printStackTrace(); }
			 */
			if (srcData == null || srcData.equals(SystemConstants.EMPTY_STRING)) {
				JOptionPane.showMessageDialog(null,
						MessageConstants.CONNECT_ERROR);
				return SystemConstants.NONE;
			}
			if (srcData.indexOf(SystemConstants.INVAILID_KJQIHAO) > 0) {
				if (nextYear) {
					qihao = ly * 1000 + 1;
				} else {
					return qihao - 1;
				}
			} else {
				doHtmlAnalyzing(srcData);
				qihao++;
			}
		}
		log.info(SFCKJ_THREAD + SystemConstants.END);
		return qihao;
	}

	/**
	 * @方法说明: 获取数据Tag
	 * @参数: @param srcData
	 * @参数: @return
	 * @return HTMLTag
	 * @throws
	 */
	private HTMLTag getDataTag(int index, String srcData) {
		HTMLDoc doc = new HTMLDoc();
		doc.load(srcData);
		// Get the HTML
		HTMLFragment html = doc.getHTML();
		NameTagFinder tagFinder = html
				.getNameFinder(HtmlTagConstants.HTML_TABLE);
		HTMLTag tag = tagFinder.getTagBlock();
		int tagIndex = 0;
		while (tag != null) {
			String expect = null;
			try {
				expect = tag.getAttributes().getAttribute(
						HtmlTagConstants.HTML_CLASS);
				if (expect != null
						&& expect
								.equalsIgnoreCase(HtmlTagConstants.HTML_KJQH_CLASS)) {
					if (tagIndex == index) {
						return tag;
					} else {
						html.removeBlock(tag);
						tag = html.getNameFinder(HtmlTagConstants.HTML_TABLE)
								.getTagBlock();
						tagIndex++;
					}

				} else {
					html.removeBlock(tag);
					tag = html.getNameFinder(HtmlTagConstants.HTML_TABLE)
							.getTagBlock();
				}
			} catch (TagParsingException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return tag;
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	private int getLastQihaoFromSite() {
		int lastQh = 0;
		String site = dataSite;
		String srcData = DataHttpClient.getGetResponse(site,
				EncodingConstants.UTF8_ENCODING);
		String expect = null;
		HTMLTag tag = getDataTag(0, srcData);
		if (tag != null) {
			try {
				expect = tag.getAttributes().getAttribute(
						HtmlTagConstants.HTML_CLASS);
				if (expect != null
						&& expect
								.equalsIgnoreCase(HtmlTagConstants.HTML_KJQH_CLASS)) {
					String src = tag.toString();
					HTMLDoc hdoc = new HTMLDoc();
					hdoc.load(src);
					// Get the HTML
					HTMLFragment nhtml = hdoc.getHTML();
					NameTagFinder ntagFinder = nhtml
							.getNameFinder(HtmlTagConstants.HTML_STRONG);
					String qh = ntagFinder.getTagBlock().getTagData()
							.toString();
					if (qh.matches(RegexConstants.INTEGER_REGEX)) {
						lastQh = Integer.parseInt(qh);
					}
				}
			} catch (TagParsingException e) {
				e.printStackTrace();
			}
		}
		return lastQh;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rb.lottery.thread.DataUpdateThread#doHtmlAnalyzing(java.
	 * lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int doHtmlAnalyzing(String srcData) {
		SfcKj sfckj = new SfcKj();
		int kjqihao = 0;
		// 开奖信息
		HTMLTag tag = getDataTag(0, srcData);
		if (tag != null) {
			String src = tag.toString();
			HTMLDoc doc = new HTMLDoc();
			doc.load(src);
			// Get the HTML
			HTMLFragment html = doc.getHTML();
			try {
				HTMLTag currentTag = html.getNameFinder(
						HtmlTagConstants.HTML_STRONG).getTagBlock();
				String kjqh = currentTag.getTagData().toString();
				if (kjqh.matches(RegexConstants.INTEGER_REGEX)) {
					kjqihao = Integer.parseInt(kjqh);
				}
				sfckj.setQihao(kjqh);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
						.getTagBlock();
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
						.getTagBlock();
				String rt = currentTag.getTagData().toString();
				String[] rta = rt.split(SystemConstants.BLANK_STRING);
				String[] kjrq = rta[0].split(SystemConstants.CN_COLON);
				if (kjrq.length > 1) {
					sfckj.setDate(kjrq[1]);
				}
				if (rta.length > 1) {
					String[] djjz = rta[1].split(SystemConstants.CN_COLON);
					if (djjz.length > 1) {
						sfckj.setDeadline(djjz[1]);
					}
				}
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TR)
						.getTagBlock();
				html.removeBlock(currentTag);
				// 获取开奖号码
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TR)
						.getTagBlock();
				HTMLDoc teamDoc = new HTMLDoc();
				teamDoc.load(currentTag.toString());
				// Get team HTML
				HTMLFragment teamHtml = teamDoc.getHTML();
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TR)
						.getTagBlock();
				HTMLDoc kjhmDoc = new HTMLDoc();
				kjhmDoc.load(currentTag.toString());
				// Get kjhm HTML
				HTMLFragment kjhmHtml = kjhmDoc.getHTML();
				html.removeBlock(currentTag);
				HTMLTag teamTag = null;
				HTMLTag kjhmTag = null;
				int tms = 14;
				while (tms > 0) {
					teamTag = teamHtml.getNameFinder(HtmlTagConstants.HTML_TD)
							.getTagBlock();
					String team = teamTag.getTagData().toString().trim();
					team = team.replaceAll(HtmlTagConstants.HTML_BLANK,
							SystemConstants.BLANK_STRING);
					team = team.replaceAll(SystemConstants.WRITE_LINE,
							SystemConstants.EMPTY_STRING);
					teamHtml.removeBlock(teamTag);
					kjhmTag = kjhmHtml
							.getNameFinder(HtmlTagConstants.HTML_SPAN)
							.getTagBlock();
					String kjhm = kjhmTag.getTagData().toString().trim();
					kjhmHtml.removeBlock(kjhmTag);
					sfckj.addKjhm(team, kjhm);
					tms--;
				}
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
						.getTagBlock();
				String sfc14Sales = currentTag.getTagData().toString();
				sfc14Sales = sfc14Sales.replaceAll(SystemConstants.COMMA,
						SystemConstants.EMPTY_STRING);
				sfc14Sales = sfc14Sales.replaceAll(SystemConstants.YUAN,
						SystemConstants.EMPTY_STRING);
				if (sfc14Sales.matches(RegexConstants.INTEGER_REGEX)) {
					sfckj.setSfc14Sales(Integer.parseInt(sfc14Sales));
				}
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
				String r9s = currentTag.getTagData().toString().trim();
				int st = r9s.indexOf(HtmlTagConstants.HTML_SEND_TAG) + 1;
				int ed = r9s.indexOf(HtmlTagConstants.HTML_EEND_TAG);
				r9s = r9s.substring(st, ed);
				r9s = r9s.replaceAll(SystemConstants.COMMA,
						SystemConstants.EMPTY_STRING);
				r9s = r9s.replaceAll(SystemConstants.YUAN,
						SystemConstants.EMPTY_STRING);
				if (r9s.matches(RegexConstants.INTEGER_REGEX)) {
					sfckj.setSfcr9Sales(Integer.parseInt(r9s));
				}
				/*
				 * String sfcr9Sales = currentTag.getTagData().toString();
				 * sfcr9Sales = sfcr9Sales.replaceAll(SystemConstants.COMMA,
				 * SystemConstants.EMPTY_STRING); sfcr9Sales =
				 * sfcr9Sales.replaceAll(SystemConstants.YUAN,
				 * SystemConstants.EMPTY_STRING); if
				 * (sfcr9Sales.matches(RegexConstants.INTEGER_REGEX)) {
				 * sfckj.setSfcr9Sales(Integer.parseInt(sfcr9Sales)); }
				 * html.removeBlock(currentTag);
				 */
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_SPAN)
						.getTagBlock();
				if (currentTag == null) {
					sfckj.setCumulation(0);
				} else {
					String cumulation = currentTag.getTagData().toString();
					cumulation = cumulation.replaceAll(SystemConstants.COMMA,
							SystemConstants.EMPTY_STRING);
					cumulation = cumulation.replaceAll(SystemConstants.YUAN,
							SystemConstants.EMPTY_STRING);
					if (cumulation.matches(RegexConstants.INTEGER_REGEX)) {
						sfckj.setCumulation(Integer.parseInt(cumulation));
					}
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		// 开奖详情
		tag = getDataTag(1, srcData);
		if (tag != null) {
			String src = tag.toString();
			HTMLDoc doc = new HTMLDoc();
			doc.load(src);
			// Get the HTML
			HTMLFragment html = doc.getHTML();
			try {
				HTMLTag currentTag = html.getNameFinder(
						HtmlTagConstants.HTML_TR).getTagBlock();
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TR)
						.getTagBlock();
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
				String firstBets = currentTag.getTagData().toString().trim();
				firstBets = firstBets.replaceAll(SystemConstants.COMMA,
						SystemConstants.EMPTY_STRING);
				firstBets = firstBets.replaceAll(SystemConstants.ZHU,
						SystemConstants.EMPTY_STRING);
				if (firstBets.matches(RegexConstants.INTEGER_REGEX)) {
					sfckj.setFirstBets(Integer.parseInt(firstBets));
				}
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
				String firstPrize = currentTag.getTagData().toString().trim();
				firstPrize = firstPrize.replaceAll(SystemConstants.COMMA,
						SystemConstants.EMPTY_STRING);
				firstPrize = firstPrize.replaceAll(SystemConstants.YUAN,
						SystemConstants.EMPTY_STRING);
				if (firstPrize.matches(RegexConstants.INTEGER_REGEX)) {
					sfckj.setFirstPrize(Integer.parseInt(firstPrize));
				}
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TR)
						.getTagBlock();
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
				if (currentTag != null) {
					String secondBets = currentTag.getTagData().toString()
							.trim();
					secondBets = secondBets.replaceAll(SystemConstants.COMMA,
							SystemConstants.EMPTY_STRING);
					secondBets = secondBets.replaceAll(SystemConstants.ZHU,
							SystemConstants.EMPTY_STRING);
					if (secondBets.matches(RegexConstants.INTEGER_REGEX)) {
						sfckj.setSecondBets(Integer.parseInt(secondBets));
					}
					html.removeBlock(currentTag);
				} else {
					boolean udf = IOManager.getInstance().updateSfcKjFile(
							kjqihao, sfckj);
					if (!udf) {
						log.error(MessageConstants.SFCKJ_UPDATE_FAIL);
					}
					return kjqihao;
				}

				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
				String secondPrize = currentTag.getTagData().toString().trim();
				secondPrize = secondPrize.replaceAll(SystemConstants.COMMA,
						SystemConstants.EMPTY_STRING);
				secondPrize = secondPrize.replaceAll(SystemConstants.YUAN,
						SystemConstants.EMPTY_STRING);
				if (secondPrize.matches(RegexConstants.INTEGER_REGEX)) {
					sfckj.setSecondPrize(Integer.parseInt(secondPrize));
				}
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TR)
						.getTagBlock();
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
				String r9Bets = currentTag.getTagData().toString().trim();
				r9Bets = r9Bets.replaceAll(SystemConstants.COMMA,
						SystemConstants.EMPTY_STRING);
				r9Bets = r9Bets.replaceAll(SystemConstants.ZHU,
						SystemConstants.EMPTY_STRING);
				if (r9Bets.matches(RegexConstants.INTEGER_REGEX)) {
					sfckj.setR9Bets(Integer.parseInt(r9Bets));
				}
				html.removeBlock(currentTag);
				currentTag = html.getNameFinder(HtmlTagConstants.HTML_TD)
						.getTagBlock();
				String r9Prize = currentTag.getTagData().toString().trim();
				r9Prize = r9Prize.replaceAll(SystemConstants.COMMA,
						SystemConstants.EMPTY_STRING);
				r9Prize = r9Prize.replaceAll(SystemConstants.YUAN,
						SystemConstants.EMPTY_STRING);
				if (r9Prize.matches(RegexConstants.INTEGER_REGEX)) {
					sfckj.setR9Prize(Integer.parseInt(r9Prize));
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		if (SAVE_MODE != 1) {
			SfcKjDAO kjdao = new SfcKjDAO();
			kjdao.saveOrUpdate(sfckj);
			SfcDAO sfcdao = new SfcDAO();
			sfcdao.updateKjNumber(sfckj);
			log.info("Save or update " + sfckj.getQihao()
					+ " sfckj record to database.");
		}
		// 更新数据文件
		if (SAVE_MODE != 2) {
			boolean udf = IOManager.getInstance().updateSfcKjFile(kjqihao,
					sfckj);
			if (!udf) {
				log.error(MessageConstants.SFCKJ_UPDATE_FAIL);
			}
		}
		return kjqihao;
	}

}
