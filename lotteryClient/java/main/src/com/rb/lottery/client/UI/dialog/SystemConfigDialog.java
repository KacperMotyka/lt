/**
 * @文件名称: SystemConfigDialog.java
 * @类路径:   com.rb.lottery.UI.dialog
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-15 下午01:04:34
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.dialog;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.system.SysConfig;

/**
 * @类功能说明: 系统设置对话框
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-15 下午01:04:34
 * @版本: 1.0.0
 */

public class SystemConfigDialog extends JFrame {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -5993205634296276847L;

	private static final int FRAME_WIDTH = 342;
	private static final int FRAME_HEIGHT = 171;
	private static final int FRAME_LOCATION_X = 600;
	private static final int FRAME_LOCATION_Y = 300;
	private static final String FRAME_TITLE = "系统设置";

	private JTabbedPane sysConfig;
	private JPanel sysCommon;
	private JTextField onePageCount;
	private JComboBox calcOdds;
	private JCheckBox useAvgOdd;
	private JCheckBox showFilterProcess;
	private JCheckBox autoCheckQh;
	private JCheckBox autoSaveConf;
	private JCheckBox autoUpdateData;
	private JCheckBox autoUpdateSoft;
	private JPanel sysRollback;
	private JPanel sysProxy;
	private JButton confirm;
	private JButton cancel;

	public SystemConfigDialog() {
		Image image = Toolkit.getDefaultToolkit().getImage(
				FilePathConstants.LOTTERY_IMG_FILE);
		this.setIconImage(image);
		this.setTitle(FRAME_TITLE);
		this.setLocation(FRAME_LOCATION_X, FRAME_LOCATION_Y);
		this
				.setPreferredSize(new java.awt.Dimension(FRAME_WIDTH,
						FRAME_HEIGHT));
		this.setLayout(new FlowLayout());
		sysConfig = new JTabbedPane();
		initSysCommon();
		sysConfig.add(sysCommon);
		sysConfig.setTitleAt(0, SystemConstants.SYSTEM_COMMON_SETTING);

		initSysRollBack();
		sysConfig.add(sysRollback);
		sysConfig.setTitleAt(1, SystemConstants.SYSTEM_ROLLBACK_SETTING);

		initSysProxy();
		sysConfig.add(sysProxy);
		sysConfig.setTitleAt(2, SystemConstants.SYSTEM_PROXY_SETTING);

		confirm = new JButton(SystemConstants.CONFIRM);
		this.add(confirm);
		cancel = new JButton(SystemConstants.CANCEL);
		this.add(cancel);
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void initSysProxy() {
		sysProxy = new JPanel(new FlowLayout());
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void initSysRollBack() {
		sysRollback = new JPanel(new FlowLayout());
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void initSysCommon() {
		sysCommon = new JPanel(new GridLayout(10, 1));

		SysConfig sysconf = SysConfig.getInstance();

		JPanel j1 = new JPanel(new FlowLayout());
		j1.add(new JLabel(SystemConstants.OPC_STRING));
		onePageCount = new JTextField(sysconf.getOnePageCount());
		j1.add(onePageCount);
		sysCommon.add(j1);
		JPanel j2 = new JPanel(new FlowLayout());
		j2.add(new JLabel(SystemConstants.DEFAULT_CALC_ODDS));
		calcOdds = new JComboBox();
		j2.add(calcOdds);
		sysCommon.add(j2);

		useAvgOdd = new JCheckBox(SystemConstants.USE_AVG_ODDS, true);
		showFilterProcess = new JCheckBox(SystemConstants.SHOW_FILTER_PROCESS,
				false);
		autoCheckQh = new JCheckBox(SystemConstants.AUTO_CHECK_QH, sysconf
				.isAutoCheckQihao());
		autoSaveConf = new JCheckBox(SystemConstants.AUTO_SAVE_CONF, sysconf
				.isAutoSave());
		autoUpdateData = new JCheckBox(SystemConstants.AUTO_UPDATE_DATA,
				sysconf.isAutoUpdate());
		autoUpdateSoft = new JCheckBox(SystemConstants.AUTO_UPDATE_SOFT,
				sysconf.isAutoUpdateSoft());
	}
}
