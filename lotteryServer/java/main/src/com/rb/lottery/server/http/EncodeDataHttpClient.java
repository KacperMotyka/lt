/**
 * @文件名称: KjDataHttpClient.java
 * @类路径:   com.rb.lottery.http
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-12-9 上午10:04:27
 * @版本:     1.0.0
 */
package com.rb.lottery.server.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import com.rb.lottery.server.common.EncodingConstants;
import com.rb.lottery.server.common.SystemConstants;
import com.rb.lottery.system.SystemEnvironment;

/**
 * @类功能说明:
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-12-9 上午10:04:27
 * @版本: 1.0.0
 */

public class EncodeDataHttpClient {

	/**
	 * 处理GET请求，返回整个页面
	 * 
	 * @param url
	 *            访问地址
	 * @param params
	 *            编码参数
	 * @return String
	 * @throws Exception
	 * @throws Exception
	 */
	public synchronized String doGet(String url, String... params)
			throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpProtocolParams.setUserAgent(httpclient.getParams(),
				SystemEnvironment.getInstance().useragent);
		String charset = EncodingConstants.UTF8_ENCODING;
		if (null != params && params.length > 0) {
			charset = params[0];
		}
		HttpGet httpget = new HttpGet();
		// solve cookie reject defect
		httpget.getParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		String content = SystemConstants.EMPTY_STRING;
		httpget.setURI(new java.net.URI(url));
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 使用EntityUtils的toString方法，传递默认编码，在EntityUtils中的默认编码是ISO-8859-1
			content = EntityUtils.toString(entity, charset);
			httpget.abort();
			httpclient.getConnectionManager().shutdown();
		}
		return content;
	}
}
