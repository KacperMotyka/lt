/**
 * @文件名称: SystemConfManager.java
 * @类路径:   com.rb.lottery.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-28 下午05:06:36
 * @版本:     1.0.0
 */
package com.rb.lottery.client.manager;

import java.io.File;
import java.io.IOException;

import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.exception.LotteryException;
import com.rb.lottery.client.util.XmlProcessor;


/**
 * @类功能说明: 系统配置处理
 * @类修改者: robin
 * @修改日期: 2011-10-31
 * @修改说明: 单例模式
 * @作者: robin
 * @创建时间: 2011-10-28 下午05:06:36
 * @版本: 1.0.0
 */

public class SystemConfManager {

	private static SystemConfManager sysManager = null;

	private SystemConfManager() {
	}

	public static SystemConfManager getInstance() {
		if (sysManager == null) {
			sysManager = new SystemConfManager();
		}
		return sysManager;
	}

	/**
	 * @方法说明: 弹出计算器程序
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void callCalc() {
		try {
			Process pro = Runtime.getRuntime().exec(
					FilePathConstants.CALC_EXE_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 弹出记事本程序
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void callNotepad() {
		try {
			Process pro = Runtime.getRuntime().exec(
					FilePathConstants.NOTEPAD_EXE_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 初始化用户配置
	 * @参数: @param userConfFile
	 * @return void
	 * @throws
	 */
	public void initUserConfig(File userConfFile) throws LotteryException {
		IOManager.getInstance().readUserConfigFile(userConfFile);
	}

	/**
	 * @方法说明: 更新系统配置
	 * @参数: @param key
	 * @参数: @param value
	 * @return void
	 * @throws
	 */
	public void updateSystemConfig(File srcFile, String key, String value) {
		XmlProcessor.updateXmlValue(srcFile, key, value);
	}

	/**
	 * @方法说明: 更新系统配置
	 * @参数: @param sysConFile
	 * @参数: @param lastSfcCount
	 * @参数: @param xmlValue
	 * @参数: @param string
	 * @参数: @param substring
	 * @return void
	 * @throws
	 */
	public void updateSystemConfig(File srcFile, String nodeName,
			String nodeAuttribue, String nodeAuttribueValue, String nodeText) {
		XmlProcessor.updateXmlValue(srcFile, nodeName, nodeAuttribue, nodeAuttribueValue, nodeText);
	}
}
