/**
 * @文件名称: MultiThreadHandler.java
 * @类路径:   com.rb.lottery.server.thread
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-19 下午04:10:08
 * @版本:     1.0.0
 */
package com.rb.lottery.server.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.rb.lottery.protocol.LtPackage;
import com.rb.lottery.server.manager.SocketManager;

/**
 * @类功能说明: 客户端事件响应
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-19 下午04:10:08
 * @版本: 1.0.0
 */

public class MultiThreadHandler implements Runnable {

	private static final Logger log = Logger
			.getLogger(MultiThreadHandler.class);

	private Socket socket;

	public MultiThreadHandler(Socket socket) {
		this.socket = socket;
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}

	public String echo(String msg) {
		return "echo:" + msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		SocketManager socketManager = SocketManager.getInstance();
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			log.info("New connection accepted " + socket.getInetAddress() + ":"
					+ socket.getPort());
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			LtPackage pack = null;
			try {
				pack = (LtPackage) (ois.readObject());
				pack.setSrcPort(socket.getPort());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			LtPackage newPack = socketManager.response(pack);
			oos.writeObject(newPack);
			/*
			 * BufferedReader br = getReader(socket); PrintWriter pw =
			 * getWriter(socket); String cmd = null; while ((cmd =
			 * br.readLine()) != null) { log.info(cmd); pw.println(echo(cmd));
			 * if (cmd.equals("getEnviroment")) { SystemEnvironment sysEnv =
			 * reqHandler.getSystemEnvironment(); }
			 * 
			 * }
			 */
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				ois.close();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
