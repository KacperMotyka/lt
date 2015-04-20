/**
 * @文件名称: IOManager.java
 * @类路径:   com.rb.lottery.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-9-30 上午09:36:48
 * @版本:     1.0.0
 */
package com.rb.lottery.client.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.rb.lottery.client.UI.main.MainFrame;
import com.rb.lottery.client.common.EncodingConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.RegexConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.common.SystemFunctions;
import com.rb.lottery.client.exception.LotteryException;
import com.rb.lottery.domain.BetBasket;
import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.domain.BjdcLottery;
import com.rb.lottery.domain.BjdcMatch;
import com.rb.lottery.domain.JczqLottery;
import com.rb.lottery.domain.JczqMatch;
import com.rb.lottery.domain.Lottery;
import com.rb.lottery.domain.MatchStatus;
import com.rb.lottery.domain.Sfc14Lottery;
import com.rb.lottery.domain.SfcKj;
import com.rb.lottery.domain.SfcR9Lottery;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemProcessor;
import com.rb.lottery.system.UserConfig;

/**
 * @类功能说明: IO操作，文件读写、字符流操作
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-9-30 上午09:36:48
 * @版本: 1.0.0
 */

public class IOManager {

	private static final Logger log = Logger.getLogger(IOManager.class);

	public File infile;
	public File outfile;

	private static IOManager manager = null;

	private IOManager() {
	}

	public static IOManager getInstance() {
		if (manager == null) {
			manager = new IOManager();
		}
		return manager;
	}

	/**
	 * @return infile
	 */
	public File getInfile() {
		return infile;
	}

	/**
	 * @param infile
	 *            infile
	 */
	public void setInfile(File infile) {
		this.infile = infile;
	}

	public void setInfile(String path) {
		File file = new File(path);
		this.infile = file;
	}

	/**
	 * @return outfile
	 */
	public File getOutfile() {
		return outfile;
	}

	/**
	 * @param outfile
	 *            outfile
	 */
	public void setOutfile(File outfile) {
		this.outfile = outfile;
	}

	public void setOutfile(String path) {
		File file = new File(path);
		this.outfile = file;
	}

