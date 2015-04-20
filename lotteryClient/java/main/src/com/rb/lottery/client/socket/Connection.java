/**
 * @文件名称: Connection.java
 * @类路径:   com.rb.lottery.client.socket
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-22 下午10:08:01
 * @版本:     1.0.0
 */
package com.rb.lottery.client.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.rb.lottery.system.SystemCache;

/**
 * @类功能说明: 服务器连接
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-22 下午10:08:01
 * @版本: 1.0.0
 */

public class Connection {

	private static final Logger log = Logger.getLogger(Connection.class);

	private Socket socket;

	public Connection() {
		try {
			socket = new Socket(SystemCache.serverIP, SystemCache.serverPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Connection(String ip, int server_port) {
		try {
			socket = new Socket(ip, server_port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 连接服务器
	 * @参数: @return
	 * @return Socket
	 * @throws
	 */
	public Socket connect() {
		return socket;
	}
}
