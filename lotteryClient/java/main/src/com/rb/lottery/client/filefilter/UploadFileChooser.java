/**
 * @文件名称: UploadFileChooser.java
 * @类路径:   com.rb.lottery.filefilter
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-8 下午01:16:42
 * @版本:     1.0.0
 */
package com.rb.lottery.client.filefilter;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.rb.lottery.client.common.FilePathConstants;


/**
 * @类功能说明: 方案文件上传选择
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-8 下午01:16:42
 * @版本: 1.0.0
 */

public class UploadFileChooser extends JFileChooser {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 6043195766652412246L;

	private FileFilter filter;

	public UploadFileChooser() {
		super();
		filter = new LtSwingFileFilter(FilePathConstants.TXT_POSTFIX);
		this.setFileFilter(filter);
		String upload_dir = FilePathConstants.UPLOAD_FILE_DIR;
		File dir = new File(upload_dir);
		if (!dir.exists()) {
			dir.mkdir();
		}
		this.setCurrentDirectory(dir);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JFileChooser#approveSelection()
	 */
	@Override
	public void approveSelection() {
		if (this.getDialogType() == JFileChooser.SAVE_DIALOG) {
		} else if (this.getDialogType() == JFileChooser.OPEN_DIALOG) {
			super.approveSelection();
		}
	}
}
