/**
 * @文件名称: FontConstants.java
 * @类路径:   com.rb.lottery.common
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-18 上午10:39:23
 * @版本:     1.0.0
 */
package com.rb.lottery.client.common;

import java.awt.Font;

/**
 * @类功能说明: 界面字体常量配置
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-18 上午10:39:23
 * @版本: 1.0.0
 */

public interface FontConstants {

	public static final String SONG_TYPEFACE_FONT = "宋体";
	public static final int SMALL_FONT_SIZE = 10;
	public static final int STANDARD_FONT_SIZE = 13;
	public static final int BIG_FONT_SIZE = 16;

	// Font
	public static final Font DEFAULT_FONT = new Font(SONG_TYPEFACE_FONT,
			Font.BOLD, SMALL_FONT_SIZE);
	public static final Font INIT_FONT = new Font(SONG_TYPEFACE_FONT,
			Font.ITALIC, BIG_FONT_SIZE);
	public static final Font EXIT_SAVE_FONT = new Font(SONG_TYPEFACE_FONT,
			Font.ITALIC, BIG_FONT_SIZE);
	public static final Font STANDARD_FONT = new Font(SONG_TYPEFACE_FONT,
			Font.BOLD, STANDARD_FONT_SIZE);
}
