/**
 * @文件名称: BetDetailHtmlPage.java
 * @类路径:   com.rb.lottery.html
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-12 下午10:14:56
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
import com.rb.lottery.domain.BjdcMatch;
import com.rb.lottery.domain.JczqMatch;
import com.rb.lottery.domain.SfcMatch;
import com.rb.lottery.client.common.EncodingConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.HtmlTagConstants;
import com.rb.lottery.client.filefilter.LtIOFileFilter;
import com.rb.lottery.client.manager.SocketManager;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;
import com.rb.lottery.user.User;

/**
 * @类功能说明: 投注详情页面
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-12 下午10:14:56
 * @版本: 1.0.0
 */

public class BetDetailHtmlPage {

	private static final Logger log = Logger.getLogger(BetDetailHtmlPage.class);

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

	public BetDetailHtmlPage() {
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

	private void genBetInfoHtmlContext(BetForm bet) {
		SocketManager som = SocketManager.getInstance();
		User user = null;
		if (SystemCache.isLogin) {
			user = SystemCache.currentUser;
		}
		this.setTitle("<title>方案详情</title>");
		head = "<head>" + meta + title + link + "</head>";
		String qihao = bet.getQihao();
		String type = null;
		long tp = bet.getLtType();
		int colnum = 0;
		if (0 == tp) {
			type = "胜负彩";
			colnum = 4;
		} else if (1 == tp) {
			type = "任选九场";
			colnum = 4;
		} else if (2 == tp) {
			type = "北京单场";
			colnum = 6;
		} else if (3 == tp) {
			type = "竞彩足球";
			colnum = 6;
		} else {
			type = "其他";
			colnum = 6;
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
		String dt = SystemEnvironment.getInstance().dateformat.format(time);
		String[] dtime = dt.split(" ");
		String money = null;
		long winflag = bet.getWinflag();
		if (-1 == winflag) {
			money = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"moy\">未开奖</span>";
		} else if (0 == winflag) {
			money = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"moy\">未中奖</span>";
		} else {
			String rf = null;
			long reflag = bet.getRewardflag();
			if (0 == reflag) {
				rf = "未派奖";
			} else {
				rf = "<font color=\"red\">已派奖</font>";
			}

			long fir = bet.getRewardBets();
			long sed = bet.getReward2Bets();
			String winbet = "<font color=\"red\">" + fir + "</font>注";
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
						+ "</font>注(&nbsp;一等奖&nbsp;" + fstr
						+ "注&nbsp;+&nbsp;二等奖&nbsp;" + sstr + "注&nbsp;)";
			}
			money = "<div class=\"zjtab\">"
					+ rf
					+ "</div><hr class=\"zjtab\" />"
					+ "<div class=\"zjtab\">中奖注数&nbsp;&nbsp;&nbsp;"
					+ winbet
					+ "</div>"
					+ "<table class=\"zjtab\"><thead><tr style=\"background-color: orange; text-align:center;\"><td>税前奖金</td><td>税后奖金</td></tr></thead>"
					+ "<tbody><tr style=\"background-color: orange; text-align:center;\"><td>"
					+ bet.getBfreward() + "元</td><td>" + bet.getAfreward()
					+ "元</td></tr></tbody></table>";
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
		String mid = bet.getMids();
		String kj = "";
		char kjnum;
		String tznum = bet.getNumbers();
		String score = "";
		String thead = null;
		String tbody = null;
		if (tp < 2) {
			thead = "<tr style=\"text-align: center; background-color: orange;\"><td class=\"cc\">场次</td>"
					+ "<td class=\"sj\">开球时间</td>"
					+ "<td class=\"dz\">对阵</td>"
					+ "<td class=\"bf\">比分</td>"
					+ "<td class=\"tz\">投注内容</td>"
					+ "<td class=\"dan\">胆</td></tr>";
			int mdCount = 0;
			String[] tzs = tznum.split(","); // 投注号码
			String[] mids = mid.split(","); // 投注场次
			tbody = "";
			List<SfcMatch> matches = som.getSfcMatchByQh(qihao);
			for (int i = 0; i < 14; i++) {
				String id = (i + 1) + "";
				if (id.length() == 1) {
					id = "0" + id;
				}
				SfcMatch match = matches.get(i);
				long matSta = match.getStatus().getSid();
				kjnum = match.getKjNumber();
				if (0 == i) {
					kj += kjnum;
				} else {
					kj += "," + kjnum;
				}
				score = "";
				if (kjnum != '*') {
					score = match.getHomeGoal() + ":" + match.getVistGoal();
				} else if (matSta > 3) {
					score = "*";
				}

				String tznr = "";
				String dan = "×";
				if (mdCount < mids.length && id.equals(mids[mdCount])) {
					int tlen = tzs[mdCount].length();
					for (int j = 0; j < tlen; j++) {
						if (tzs[mdCount].charAt(j) == kjnum || matSta > 3) {
							if (tzs[mdCount].charAt(j) == '3') {
								if (0 == j) {
									tznr += "<font color=\"red\">胜</font>";
								} else {
									tznr += ",<font color=\"red\">胜</font>";
								}
							} else if (tzs[mdCount].charAt(j) == '1') {
								if (0 == j) {
									tznr += "<font color=\"red\">平</font>";
								} else {
									tznr += ",<font color=\"red\">平</font>";
								}
							} else if (tzs[mdCount].charAt(j) == '0') {
								if (0 == j) {
									tznr += "<font color=\"red\">负</font>";
								} else {
									tznr += ",<font color=\"red\">负</font>";
								}
							} else {
								dan = "√";
							}
						} else {
							if (tzs[mdCount].charAt(j) == '3') {
								if (0 == j) {
									tznr += "胜";
								} else {
									tznr += ",胜";
								}
							} else if (tzs[mdCount].charAt(j) == '1') {
								if (0 == j) {
									tznr += "平";
								} else {
									tznr += ",平";
								}
							} else if (tzs[mdCount].charAt(j) == '0') {
								if (0 == j) {
									tznr += "负";
								} else {
									tznr += ",负";
								}
							} else {
								dan = "√";
							}
						}
					}
					mdCount++;
				} else {
					tznr = "-";
				}
				tbody += "<tr style=\"text-align: center; background-color: gray;\"><td>"
						+ id
						+ "</td>"
						+ "<td>20"
						+ qihao.substring(0, 2)
						+ "-"
						+ match.getTime()
						+ "</td>"
						+ "<td>"
						+ match.getHome()
						+ "&nbsp;&nbsp;&nbsp;VS&nbsp;&nbsp;&nbsp;"
						+ match.getVisitor()
						+ "</td>"
						+ "<td>"
						+ score
						+ "</td>"
						+ "<td>"
						+ tznr
						+ "</td>"
						+ "<td>"
						+ dan
						+ "</td></tr>";
			}

		} else {
			thead = "<tr style=\"text-align: center; background-color: orange;\"><td class=\"cc\">场次</td>"
					+ "<td class=\"sj\">开球时间</td>"
					+ "<td class=\"dz\">对阵(让球)</td>"
					+ "<td class=\"bf\">比分</td>"
					+ "<td class=\"tz\">投注内容</td>"
					+ "<td class=\"cg\">彩果</td>"
					+ "<td class=\"sp\">SP值</td>"
					+ "<td class=\"dan\">胆</td></tr>";
			if (2 == tp) {
				tbody = "";
				String[] mids = mid.split(",");
				String[] tzs = tznum.split(",");
				int mlen = mids.length;
				List<BjdcMatch> matches = som.getBjdcMatchByQhIds(qihao, mids);
				for (int i = 0; i < mlen; i++) {
					BjdcMatch match = matches.get(i);
					long matSta = match.getStatus().getSid();
					kjnum = match.getKjNumber();
					if (0 == i) {
						kj += kjnum;
					} else {
						kj += "," + kjnum;
					}
					score = "";
					if (kjnum != '*') {
						score = match.getHomeGoal() + ":" + match.getVistGoal();
					} else if (matSta > 3) {
						score = "*";
					}

					int tlen = tzs[i].length();
					String tznr = "";
					String dan = "×";
					String ret = "";
					if ('3' == kjnum) {
						ret = "胜";
					} else if ('1' == kjnum) {
						ret = "平";
					} else if ('0' == kjnum) {
						ret = "负";
					} else if (matSta > 3) {
						ret = "胜,平,负";
					}
					double bsp = match.getSP();
					String sp = "";
					long sid = match.getStatus().getSid();
					if (3 == sid) {
						if (bsp - 1.00 > 0.001) {
							sp = match.getSP() + "";
						}
					} else if (4 == sid || 41 == sid || 42 == sid) {
						sp = "1.00";
					}
					for (int j = 0; j < tlen; j++) {
						if (tzs[i].charAt(j) == kjnum || matSta > 3) {
							if (tzs[i].charAt(j) == '3') {
								if (0 == j) {
									tznr += "<font color=\"red\">胜</font>";
								} else {
									tznr += ",<font color=\"red\">胜</font>";
								}
							} else if (tzs[i].charAt(j) == '1') {
								if (0 == j) {
									tznr += "<font color=\"red\">平</font>";
								} else {
									tznr += ",<font color=\"red\">平</font>";
								}
							} else if (tzs[i].charAt(j) == '0') {
								if (0 == j) {
									tznr += "<font color=\"red\">负</font>";
								} else {
									tznr += ",<font color=\"red\">负</font>";
								}
							} else {
								dan = "√";
							}
						} else {
							if (tzs[i].charAt(j) == '3') {
								if (0 == j) {
									tznr += "胜";
								} else {
									tznr += ",胜";
								}
							} else if (tzs[i].charAt(j) == '1') {
								if (0 == j) {
									tznr += "平";
								} else {
									tznr += ",平";
								}
							} else if (tzs[i].charAt(j) == '0') {
								if (0 == j) {
									tznr += "负";
								} else {
									tznr += ",负";
								}
							} else {
								dan = "√";
							}
						}
					}
					tbody += "<tr style=\"text-align: center; background-color: gray;\"><td>"
							+ mids[i]
							+ "</td>"
							+ "<td>"
							+ SystemEnvironment.getInstance().dateformat
									.format(match.getPlayTime())
							+ "</td>"
							+ "<td>"
							+ match.getHome()
							+ "("
							+ match.getConcede()
							+ ")&nbsp;&nbsp;VS&nbsp;&nbsp;"
							+ match.getVisitor()
							+ "</td>"
							+ "<td>"
							+ score
							+ "</td>"
							+ "<td>"
							+ tznr
							+ "</td>"
							+ "<td>"
							+ ret
							+ "</td>"
							+ "<td>"
							+ sp
							+ "</td>"
							+ "<td>"
							+ dan
							+ "</td></tr>";
				}
			} else if (3 == tp) {
				tbody = "";
				String[] mids = mid.split(",");
				String[] tzs = tznum.split(",");
				int mlen = mids.length;
				List<JczqMatch> matches = som.getJczqMatchByQhIds(qihao, mids);
				for (int i = 0; i < mlen; i++) {
					JczqMatch match = matches.get(i);
					long matSta = match.getStatus().getSid();
					kjnum = match.getKjNumber();
					if (0 == i) {
						kj += kjnum;
					} else {
						kj += "," + kjnum;
					}
					score = "";
					if (kjnum != '*') {
						score = match.getHomeGoal() + ":" + match.getVistGoal();
					} else if (matSta > 3) {
						score = "*";
					}

					int tlen = tzs[i].length();
					String tznr = "";
					String dan = "×";
					String ret = "";

					if ('3' == kjnum) {
						ret = "胜";
					} else if ('1' == kjnum) {
						ret = "平";
					} else if ('0' == kjnum) {
						ret = "负";
					} else if (matSta > 3) {
						ret = "胜,平,负";
					}
					double bsp = match.getSP();
					String sp = "";
					long sid = match.getStatus().getSid();
					if (3 == sid) {
						if (bsp - 1.00 > 0.001) {
							sp = match.getSP() + "";
						}
					} else if (4 == sid || 41 == sid || 42 == sid) {
						sp = "1.00";
					}
					for (int j = 0; j < tlen; j++) {
						if (tzs[i].charAt(j) == kjnum || matSta > 3) {
							if (tzs[i].charAt(j) == '3') {
								if (0 == j) {
									tznr += "<font color=\"red\">胜</font>";
								} else {
									tznr += ",<font color=\"red\">胜</font>";
								}
							} else if (tzs[i].charAt(j) == '1') {
								if (0 == j) {
									tznr += "<font color=\"red\">平</font>";
								} else {
									tznr += ",<font color=\"red\">平</font>";
								}
							} else if (tzs[i].charAt(j) == '0') {
								if (0 == j) {
									tznr += "<font color=\"red\">负</font>";
								} else {
									tznr += ",<font color=\"red\">负</font>";
								}
							} else {
								dan = "√";
							}
						} else {
							if (tzs[i].charAt(j) == '3') {
								if (0 == j) {
									tznr += "胜";
								} else {
									tznr += ",胜";
								}
							} else if (tzs[i].charAt(j) == '1') {
								if (0 == j) {
									tznr += "平";
								} else {
									tznr += ",平";
								}
							} else if (tzs[i].charAt(j) == '0') {
								if (0 == j) {
									tznr += "负";
								} else {
									tznr += ",负";
								}
							} else {
								dan = "√";
							}
						}
					}
					tbody += "<tr style=\"text-align: center; background-color: gray;\"><td>"
							+ mids[i]
							+ "</td>"
							+ "<td>"
							+ SystemEnvironment.getInstance().dateformat
									.format(match.getPlayTime())
							+ "</td>"
							+ "<td>"
							+ match.getHome()
							+ "("
							+ match.getConcede()
							+ ")&nbsp;&nbsp;VS&nbsp;&nbsp;"
							+ match.getVisitor()
							+ "</td>"
							+ "<td>"
							+ score
							+ "</td>"
							+ "<td>"
							+ tznr
							+ "</td>"
							+ "<td>"
							+ ret
							+ "</td>"
							+ "<td>"
							+ sp
							+ "</td>"
							+ "<td>"
							+ dan
							+ "</td></tr>";
				}
			}
			String pa = bet.getPattern();
			pa = pa.substring(1, pa.length() - 1);
			tbody += "<tr><td class=\"gglx\" colspan=\""
					+ colnum
					+ "\">过关类型&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
					+ pa + "</td></tr>";
		}
		body = "<body class=\"rim\">" + "<div class=\"head\"><div><h1>"
				+ type
				+ "&nbsp;<font color=\"red\">"
				+ qihao
				+ "</font>期&nbsp;"
				+ wf
				+ "&nbsp;代购</h1></div>"
				+ "<div>发起时间："
				+ dtime[0]
				+ "&nbsp;<span class=\"smallFont\">"
				+ dtime[1]
				+ "</span>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;方案编号："
				+ bet.getBid()
				+ "</div></div>"
				+ "<div class=\"body\"><div><span class=\"boldFont\">发&nbsp;&nbsp;&nbsp;&nbsp;起&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"
				+ user.getUsername()
				+ "</div><hr />"
				+ "<div><span class=\"boldFont\">方&nbsp;案&nbsp;状&nbsp;态</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ status
				+ "</div><hr />"
				+ "<div><span class=\"boldFont\">方&nbsp;案&nbsp;金&nbsp;额</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ bet.getMoney()
				+ "元</div><hr />"
				+ "<div><span class=\"boldFont\">方&nbsp;案&nbsp;倍&nbsp;数</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ bet.getMulti()
				+ "倍</div><hr />"
				+ "<div><table class=\"zjtable\"><tr><td class=\"zjqk\"><span class=\"boldFont\">中&nbsp;奖&nbsp;情&nbsp;况</span><td><td class=\"moy\">"
				+ money
				+ "</td></tr></table></div><hr />"
				+ "<div><span class=\"boldFont\">开&nbsp;奖&nbsp;结&nbsp;果</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color=\"red\">"
				+ kj + "</font></div><hr />";
		body += "<div class=\"fanr\">"
				+ "<table class=\"dttab\"><thead><tr><td colspan=\"" + colnum
				+ "\"><span class=\"boldFont\">方案内容</span></td></tr>" + thead
				+ "</thead>" + "<tbody>" + tbody + "</tbody>";
		body += "</table></div></body>";
		html = "<html>" + head + body + "</html>";
		context = doc + html;
		return;
	}

	public void genBetDetailHtmlPage(List<BetForm> bets) {
		if (bets != null && bets.size() > 0) {
			OutputStreamWriter osw = null;
			BufferedWriter writer = null;
			for (BetForm bet : bets) {
				genBetInfoHtmlContext(bet);
				File file = new File(FilePathConstants.HTML_FILE_DIR);
				if (!file.exists()) {
					file.mkdir();
				}
				String fileDir = FilePathConstants.HTML_FILE_DIR;
				String fileName = bet.getBid() + FilePathConstants.HTML_POSTFIX;
				String theFile = fileDir + File.separator + fileName;
				file = new File(theFile);
				try {
					osw = new OutputStreamWriter(new FileOutputStream(file),
							EncodingConstants.UTF8_ENCODING);
					writer = new BufferedWriter(osw);
					writer.write(context);
					writer.flush();
					writer.close();
					log.info(fileName + " html page has been generated.");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
