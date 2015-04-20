/**
 * @文件名称: BasicDialog.java
 * @类路径:   com.rb.lottery.UI.dialog
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-25 下午02:04:48
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.dialog;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * @类功能说明: 窗口基类（表单验证）
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-25 下午02:04:48
 * @版本: 1.0.0
 */

public abstract class BasicDialog extends JFrame {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -8140437353646043500L;

	protected static final int BOTH_CONDITION = 0;
	protected static final int ONE_CONDITION = 1;
	protected boolean[] condition_flag;
	protected int condition_type;

	protected JButton commit;
	protected JButton cancel;

	/**
	 * @类名: BasicDialog.java
	 * @描述: TODO
	 * @throws HeadlessException
	 */
	public BasicDialog() throws HeadlessException {
		this.getRootPane().setDefaultButton(commit);
	}

	/**
	 * @类名: BasicDialog.java
	 * @描述: TODO
	 * @param gc
	 */
	public BasicDialog(GraphicsConfiguration gc) {
		super(gc);
	}

	/**
	 * @类名: BasicDialog.java
	 * @描述: TODO
	 * @param title
	 * @throws HeadlessException
	 */
	public BasicDialog(String title) throws HeadlessException {
		super(title);
	}

	/**
	 * @类名: BasicDialog.java
	 * @描述: TODO
	 * @param title
	 * @param gc
	 */
	public BasicDialog(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

	/**
	 * @方法说明: 设置条件
	 * @参数:     @param index
	 * @参数:     @param b
	 * @return    void
	 * @throws
	 */
	public void setCondition(int index, boolean b) {
		condition_flag[index] = b;
		if (isValidForm()) {
			this.setCommitEnable(true);
		} else {
			this.setCommitEnable(false);
		}
	}

	/**
	 * @方法说明: 验证提交表单是否有效
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	private boolean isValidForm() {

		boolean valid = true;
		int sz = condition_flag.length;
		if (condition_type == BOTH_CONDITION) {
			valid = true;
			for (int i = 0; i < sz; i++) {
				valid = valid && condition_flag[i];
			}
		} else if (condition_type == ONE_CONDITION) {
			valid = false;
			for (int i = 0; i < sz; i++) {
				valid = valid || condition_flag[i];
			}
		}
		return valid;
	}
	
	/**
	 * @方法说明: 设置提交按钮是否可用
	 * @参数:     @param b
	 * @return    void
	 * @throws
	 */
	private void setCommitEnable(boolean b) {
		commit.setEnabled(b);
	}
	
	/**
	 * @方法说明: 销毁窗口
	 * @参数:     
	 * @return    void
	 * @throws
	 */
	abstract public void destroy();
	
	
	/**
	 * @方法说明:  修改表单验证状态
	 * @参数:     @param component
	 * @参数:     @param text
	 * @参数:     @param c
	 * @return    void
	 * @throws
	 */
	abstract public void changeStatus(String component, String text, Color c);
}
