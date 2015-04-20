/**
 * @文件名称: KjDAO.java
 * @类路径:   com.rb.lottery.dao
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-5 下午05:05:37
 * @版本:     1.0.0
 */
package com.rb.lottery.server.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import com.rb.lottery.domain.BetForm;
import com.rb.lottery.system.SystemProcessor;

/**
 * @类功能说明: 自动开奖处理
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-5 下午05:05:37
 * @版本: 1.0.0
 */

public class KjDAO extends BasicDAO {

	private static final Logger log = Logger.getLogger(KjDAO.class);
	
	private static final String KJ_PROC_NAME = "KJPROC";

	/**
	 * @方法说明: 开奖
	 * @参数:
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean doAutoKj() {
		log.info("开奖...");
		Session session = null;
		Transaction tx = null;
		Query query = null;
		String hql = null;
		try {
			// 更新开奖号码
			log.info("更新开奖号码...");
			doProc(KJ_PROC_NAME);
			// 开奖
			log.info("开始开奖...");
			session = HibernateSessionFactory.getSession();
			hql = "from BetForm b where b.status=3 and b.winflag=-1 and b.kjnumbers is not null";
			query = session.createQuery(hql);
			List<BetForm> blist = query.list();
			if (blist != null && blist.size() > 0) {
				long ltType;
				for (BetForm bet : blist) {
					ltType = bet.getLtType();
					if (0 == ltType) { // 14场胜负彩
						SystemProcessor.doSfc14Kj(bet);
					} else if (1 == ltType) { // 胜负彩任选9场
						SystemProcessor.doSfcR9Kj(bet);
					} else if (2 == ltType) { // 北单
						SystemProcessor.doBjdcKj(bet);
					} else if (3 == ltType) { // 竞彩
						SystemProcessor.doJczqKj(bet);
					}
					// 更新订单信息，触发开奖过程
					if(!session.isOpen()) {
						session = HibernateSessionFactory.getSession();
					}
					tx = session.beginTransaction();
					session.update(bet);
					session.flush();
					session.clear();
					tx.commit();
				}
			}
			log.info("开奖结束.");
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return true;
	}
}