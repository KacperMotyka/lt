/**
 * @文件名称: LayoutDemo.java
 * @类路径:   com.rb.cxf.ui
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-13 上午10:42:30
 * @版本:     1.0.0
 */
package com.rb.lottery.client.demo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @类功能说明: JAVA GUI布局实例
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-10-13 上午10:42:30
 * @版本: 1.0.0
 */

public class LayoutDemo extends JFrame implements ActionListener {

    /**
     * @Fields serialVersionUID: TODO
     */
    private static final long serialVersionUID = -1451124820704966541L;
    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    private static final String[] grid = { "网0", "格1", "布2", "局3", "管4", "理器5" };
    private JButton gridButton[] = new JButton[grid.length];
    private final String[] flow = { "流", "式", "布", "局", "管", "理", "器" };
    private JButton flowButton[] = new JButton[flow.length];
    private final String[] border = { "边界", "布局", "管", "理", "器" };
    private JButton borderButton[] = new JButton[border.length];

    JButton bt = new JButton("卡片切换");
    JButton bt1 = new JButton("网格包布局");

    public LayoutDemo() {
        super();
        init();
        this.setBackground(Color.YELLOW);
        this.setTitle("布局管理器综合实例");
        this.setLocation(500, 300);
        this.pack();
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void init() {
        JPanel gridpanel = new JPanel();
        gridpanel.setLayout(new GridLayout(2, 3, 3, 3));
        for (int i = 0; i < grid.length; i++) {
            gridButton[i] = new JButton(grid[i]);
            gridpanel.add(gridButton[i]);
            gridButton[i].setForeground(Color.BLUE);
        }

        JPanel flowpanel = new JPanel();
        flowpanel.setLayout(new FlowLayout());
        for (int i = 0; i < flow.length; i++) {
            flowButton[i] = new JButton(flow[i]);
            flowpanel.add(flowButton[i]);
            flowButton[i].setForeground(Color.RED);
        }

        JPanel borderpanel = new JPanel();
        borderpanel.setLayout(new BorderLayout());
        for (int i = 0; i < border.length; i++) {
            borderButton[i] = new JButton(border[i]);
            borderButton[i].setForeground(Color.CYAN);
        }
        borderpanel.add("North", borderButton[0]);
        borderpanel.add("East", borderButton[1]);
        borderpanel.add("South", borderButton[2]);
        borderpanel.add("West", borderButton[3]);
        borderpanel.add("Center", borderButton[4]);

        JPanel cardpanel = new JPanel();
        cardpanel.setLayout(new BorderLayout());
        JPanel cp = new JPanel();
        bt.addActionListener(this);
        cp.add(bt);
        cardpanel.add("North", cp);
        JPanel p1 = new JPanel();
        p1.add(new JButton("Button 1"));
        p1.add(new JButton("Button 2"));
        p1.add(new JButton("Button 3"));
        JPanel p2 = new JPanel();
        p2.add(new TextField("textField", 20));

        JPanel cards = new JPanel();
        CardLayout layout = new CardLayout();
        cards.setLayout(layout);
        cards.add("panel with buttons", p1);
        cards.add("panel with textField", p2);
        cardpanel.add("Center", cards);

        JPanel gridbagpanel = new JPanel();
        gridbagpanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        gridbagpanel.add(new JButton("网1"), c);
        gridbagpanel.add(new JButton("格2"), c);
        gridbagpanel.add(new JButton("包3"), c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbagpanel.add(new JButton("布4"), c);
        c.weightx = 0.0;
        gridbagpanel.add(new JButton("局5"), c);
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridbagpanel.add(new JButton("管6"), c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbagpanel.add(new JButton("理7"), c);
        c.gridheight = 2;
        c.gridwidth = 1;
        c.weighty = 1.0;
        gridbagpanel.add(new JButton("器8"), c);
        c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 1;
        gridbagpanel.add(new JButton("器9"), c);
        gridbagpanel.add(new JButton("器0"), c);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("North", flowpanel);
        getContentPane().add("East", cardpanel);
        getContentPane().add("South", gridpanel);
        getContentPane().add("West", gridbagpanel);
        getContentPane().add("Center", borderpanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    public static void main(String args[]) {
        LayoutDemo demo = new LayoutDemo();
        demo.setVisible(true);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
