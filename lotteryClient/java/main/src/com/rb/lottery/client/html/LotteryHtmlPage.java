/**
 * @文件名称: LotteryHtmlPage.java
 * @类路径:   com.rb.lottery.html
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-31 上午10:45:09
 * @版本:     1.0.0
 */
package com.rb.lottery.client.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.domain.BjdcMatch;
import com.rb.lottery.domain.JczqMatch;
import com.rb.lottery.domain.MatchStatus;
import com.rb.lottery.domain.SfcKj;
import com.rb.lottery.domain.SfcMatch;
import com.rb.lottery.client.common.EncodingConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.HtmlTagConstants;
import com.rb.lottery.client.filefilter.LtIOFileFilter;

/**
 * @类功能说明:生成投注信息页面
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-31 上午10:45:09
 * @版本: 1.0.0
 */

public class LotteryHtmlPage {

	private static final Logger log = Logger.getLogger(LotteryHtmlPage.class);

	private String doc;
	private String html;
	private String head;
	private String meta;
	private String title;
	private static List<String> css;
	private String link;
	private String body;

	private String context;

	static {
		String cssDir = FilePathConstants.CSS_FILE_DIR;
		css = new ArrayList<String>();
		File file = new File(cssDir);
		File[] cssFiles = file.listFiles(new LtIOFileFilter(
				FilePathConstants.CSS_FILE_POSTFIX));
		if (cssFiles != null && cssFiles.length > 0) {
			for (File f : cssFiles) {
				css.add(FilePathConstants.UP_DIR + File.separator + cssDir
						+ File.separator + f.getName());
			}
		}
	}

