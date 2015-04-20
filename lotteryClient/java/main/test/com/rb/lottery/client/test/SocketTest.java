/**
 * @文件名称: SocketTest.java
 * @类路径:   com.rb.lottery.client.test
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-22 下午10:00:18
 * @版本:     1.0.0
 */
package com.rb.lottery.client.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @类功能说明: socket测试
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-22 下午10:00:18
 * @版本: 1.0.0
 */

public class SocketTest {

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
			socket = new Socket("localhost", port);
			// 发送关闭命令
			OutputStream socketOut = socket.getOutputStream();
			socketOut.write("shutdown\r\n".getBytes());

			// 接收服务器的反馈
			BufferedReader br = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			String msg = null;
			while ((msg = br.readLine()) != null)
				System.out.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
