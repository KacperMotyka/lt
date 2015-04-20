/**
 * @文件名称: MainFrame.java
 * @类路径:   com.rb.lottery.server.UI.main
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-23 上午11:27:32
 * @版本:     1.0.0
 */
package com.rb.lottery.server.UI.main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.rb.lottery.server.common.CommandConstants;
import com.rb.lottery.server.common.FilePathConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.server.dao.SystemDAO;
import com.rb.lottery.server.listener.MainFrameListener;
import com.rb.lottery.server.listener.ServerListener;

/**
 * @类功能说明: 服务器端主界面
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-23 上午11:27:32
 * @版本: 1.0.0
 */

public class MainFrame extends JFrame {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -3816105091299827944L;
	private static final String TITLE = "彩票投注服务器端";
	private static final int MAIN_FRAME_WIDTH = 400;
	private static final int MAIN_FRAME_HEIGHT = 300;
	private static final int MAIN_FRAME_LOCATION_X = 200;
	private static final int MAIN_FRAME_LOCATION_Y = 50;

	private static MainFrame mainFrame = null;

	private JPanel mainPane;
	private JPanel verp;
	private JPanel subp;
	private JPanel stap;
	private JComboBox version;
	private JButton start;
	private JButton stop;
	private JLabel status;

	public static MainFrame getInstance() {
		if (mainFrame == null) {
			mainFrame = new MainFrame();
		}
		return mainFrame;
	}

	private MainFrame() {
		super();
		Image image = Toolkit.getDefaultToolkit().getImage(
				FilePathConstants.LOTTERY_IMG_FILE);
		this.setIconImage(image);
		this.setTitle(TITLE);
		this.setLocation(MAIN_FRAME_LOCATION_X, MAIN_FRAME_LOCATION_Y);
		this
				.setSize(new java.awt.Dimension(MAIN_FRAME_WIDTH,
						MAIN_FRAME_HEIGHT));
		// 设置无标题栏
		this.setUndecorated(false);
		this.setBackground(Color.lightGray);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new MainFrameListener());
		initMainFrame();
	}

	/**
	 * @方法说明: 初始化主界面
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void initMainFrame() {
		mainPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mainPane.setPreferredSize(new java.awt.Dimension(400, 300));

		verp = new JPanel(new FlowLayout(FlowLayout.CENTER));
		verp.setPreferredSize(new java.awt.Dimension(400, 100));

		JLabel margin1 = new JLabel();
		margin1.setPreferredSize(new java.awt.Dimension(400, 40));
		verp.add(margin1);
		JLabel j1 = new JLabel(SystemConstants.CHOOSE_VERSION);
		j1.setPreferredSize(new java.awt.Dimension(120, 30));
		verp.add(j1);

		SystemDAO sdao = new SystemDAO();
		String[] versions = sdao.getAllAvailableVersion();
		version = new JComboBox(versions);
		version.setPreferredSize(new java.awt.Dimension(100, 30));
		verp.add(version);
		mainPane.add(verp);

		subp = new JPanel(new FlowLayout(FlowLayout.CENTER));
		subp.setPreferredSize(new java.awt.Dimension(400, 120));
		start = new JButton(SystemConstants.SERVER_START);
		start.setPreferredSize(new java.awt.Dimension(80, 30));
		start.addActionListener(new ServerListener(
				CommandConstants.CMD_SERVER_START));
		subp.add(start);
		stop = new JButton(SystemConstants.SERVER_STOP);
		stop.setEnabled(false);
		stop.setPreferredSize(new java.awt.Dimension(80, 30));
		stop.addActionListener(new ServerListener(
				CommandConstants.CMD_SERVER_STOP));
		subp.add(stop);
		mainPane.add(subp);

		stap = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		stap.setPreferredSize(new java.awt.Dimension(400, 60));
		status = new JLabel(SystemConstants.NONE_START, JLabel.CENTER);
		status.setPreferredSize(new java.awt.Dimension(100, 30));
		stap.add(status);
		mainPane.add(stap);

		this.add(mainPane);

		this.setState(Frame.NORMAL);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * @方法说明: 获取启动版本
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public String getStartVersion() {
		return version.getSelectedItem().toString();
	}

	/**
	 * @方法说明: 修改状态
	 * @参数: @param starting
	 * @参数: @param red
	 * @return void
	 * @throws
	 */
	public void setStatus(String text, Color color) {
		status.setText(text);
		status.setForeground(color);
	}

	/**
	 * @方法说明: 修改按钮状态
	 * @参数: @param text
	 * @return void
	 * @throws
	 */
	public void setBtnStatus(String text) {
		if (text.equalsIgnoreCase("starting")) {
			start.setEnabled(false);
			stop.setEnabled(true);
			version.setEnabled(false);
		} else if (text.equalsIgnoreCase("started")) {
			start.setEnabled(false);
			stop.setEnabled(true);
			version.setEnabled(false);
		} else if (text.equalsIgnoreCase("stopping")) {
			start.setEnabled(false);
			stop.setEnabled(false);
			version.setEnabled(false);
		} else if (text.equalsIgnoreCase("stopped")) {
			start.setEnabled(true);
			stop.setEnabled(false);
			version.setEnabled(true);
		}
	}
}
