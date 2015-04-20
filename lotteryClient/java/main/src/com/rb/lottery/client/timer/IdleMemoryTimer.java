/**
 * @文件名称: IdleMemoryTimer.java
 * @类路径:   com.rb.lottery.schdule
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-8 下午04:53:12
 * @版本:     1.0.0
 */
package com.rb.lottery.client.timer;

import java.lang.management.ManagementFactory;
import java.util.TimerTask;

import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.system.SystemProcessor;
import com.sun.management.OperatingSystemMXBean;

/**
 * @类功能说明: 空闲内存检测线程
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-8 下午04:53:12
 * @版本: 1.0.0
 */

public class IdleMemoryTimer extends TimerTask {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.TimerTask#run()
     */
    @Override
    public void run() {
        // Runtime rt = Runtime.getRuntime();
        // String free = Math.round(rt.freeMemory() / 1024.0) +
        // SystemConstants.MB;
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        String free = Math.round(osmxb.getFreePhysicalMemorySize() / 1048576.0) + SystemConstants.MB;
        SystemProcessor.setIdleMemory(free);
    }
}
