/**
 * @文件名称: MainFrameListener.java
 * @类路径:   com.rb.lottery.UI.listener
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-20 下午04:53:55
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.listener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.rb.lottery.system.SystemProcessor;

/**
 * @类功能说明: 主界面事件响应
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-20 下午04:53:55
 * @版本: 1.0.0
 */

public class MainFrameListener implements WindowListener {

	private boolean isSave;
	/**
	 * @return isSave
	 */
	public boolean isSave() {
		return isSave;
	}

	/**
	 * @param isSave isSave
	 */
	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		if (isSave) {
			SystemProcessor.saveAndExit();
		} else {
			SystemProcessor.saveUserConfig();
			System.exit(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		isSave = SystemProcessor.isExitSave();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent
	 * )
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent
	 * )
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
