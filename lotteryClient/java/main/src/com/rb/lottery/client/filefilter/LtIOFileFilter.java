/**
 * @文件名称: LtIOFileFilter.java
 * @类路径:   com.rb.lottery.filefilter
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-8 下午01:19:52
 * @版本:     1.0.0
 */
package com.rb.lottery.client.filefilter;

import java.io.File;
import java.io.FileFilter;

/**
 * @类功能说明: 文件过滤器类
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-8 下午01:19:52
 * @版本: 1.0.0
 */

public class LtIOFileFilter implements FileFilter {

	private String postfix;

	public LtIOFileFilter(String postfix) {
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
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return false;
		}
		String fileName = f.getName().toLowerCase();
		return fileName.endsWith(postfix);
	}

}
