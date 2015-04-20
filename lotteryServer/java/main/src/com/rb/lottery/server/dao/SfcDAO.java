/**
 * @文件名称: SfcDAO.java
 * @类路径:   dao
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-25 上午09:00:10
 * @版本:     1.0.0
 */
package com.rb.lottery.server.dao;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rb.lottery.domain.SfcKj;
import com.rb.lottery.domain.SfcMatch;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-25 上午09:00:10
 * @版本: 1.0.0
 */

public class SfcDAO extends BasicDAO {

	private static final Logger log = Logger.getLogger(SfcDAO.class);

	@SuppressWarnings("unchecked")
	public SfcMatch getSfcMatchByQhId(String qihao, String id) {
		String hql = "from SfcMatch s where s.id.qihao=? and s.id.id=?";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, qihao);
		query.setString(1, id);
		List<SfcMatch> list = query.list();
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
	 * @参数:     @param qihao
	 * @参数:     @return
	 * @return    List<SfcMatch>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<SfcMatch> getSfcMatchesByQh(String qihao) {
		String hql = "from SfcMatch s where s.id.qihao=? order by s.id.id asc";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, qihao);
		List<SfcMatch> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		return list;
	}

	/**
	 * @方法说明: 
	 * @参数:     @param sfckj
	 * @return    void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean updateKjNumber(SfcKj sfckj) {
		boolean pass = true;
		String qihao = sfckj.getQihao();
		String kj = sfckj.getKjNumber();
		String []kjs = kj.split(" ");
		List<SfcMatch> matches = getSfcMatchesByQh(qihao);
		for(int i = 0; i < 14; i++) {
			SfcMatch match = matches.get(i);
			match.setKjNumber(kjs[i].charAt(0));
			pass = pass && saveOrUpdate(match);
		}
		return pass;
	}
}
