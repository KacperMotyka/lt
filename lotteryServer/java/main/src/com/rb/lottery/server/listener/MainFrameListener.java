/**
 * @文件名称: MainFrameListener.java
 * @类路径:   com.rb.lottery.server.listener
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-23 下午03:33:13
 * @版本:     1.0.0
 */
package com.rb.lottery.server.listener;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import com.rb.lottery.server.UI.main.MainFrame;
import com.rb.lottery.server.common.MessageConstants;
import com.rb.lottery.server.common.FilePathConstants;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-23 下午03:33:13
 * @版本: 1.0.0
 */

public class MainFrameListener implements ActionListener, WindowListener {

	private PopupMenu pop;
	private MenuItem open;
	private MenuItem exit;
	private static TrayIcon trayicon;

	private static boolean hasAsk = false;

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
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		final MainFrame mf = MainFrame.getInstance();
		int isOK = 0;
		if (!hasAsk) {
			isOK = JOptionPane.showConfirmDialog(null,
					MessageConstants.TO_SYSTEMTRAY,
					MessageConstants.INFOMATION,
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null);
		}
		if (isOK == 0) {
			hasAsk = true;
			if (SystemTray.isSupported()) {
				SystemTray tray = SystemTray.getSystemTray();
				pop = new PopupMenu();
				// 运行时设置VM参数为[-Dfile.encoding=GB18030],否则乱码
				open = new MenuItem("打开");
				open.addActionListener(this);

				exit = new MenuItem("退出");
				exit.addActionListener(this);

				pop.add(open);
				pop.add(exit);

				Image icon = Toolkit.getDefaultToolkit().getImage(
						FilePathConstants.LOTTERY_IMG_FILE);
				trayicon = new TrayIcon(icon, mf.getTitle(), pop);
				trayicon.addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							mf.setVisible(true);
							SystemTray.getSystemTray().remove(trayicon);
						}
					}

					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}
				});
				try {
					mf.setVisible(false);
					tray.add(trayicon);
				} catch (AWTException ae) {
					ae.printStackTrace();
				}
			}
		} else if (1 == isOK) {
			mf.dispose();
			System.exit(0);
		}

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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		MainFrame mf = MainFrame.getInstance();
		if (open == e.getSource()) {
			mf.setVisible(true);
			SystemTray.getSystemTray().remove(trayicon);
		}
		if (exit == e.getSource()) {
			mf.dispose();
			System.exit(0);
		}

	}

}
