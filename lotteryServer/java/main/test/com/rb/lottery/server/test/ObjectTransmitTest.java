/**
 * @文件名称: ObjectTransmitTest.java
 * @类路径:   com.rb.lottery.client.test
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-22 下午10:19:51
 * @版本:     1.0.0
 */
package com.rb.lottery.server.test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.rb.lottery.user.User;

/**
 * @类功能说明: socket对象传递测试(服务器端)
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-22 下午10:19:51
 * @版本: 1.0.0
 */

public class ObjectTransmitTest {

	/**
	 * @方法说明:
	 * @参数: @param args
	 * @return void
	 * @throws
	 */
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		int port = 8821;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server Waiting...");
			Socket pipe = serverSocket.accept();
			ObjectInputStream serverInputStream = new ObjectInputStream(pipe
					.getInputStream());
			ObjectOutputStream serverOutputStream = new ObjectOutputStream(pipe
					.getOutputStream());
			User user = (User) serverInputStream.readObject();
			user.setUserid("002");
			user.setUsername("caibirds2");
			serverOutputStream.writeObject(user);
			serverInputStream.close();
			serverOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
