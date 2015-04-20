/**
 * @文件名称: AnalysisManager.java
 * @类路径:   com.rb.lottery.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-9-16 上午08:42:10
 * @版本:     1.0.0
 */
package com.rb.lottery.client.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * @类功能说明: 统计分析
 * @类修改者: robin
 * @修改日期: 2011-10-31
 * @修改说明: 修改为单例模式
 * @作者: robin
 * @创建时间: 2011-9-16 上午08:42:10
 * @版本: 1.0.0
 */

public class AnalysisManager {

	public double averageThr;
	public double averageOne;
	public double averageZer;
	public double averageMCT;
	public double averageMCO;
	public double averageMCZ;
	public double averageBK;
	public double averageSCO;

	private static AnalysisManager analysisManager = null;

	public static AnalysisManager getInstance() {
		if (analysisManager == null) {
			analysisManager = new AnalysisManager();
		}
		return analysisManager;
	}

	/**
	 * @类名: AnalysisManager.java
	 * @描述: TODO
	 */
	private AnalysisManager() {
	}

	/**
	 * @方法说明: 历史分析
	 * @参数: @param url
	 * @return void
	 * @throws
	 */
	public void doAnalysis(String url) {
		File file = new File(url);
		File infile = null, outfile = null;
		String[] filelist = null;
		if (file.isDirectory()) {
			filelist = file.list();
		} else if (file.isFile()) {
			filelist = new String[1];
			filelist[0] = "";
		}
		int filelen = filelist.length;
		String year = "";
		int totalStage = 0; // 总期数
		int stageNumber = 0; // 期数
		int[][] maxTOZ = new int[3][15]; // 最大个数
		int[][] maxConTOZ = new int[3][15]; // 最长连续个数
		int[] breaks = new int[15]; // 断点个数
		int[] scores = new int[43]; // 积分总和
		int three = 0;
		int maxConThr = 0;
		int one = 0;
		int maxConOne = 0;
		int zero = 0;
		int maxConZer = 0;
		int bk = 0;
		int score = 0;
		int totalThr = 0;
		int totalOne = 0;
		int totalZer = 0;
		int totalMCT = 0;
		int totalMCO = 0;
		int totalMCZ = 0;
		int totalBK = 0;
		int totalSCO = 0;
		double aveThr = 0.0;
		double aveOne = 0.0;
		double aveZer = 0.0;
		double aveMCT = 0.0;
		double aveMCO = 0.0;
		double aveMCZ = 0.0;
		double aveBK = 0.0;
		double aveSCO = 0.0;
		String line = "";
		NumberFormat format = NumberFormat
				.getNumberInstance(java.util.Locale.CHINA);
		format.setMinimumFractionDigits(4);
		for (int files = 0; files < filelen; files++) {
			infile = new File(url + "\\" + filelist[files]);
			year = infile.getName().substring(0, 2);
			if (year.charAt(0) < '0' || year.charAt(0) > '9'
					|| year.charAt(1) < '0' || year.charAt(1) > '9') {
				continue;
			}
			BufferedReader reader = null;
			try {
				System.out.println("读取文件" + infile.getName() + "...");
				reader = new BufferedReader(new FileReader(infile));
				stageNumber = 0;
				while ((line = reader.readLine()) != null) {
					line = line.trim();
					if (line.equals("")) {
						continue;
					}
					three = 0;
					maxConThr = 0;
					one = 0;
					maxConOne = 0;
					zero = 0;
					maxConZer = 0;
					score = 0;
					totalStage++;
					stageNumber++;
					// System.out.println("第" + year + (stageNumber < 100 ? "0"
					// : "") + stageNumber + "期:  " + line);
					String[] sco = line.split(" ");
					if (sco == null || sco.length != 14) {
						System.out.println("第" + year
								+ (stageNumber < 100 ? "0" : "") + stageNumber
								+ "期" + "无效");
					} else {
						for (int i = 0; i < 14; i++) {
							sco[i] = sco[i].trim();
							if (sco[i].equals("3")) {
								three++;
								totalThr++;
								score += 3;
								totalSCO += 3;
								int curConTre = 1;
								for (int j = i + 1; j < 14; j++) {
									if (sco[j].equals("3")) {
										curConTre++;
									} else {
										break;
									}
								}
								if (curConTre > maxConThr) {
									maxConThr = curConTre;
								}
							} else if (sco[i].equals("1")) {
								one++;
								totalOne++;
								score++;
								totalSCO++;
								int curConOne = 1;
								for (int j = i + 1; j < 14; j++) {
									if (sco[j].equals("1")) {
										curConOne++;
									} else {
										break;
									}
								}
								if (curConOne > maxConOne) {
									maxConOne = curConOne;
								}
							} else if (sco[i].equals("0")) {
								zero++;
								totalZer++;
								int curConZer = 1;
								for (int j = i + 1; j < 14; j++) {
									if (sco[j].equals("1")) {
										curConZer++;
									} else {
										break;
									}
								}
								if (curConZer > maxConZer) {
									maxConZer = curConZer;
								}
							}
						}
						bk = 0;
						for (int i = 1; i < 14; i++) {
							if (!sco[i].equals(sco[i - 1])) {
								bk++;
							}
						}
						breaks[bk]++;
						totalBK += bk;
						// System.out.println("3的个数:         " + three +
						// "\n1的个数:         " + one + "\n0的个数:         " +
						// zero);
						// System.out.println("最长连续3的个数: " + maxConThr +
						// "\n最长连续1的个数: " + maxConOne + "\n最长连续0的个数: " +
						// maxConZer);
						// System.out.println("断点数:         " + bk);
						// System.out.println("");
						scores[score]++;
						maxTOZ[0][three]++;
						maxTOZ[1][one]++;
						maxTOZ[2][zero]++;
						maxConTOZ[0][maxConThr]++;
						totalMCT += maxConThr;
						maxConTOZ[1][maxConOne]++;
						totalMCO += maxConOne;
						maxConTOZ[2][maxConZer]++;
						totalMCZ += maxConZer;
					}
				}
				reader.close();
			} catch (IOException e) {
				System.out.println("文件" + infile.getName() + "读取失败.");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("文件" + infile.getName() + "读取失败.");
				e.printStackTrace();
			}
		}
		try {
			aveThr = totalThr * 1.0 / totalStage;
			aveOne = totalOne * 1.0 / totalStage;
			aveZer = totalZer * 1.0 / totalStage;
			aveMCT = totalMCT * 1.0 / totalStage;
			aveMCO = totalMCO * 1.0 / totalStage;
			aveMCZ = totalMCZ * 1.0 / totalStage;
			aveBK = totalBK * 1.0 / totalStage;
			aveSCO = totalSCO * 1.0 / totalStage;

			// System.out.println("3的平均个数:     " + format.format(aveThr) +
			// "\n1的平均个数:     " + format.format(aveOne) + "\n0的平均个数:     " +
			// format.format(aveZer));
			// System.out.println("最长连续3的平均个数:     " + format.format(aveMCT) +
			// "\n最长连续1的平均个数:     " + format.format(aveMCO) +
			// "\n最长连续0的平均个数:     " + format.format(aveMCZ));
			// System.out.println("平均断点数: " + format.format(aveBK));
			// System.out.println("平均积分数: " + format.format(aveSCO));
			// System.out.println("文件" + infile.getName() + "读取成功结束.");

			if (outfile == null) {
				outfile = new File("dist\\report.txt");
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
			writer.write("总共期数: " + totalStage + "\r\n");
			for (int i = 0; i < 15; i++) {
				writer.write("3的个数为" + i + "的期数:" + (i > 9 ? " " : "  ")
						+ maxTOZ[0][i] + "\r\n");
			}
			writer.write("\r\n");
			for (int i = 0; i < 15; i++) {
				writer.write("1的个数为" + i + "的期数: " + (i > 9 ? " " : "  ")
						+ maxTOZ[1][i] + "\r\n");
			}
			writer.write("\r\n");
			for (int i = 0; i < 15; i++) {
				writer.write("0的个数为" + i + "的期数: " + (i > 9 ? " " : "  ")
						+ maxTOZ[2][i] + "\r\n");
			}
			writer.write("\r\n");
			for (int i = 0; i < 15; i++) {
				writer.write("3的最长连续个数为" + i + "的期数: " + (i > 9 ? " " : "  ")
						+ maxConTOZ[0][i] + "\r\n");
			}
			writer.write("\r\n");
			for (int i = 0; i < 15; i++) {
				writer.write("1的最长连续个数为" + i + "的期数: " + (i > 9 ? " " : "  ")
						+ maxConTOZ[1][i] + "\r\n");
			}
			writer.write("\r\n");
			for (int i = 0; i < 15; i++) {
				writer.write("0的最长连续个数为" + i + "的期数: " + (i > 9 ? " " : "  ")
						+ maxConTOZ[2][i] + "\r\n");
			}
			writer.write("\r\n");
			for (int i = 0; i < 15; i++) {
				writer.write("断点数为" + i + "的期数: " + (i > 9 ? " " : "  ")
						+ breaks[i] + "\r\n");
			}
			writer.write("\r\n");
			for (int i = 0; i < 43; i++) {
				writer.write("积分数为" + i + "的期数: " + (i > 9 ? " " : "  ")
						+ scores[i] + "\r\n");
			}
			writer.write("\r\n");
			writer.write("3的平均个数: " + format.format(aveThr) + "\r\n1的平均个数: "
					+ format.format(aveOne) + "\r\n0的平均个数: "
					+ format.format(aveZer));
			writer.write("\r\n");
			writer.write("最长连续3的平均个数: " + format.format(aveMCT)
					+ "\r\n最长连续1的平均个数: " + format.format(aveMCO)
					+ "\r\n最长连续0的平均个数: " + format.format(aveMCZ));
			writer.write("\r\n");
			writer.write("平均断点数: " + format.format(aveBK));
			writer.write("\r\n");
			writer.write("平均积分: " + format.format(aveSCO));

			System.out.println("结果输出目录: " + outfile.getAbsolutePath());
			writer.close();
		} catch (IOException e) {
			System.out.println("文件" + infile.getName() + "读取失败.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("文件" + infile.getName() + "读取失败.");
			e.printStackTrace();
		}

		averageThr = aveThr;
		averageOne = aveOne;
		averageZer = aveZer;
		averageMCT = aveMCT;
		averageMCO = aveMCO;
		averageMCZ = aveMCZ;
		averageBK = aveBK;
		averageSCO = aveSCO;
	}

}
