/**
 * @文件名称: SfcKjDAO.java
 * @类路径:   com.rb.lottery.dao
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-30 下午05:01:09
 * @版本:     1.0.0
 */
package com.rb.lottery.server.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.rb.lottery.domain.SfcKj;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-30 下午05:01:09
 * @版本: 1.0.0
 */

public class SfcKjDAO extends BasicDAO {

	Logger log = Logger.getLogger(SfcKjDAO.class);

	@SuppressWarnings("unchecked")
	public SfcKj getSfcKjByQihao(String qihao) {
		String hql = "from SfcKj s where s.qihao=?";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, qihao);
		List<SfcKj> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
