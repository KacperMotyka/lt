/**
 * @文件名称: Sender.java
 * @类路径:   com.rb.lottery.client.socket
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-27 下午01:24:05
 * @版本:     1.0.0
 */
package com.rb.lottery.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.rb.lottery.protocol.LtPackage;


/**
 * @类功能说明: 请求发送
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-27 下午01:24:05
 * @版本: 1.0.0
 */

public class Sender {

	public Sender() {

	}

	/**
	 * @方法说明: 数据包发送（客户端和服务器端对象一致）
	 * @参数: @return
	 * @return LtPackage
	 * @throws
	 */
	public LtPackage doPost(LtPackage pack) {
		LtPackage received = null;
		ObjectInputStream socketIn = null;
		ObjectOutputStream socketOut = null;
		try {
			String destIP = pack.getDestIP();
			int destPort = pack.getDestPort();
			Socket socket = new Socket(destIP, destPort);
			// 发送命令
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			socketOut.writeObject(pack);
			// 接收服务器的反馈
			socketIn = new ObjectInputStream(socket.getInputStream());
			received = (LtPackage) (socketIn.readObject());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				socketIn.close();
				socketOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return received;
	}
}