	public LotteryHtmlPage() {
		doc = HtmlTagConstants.HTML_DOC_TYPE;
		meta = HtmlTagConstants.HTML_META;
		context = doc;
		genCssLink();
		html = "";
		head = "";
		title = "";
		body = "";
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void genCssLink() {
		link = "";
		if (css != null && css.size() > 0) {
			for (String cs : css) {
				link += ("<link rel=\"stylesheet\" type=\"text/css\"  href=\""
						+ cs + "\" />");
			}
		}
	}

	/**
	 * @return doc
	 */
	public String getDoc() {
		return doc;
	}

	/**
	 * @param doc
	 *            doc
	 */
	public void setDoc(String doc) {
		this.doc = doc;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return meta
	 */
	public String getMeta() {
		return meta;
	}

	/**
	 * @param meta
	 *            meta
	 */
	public void setMeta(String meta) {
		this.meta = meta;
	}

	/**
	 * @return link
	 */
	public String getLink() {
		if (link == null) {
			genCssLink();
		}
		return link;
	}

	/**
	 * @param link
	 *            link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return head
	 */
	public String getHead() {
		return head;
	}

	/**
	 * @param head
	 *            head
	 */
	public void setHead(String head) {
		this.head = head;
	}

	/**
	 * @return body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return html
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * @param html
	 *            html
	 */
	public void setHtml(String html) {
		this.html = html;
	}

	/**
	 * @return css
	 */
	public List getCss() {
		return css;
	}

	/**
	 * @param css
	 *            css
	 */
	public void setCss(List css) {
		this.css = css;
	}

	/**
	 * @return context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param context
	 *            context
	 */
	public void setContext(String context) {
		this.context = context;
	}

	private void genLotteyHtmlContext(HtmlType type, List<BetMatch> matches) {
		/*
		String qihao = null;
		if (type == HtmlType.胜负彩投注) {
			
			this.setTitle("<title>胜负彩" + qihao + "期投注</title>");
			head = "<head>" + meta + title + link + "</head>";
			SfcDAO dao = new SfcDAO();
			List<SfcMatch> matches = dao.getSfcMatchesByQh(qihao);
			body = "<body>" + "<div>胜负彩第" + qihao
					+ "期</div>"
					+ "<table class=\"bettab\">"
					+ "<thead>"
					+ "<tr>"
					+ "<td rowspan=\"2\">比赛场次</td>"
					+ "<td rowspan=\"2\">赛事</td>"
					+ "<td rowspan=\"2\">比赛时间</td>"
					+ "<td colspan=\"2\">对阵</td>"
					+ "<td colspan=\"3\">平均赔率</td>"
					+ "<td colspan=\"3\">投注率</td>"
					+
					// "<td colspan=\"3\">投注人气</td>" +
					"<td rowspan=\"2\">比分</td>" + "<td rowspan=\"2\">彩果</td>"
					+ "</tr>" + "<tr>" + "<td>主队</td>" + "<td>客队</td>"
					+ "<td>胜</td>" + "<td>平</td>" + "<td>负</td>" + "<td>胜</td>"
					+ "<td>平</td>" + "<td>负</td>" + "</tr>" + "</thead>"
					+ "<tbody>";
			if (matches != null && matches.size() > 0) {
				for (SfcMatch match : matches) {
					body += "<tr>" + "<td>" + match.getMatchId() + "</td>"
							+ "<td>" + match.getCompetition() + "</td>"
							+ "<td>" + match.getTime() + "</td>";
					if (match.getHomeRank() != null) {
						body += "<td>" + match.getHomeRank() + match.getHome()
								+ "</td>";
					} else {
						body += "<td>" + match.getHome() + "</td>";
					}
					if (match.getVistRank() != null) {
						body += "<td>" + match.getVisitor()
								+ match.getVistRank() + "</td>";
					} else {
						body += "<td>" + match.getVisitor() + "</td>";
					}
					body += "<td>" + match.getWinOdds() + "</td>" + "<td>"
							+ match.getDrawOdds() + "</td>" + "<td>"
							+ match.getLossOdds() + "</td>" + "<td>"
							+ (match.getWinRate() * 100) + "%</td>" + "<td>"
							+ (match.getDrawRate() * 100) + "%</td>" + "<td>"
							+ (match.getLossRate() * 100) + "%</td>";
					MatchStatus status = match.getStatus();
					// 正常结束
					if (3 == status.getSid()) {
						body += "<td><strong>" + match.getHomeGoal() + "-"
								+ match.getVistGoal() + "</strong></td>"
								+ "<td><font color=\"red\">"
								+ match.getKjNumber() + "</font></td>"
								+ "</tr>";
					} else {
						body += "<td></td>" + "<td>" + status.toString()
								+ "</td>" + "</tr>";
					}
				}
			}
			body += "</tbody>" + "</table>" + "</body>";
			html = "<html>" + head + body + "</html>";
			context = doc + html;
			return;
		}
		if (type == HtmlType.胜负彩开奖) {
			if (paraLen == 0) {
				log
						.error("Can not generate SFCKJ static html page, due to Unknown Qihao.");
				return;
			}
			qihao = param[0];
			this.setTitle("<title>胜负彩" + qihao + "期开奖</title>");
			head = "<head>" + meta + title + link + "</head>";
			SfcKjDAO dao = new SfcKjDAO();
			SfcKj sfckj = dao.getSfcKjByQihao(qihao);
			body = "<body>" + "<div>胜负彩第" + qihao + "期开奖</div>";
			if (sfckj != null) {
				String[] teams = sfckj.getTeams().split("/");
				String[] kjnums = sfckj.getKjNumber().split(" ");
				body += "<table class=\"kjtab\">" + "<thead>" + "<tr>"
						+ "<td colspan=\"14\">足彩胜负(任九)第"
						+ "<strong><font color=\"red\">" + qihao
						+ "</font></strong>" + "期&nbsp;&nbsp;开奖日期:&nbsp"
						+ sfckj.getDate() + "&nbsp;&nbsp;兑奖截止日期:&nbsp;"
						+ sfckj.getDeadline() + "</td>" + "</tr>" + "</thead>"
						+ "<tbody>" + "<tr>";
				for (String team : teams) {
					body += "<td>" + team + "</td>";
				}
				body += "</tr>" + "<tr>";
				for (String kjn : kjnums) {
					body += "<td>" + kjn + "</td>";
				}
				body += "</tr>" + "<tr>"
						+ "<td colspan=\"14\">本期足彩胜负彩销量:&nbsp;"
						+ sfckj.getSfc14Sales()
						+ "&nbsp;&nbsp;本期任九销量:&nbsp;"
						+ sfckj.getSfcr9Sales()
						+ "&nbsp;&nbsp;足彩胜负奖池滚存:&nbsp;"
						+ sfckj.getCumulation()
						+ "</td>"
						+ "</tr>"
						+ "</tbody>"
						+ "</table>"
						+ "<br />"
						+ "<table>"
						+ "<thead>"
						+ "<tr>"
						+ "<td colspan=\"3\">开奖详情</td>"
						+ "</tr>"
						+ "</thead>"
						+ "<tbody>"
						+ "<tr>"
						+ "<td>奖项</td>"
						+ "<td>中奖注数</td>"
						+ "<td>单注奖金（元）</td>"
						+ "</tr><tr>"
						+ "<td>一等奖</td>"
						+ "<td><font color=\"red\">"
						+ sfckj.getFirstBets()
						+ "</font></td>"
						+ "<td>"
						+ sfckj.getFirstPrize()
						+ "</td>"
						+ "</tr><tr>"
						+ "<td>二等奖</td>"
						+ "<td><font color=\"red\">"
						+ sfckj.getSecondBets()
						+ "</font></td>"
						+ "<td>"
						+ sfckj.getSecondPrize()
						+ "</td>"
						+ "<td>任九</td>"
						+ "<td><font color=\"red\">"
						+ sfckj.getR9Bets()
						+ "</font></td>"
						+ "<td>"
						+ sfckj.getR9Prize()
						+ "</td>"
						+ "</tr></tbody></table>";
			}
			body += "</body>";
			html = "<html>" + head + body + "</html>";
			context = doc + html;
			return;
		}
		if (type == HtmlType.北京单场投注) {
			if (paraLen == 0) {
				log
						.error("Can not generate BJDC static html page, due to Unknown Qihao.");
				return;
			}
			qihao = param[0];
			this.setTitle("<title>北京单场" + qihao + "期投注</title>");
			head = "<head>" + meta + title + link + "</head>";
			BjdcDAO dao = new BjdcDAO();
			List<BjdcMatch> matches = dao.getBjdcMatchesByQh(qihao);
			body = "<body>" + "<div>北京单场第" + qihao
					+ "期</div>"
					+ "<table class=\"bettab\">"
					+ "<thead>"
					+ "<tr>"
					+ "<td rowspan=\"2\">比赛场次</td>"
					+ "<td rowspan=\"2\">赛事</td>"
					+ "<td rowspan=\"2\">比赛时间</td>"
					+ "<td colspan=\"3\">对阵</td>"
					+ "<td colspan=\"3\">平均赔率</td>"
					+ "<td colspan=\"3\">投注率</td>"
					+
					// "<td colspan=\"3\">投注人气</td>" +
					"<td rowspan=\"2\">比分</td>" + "<td rowspan=\"2\">彩果</td>"
					+ "</tr>" + "<tr>" + "<td>主队</td>" + "<td>让球</td>"
					+ "<td>客队</td>" + "<td>胜</td>" + "<td>平</td>"
					+ "<td>负</td>" + "<td>胜</td>" + "<td>平</td>" + "<td>负</td>"
					+ "</tr>" + "</thead>" + "<tbody>";
			if (matches != null && matches.size() > 0) {
				for (BjdcMatch match : matches) {
					body += "<tr>" + "<td>" + match.getMatchId() + "</td>"
							+ "<td>" + match.getCompetition() + "</td>"
							+ "<td>" + match.getTime() + "</td>" + "<td>"
							+ match.getHomeRank() + match.getHome() + "</td>"
							+ "<td>" + (match.getConcede() > 0 ? "+" : "")
							+ match.getConcede() + "</td>" + "<td>"
							+ match.getVisitor() + match.getVistRank()
							+ "</td>" + "<td>" + match.getWinOdds() + "</td>"
							+ "<td>" + match.getDrawOdds() + "</td>" + "<td>"
							+ match.getLossOdds() + "</td>" + "<td>"
							+ (match.getWinRate() * 100) + "%</td>" + "<td>"
							+ (match.getDrawRate() * 100) + "%</td>" + "<td>"
							+ (match.getLossRate() * 100) + "%</td>";
					MatchStatus status = match.getStatus();
					// 正常结束
					if (3 == status.getSid()) {
						body += "<td><strong>" + match.getHomeGoal() + "-"
								+ match.getVistGoal() + "</strong></td>"
								+ "<td><font color=\"red\">"
								+ match.getKjNumber() + "</font></td>"
								+ "</tr>";
					} else {
						body += "<td></td>" + "<td>" + status.toString()
								+ "</td>" + "</tr>";
					}
				}
			}
			body += "</tbody>" + "</table>" + "</body>";
			html = "<html>" + head + body + "</html>";
			context = doc + html;
			return;
		}
		if (type == HtmlType.竞彩足球投注) {
			if (paraLen == 0) {
				log
						.error("Can not generate JCZQ static html page, due to Unknown Qihao.");
				return;
			}
			qihao = param[0];
			this.setTitle("<title>竞彩足球" + qihao.substring(0, 4) + "-"
					+ qihao.substring(4, 6) + "-" + qihao.substring(6)
					+ "期投注</title>");
			head = "<head>" + meta + title + link + "</head>";
			JczqDAO dao = new JczqDAO();
			List<JczqMatch> matches = dao.getJczqMatchesByQh(qihao);
			body = "<body>" + "<div>竞彩足球第" + qihao.substring(0, 4) + "-"
					+ qihao.substring(4, 6) + "-" + qihao.substring(6)
					+ "期</div>"
					+ "<table class=\"bettab\">"
					+ "<thead>"
					+ "<tr>"
					+ "<td rowspan=\"2\">比赛场次</td>"
					+ "<td rowspan=\"2\">赛事</td>"
					+ "<td rowspan=\"2\">比赛时间</td>"
					+ "<td colspan=\"3\">对阵</td>"
					+ "<td colspan=\"3\">平均赔率</td>"
					+ "<td colspan=\"3\">投注率</td>"
					+
					// "<td colspan=\"3\">投注人气</td>" +
					"<td rowspan=\"2\">比分</td>" + "<td rowspan=\"2\">彩果</td>"
					+ "</tr>" + "<tr>" + "<td>主队</td>" + "<td>让球</td>"
					+ "<td>客队</td>" + "<td>胜</td>" + "<td>平</td>"
					+ "<td>负</td>" + "<td>胜</td>" + "<td>平</td>" + "<td>负</td>"
					+ "</tr>" + "</thead>" + "<tbody>";
			if (matches != null && matches.size() > 0) {
				for (JczqMatch match : matches) {
					body += "<tr>" + "<td>" + match.getMatchId() + "</td>"
							+ "<td>" + match.getCompetition() + "</td>"
							+ "<td>" + match.getTime() + "</td>" + "<td>"
							+ match.getHomeRank() + match.getHome() + "</td>"
							+ "<td>" + (match.getConcede() > 0 ? "+" : "")
							+ match.getConcede() + "</td>" + "<td>"
							+ match.getVisitor() + match.getVistRank()
							+ "</td>" + "<td>" + match.getWinOdds() + "</td>"
							+ "<td>" + match.getDrawOdds() + "</td>" + "<td>"
							+ match.getLossOdds() + "</td>" + "<td>"
							+ (match.getWinRate() * 100) + "%</td>" + "<td>"
							+ (match.getDrawRate() * 100) + "%</td>" + "<td>"
							+ (match.getLossRate() * 100) + "%</td>";
					MatchStatus status = match.getStatus();
					// 正常结束
					if (3 == status.getSid()) {
						body += "<td><strong>" + match.getHomeGoal() + "-"
								+ match.getVistGoal() + "</strong></td>"
								+ "<td><font color=\"red\">"
								+ match.getKjNumber() + "</font></td>"
								+ "</tr>";
					} else {
						body += "<td></td>" + "<td>" + status.toString()
								+ "</td>" + "</tr>";
					}
				}
			}
			body += "</tbody>" + "</table>" + "</body>";
			html = "<html>" + head + body + "</html>";
			context = doc + html;
			return;
		}
	}

	public void genLotteryHtmlPage(HtmlType type, String... param) {
		genLotteyHtmlContext(type, param);
		File file = new File(FilePathConstants.HTML_FILE_DIR);
		if (!file.exists()) {
			file.mkdir();
		}
		int paraLen = param.length;
		String qihao = null;
		if (paraLen > 0) {
			qihao = param[0];
		}
		String fileDir = FilePathConstants.HTML_FILE_DIR;
		String fileName = type.toString() + qihao
				+ FilePathConstants.HTML_POSTFIX;
		String theFile = fileDir + File.separator + fileName;
		file = new File(theFile);
		OutputStreamWriter osw = null;
		BufferedWriter writer = null;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(file),
					EncodingConstants.UTF8_ENCODING);
			writer = new BufferedWriter(osw);
			writer.write(context);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.flush();
				writer.close();
				log.info(fileName + " html page has been generated.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/
	}

}
