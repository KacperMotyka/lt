/**
 * @文件名称: UserDAO.java
 * @类路径:   com.rb.lottery.dao
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-2-22 下午04:03:11
 * @版本:     1.0.0
 */
package com.rb.lottery.server.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rb.lottery.domain.BetForm;
import com.rb.lottery.domain.CapitalInfo;
import com.rb.lottery.domain.TradeInfo;
import com.rb.lottery.user.User;
import com.rb.lottery.user.UserInfo;
import com.rb.lottery.user.UserSfQa;

/**
 * @类功能说明: 用户信息数据库操作
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-2-22 下午04:03:11
 * @版本: 1.0.0
 */

public class UserDAO extends BasicDAO {

	private static final Logger log = Logger.getLogger(UserDAO.class);

	public User getUserByName(String username) {
		String hql = "from User u where u.username = ?";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, username);
		List<User> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public boolean isExistUserName(String username) {
		User user = getUserByName(username);
		if (user == null) {
			return false;
		}
		return true;
	}

	/**
	 * @方法说明:注册用户
	 * @参数: @param user
	 * @return void
	 * @throws
	 */
	public boolean register(User user, UserInfo userInfo) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(user);
			session.flush();
			session.clear();
			tx.commit();
			tx = session.beginTransaction();
			session.save(userInfo);
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

	/**
	 * @方法说明:添加用户信息
	 * @参数: @param usrInfo
	 * @return void
	 * @throws
	 */
	public void addUserInfo(UserInfo usrInfo) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(usrInfo);
			session.flush();
			session.clear();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	/**
	 * @方法说明: 登录
	 * @参数: @param username
	 * @参数: @param password
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public int login(String username, String password) {
		String hql = "from User u where u.username = ? and u.password = ?";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, username);
		query.setString(1, password);
		List list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		if (list != null && list.size() > 0) {
			return 0;
		}
		if (isExistUserName(username)) {
			return 1;
		}
		return 2;
	}

	/**
	 * @方法说明: 通过ID查询用户
	 * @参数: @param string
	 * @参数: @return
	 * @return User
	 * @throws
	 */
	public User getUserById(String uid) {
		String hql = "from User u where u.userid = ?";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, uid);
		List<User> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @方法说明: 更改用户信息
	 * @参数: @param user
	 * @return boolean
	 * @throws
	 */
	public boolean updateUser(User user) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(user);
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

	/**
	 * @方法说明: 查询用户密保问题
	 * @参数: @param userid
	 * @参数: @return
	 * @return List<UserSfQa>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<UserSfQa> getSafeQaByUserId(String userid) {
		String hql = "from UserSfQa u where u.user.userid = ?";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, userid);
		List<UserSfQa> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		return list;
	}

	/**
	 * @方法说明: 删除密保问题
	 * @参数: @param userid
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean deleteUserSfQaByUserId(String userid) {
		String hql = "delete UserSfQa u where u.user.userid = ?";
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString(0, userid);
			query.executeUpdate();
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

	/**
	 * @方法说明: 更新用户密保问题，先删除原密保问题
	 * @参数: @param sfqa
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean updateUserSfQa(List<UserSfQa> qas) {
		if (qas == null || qas.size() == 0) {
			return true;
		}
		String userid = qas.get(0).getUser().getUserid();
		boolean del = this.deleteUserSfQaByUserId(userid);
		if (del) {
			boolean su = this.saveOrUpdateAll(qas);
			if (!su) {
				return false;
			}
		} else {
			log.info("删除原密保问题失败");
			return false;
		}
		return true;
	}

	/**
	 * @方法说明:密保验证
	 * @参数: @param sfqas
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean checkSfQa(List<UserSfQa> sfqas) {
		if (sfqas == null || sfqas.size() == 0) {
			return true;
		}
		String userid = sfqas.get(0).getUser().getUserid();
		boolean isPass = true;
		String hql = "from UserSfQa u where u.user.userid = ? and u.question = ? and u.answer = ?";
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, userid);
		for (UserSfQa qa : sfqas) {
			query.setString(1, qa.getQuestion());
			query.setString(2, qa.getAnswer());
			List list = query.list();
			if (list == null || list.size() == 0) {
				isPass = false;
				break;
			}
		}
		return isPass;
	}

	/**
	 * @方法说明: 获取资产明细
	 * @参数: @param userid
	 * @参数: @return
	 * @return CapitalInfo
	 * @throws
	 */
	public CapitalInfo getCapitalInfoByUserId(String userid) {
		Session session = HibernateSessionFactory.getSession();
		String hql = "from CapitalInfo c where c.userid = ?";
		Query query = session.createQuery(hql);
		query.setString(0, userid);
		List<CapitalInfo> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @方法说明: 更新用户明细表
	 * @参数: @param cap
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean updateCapitalInfo(CapitalInfo cap) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(cap);
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

	/**
	 * @方法说明:添加交易记录
	 * @参数: @param trade
	 * @return void
	 * @throws
	 */
	public boolean addTradeInfo(TradeInfo trade) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(trade);
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

	/**
	 * @方法说明: 保存投注信息
	 * @参数: @param betInfo
	 * @参数: @return
	 * @return boolean
	 * @throws
	 */
	public boolean addBetInfo(BetForm betInfo) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(betInfo);
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

	/**
	 * @方法说明: 获取用户信息
	 * @参数: @param userid
	 * @参数: @return
	 * @return UserInfo
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public UserInfo getUserInfoById(String userid) {
		Session session = HibernateSessionFactory.getSession();
		String hql = "from UserInfo ui where ui.userid = ?";
		Query query = session.createQuery(hql);
		query.setString(0, userid);
		List<UserInfo> list = query.list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
