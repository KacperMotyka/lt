/**
 * @文件名称: SystemManager.java
 * @类路径:   com.rb.lottery.server.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-16 下午05:00:02
 * @版本:     1.0.0
 */
package com.rb.lottery.server.manager;

import java.io.File;

import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;

import com.rb.lottery.server.dao.HibernateSessionFactory;
import com.rb.lottery.server.dao.SystemDAO;
import com.rb.lottery.server.exception.LotteryException;
import com.rb.lottery.server.util.XmlProcessor;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;

/**
 * @类功能说明: 系统处理
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-16 下午05:00:02
 * @版本: 1.0.0
 */

public class SystemManager {

	private static final Logger log = Logger.getLogger(SystemManager.class);

	private static SystemManager sysManager = null;

	private SystemManager() {
	}

	public static SystemManager getInstance() {
		if (sysManager == null) {
			sysManager = new SystemManager();
		}
		return sysManager;
	}

	/**
	 * @throws LotteryException
	 * @方法说明: 初始化系统配置
	 * @参数: @param sysConfFile
	 * @return void
	 * @throws
	 */
	public boolean initSysConfig(File sysConfFile) throws LotteryException {
		return IOManager.getInstance().readSysConfigFile(sysConfFile);
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
		XmlProcessor.updateXmlValue(srcFile, nodeName, nodeAuttribue,
				nodeAuttribueValue, nodeText);
	}

	/**
	 * @方法说明: 初始化系统环境配置
	 * @参数:
	 * @return void
	 * @throws
	 */
	public boolean initSysEnvironment(String version) {
		try {
			SystemDAO sdao = new SystemDAO();
			// 初始化系统环境配置
			SystemEnvironment.init(sdao.getEnvironmentByVersion(version));
			log.info("[" + SystemEnvironment.getInstance().name
					+ "] System Environment has been Initialed");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @方法说明:测试数据库连接
	 * @参数:
	 * @return void
	 * @throws
	 */
	public boolean testDatabaseConnect() {
		try {
			HibernateSessionFactory.testDatabaseConnection();
			log.info("Connect to database: "
					+ HibernateSessionFactory.getConfiguration().getProperty(
							"connection.url"));
		} catch (GenericJDBCException je) {
			je.printStackTrace();
			log.error("Can not connect to database: "
					+ HibernateSessionFactory.getConfiguration().getProperty(
							"connection.url"));
			log.info("Change save mode to file");
			SystemCache.save_mode = 1;
			return false;
		}
		return true;
	}

}
