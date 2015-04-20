/**
 * @文件名称: BjdcDAO.java
 * @类路径:   com.rb.lottery.dao
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-30 下午05:05:53
 * @版本:     1.0.0
 */
package com.rb.lottery.server.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.rb.lottery.domain.BjdcMatch;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-30 下午05:05:53
 * @版本: 1.0.0
 */

public class BjdcDAO extends BasicDAO {

	private static final Logger log = Logger.getLogger(BjdcDAO.class);

	@SuppressWarnings("unchecked")
	public BjdcMatch getBjdcMatchByQhId(String qihao, String id) {
		String hql = "from BjdcMatch b where b.id.qihao=? and b.id.id=?";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, qihao);
		query.setString(1, id);
		List<BjdcMatch> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @方法说明:
	 * @参数: @param qihao
	 * @参数: @return
	 * @return List<BjdcMatch>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<BjdcMatch> getBjdcMatchesByQh(String qihao) {
		String hql = "from BjdcMatch s where s.id.qihao=?";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, qihao);
		List<BjdcMatch> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		return list;
	}

	/**
	 * @方法说明: 批量获取比赛
	 * @参数: @param qihao
	 * @参数: @param mids
	 * @参数: @return
	 * @return List<BjdcMatch>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<BjdcMatch> getBjdcMatchesByQhIds(String qihao, Set<String> mids) {
		if (mids == null || mids.size() == 0) {
			return null;
		}
		int sz = mids.size();
		String hql = "from BjdcMatch s where s.id.qihao=? and (";
		for (int i = 0; i < sz; i++) {
			if (i == 0) {
				hql += "s.id.id=?";
			} else {
				hql += " or s.id.id=?";
			}
		}
		hql += ")";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, qihao);
		Iterator it = mids.iterator();
		for (int i = 1; i <= sz; i++) {
			query.setString(i, (String) it.next());
		}
		List<BjdcMatch> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		return list;
	}

	/**
	 * @方法说明: 批量获取比赛
	 * @参数: @param qihao
	 * @参数: @param mids
	 * @参数: @return
	 * @return List<BjdcMatch>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<BjdcMatch> getBjdcMatchesByQhIds(String qihao, String[] mids) {
		if (mids == null || mids.length == 0) {
			return null;
		}
		int sz = mids.length;
		String hql = "from BjdcMatch s where s.id.qihao=? and (";
		for (int i = 0; i < sz; i++) {
			if (i == 0) {
				hql += "s.id.id=?";
			} else {
				hql += " or s.id.id=?";
			}
		}
		hql += ")";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, qihao);
		for (int i = 0; i < sz; i++) {
			query.setString(i + 1, mids[i]);
		}
		List<BjdcMatch> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		return list;
	}
}
