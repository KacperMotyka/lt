/**
 * @文件名称: ServerListener.java
 * @类路径:   com.rb.lottery.server.listener
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-26 下午02:21:31
 * @版本:     1.0.0
 */
package com.rb.lottery.server.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.rb.lottery.server.UI.main.MainFrame;
import com.rb.lottery.server.common.CommandConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;
import com.rb.lottery.system.SystemProcessor;

/**
 * @类功能说明: 服务器启动停止监听
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-26 下午02:21:31
 * @版本: 1.0.0
 */

public class ServerListener implements ActionListener {

	private static final Logger log = Logger.getLogger(ServerListener.class);

	private String command;

	public ServerListener() {
		super();
	}

	public ServerListener(String cmd) {
		super();
		this.command = cmd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = command;
		Object src = e.getSource();
		if (cmd.equals(SystemConstants.EMPTY_STRING)) {
			cmd = e.getActionCommand();
		}
		if (cmd.equals(CommandConstants.CMD_SERVER_START)) {
			serverStarting();
			return;
		}
		if (cmd.equals(CommandConstants.CMD_SERVER_STOP)) {
			serverStopping();
			return;
		}
	}

	/**
	 * @方法说明: 停止服务器
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void serverStopping() {
		MainFrame mf = MainFrame.getInstance();
		mf.setStatus(SystemConstants.STOPPING, Color.BLUE);
		mf.setBtnStatus("stopping");
		SystemCache.current_version = null;
		boolean stopServer = SystemProcessor.stopServerListener();
		if (!stopServer) {
			JOptionPane.showMessageDialog(null, "停止服务器端口监听失败!");
			return;
		}
		boolean stopUpdate = SystemProcessor.stopUpdateTimer();
		if (!stopUpdate) {
			JOptionPane.showMessageDialog(null, "停止数据定时更新线程失败!");
			return;
		}
		log.info("服务器已停止");
		mf.setStatus(SystemConstants.STOPPED, Color.BLUE);
		mf.setBtnStatus("stopped");
	}

	/**
	 * @方法说明: 启动服务器
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void serverStarting() {
		MainFrame mf = MainFrame.getInstance();
		mf.setStatus(SystemConstants.STARTING, Color.RED);
		mf.setBtnStatus("starting");
		String version = mf.getStartVersion();
		SystemCache.current_version = version;
		boolean initEnv = SystemProcessor.initEnvironment(version);
		if (!initEnv) {
			JOptionPane.showMessageDialog(null, "系统环境初始化失败,请检查配置!");
			return;
		}
		mf.setTitle(SystemEnvironment.getInstance().name + " - Server");
		boolean updateSche = SystemProcessor.startDateUpdateThread();
		if (!updateSche) {
			JOptionPane.showMessageDialog(null, "数据自动更新线程启动,请检查配置!");
			return;
		}
		mf.setStatus(SystemConstants.STARTED, Color.RED);
		mf.setBtnStatus("started");
		log.info("服务器启动成功");
		boolean startServer = SystemProcessor.startServerListener();
		if (!startServer) {
			log.info("服务器启动失败");
			JOptionPane.showMessageDialog(null, "启动服务器端口监听失败,请检查配置!");
			mf.setStatus(SystemConstants.STOPPED, Color.BLACK);
			mf.setBtnStatus("stopped");
			return;
		}
	}
}
