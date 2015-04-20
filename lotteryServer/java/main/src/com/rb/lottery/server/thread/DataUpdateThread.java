/**
 * @文件名称: DataUpdateThread.java
 * @类路径:   com.rb.lottery.thread
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-2 上午09:21:53
 * @版本:     1.0.0
 */
package com.rb.lottery.server.thread;

import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import com.rb.lottery.server.common.MessageConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.system.SystemCache;

/**
 * @类功能说明: 数据更新线程
 * @类修改者: robin
 * @修改日期: 2011-11-21
 * @修改说明: 添加进程条
 * @作者: robin
 * @创建时间: 2011-11-2 上午09:21:53
 * @版本: 1.0.0
 */

abstract public class DataUpdateThread extends Thread {

	JProgressBar jProgressBar;

	// 更新类型
	protected int type;
	// 从该网站更新
	protected String dataSite;
	// 当前需要更新的期号
	protected int qihao;
	//保存模式 1-文件 2-数据库 3-both
	protected static int SAVE_MODE = SystemCache.save_mode;

	public DataUpdateThread() {
		super();
		this.type = -1;
		this.dataSite = SystemConstants.EMPTY_STRING;
		this.qihao = 0;
		this.jProgressBar = new JProgressBar(0, 100);
	}

	public DataUpdateThread(JProgressBar jProgressBar) {
		super();
		this.type = -1;
		this.dataSite = SystemConstants.EMPTY_STRING;
		this.qihao = 0;
		this.jProgressBar = jProgressBar;
	}

	/**
	 * @类名: DataUpdateThread.java
	 * @描述: TODO
	 * @param type
	 * @param srcData
	 */
	public DataUpdateThread(int type, String dataSite, int qihao) {
		super();
		this.type = type;
		this.dataSite = dataSite;
		this.qihao = qihao;
		this.jProgressBar = new JProgressBar(0, 100);
	}

	public DataUpdateThread(int type, String dataSite, int qihao,
			JProgressBar jProgressBar) {
		super();
		this.type = type;
		this.dataSite = dataSite;
		this.qihao = qihao;
		this.jProgressBar = jProgressBar;
	}

	public DataUpdateThread(ThreadGroup group, int type, String dataSite,
			int qihao) {
		super(group, null, SystemConstants.EMPTY_STRING, 0);
		this.type = type;
		this.dataSite = dataSite;
		this.qihao = qihao;
		this.jProgressBar = new JProgressBar(0, 100);
	}

	public DataUpdateThread(ThreadGroup group, int type, String dataSite,
			int qihao, JProgressBar jProgressBar) {
		super(group, null, SystemConstants.EMPTY_STRING, 0);
		this.type = type;
		this.dataSite = dataSite;
		this.qihao = qihao;
		this.jProgressBar = jProgressBar;
	}

	/**
	 * @类名: DataUpdateThread.java
	 * @描述: TODO
	 * @param group
	 * @param i
	 * @param jczqSite
	 */
	public DataUpdateThread(ThreadGroup group, int type, String dataSite,
			JProgressBar jProgressBar) {
		super(group, null, SystemConstants.EMPTY_STRING, 0);
		this.type = type;
		this.dataSite = dataSite;
		this.jProgressBar = jProgressBar;
	}

	public DataUpdateThread(ThreadGroup group, int type, String dataSite) {
		super(group, null, SystemConstants.EMPTY_STRING, 0);
		this.type = type;
		this.dataSite = dataSite;
		this.jProgressBar = new JProgressBar(0, 100);
	}

	/**
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return dateSite
	 */
	public String getDataSite() {
		return dataSite;
	}

	/**
	 * @param dateSite
	 *            dateSite
	 */
	public void setDataSite(String dataSite) {
		this.dataSite = dataSite;
	}

	/**
	 * @return qihao
	 */
	public int getQihao() {
		return qihao;
	}

	/**
	 * @param qihao
	 *            qihao
	 */
	public void setQihao(int qihao) {
		this.qihao = qihao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			updateFromSite();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, MessageConstants.CONNECT_ERROR);
		}
	}

	/**
	 * @方法说明: 从指定网站得到数据进行解析
	 * @参数: @param dataSite
	 * @return void
	 * @throws
	 */
	abstract public int updateFromSite() throws UnknownHostException;

	/**
	 * @方法说明: html源文件解析
	 * @参数: @param srcData html源文件
	 * @return int 返回期号
	 * @throws
	 */
	abstract public int doHtmlAnalyzing(String srcData);

}
