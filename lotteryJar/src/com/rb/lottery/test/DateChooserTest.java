/**
 * @文件名称: DateChooserTest.java
 * @类路径:   com.rb.lottery.test
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-21 下午08:43:52
 * @版本:     1.0.0
 */
package com.rb.lottery.test;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.rb.gui.datechooser.DateChooser;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-21 下午08:43:52
 * @版本: 1.0.0
 */

public class DateChooserTest {

	/**
	 * @方法说明:
	 * @参数: @param args
	 * @return void
	 * @throws
	 */
	public static void main(String[] args) {
		final DateChooser mp = new DateChooser();
		JFrame jf = new JFrame("test");
		jf.add(mp, BorderLayout.CENTER);
		jf.add(new JButton("测试用的"), BorderLayout.NORTH);
		jf.pack();
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
