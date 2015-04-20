/**
 * @文件名称: Package.java
 * @类路径:   com.rb.lottery.protocol
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-27 下午03:53:31
 * @版本:     1.0.0
 */
package com.rb.lottery.protocol;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @类功能说明: 数据包
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-27 下午03:53:31
 * @版本: 1.0.0
 */

public class LtPackage implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -2342586738621516629L;

	private String srcIP;
	private int srcPort;
	private String destIP;
	private int destPort;
	private String name;
	private Map<String, Object> parameter;

	public LtPackage() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			srcIP = addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public LtPackage(String name, Map<String, Object> parameter) {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			srcIP = addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.name = name;
		this.parameter = parameter;
	}

	/**
	 * @return srcIP
	 */
	public String getSrcIP() {
		return srcIP;
	}

	/**
	 * @param srcIP
	 *            srcIP
	 */
	public void setSrcIP(String srcIP) {
		this.srcIP = srcIP;
	}

	/**
	 * @return srcPort
	 */
	public int getSrcPort() {
		return srcPort;
	}

	/**
	 * @param srcPort
	 *            srcPort
	 */
	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}

	/**
	 * @return destIP
	 */
	public String getDestIP() {
		return destIP;
	}

	/**
	 * @param destIP
	 *            destIP
	 */
	public void setDestIP(String destIP) {
		this.destIP = destIP;
	}

	/**
	 * @return destPort
	 */
	public int getDestPort() {
		return destPort;
	}

	/**
	 * @param destPort
	 *            destPort
	 */
	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return parameter
	 */
	public Object getParameter(String key) {
		return parameter.get(key);
	}

	/**
	 * @param parameter
	 *            parameter
	 */
	public void setParameter(Map<String, Object> parameter) {
		this.parameter = parameter;
	}

	/**
	 * @方法说明: 添加参数
	 * @参数: @param key
	 * @参数: @param obj
	 * @return void
	 * @throws
	 */
	public void addParameter(String key, Object obj) {
		if (null == parameter) {
			parameter = new HashMap<String, Object>();
		}
		parameter.put(key, obj);
	}

	/**
	 * @方法说明: 清除参数
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void clear() {
		if (null != parameter) {
			parameter.clear();
		}
	}
}