/**
 * @文件名称: ChangePwdDialog.java
 * @类路径:   com.rb.lottery.UI.dialog
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-23 下午03:54:40
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

import com.rb.lottery.client.UI.listener.ChangePwdListener;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明:密码修改窗口
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-23 下午03:54:40
 * @版本: 1.0.0
 */

public class ChangePwdDialog extends BasicDialog {

    /**
     * @Fields serialVersionUID: TODO
     */
    private static final long serialVersionUID = 5441569349018667979L;

    private static ChangePwdDialog cpdDialog = null;
    private static final String FRAME_TITLE = "密码修改";
    private static final String OLD_PWD = "原始密码：";
    private static final String NEW_PWD = "新密码：";
    private static final String NEW_PWD_CONFIRM = "新密码确认：";
    private static final String CHANGE = "修改";

    private JPanel cpanel;
    private JPasswordField oldPwd;
    private JLabel opdStatus;
    private JPasswordField newPwd;
    private JLabel npdStatus;
    private JPasswordField newPwdConfirm;
    private JLabel npdcfStatus;

    /**
     * @方法说明:
     * @参数: @return
     * @return LoginDialog
     * @throws
     */
    public static ChangePwdDialog getInstance() {
        if (cpdDialog == null) {
            cpdDialog = new ChangePwdDialog();
        }
        return cpdDialog;
    }

    private ChangePwdDialog() {
        cpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // 原始密码
        JLabel j1 = new JLabel(OLD_PWD, JLabel.RIGHT);
        j1.setPreferredSize(new Dimension(100, 25));
        cpanel.add(j1);
        oldPwd = new JPasswordField();
        oldPwd.setPreferredSize(new Dimension(160, 20));
        oldPwd.getDocument().addDocumentListener(new ChangePwdListener(CommandConstants.CMD_OLD_PWD));
        cpanel.add(oldPwd);
        opdStatus = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
        opdStatus.setPreferredSize(new Dimension(110, 20));
        opdStatus.setForeground(Color.RED);
        cpanel.add(opdStatus);
        // 新密码
        JLabel j2 = new JLabel(NEW_PWD, JLabel.RIGHT);
        j2.setPreferredSize(new Dimension(100, 20));
        cpanel.add(j2);
        newPwd = new JPasswordField();
        // password.setEchoChar('*');
        newPwd.setPreferredSize(new Dimension(160, 20));
        newPwd.getDocument().addDocumentListener(new ChangePwdListener(CommandConstants.CMD_NEW_PWD));
        cpanel.add(newPwd);
        npdStatus = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
        npdStatus.setPreferredSize(new Dimension(110, 20));
        npdStatus.setForeground(Color.RED);
        cpanel.add(npdStatus);
        // 新确认密码
        JLabel j3 = new JLabel(NEW_PWD_CONFIRM, JLabel.RIGHT);
        j3.setPreferredSize(new Dimension(100, 20));
        cpanel.add(j3);
        newPwdConfirm = new JPasswordField();
        newPwdConfirm.setPreferredSize(new Dimension(160, 20));
        newPwdConfirm.getDocument().addDocumentListener(new ChangePwdListener(CommandConstants.CMD_NEW_PWD_CONFIRM));
        cpanel.add(newPwdConfirm);
        npdcfStatus = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
        npdcfStatus.setPreferredSize(new Dimension(110, 20));
        npdcfStatus.setForeground(Color.RED);
        cpanel.add(npdcfStatus);
        // 登录&取消
        JLabel je = new JLabel();
        je.setPreferredSize(new Dimension(80, 25));
        cpanel.add(je);
        commit = new JButton(CHANGE);
        commit.setEnabled(false);
        commit.setPreferredSize(new Dimension(80, 25));
        commit.addActionListener(new ChangePwdListener(CommandConstants.CMD_CHANGEPASSWORD));
        cpanel.add(commit);
        cancel = new JButton(SystemConstants.CANCEL);
        cancel.setPreferredSize(new Dimension(80, 25));
        cancel.addActionListener(new ChangePwdListener(CommandConstants.CMD_CANCEL));
        cpanel.add(cancel);

        this.add(cpanel);
        // 设置表单验证模式
        this.condition_type = BOTH_CONDITION;
        condition_flag = new boolean[3];
        condition_flag[0] = false;
        condition_flag[1] = false;
        condition_flag[2] = false;
        this.setTitle(FRAME_TITLE);
        Image image = Toolkit.getDefaultToolkit().getImage(FilePathConstants.LOTTERY_IMG_FILE);
        this.setIconImage(image);
        // Point location = MainFrame.getInstance().getPasswayButtonLocation();
        this.setLocation(400, 280);
        this.setPreferredSize(new java.awt.Dimension(400, 170));
        this.setUndecorated(false);
        this.pack();
        // MainFrame.getInstance().setFrameEnable(false);
        // this.addKeyListener(new LoginListener());
        // this.getRootPane().setDefaultButton(commit);
        this.addWindowListener(new ChangePwdListener());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // this.setVisible(true);
    }

    /**
     * @方法说明:
     * @参数: @param cmdOldPwd
     * @参数: @param enterOldPwd
     * @参数: @param red
     * @return void
     * @throws
     */
    @Override
    public void changeStatus(String component, String text, Color c) {
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
     * @方法说明: 获取原始密码
     * @参数: @return
     * @return String
     * @throws
     */
    public String getOldPassword() {
        String oldpwd = new String(oldPwd.getPassword());
        return oldpwd;
    }

    /**
     * @方法说明:获取新密码
     * @参数: @return
     * @return String
     * @throws
     */
    public String getNewPassword() {
        String newpwd = new String(newPwd.getPassword());
        return newpwd;
    }

    /**
     * @方法说明: 清空
     * @参数:
     * @return void
     * @throws
     */
    public void clear() {
        oldPwd.setText("");
        newPwd.setText("");
        newPwdConfirm.setText("");
        commit.setEnabled(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rb.lottery.UI.dialog.BasicDialog#destroy()
     */
    @Override
    public void destroy() {
        this.dispose();
        cpdDialog = null;
    }
}
