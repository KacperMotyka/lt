package com.rb.lottery.test;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.rb.lottery.datatable.DefaultGroup;
import com.rb.lottery.datatable.GroupableTableHeader;

/** *//**
 * 合并列头测试
 */
@SuppressWarnings("serial")
public class GroupableTableHeaderTest extends JFrame {

    /** *//**
     * @param args
     */
    public static void main(String[] args) {
        GroupableTableHeaderTest test = new GroupableTableHeaderTest();
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.setSize(800, 600);
        test.setVisible(true);
    }

    private DefaultTableModel tableModel = new DefaultTableModel() {

        /**//*
         * （非 Javadoc）
         * 
         * @see javax.swing.table.DefaultTableModel#getColumnCount()
         */
        @Override
        public int getColumnCount() {
            return 6;
        }

        /**//*
         * （非 Javadoc）
         * 
         * @see javax.swing.table.DefaultTableModel#getRowCount()
         */
        @Override
        public int getRowCount() {
            return 2;
        }

    };

    private JTable table = new JTable(tableModel);

    private JScrollPane scroll = new JScrollPane(table);

    /** *//**
     * @throws HeadlessException
     */
    public GroupableTableHeaderTest() throws HeadlessException {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        GroupableTableHeader tableHeader = new GroupableTableHeader();
        table.setTableHeader(tableHeader);

        DefaultGroup group = new DefaultGroup();
        group.setRow(0);
        group.setRowSpan(2);
        group.setColumn(0);
        group.setHeaderValue("楼层");
        tableHeader.addGroup(group);

        group = new DefaultGroup();
        group.setRow(0);
        group.setRowSpan(2);
        group.setColumn(1);
        group.setHeaderValue("水平/垂直系数");
        tableHeader.addGroup(group);

        group = new DefaultGroup();
        group.setRow(0);
        group.setColumn(2);
        group.setColumnSpan(2);
        group.setHeaderValue("A & B");
        tableHeader.addGroup(group);

        group = new DefaultGroup();
        group.setRow(1);
        group.setColumn(2);
        group.setHeaderValue("Column A");
        tableHeader.addGroup(group);

        group = new DefaultGroup();
        group.setRow(1);
        group.setColumn(3);
        group.setHeaderValue("Column B");
        tableHeader.addGroup(group);

        group = new DefaultGroup();
        group.setRow(0);
        group.setColumn(4);
        group.setColumnSpan(2);
        group.setHeaderValue("C & D");
        tableHeader.addGroup(group);

        group = new DefaultGroup();
        group.setRow(1);
        group.setColumn(4);
        group.setHeaderValue("Column C");
        tableHeader.addGroup(group);

        group = new DefaultGroup();
        group.setRow(1);
        group.setColumn(5);
        group.setHeaderValue("Column D");
        tableHeader.addGroup(group);

        getContentPane().add(scroll, BorderLayout.CENTER);
    }
}
