package com.rb.lottery.client.demo;

import java.awt.BorderLayout;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation, company or business for any purpose whatever) then
 * you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of
 * Jigloo implies acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO
 * JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class MainFrameDemo extends javax.swing.JFrame {

    private JMenuItem helpMenuItem;
    private JMenu jMenu5;
    private JMenuItem jMenuItem1;
    private JSeparator jSeparator3;
    private JPanel jPanel1;
    private JMenuItem deleteMenuItem;
    private JSeparator jSeparator1;
    private JMenuItem pasteMenuItem;
    private JMenuItem copyMenuItem;
    private JMenuItem cutMenuItem;
    private JMenu jMenu4;
    private JMenuItem exitMenuItem;
    private JSeparator jSeparator2;
    private JMenuItem closeFileMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem openFileMenuItem;
    private JMenuItem newFileMenuItem;
    private JMenu jMenu3;
    private JMenuBar jMenuBar1;

    /**
     * Auto-generated main method to display this JFrame
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrameDemo inst = new MainFrameDemo();
                inst.setLocationRelativeTo(null);
                inst.setVisible(true);
            }
        });
    }

    public MainFrameDemo() {
        super();
        initGUI();
    }

    private void initGUI() {
        try {
            {
                jPanel1 = new JPanel();
                getContentPane().add(jPanel1, BorderLayout.NORTH);
                jPanel1.setPreferredSize(new java.awt.Dimension(558, 30));
                {
                    jSeparator3 = new JSeparator();
                    jPanel1.add(jSeparator3);
                    jSeparator3.setPreferredSize(new java.awt.Dimension(8, 22));
                    jSeparator3.setOrientation(SwingConstants.VERTICAL);
                }
            }
            this.setSize(566, 418);
            {
                jMenuBar1 = new JMenuBar();
                setJMenuBar(jMenuBar1);
                {
                    jMenu3 = new JMenu();
                    jMenuBar1.add(jMenu3);
                    jMenu3.setText("File");
                    {
                        newFileMenuItem = new JMenuItem();
                        jMenu3.add(newFileMenuItem);
                        newFileMenuItem.setText("New");
                    }
                    {
                        openFileMenuItem = new JMenuItem();
                        jMenu3.add(openFileMenuItem);
                        openFileMenuItem.setText("Open");
                    }
                    {
                        saveMenuItem = new JMenuItem();
                        jMenu3.add(saveMenuItem);
                        saveMenuItem.setText("Save");
                    }
                    {
                        saveAsMenuItem = new JMenuItem();
                        jMenu3.add(saveAsMenuItem);
                        saveAsMenuItem.setText("Save As ...");
                    }
                    {
                        closeFileMenuItem = new JMenuItem();
                        jMenu3.add(closeFileMenuItem);
                        closeFileMenuItem.setText("Close");
                    }
                    {
                        jSeparator2 = new JSeparator();
                        jMenu3.add(jSeparator2);
                    }
                    {
                        exitMenuItem = new JMenuItem();
                        jMenu3.add(exitMenuItem);
                        exitMenuItem.setText("Exit");
                    }
                }
                {
                    jMenu4 = new JMenu();
                    jMenuBar1.add(jMenu4);
                    jMenu4.setText("Edit");
                    {
                        cutMenuItem = new JMenuItem();
                        jMenu4.add(cutMenuItem);
                        cutMenuItem.setText("Cut");
                    }
                    {
                        copyMenuItem = new JMenuItem();
                        jMenu4.add(copyMenuItem);
                        copyMenuItem.setText("Copy");
                        {
                            jMenuItem1 = new JMenuItem();
                            copyMenuItem.add(jMenuItem1);
                            jMenuItem1.setText("jMenuItem1");
                        }
                    }
                    {
                        pasteMenuItem = new JMenuItem();
                        jMenu4.add(pasteMenuItem);
                        pasteMenuItem.setText("Paste");
                    }
                    {
                        jSeparator1 = new JSeparator();
                        jMenu4.add(jSeparator1);
                    }
                    {
                        deleteMenuItem = new JMenuItem();
                        jMenu4.add(deleteMenuItem);
                        deleteMenuItem.setText("Delete");
                    }
                }
                {
                    jMenu5 = new JMenu();
                    jMenuBar1.add(jMenu5);
                    jMenu5.setText("Help");
                    {
                        helpMenuItem = new JMenuItem();
                        jMenu5.add(helpMenuItem);
                        helpMenuItem.setText("Help");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
