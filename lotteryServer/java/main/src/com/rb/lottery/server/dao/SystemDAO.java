/**
 * @文件名称: SystemDAO.java
 * @类路径:   com.rb.lottery.server.dao
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-23 下午01:28:55
 * @版本:     1.0.0
 */
package com.rb.lottery.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rb.lottery.system.SystemEnvironment;

/**
 * @类功能说明: 系统配置DAO
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-23 下午01:28:55
 * @版本: 1.0.0
 */

public class SystemDAO extends BasicDAO {

	/**
	 * @方法说明: 获取所有有效版本
	 * @参数:     @return
	 * @return    String[]
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String[] getAllAvailableVersion() {
		String hql = "from SystemEnvironment se where se.deprecated = 1 order by se.version asc";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		List<SystemEnvironment> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		String[] versions = null;
		if (list != null) {
			int len = list.size();
			versions = new String[len];
			for (int i = 0; i < len; i++) {
				versions[i] = list.get(i).getVersion();
			}
		}
		return versions;
	}

	/**
	 * @方法说明: 获取环境变量
	 * @参数:     @param version
	 * @return    void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public SystemEnvironment getEnvironmentByVersion(String version) {
		String hql = "from SystemEnvironment se where se.version = ?";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, version);
		List<SystemEnvironment> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