	/**
	 * @方法说明: 打印文件
	 * @参数: @param srcFile
	 * @return void
	 * @throws
	 */
	public void printFile(File srcFile) {
		log.info("打印文件: " + srcFile.getName());
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(srcFile),
					EncodingConstants.UTF8_ENCODING));
			String line = SystemConstants.EMPTY_STRING;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			log.error(SystemConstants.FILE + srcFile.getName()
					+ SystemConstants.READ + SystemConstants.FAIL);
			e.printStackTrace();
		} catch (Exception e) {
			log.error(SystemConstants.FILE + srcFile.getName()
					+ SystemConstants.READ + SystemConstants.FAIL);
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 文件拷贝
	 * @参数: @param betFile
	 * @参数: @param resultFile
	 * @return void
	 * @throws
	 */
	public void FileCopy(File srcFile, File destFile) {
		log.info("从文件【" + srcFile.getName() + "】拷贝到文件【" + destFile.getName()
				+ "】");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(srcFile),
					EncodingConstants.GBK_ENCODING));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(destFile),
					EncodingConstants.GBK_ENCODING));
			String line = SystemConstants.EMPTY_STRING;
			while ((line = reader.readLine()) != null) {
				writer.write(line + SystemConstants.WRITE_LINE);
			}
			reader.close();
			writer.close();
		} catch (IOException e) {
			log.error(SystemConstants.FILE + srcFile.getName()
					+ SystemConstants.COPY + SystemConstants.FAIL);
			e.printStackTrace();
		} catch (Exception e) {
			log.error(SystemConstants.FILE + srcFile.getName()
					+ SystemConstants.COPY + SystemConstants.FAIL);
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 读取用户配置文件
	 * @参数: @param userConFile
	 * @return void
	 * @throws
	 */
	public void readUserConfigFile(File userConFile) {
		log.info("读取用户配置文件");
		this.setInfile(userConFile);
		int len = 0;
		int type = 0;
		InputStreamReader isreader = null;
		BufferedReader reader = null;
		try {
			isreader = new InputStreamReader(new FileInputStream(infile),
					EncodingConstants.GBK_ENCODING);
			reader = new BufferedReader(isreader);
			String line = SystemConstants.EMPTY_STRING;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.equals(SystemConstants.EMPTY_STRING)
						|| line.charAt(0) == SystemConstants.LEFT_BRACKET) {
					continue;
				}
				len = line.length();
				int eqlIdx = line.indexOf(SystemConstants.EQUAL);
				String key = line.substring(0, eqlIdx).trim();
				String value = SystemConstants.EMPTY_STRING;
				if (len != eqlIdx + 1) {
					value = line.substring(eqlIdx + 1).trim();
				}
				if (key.equals(SystemConstants.BET_TYPE)) {
					type = SystemFunctions.convertByTypeName(value);
					if (type < 0) {
						throw (new LotteryException(12));
					} else {
						UserConfig.getInstance().setTzlx(type);
					}
					continue;
				}
				if (key.equals(SystemConstants.QIHAO)) {
					if (type < 3
							&& !value.matches(RegexConstants.INTEGER_REGEX)) {
						throw (new LotteryException(12));
					} else {
						UserConfig.getInstance().setQihao(value);
					}
					continue;
				}
				if (key.equals(SystemConstants.BETCODE)) {
					if (type < 0 || !SystemFunctions.isBetLegal(type, value)) {
						throw (new LotteryException(12));
					} else {
						UserConfig.getInstance().setBet(value);
					}
					continue;
				}
				// TODO get other parameter
			}
			reader.close();
			isreader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (LotteryException e) {
			e.printStackTrace();
			return;
		} finally {
			// do nothing
		}
	}

	@SuppressWarnings("unchecked")
	public LotteryException getLotteryException(int code) {
		// 输入流对象
		int iCode = 0;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(infile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			iCode = 10;
		}
		// jdom解析器
		SAXBuilder sab = new SAXBuilder();
		Document doc = null;
		try {
			doc = sab.build(fis);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 获得XML的根元素
		Element exceptions = doc.getRootElement();
		// 获得根元素下的所有子元素
		List<Element> exceptionlist = exceptions
				.getChildren(SystemConstants.EXCEPTION);

		LotteryException expt = new LotteryException();
		for (Element exception : exceptionlist) {
			String theCode = (String) exception
					.getChildTextTrim(SystemConstants.CODE);
			theCode = theCode.trim();

			try {
				iCode = Integer.parseInt(theCode);
				if (iCode == code) {
					String msg = (String) exception
							.getChildTextTrim(SystemConstants.MESSAGE);
					expt.setCode(code);
					expt.setMessage(msg);
					return expt;
				}
			} catch (Exception e) {
				iCode = 11;
			}
		}
		expt.setCode(-1);
		expt.setMessage(MessageConstants.UNKNOWN_ERROR);
		return expt;
	}

	/**
	 * @方法说明: 新建
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void newProject() {
		log.info("新建投注方案");
		SystemProcessor.initLottery();
		MainFrame.getInstance().initProject();
	}

	/**
	 * @param fileName
	 * @方法说明: 保存投注方案
	 * @参数:
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void doSavePrj(String fileName) {
		log.info("保存投注方案到" + fileName);
		File file = new File(fileName);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			Lottery lt = SystemProcessor
					.getLottery(SystemCache.currentLotteryId);
			String line = SystemConstants.LOTTERY_ID + SystemConstants.EQUAL
					+ lt.getLotteryId() + SystemConstants.WRITE_LINE;
			writer.write(line);
			line = SystemConstants.LABEL_BETTYPE + SystemConstants.EQUAL
					+ SystemConstants.BET_TYPES[lt.getType()]
					+ SystemConstants.WRITE_LINE;
			writer.write(line);
			line = SystemConstants.QIHAO + SystemConstants.EQUAL
					+ lt.getQihao() + SystemConstants.WRITE_LINE;
			writer.write(line);
			line = SystemConstants.PASSWAY + SystemConstants.EQUAL
					+ lt.getPassway().toString() + SystemConstants.WRITE_LINE;
			writer.write(line);
			line = SystemConstants.SINGLE_SELECT + SystemConstants.SLASH
					+ SystemConstants.DOUBLE_SELECT + SystemConstants.SLASH
					+ SystemConstants.TRIBLE_SELECT + SystemConstants.SLASH
					+ SystemConstants.DAN + SystemConstants.EQUAL
					+ lt.getSelectsString() + SystemConstants.WRITE_LINE;
			writer.write(line);
			line = SystemConstants.SELECT_MATCH + SystemConstants.EQUAL
					+ SystemConstants.WRITE_LINE;
			writer.write(line);
			Map<String, String> betMatches = lt.getMulMatches();
			Set keySet = betMatches.keySet();
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = betMatches.get(key);
				line = key + SystemConstants.EQUAL + value
						+ SystemConstants.WRITE_LINE;
				writer.write(line);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明:打开投注方案
	 * @参数: @param fileName
	 * @return void
	 * @throws
	 */
	public void doOpenPrj(String fileName) {
		log.info("打开投注方案:  " + fileName);
		File file = new File(fileName);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), EncodingConstants.UTF8_ENCODING));
			Lottery lottery = null;
			String lotteryId = SystemConstants.EMPTY_STRING;
			String line = SystemConstants.EMPTY_STRING;
			int type = -1;
			String qihao = SystemConstants.EMPTY_STRING;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.equals(SystemConstants.EMPTY_STRING)) {
					continue;
				}
				String[] tmp = line.split(SystemConstants.EQUAL);
				if (tmp.length > 1) {
					String sdt = SystemConstants.SINGLE_SELECT
							+ SystemConstants.SLASH
							+ SystemConstants.DOUBLE_SELECT
							+ SystemConstants.SLASH
							+ SystemConstants.TRIBLE_SELECT
							+ SystemConstants.SLASH + SystemConstants.DAN;
					tmp[0] = tmp[0].trim();
					tmp[1] = tmp[1].trim();
					if (tmp[0].equals(SystemConstants.LABEL_BETTYPE)) {
						type = SystemFunctions.convertByTypeName(tmp[1]);
						if (type != SystemCache.currentType) {
							JOptionPane.showMessageDialog(null,
									MessageConstants.NOT_CURRNET_TYPE);
							return;
						}
						switch (type) {
						case 0:
							lottery = new Sfc14Lottery();
							break;
						case 1:
							lottery = new SfcR9Lottery();
							break;
						case 2:
							lottery = new BjdcLottery();
							break;
						case 3:
							lottery = new JczqLottery();
						}
						lottery.setLotteryId(lotteryId);
					} else if (tmp[0].equals(SystemConstants.QIHAO)) {
						qihao = tmp[1];
						if (!qihao.equals(SystemCache.currentQihao)) {
							JOptionPane.showMessageDialog(null,
									MessageConstants.NOT_CURRNET_QIHAO);
							return;
						}
						lottery.setQihao(qihao);
					} else if (tmp[0].equals(SystemConstants.LOTTERY_ID)) {
						lotteryId = tmp[1];
					} else if (tmp[0].equals(SystemConstants.PASSWAY)) {
						tmp[1] = tmp[1].substring(1, tmp[1].length() - 1);
						String[] psws = tmp[1].split(SystemConstants.COMMA);
						List<String> passway = new ArrayList<String>();
						for (String psw : psws) {
							passway.add(psw.trim());
						}
						lottery.setPassway(passway);
					} else if (tmp[0].equals(sdt)) {
						lottery.setSelects(tmp[1]);
					}
				} else {
					tmp[0] = tmp[0].trim();
					if (tmp[0].equals(SystemConstants.SELECT_MATCH)) {
						while ((line = reader.readLine()) != null) {
							line = line.trim();
							if (line.equals(SystemConstants.EMPTY_STRING)) {
								continue;
							}
							String[] match = line.split(SystemConstants.EQUAL);
							lottery.addMulMatch(match[0].trim(), match[1]
									.trim());
						}
					}
				}
			}
			reader.close();
			SystemProcessor.initLottery(lottery);
			MainFrame.getInstance().initProject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 保存投注结果
	 * @参数: @param fileName
	 * @return void
	 * @throws
	 */
	public void doSaveRet(String fileName) {
		log.info("保存投注结果到" + fileName);
		File file = new File(fileName);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			BetBasket basket = SystemProcessor
					.getBasket(SystemCache.currentBasketId);
			String line = SystemConstants.BASKET_ID + SystemConstants.EQUAL
					+ SystemCache.currentBasketId + SystemConstants.WRITE_LINE;
			writer.write(line);

			line = SystemConstants.BASKET_NAME + SystemConstants.EQUAL
					+ basket.getName() + SystemConstants.WRITE_LINE;
			writer.write(line);
			line = SystemConstants.LOTTERY_ID + SystemConstants.EQUAL
					+ SystemCache.currentLotteryId + SystemConstants.WRITE_LINE;
			writer.write(line);
			line = SystemConstants.LABEL_BETTYPE + SystemConstants.EQUAL
					+ SystemConstants.BET_TYPES[SystemCache.currentType]
					+ SystemConstants.WRITE_LINE;
			writer.write(line);
			line = SystemConstants.QIHAO + SystemConstants.EQUAL
					+ SystemCache.currentQihao + SystemConstants.WRITE_LINE;
			writer.write(line);
			line = SystemConstants.MULTI_NUMBER + SystemConstants.EQUAL
					+ SystemConstants.WRITE_LINE;
			writer.write(line);
			Map<String, List<String>> multiBets = basket.getMultiBets();
			line = SystemFunctions.toFileString(multiBets);
			writer.write(line);
			line = SystemConstants.SINGLE_NUMBER + SystemConstants.EQUAL
					+ SystemConstants.WRITE_LINE;
			writer.write(line);
			Map<String, List<String>> singleBets = basket.getSingleBets();
			line = SystemFunctions.toFileString(singleBets);
			writer.write(line);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 打开投注结果
	 * @参数: @param fileName
	 * @return void
	 * @throws
	 */
	public void doOpenRet(String fileName) {
		log.info("打开投注结果: " + fileName);
		File file = new File(fileName);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), EncodingConstants.UTF8_ENCODING));
			BetBasket basket = new BetBasket();
			String lotteryId = SystemConstants.EMPTY_STRING;
			String line = SystemConstants.EMPTY_STRING;
			int type = -1;
			String qihao = SystemConstants.EMPTY_STRING;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.equals(SystemConstants.EMPTY_STRING)) {
					continue;
				}
				String[] tmp = line.split(SystemConstants.EQUAL);
				if (tmp.length > 1) {
					tmp[0] = tmp[0].trim();
					tmp[1] = tmp[1].trim();
					if (tmp[0].equals(SystemConstants.LABEL_BETTYPE)) {
						type = SystemFunctions.convertByTypeName(tmp[1]);
						if (type != SystemCache.currentType) {
							JOptionPane.showMessageDialog(null,
									MessageConstants.NOT_CURRNET_TYPE);
							return;
						}
						basket.setType(type);
					} else if (tmp[0].equals(SystemConstants.QIHAO)) {
						qihao = tmp[1];
						if (!qihao.equals(SystemCache.currentQihao)) {
							JOptionPane.showMessageDialog(null,
									MessageConstants.NOT_CURRNET_QIHAO);
							return;
						}
						basket.setQihao(qihao);
					} else if (tmp[0].equals(SystemConstants.LOTTERY_ID)) {
						lotteryId = tmp[1];
					} else if (tmp[0].equals(SystemConstants.BASKET_NAME)) {
						basket.setName(tmp[1]);
					} else if (tmp[0].equals(SystemConstants.BASKET_ID)) {
						basket.setId(tmp[1]);
					}
				} else {
					tmp[0] = tmp[0].trim();
					if (tmp[0].equals(SystemConstants.MULTI_NUMBER)) {
						while ((line = reader.readLine()) != null) {
							line = line.trim();
							if (line.equals(SystemConstants.EMPTY_STRING)) {
								continue;
							}
							String[] bet = line.split(SystemConstants.EQUAL);
							if (bet[0].equals(SystemConstants.SINGLE_NUMBER)) {
								while ((line = reader.readLine()) != null) {
									line = line.trim();
									if (line
											.equals(SystemConstants.EMPTY_STRING)) {
										continue;
									}
									bet = line.split(SystemConstants.EQUAL);
									basket.addSingleBet(bet[0], bet[1]);
								}
							} else {
								basket.addMultiBet(bet[0], bet[1]);
							}
						}
					}
				}
			}
			basket.getMatchCount();
			reader.close();
			SystemProcessor.addBasket(basket);
			MainFrame.getInstance().displaySingleBet(1);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 更新胜负彩数据文件
	 * @参数: @param List<BetMatch>
	 * @return void
	 * @throws
	 */
	public boolean updateSfcFile(List<BetMatch> list) {
		if (list == null || list.size() != 14) {
			return false;
		}
		String qihao = list.get(0).getQihao();
		String fileName = qihao + SystemConstants.SFC
				+ FilePathConstants.DATA_FILE_POSTFIX;
		String fileDir = FilePathConstants.SFC_BETDATA_DIR
				+ SystemConstants.NEXT_FOLDER + fileName;
		File sfcDataFile = new File(fileDir);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(sfcDataFile));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		String tmpLine = SystemConstants.EMPTY_STRING;
		tmpLine = SystemConstants.LABEL_BETTYPE + SystemConstants.COLON
				+ SystemConstants.BLANK_STRING + SystemConstants.BET_TYPES[0]
				+ SystemConstants.WRITE_LINE;
		try {
			writer.write(tmpLine);
			tmpLine = SystemConstants.QIHAO + SystemConstants.COLON
					+ SystemConstants.BLANK_STRING + qihao
					+ SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.MATCH_ID + SystemConstants.TABLE
					+ SystemConstants.TABLE + SystemConstants.ITEM_NAME
					+ SystemConstants.TABLE + SystemConstants.TABLE
					+ SystemConstants.PLAY_TIME + SystemConstants.TABLE
					+ SystemConstants.TABLE + SystemConstants.VENUS
					+ SystemConstants.TABLE + SystemConstants.TABLE
					+ SystemConstants.BWIN + SystemConstants.TABLE
					+ SystemConstants.TABLE + SystemConstants.BET_PERCENT
					+ SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			for (BetMatch match : list) {
				String hr = match.getHomeRank();
				if(null == hr) {
					hr = "";
				}
				String vr = match.getVistRank();
				if(null == vr) {
					vr = "";
				}
				tmpLine = match.getMatchId() + SystemConstants.TABLE
						+ match.getCompetition() + SystemConstants.TABLE
						+ match.getTime() + SystemConstants.TABLE
						+ hr + match.getHome()
						+ SystemConstants.TABLE + SystemConstants.VS
						+ SystemConstants.TABLE + match.getVisitor()
						+ vr + SystemConstants.TABLE
						+ match.getWinOdds() + SystemConstants.TABLE
						+ match.getDrawOdds() + SystemConstants.TABLE
						+ match.getLossOdds() + SystemConstants.TABLE
						+ SystemFunctions.doubleToPercent(match.getWinRate())
						+ SystemConstants.TABLE
						+ SystemFunctions.doubleToPercent(match.getDrawRate())
						+ SystemConstants.TABLE
						+ SystemFunctions.doubleToPercent(match.getLossRate())
						+ SystemConstants.WRITE_LINE;
				writer.write(tmpLine);
			}
			writer.close();
			log.info(SystemConstants.UPDATED + SystemConstants.FILE + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @方法说明: 更新胜负彩开奖文件
	 * @参数: @param kjqihao
	 * @参数: @param sfckj
	 * @return void
	 * @throws
	 */
	public boolean updateSfcKjFile(int kjqihao, SfcKj sfckj) {
		if (sfckj == null) {
			return false;
		}
		int year = kjqihao / 1000;
		int qihao = kjqihao % 1000;
		// 更新历史统计文件
		String fileName = year + FilePathConstants.TXT_POSTFIX;
		String fileDir = FilePathConstants.SFC_WINNUMBER_DIR
				+ SystemConstants.NEXT_FOLDER + fileName;
		File sfcWnFile = new File(fileDir);
		BufferedReader reader = null;
		BufferedWriter writer = null;
		String line = SystemConstants.EMPTY_STRING;
		try {
			if (!sfcWnFile.exists()) {
				sfcWnFile.createNewFile();
			}
			int lineIndex = 0;
			String context = SystemConstants.EMPTY_STRING;
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					sfcWnFile), EncodingConstants.GBK_ENCODING);
			reader = new BufferedReader(isr);
			boolean hasupdate = false;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.equals(SystemConstants.EMPTY_STRING)) {
					continue;
				}
				lineIndex++;
				if (lineIndex == qihao) {
					line = sfckj.getKjNumber();
					hasupdate = true;
				}
				line += SystemConstants.WRITE_LINE;
				context += line;
			}
			if (!hasupdate) {
				line = sfckj.getKjNumber() + SystemConstants.WRITE_LINE;
				context += line;
			}
			reader.close();
			isr.close();
			OutputStreamWriter osw = new OutputStreamWriter(
					new FileOutputStream(sfcWnFile),
					EncodingConstants.GBK_ENCODING);
			writer = new BufferedWriter(osw);
			writer.write(context);
			writer.flush();
			writer.close();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		// 更新开奖信息文件
		fileName = kjqihao + SystemConstants.SFCKJ
				+ FilePathConstants.DATA_FILE_POSTFIX;
		fileDir = FilePathConstants.SFC_KJINFO_DIR
				+ SystemConstants.NEXT_FOLDER + fileName;
		File sfcKjFile = new File(fileDir);
		String tmpLine = SystemConstants.EMPTY_STRING;
		try {
			writer = new BufferedWriter(new FileWriter(sfcKjFile));
			tmpLine = SystemConstants.SFC_KJ_FILE + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.SFC_KJQH + SystemConstants.EQUAL
					+ kjqihao + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.SFC_KJRQ + SystemConstants.EQUAL
					+ sfckj.getDate() + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.SFC_DJRI + SystemConstants.EQUAL
					+ sfckj.getDeadline() + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			writer.write(SystemConstants.WRITE_LINE);
			tmpLine = SystemConstants.SFC_KJHM + SystemConstants.COLON
					+ SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = sfckj.getTeams() + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = sfckj.getKjNumber() + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			writer.write(SystemConstants.WRITE_LINE);
			tmpLine = SystemConstants.SFC_KJXQ + SystemConstants.COLON
					+ SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.SFC_14XL + SystemConstants.EQUAL
					+ sfckj.getSfc14Sales() + SystemConstants.BLANK_STRING
					+ SystemConstants.YUAN + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.SFC_R9XL + SystemConstants.EQUAL
					+ sfckj.getSfcr9Sales() + SystemConstants.BLANK_STRING
					+ SystemConstants.YUAN + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.SFC_CUMULATION + SystemConstants.EQUAL
					+ sfckj.getCumulation() + SystemConstants.BLANK_STRING
					+ SystemConstants.YUAN + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.SFC_FIRST_BETS + SystemConstants.EQUAL
					+ sfckj.getFirstBets() + SystemConstants.BLANK_STRING
					+ SystemConstants.ZHU + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.SFC_FIRST_PRIZE + SystemConstants.EQUAL
					+ sfckj.getFirstPrize() + SystemConstants.BLANK_STRING
					+ SystemConstants.YUAN + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.SFC_SECOND_BETS + SystemConstants.EQUAL
					+ sfckj.getSecondBets() + SystemConstants.BLANK_STRING
					+ SystemConstants.ZHU + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.SFC_SECOND_PRIZE + SystemConstants.EQUAL
					+ sfckj.getSecondPrize() + SystemConstants.BLANK_STRING
					+ SystemConstants.YUAN + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.SFC_R9_BETS + SystemConstants.EQUAL
					+ sfckj.getR9Bets() + SystemConstants.BLANK_STRING
					+ SystemConstants.ZHU + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.SFC_R9_PRIZE + SystemConstants.EQUAL
					+ sfckj.getR9Prize() + SystemConstants.BLANK_STRING
					+ SystemConstants.YUAN + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info(SystemConstants.UPDATED + SystemConstants.FILE + fileName);
		return true;
	}

	/**
	 * @方法说明: 更新北京单场数据文件
	 * @参数: @param bmList
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean updateBjdcFiles(int qihao, Map<String, List<BetMatch>> bmMap) {
		// 投注数据
		boolean success = updateBjdcDataFile(qihao, bmMap);
		// 历史开奖数据
		success = success && updateBjdcKjFile(qihao, bmMap);
		return success;
	}

	/**
	 * @方法说明: 更新北京单场历史开奖数据文件
	 * @参数: @param qihao
	 * @参数: @param bmMap
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private boolean updateBjdcKjFile(int kjqihao,
			Map<String, List<BetMatch>> bmMap) {
		if (bmMap == null) {
			return false;
		}
		String fileName = SystemConstants.EMPTY_STRING;
		int year = kjqihao / 10000;
		if (year < 10) {
			fileName += SystemConstants.ZERO_STRING;
		}
		fileName += year;
		int month = kjqihao % 10000 / 100;
		if (month < 10) {
			fileName += SystemConstants.ZERO_STRING;
		}
		fileName += month;
		int qihao = kjqihao % 100;

		// 更新历史统计文件
		fileName += FilePathConstants.TXT_POSTFIX;
		String fileDir = FilePathConstants.BJDC_WINNUMBER_DIR
				+ SystemConstants.NEXT_FOLDER + fileName;
		File bjdcWnFile = new File(fileDir);
		BufferedReader reader = null;
		BufferedWriter writer = null;
		String line = SystemConstants.EMPTY_STRING;
		try {
			if (!bjdcWnFile.exists()) {
				bjdcWnFile.createNewFile();
			}
			int lineIndex = 0;
			String context = SystemConstants.EMPTY_STRING;
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					bjdcWnFile), EncodingConstants.GBK_ENCODING);
			reader = new BufferedReader(isr);
			boolean hasUpdated = false;
			String kjLine = SystemConstants.EMPTY_STRING;
			// key排序
			TreeMap<String, List<BetMatch>> trmap = new TreeMap(bmMap);
			Iterator it = trmap.entrySet().iterator();
			while (it.hasNext()) {
				Entry entry = (Entry) it.next();
				List<BetMatch> mts = (List<BetMatch>) entry.getValue();
				if (mts != null && mts.size() > 0) {
					String kjNumber = SystemConstants.EMPTY_STRING;
					for (BetMatch m : mts) {
						int kjNum = m.getKjNumber();
						if (kjNum < 0) {
							kjNumber = SystemConstants.ASTERISK_STRING;
						} else {
							kjNumber = kjNum + SystemConstants.EMPTY_STRING;
						}
						kjLine += kjNumber;
					}
				}
			}
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.equals(SystemConstants.EMPTY_STRING)) {
					continue;
				}
				lineIndex++;
				if (lineIndex == qihao) {
					line = kjLine;
					hasUpdated = true;
				}
				line += SystemConstants.WRITE_LINE;
				context += line;
			}
			if (!hasUpdated) {
				context += kjLine + SystemConstants.WRITE_LINE;
			}
			reader.close();
			isr.close();
			OutputStreamWriter osw = new OutputStreamWriter(
					new FileOutputStream(bjdcWnFile),
					EncodingConstants.GBK_ENCODING);
			writer = new BufferedWriter(osw);
			writer.write(context);
			writer.flush();
			writer.close();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		log.info(SystemConstants.UPDATED + SystemConstants.FILE + fileName);
		return true;
	}

	/**
	 * @方法说明: 更新北京单场投注数据文件
	 * @参数: @param bmList
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private boolean updateBjdcDataFile(int qihao,
			Map<String, List<BetMatch>> bmMap) {
		if (bmMap == null) {
			return false;
		}
		// key排序
		TreeMap<String, List<BjdcMatch>> trmap = new TreeMap(bmMap);

		String fileName = qihao + SystemConstants.BJDC
				+ FilePathConstants.DATA_FILE_POSTFIX;
		String fileDir = FilePathConstants.BJDC_BETDATA_DIR
				+ SystemConstants.NEXT_FOLDER + fileName;
		File bjdcDataFile = new File(fileDir);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(bjdcDataFile));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		String tmpLine = SystemConstants.EMPTY_STRING;
		try {
			tmpLine = SystemConstants.LABEL_BETTYPE + SystemConstants.COLON
					+ SystemConstants.BLANK_STRING
					+ SystemConstants.BET_TYPES[2] + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			tmpLine = SystemConstants.QIHAO + SystemConstants.COLON
					+ SystemConstants.BLANK_STRING + qihao
					+ SystemConstants.WRITE_LINE + SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			Set keySet = trmap.keySet();
			Iterator iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				String[] mondat = key.split(SystemConstants.PART);
				tmpLine = SystemConstants.TABLE + SystemConstants.TABLE
						+ SystemConstants.TABLE + SystemConstants.TABLE
						+ mondat[0] + SystemConstants.YEAR + mondat[1]
						+ SystemConstants.MONTH + mondat[2]
						+ SystemConstants.DATE + SystemConstants.TIME_10PERIOD
						+ SystemConstants.WRITE_LINE;
				writer.write(tmpLine);
				tmpLine = SystemConstants.MATCH_ID + SystemConstants.TABLE
						+ SystemConstants.TABLE + SystemConstants.ITEM_NAME
						+ SystemConstants.TABLE + SystemConstants.TABLE
						+ SystemConstants.PLAY_TIME + SystemConstants.TABLE
						+ SystemConstants.TABLE + SystemConstants.VENUS
						+ SystemConstants.TABLE + SystemConstants.TABLE
						+ SystemConstants.BWIN + SystemConstants.TABLE
						+ SystemConstants.TABLE + SystemConstants.BET_PERCENT
						+ SystemConstants.TABLE + SystemConstants.TABLE
						+ SystemConstants.PANKOU + SystemConstants.TABLE
						+ SystemConstants.TABLE + SystemConstants.FINAL_SCORE
						+ SystemConstants.TABLE + SystemConstants.TABLE
						+ SystemConstants.NOW_SP + SystemConstants.WRITE_LINE;
				writer.write(tmpLine);
				List<BjdcMatch> matches = trmap.get(key);
				for (BjdcMatch match : matches) {
					MatchStatus stu = match.getStatus();
					String bf = SystemConstants.EMPTY_STRING;
					String winSP = SystemConstants.NONE_SP;
					String drawSP = SystemConstants.NONE_SP;
					String lossSP = SystemConstants.NONE_SP;
					// 正常结束
					if (3 == stu.getSid()) {
						bf = match.getHomeGoal() + SystemConstants.COLON
								+ match.getVistGoal();
						int kjn = match.getKjNumber();
						if (kjn == 3) {
							winSP = match.getJsWinSP()
									+ SystemConstants.EMPTY_STRING;
						} else if (kjn == 1) {
							drawSP = match.getJsDrawSP()
									+ SystemConstants.EMPTY_STRING;
						} else {
							lossSP = match.getJsLossSP()
									+ SystemConstants.EMPTY_STRING;
						}
					} else if (41 == stu.getSid()) { // 延期
						bf = SystemConstants.DELAY;
					} else if (42 == stu.getSid()) { // 腰斩
						bf = SystemConstants.CUTTING;
					} else {
						bf = SystemConstants.NOT_FINISH;
						winSP = match.getJsWinSP()
								+ SystemConstants.EMPTY_STRING;
						drawSP = match.getJsDrawSP()
								+ SystemConstants.EMPTY_STRING;
						lossSP = match.getJsLossSP()
								+ SystemConstants.EMPTY_STRING;
					}
					tmpLine = match.getMatchId()
							+ SystemConstants.TABLE
							+ match.getCompetition()
							+ SystemConstants.TABLE
							+ match.getTime()
							+ SystemConstants.TABLE
							+ (match.getHomeRank() == null ? SystemConstants.EMPTY_STRING
									: match.getHomeRank())
							+ match.getHome()
							+ SystemConstants.LEFT_PAREBTHESE
							+ match.getConcede()
							+ SystemConstants.RIGHT_PAREBTHESE
							+ SystemConstants.TABLE
							+ SystemConstants.VS
							+ SystemConstants.TABLE
							+ match.getVisitor()
							+ (match.getVistRank() == null ? SystemConstants.EMPTY_STRING
									: match.getVistRank())
							+ SystemConstants.TABLE
							+ match.getWinOdds()
							+ SystemConstants.TABLE
							+ match.getDrawOdds()
							+ SystemConstants.TABLE
							+ match.getLossOdds()
							+ SystemConstants.TABLE
							+ SystemFunctions.doubleToPercent(match
									.getWinRate())
							+ SystemConstants.TABLE
							+ SystemFunctions.doubleToPercent(match
									.getDrawRate())
							+ SystemConstants.TABLE
							+ SystemFunctions.doubleToPercent(match
									.getLossRate()) + SystemConstants.TABLE
							+ match.getPankou() + SystemConstants.TABLE + bf
							+ SystemConstants.TABLE + winSP
							+ SystemConstants.TABLE + drawSP
							+ SystemConstants.TABLE + lossSP
							+ SystemConstants.WRITE_LINE;
					writer.write(tmpLine);
				}
				writer.write(SystemConstants.WRITE_LINE);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info(SystemConstants.UPDATED + SystemConstants.FILE + fileName);
		return true;
	}

	/**
	 * @方法说明: 更新竞彩足球数据文件
	 * @参数: @param bmList
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean updateJczqFiles(Map<String, List<BetMatch>> bmMap) {
		boolean success = true;
		if (bmMap != null && bmMap.size() > 0) {
			success = updateJczqDataFile(bmMap);
			success = success && updateJczqKjFile(bmMap);
		}
		return success;
	}

	/**
	 * @方法说明: 更新竞彩足数开奖数据文件
	 * @参数: @param bmMap
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private boolean updateJczqKjFile(Map<String, List<BetMatch>> bmMap) {
		if (bmMap == null) {
			return false;
		}
		String fileName = SystemConstants.EMPTY_STRING;
		String fileDir = SystemConstants.EMPTY_STRING;
		File jczqWnFile = null;
		BufferedWriter writer = null;
		String line = SystemConstants.EMPTY_STRING;
		try {
			// key排序
			TreeMap<String, List<BetMatch>> trmap = new TreeMap(bmMap);

			Iterator it = trmap.entrySet().iterator();
			while (it.hasNext()) {
				Entry entry = (Entry) it.next();
				String period = (String) entry.getKey();
				if (period.equals(SystemConstants.EMPTY_STRING)) {
					continue;
				}
				List<BetMatch> mts = (List<BetMatch>) entry.getValue();
				String[] mondat = period.split(SystemConstants.PART);
				String year = mondat[0].substring(2);
				String month = period.split(SystemConstants.PART)[1];
				fileName = year + month + FilePathConstants.TXT_POSTFIX;
				fileDir = FilePathConstants.JCZQ_WINNUMBER_DIR
						+ SystemConstants.NEXT_FOLDER + fileName;
				jczqWnFile = new File(fileDir);
				String context = SystemConstants.EMPTY_STRING;
				if (jczqWnFile.exists()) {
					boolean hasUpdate = false;
					String newKjline = period + SystemConstants.EQUAL;
					if (mts != null && mts.size() > 0) {
						String kjNumber = SystemConstants.EMPTY_STRING;
						for (BetMatch m : mts) {
							int kjNum = m.getKjNumber();
							if (kjNum < 0) {
								kjNumber = SystemConstants.ASTERISK_STRING;
							} else {
								kjNumber = kjNum + SystemConstants.EMPTY_STRING;
							}
							newKjline += kjNumber;
						}
					}
					String tmpline = SystemConstants.EMPTY_STRING;
					InputStreamReader isr = new InputStreamReader(
							new FileInputStream(jczqWnFile),
							EncodingConstants.GBK_ENCODING);
					BufferedReader reader = new BufferedReader(isr);
					while ((tmpline = reader.readLine()) != null) {
						tmpline = tmpline.trim();
						if (tmpline.equals(SystemConstants.EMPTY_STRING)) {
							continue;
						}
						String pid = tmpline.split(SystemConstants.EQUAL)[0];

						if (pid.equals(period)) {
							tmpline = newKjline;
							hasUpdate = true;
						}
						context += (tmpline + SystemConstants.WRITE_LINE);
					}
					if (!hasUpdate) {
						context += (newKjline + SystemConstants.WRITE_LINE);
					}
					reader.close();
					isr.close();
					OutputStreamWriter osw = new OutputStreamWriter(
							new FileOutputStream(jczqWnFile),
							EncodingConstants.GBK_ENCODING);
					writer = new BufferedWriter(osw);
					writer.write(context);
					writer.flush();
					writer.close();
					osw.close();
				} else {
					writer = new BufferedWriter(new FileWriter(jczqWnFile));
					line = period + SystemConstants.EQUAL;
					if (mts != null && mts.size() > 0) {
						String kjNumber = SystemConstants.EMPTY_STRING;
						for (BetMatch m : mts) {
							int kjNum = m.getKjNumber();
							if (kjNum < 0) {
								kjNumber = SystemConstants.ASTERISK_STRING;
							} else {
								kjNumber = kjNum + SystemConstants.EMPTY_STRING;
							}
							line += kjNumber;
						}
					}
					line += SystemConstants.WRITE_LINE;
					writer.write(line);
					writer.flush();
					writer.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		log.info(SystemConstants.UPDATED + SystemConstants.FILE + fileName);
		return true;
	}

	/**
	 * @方法说明: 更新竞彩足球投注数据文件
	 * @参数: @param bmMap
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private boolean updateJczqDataFile(Map<String, List<BetMatch>> bmMap) {
		if (bmMap == null) {
			return false;
		}
		// key排序
		TreeMap<String, List<JczqMatch>> trmap = new TreeMap(bmMap);

		String fileName = SystemConstants.EMPTY_STRING;
		String fileDir = SystemConstants.EMPTY_STRING;
		File jczqDataFile = null;
		BufferedWriter writer = null;
		String tmpLine = SystemConstants.EMPTY_STRING;
		String period = SystemConstants.EMPTY_STRING;
		try {
			Set keySet = trmap.keySet();
			Iterator iterator = keySet.iterator();
			while (iterator.hasNext()) {
				period = (String) iterator.next();
				if (period.equals(SystemConstants.EMPTY_STRING)) {
					continue;
				}
				String[] mondat = period.split(SystemConstants.PART);
				fileName = period + SystemConstants.JCZQ
						+ FilePathConstants.DATA_FILE_POSTFIX;
				fileDir = FilePathConstants.JCZQ_BETDATA_DIR
						+ SystemConstants.NEXT_FOLDER + fileName;
				jczqDataFile = new File(fileDir);
				writer = new BufferedWriter(new FileWriter(jczqDataFile));
				tmpLine = SystemConstants.LABEL_BETTYPE + SystemConstants.COLON
						+ SystemConstants.BLANK_STRING
						+ SystemConstants.BET_TYPES[3]
						+ SystemConstants.WRITE_LINE;
				writer.write(tmpLine);
				tmpLine = SystemConstants.CURRENT_TIME + SystemConstants.COLON
						+ SystemConstants.BLANK_STRING + period
						+ SystemConstants.WRITE_LINE
						+ SystemConstants.WRITE_LINE;
				writer.write(tmpLine);
				tmpLine = SystemConstants.TABLE + SystemConstants.TABLE
						+ SystemConstants.TABLE + SystemConstants.TABLE
						+ mondat[0] + SystemConstants.YEAR + mondat[1]
						+ SystemConstants.MONTH + mondat[2]
						+ SystemConstants.DATE + SystemConstants.TIME_10PERIOD
						+ SystemConstants.WRITE_LINE;
				writer.write(tmpLine);
				tmpLine = SystemConstants.MATCH_ID + SystemConstants.TABLE
						+ SystemConstants.TABLE + SystemConstants.ITEM_NAME
						+ SystemConstants.TABLE + SystemConstants.TABLE
						+ SystemConstants.PLAY_TIME + SystemConstants.TABLE
						+ SystemConstants.TABLE + SystemConstants.VENUS
						+ SystemConstants.TABLE + SystemConstants.TABLE
						+ SystemConstants.BWIN + SystemConstants.TABLE
						+ SystemConstants.TABLE + SystemConstants.BET_PERCENT
						+ SystemConstants.TABLE + SystemConstants.FINAL_SCORE
						+ SystemConstants.TABLE + SystemConstants.NOW_SP
						+ SystemConstants.WRITE_LINE;
				writer.write(tmpLine);
				List<JczqMatch> matches = trmap.get(period);
				for (JczqMatch match : matches) {
					MatchStatus stu = match.getStatus();
					String bf = SystemConstants.EMPTY_STRING;
					String winSP = SystemConstants.NONE_SP;
					String drawSP = SystemConstants.NONE_SP;
					String lossSP = SystemConstants.NONE_SP;
					// 正常结束
					if (3 == stu.getSid()) {
						bf = match.getHomeGoal() + SystemConstants.COLON
								+ match.getVistGoal();
						int kjn = match.getKjNumber();
						if (kjn == 3) {
							winSP = match.getJsWinSP()
									+ SystemConstants.EMPTY_STRING;
						} else if (kjn == 1) {
							drawSP = match.getJsDrawSP()
									+ SystemConstants.EMPTY_STRING;
						} else {
							lossSP = match.getJsLossSP()
									+ SystemConstants.EMPTY_STRING;
						}
					} else if (41 == stu.getSid()) { // 延期
						bf = SystemConstants.DELAY;
					} else if (42 == stu.getSid()) { // 腰斩
						bf = SystemConstants.CUTTING;
					} else {
						bf = SystemConstants.NOT_FINISH;
						winSP = match.getJsWinSP()
								+ SystemConstants.EMPTY_STRING;
						drawSP = match.getJsDrawSP()
								+ SystemConstants.EMPTY_STRING;
						lossSP = match.getJsLossSP()
								+ SystemConstants.EMPTY_STRING;
					}
					tmpLine = match.getMatchId()
							+ SystemConstants.TABLE
							+ match.getCompetition()
							+ SystemConstants.TABLE
							+ match.getTime()
							+ SystemConstants.TABLE
							+ (match.getHomeRank() == null ? SystemConstants.EMPTY_STRING
									: match.getHomeRank())
							+ match.getHome()
							+ SystemConstants.LEFT_PAREBTHESE
							+ match.getConcede()
							+ SystemConstants.RIGHT_PAREBTHESE
							+ SystemConstants.TABLE
							+ SystemConstants.VS
							+ SystemConstants.TABLE
							+ match.getVisitor()
							+ (match.getVistRank() == null ? SystemConstants.EMPTY_STRING
									: match.getVistRank())
							+ SystemConstants.TABLE
							+ match.getWinOdds()
							+ SystemConstants.TABLE
							+ match.getDrawOdds()
							+ SystemConstants.TABLE
							+ match.getLossOdds()
							+ SystemConstants.TABLE
							+ SystemFunctions.doubleToPercent(match
									.getWinRate())
							+ SystemConstants.TABLE
							+ SystemFunctions.doubleToPercent(match
									.getDrawRate())
							+ SystemConstants.TABLE
							+ SystemFunctions.doubleToPercent(match
									.getLossRate()) + SystemConstants.TABLE
							+ bf + SystemConstants.TABLE + winSP
							+ SystemConstants.TABLE + drawSP
							+ SystemConstants.TABLE + lossSP
							+ SystemConstants.WRITE_LINE;
					writer.write(tmpLine);
				}
				writer.write(SystemConstants.WRITE_LINE);
				writer.flush();
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info(SystemConstants.UPDATED + SystemConstants.FILE + fileName);
		return true;
	}

	/**
	 * @方法说明: 从对应文件中获取数据
	 * @参数: @param type 投注类型
	 * @参数: @param sysQihao 期号
	 * @return Map<String, List<BetMatch>>
	 * @throws
	 */
	public Map<String, List<BetMatch>> getGUIDate(int type, String qihao) {
		Map<String, List<BetMatch>> map = new HashMap<String, List<BetMatch>>();
		String filePath = SystemConstants.EMPTY_STRING;
		String fileName = SystemConstants.EMPTY_STRING;
		if (type < 2) {
			fileName = qihao + SystemConstants.SFC
					+ FilePathConstants.DATA_FILE_POSTFIX;
			filePath = FilePathConstants.SFC_BETDATA_DIR
					+ SystemConstants.NEXT_FOLDER + fileName;
		} else if (type == 2) {
			fileName = qihao + SystemConstants.BJDC
					+ FilePathConstants.DATA_FILE_POSTFIX;
			filePath = FilePathConstants.BJDC_BETDATA_DIR
					+ SystemConstants.NEXT_FOLDER + fileName;
		} else {
			fileName = qihao + SystemConstants.JCZQ
					+ FilePathConstants.DATA_FILE_POSTFIX;
			filePath = FilePathConstants.JCZQ_BETDATA_DIR
					+ SystemConstants.NEXT_FOLDER + fileName;
		}
		File srcFile = new File(filePath);
		log.info("读取文件【" + srcFile.getName() + "】数据...");
		if (!srcFile.exists()) {
			JOptionPane.showMessageDialog(null,
					MessageConstants.NO_CURRENT_PERIOD);
			log.error("文件【" + srcFile.getName() + "】不存在!");
			return null;
		}
		String line = SystemConstants.EMPTY_STRING;
		String tmpstr = SystemConstants.EMPTY_STRING;
		String substr = SystemConstants.EMPTY_STRING;
		int tmpValue = 0;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(srcFile),
					EncodingConstants.UTF8_ENCODING));
			if (type < 2) {
				List<BetMatch> matches = new ArrayList<BetMatch>();
				while ((line = reader.readLine()) != null) {
					line = line.trim();
					if (line.equals(SystemConstants.EMPTY_STRING)) {
						continue;
					}
					tmpstr = line.substring(0, 2);
					if (tmpstr.matches(RegexConstants.INTEGER_REGEX)) {
						BetMatch match = new BetMatch();
						String[] dts = line.split(SystemConstants.TABLE);
						match.setType(type);
						match.setQihao(qihao);
						match.setMatchId(dts[0]);
						match.setCompetition(dts[1]);
						match.setTime(dts[2]);
						if (dts[3].charAt(0) == SystemConstants.LEFT_BRACKET) {
							match.setHomeRank(dts[3].substring(0, 4));
							match.setHome(dts[3].substring(4));
						} else {
							match.setHomeRank(SystemConstants.EMPTY_STRING);
							match.setHome(dts[3]);
						}
						if (dts[5].charAt(dts[5].length() - 1) == SystemConstants.RIGHT_BRACKET) {

							match.setVisitor(dts[5].substring(0, dts[5]
									.length() - 4));
							match.setVistRank(dts[5]
									.substring(dts[5].length() - 4));
						} else {
							match.setVisitor(dts[5]);
							match.setVistRank(SystemConstants.EMPTY_STRING);
						}
						match.setWinOdds(Double.parseDouble(dts[6]));
						match.setDrawOdds(Double.parseDouble(dts[7]));
						match.setLossOdds(Double.parseDouble(dts[8]));
						match.setWinRate(SystemFunctions
								.percentToDouble(dts[9]));
						match.setDrawRate(SystemFunctions
								.percentToDouble(dts[10]));
						match.setLossRate(SystemFunctions
								.percentToDouble(dts[11]));
						if (dts.length > 14) {
							match.setWinRQ(Integer.parseInt(dts[12]));
							match.setDrawRQ(Integer.parseInt(dts[13]));
							match.setLossRQ(Integer.parseInt(dts[14]));
						}
						matches.add(match);
					}
				}
				map.put(SystemConstants.EMPTY_STRING, matches);
			} else {
				int tmpIndex = 0;
				while ((line = reader.readLine()) != null) {
					// do not filter the table("\t")
					line = line.replaceAll(SystemConstants.BLANK_STRING,
							SystemConstants.EMPTY_STRING);
					if (line.equals(SystemConstants.EMPTY_STRING)) {
						continue;
					}
					tmpstr = line.substring(0, 1);
					if (tmpstr.equals(SystemConstants.TABLE)) {
						line = line.trim();
						String period = SystemFunctions.DateConvert(line);
						List<BetMatch> matches = new ArrayList<BetMatch>();
						while ((line = reader.readLine()) != null) {
							line = line.trim();
							if (line.equals(SystemConstants.EMPTY_STRING)) {
								map.put(period, matches);
								break;
							}
							tmpstr = line.substring(0, 1);
							if (tmpstr.matches(RegexConstants.INTEGER_REGEX)
									|| tmpstr.equals(SystemConstants.WEEK)) {
								BetMatch match = new BetMatch();
								String[] dts = line
										.split(SystemConstants.TABLE);
								match.setType(type);
								match.setQihao(qihao);
								match.setPeriod(period);
								match.setMatchId(dts[0]);
								match.setCompetition(dts[1]);
								match.setTime(dts[2]);
								tmpIndex = dts[3]
										.indexOf(SystemConstants.LEFT_PAREBTHESE);
								if (dts[3].charAt(0) == SystemConstants.LEFT_BRACKET) {
									int tix = dts[3]
											.indexOf(SystemConstants.RIGHT_BRACKET);
									match.setHomeRank(dts[3].substring(0,
											tix + 1));
									match.setHome(dts[3].substring(tix + 1,
											tmpIndex));
								} else {
									match
											.setHomeRank(SystemConstants.EMPTY_STRING);
									match
											.setHome(dts[3].substring(0,
													tmpIndex));
								}
								substr = dts[3].substring(tmpIndex + 1, dts[3]
										.length() - 1);
								if (substr
										.matches(RegexConstants.INTEGER_REGEX)) {
									tmpValue = Integer.parseInt(substr);
									match.setConcede(tmpValue);
								}
								if (dts[5].charAt(dts[5].length() - 1) == SystemConstants.RIGHT_BRACKET) {
									match.setVisitor(dts[5].substring(0, dts[5]
											.length() - 4));
									match.setVistRank(dts[5].substring(dts[5]
											.length() - 4));
								} else {
									match.setVisitor(dts[5]);
									match
											.setVistRank(SystemConstants.EMPTY_STRING);
								}
								match.setWinOdds(Double.parseDouble(dts[6]));
								match.setDrawOdds(Double.parseDouble(dts[7]));
								match.setLossOdds(Double.parseDouble(dts[8]));
								match.setWinRate(SystemFunctions
										.percentToDouble(dts[9]));
								match.setDrawRate(SystemFunctions
										.percentToDouble(dts[10]));
								match.setLossRate(SystemFunctions
										.percentToDouble(dts[11]));
								matches.add(match);
							}
						}
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("文件【" + srcFile.getName() + "】读取完成 ");
		return map;
	}
}
