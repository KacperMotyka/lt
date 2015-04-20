/**
 * @文件名称: SumJavaCodeDemo.java
 * @类路径:   com.rb.lottery.demo
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-13 下午02:14:35
 * @版本:     1.0.0
 */
package com.rb.lottery.server.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.rb.lottery.system.SystemProcessor;

/**
 * @类功能说明: 统计JAVA代码行数
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-10-13 下午02:14:35
 * @版本: 1.0.0
 */

public class SumJavaCodeDemo {

	private static final Logger log = Logger.getLogger(SumJavaCodeDemo.class);

	static long files = 0; // 文件数

	static long normalLines = 0; // 代码行

	static long commentLines = 0; // 注释行

	static long whiteLines = 0; // 空行

	public static final String FILE_DIR = ".";

	public static void main(String[] args) {
		
		SystemProcessor.initLog4j();

		SumJavaCodeDemo sjc = new SumJavaCodeDemo();

		File f = new File(FILE_DIR);

		log.info("\nJAVA文件:");
		sjc.treeFile(f);
		
		log.info("文件数：" + files);
		log.info("代码行：" + normalLines);
		log.info("注释行：" + commentLines);
		log.info("空行:" + whiteLines);

	}

	/**
	 * 
	 * 查找出一个目录下所有的.java文件
	 * 
	 * 
	 * 
	 * @param f
	 *            要查找的目录
	 */

	private void treeFile(File f) {

		File[] childs = f.listFiles();

		// int count = 0;

		// int sum = 0;
		
		for (int i = 0; i < childs.length; i++) {

			// System.out.println(preStr + childs[i].getName());

			if (!childs[i].isDirectory()) {

				if (childs[i].getName().matches(".*\\.java$")) {

					System.out.println(childs[i].getName());

					files++;

					sumCode(childs[i]);

				}

			} else {

				treeFile(childs[i]);

				// sum += count;

			}

		}

	}

	/**
	 * 
	 * 计算一个.java文件中的代码行，空行，注释行
	 * 
	 * 
	 * 
	 * @param file
	 * 
	 *            要计算的.java文件
	 */

	private void sumCode(File file) {

		BufferedReader br = null;

		boolean comment = false;

		try {

			br = new BufferedReader(new FileReader(file));

			String line = "";

			try {

				while ((line = br.readLine()) != null) {

					line = line.trim();

					if (line.matches("^[\\s&&[^\\n]]*$")) {

						whiteLines++;

					} else if (line.startsWith("/*") && !line.endsWith("*/")) {

						commentLines++;

						comment = true;

					} else if (true == comment) {

						commentLines++;

						if (line.endsWith("*/")) {

							comment = false;

						}

					} else if (line.startsWith("//")) {

						commentLines++;

					} else {

						normalLines++;

					}

				}

			} catch (IOException e) {

				e.printStackTrace();

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} finally {

			if (br != null) {

				try {

					br.close();

					br = null;

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
