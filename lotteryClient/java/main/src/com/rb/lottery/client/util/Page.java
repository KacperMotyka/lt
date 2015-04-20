/**
 * @文件名称: Page.java
 * @类路径:   com.rb.lottery.util
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-17 下午01:06:36
 * @版本:     1.0.0
 */
package com.rb.lottery.client.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.system.SystemEnvironment;

/**
 * @类功能说明: 分页查询，处理分页相关
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-17 下午01:06:36
 * @版本: 1.0.0
 */

public class Page implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 */
	private static final long serialVersionUID = -8318031536786359565L;

	private Map data;

	private int currentPage = 1; // 当前页数
	private int pageSize = SystemEnvironment.getInstance().default_page_size; // 每页记录数，默认10条
	private int pageCount = 0; // 总共页数
	private int recordCount = 0; // 记录总条数

	public Page() {
		super();
	}

	public Page(int currentPage) {
		super();
		setCurrentPage(currentPage);
	}

	public Page(Map data) {
		super();
		setData(data);
		initPage();
	}

	public void clear() {
		data.clear();
		currentPage = 0;
		pageCount = 0;
		recordCount = 0;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		if (currentPage < 1) {
			this.currentPage = 1;
		}
		if (currentPage > pageCount) {
			this.currentPage = pageCount;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		pageCount = (recordCount % pageSize) == 0 ? (recordCount / pageSize)
				: ((int) (recordCount / pageSize) + 1);
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	/**
	 * @return data
	 */
	public Map getData() {
		return data;
	}

	/**
	 * @param data
	 *            data
	 */
	public void setData(Map data) {
		this.data = data;
	}

	/**
	 * @方法说明: 通过data初始化分页数
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void initPage() {
		if (data == null) {
			currentPage = 0;
			return;
		}
		Set keySet = data.keySet();
		if (keySet == null) {
			currentPage = 0;
			return;
		}
		Iterator it = keySet.iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object value = data.get(key);
			if (value instanceof List) {
				List list = (List) value;
				recordCount += list.size();
			} else {
				recordCount++;
			}
		}
		int pgCount = getPageCount();
		if (currentPage > pgCount) {
			currentPage = pgCount;
		}
	}

	/**
	 * @方法说明: 通过list初始化分页数
	 * @参数: @param list
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void initPage(List list) {
		if (list != null && list.size() > 0) {
			this.recordCount = list.size();
			getPageCount();
		}
		if (currentPage > pageCount) {
			currentPage = pageCount;
		}
	}

	/**
	 * @方法说明: 得到分页数据
	 * @参数: @param list
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List doPaging(List list) {
		initPage(list);
		int start = (currentPage - 1) * pageSize;
		if (list == null || currentPage <= 0 || start >= list.size()) {
			return null;
		}
		int end = start + pageSize;
		if (end > list.size()) {
			end = list.size();
		}
		List newList = new ArrayList();
		for (int index = start; index < end; index++) {
			newList.add(list.get(index));
		}
		return newList;
	}

	/**
	 * @方法说明: 得到分页数据
	 * @参数: @param list
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Map doPaging() {
		Map result = new TreeMap();
		if (currentPage == 0) {
			return result;
		}
		if (data == null) {
			return result;
		}
		Set keySet = data.keySet();
		if (keySet == null) {
			return result;
		}
		Iterator it = keySet.iterator();
		int pageStartIdx = (this.currentPage - 1) * this.pageSize;
		int pageEndIdx = pageStartIdx + this.pageSize;
		int listStartIdx = 0;
		int listEndIdx = 0;
		int leftCnt = this.pageSize;
		int addSize = 0;
		while (it.hasNext() && leftCnt > 0) {
			Object key = it.next();
			Object value = data.get(key);
			if (value instanceof List) {
				List list = (List) value;
				listStartIdx = listEndIdx;
				listEndIdx += list.size();
				if (listEndIdx > pageStartIdx) {
					List tmp = new ArrayList();
					int start = pageStartIdx - listStartIdx;
					if (start < 0) {
						start = 0;
					}
					if (listEndIdx < pageEndIdx) {
						addSize = listEndIdx - listStartIdx;
						for (int i = start; i < addSize; i++) {
							tmp.add(list.get(i));
						}
						result.put(key, tmp);
					} else {
						addSize = pageEndIdx - listStartIdx;
						for (int i = start; i < addSize; i++) {
							tmp.add(list.get(i));
						}
						result.put(key, tmp);
						break;
					}
				}
			} else {
				leftCnt--;
				result.put(key, value);
			}

		}
		return result;
	}

	/**
	 * @方法说明: 第一页
	 * @参数: @return
	 * @return Map
	 * @throws
	 */
	public Map firstPage() {
		if (pageCount == 0) {
			currentPage = 0;
		} else {
			currentPage = 1;
		}
		return doPaging();
	}

	/**
	 * @方法说明: 上一页
	 * @参数: @return
	 * @return Map
	 * @throws
	 */
	public Map previousPage() {
		currentPage--;
		if (pageCount == 0) {
			currentPage = 0;
		} else {
			if (currentPage < 1)
				currentPage = 1;
		}
		return doPaging();
	}

	/**
	 * @方法说明: 下一页
	 * @参数: @return
	 * @return Map
	 * @throws
	 */
	public Map nextPage() {
		currentPage++;
		if (pageCount == 0) {
			currentPage = 0;
		} else {
			if (currentPage > pageCount) {
				currentPage = pageCount;
			}
		}
		return doPaging();
	}

	/**
	 * @方法说明: 下一页
	 * @参数: @return
	 * @return Map
	 * @throws
	 */
	public Map lastPage() {
		if (pageCount == 0) {
			currentPage = 0;
		} else {
			currentPage = pageCount;
		}
		return doPaging();
	}
}
