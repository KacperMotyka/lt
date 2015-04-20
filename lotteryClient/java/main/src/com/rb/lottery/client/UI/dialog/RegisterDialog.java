/**
 * @文件名称: RegisterDialog.java
 * @类路径:   com.rb.lottery.UI.dialog
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-21 下午05:32:06
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.rb.gui.datechooser.DateChooser;
import com.rb.lottery.client.UI.listener.RegisterListener;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.FontConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明: 注册窗口
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-21 下午05:32:06
 * @版本: 1.0.0
 */

public class RegisterDialog extends BasicDialog {

    /**
     * @Fields serialVersionUID: TODO
     */
    private static final long serialVersionUID = -7010917912405123675L;
    private static RegisterDialog registerDialog = null;
    private static final String FRAME_TITLE = "注册";
    private static final int ALL_CONDITIONS = 19;
    private static JFrame dateFrame = null;
    private static DateChooser dateChooser = null;

    private JPanel rpanel;
    private JTextField username;
    private JLabel unStatus;
    private JPasswordField password;
    private JLabel pwdStatus;
    private JPasswordField passwordConfirm;
    private JLabel pwdcfStatus;
    private JTextField cname;
    private JLabel cnStatus;
    private JTextField ename;
    private JLabel enStatus;
    private JComboBox sex;
    private JLabel sxStatus;
    private JComboBox age;
    private JLabel agStatus;
    // private JTextField birthday;
    private DateChooser birthday;
    private JLabel btStatus;
    private JTextField birthplace;
    private JLabel bpStatus;
    private JTextField address;
    private JLabel adStatus;
    private JTextField post;
    private JLabel ptStatus;
    private JTextField email;
    private JLabel emStatus;
    private JTextField qq;
    private JLabel qqStatus;
    private JTextField phone;
    private JLabel phStatus;
    private JTextField mobile;
    private JLabel mbStatus;
    private JComboBox bloodtype;
    private JLabel blStatus;
    private JTextField weight;
    private JLabel wgStatus;
    private JTextField height;
    private JLabel hgStatus;
    private JCheckBox obey;

    public static RegisterDialog getInstance() {
        if (registerDialog == null) {
            registerDialog = new RegisterDialog();
        }
        return registerDialog;
    }

