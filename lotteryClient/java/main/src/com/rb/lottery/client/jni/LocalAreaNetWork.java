/**
 * @文件名称: LocalAreaNetWork.java
 * @类路径:   com.rb.lottery.client.jni
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-29 上午10:04:49
 * @版本:     1.0.0
 */
package com.rb.lottery.client.jni;

import java.io.File;

import com.rb.lottery.client.common.FilePathConstants;

/**
 * @类功能说明: 本地连接
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-29 上午10:04:49
 * @版本: 1.0.0
 */

public class LocalAreaNetWork {

	static {
		System.load(System.getProperty("user.dir") + File.separator
				+ FilePathConstants.LOCALNETWORK_API_DLL_DIR);
	}

	/**
	 * 获取本机名
	 * @return hostName
	 */
	public native String getHostName();

	/**
	 * 获取Primary Dns Suffix
	 * @return pridnssuf
	 */
	public native String getPridnssuf();

	/**
	 * 获取Node Type
	 * @return nodeType
	 */
	public native String getNodeType();

	/**
	 * 获取IP Routing Enabled
	 * @return iproutenable
	 */
	public native boolean isIproutenable();

	/**
	 * 获取WINS Proxy Enabled
	 * @return winproenable
	 */
	public native boolean isWinproenable();

	/**
	 * 获取本地连接网卡描述
	 * @return Description
	 */
	public native String getDescription();

	/**
	 * 获取物理地址MAC
	 * @return MAC
	 */
	public native String getMAC();

	/**
	 * 获取Dhcp Enabled
	 * @return DhcpEnabled
	 */
	public native boolean isDhcpEnabled();

	/**
	 * 获取IP地址
	 * @return ip
	 */
	public native String getIP();
	
	/**
	 * 获取子网掩码Subnet Mask
	 * @return SubnetMask
	 */
	public native String getSubnetMask();
	
	/**
	 * 获取默认网关
	 * @return DefaultGateway
	 */
	public native String getDefaultGateway();
	
	/**
	 * 获取DNS服务器
	 * @参数 index 第i个DNS服务器，无则返回null
	 * @return DNSServers
	 */
	public native String getDNSServers(int index);
}
