/**
 * @文件名称: LotteryBasketPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午05:31:06
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.rb.lottery.client.UI.table.DataTableCellRenderer;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;


/**
 * @类功能说明: 
 * @类修改者:   
 * @修改日期:   
 * @修改说明:   
 * @作者:       robin
 * @创建时间:   2011-11-13 下午05:31:06
 * @版本:       1.0.0
 */

public class LotteryBasketPanel extends JPanel {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 372009290121940937L;

	private JPanel basketMenu;
	private JButton basketMenu1;
	private JButton basketMenu2;
	private JButton basketMenu3;
	private JButton basketMenu4;
	private JButton basketMenu5;
	private JButton basketMenu6;
	private JButton basketMenu7;
	private JButton basketMenu8;
	private JButton basketMenu9;
	private JButton basketMenu10;
	private JScrollPane basketData;
	private JTable basketTable;
	private JPanel baskePage;
	private JButton firstPage;
	private JButton previousPage;
	private JButton nextPage;
	private JButton lastPage;
	private JButton gotoPage;
	private JTextField gotoPageText;
	private JLabel pageCount;
	
	public LotteryBasketPanel() {
		super();
		init();
	}
	
	public LotteryBasketPanel(LayoutManager layout) {
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
		this.setPreferredSize(new java.awt.Dimension(300, 342));
		this.setBorder(new EtchedBorder());

		initBasketMenuPane();
		this.add(basketMenu);

		initBasketDataPane();
		this.add(basketData);

		initBasketPagePane();
		this.add(baskePage);
	}
	
	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void initBasketMenuPane() {
		basketMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
		basketMenu.setPreferredSize(new java.awt.Dimension(300, 26));

		basketMenu1 = new JButton(new ImageIcon(
				FilePathConstants.BASKET_MENU1_IMG));
		basketMenu1.setPreferredSize(new java.awt.Dimension(24, 20));
		basketMenu.add(basketMenu1);
		basketMenu2 = new JButton(new ImageIcon(
				FilePathConstants.BASKET_MENU2_IMG));
		basketMenu2.setPreferredSize(new java.awt.Dimension(24, 20));
		basketMenu.add(basketMenu2);
		basketMenu3 = new JButton(new ImageIcon(
				FilePathConstants.BASKET_MENU3_IMG));
		basketMenu3.setPreferredSize(new java.awt.Dimension(24, 20));
		basketMenu.add(basketMenu3);
		basketMenu4 = new JButton(new ImageIcon(
				FilePathConstants.BASKET_MENU4_IMG));
		basketMenu4.setPreferredSize(new java.awt.Dimension(24, 20));
		basketMenu.add(basketMenu4);
		basketMenu5 = new JButton(new ImageIcon(
				FilePathConstants.BASKET_MENU5_IMG));
		basketMenu5.setPreferredSize(new java.awt.Dimension(24, 20));
		basketMenu.add(basketMenu5);
		basketMenu6 = new JButton(new ImageIcon(
				FilePathConstants.BASKET_MENU6_IMG));
		basketMenu6.setPreferredSize(new java.awt.Dimension(24, 20));
		basketMenu.add(basketMenu6);
		basketMenu7 = new JButton(new ImageIcon(
				FilePathConstants.BASKET_MENU7_IMG));
		basketMenu7.setPreferredSize(new java.awt.Dimension(24, 20));
		basketMenu.add(basketMenu7);
		basketMenu8 = new JButton(new ImageIcon(
				FilePathConstants.BASKET_MENU8_IMG));
		basketMenu8.setPreferredSize(new java.awt.Dimension(24, 20));
		basketMenu.add(basketMenu8);
		basketMenu9 = new JButton(new ImageIcon(
				FilePathConstants.BASKET_MENU9_IMG));
		basketMenu9.setPreferredSize(new java.awt.Dimension(24, 20));
		basketMenu.add(basketMenu9);
		basketMenu10 = new JButton(new ImageIcon(
				FilePathConstants.BASKET_MENU10_IMG));
		basketMenu10.setPreferredSize(new java.awt.Dimension(24, 20));
		basketMenu.add(basketMenu10);
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void initBasketDataPane() {
		basketTable = new JTable();
		DefaultTableModel deft = (DefaultTableModel) basketTable.getModel();
		basketTable.setModel(deft);
		deft.addColumn(SystemConstants.INDEX);
		deft.addColumn(SystemConstants.DEFAULT_BASKET_DATA);

		// 单元格显示
		DataTableCellRenderer render = new DataTableCellRenderer();
		render.setHorizontalAlignment(JLabel.LEFT);
		render.setVerticalAlignment(JLabel.CENTER);
		basketTable.setDefaultRenderer(Object.class, render);

		// 列管理
		TableColumnModel colModel = basketTable.getColumnModel();
		TableColumn column = colModel.getColumn(0);
		column.setPreferredWidth(80);
		basketData = new JScrollPane(basketTable);
		basketData.setPreferredSize(new java.awt.Dimension(300, 260));
		basketData.setBackground(Color.WHITE);
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	private void initBasketPagePane() {
		baskePage = new JPanel(new FlowLayout(FlowLayout.LEFT + 20));
		baskePage.setPreferredSize(new java.awt.Dimension(300, 30));

		firstPage = new JButton(SystemConstants.FIRST_PAGE);
		firstPage.setPreferredSize(new java.awt.Dimension(30, 30));
		firstPage.setBorder(null);
		baskePage.add(firstPage);
		previousPage = new JButton(SystemConstants.PREVIOUS_PAGE);
		previousPage.setPreferredSize(new java.awt.Dimension(30, 30));
		previousPage.setBorder(null);
		baskePage.add(previousPage);
		nextPage = new JButton(SystemConstants.NEXT_PAGE);
		nextPage.setPreferredSize(new java.awt.Dimension(30, 30));
		nextPage.setBorder(null);
		baskePage.add(nextPage);
		lastPage = new JButton(SystemConstants.LAST_PAGE);
		lastPage.setPreferredSize(new java.awt.Dimension(30, 30));
		lastPage.setBorder(null);
		baskePage.add(lastPage);
		gotoPage = new JButton(SystemConstants.GOTO);
		gotoPage.setPreferredSize(new java.awt.Dimension(30, 30));
		gotoPage.setForeground(Color.BLUE);
		gotoPage.setBorder(null);
		baskePage.add(gotoPage);
		gotoPageText = new JTextField();
		gotoPageText.setPreferredSize(new java.awt.Dimension(30, 30));
		gotoPageText.setBorder(null);
		baskePage.add(gotoPageText);
		baskePage.add(new JLabel(SystemConstants.SLASH));
		pageCount = new JLabel(SystemConstants.ZERO_STRING);
		baskePage.add(pageCount);
		baskePage.add(new JLabel(SystemConstants.PAGE));
	}
}
