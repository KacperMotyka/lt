/**
 * @文件名称: CryptoguardDialog.java
 * @类路径:   com.rb.lottery.UI.dialog
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-24 下午03:51:36
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.rb.lottery.client.UI.listener.CryptoguardListener;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.FontConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明: 密保问题设置窗口
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-24 下午03:51:36
 * @版本: 1.0.0
 */

public class CryptoguardDialog extends BasicDialog {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 2253351895277449454L;
	private static CryptoguardDialog cpdDialog = null;
	private static final String FRAME_TITLE = "密保问题设置";
	private static final String COMMIT = "提交";
	private static final String QUESTION1 = "问题1：";
	private static final String OWNQUESTION1 = "自定义问题1：";
	private static final String ANSWER1 = "答案1：";
	private static final String QUESTION2 = "问题2：";
	private static final String OWNQUESTION2 = "自定义问题2：";
	private static final String ANSWER2 = "答案2：";
	private static final String QUESTION3 = "问题3：";
	private static final String OWNQUESTION3 = "自定义问题3：";
	private static final String ANSWER3 = "答案3：";
	private static final String[] QUESTIONS = { "请选择", "我叫什么名字?", "我的出生地在哪?",
			"我的高中班主任叫什么名字?", "我的生日是哪天?", "我爸爸的职业是什么?", "我妈妈的生日是哪天?", "自定义问题..." };

	// 密保
	private JPanel cpanel;
	private JComboBox question1;
	private JLabel jo1;
	private JTextField ownquestion1;
	private JTextField answer1;
	private JComboBox question2;
	private JLabel jo2;
	private JTextField ownquestion2;
	private JTextField answer2;
	private JComboBox question3;
	private JLabel jo3;
	private JTextField ownquestion3;
	private JTextField answer3;

	public static CryptoguardDialog getInstance() {
		if (cpdDialog == null) {
			cpdDialog = new CryptoguardDialog();
		}
		return cpdDialog;
	}

