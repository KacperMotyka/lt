/**
 * @文件名称: BasicDAO.java
 * @类路径:   com.rb.lottery.dao
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-29 上午09:30:07
 * @版本:     1.0.0
 */
package com.rb.lottery.server.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import com.rb.lottery.server.dao.HibernateSessionFactory;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-29 上午09:30:07
 * @版本: 1.0.0
 */

public class BasicDAO<T> {

	private static final Logger log = Logger.getLogger(BasicDAO.class);

	// 批量添加、更新、删除记录每次提交的记录数
	private static final int FLUSH_COUNT = 20;

	@SuppressWarnings("unchecked")
	private List<String> getAllTables() {
		String sql = "select t.table_name from user_tables t";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery(sql);
		return query.list();
	}

	public void createTable(String year) {
		List<String> tables = getAllTables();
		final String sfc_table = "LT_SFC_" + year;
		final String sfckj_table = "LT_SFCKJ_" + year;
		if (tables.indexOf(sfc_table) < 0) {
			final String sql = "create table " + sfc_table;
			// TODO add column
			Session session = HibernateSessionFactory.getSession();
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					Statement statement = connection.createStatement();
					statement.execute(sql);
					connection.commit();
					log.info("Table 【" + sfc_table + "】【 " + sfckj_table
							+ "】has been created.");
				}
			});
		} else {
			log.error("Table 【" + sfc_table + "】【" + sfckj_table
					+ "】is already exist.");
		}
	}

	/**
	 * @方法说明: 保存或更新
	 * @参数: @param obj
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean saveOrUpdate(T obj) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(obj);
			session.flush();
			session.clear();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		}
		if (session != null && session.isOpen()) {
			session.close();
		}
		return true;
	}

	/**
	 * @方法说明: 批量保存更新
	 * @参数: @param objs
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean saveOrUpdateAll(List<T> objs) {
		Session session = HibernateSessionFactory.getSession();
		int count = 0;
		Transaction tx = null;
		// 开始事务
		try {
			tx = session.beginTransaction();
			for (T obj : objs) {
				session.saveOrUpdate(obj);
				count++;
				if (count % FLUSH_COUNT == 0) {
					session.flush();
					session.clear();
					tx.commit();
					tx = session.beginTransaction();
				}
			}
			tx.commit();
		} catch (NonUniqueObjectException e) {
			tx.rollback();
			session.clear();
			List<T> uniqueList = new ArrayList<T>();
			for (T obj : objs) {
				if (uniqueList.indexOf(obj) < 0) {
					uniqueList.add(obj);
				}
			}
			saveOrUpdateAll(uniqueList);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		}
		if (session != null && session.isOpen()) {
			session.close();
		}
		return true;
	}

	/**
	 * @方法说明: 批量保存（每个都提交一次）
	 * @参数: @param objs
	 * @return void
	 * @throws
	 */
	public boolean saveOrUpdateAllSeq(List<T> objs) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		try {
			for (T obj : objs) {
				// 开始事务
				tx = session.beginTransaction();
				session.saveOrUpdate(obj);
				session.flush();
				session.clear();
				tx.commit();
			}
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		}
		if (session != null && session.isOpen()) {
			session.close();
		}
		return true;
	}

	/**
	 * @方法说明: 删除
	 * @参数: @param obj
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean delete(T obj) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(obj);
			session.flush();
			session.clear();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		}
		if (session != null && session.isOpen()) {
			session.close();
		}
		return true;
	}

	/**
	 * @方法说明: 批量删除
	 * @参数: @param objs
	 * @return void
	 * @throws
	 */
	public boolean deleteAll(List<T> objs) {
		Session session = HibernateSessionFactory.getSession();
		int count = 0;
		// 开始事务
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (T obj : objs) {
				session.delete(obj);
				count++;
				if (count % FLUSH_COUNT == 0) {
					session.flush();
					session.clear();
					tx.commit();
					tx = session.beginTransaction();
				}
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		}
		if (session != null && session.isOpen()) {
			session.close();
		}
		return true;
	}

	/**
	 * @方法说明: 执行存储过程
	 * @参数: @param proc_name
	 * @return void
	 * @throws
	 */
	public void doProc(final String proc_name) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.doWork(new Work() {
				@Override
				public void execute(Connection coon) throws SQLException {
					String procedure = "{call " + proc_name + "()}";
					CallableStatement cstmt = coon.prepareCall(procedure);
					cstmt.executeUpdate();
				}
			});
			session.flush();
			session.clear();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}
