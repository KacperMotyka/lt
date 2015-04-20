/**
 * @文件名称: ServerStart.java
 * @类路径:   com.rb.lottery.server.run
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-16 下午02:42:23
 * @版本:     1.0.0
 */
package com.rb.lottery.server.run;

import com.rb.lottery.server.UI.main.MainFrame;
import com.rb.lottery.system.SystemProcessor;


/**
 * @类功能说明: 服务器启动
 * @类修改者:     
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2012-3-16 下午02:42:23
 * @版本:       1.0.0
 */

public class ServerStart {

	
	/**
	 * @方法说明: 
	 * @参数:     @param args
	 * @return    void
	 * @throws
	 */
	public static void main(String[] args) {
		// 初始化日志配置
		SystemProcessor.initLog4j();
		
		// 服务器端界面
		MainFrame mf = MainFrame.getInstance();
		mf.setVisible(true);
	}

}
