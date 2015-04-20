/**
 * @文件名称: CompositeId.java
 * @类路径:   com.rb.lottery.domain
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-26 下午01:14:03
 * @版本:     1.0.0
 */
package com.rb.lottery.domain;

import java.io.Serializable;

import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-26 下午01:14:03
 * @版本: 1.0.0
 */

public class CompositeId implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = 6637764347764080559L;

	private String qihao;
	private String id;

	public CompositeId() {
		qihao = SystemConstants.EMPTY_STRING;
		id = SystemConstants.EMPTY_STRING;
	}

	/**
	 * @return qihao
	 */
	public String getQihao() {
		return qihao;
	}

	/**
	 * @param qihao
	 *            qihao
	 */
	public void setQihao(String qihao) {
		this.qihao = qihao;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((qihao == null) ? 0 : qihao.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeId other = (CompositeId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (qihao == null) {
			if (other.qihao != null)
				return false;
		} else if (!qihao.equals(other.qihao))
			return false;
		return true;
	}

}
