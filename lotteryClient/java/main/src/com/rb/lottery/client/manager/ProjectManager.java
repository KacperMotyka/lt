/**
 * @文件名称: ProjectManager.java
 * @类路径:   com.rb.lottery.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-31 下午02:34:08
 * @版本:     1.0.0
 */
package com.rb.lottery.client.manager;

import java.awt.HeadlessException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.rb.lottery.client.UI.dialog.PasswayDialog;
import com.rb.lottery.client.UI.main.MainFrame;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.filefilter.ProjectFileChooser;
import com.rb.lottery.client.filefilter.ResultFileChooser;
import com.rb.lottery.client.filefilter.UploadFileChooser;
import com.rb.lottery.domain.BetBasket;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemProcessor;

/**
 * @类功能说明: 方案处理类
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-10-31 下午02:34:08
 * @版本: 1.0.0
 */

public class ProjectManager {

	private static ProjectManager projectManager = null;

	private ProjectManager() {
	}

	public static ProjectManager getInstance() {
		if (projectManager == null) {
			projectManager = new ProjectManager();
		}
		return projectManager;
	}

	/**
	 * @方法说明:
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void choosePassway() {
		int matchCount = SystemProcessor.getLottery(
				SystemCache.currentLotteryId).getBetMatchCount();
		PasswayDialog pf = new PasswayDialog(matchCount);
		pf.setVisible(true);
	}

	/**
	 * @方法说明: 新建工程
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void newProject() {
		ImageIcon icon = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
		int isOK = JOptionPane.showConfirmDialog(null,
				MessageConstants.NEW_PROJECT_INFO, MessageConstants.INFOMATION,
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				icon);
		if (isOK == 0) // YES
		{
			IOManager.getInstance().newProject();
		}
	}

	/**
	 * @方法说明: 弹出保存文件框
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void saveProject() {
		try {
			JFileChooser jfc = new ProjectFileChooser();
			jfc.setDialogTitle(SystemConstants.MENU_SAVEPRO);
			int result = jfc.showSaveDialog(new JFrame());
			if (result == JFileChooser.APPROVE_OPTION) {
				String fileName = jfc.getCurrentDirectory()
						+ SystemConstants.NEXT_FOLDER
						+ jfc.getSelectedFile().getName();
				if (!fileName.endsWith(FilePathConstants.PROJECT_FILE_POSTFIX)) {
					fileName += FilePathConstants.PROJECT_FILE_POSTFIX;
				}
				IOManager.getInstance().doSavePrj(fileName);
				JOptionPane.showMessageDialog(null,
						MessageConstants.SAVE_PAROJECT_SUCCESS);
			}
		} catch (HeadlessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 弹出读取文件框
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void openProject() {
		try {
			JFileChooser jfc = new ProjectFileChooser();
			jfc.setDialogTitle(SystemConstants.MENU_OPENPRO);
			int result = jfc.showOpenDialog(new JFrame());
			if (result == JFileChooser.APPROVE_OPTION) {
				String fileName = jfc.getCurrentDirectory()
						+ SystemConstants.NEXT_FOLDER
						+ jfc.getSelectedFile().getName();
				IOManager.getInstance().doOpenPrj(fileName);
			}
		} catch (HeadlessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 单式拆分
	 * @参数: @param toNewBasket
	 * @参数: @param isSingleDisplay
	 * @return void
	 * @throws
	 */
	public void doPart(boolean toNewBasket) {
		BetBasket basket = SystemProcessor
				.generateCurrentBets(SystemCache.currentLotteryId);
		if (basket == null) {
			return;
		}
		if (toNewBasket) {
			SystemProcessor.addBasket(basket);
		} else {
			SystemProcessor.initBasket(basket);
		}
		MainFrame.getInstance().displaySingleBet(1);
		int singleBetCount = basket.getSingleBetCount();
		String message = MessageConstants.SINGLE_SUCCESS + singleBetCount;
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * @方法说明: 投注
	 * @参数: @param toNewBasket
	 * @参数: @param isSingleDisplay
	 * @return void
	 * @throws
	 */
	public void displayBets(boolean toNewBasket, boolean isSingleDisplay,
			BetBasket basket) {
		if (basket == null) {
			return;
		}
		if (toNewBasket) {
			SystemProcessor.addBasket(basket);
		} else {
			SystemProcessor.initBasket(basket);
		}
		// int singleBetCount = basket.getSingleBetCount();
		// int multiBetCount = basket.getMultiBetCount();
		if (isSingleDisplay) {
			MainFrame.getInstance().displaySingleBet(1);
			// String message = MessageConstants.SINGLE_SUCCESS +
			// singleBetCount;
			// JOptionPane.showMessageDialog(null, message);
		} else {
			MainFrame.getInstance().displayMultiBet(1);
			// String message = MessageConstants.MULTI_SUCCESS + multiBetCount;
			// JOptionPane.showMessageDialog(null, message);
		}
	}

	/**
	 * @方法说明: 保存投注结果
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void saveResult() {
		try {
			JFileChooser jfc = new ResultFileChooser();
			jfc.setDialogTitle(SystemConstants.MENU_SAVERET);
			int result = jfc.showSaveDialog(new JFrame());
			if (result == JFileChooser.APPROVE_OPTION) {
				String fileName = jfc.getCurrentDirectory()
						+ SystemConstants.NEXT_FOLDER
						+ jfc.getSelectedFile().getName();
				if (!fileName.endsWith(FilePathConstants.RESULT_FILE_POSTFIX)) {
					fileName += FilePathConstants.RESULT_FILE_POSTFIX;
				}
				IOManager.getInstance().doSaveRet(fileName);
				JOptionPane.showMessageDialog(null,
						MessageConstants.SAVE_RESULT_SUCCESS);
			}
		} catch (HeadlessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 打开投注结果
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void openResult() {
		try {
			JFileChooser jfc = new ResultFileChooser();
			jfc.setDialogTitle(SystemConstants.MENU_OPENRET);
			int result = jfc.showOpenDialog(new JFrame());
			if (result == JFileChooser.APPROVE_OPTION) {
				String fileName = jfc.getCurrentDirectory()
						+ SystemConstants.NEXT_FOLDER
						+ jfc.getSelectedFile().getName();
				IOManager.getInstance().doOpenRet(fileName);
			}
		} catch (HeadlessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 上传投注方案（投注）
	 * @参数:
	 * @return void
	 * @throws
	 */
	public void uploadProject() {
		try {
			JFileChooser jfc = new UploadFileChooser();
			jfc.setDialogTitle(SystemConstants.MENU_UPLOAD);
			int result = jfc.showOpenDialog(new JFrame());
			if (result == JFileChooser.APPROVE_OPTION) {
				String fileName = jfc.getCurrentDirectory()
						+ SystemConstants.NEXT_FOLDER
						+ jfc.getSelectedFile().getName();
				UploadManager.getInstance().doUploadBet(fileName);
			}
		} catch (HeadlessException e) {
			e.printStackTrace();
		}
	}
}