	private CryptoguardDialog() {
		cpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// 抬头信息
		JLabel ai = new JLabel(MessageConstants.CHOOSE_CRYPTD, JLabel.LEFT);
		ai.setPreferredSize(new Dimension(340, 20));
		ai.setForeground(Color.BLUE);
		ai.setFont(FontConstants.STANDARD_FONT);
		cpanel.add(ai);
		// 分隔符
		JSeparator sp = new JSeparator(JSeparator.HORIZONTAL);
		sp.setForeground(Color.BLUE);
		sp.setPreferredSize(new Dimension(320, 5));
		cpanel.add(sp);
		// 第一个问题
		// 选择问题
		JLabel j1 = new JLabel(QUESTION1, JLabel.RIGHT);
		j1.setPreferredSize(new Dimension(90, 20));
		cpanel.add(j1);
		question1 = new JComboBox(QUESTIONS);
		question1.setPreferredSize(new Dimension(200, 20));
		question1.addItemListener(new CryptoguardListener(
				CommandConstants.CMD_QUESTION + "1"));
		cpanel.add(question1);
		// 自定义问题
		jo1 = new JLabel(OWNQUESTION1, JLabel.RIGHT);
		jo1.setPreferredSize(new Dimension(90, 20));
		jo1.setVisible(false);
		cpanel.add(jo1);
		ownquestion1 = new JTextField();
		ownquestion1.setPreferredSize(new Dimension(200, 20));
		ownquestion1.setVisible(false);
		ownquestion1.getDocument().addDocumentListener(
				new CryptoguardListener(CommandConstants.CMD_QUESTION + "1"));
		cpanel.add(ownquestion1);
		// 问题答案
		JLabel ja1 = new JLabel(ANSWER1, JLabel.RIGHT);
		ja1.setPreferredSize(new Dimension(90, 20));
		cpanel.add(ja1);
		answer1 = new JTextField();
		answer1.setPreferredSize(new Dimension(200, 20));
		answer1.getDocument().addDocumentListener(
				new CryptoguardListener(CommandConstants.CMD_ANSWER + "1"));
		cpanel.add(answer1);
		JLabel je1 = new JLabel();
		je1.setPreferredSize(new Dimension(340, 30));
		cpanel.add(je1);
		// 第二个问题
		// 选择问题
		JLabel j2 = new JLabel(QUESTION2, JLabel.RIGHT);
		j2.setPreferredSize(new Dimension(90, 20));
		cpanel.add(j2);
		question2 = new JComboBox(QUESTIONS);
		question2.setPreferredSize(new Dimension(200, 20));
		question2.addItemListener(new CryptoguardListener(
				CommandConstants.CMD_QUESTION + "2"));
		cpanel.add(question2);
		// 自定义问题
		jo2 = new JLabel(OWNQUESTION2, JLabel.RIGHT);
		jo2.setPreferredSize(new Dimension(90, 20));
		jo2.setVisible(false);
		cpanel.add(jo2);
		ownquestion2 = new JTextField();
		ownquestion2.setPreferredSize(new Dimension(200, 20));
		ownquestion2.setVisible(false);
		ownquestion2.getDocument().addDocumentListener(
				new CryptoguardListener(CommandConstants.CMD_QUESTION + "2"));
		cpanel.add(ownquestion2);
		// 问题答案
		JLabel ja2 = new JLabel(ANSWER2, JLabel.RIGHT);
		ja2.setPreferredSize(new Dimension(90, 20));
		cpanel.add(ja2);
		answer2 = new JTextField();
		answer2.setPreferredSize(new Dimension(200, 20));
		answer2.getDocument().addDocumentListener(
				new CryptoguardListener(CommandConstants.CMD_ANSWER + "2"));
		cpanel.add(answer2);
		JLabel je2 = new JLabel();
		je2.setPreferredSize(new Dimension(320, 30));
		cpanel.add(je2);
		// 第三个问题
		// 选择问题
		JLabel j3 = new JLabel(QUESTION3, JLabel.RIGHT);
		j3.setPreferredSize(new Dimension(90, 20));
		cpanel.add(j3);
		question3 = new JComboBox(QUESTIONS);
		question3.setPreferredSize(new Dimension(200, 20));
		question3.addItemListener(new CryptoguardListener(
				CommandConstants.CMD_QUESTION + "3"));
		cpanel.add(question3);
		// 自定义问题
		jo3 = new JLabel(OWNQUESTION3, JLabel.RIGHT);
		jo3.setPreferredSize(new Dimension(90, 20));
		jo3.setVisible(false);
		cpanel.add(jo3);
		ownquestion3 = new JTextField();
		ownquestion3.setPreferredSize(new Dimension(200, 20));
		ownquestion3.setVisible(false);
		ownquestion3.getDocument().addDocumentListener(
				new CryptoguardListener(CommandConstants.CMD_QUESTION + "3"));
		cpanel.add(ownquestion3);
		// 问题答案
		JLabel ja3 = new JLabel(ANSWER3, JLabel.RIGHT);
		ja3.setPreferredSize(new Dimension(90, 20));
		cpanel.add(ja3);
		answer3 = new JTextField();
		answer3.setPreferredSize(new Dimension(200, 20));
		answer3.getDocument().addDocumentListener(
				new CryptoguardListener(CommandConstants.CMD_ANSWER + "3"));
		cpanel.add(answer3);
		JLabel je3 = new JLabel();
		je3.setPreferredSize(new Dimension(320, 30));
		cpanel.add(je3);
		// 提交&取消
		JLabel je = new JLabel();
		je.setPreferredSize(new Dimension(70, 25));
		cpanel.add(je);
		commit = new JButton(COMMIT);
		commit.setPreferredSize(new Dimension(80, 25));
		commit.setEnabled(false);
		commit.addActionListener(new CryptoguardListener(
				CommandConstants.CMD_COMMIT));
		cpanel.add(commit);
		cancel = new JButton(SystemConstants.CANCEL);
		cancel.setPreferredSize(new Dimension(80, 25));
		cancel.addActionListener(new CryptoguardListener(
				CommandConstants.CMD_CANCEL));
		cpanel.add(cancel);

		this.add(cpanel);
		// 设置表单验证模式
		this.condition_type = ONE_CONDITION;
		condition_flag = new boolean[3];
		condition_flag[0] = false;
		condition_flag[1] = false;
		condition_flag[2] = false;
		this.setTitle(FRAME_TITLE);
		Image image = Toolkit.getDefaultToolkit().getImage(
				FilePathConstants.LOTTERY_IMG_FILE);
		this.setIconImage(image);
		// Point location = MainFrame.getInstance().getPasswayButtonLocation();
		this.setLocation(400, 50);
		this.setPreferredSize(new java.awt.Dimension(360, 460));
		this.setUndecorated(false);
		this.pack();
		this.addWindowListener(new CryptoguardListener());
		// MainFrame.getInstance().setFrameEnable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// this.setVisible(true);
	}

