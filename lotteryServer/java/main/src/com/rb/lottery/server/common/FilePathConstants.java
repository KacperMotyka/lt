/**
 * @文件名称: FilePathConstants.java
 * @类路径:   com.rb.lottery.common
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-18 上午10:43:27
 * @版本:     1.0.0
 */
package com.rb.lottery.server.common;


/**
 * @类功能说明:  文件路径常量配置
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-18 上午10:43:27
 * @版本:       1.0.0
 */

public interface FilePathConstants {

	// file post
	public static final String PROJECT_FILE_POSTFIX = ".lprj";
	public static final String RESULT_FILE_POSTFIX = ".lret";
	public static final String HTML_POSTFIX = ".html";
	public static final String SHTML_POSTFIX = ".shtml";
	public static final String TXT_POSTFIX = ".txt";
	public static final String DATA_FILE_POSTFIX = ".dat";
	public static final String CSS_FILE_POSTFIX = ".css";
	
	// configuration files path
	public static final String UP_DIR = "..";
	public static final String LOG4J_CONFIG_FILE = "java\\main\\resource\\properties\\log4j.properties";
	public static final String SYSTEM_CONFIG_FILE = "java\\main\\resource\\sysConf\\sysConfig.xml";
	public static final String EXCEPTION_CONFIG_FILE = "java\\main\\resource\\sysConf\\exception.xml";
	public static final String CSS_FILE_DIR = "java\\main\\resource\\css";
	
	// image files path
	public static final String LOTTERY_IMG_FILE = "java\\main\\resource\\image\\lottery.jpg";
	public static final String BACKGROUND_IMG_FILE = "java\\main\\resource\\image\\background.jpg";
	
	// data files path
	public static final String SFC_WINNUMBER_DIR = "data\\sfc\\winnumber";
	public static final String SFC_BETDATA_DIR = "data\\sfc\\betdata";
	public static final String SFC_KJINFO_DIR = "data\\sfc\\kjinfo";
	public static final String BJDC_WINNUMBER_DIR = "data\\bjdc\\winnumber";
	public static final String BJDC_BETDATA_DIR = "data\\bjdc\\betdata";
	public static final String JCZQ_WINNUMBER_DIR = "data\\jczq\\winnumber";
	public static final String JCZQ_BETDATA_DIR = "data\\jczq\\betdata";
	public static final String DIST_FILE_DIR = "dist";
	
	// dll files path
	public static final String FILTER_API_DLL_DIR = "java\\main\\resource\\sysConf\\apiDll\\lotteryFilter.dll";
	public static final String SYSTEM_API_DLL_DIR = "java\\main\\resource\\sysConf\\apiDll\\LAPIrb.dll";
}
