/**
 * @文件名称: JczqDAO.java
 * @类路径:   com.rb.lottery.dao
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-30 下午05:24:26
 * @版本:     1.0.0
 */
package com.rb.lottery.server.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.rb.lottery.domain.JczqMatch;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-30 下午05:24:26
 * @版本: 1.0.0
 */

public class JczqDAO extends BasicDAO {

	private static final Logger log = Logger.getLogger(JczqDAO.class);

	@SuppressWarnings("unchecked")
	public JczqMatch getJczqMatchByQhId(String qihao, String id) {
		String hql = "from JczqMatch s where s.id.qihao=? and s.id.id=?";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, qihao);
		query.setString(1, id);
		List<JczqMatch> list = query.list();
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
	 * @return List<JczqMatch>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<JczqMatch> getJczqMatchesByQh(String qihao) {
		String hql = "from JczqMatch s where s.id.qihao=?";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, qihao);
		List<JczqMatch> list = query.list();
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
	 * @return List<JczqMatch>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<JczqMatch> getJczqMatchesByQhIds(String qihao, Set<String> mids) {
		if (mids == null || mids.size() == 0) {
			return null;
		}
		int sz = mids.size();
		String hql = "from JczqMatch s where s.id.qihao=? and (";
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
		List<JczqMatch> list = query.list();
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
	 * @return List<JczqMatch>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<JczqMatch> getJczqMatchesByQhIds(String qihao, String[] mids) {
		if (mids == null || mids.length == 0) {
			return null;
		}
		int sz = mids.length;
		String hql = "from JczqMatch s where s.id.qihao=? and (";
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
		List<JczqMatch> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		return list;
	}
}
