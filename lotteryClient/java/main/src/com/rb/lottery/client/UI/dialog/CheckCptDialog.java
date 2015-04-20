/**
 * @文件名称: CheckCptDialog.java
 * @类路径:   com.rb.lottery.UI.dialog
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-24 下午09:34:54
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rb.lottery.client.UI.listener.CheckCptListener;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemProcessor;
import com.rb.lottery.user.UserSfQa;

/**
 * @类功能说明: 密保验证（密码修改，密保修改，支付密码修改时验证）
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-24 下午09:34:54
 * @版本: 1.0.0
 */

public class CheckCptDialog extends BasicDialog {

    /**
     * @Fields serialVersionUID: TODO
     */
    private static final long serialVersionUID = 1329278908251092122L;
    private static final String FRAME_TITLE = "验证密保";
    private static final String REFRESH = "刷新";
    private static CheckCptDialog ccptDialog = null;
    private int qasz;
    private String nextTo; // 下个跳转页面

    private JPanel cpanel;
    private JLabel[] questions;
    private JTextField[] answers;
    private JLabel[] ansStatus;
    private JButton refresh;

    public static CheckCptDialog getInstance() {
        return ccptDialog;
    }

    public static CheckCptDialog getInstance(List<UserSfQa> qas, String next) {
        if (ccptDialog == null) {
            ccptDialog = new CheckCptDialog(qas, next);
        }
        return ccptDialog;
    }

    private CheckCptDialog(List<UserSfQa> qas, String next) {
        cpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        qasz = qas.size();
        nextTo = next;
        condition_flag = new boolean[qasz];
        questions = new JLabel[qasz];
        answers = new JTextField[qasz];
        ansStatus = new JLabel[qasz];
        for (int i = 0; i < qasz; i++) {
            condition_flag[i] = false;
            JLabel jql = new JLabel("问题" + (i + 1) + "：", JLabel.RIGHT);
            jql.setPreferredSize(new Dimension(80, 20));
            cpanel.add(jql);
            questions[i] = new JLabel(qas.get(i).getQuestion(), JLabel.LEFT);
            questions[i].setPreferredSize(new Dimension(200, 20));
            cpanel.add(questions[i]);
            JLabel jem = new JLabel();
            jem.setPreferredSize(new Dimension(100, 20));
            cpanel.add(jem);
            JLabel jqa = new JLabel("回答" + (i + 1) + "：", JLabel.RIGHT);
            jqa.setPreferredSize(new Dimension(80, 20));
            cpanel.add(jqa);
            answers[i] = new JTextField();
            answers[i].setPreferredSize(new Dimension(200, 20));
            answers[i].getDocument().addDocumentListener(new CheckCptListener("question" + i));
            cpanel.add(answers[i]);
            ansStatus[i] = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
            ansStatus[i].setPreferredSize(new Dimension(100, 20));
            ansStatus[i].setForeground(Color.RED);
            cpanel.add(ansStatus[i]);
            JLabel je = new JLabel();
            je.setPreferredSize(new Dimension(420, 25));
            cpanel.add(je);
        }
        // 刷新&下一步&取消
        JLabel jle = new JLabel();
        jle.setPreferredSize(new Dimension(60, 25));
        cpanel.add(jle);
        refresh = new JButton(REFRESH);
        refresh.setPreferredSize(new Dimension(80, 25));
        refresh.addActionListener(new CheckCptListener(CommandConstants.CMD_REFRESH));
        cpanel.add(refresh);
        commit = new JButton(SystemConstants.NEXT);
        commit.setPreferredSize(new Dimension(80, 25));
        commit.setEnabled(false);
        commit.addActionListener(new CheckCptListener(CommandConstants.CMD_NEXT));
        cpanel.add(commit);
        cancel = new JButton(SystemConstants.CANCEL);
        cancel.setPreferredSize(new Dimension(80, 25));
        cancel.addActionListener(new CheckCptListener(CommandConstants.CMD_CANCEL));
        cpanel.add(cancel);

        this.add(cpanel);
        // 设置表单验证模式
        this.condition_type = BOTH_CONDITION;
        this.setTitle(FRAME_TITLE);
        Image image = Toolkit.getDefaultToolkit().getImage(FilePathConstants.LOTTERY_IMG_FILE);
        this.setIconImage(image);
        // Point location = MainFrame.getInstance().getPasswayButtonLocation();
        this.setLocation(400, 50);
        this.setPreferredSize(new java.awt.Dimension(420, 180 + 60 * qasz));
        this.setUndecorated(false);
        this.pack();
        this.addWindowListener(new CheckCptListener());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // MainFrame.getInstance().setFrameEnable(false);
        // this.setVisible(true);
    }

    /**
     * @方法说明: 修改状态
     * @参数: @param component
     * @参数: @param text
     * @参数: @param c
     * @return void
     * @throws
     */
    @Override
    public void changeStatus(String component, String text, Color c) {
        int cmdlen = component.length();
        String dcmd = component.substring(0, cmdlen - 1);
        int index = Integer.parseInt(component.substring(cmdlen - 1));
        if (dcmd.equals("question")) {
            ansStatus[index].setText(text);
            ansStatus[index].setForeground(c);
            return;
        }
    }

    /**
     * @方法说明: 刷新密保问题
     * @参数:
     * @return void
     * @throws
     */
    public void refreshQuestion() {
        List<UserSfQa> safeqas = SystemProcessor.getRondomSafeQas();
        int sz = safeqas.size();
        Random rand = new Random();
        int ascdes = Math.abs(rand.nextInt()) % 2;
        if (0 == ascdes) {
            for (int i = 0; i < sz; i++) {
                questions[i].setText(safeqas.get(sz - i - 1).getQuestion());
            }
        } else {
            for (int i = 0; i < sz; i++) {
                questions[i].setText(safeqas.get(i).getQuestion());
            }
        }
    }

    /**
     * @return nextTo
     */
    public String getNextTo() {
        return nextTo;
    }

    /**
     * @param nextTo
     *            nextTo
     */
    public void setNextTo(String nextTo) {
        this.nextTo = nextTo;
    }

    /**
     * @方法说明:获取密保验证
     * @参数: @return
     * @return List<UserSfQa>
     * @throws
     */
    public List<UserSfQa> getSafeQas() {
        List<UserSfQa> sfqas = new ArrayList<UserSfQa>();
        for (int i = 0; i < qasz; i++) {
            UserSfQa qa = new UserSfQa();
            qa.setUser(SystemCache.currentUser);
            qa.setQuestion(questions[i].getText());
            qa.setAnswer(answers[i].getText());
            sfqas.add(qa);
        }
        return sfqas;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rb.lottery.UI.dialog.BasicDialog#destroy()
     */
    @Override
    public void destroy() {
        this.dispose();
        ccptDialog = null;
    }

}
