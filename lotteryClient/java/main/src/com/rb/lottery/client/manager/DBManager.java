/**
 * @文件名称: DBManager.java
 * @类路径:   com.rb.cxf.manager
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-9-22 下午04:08:30
 * @版本:     1.0.0
 */
package com.rb.lottery.client.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @类功能说明: 数据库连接封装
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-9-22 下午04:08:30
 * @版本: 1.0.0
 */

public class DBManager {

	private static final String COONECT_URL = "jdbc:oracle:thin:@ip:orcl";
	private static final String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";
	private static final String USER = "username";
	private static final String PASSWORD = "password";

	private static DBManager dbmanager = null;

	private static Connection connection;
	private static Statement statement;

	private DBManager() {
	}

	public static DBManager getInstance() {
		if (dbmanager == null) {
			dbmanager = new DBManager();
		}
		return dbmanager;
	}

	public void connect() {
		this.close();
		try {
			Class.forName(DRIVERNAME);
			connection = DriverManager.getConnection(COONECT_URL, USER,
					PASSWORD);
			statement = connection.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		if (connection != null || statement != null) {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public ResultSet executeQuery(String sql) {
		this.connect();
		ResultSet result = null;
		try {
			result = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
		return result;
	}

	public boolean excute(String sql) {
		this.connect();
		boolean status = false;
		try {
			status = statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
		return status;
	}

}
