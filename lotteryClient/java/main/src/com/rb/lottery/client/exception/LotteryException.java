/**
 * @文件名称: LotteryException.java
 * @类路径:   com.rb.lottery.exception
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-10-25 上午10:56:16
 * @版本:     1.0.0
 */
package com.rb.lottery.client.exception;

import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.manager.IOManager;

/**
 * @类功能说明: 自定义异常类
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-10-25 上午10:56:16
 * @版本: 1.0.0
 */

public class LotteryException extends Throwable {

    /**
     * @Fields serialVersionUID: TODO
     */
    private static final long serialVersionUID = 7563592022665249215L;

    private int code;
    private String message;

    public LotteryException() {
        super();
    }

    public LotteryException(String msg) {
        super(msg);
    }

    public LotteryException(Throwable cause) {
        super(cause);
    }

    public LotteryException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public LotteryException(int code) {
        this.code = code;
        IOManager iom = IOManager.getInstance();
        iom.setInfile(FilePathConstants.EXCEPTION_CONFIG_FILE);
        this.message = iom.getLotteryException(code).getMessage();
    }

    /**
     * @return code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code
     *            code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
