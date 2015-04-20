/**
 * @文件名称: SysConfig.java
 * @类路径:   com.rb.lottery.system
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-21 下午03:20:45
 * @版本:     1.0.0
 */
package com.rb.lottery.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类功能说明: 系统配置类
 * @类修改者: robin
 * @修改日期: 2012-3-28
 * @修改说明: 序列化
 * @作者: robin
 * @创建时间: 2011-10-21 下午03:20:45
 * @版本: 1.0.0
 */

public class SysConfig implements java.io.Serializable {

    /**
     * @Fields serialVersionUID: TODO
     */
    private static final long serialVersionUID = -1056874687951385145L;

    private static SysConfig sysconf = null;

    private boolean autoUpdate;
    private boolean autoKj;
    private boolean autoSave;
    private boolean autoCheckQihao;
    private boolean autoUpdateSoft;
    private String sfcSite;
    private String sfcKjSite;
    private String bjdcSite;
    private String jczqSite;
    private String sfcQihao;
    private String bjdcQihao;
    private String jczqPeriod;
    private Map<String, List<String>> backupsiteList;
    private int maxQihaoCount;
    private int lastSfcYear;
    private int lastSfcCount;
    private int lastBjdcMonth;
    private int lastBjdcCount;
    private int onePageCount;
    private boolean proxyUsed;

    private SysConfig() {
        autoUpdate = false;
        autoSave = false;
        maxQihaoCount = 10;
        onePageCount = 15;
        proxyUsed = false;
        backupsiteList = new HashMap<String, List<String>>();
    }

    public static SysConfig getInstance() {
        if (sysconf == null) {
            sysconf = new SysConfig();
        }
        return sysconf;
    }

    /**
     * @return autoUpdate
     */
    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    /**
     * @param autoUpdate
     *            autoUpdate
     */
    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    /**
     * @return autoKj
     */
    public boolean isAutoKj() {
        return autoKj;
    }

    /**
     * @param autoKj
     *            autoKj
     */
    public void setAutoKj(boolean autoKj) {
        this.autoKj = autoKj;
    }

    /**
     * @return autoSave
     */
    public boolean isAutoSave() {
        return autoSave;
    }

    /**
     * @param autoSave
     *            autoSave
     */
    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    /**
     * @return autoCheckQihao
     */
    public boolean isAutoCheckQihao() {
        return autoCheckQihao;
    }

    /**
     * @param autoCheckQihao
     *            autoCheckQihao
     */
    public void setAutoCheckQihao(boolean autoCheckQihao) {
        this.autoCheckQihao = autoCheckQihao;
    }

    /**
     * @return autoUpdateSoft
     */
    public boolean isAutoUpdateSoft() {
        return autoUpdateSoft;
    }

    /**
     * @param autoUpdateSoft
     *            autoUpdateSoft
     */
    public void setAutoUpdateSoft(boolean autoUpdateSoft) {
        this.autoUpdateSoft = autoUpdateSoft;
    }

    /**
     * @return sfcSite
     */
    public String getSfcSite() {
        return sfcSite;
    }

    /**
     * @param String
     *            sfcSite
     */
    public void setSfcSite(String sfcSite) {
        this.sfcSite = sfcSite;
    }

    /**
     * @return sfcKjSite
     */
    public String getSfcKjSite() {
        return sfcKjSite;
    }

    /**
     * @param sfcKjSite
     *            sfcKjSite
     */
    public void setSfcKjSite(String sfcKjSite) {
        this.sfcKjSite = sfcKjSite;
    }

    /**
     * @return bjdcSite
     */
    public String getBjdcSite() {
        return bjdcSite;
    }

    /**
     * @param bjdcSite
     *            bjdcSite
     */
    public void setBjdcSite(String bjdcSite) {
        this.bjdcSite = bjdcSite;
    }

    /**
     * @return jczqSite
     */
    public String getJczqSite() {
        return jczqSite;
    }

    /**
     * @param jczqSite
     *            jczqSite
     */
    public void setJczqSite(String jczqSite) {
        this.jczqSite = jczqSite;
    }

    /**
     * @return sfcQihao
     */
    public String getSfcQihao() {
        return sfcQihao;
    }

