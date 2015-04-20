/**
 * @文件名称: PayPasswordDialog.java
 * @类路径:   com.rb.lottery.UI.dialog
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-24 下午04:20:29
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.rb.lottery.client.UI.listener.PayPasswordListener;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.system.SystemProcessor;

/**
 * @类功能说明:支付密码设置窗口
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-24 下午04:20:29
 * @版本: 1.0.0
 */

public class PayPasswordDialog extends BasicDialog {

    /**
     * @Fields serialVersionUID: TODO
     */
    private static final long serialVersionUID = 1553076116491004930L;
    private static PayPasswordDialog ppdDialog = null;
    private static final String FRAME_TITLE = "支付密码设置";
    private static final String ACT_PWD = "原帐户密码：";
    private static final String OLD_PWD = "原支付密码：";
    private static final String NEW_PWD = "新支付密码：";
    private static final String NEW_PWD_CONFIRM = "新密码确认：";
    private static final String COMMIT = "提交";

    private JPanel cpanel;
    private JPasswordField actPwd;
    private JLabel actStatus;
    private JLabel oldLab;
    private JPasswordField oldPwd;
    private JLabel opdStatus;
    private JPasswordField newPwd;
    private JLabel npdStatus;
    private JPasswordField newPwdConfirm;
    private JLabel npdcfStatus;

    public static PayPasswordDialog getInstance() {
        if (ppdDialog == null) {
            ppdDialog = new PayPasswordDialog();
        }
        return ppdDialog;
    }

    private PayPasswordDialog() {
        cpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // 原始帐户密码
        JLabel j1 = new JLabel(ACT_PWD, JLabel.RIGHT);
        j1.setPreferredSize(new Dimension(100, 25));
        cpanel.add(j1);
        actPwd = new JPasswordField();
        actPwd.setPreferredSize(new Dimension(160, 20));
        actPwd.getDocument().addDocumentListener(new PayPasswordListener(CommandConstants.CMD_ACT_PWD));
        cpanel.add(actPwd);
        actStatus = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
        actStatus.setPreferredSize(new Dimension(110, 20));
        actStatus.setForeground(Color.RED);
        cpanel.add(actStatus);
        // 原始支付密码
        oldLab = new JLabel(OLD_PWD, JLabel.RIGHT);
        oldLab.setPreferredSize(new Dimension(100, 25));
        cpanel.add(oldLab);
        oldPwd = new JPasswordField();
        oldPwd.setPreferredSize(new Dimension(160, 20));
        oldPwd.getDocument().addDocumentListener(new PayPasswordListener(CommandConstants.CMD_OLD_PWD));
        cpanel.add(oldPwd);
        opdStatus = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
        opdStatus.setPreferredSize(new Dimension(110, 20));
        opdStatus.setForeground(Color.RED);
        cpanel.add(opdStatus);
        // 新支付密码
        JLabel j3 = new JLabel(NEW_PWD, JLabel.RIGHT);
        j3.setPreferredSize(new Dimension(100, 20));
        cpanel.add(j3);
        newPwd = new JPasswordField();
        // password.setEchoChar('*');
        newPwd.setPreferredSize(new Dimension(160, 20));
        newPwd.getDocument().addDocumentListener(new PayPasswordListener(CommandConstants.CMD_NEW_PWD));
        cpanel.add(newPwd);
        npdStatus = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
        npdStatus.setPreferredSize(new Dimension(110, 20));
        npdStatus.setForeground(Color.RED);
        cpanel.add(npdStatus);
        // 新支付确认密码
        JLabel j4 = new JLabel(NEW_PWD_CONFIRM, JLabel.RIGHT);
        j4.setPreferredSize(new Dimension(100, 20));
        cpanel.add(j4);
        newPwdConfirm = new JPasswordField();
        newPwdConfirm.setPreferredSize(new Dimension(160, 20));
        newPwdConfirm.getDocument().addDocumentListener(new PayPasswordListener(CommandConstants.CMD_NEW_PWD_CONFIRM));
        cpanel.add(newPwdConfirm);
        npdcfStatus = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
        npdcfStatus.setPreferredSize(new Dimension(110, 20));
        npdcfStatus.setForeground(Color.RED);
        cpanel.add(npdcfStatus);
        // 登录&取消
        JLabel je = new JLabel();
        je.setPreferredSize(new Dimension(80, 25));
        cpanel.add(je);
        commit = new JButton(COMMIT);
        commit.setEnabled(false);
        commit.setPreferredSize(new Dimension(80, 25));
        commit.addActionListener(new PayPasswordListener(CommandConstants.CMD_CHANGE_PAYPASSWORD));
        cpanel.add(commit);
        cancel = new JButton(SystemConstants.CANCEL);
        cancel.setPreferredSize(new Dimension(80, 25));
        cancel.addActionListener(new PayPasswordListener(CommandConstants.CMD_CANCEL));
        cpanel.add(cancel);

        this.add(cpanel);
        // 设置表单验证模式
        this.condition_type = BOTH_CONDITION;
        condition_flag = new boolean[4];
        condition_flag[0] = false;
        condition_flag[1] = false;
        if (!SystemProcessor.hasPayPassword()) {
            condition_flag[1] = true;
            oldLab.setVisible(false);
            oldPwd.setVisible(false);
            opdStatus.setVisible(false);
        }

        condition_flag[2] = false;
        condition_flag[3] = false;
        this.setTitle(FRAME_TITLE);
        Image image = Toolkit.getDefaultToolkit().getImage(FilePathConstants.LOTTERY_IMG_FILE);
        this.setIconImage(image);
        // Point location = MainFrame.getInstance().getPasswayButtonLocation();
        this.setLocation(400, 280);
        this.setPreferredSize(new java.awt.Dimension(400, 200));
        this.setUndecorated(false);
        this.pack();
        // MainFrame.getInstance().setFrameEnable(false);
        // this.addKeyListener(new LoginListener());
        // this.getRootPane().setDefaultButton(commit);
        this.addWindowListener(new PayPasswordListener());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // this.setVisible(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rb.lottery.UI.dialog.BasicDialog#destroy()
     */
    @Override
    public void destroy() {
        this.dispose();
        ppdDialog = null;
    }

    /**
     * @方法说明:
     * @参数: @param component
     * @参数: @param text
     * @参数: @param red
     * @return void
     * @throws
     */
    @Override
    public void changeStatus(String component, String text, Color c) {
        if (component.equals(CommandConstants.CMD_ACT_PWD)) {
            actStatus.setText(text);
            actStatus.setForeground(c);
            return;
        }
        if (component.equals(CommandConstants.CMD_OLD_PWD)) {
            opdStatus.setText(text);
            opdStatus.setForeground(c);
            return;
        }
        if (component.equals(CommandConstants.CMD_NEW_PWD)) {
            npdStatus.setText(text);
            npdStatus.setForeground(c);
            return;
        }
        if (component.equals(CommandConstants.CMD_NEW_PWD_CONFIRM)) {
            npdcfStatus.setText(text);
            npdcfStatus.setForeground(c);
            return;
        }
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getNewPassword() {
        String newpwd = new String(newPwd.getPassword());
        return newpwd;
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getOldPassword() {
        String oldpwd = new String(oldPwd.getPassword());
        return oldpwd;
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getActPassword() {
        String actpwd = new String(actPwd.getPassword());
        return actpwd;
    }

    /**
     * @方法说明:清除
     * @参数:
     * @return void
     * @throws
     */
    public void clear() {
        actPwd.setText("");
        oldPwd.setText("");
        newPwd.setText("");
        newPwdConfirm.setText("");
        commit.setEnabled(false);
    }

}
