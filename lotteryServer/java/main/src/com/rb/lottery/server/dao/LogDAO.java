/**
 * @文件名称: LogDAO.java
 * @类路径:   com.rb.lottery.dao
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-23 下午05:31:33
 * @版本:     1.0.0
 */
package com.rb.lottery.server.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rb.lottery.server.util.Log;

/**
 * @类功能说明: 日志操作
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-23 下午05:31:33
 * @版本: 1.0.0
 */

public class LogDAO extends BasicDAO {

	private static final Logger log = Logger.getLogger(LogDAO.class);

	public boolean addLog(Log mylog) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(mylog);
			session.flush();
			session.clear();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return true;
	}
}
