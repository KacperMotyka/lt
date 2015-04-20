/**
 * @文件名称: IOManager.java
 * @类路径:   com.rb.lottery.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-9-30 上午09:36:48
 * @版本:     1.0.0
 */
package com.rb.lottery.server.manager;

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

import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.domain.BjdcMatch;
import com.rb.lottery.domain.JczqMatch;
import com.rb.lottery.domain.MatchStatus;
import com.rb.lottery.domain.SfcKj;
import com.rb.lottery.domain.SfcMatch;
import com.rb.lottery.server.common.EncodingConstants;
import com.rb.lottery.server.common.FilePathConstants;
import com.rb.lottery.server.common.MessageConstants;
import com.rb.lottery.server.common.RegexConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.server.common.SystemFunctions;
import com.rb.lottery.server.dao.BjdcDAO;
import com.rb.lottery.server.dao.HibernateSessionFactory;
import com.rb.lottery.server.dao.JczqDAO;
import com.rb.lottery.server.dao.SfcDAO;
import com.rb.lottery.server.exception.LotteryException;
import com.rb.lottery.server.util.XmlProcessor;
import com.rb.lottery.system.SysConfig;

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
	 * @throws LotteryException
	 * @方法说明: 读取系统配置文件
	 * @参数: @param sysConFile
	 * @return void
	 * @throws
	 */
	public boolean readSysConfigFile(File sysConFile) throws LotteryException {
		log.info("读取系统配置文件");
		try {
			SysConfig sysConfig = SysConfig.getInstance();
			String autoUpdate = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.AUTOUPDATE);
			if (autoUpdate == null) {
				autoUpdate = SystemConstants.TRUE;
			}
			sysConfig
					.setAutoUpdate(SystemFunctions.stringToBoolean(autoUpdate));
			String autoKj = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.AUTOKJ);
			if (autoKj == null) {
				autoKj = SystemConstants.TRUE;
			}
			sysConfig.setAutoKj(SystemFunctions.stringToBoolean(autoKj));
			String autoSave = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.AUTOSAVE);
			if (autoSave == null) {
				autoSave = SystemConstants.FALSE;
			}
			sysConfig.setAutoSave(SystemFunctions.stringToBoolean(autoSave));
			String autoCheckQihao = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.AUTOCHECKQH);
			if (autoCheckQihao == null) {
				autoCheckQihao = SystemConstants.FALSE;
			}
			sysConfig.setAutoCheckQihao(SystemFunctions
					.stringToBoolean(autoCheckQihao));
			String autoUpdtSoft = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.AUTOUPDTSOFT);
			if (autoUpdtSoft == null) {
				autoUpdtSoft = SystemConstants.FALSE;
			}
			sysConfig.setAutoUpdateSoft(SystemFunctions
					.stringToBoolean(autoUpdtSoft));
			String sfcSite = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.SFC_SITE);
			sysConfig.setSfcSite(sfcSite);
			String sfcKjSite = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.SFCKJ_SITE);
			sysConfig.setSfcKjSite(sfcKjSite);
			String bjdcSite = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.BJDC_SITE);
			sysConfig.setBjdcSite(bjdcSite);
			String jczqSite = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.JCZQ_SITE);
			sysConfig.setJczqSite(jczqSite);
			String sfcQihao = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.CURRENT_SFC_QIHAO);
			sysConfig.setSfcQihao(sfcQihao);
			String bjdcQihao = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.CURRENT_BJDC_QIHAO);
			sysConfig.setBjdcQihao(bjdcQihao);
			String jczqPeriod = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.CURRENT_JCZQ_PERIOD);
			sysConfig.setJczqPeriod(jczqPeriod);
			String maxQihaoCount = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.MAX_QIHAO_COUNT);
			if (maxQihaoCount.matches(RegexConstants.INTEGER_REGEX)) {
				sysConfig.setMaxQihaoCount(Integer.parseInt(maxQihaoCount));
			} else {
				throw (new LotteryException(12));
			}
			String lastSfcYear = XmlProcessor.getXmlAttributeValueByKey(
					sysConFile, SystemConstants.LAST_SFC_COUNT,
					SystemConstants.VALUE);
			if (lastSfcYear.matches(RegexConstants.INTEGER_REGEX)) {
				sysConfig.setLastSfcYear(Integer.parseInt(lastSfcYear));
			} else {
				throw (new LotteryException(12));
			}
			String lastSfcCount = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.LAST_SFC_COUNT);
			if (lastSfcCount.matches(RegexConstants.INTEGER_REGEX)) {
				sysConfig.setLastSfcCount(Integer.parseInt(lastSfcCount));
			} else {
				throw (new LotteryException(12));
			}
			String lastBjdcMonth = XmlProcessor.getXmlAttributeValueByKey(
					sysConFile, SystemConstants.LAST_BJDC_COUNT,
					SystemConstants.VALUE);
			if (lastBjdcMonth.matches(RegexConstants.INTEGER_REGEX)) {
				sysConfig.setLastBjdcMonth(Integer.parseInt(lastBjdcMonth));
			} else {
				throw (new LotteryException(12));
			}
			String lastBjdcCount = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.LAST_BJDC_COUNT);
			if (lastBjdcCount.matches(RegexConstants.INTEGER_REGEX)) {
				sysConfig.setLastBjdcCount(Integer.parseInt(lastBjdcCount));
			} else {
				throw (new LotteryException(12));
			}
			String onePageCount = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.ONE_PAGE_COUNT);
			if (onePageCount.matches(RegexConstants.INTEGER_REGEX)) {
				sysConfig.setOnePageCount(Integer.parseInt(onePageCount));
			} else {
				throw (new LotteryException(12));
			}
			String proxyUsed = XmlProcessor.getXmlValueByKey(sysConFile,
					SystemConstants.PROXY_USED);
			if (proxyUsed == null) {
				proxyUsed = SystemConstants.FALSE;
			}
			sysConfig.setProxyUsed(SystemFunctions.stringToBoolean(proxyUsed));
			Map<String, List<String>> backup = new HashMap<String, List<String>>();
			List<String> backupsfcList = XmlProcessor.getXmlValuesByKey(
					sysConFile, SystemConstants.SFC_SITE_LIST);
			backup.put(SystemConstants.SFC_SITE_LIST, backupsfcList);
			List<String> backupsfcKjList = XmlProcessor.getXmlValuesByKey(
					sysConFile, SystemConstants.SFCKJ_SITE_LIST);
			backup.put(SystemConstants.SFCKJ_SITE_LIST, backupsfcKjList);
			List<String> backupbjdcList = XmlProcessor.getXmlValuesByKey(
					sysConFile, SystemConstants.BJDC_SITE_LIST);
			backup.put(SystemConstants.BJDC_SITE_LIST, backupbjdcList);
			List<String> backupjczqList = XmlProcessor.getXmlValuesByKey(
					sysConFile, SystemConstants.JCZQ_SITE_LIST);
			backup.put(SystemConstants.JCZQ_SITE_LIST, backupjczqList);
			sysConfig.setBackupsiteList(backup);
			// TODO get other parameter
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} catch (LotteryException e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
	 * @方法说明:由备份网站数据更新胜负彩数据文件
	 * @参数: @param bmList
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean updateBackupSfcFile(List<SfcMatch> list) {
		if (list == null || list.size() != 14) {
			return false;
		}
		String qihao = list.get(0).getQihao();
		File folder = new File(FilePathConstants.SFC_BETDATA_DIR);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String fileName = qihao + SystemConstants.SFC
				+ FilePathConstants.DATA_FILE_POSTFIX;
		String theFile = FilePathConstants.SFC_BETDATA_DIR
				+ SystemConstants.NEXT_FOLDER + fileName;
		File sfcDataFile = new File(theFile);
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
					+ SystemConstants.TABLE + SystemConstants.BET_POPULARITY
					+ SystemConstants.WRITE_LINE;
			writer.write(tmpLine);
			for (SfcMatch match : list) {
				match.generateRates();
				tmpLine = match.getMatchId() + SystemConstants.TABLE
						+ match.getCompetition() + SystemConstants.TABLE
						+ match.getTime() + SystemConstants.TABLE
						+ match.getHomeRank() + match.getHome()
						+ SystemConstants.TABLE + SystemConstants.VS
						+ SystemConstants.TABLE + match.getVisitor()
						+ match.getVistRank() + SystemConstants.TABLE
						+ match.getWinOdds() + SystemConstants.TABLE
						+ match.getDrawOdds() + SystemConstants.TABLE
						+ match.getLossOdds() + SystemConstants.TABLE
						+ SystemFunctions.doubleToPercent(match.getWinRate())
						+ SystemConstants.TABLE
						+ SystemFunctions.doubleToPercent(match.getDrawRate())
						+ SystemConstants.TABLE
						+ SystemFunctions.doubleToPercent(match.getLossRate())
						+ SystemConstants.TABLE + match.getWinRQ()
						+ SystemConstants.TABLE + match.getDrawRQ()
						+ SystemConstants.TABLE + match.getLossRQ()
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
	 * @方法说明: 更新胜负彩数据文件
	 * @参数: @param List<BetMatch>
	 * @return void
	 * @throws
	 */
	public boolean updateSfcFile(List<SfcMatch> list) {
		if (list == null || list.size() != 14) {
			return false;
		}
		String qihao = list.get(0).getQihao();
		File folder = new File(FilePathConstants.SFC_BETDATA_DIR);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String fileName = qihao + SystemConstants.SFC
				+ FilePathConstants.DATA_FILE_POSTFIX;
		String thefile = FilePathConstants.SFC_BETDATA_DIR
				+ SystemConstants.NEXT_FOLDER + fileName;
		File sfcDataFile = new File(thefile);
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
			for (SfcMatch match : list) {
				tmpLine = match.getMatchId() + SystemConstants.TABLE
						+ match.getCompetition() + SystemConstants.TABLE
						+ match.getTime() + SystemConstants.TABLE
						+ match.getHomeRank() + match.getHome()
						+ SystemConstants.TABLE + SystemConstants.VS
						+ SystemConstants.TABLE + match.getVisitor()
						+ match.getVistRank() + SystemConstants.TABLE
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
		File folder = new File(FilePathConstants.SFC_WINNUMBER_DIR);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String fileName = year + FilePathConstants.TXT_POSTFIX;
		String theFile = FilePathConstants.SFC_WINNUMBER_DIR
				+ SystemConstants.NEXT_FOLDER + fileName;
		File sfcWnFile = new File(theFile);
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
		File kjfolder = new File(FilePathConstants.SFC_KJINFO_DIR);
		if (!kjfolder.exists()) {
			kjfolder.mkdirs();
		}
		fileName = kjqihao + SystemConstants.SFCKJ
				+ FilePathConstants.DATA_FILE_POSTFIX;
		theFile = FilePathConstants.SFC_KJINFO_DIR
				+ SystemConstants.NEXT_FOLDER + fileName;
		File sfcKjFile = new File(theFile);
		String tmpLine = SystemConstants.EMPTY_STRING;
		try {
			if (!sfcKjFile.exists()) {
				sfcKjFile.createNewFile();
			}
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
	public boolean updateBjdcFiles(int qihao, Map<String, List<BjdcMatch>> bmMap) {
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
			Map<String, List<BjdcMatch>> bmMap) {
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
		File folder = new File(FilePathConstants.BJDC_WINNUMBER_DIR);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		fileName += FilePathConstants.TXT_POSTFIX;
		String theFile = FilePathConstants.BJDC_WINNUMBER_DIR
				+ SystemConstants.NEXT_FOLDER + fileName;
		File bjdcWnFile = new File(theFile);
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
			Map<String, List<BjdcMatch>> bmMap) {
		if (bmMap == null) {
			return false;
		}
		// key排序
		TreeMap<String, List<BjdcMatch>> trmap = new TreeMap(bmMap);
		File folder = new File(FilePathConstants.BJDC_BETDATA_DIR);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String fileName = qihao + SystemConstants.BJDC
				+ FilePathConstants.DATA_FILE_POSTFIX;
		String theFile = FilePathConstants.BJDC_BETDATA_DIR
				+ SystemConstants.NEXT_FOLDER + fileName;
		File bjdcDataFile = new File(theFile);
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
	public boolean updateJczqFiles(Map<String, List<JczqMatch>> bmMap) {
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
	private boolean updateJczqKjFile(Map<String, List<JczqMatch>> bmMap) {
		if (bmMap == null) {
			return false;
		}
		String fileName = SystemConstants.EMPTY_STRING;
		String thefile = SystemConstants.EMPTY_STRING;
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
				File folder = new File(FilePathConstants.JCZQ_WINNUMBER_DIR);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				fileName = year + month + FilePathConstants.TXT_POSTFIX;
				thefile = FilePathConstants.JCZQ_WINNUMBER_DIR
						+ SystemConstants.NEXT_FOLDER + fileName;
				jczqWnFile = new File(thefile);
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
	private boolean updateJczqDataFile(Map<String, List<JczqMatch>> bmMap) {
		if (bmMap == null) {
			return false;
		}
		// key排序
		TreeMap<String, List<JczqMatch>> trmap = new TreeMap(bmMap);

		String fileName = SystemConstants.EMPTY_STRING;
		String theFile = SystemConstants.EMPTY_STRING;
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
				File folder = new File(FilePathConstants.JCZQ_BETDATA_DIR);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				fileName = period + SystemConstants.JCZQ
						+ FilePathConstants.DATA_FILE_POSTFIX;
				theFile = FilePathConstants.JCZQ_BETDATA_DIR
						+ SystemConstants.NEXT_FOLDER + fileName;
				jczqDataFile = new File(theFile);
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
	 * @方法说明: 从数据库或对应文件中获取数据
	 * @参数: @param type 投注类型
	 * @参数: @param qihao 期号
	 * @return Map<String, List<BetMatch>>
	 * @throws
	 */
	public Map<String, List<BetMatch>> getBetDate(int type, String qihao) {
		Map<String, List<BetMatch>> map = new HashMap<String, List<BetMatch>>();
		boolean databaseConn = HibernateSessionFactory.testDatabaseConnection();
		if (databaseConn) {
			if (type < 2) {
				SfcDAO sdao = new SfcDAO();
				List<SfcMatch> sfcs = sdao.getSfcMatchesByQh(qihao);
				List<BetMatch> bts = new ArrayList<BetMatch>();
				bts.addAll(sfcs);
				map.put("sfc", bts);
			} else if (2 == type) {
				BjdcDAO bdao = new BjdcDAO();
				List<BjdcMatch> bjdcs = bdao.getBjdcMatchesByQh(qihao);
				for (BetMatch match : bjdcs) {
					String period = match.getPeriod();
					List<BetMatch> bts = map.get(period);
					if (null == bts) {
						bts = new ArrayList<BetMatch>();
					}
					bts.add(match);
					map.put(period, bts);
				}
			} else if (3 == type) {
				JczqDAO jdao = new JczqDAO();
				List<JczqMatch> jczqs = jdao.getJczqMatchesByQh(qihao);
				for (BetMatch match : jczqs) {
					String period = match.getPeriod();
					List<BetMatch> bts = map.get(period);
					if (null == bts) {
						bts = new ArrayList<BetMatch>();
					}
					bts.add(match);
					map.put(period, bts);
				}
			}
		} else {
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
								match.setVistRank(dts[5].substring(dts[5]
										.length() - 4));
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
								if (tmpstr
										.matches(RegexConstants.INTEGER_REGEX)
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
										match.setHome(dts[3].substring(0,
												tmpIndex));
									}
									substr = dts[3].substring(tmpIndex + 1,
											dts[3].length() - 1);
									if (substr
											.matches(RegexConstants.INTEGER_REGEX)) {
										tmpValue = Integer.parseInt(substr);
										match.setConcede(tmpValue);
									}
									if (dts[5].charAt(dts[5].length() - 1) == SystemConstants.RIGHT_BRACKET) {
										match.setVisitor(dts[5].substring(0,
												dts[5].length() - 4));
										match
												.setVistRank(dts[5]
														.substring(dts[5]
																.length() - 4));
									} else {
										match.setVisitor(dts[5]);
										match
												.setVistRank(SystemConstants.EMPTY_STRING);
									}
									match
											.setWinOdds(Double
													.parseDouble(dts[6]));
									match.setDrawOdds(Double
											.parseDouble(dts[7]));
									match.setLossOdds(Double
											.parseDouble(dts[8]));
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
		}
		return map;
	}
}
