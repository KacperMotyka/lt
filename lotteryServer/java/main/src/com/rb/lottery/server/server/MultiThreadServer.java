/**
 * @文件名称: MultiThreadServer.java
 * @类路径:   com.rb.lottery.server.thread
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-19 下午04:02:08
 * @版本:     1.0.0
 */
package com.rb.lottery.server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.rb.lottery.server.handler.MultiThreadHandler;
import com.rb.lottery.system.SystemEnvironment;

/**
 * @类功能说明: 多线程socket
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-19 下午04:02:08
 * @版本: 1.0.0
 */

public class MultiThreadServer {

	private static final Logger log = Logger.getLogger(MultiThreadServer.class);

	private int port = SystemEnvironment.getInstance().server_port;
	private int port_size = SystemEnvironment.getInstance().thread_pool_size;
	private ServerSocket serverSocket;
	private ExecutorService executorService;// 线程池
	
	private boolean stop = false;

	/**
	 * @return port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return port_size
	 */
	public int getPort_size() {
		return port_size;
	}

	/**
	 * @param portSize
	 *            port_size
	 */
	public void setPort_size(int portSize) {
		port_size = portSize;
	}

	/**
	 * @return stop
	 */
	public boolean isStop() {
		return stop;
	}

	/**
	 * @param stop stop
	 */
	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public MultiThreadServer() throws IOException {
		serverSocket = new ServerSocket(port);
		// Runtime的availableProcessor()方法返回当前系统的CPU数目.
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors()
				* port_size);
	}

	public void startServices() throws Exception {
		while (!stop) {
			Socket socket = null;
			try {
				// 接收客户连接,只要客户进行了连接,就会触发accept();从而建立连接
				socket = serverSocket.accept();
				executorService.execute(new MultiThreadHandler(socket));
			} catch (Exception e) {
				e.printStackTrace();
				throw (e);
			}
		}
	}

	public void stopServices() throws IOException {
		executorService.shutdownNow();
		executorService = null;
		
		serverSocket = null;
	}

}
