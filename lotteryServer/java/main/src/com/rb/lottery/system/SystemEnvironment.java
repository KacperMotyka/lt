/**
 * @文件名称: SystemEnvironment.java
 * @类路径:   com.rb.lottery.server.system
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2012-3-23 上午10:20:15
 * @版本:     1.0.0
 */
package com.rb.lottery.system;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * @类功能说明: 系统环境
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2012-3-23 上午10:20:15
 * @版本: 1.0.0
 */

public class SystemEnvironment implements Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 1073227556086337588L;

	private static SystemEnvironment sysenv = null;

	// 系统名称(客户端标题)
	public String name;
	// 系统版本号
	public String version;
	// 该版本是否已废弃 0-已废弃 1-仍可使用
	public Integer deprecated;
	// 客户端浏览器配置
	public String useragent;
	// 收费模式 0-免费模式 1-收费模式
	public Integer fee_mode;
	// 投注时间格式
	public String datefmt;
	public DateFormat dateformat;
	// 金额小数位数格式
	public String moneyfmt;
	public DecimalFormat moneyformat;
	// 系统投注数据延时更新时间(ms)
	public Long delay_period;
	// 系统投注数据定时更新周期(ms)
	public Long update_period;
	// 系统日志默认保存天数
	public Long log_keep_days;
	// 网络连接超时时间
	public Long connection_timeout;
	// 数据保存模式 1-文件 2- 数据库 3-both
	public Integer save_mode;
	// 交易&资产记录更新模式 0-手动 1-触发器
	public Integer trade_mode;

	// 默认页面大小
	public Integer default_page_size;
	// 胜负彩数据更新提前天数
	public Integer sfc_update_ahead;
	// 北京单场数据更新提前天数
	public Integer bjdc_update_ahead;
	// 竞彩足球数据更新提前天数
	public Integer jczq_update_ahead;
	// 密保验证问题数
	public Integer qa_max_answer;
	// 密保验证最大失败数-账户锁定
	public Integer max_cpd_check;
	// 充值积分系数
	public Double recharge_factor;
	// 免费模式下最大累计充值额度
	public Long max_recharge;
	// 彩票每注金额
	public Long money_per_bet;
	// 单式投注比赛开始前截至时间(分钟)
	public Long single_bet_deadline;
	// 单式投注比赛开始前截至时间(分钟)
	public Long multi_bet_deadline;
	// 单注奖金缴税限定(超过则需缴税)
	public Double tax_reward;
	// 单注奖金超过缴税限定的缴税比例
	public Double tax_rate;
	// 北京单场开奖奖金系数
	public Double bjdc_rate;
	// 竞彩足球浮动场次开奖奖金系数（单场投注为浮动奖金开奖）
	public Double jczq_rate;

	// 多线程socket服务器端端口号
	public Integer server_port;
	// 单个CPU线程池大小
	public Integer thread_pool_size;
	// 帐户密码密钥
	public String account_encrypt_key;
	// 支付密码密钥
	public String pay_encrypt_key;

	public static SystemEnvironment getInstance() {
		if (sysenv == null) {
			sysenv = new SystemEnvironment();
		}
		return sysenv;
	}

	private SystemEnvironment() {

	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return deprecated
	 */
	public Integer getDeprecated() {
		return deprecated;
	}

	/**
	 * @param deprecated
	 *            deprecated
	 */
	public void setDeprecated(Integer deprecated) {
		this.deprecated = deprecated;
	}

	/**
	 * @return useragent
	 */
	public String getUseragent() {
		return useragent;
	}

	/**
	 * @param useragent
	 *            useragent
	 */
	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}

	/**
	 * @return fee_mode
	 */
	public Integer getFee_mode() {
		return fee_mode;
	}

	/**
	 * @param feeMode
	 *            fee_mode
	 */
	public void setFee_mode(Integer feeMode) {
		fee_mode = feeMode;
	}

	/**
	 * @return datefmt
	 */
	public String getDatefmt() {
		return datefmt;
	}

	/**
	 * @param datefmt
	 *            datefmt
	 */
	public void setDatefmt(String datefmt) {
		this.datefmt = datefmt;
		this.dateformat = new SimpleDateFormat(this.datefmt);
	}

	/**
	 * @return dateformat
	 */
	public DateFormat getDateformat() {
		this.dateformat = new SimpleDateFormat(this.datefmt);
		return dateformat;
	}

	/**
	 * @param dateformat
	 *            dateformat
	 */
	public void setDateformat(SimpleDateFormat dateformat) {
		this.dateformat = dateformat;
	}

	/**
	 * @return moneyfmt
	 */
	public String getMoneyfmt() {
		return moneyfmt;
	}

	/**
	 * @param moneyfmt
	 *            moneyfmt
	 */
	public void setMoneyfmt(String moneyfmt) {
		this.moneyfmt = moneyfmt;
		this.moneyformat = new DecimalFormat(this.moneyfmt);
	}

	/**
	 * @return moneyformat
	 */
	public DecimalFormat getMoneyformat() {
		this.moneyformat = new DecimalFormat(this.moneyfmt);
		return moneyformat;
	}

	/**
	 * @param moneyformat
	 *            moneyformat
	 */
	public void setMoneyformat(DecimalFormat moneyformat) {
		this.moneyformat = moneyformat;
	}

	/**
	 * @return delay_period
	 */
	public Long getDelay_period() {
		return delay_period;
	}

	/**
	 * @param delayPeriod
	 *            delay_period
	 */
	public void setDelay_period(Long delayPeriod) {
		delay_period = delayPeriod;
	}

	/**
	 * @return update_period
	 */
	public Long getUpdate_period() {
		return update_period;
	}

	/**
	 * @param updatePeriod
	 *            update_period
	 */
	public void setUpdate_period(Long updatePeriod) {
		update_period = updatePeriod;
	}

	/**
	 * @return log_keep_days
	 */
	public Long getLog_keep_days() {
		return log_keep_days;
	}

	/**
	 * @param logKeepDays
	 *            log_keep_days
	 */
	public void setLog_keep_days(Long logKeepDays) {
		log_keep_days = logKeepDays;
	}

	/**
	 * @return connection_timeout
	 */
	public Long getConnection_timeout() {
		return connection_timeout;
	}

	/**
	 * @param connectionTimeout
	 *            connection_timeout
	 */
	public void setConnection_timeout(Long connectionTimeout) {
		connection_timeout = connectionTimeout;
	}

	/**
	 * @return save_mode
	 */
	public Integer getSave_mode() {
		return save_mode;
	}

	/**
	 * @param saveMode
	 *            save_mode
	 */
	public void setSave_mode(Integer saveMode) {
		save_mode = saveMode;
	}

	/**
	 * @return trade_mode
	 */
	public Integer getTrade_mode() {
		return trade_mode;
	}

	/**
	 * @param tradeMode
	 *            trade_mode
	 */
	public void setTrade_mode(Integer tradeMode) {
		trade_mode = tradeMode;
	}

	/**
	 * @return default_page_size
	 */
	public Integer getDefault_page_size() {
		return default_page_size;
	}

	/**
	 * @param defaultPageSize
	 *            default_page_size
	 */
	public void setDefault_page_size(Integer defaultPageSize) {
		default_page_size = defaultPageSize;
	}

	/**
	 * @return sfc_update_ahead
	 */
	public Integer getSfc_update_ahead() {
		return sfc_update_ahead;
	}

	/**
	 * @param sfcUpdateAhead
	 *            sfc_update_ahead
	 */
	public void setSfc_update_ahead(Integer sfcUpdateAhead) {
		sfc_update_ahead = sfcUpdateAhead;
	}

	/**
	 * @return bjdc_update_ahead
	 */
	public Integer getBjdc_update_ahead() {
		return bjdc_update_ahead;
	}

	/**
	 * @param bjdcUpdateAhead
	 *            bjdc_update_ahead
	 */
	public void setBjdc_update_ahead(Integer bjdcUpdateAhead) {
		bjdc_update_ahead = bjdcUpdateAhead;
	}

	/**
	 * @return jczq_update_ahead
	 */
	public Integer getJczq_update_ahead() {
		return jczq_update_ahead;
	}

	/**
	 * @param jczqUpdateAhead
	 *            jczq_update_ahead
	 */
	public void setJczq_update_ahead(Integer jczqUpdateAhead) {
		jczq_update_ahead = jczqUpdateAhead;
	}

	/**
	 * @return qa_max_answer
	 */
	public Integer getQa_max_answer() {
		return qa_max_answer;
	}

	/**
	 * @param qaMaxAnswer
	 *            qa_max_answer
	 */
	public void setQa_max_answer(Integer qaMaxAnswer) {
		qa_max_answer = qaMaxAnswer;
	}

	/**
	 * @return max_cpd_check
	 */
	public Integer getMax_cpd_check() {
		return max_cpd_check;
	}

	/**
	 * @param maxCpdCheck
	 *            max_cpd_check
	 */
	public void setMax_cpd_check(Integer maxCpdCheck) {
		max_cpd_check = maxCpdCheck;
	}

	/**
	 * @return recharge_factor
	 */
	public Double getRecharge_factor() {
		return recharge_factor;
	}

	/**
	 * @param rechargeFactor
	 *            recharge_factor
	 */
	public void setRecharge_factor(Double rechargeFactor) {
		recharge_factor = rechargeFactor;
	}

	/**
	 * @return max_recharge
	 */
	public Long getMax_recharge() {
		return max_recharge;
	}

	/**
	 * @param maxRecharge
	 *            max_recharge
	 */
	public void setMax_recharge(Long maxRecharge) {
		max_recharge = maxRecharge;
	}

	/**
	 * @return money_per_bet
	 */
	public Long getMoney_per_bet() {
		return money_per_bet;
	}

	/**
	 * @param moneyPerBet
	 *            money_per_bet
	 */
	public void setMoney_per_bet(Long moneyPerBet) {
		money_per_bet = moneyPerBet;
	}

	/**
	 * @return single_bet_deadline
	 */
	public Long getSingle_bet_deadline() {
		return single_bet_deadline;
	}

	/**
	 * @param singleBetDeadline
	 *            single_bet_deadline
	 */
	public void setSingle_bet_deadline(Long singleBetDeadline) {
		single_bet_deadline = singleBetDeadline;
	}

	/**
	 * @return multi_bet_deadline
	 */
	public Long getMulti_bet_deadline() {
		return multi_bet_deadline;
	}

	/**
	 * @param multiBetDeadline
	 *            multi_bet_deadline
	 */
	public void setMulti_bet_deadline(Long multiBetDeadline) {
		multi_bet_deadline = multiBetDeadline;
	}

	/**
	 * @return tax_reward
	 */
	public Double getTax_reward() {
		return tax_reward;
	}

	/**
	 * @param taxReward
	 *            tax_reward
	 */
	public void setTax_reward(Double taxReward) {
		tax_reward = taxReward;
	}

	/**
	 * @return tax_rate
	 */
	public Double getTax_rate() {
		return tax_rate;
	}

	/**
	 * @param taxRate
	 *            tax_rate
	 */
	public void setTax_rate(Double taxRate) {
		tax_rate = taxRate;
	}

	/**
	 * @return bjdc_rate
	 */
	public Double getBjdc_rate() {
		return bjdc_rate;
	}

	/**
	 * @param bjdcRate
	 *            bjdc_rate
	 */
	public void setBjdc_rate(Double bjdcRate) {
		bjdc_rate = bjdcRate;
	}

	/**
	 * @return jczq_rate
	 */
	public Double getJczq_rate() {
		return jczq_rate;
	}

	/**
	 * @param jczqRate
	 *            jczq_rate
	 */
	public void setJczq_rate(Double jczqRate) {
		jczq_rate = jczqRate;
	}

	/**
	 * @return server_port
	 */
	public Integer getServer_port() {
		return server_port;
	}

	/**
	 * @param serverPort
	 *            server_port
	 */
	public void setServer_port(Integer serverPort) {
		server_port = serverPort;
	}

	/**
	 * @return thread_pool_size
	 */
	public Integer getThread_pool_size() {
		return thread_pool_size;
	}

	/**
	 * @param threadPoolSize
	 *            thread_pool_size
	 */
	public void setThread_pool_size(Integer threadPoolSize) {
		thread_pool_size = threadPoolSize;
	}
	
	/**
	 * @return account_encrypt_key
	 */
	public String getAccount_encrypt_key() {
		return account_encrypt_key;
	}

	/**
	 * @param accountEncryptKey
	 *            account_encrypt_key
	 */
	public void setAccount_encrypt_key(String accountEncryptKey) {
		account_encrypt_key = accountEncryptKey;
	}

	/**
	 * @return pay_encrypt_key
	 */
	public String getPay_encrypt_key() {
		return pay_encrypt_key;
	}

	/**
	 * @param payEncryptKey
	 *            pay_encrypt_key
	 */
	public void setPay_encrypt_key(String payEncryptKey) {
		pay_encrypt_key = payEncryptKey;
	}
	
	/**
	 * @方法说明: 初始化
	 * @参数: @param env
	 * @return void
	 * @throws
	 */
	public static void init(SystemEnvironment env) {
		SystemEnvironment thisEnv = SystemEnvironment.getInstance();
		thisEnv.version = env.getVersion();
		SystemCache.current_version = thisEnv.version;
		thisEnv.name = env.getName();
		thisEnv.deprecated = env.getDeprecated();
		thisEnv.useragent = env.getUseragent();
		thisEnv.fee_mode = env.getFee_mode();
		SystemCache.fee_mode = thisEnv.fee_mode;
		thisEnv.datefmt = env.getDatefmt();
		thisEnv.dateformat = new SimpleDateFormat(env.getDatefmt());
		thisEnv.moneyfmt = env.getMoneyfmt();
		thisEnv.moneyformat = new DecimalFormat(env.getMoneyfmt());
		thisEnv.delay_period = env.getDelay_period();
		thisEnv.update_period = env.getUpdate_period();
		thisEnv.log_keep_days = env.getLog_keep_days();
		thisEnv.connection_timeout = env.getConnection_timeout();
		thisEnv.save_mode = env.getSave_mode();
		SystemCache.save_mode = thisEnv.save_mode;
		thisEnv.trade_mode = env.getTrade_mode();
		SystemCache.trade_mode = thisEnv.trade_mode;
		thisEnv.default_page_size = env.getDefault_page_size();
		thisEnv.sfc_update_ahead = env.getSfc_update_ahead();
		thisEnv.bjdc_update_ahead = env.getBjdc_update_ahead();
		thisEnv.jczq_update_ahead = env.getJczq_update_ahead();
		thisEnv.qa_max_answer = env.getQa_max_answer();
		thisEnv.max_cpd_check = env.getMax_cpd_check();
		thisEnv.recharge_factor = env.getRecharge_factor();
		thisEnv.max_recharge = env.getMax_recharge();
		thisEnv.money_per_bet = env.getMoney_per_bet();
		thisEnv.single_bet_deadline = env.getSingle_bet_deadline();
		thisEnv.multi_bet_deadline = env.getMulti_bet_deadline();
		thisEnv.tax_reward = env.getTax_reward();
		thisEnv.tax_rate = env.getTax_rate();
		thisEnv.bjdc_rate = env.getBjdc_rate();
		thisEnv.jczq_rate = env.getJczq_rate();
		thisEnv.server_port = env.getServer_port();
		thisEnv.thread_pool_size = env.getThread_pool_size();
		thisEnv.account_encrypt_key = env.getAccount_encrypt_key();
		thisEnv.pay_encrypt_key = env.getPay_encrypt_key();
	}

	/**
	 * @方法说明: 复制
	 * @参数: @return
	 * @return this
	 * @throws
	 */
	public SystemEnvironment copy() {
		SystemEnvironment sysEnv = new SystemEnvironment();
		sysEnv.setVersion(version);
		sysEnv.setName(name);
		sysEnv.setDeprecated(deprecated);
		sysEnv.setUseragent(useragent);
		sysEnv.setFee_mode(fee_mode);
		sysEnv.setDatefmt(datefmt);
		sysEnv.setDateformat(new SimpleDateFormat(datefmt));
		sysEnv.setMoneyfmt(moneyfmt);
		sysEnv.setMoneyformat(new DecimalFormat(moneyfmt));
		sysEnv.setDelay_period(delay_period);
		sysEnv.setUpdate_period(update_period);
		sysEnv.setLog_keep_days(log_keep_days);
		sysEnv.setConnection_timeout(connection_timeout);
		sysEnv.setSave_mode(save_mode);
		sysEnv.setTrade_mode(trade_mode);
		sysEnv.setDefault_page_size(default_page_size);
		sysEnv.setSfc_update_ahead(sfc_update_ahead);
		sysEnv.setBjdc_update_ahead(bjdc_update_ahead);
		sysEnv.setJczq_update_ahead(jczq_update_ahead);
		sysEnv.setQa_max_answer(qa_max_answer);
		sysEnv.setMax_cpd_check(max_cpd_check);
		sysEnv.setRecharge_factor(recharge_factor);
		sysEnv.setMax_recharge(max_recharge);
		sysEnv.setMoney_per_bet(money_per_bet);
		sysEnv.setSingle_bet_deadline(single_bet_deadline);
		sysEnv.setMulti_bet_deadline(multi_bet_deadline);
		sysEnv.setTax_reward(tax_reward);
		sysEnv.setTax_rate(tax_rate);
		sysEnv.setBjdc_rate(bjdc_rate);
		sysEnv.setJczq_rate(jczq_rate);
		sysEnv.setServer_port(server_port);
		sysEnv.setThread_pool_size(thread_pool_size);
		sysEnv.setAccount_encrypt_key(account_encrypt_key);
		sysEnv.setPay_encrypt_key(pay_encrypt_key);
		return sysEnv;
	}
}
