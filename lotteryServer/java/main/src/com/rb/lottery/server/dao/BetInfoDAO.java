/**
 * @文件名称: BetInfoDAO.java
 * @类路径:   com.rb.lottery.dao
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-12 下午08:39:32
 * @版本:     1.0.0
 */
package com.rb.lottery.server.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.rb.lottery.domain.BetForm;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-12 下午08:39:32
 * @版本: 1.0.0
 */

public class BetInfoDAO extends BasicDAO {

	private static final Logger log = Logger.getLogger(BetInfoDAO.class);

	/**
	 * @方法说明: 获取用户投注信息
	 * @参数: @param uid
	 * @参数: @return
	 * @return List<BetForm>
	 * @throws
	 */
	public List<BetForm> getBetFormsByUserId(String uid) {
		String hql = "from BetForm b where b.user.userid=? order by b.btime desc";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, uid);
		List<BetForm> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		return list;
	}

}
