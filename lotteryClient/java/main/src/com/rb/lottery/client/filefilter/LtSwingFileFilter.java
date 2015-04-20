/**
 * @文件名称: LtSwingFileFilter.java
 * @类路径:   com.rb.lottery.filefilter
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-8 下午01:32:05
 * @版本:     1.0.0
 */
package com.rb.lottery.client.filefilter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明:Swing文件过滤类
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-8 下午01:32:05
 * @版本: 1.0.0
 */

public class LtSwingFileFilter extends FileFilter {

	private String postfix;

	public LtSwingFileFilter(String postfix) {
		super();
		this.postfix = postfix;
	}

	/**
	 * @return postfix
	 */
	public String getPostfix() {
		return postfix;
	}

	/**
	 * @param postfix
	 *            postfix
	 */
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		String fileName = f.getName().toLowerCase();
		return fileName.endsWith(postfix);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		return SystemConstants.ASTERISK_STRING + postfix;
	}

}