	/**
	 * @方法说明: 检查是否选择自定义
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void checkSelectOwn(String component) {
		String select = null;
		if (component.equals(CommandConstants.CMD_QUESTION + "1")) {
			select = question1.getSelectedItem().toString();
			if (select.equals("自定义问题...")) {
				jo1.setVisible(true);
				ownquestion1.setVisible(true);
				if (ownquestion1.getText().equals("")
						|| answer1.getText().equals("")) {
					this.setCondition(0, false);
				} else {
					this.setCondition(0, true);
				}
			} else {
				jo1.setVisible(false);
				ownquestion1.setVisible(false);
				if (answer1.getText().equals("")) {
					this.setCondition(0, false);
				} else {
					if (select.equals("请选择")) {
						this.setCondition(0, false);
					} else {
						this.setCondition(0, true);
					}
				}
			}
		} else if (component.equals(CommandConstants.CMD_QUESTION + "2")) {
			select = question2.getSelectedItem().toString();
			if (select.equals("自定义问题...")) {
				jo2.setVisible(true);
				ownquestion2.setVisible(true);
				if (ownquestion2.getText().equals("")
						|| answer2.getText().equals("")) {
					this.setCondition(1, false);
				} else {
					this.setCondition(1, true);
				}
			} else {
				jo2.setVisible(false);
				ownquestion2.setVisible(false);
				if (answer2.getText().equals("")) {
					this.setCondition(1, false);
				} else {
					if (select.equals("请选择")) {
						this.setCondition(1, false);
					} else {
						this.setCondition(1, true);
					}
				}
			}
		} else {
			select = question3.getSelectedItem().toString();
			if (select.equals("自定义问题...")) {
				jo3.setVisible(true);
				ownquestion3.setVisible(true);
				if (ownquestion3.getText().equals("")
						|| answer3.getText().equals("")) {
					this.setCondition(2, false);
				} else {
					this.setCondition(2, true);
				}
			} else {
				jo3.setVisible(false);
				ownquestion3.setVisible(false);
				if (answer3.getText().equals("")) {
					this.setCondition(2, false);
				} else {
					if (select.equals("请选择")) {
						this.setCondition(2, false);
					} else {
						this.setCondition(2, true);
					}
				}
			}
		}
	}

	/**
	 * @方法说明: 获取answer
	 * @参数: @param index
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public String getAnswer(int index) {
		if (1 == index) {
			return answer1.getText();
		}
		if (2 == index) {
			return answer2.getText();
		}
		return answer3.getText();
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public String getSelectText1() {
		return question1.getSelectedItem().toString();
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public String getSelectText2() {
		return question2.getSelectedItem().toString();
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public String getSelectText3() {
		return question3.getSelectedItem().toString();
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public String getOwnQuestion1() {
		return ownquestion1.getText();
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public String getOwnQuestion2() {
		return ownquestion2.getText();
	}

	/**
	 * @方法说明:
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public String getOwnQuestion3() {
		return ownquestion3.getText();
	}

	/**
	 * @方法说明:
	 * @参数: @param index
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	public String getQuestion(int index) {
		String question = null;
		if (1 == index) {
			question = question1.getSelectedItem().toString();
			if (question.equals("自定义问题...")) {
				return ownquestion1.getText();
			} else if (question.equals("请选择")) {
				return "";
			}
			return question;
		}
		if (2 == index) {
			question = question2.getSelectedItem().toString();
			if (question.equals("自定义问题...")) {
				return ownquestion2.getText();
			} else if (question.equals("请选择")) {
				return "";
			}
			return question;
		}
		question = question3.getSelectedItem().toString();
		if (question.equals("自定义问题...")) {
			return ownquestion3.getText();
		} else if (question.equals("请选择")) {
			return "";
		}
		return question;
	}

	/* (non-Javadoc)
	 * @see com.rb.lottery.UI.dialog.BasicDialog#destroy()
	 */
	@Override
	public void destroy() {
		this.dispose();
		cpdDialog = null;
	}

	/* (non-Javadoc)
	 * @see com.rb.lottery.UI.dialog.BasicDialog#changeStatus(java.lang.String, java.lang.String, java.awt.Color)
	 */
	@Override
	public void changeStatus(String component, String text, Color c) {
		// TODO Auto-generated method stub
		
	}
}
