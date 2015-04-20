/**
 * @文件名称: ProjectFileChooser.java
 * @类路径:   com.rb.lottery.filefilter
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-12 下午07:19:08
 * @版本:     1.0.0
 */
package com.rb.lottery.client.filefilter;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.common.SystemFunctions;

/**
 * @类功能说明: 投注方案文件打开保存对话框
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-12 下午07:19:08
 * @版本: 1.0.0
 */

public class ProjectFileChooser extends JFileChooser {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 4516406764140746417L;

	private FileFilter filter;

	public ProjectFileChooser() {
		super();
		filter = new LtSwingFileFilter(FilePathConstants.PROJECT_FILE_POSTFIX);
		this.setFileFilter(filter);
		// String classpath =
		// System.getProperty(SystemConstants.SYSTEM_CLASSPATH);
		String proj_dir = FilePathConstants.PROJECT_FILE_DIR;
		File dir = new File(proj_dir);
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
			String fileName = this.getSelectedFile().getName();
			if (!SystemFunctions.isLegalFileName(fileName)) {
				JOptionPane.showMessageDialog(null,
						MessageConstants.ILEGAL_FILE_MESSAGE);
				return;
			}
			String filePath = this.getCurrentDirectory()
					+ SystemConstants.NEXT_FOLDER + fileName;
			if (!filePath.endsWith(FilePathConstants.PROJECT_FILE_POSTFIX)) {
				filePath += FilePathConstants.PROJECT_FILE_POSTFIX;
			}
			File file = new File(filePath);
			if (file.exists()) {
				ImageIcon icon = new ImageIcon(
						FilePathConstants.DATATABLE_IMG_FILE);
				int isOK = JOptionPane.showConfirmDialog(null,
						MessageConstants.FILE_EXIST_INFO,
						MessageConstants.INFOMATION, JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, icon);
				if (isOK == 0) {
					super.approveSelection();
				} else {
					return;
				}
			} else {
				super.approveSelection();
			}
		} else if (this.getDialogType() == JFileChooser.OPEN_DIALOG) {
			super.approveSelection();
		}
	}
}