    /**
     * @param sfcQihao
     *            sfcQihao
     */
    public void setSfcQihao(String sfcQihao) {
        this.sfcQihao = sfcQihao;
    }

    /**
     * @return bjdQqihao
     */
    public String getBjdcQihao() {
        return bjdcQihao;
    }

    /**
     * @param bjdQqihao
     *            bjdQqihao
     */
    public void setBjdcQihao(String bjdcQihao) {
        this.bjdcQihao = bjdcQihao;
    }

    /**
     * @return jczqPeriod
     */
    public String getJczqPeriod() {
        return jczqPeriod;
    }

    /**
     * @param jczqPeriod
     *            jczqPeriod
     */
    public void setJczqPeriod(String jczqPeriod) {
        this.jczqPeriod = jczqPeriod;
    }

    /**
     * @return backupsiteList
     */
    public Map<String, List<String>> getBackupsiteList() {
        return backupsiteList;
    }

    /**
     * @return backupsiteList
     */
    public String getBackupSite(String siteType, int index) {
        String result = null;
        List<String> sites = backupsiteList.get(siteType);
        if (sites != null && sites.size() > index) {
            result = sites.get(index);
        }
        return result;
    }

    /**
     * @param backupsiteList
     *            backupsiteList
     */
    public void setBackupsiteList(Map<String, List<String>> backupsiteList) {
        this.backupsiteList = backupsiteList;
    }

    public void addBackupsite(String siteType, String url) {
        List<String> sites = backupsiteList.get(siteType);
        if (sites == null) {
            sites = new ArrayList<String>();
        }
        sites.add(url);
        backupsiteList.put(siteType, sites);
    }

    /**
     * @return maxQihaoCount
     */
    public int getMaxQihaoCount() {
        return maxQihaoCount;
    }

    /**
     * @param maxQihaoCount
     *            maxQihaoCount
     */
    public void setMaxQihaoCount(int maxQihaoCount) {
        this.maxQihaoCount = maxQihaoCount;
    }

    /**
     * @return lastSfcYear
     */
    public int getLastSfcYear() {
        return lastSfcYear;
    }

    /**
     * @param lastSfcYear
     *            lastSfcYear
     */
    public void setLastSfcYear(int lastSfcYear) {
        this.lastSfcYear = lastSfcYear;
    }

    /**
     * @return lastSfcCount
     */
    public int getLastSfcCount() {
        return lastSfcCount;
    }

    /**
     * @param lastSfcCount
     *            lastSfcCount
     */
    public void setLastSfcCount(int lastSfcCount) {
        this.lastSfcCount = lastSfcCount;
    }

    /**
     * @return lastBjdcMonth
     */
    public int getLastBjdcMonth() {
        return lastBjdcMonth;
    }

    /**
     * @param lastBjdcMonth
     *            lastBjdcMonth
     */
    public void setLastBjdcMonth(int lastBjdcMonth) {
        this.lastBjdcMonth = lastBjdcMonth;
    }

    /**
     * @return lastBjdcCount
     */
    public int getLastBjdcCount() {
        return lastBjdcCount;
    }

    /**
     * @param lastBjdcCount
     *            lastBjdcCount
     */
    public void setLastBjdcCount(int lastBjdcCount) {
        this.lastBjdcCount = lastBjdcCount;
    }

    /**
     * @return onePageCount
     */
    public int getOnePageCount() {
        return onePageCount;
    }

    /**
     * @param onePageCount
     *            onePageCount
     */
    public void setOnePageCount(int onePageCount) {
        this.onePageCount = onePageCount;
    }

    /**
     * @return proxyUsed
     */
    public boolean isProxyUsed() {
        return proxyUsed;
    }

    /**
     * @param proxyUsed
     *            proxyUsed
     */
    public void setProxyUsed(boolean proxyUsed) {
        this.proxyUsed = proxyUsed;
    }

    @Override
    public Object clone() {
        return this;
    }

    /**
     * @方法说明: 初始化
     * @参数: @param sys
     * @return void
     * @throws
     */
    public static void init(SysConfig sys) {
        sysconf = (SysConfig) sys.clone();
    }
}
