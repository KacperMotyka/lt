/**
 * @文件名称: LotteryDataPanel.java
 * @类路径:   com.rb.lottery.UI.panel
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-13 下午03:01:14
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.panel;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.rb.lottery.client.UI.table.DataTableCellRenderer;
import com.rb.lottery.client.UI.table.DataTableModel;
import com.rb.lottery.client.UI.table.DataTableMouseAdapter;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.common.SystemFunctions;
import com.rb.lottery.datatable.DefaultGroup;
import com.rb.lottery.datatable.GroupableTableHeader;
import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemProcessor;

/**
 * @类功能说明: 主界面=>工作区=>投注区面板=>投注比赛数据面板
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-13 下午03:01:14
 * @版本: 1.0.0
 */

public class LotteryDataPanel extends JScrollPane {

    /**
     * @Fields serialVersionUID: TODO
     */
    private static final long serialVersionUID = -5356352901491754695L;

    private static final int TABLE_ROW_HEIGHT = 20;

    private JTable dataTable;
    private DataTableModel model;

    public LotteryDataPanel() {
        super();
        init();
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void init() {
        /*
         * int row = 0; int col = 7; if (type < 2) { row = 14; } else { row = SystemFunctions.getRowSize(data); }
         */
        model = new DataTableModel();
        dataTable = new JTable(model);
        dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        dataTable.setBorder(null);
        dataTable.setRowHeight(TABLE_ROW_HEIGHT);
        dataTable.setShowHorizontalLines(false);
        dataTable.setShowVerticalLines(false);
        dataTable.setSelectionBackground(Color.magenta);
        dataTable.setSelectionForeground(Color.pink);
        dataTable.addMouseListener(new DataTableMouseAdapter() {
            @Override
            public void mouseSingleClicked(MouseEvent e) {
                int rowIndex = dataTable.rowAtPoint(e.getPoint());
                int colIndex = dataTable.columnAtPoint(e.getPoint());
                if (colIndex > 6) {
                    checkCellBox(rowIndex, colIndex);
                }
            }
        });

        // 单元格显示
        DataTableCellRenderer render = new DataTableCellRenderer();
        render.setHorizontalAlignment(JLabel.CENTER);
        render.setVerticalAlignment(JLabel.CENTER);
        dataTable.setDefaultRenderer(Object.class, render);

        // 列管理
        TableColumnModel colModel = dataTable.getColumnModel();
        TableColumn column = colModel.getColumn(3);
        column.setPreferredWidth(240);
        colModel.getColumn(7).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        colModel.getColumn(8).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        colModel.getColumn(9).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        colModel.getColumn(10).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        colModel.getColumn(11).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        String[] columnName = { SystemConstants.GAME_ID, SystemConstants.ITEM_NAME, SystemConstants.PLAY_TIME,
                SystemConstants.VENUS, SystemConstants.BWIN, SystemConstants.WIN, SystemConstants.DRAW,
                SystemConstants.LOSS, SystemConstants.BETCHOOSE, SystemConstants.DAN_SET, SystemConstants.OPERATION };
        GroupableTableHeader tableHeader = new GroupableTableHeader();
        dataTable.setTableHeader(tableHeader);
        DefaultGroup group = new DefaultGroup();
        group.setLocation(0, 0, 2, 1);
        group.setHeaderValue(columnName[0]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 1, 2, 1);
        group.setHeaderValue(columnName[1]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 2, 2, 1);
        group.setHeaderValue(columnName[2]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 3, 2, 1);
        group.setHeaderValue(columnName[3]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 4, 1, 3);
        group.setHeaderValue(columnName[4]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(1, 4, 1, 1);
        group.setHeaderValue(columnName[5]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(1, 5, 1, 1);
        group.setHeaderValue(columnName[6]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(1, 6, 1, 1);
        group.setHeaderValue(columnName[7]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 7, 1, 3);
        group.setHeaderValue(columnName[8]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(1, 7, 1, 1);
        group.setHeaderValue(columnName[5]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(1, 8, 1, 1);
        group.setHeaderValue(columnName[6]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(1, 9, 1, 1);
        group.setHeaderValue(columnName[7]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 10, 2, 1);
        group.setHeaderValue(columnName[9]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 11, 2, 1);
        group.setHeaderValue(columnName[10]);
        tableHeader.addGroup(group);

        // initial GUI data
        initGUIData();

        this.add(dataTable);
        // dataPane.setLayout(new ScrollPaneLayout());
        this.setBorder(new EtchedBorder());
        this.setPreferredSize(new java.awt.Dimension(314, 294));
        this.setVisible(true);
    }

    /**
     * @方法说明: 初始化GUI数据
     * @参数:
     * @return void
     * @throws
     */
    @SuppressWarnings("unchecked")
    public void initGUIData() {
        model.clear();
        int type = SystemCache.currentType;
        TreeMap<String, List<BetMatch>> data = SystemProcessor.getMatchData();
        Object[] rowData = new Object[12];
        String vs = SystemConstants.EMPTY_STRING;
        if (type < 2) {
            Set keySet = data.keySet();
            Iterator iterator = keySet.iterator();
            List<BetMatch> matches = data.get(iterator.next());
            for (BetMatch match : matches) {
                rowData[0] = match.getMatchId();
                rowData[1] = match.getCompetition();
                rowData[2] = match.getTime();
                vs = match.getHomeRank()
                        + match.getHome()
                        + SystemConstants.BLANK_STRING
                        + SystemConstants.VS
                        + SystemConstants.BLANK_STRING
                        + match.getVisitor()
                        + match.getVistRank();
                rowData[3] = vs;
                rowData[4] = match.getWinOdds() + SystemConstants.EMPTY_STRING;
                rowData[5] = match.getDrawOdds() + SystemConstants.EMPTY_STRING;
                rowData[6] = match.getLossOdds() + SystemConstants.EMPTY_STRING;
                rowData[7] = new Boolean(false);
                rowData[8] = new Boolean(false);
                rowData[9] = new Boolean(false);
                rowData[10] = new Boolean(false);
                rowData[11] = new Boolean(false);
                model.addRow(rowData);
            }
        } else {
            Set keySet = data.keySet();
            Iterator iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String[] mondat = key.split(SystemConstants.PART);
                String period = mondat[0]
                        + SystemConstants.YEAR
                        + mondat[1]
                        + SystemConstants.MONTH
                        + mondat[2]
                        + SystemConstants.DATE
                        + SystemConstants.TIME_10PERIOD;
                rowData[0] = SystemConstants.EMPTY_STRING;
                rowData[1] = SystemConstants.EMPTY_STRING;
                rowData[2] = SystemConstants.EMPTY_STRING;
                rowData[3] = period;
                rowData[4] = SystemConstants.EMPTY_STRING;
                rowData[5] = SystemConstants.EMPTY_STRING;
                rowData[6] = SystemConstants.EMPTY_STRING;
                rowData[7] = new Boolean(false);
                rowData[8] = new Boolean(false);
                rowData[9] = new Boolean(false);
                rowData[10] = new Boolean(false);
                rowData[11] = new Boolean(false);
                model.addRow(rowData);
                List<BetMatch> matches = data.get(key);
                for (BetMatch match : matches) {
                    rowData[0] = match.getMatchId();
                    rowData[1] = match.getCompetition();
                    rowData[2] = match.getTime();
                    vs = match.getHomeRank()
                            + match.getHome()
                            + SystemConstants.LEFT_PAREBTHESE
                            + match.getConcede()
                            + SystemConstants.RIGHT_PAREBTHESE
                            + SystemConstants.BLANK_STRING
                            + SystemConstants.VS
                            + SystemConstants.BLANK_STRING
                            + match.getVisitor()
                            + match.getVistRank();
                    rowData[3] = vs;
                    rowData[4] = match.getWinOdds() + SystemConstants.EMPTY_STRING;
                    rowData[5] = match.getDrawOdds() + SystemConstants.EMPTY_STRING;
                    rowData[6] = match.getLossOdds() + SystemConstants.EMPTY_STRING;
                    rowData[7] = new Boolean(false);
                    rowData[8] = new Boolean(false);
                    rowData[9] = new Boolean(false);
                    rowData[10] = new Boolean(false);
                    rowData[11] = new Boolean(false);
                    // rowData[11] = new JButton(SystemConstants.ALL_BUTTON);
                    model.addRow(rowData);
                }
            }
        }
    }

    /**
     * @方法说明:
     * @参数: @param rowIndex
     * @参数: @param colIndex
     * @return void
     * @throws
     */
    public void checkCellBox(int rowIndex, int colIndex) {
        boolean flag = false;
        if (rowIndex == 0) {
            if (colIndex > 6) {
                if (colIndex == 11) {
                    checkAll();
                } else {
                    checkAllColumn(colIndex);
                }
                flag = true;
            }
            return;
        }
        if (colIndex > 6) {
            if (colIndex == 11) {
                checkAllRow(rowIndex);
            } else {
                checkCell(rowIndex, colIndex);
            }
            flag = true;
        }
        if (flag) {
            SystemProcessor.updateBetStatu();
        }
    }

    /**
     * @方法说明:
     * @参数: @param rowIndex
     * @参数: @param colIndex
     * @return void
     * @throws
     */
    public void checkCell(int rowIndex, int colIndex) {
        String id = (String) dataTable.getValueAt(rowIndex, 0);
        boolean noneCheck = isNoneCheck(rowIndex);
        if (noneCheck) {
            dataTable.setValueAt(new Boolean(false), rowIndex, 10);
        }
        if (!id.equals(SystemConstants.EMPTY_STRING)) {
            Boolean checked = (Boolean) dataTable.getValueAt(rowIndex, colIndex);
            if (checked.booleanValue() == false) {
                SystemProcessor.removeOneBet(id, SystemFunctions.convertToBet(colIndex));
            } else {
                SystemProcessor.addOneBet(id, SystemFunctions.convertToBet(colIndex));
            }
        }
    }

    /**
     * @方法说明:
     * @参数: @param rowIndex
     * @return void
     * @throws
     */
    public void checkAllRow(int rowIndex) {
        String id = (String) dataTable.getValueAt(rowIndex, 0);
        Boolean checked = (Boolean) dataTable.getValueAt(rowIndex, 11);
        if (checked.booleanValue() == false) {
            dataTable.setValueAt(checked, rowIndex, 10);
        }
        dataTable.setValueAt(checked, rowIndex, 7);
        dataTable.setValueAt(checked, rowIndex, 8);
        dataTable.setValueAt(checked, rowIndex, 9);
        if (!id.equals(SystemConstants.EMPTY_STRING)) {
            if (checked.booleanValue() == false) {
                SystemProcessor.removeOneBet(id, SystemFunctions.convertToBet(11));
            } else {
                SystemProcessor.addOneBet(id, SystemFunctions.convertToBet(11));
            }
        }
    }

    /**
     * @方法说明:
     * @参数: @param colIndex
     * @return void
     * @throws
     */
    public void checkAllColumn(int colIndex) {
        String id = SystemConstants.EMPTY_STRING;
        Boolean checked = (Boolean) dataTable.getValueAt(0, colIndex);
        int rows = dataTable.getRowCount();
        if (checked.booleanValue() == false) {
            for (int i = 1; i < rows; i++) {
                dataTable.setValueAt(checked, i, colIndex);
                boolean noneCheck = isNoneCheck(i);
                if (noneCheck) {
                    dataTable.setValueAt(checked, i, 10);
                }
                id = (String) dataTable.getValueAt(i, 0);
                if (!id.equals(SystemConstants.EMPTY_STRING)) {
                    SystemProcessor.removeOneBet(id, SystemFunctions.convertToBet(11));
                }
            }
        } else {
            for (int i = 1; i < rows; i++) {
                if (colIndex != 10 || !isNoneCheck(i)) {
                    dataTable.setValueAt(checked, i, colIndex);
                }
                id = (String) dataTable.getValueAt(i, 0);
                if (!id.equals(SystemConstants.EMPTY_STRING)) {
                    SystemProcessor.addOneBet(id, SystemFunctions.convertToBet(colIndex));
                }
            }
        }
    }

    /**
     * @方法说明:
     * @参数: @param colIndex
     * @return void
     * @throws
     */
    public void checkAll() {
        int rows = dataTable.getRowCount();
        for (int i = 0; i < rows; i++) {
            this.checkAllRow(i);
        }
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    private boolean isNoneCheck(int rowIndex) {
        Boolean checked1 = (Boolean) dataTable.getValueAt(rowIndex, 7);
        Boolean checked2 = (Boolean) dataTable.getValueAt(rowIndex, 8);
        Boolean checked3 = (Boolean) dataTable.getValueAt(rowIndex, 9);
        boolean result = !(checked1.booleanValue() || checked2.booleanValue() || checked3.booleanValue());
        return result;
    }
}
