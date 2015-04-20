/**
 * @文件名称: ObjectTransmitTest.java
 * @类路径:   com.rb.lottery.client.test
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-22 下午10:19:51
 * @版本:     1.0.0
 */
package com.rb.lottery.client.test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.rb.lottery.user.User;

/**
 * @类功能说明: socket对象传递测试(客户端)
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
		Socket socket = null;
		int port = 8821;
		try {
			User user = new User();
			user.setUsername("caibirds1");
			user.setUserid("001");
			System.out.println("Before tansport:\nUserID= " + user.getUserid());
			System.out.println("UserName= " + user.getUsername());
			socket = new Socket("localhost", port);
			ObjectOutputStream clientOutputStream = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream clientInputStream = new ObjectInputStream(socket
					.getInputStream());
			clientOutputStream.writeObject(user);
			user = (User) clientInputStream.readObject();
			System.out.println("\nAfter tansport:\nUserID = "
					+ user.getUserid());
			System.out.println("UserName = " + user.getUsername());
			clientOutputStream.close();
			clientInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
