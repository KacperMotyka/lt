/**
 * @文件名称: ClientStart.java
 * @类路径:   com.rb.lottery.client.run
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-16 下午02:45:28
 * @版本:     1.0.0
 */
package com.rb.lottery.client.run;

import com.rb.lottery.client.UI.main.MainFrame;
import com.rb.lottery.system.SystemProcessor;


/**
 * @类功能说明: 客户端启动
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2012-3-16 下午02:45:28
 * @版本:       1.0.0
 */

public class ClientStart {

	/**
	 * @方法说明: 
	 * @参数:     @param args
	 * @return    void
	 * @throws
	 */
	public static void main(String[] args) {
		// 系统处理器初始化
		SystemProcessor.init();
		// 系统环境初始化
		SystemProcessor.initEnvironment();
		// 启动客户端
		MainFrame mf = MainFrame.getInstance();
	}

}
