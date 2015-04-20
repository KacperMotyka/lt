/**
 * @文件名称: LotteryQuickMenuPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午12:04:15
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 主界面=>快速菜单面板
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午12:04:15
 * @版本:       1.0.0
 */

public class LotteryQuickMenuPanel extends JPanel{

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -2209017259350402610L;

	private JButton proj;
	private JButton save;
	private JButton abtn;
	private JButton bbtn;
	private JButton cbtn;
	private JButton dbtn;
	private JButton ebtn;
	private JButton fbtn;
	
	public LotteryQuickMenuPanel() {
		super();
		init();
	}
	
	public LotteryQuickMenuPanel(LayoutManager layout) {
		super(layout);
		init();
	}

	/**
	 * @方法说明: 
	 * @参数:     
	 * @return    void
	 * @throws
	 */
	private void init() {
		this.setBorder(new EtchedBorder());
		this.setPreferredSize(new java.awt.Dimension(938, 52));
		// this.setBackground(SystemConstants.QICKMENU_BG);

		ImageIcon pro_img = new ImageIcon(FilePathConstants.EXPORT_IMG_FILE);
		proj = new JButton(pro_img);
		proj.setBorder(null);
		proj.setText(SystemConstants.QUICKMENU_OPENPRO);
		this.add(proj);

		ImageIcon save_img = new ImageIcon(FilePathConstants.SAVE_IMG_FILE);
		save = new JButton(save_img);
		save.setBorder(null);
		save.setText(SystemConstants.QUICKMENU_SAVEPRO);
		this.add(save);

		JSeparator s1 = new JSeparator();
		s1.setPreferredSize(new java.awt.Dimension(2, 35));
		s1.setOrientation(SwingConstants.VERTICAL);
		this.add(s1);

		ImageIcon i1_img = new ImageIcon(FilePathConstants.DATAUPDATE_IMG_FILE);
		abtn = new JButton(i1_img);
		abtn.setBorder(null);
		abtn.setText(SystemConstants.QUICKMENU_DATAUPDATE);
		this.add(abtn);

		JSeparator s2 = new JSeparator();
		s2.setPreferredSize(new java.awt.Dimension(2, 35));
		s2.setOrientation(SwingConstants.VERTICAL);
		this.add(s2);

		ImageIcon i2_img = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
		bbtn = new JButton(i2_img);
		bbtn.setBorder(null);
		bbtn.setText(SystemConstants.QUICKMENU_DATATABLE);
		this.add(bbtn);

		ImageIcon i3_img = new ImageIcon(FilePathConstants.BETSCAT_IMG_FILE);
		cbtn = new JButton(i3_img);
		cbtn.setBorder(null);
		cbtn.setText(SystemConstants.QUICKMENU_BETSCAT);
		this.add(cbtn);

		ImageIcon i4_img = new ImageIcon(FilePathConstants.WINQUERY_IMG_FILE);
		dbtn = new JButton(i4_img);
		dbtn.setBorder(null);
		dbtn.setText(SystemConstants.QUICKMENU_WINQUERY);
		this.add(dbtn);

		JSeparator s3 = new JSeparator();
		s3.setPreferredSize(new java.awt.Dimension(2, 35));
		s3.setOrientation(SwingConstants.VERTICAL);
		this.add(s3);

		ImageIcon i5_img = new ImageIcon(FilePathConstants.BUY_IMG_FILE);
		ebtn = new JButton(i5_img);
		ebtn.setBorder(null);
		ebtn.setText(SystemConstants.QUICKMENU_BUY);
		this.add(ebtn);

		ImageIcon i6_img = new ImageIcon(FilePathConstants.STUDY_IMG_FILE);
		fbtn = new JButton(i6_img);
		fbtn.setBorder(null);
		fbtn.setText(SystemConstants.QUICKMENU_STUDY);
		this.add(fbtn);
	}
}
