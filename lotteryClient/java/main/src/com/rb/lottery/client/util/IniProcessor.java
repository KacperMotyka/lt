/**
 * @文件名称: IniProcessor.java
 * @类路径:   com.rb.lottery.util
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-23 下午02:53:25
 * @版本:     1.0.0
 */
package com.rb.lottery.client.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.rb.lottery.client.common.EncodingConstants;
import com.rb.lottery.client.common.RegexConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.common.SystemFunctions;

/**
 * @类功能说明: ini等配置文件处理
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-23 下午02:53:25
 * @版本: 1.0.0
 */

public class IniProcessor {

	private static final Logger log = Logger.getLogger(IniProcessor.class);

	/**
	 * 　　 从ini配置文档中读取变量的值 　　* @param file 配置文档的路径 　　* @param section 要获取的变量所在段名称
	 * 　　* @param variable 要获取的变量名称 　　* @param defaultValue 变量名称不存在时的默认值 　　* @return
	 * 变量的值 　　* @throws IOException 抛出文档操作可能出现的io异常 　　
	 */
	public static String getProfileString(String file, String section,
			String variable, String defaultValue) throws IOException {
		String strLine, value = SystemConstants.EMPTY_STRING;
		InputStreamReader isreader = new InputStreamReader(new FileInputStream(
				file), EncodingConstants.GBK_ENCODING);
		BufferedReader bufferedReader = new BufferedReader(isreader);
		boolean isInSection = false;
		try {
			while ((strLine = bufferedReader.readLine()) != null) {
				strLine = strLine.trim();
				// strLine = strLine.split("[;]")[0];
				Pattern p;
				Matcher m;
				p = Pattern.compile(RegexConstants.LEFT_BRACKET + section
						+ RegexConstants.RIGHT_BRACKET);
				m = p.matcher((strLine));
				if (m.matches()) {
					p = Pattern.compile(RegexConstants.LEFT_BRACKET + section
							+ RegexConstants.RIGHT_BRACKET);
					m = p.matcher(strLine);
					if (m.matches()) {
						isInSection = true;
					} else {
						isInSection = false;
					}
				}
				if (isInSection == true) {
					strLine = strLine.trim();
					String[] strArray = strLine.split(SystemConstants.EQUAL);
					if (strArray.length == 1) {
						value = strArray[0].trim();
						if (value.equalsIgnoreCase(variable)) {
							value = SystemConstants.EMPTY_STRING;
							return value;
						}
					} else if (strArray.length == 2) {
						value = strArray[0].trim();
						if (value.equalsIgnoreCase(variable)) {
							value = strArray[1].trim();
							return value;
						}
					} else if (strArray.length > 2) {
						value = strArray[0].trim();
						if (value.equalsIgnoreCase(variable)) {
							value = strLine.substring(
									strLine.indexOf(SystemConstants.EQUAL) + 1)
									.trim();
							return value;
						}
					}
				}
			}
		} finally {
			bufferedReader.close();
		}
		return defaultValue;
	}

	/**
	 * 修改ini配置文档中变量的值
	 * 
	 * @param file
	 *            配置文档的路径
	 * @param section
	 *            要修改的变量所在段名称
	 * @param variable
	 *            要修改的变量名称
	 * @param value
	 *            变量的新值
	 * @throws IOException
	 *             抛出文档操作可能出现的io异常
	 */
	public static boolean setProfileString(String file, String section,
			String variable, String value) throws IOException {
		String fileContent, allLine, strLine, newLine;
		String getValue;
		InputStreamReader isreader = new InputStreamReader(new FileInputStream(
				file), EncodingConstants.GBK_ENCODING);
		BufferedReader bufferedReader = new BufferedReader(isreader);
		boolean isInSection = false;
		fileContent = SystemConstants.EMPTY_STRING;
		try {
			while ((allLine = bufferedReader.readLine()) != null) {
				allLine = allLine.trim();
				log.debug("allLine == " + allLine);
				strLine = allLine;
				Pattern p;
				Matcher m;
				if (SystemFunctions.isSection(strLine)) {
					p = Pattern.compile(RegexConstants.LEFT_BRACKET + section
							+ RegexConstants.RIGHT_BRACKET);
					m = p.matcher((strLine));
					if (m.matches()) {
						isInSection = true;
					} else {
						isInSection = false;
					}
				}
				if (isInSection == true) {
					strLine = strLine.trim();
					String[] strArray = strLine.split(SystemConstants.EQUAL);
					getValue = strArray[0].trim();
					if (getValue.equalsIgnoreCase(variable)) {
						// newLine = getValue + " = " + value + " " + remarkStr;
						newLine = getValue + SystemConstants.EQUAL + value
								+ SystemConstants.WRITE_LINE;
						fileContent += newLine;
						while ((allLine = bufferedReader.readLine()) != null) {
							fileContent += allLine + SystemConstants.WRITE_LINE;
						}
						bufferedReader.close();
						isreader.close();
						OutputStreamWriter osw = new OutputStreamWriter(
								new FileOutputStream(file),
								EncodingConstants.GBK_ENCODING);
						BufferedWriter bufferedWriter = new BufferedWriter(osw);
						bufferedWriter.write(fileContent);
						bufferedWriter.flush();
						bufferedWriter.close();
						osw.close();
						return true;
					}
				}
				fileContent += allLine + SystemConstants.WRITE_LINE;
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			bufferedReader.close();
		}
		return false;
	}

}
