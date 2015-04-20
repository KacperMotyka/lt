/**
 * @文件名称: BetFormHtmlPage.java
 * @类路径:   com.rb.lottery.html
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-12 下午08:28:39
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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.rb.lottery.domain.BetForm;
import com.rb.lottery.client.common.EncodingConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.HtmlTagConstants;
import com.rb.lottery.client.filefilter.LtIOFileFilter;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;

/**
 * @类功能说明: 生成投注订单信息
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-12 下午08:28:39
 * @版本: 1.0.0
 */

public class BetFormHtmlPage {

	private static final Logger log = Logger.getLogger(BetFormHtmlPage.class);

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

	public BetFormHtmlPage() {
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

	private void genBetInfoHtmlContext(List<BetForm> bets) {
		String username = "";
		if (SystemCache.isLogin) {
			username = SystemCache.currentUser.getUsername();
		}
		this.setTitle("<title>投注记录</title>");
		head = "<head>" + meta + title + link + "</head>";
		body = "<body>" + "<div>帐户：<span class=\"account\">" + username
				+ "</span></div>"
				+ "<div><span class=\"bigFont\">投注记录</span></div>"
				+ "<table class=\"bettab\">" + "<thead>" + "<tr>"
				+ "<td>彩种</td>" + "<td>期数</td>" + "<td class=\"wf\">玩法</td>"
				+ "<td class=\"sj\">投注时间</td>" + "<td>购买金额(元)</td>"
				+ "<td>中奖注数(注)</td>" + "<td>奖金(元)</td>" + "<td>状态</td>"
				+ "<td>操作</td></thead><tbody>";
		if (bets != null && bets.size() > 0) {
			for (BetForm bet : bets) {
				String type = null;
				long tp = bet.getLtType();
				if (0 == tp) {
					type = "胜负彩";
				} else if (1 == tp) {
					type = "任选九场";
				} else if (2 == tp) {
					type = "北京单场";
				} else if (3 == tp) {
					type = "竞彩足球";
				} else {
					type = "其他";
				}
				String wf = null;
				long bttype = bet.getBtType();
				if (0 == bttype) {
					wf = "复式";
				} else if (1 == bttype) {
					wf = "单式";
				} else if (20 == bttype) {
					wf = "复式上传";
				} else if (21 == bttype) {
					wf = "单式上传";
				} else {
					wf = "其他";
				}
				if (tp > 1 && 0 == bttype) {
					wf += bet.getPattern();
				}
				Date time = bet.getBtime();
				String dt = SystemEnvironment.getInstance().dateformat
						.format(time);
				String[] dtime = dt.split(" ");
				String winbet = "";
				String money = null;
				long winflag = bet.getWinflag();
				if (-1 == winflag) {
					money = "未开奖";
				} else if (0 == winflag) {
					winbet = "×";
					money = "<span class=\"gray\">未中奖</span>";
				} else {
					long fir = bet.getRewardBets();
					long sed = bet.getReward2Bets();
					winbet = "<font color=\"red\">" + fir + "</font>";

					if (0 == tp) {
						String fstr = fir + "";
						String sstr = sed + "";
						if (fir > 0) {
							fstr = "<font color=\"red\">" + fir + "</font>";
						}
						if (sed > 0) {
							sstr = "<font color=\"red\">" + sed + "</font>";
						}
						winbet = "<font color=\"red\">" + (fir + sed)
								+ "</font>&nbsp;&nbsp;(&nbsp;" + fstr
								+ "&nbsp;+&nbsp;" + sstr + "&nbsp;)";
					}
					money = "<font color=\"red\">+" + bet.getAfreward()
							+ "</font>";
				}
				String status = null;
				long sta = bet.getStatus();
				if (0 == sta) {
					status = "下单失败";
				} else if (1 == sta) {
					status = "下单成功";
				} else if (2 == sta) {
					status = "出票中";
				} else if (3 == sta) {
					status = "已出票";
				} else if (4 == sta) {
					status = "出票失败";
				} else {
					status = "其他";
				}
				body += "<tr>" + "<td class=\"boldFont\">"
						+ type
						+ "</td>"
						+ "<td>"
						+ bet.getQihao()
						+ "</td>"
						+ "<td class=\"bigWide\">"
						+ wf
						+ "</td>"
						+ "<td class=\"wide\">"
						+ dtime[0]
						+ "&nbsp;<span class=\"smallFont\">"
						+ dtime[1]
						+ "</span></td>"
						+ "<td>"
						+ bet.getMoney()
						+ "</td>"
						+ "<td>"
						+ winbet
						+ "</td>"
						+ "<td>"
						+ money
						+ "</td>"
						+ "<td>"
						+ status
						+ "</td>"
						+ "<td><a href=\""
						+ bet.getBid()
						+ ".html\"><font color=\"blue\">详情</font></a></td></tr>";
			}
		}
		body += "</tbody>" + "</table>" + "</body>";
		html = "<html>" + head + body + "</html>";
		context = doc + html;
		return;
	}

	public void genBetInfoHtmlPage(List<BetForm> bets) {
		genBetInfoHtmlContext(bets);
		File file = new File(FilePathConstants.HTML_FILE_DIR);
		if (!file.exists()) {
			file.mkdir();
		}
		String fileDir = FilePathConstants.HTML_FILE_DIR;
		String uid = "";
		if (SystemCache.isLogin) {
			uid = SystemCache.currentUser.getUserid();
		}
		String fileName = uid + FilePathConstants.HTML_POSTFIX;
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
	}
}
