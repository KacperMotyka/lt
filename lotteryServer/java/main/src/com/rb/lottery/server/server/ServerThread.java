/**
 * @文件名称: ServerThread.java
 * @类路径:   com.rb.lottery.server.server
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-27 上午09:13:11
 * @版本:     1.0.0
 */
package com.rb.lottery.server.server;

import java.io.IOException;

import org.apache.log4j.Logger;
import com.rb.lottery.server.common.SystemConstants;

/**
 * @类功能说明: 服务器监听端口线程
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-27 上午09:13:11
 * @版本: 1.0.0
 */

public class ServerThread extends Thread {

	private static final Logger log = Logger.getLogger(ServerThread.class);

	private static final String THREAD_NAME = "服务器监听端口线程";

	private MultiThreadServer serverThread;

	public ServerThread() {
		super();
		this.setName(THREAD_NAME);
		try {
			serverThread = new MultiThreadServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ServerThread(ThreadGroup group) {
		super(group, null, SystemConstants.EMPTY_STRING, 0);
		this.setName(THREAD_NAME);
		try {
			serverThread = new MultiThreadServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return port
	 */
	public int getPort() {
		return serverThread.getPort();
	}

	/**
	 * @param port
	 *            port
	 */
	public void setPort(int port) {
		serverThread.setPort(port);
	}

	/**
	 * @return port_size
	 */
	public int getPort_size() {
		return serverThread.getPort_size();
	}

	/**
	 * @param portSize
	 *            port_size
	 */
	public void setPort_size(int portSize) {
		serverThread.setPort_size(portSize);
	}

	/**
	 * @return stop
	 */
	public boolean isStop() {
		return serverThread.isStop();
	}

	/**
	 * @param stop
	 *            stop
	 */
	public void setStop(boolean stop) {
		serverThread.setStop(stop);
	}

	/**
	 * @方法说明: 停止
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void stopService() {
		setStop(true);
		try {
			serverThread.stopServices();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			setStop(false);
			serverThread.startServices();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

}