    private RegisterDialog() {
        rpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // 抬头信息
        JLabel ai = new JLabel(MessageConstants.ACCOUNT_INFO, JLabel.LEFT);
        ai.setPreferredSize(new Dimension(390, 20));
        ai.setForeground(Color.BLUE);
        ai.setFont(FontConstants.STANDARD_FONT);
        rpanel.add(ai);
        // 分隔符
        JSeparator sp = new JSeparator(JSeparator.HORIZONTAL);
        sp.setForeground(Color.BLUE);
        sp.setPreferredSize(new Dimension(380, 5));
        rpanel.add(sp);
        // 帐号
        JLabel j1 = new JLabel(SystemConstants.USERNAME, JLabel.RIGHT);
        j1.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j1);
        username = new JTextField();
        username.setPreferredSize(new Dimension(180, 20));
        username.getDocument().addDocumentListener(new RegisterListener(CommandConstants.CMD_USERNAME));
        rpanel.add(username);
        unStatus = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
        unStatus.setPreferredSize(new Dimension(100, 20));
        unStatus.setForeground(Color.RED);
        rpanel.add(unStatus);
        // 密码
        JLabel j2 = new JLabel(SystemConstants.PASSWORD, JLabel.RIGHT);
        j2.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j2);
        password = new JPasswordField();
        // password.setEchoChar('*');
        password.setPreferredSize(new Dimension(180, 20));
        password.getDocument().addDocumentListener(new RegisterListener(CommandConstants.CMD_PASSWORD));
        rpanel.add(password);
        pwdStatus = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
        pwdStatus.setPreferredSize(new Dimension(100, 20));
        pwdStatus.setForeground(Color.RED);
        rpanel.add(pwdStatus);
        // 确认密码
        JLabel j3 = new JLabel(SystemConstants.PASSWORD_CONFIRM, JLabel.RIGHT);
        j3.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j3);
        passwordConfirm = new JPasswordField();
        passwordConfirm.setPreferredSize(new Dimension(180, 20));
        passwordConfirm.getDocument().addDocumentListener(new RegisterListener(CommandConstants.CMD_PASSWORD_CONFIRM));
        rpanel.add(passwordConfirm);
        pwdcfStatus = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
        pwdcfStatus.setPreferredSize(new Dimension(100, 20));
        pwdcfStatus.setForeground(Color.RED);
        rpanel.add(pwdcfStatus);
        JLabel e1 = new JLabel();
        e1.setPreferredSize(new Dimension(400, 10));
        rpanel.add(e1);

        // 抬头信息
        JLabel ui = new JLabel(MessageConstants.USER_INFO, JLabel.LEFT);
        ui.setPreferredSize(new Dimension(390, 20));
        ui.setForeground(Color.BLUE);
        ui.setFont(FontConstants.STANDARD_FONT);
        rpanel.add(ui);
        // 分隔符
        JSeparator usp = new JSeparator(JSeparator.HORIZONTAL);
        usp.setForeground(Color.BLUE);
        usp.setPreferredSize(new Dimension(380, 5));
        rpanel.add(usp);
        // 中文姓名
        JLabel j4 = new JLabel(SystemConstants.CHINESE_NAME, JLabel.RIGHT);
        j4.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j4);
        cname = new JTextField();
        cname.setPreferredSize(new Dimension(180, 20));
        cname.getDocument().addDocumentListener(new RegisterListener(CommandConstants.CMD_CHINESE_NAME));
        rpanel.add(cname);
        cnStatus = new JLabel(SystemConstants.MUST_FLAG, JLabel.LEFT);
        cnStatus.setPreferredSize(new Dimension(110, 20));
        cnStatus.setForeground(Color.RED);
        rpanel.add(cnStatus);
        // 英文姓名
        JLabel j5 = new JLabel(SystemConstants.ENGLISH_NAME, JLabel.RIGHT);
        j5.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j5);
        ename = new JTextField();
        ename.setPreferredSize(new Dimension(180, 20));
        rpanel.add(ename);
        enStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        enStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(enStatus);
        // 性别
        JLabel j6 = new JLabel(SystemConstants.SEX, JLabel.RIGHT);
        j6.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j6);
        sex = new JComboBox(SystemConstants.SEX_COMBO);
        sex.setPreferredSize(new Dimension(180, 20));
        rpanel.add(sex);
        sxStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        sxStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(sxStatus);
        // 年龄
        JLabel j7 = new JLabel(SystemConstants.AGE, JLabel.RIGHT);
        j7.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j7);
        String[] ages = new String[120];
        ages[0] = SystemConstants.PLEASE_CHOOSE;
        for (int i = 1; i < 120; i++) {
            ages[i] = i + SystemConstants.EMPTY_STRING;
        }
        age = new JComboBox(ages);
        age.setPreferredSize(new Dimension(180, 20));
        rpanel.add(age);
        agStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        agStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(agStatus);
        // 生日
        JLabel j8 = new JLabel(SystemConstants.BIRTHDAY, JLabel.RIGHT);
        j8.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j8);
        // birthday = new JTextField();
        birthday = new DateChooser();
        birthday.setPreferredSize(new Dimension(180, 20));
        // birthday.addFocusListener(new RegisterListener(
        // CommandConstants.CMD_BIRTHDAY));
        rpanel.add(birthday);
        btStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        btStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(btStatus);
        // 出生地
        JLabel j9 = new JLabel(SystemConstants.BIRTHPLACE, JLabel.RIGHT);
        j9.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j9);
        birthplace = new JTextField();
        birthplace.setPreferredSize(new Dimension(180, 20));
        rpanel.add(birthplace);
        bpStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        bpStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(bpStatus);
        // 住址
        JLabel j10 = new JLabel(SystemConstants.ADDRESS, JLabel.RIGHT);
        j10.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j10);
        address = new JTextField();
        address.setPreferredSize(new Dimension(180, 20));
        rpanel.add(address);
        adStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        adStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(adStatus);
        // 邮编
        JLabel j11 = new JLabel(SystemConstants.POST, JLabel.RIGHT);
        j11.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j11);
        post = new JTextField();
        post.setPreferredSize(new Dimension(180, 20));
        rpanel.add(post);
        ptStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        ptStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(ptStatus);
        // 电子邮件
        JLabel j12 = new JLabel(SystemConstants.EMAIL, JLabel.RIGHT);
        j12.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j12);
        email = new JTextField();
        email.setPreferredSize(new Dimension(180, 20));
        rpanel.add(email);
        emStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        emStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(emStatus);
        // QQ
        JLabel j13 = new JLabel(SystemConstants.QQ, JLabel.RIGHT);
        j13.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j13);
        qq = new JTextField();
        qq.setPreferredSize(new Dimension(180, 20));
        rpanel.add(qq);
        qqStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        qqStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(qqStatus);
        // 固定电话
        JLabel j14 = new JLabel(SystemConstants.PHONE, JLabel.RIGHT);
        j14.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j14);
        phone = new JTextField();
        phone.setPreferredSize(new Dimension(180, 20));
        rpanel.add(phone);
        phStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        phStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(phStatus);
        // 手机
        JLabel j15 = new JLabel(SystemConstants.MOBILE, JLabel.RIGHT);
        j15.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j15);
        mobile = new JTextField();
        mobile.setPreferredSize(new Dimension(180, 20));
        rpanel.add(mobile);
        mbStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        mbStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(mbStatus);
        // 血型
        JLabel j16 = new JLabel(SystemConstants.BLOOD_TYPE, JLabel.RIGHT);
        j16.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j16);
        bloodtype = new JComboBox(SystemConstants.BLOOD_TYPE_COMBO);
        bloodtype.setPreferredSize(new Dimension(180, 20));
        rpanel.add(bloodtype);
        blStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        blStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(blStatus);
        // 身高
        JLabel j17 = new JLabel(SystemConstants.HEIGHT, JLabel.RIGHT);
        j17.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j17);
        height = new JTextField();
        height.setPreferredSize(new Dimension(180, 20));
        height.getDocument().addDocumentListener(new RegisterListener(CommandConstants.CMD_HEIGHT));
        rpanel.add(height);
        hgStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        hgStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(hgStatus);
        // 体重
        JLabel j18 = new JLabel(SystemConstants.WEIGHT, JLabel.RIGHT);
        j18.setPreferredSize(new Dimension(80, 20));
        rpanel.add(j18);
        weight = new JTextField();
        weight.setPreferredSize(new Dimension(180, 20));
        weight.getDocument().addDocumentListener(new RegisterListener(CommandConstants.CMD_WEIGHT));
        rpanel.add(weight);
        wgStatus = new JLabel(SystemConstants.PICK_FLAG, JLabel.LEFT);
        wgStatus.setPreferredSize(new Dimension(100, 20));
        rpanel.add(wgStatus);
        // 阅读协议
        JLabel jo = new JLabel();
        jo.setPreferredSize(new Dimension(100, 20));
        rpanel.add(jo);
        obey = new JCheckBox(MessageConstants.OBEY);
        obey.setPreferredSize(new Dimension(280, 20));
        obey.addActionListener(new RegisterListener(CommandConstants.CMD_OBEY));
        rpanel.add(obey);
        // 保存&取消
        JLabel je = new JLabel();
        je.setPreferredSize(new Dimension(100, 25));
        rpanel.add(je);
        commit = new JButton(FRAME_TITLE);
        commit.setPreferredSize(new Dimension(80, 25));
        commit.setEnabled(false);
        commit.addActionListener(new RegisterListener(CommandConstants.CMD_REGISTER));
        rpanel.add(commit);
        cancel = new JButton(SystemConstants.CANCEL);
        cancel.setPreferredSize(new Dimension(80, 25));
        cancel.addActionListener(new RegisterListener(CommandConstants.CMD_CANCEL));
        rpanel.add(cancel);

        this.add(rpanel);
        this.condition_type = BOTH_CONDITION;
        condition_flag = new boolean[ALL_CONDITIONS];
        condition_flag[0] = false;
        condition_flag[1] = false;
        condition_flag[2] = false;
        condition_flag[3] = false;
        condition_flag[4] = true;
        condition_flag[5] = true;
        condition_flag[6] = true;
        condition_flag[7] = true;
        condition_flag[8] = true;
        condition_flag[9] = true;
        condition_flag[10] = true;
        condition_flag[11] = true;
        condition_flag[12] = true;
        condition_flag[13] = true;
        condition_flag[14] = true;
        condition_flag[15] = true;
        condition_flag[16] = true;
        condition_flag[17] = true;
        condition_flag[18] = false;
        this.setTitle(FRAME_TITLE);
        Image image = Toolkit.getDefaultToolkit().getImage(FilePathConstants.LOTTERY_IMG_FILE);
        this.setIconImage(image);
        // Point location = MainFrame.getInstance().getPasswayButtonLocation();
        this.setLocation(400, 50);
        this.setPreferredSize(new java.awt.Dimension(410, 670));
        this.setUndecorated(false);
        this.pack();
        this.addWindowListener(new RegisterListener());
        // MainFrame.getInstance().setFrameEnable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // this.setVisible(true);
    }

    /**
     * @方法说明: 修改状态
     * @参数: @param component
     * @参数: @param status
     * @return void
     * @throws
     */
    @Override
    public void changeStatus(String component, String text, Color c) {
        if (component.equals(CommandConstants.CMD_USERNAME)) {
            unStatus.setText(text);
            unStatus.setForeground(c);
            return;
        }
        if (component.equals(CommandConstants.CMD_PASSWORD)) {
            pwdStatus.setText(text);
            pwdStatus.setForeground(c);
            return;
        }
        if (component.equals(CommandConstants.CMD_PASSWORD_CONFIRM)) {
            pwdcfStatus.setText(text);
            pwdcfStatus.setForeground(c);
            return;
        }
        if (component.equals(CommandConstants.CMD_CHINESE_NAME)) {
            cnStatus.setText(text);
            cnStatus.setForeground(c);
            return;
        }
        if (component.equals(CommandConstants.CMD_HEIGHT)) {
            hgStatus.setText(text);
            hgStatus.setForeground(c);
            return;
        }
        if (component.equals(CommandConstants.CMD_WEIGHT)) {
            wgStatus.setText(text);
            wgStatus.setForeground(c);
            return;
        }
    }

    public String getUserName() {
        return username.getText();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getPassword() {
        String pwd = new String(password.getPassword());
        return pwd;
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    public void openDateChooser() {
        if (dateFrame == null) {
            dateFrame = new JFrame();
        }
        dateFrame.setUndecorated(true);
        dateFrame.setPreferredSize(new java.awt.Dimension(200, 200));
        if (dateChooser == null) {
            dateChooser = new DateChooser();
        }
        dateFrame.add(dateChooser);
        // dateFrame.setLocation(birthday.getLocation());
        dateFrame.setVisible(true);
        // this.setEnabled(false);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    public void closeDateChooser() {
        dateFrame.setVisible(false);
        dateFrame.dispose();
        dateFrame = null;
    }

    public String getChineseName() {
        return cname.getText().trim();
    }

    public Date getBirthday() {
        if (dateChooser == null) {
            return null;
        }
        return dateChooser.getDate();
    }

    public boolean isObey() {
        return obey.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getEnglishName() {
        String enm = ename.getText();
        enm = enm.trim();
        if (enm.equals(SystemConstants.EMPTY_STRING)) {
            return null;
        }
        return enm;
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public Long getSex() {
        String sx = sex.getSelectedItem().toString();
        if (sx.equals(SystemConstants.PLEASE_CHOOSE)) {
            return null;
        }
        if (sx.equals(SystemConstants.MALE)) {
            return Long.valueOf(0);
        }
        return Long.valueOf(1);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return Long
     * @throws
     */
    public Long getAge() {
        String ag = age.getSelectedItem().toString();
        if (ag.equals(SystemConstants.PLEASE_CHOOSE)) {
            return null;
        }
        return Long.valueOf(ag);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getAddress() {
        String addr = address.getText();
        addr = addr.trim();
        if (addr.equals(SystemConstants.EMPTY_STRING)) {
            return null;
        }
        return addr;
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getPost() {
        String pst = post.getText();
        pst = pst.trim();
        if (pst.equals(SystemConstants.EMPTY_STRING)) {
            return null;
        }
        return pst;
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getEmail() {
        String eml = email.getText();
        eml = eml.trim();
        if (eml.equals(SystemConstants.EMPTY_STRING)) {
            return null;
        }
        return eml;
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getQQ() {
        String q = qq.getText();
        q = q.trim();
        if (q.equals(SystemConstants.EMPTY_STRING)) {
            return null;
        }
        return q;
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getPhone() {
        String ph = phone.getText();
        ph = ph.trim();
        if (ph.equals(SystemConstants.EMPTY_STRING)) {
            return null;
        }
        return ph;
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getMobile() {
        String mb = mobile.getText();
        mb = mb.trim();
        if (mb.equals(SystemConstants.EMPTY_STRING)) {
            return null;
        }
        return mb;
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getBloodType() {
        String bt = bloodtype.getSelectedItem().toString();
        if (bt.equals(SystemConstants.PLEASE_CHOOSE)) {
            return null;
        }
        return bt;
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return Double
     * @throws
     */
    public Double getUHeight() {
        String hg = height.getText();
        hg = hg.trim();
        if (hg.equals(SystemConstants.EMPTY_STRING)) {
            return null;
        }
        return Double.valueOf(hg);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return Double
     * @throws
     */
    public Double getUWeight() {
        String wg = weight.getText();
        wg = wg.trim();
        if (wg.equals(SystemConstants.EMPTY_STRING)) {
            return null;
        }
        return Double.valueOf(wg);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return String
     * @throws
     */
    public String getBirthPlace() {
        String bp = birthplace.getText();
        bp = bp.trim();
        if (bp.equals(SystemConstants.EMPTY_STRING)) {
            return null;
        }
        return bp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rb.lottery.UI.dialog.BasicDialog#destroy()
     */
    @Override
    public void destroy() {
        this.dispose();
        registerDialog = null;
    }
}
