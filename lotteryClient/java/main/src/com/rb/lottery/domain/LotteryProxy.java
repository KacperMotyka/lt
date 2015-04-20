/**
 * @文件名称: LotteryProxy.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-15 下午02:32:54
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;

import java.net.SocketAddress;
import java.net.Proxy.Type;


/**
 * @类功能说明: 网络代理
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-12-15 下午02:32:54
 * @版本:       1.0.0
 */

public class LotteryProxy extends java.net.Proxy implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -6833887310717298431L;

	/**
	 * @类名: Proxy.java
	 * @描述: TODO
	 * @param type
	 * @param sa
	 */
	public LotteryProxy(Type type, SocketAddress sa) {
		super(type, sa);
		// TODO Auto-generated constructor stub
	}

}
