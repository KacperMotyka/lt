/**
 * @文件名称: HttpClient.java
 * @类路径:   com.rb.lottery.http
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-1 下午02:02:41
 * @版本:     1.0.0
 */
package com.rb.lottery.server.http;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.util.*;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.rb.lottery.server.common.SystemConstants;

/**
 * @类功能说明: 读取网页内容用于数据更新
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-11-1 下午02:02:41
 * @版本: 1.0.0
 */

public class DataHttpClient {

	// 获得ConnectionManager，设置相关参数
	private static MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();

	private static int connectionTimeOut = 20000;
	private static int socketTimeOut = 10000;
	private static int maxConnectionPerHost = 5;
	private static int maxTotalConnections = 40;

	// 标志初始化是否完成的flag
	private static boolean initialed = false;

	public DataHttpClient() {
		DataHttpClient.SetPara();
	}

	// 初始化ConnectionManger的方法
	public static void SetPara() {
		manager.getParams().setConnectionTimeout(connectionTimeOut);
		manager.getParams().setSoTimeout(socketTimeOut);
		manager.getParams().setDefaultMaxConnectionsPerHost(
				maxConnectionPerHost);
		manager.getParams().setMaxTotalConnections(maxTotalConnections);
		initialed = true;
	}

	// 通过get方法获取网页内容
	@SuppressWarnings("finally")
	public static String getGetResponse(String url, String encode) {
		HttpClient client = new HttpClient(manager);

		if (initialed) {
			DataHttpClient.SetPara();
		}

		GetMethod get = new GetMethod(url);
		get.setFollowRedirects(true);
		// solve cookie reject defect
		get.getParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);

		String result = null;
		StringBuffer resultBuffer = new StringBuffer();

		try {

			client.executeMethod(get);
			// 在目标页面情况未知的条件下，不推荐使用getResponseBodyAsString()方法
			// String strGetResponseBody = post.getResponseBodyAsString();
			BufferedReader in = new BufferedReader(new InputStreamReader(get
					.getResponseBodyAsStream(), get.getResponseCharSet()));

			String inputLine = null;

			while ((inputLine = in.readLine()) != null) {
				resultBuffer.append(inputLine);
				resultBuffer.append(SystemConstants.NEW_LINE);
			}

			in.close();
			result = resultBuffer.toString();

			// iso-8859-1 is the default reading encode
			// result = DataHttpClient.ConverterStringCode(
			// resultBuffer.toString(), get.getResponseCharSet(), encode);
		} catch (Exception e) {
			e.printStackTrace();
			result = SystemConstants.EMPTY_STRING;
		} finally {
			get.releaseConnection();
			return result;
		}
	}

	@SuppressWarnings("finally")
	public static String getPostResponse(String url, String encode) {
		HttpClient client = new HttpClient(manager);
		if (initialed) {
			DataHttpClient.SetPara();
		}

		PostMethod post = new PostMethod(url);
		post.setFollowRedirects(false);

		StringBuffer resultBuffer = new StringBuffer();

		String result = null;

		try {
			client.executeMethod(post);

			BufferedReader in = new BufferedReader(new InputStreamReader(post
					.getResponseBodyAsStream(), post.getResponseCharSet()));
			String inputLine = null;

			while ((inputLine = in.readLine()) != null) {
				resultBuffer.append(inputLine);
				resultBuffer.append(SystemConstants.NEW_LINE);
			}

			in.close();

			// iso-8859-1 is the default reading encode
			result = DataHttpClient.ConverterStringCode(
					resultBuffer.toString(), post.getResponseCharSet(), encode);
		} catch (Exception e) {
			e.printStackTrace();

			result = SystemConstants.EMPTY_STRING;
		} finally {
			post.releaseConnection();
			return result;
		}
	}

	@SuppressWarnings("finally")
	public static String getPostResponse(String url, String encode,
			NameValuePair[] nameValuePair) {
		HttpClient client = new HttpClient(manager);

		if (initialed) {
			DataHttpClient.SetPara();
		}

		PostMethod post = new PostMethod(url);
		post.setRequestBody(nameValuePair);
		post.setFollowRedirects(false);
		String result = null;
		StringBuffer resultBuffer = new StringBuffer();

		try {
			client.executeMethod(post);
			BufferedReader in = new BufferedReader(new InputStreamReader(post
					.getResponseBodyAsStream(), post.getResponseCharSet()));
			String inputLine = null;

			while ((inputLine = in.readLine()) != null) {
				resultBuffer.append(inputLine);
				resultBuffer.append(SystemConstants.NEW_LINE);
			}

			in.close();

			// iso-8859-1 is the default reading encode
			result = DataHttpClient.ConverterStringCode(
					resultBuffer.toString(), post.getResponseCharSet(), encode);
		} catch (Exception e) {
			e.printStackTrace();

			result = SystemConstants.EMPTY_STRING;
		} finally {
			post.releaseConnection();
			return result;
		}
	}

	private static String ConverterStringCode(String src, String srcEncode,
			String destEncode) {
		if (src != null) {
			try {
				return new String(src.getBytes(srcEncode), destEncode);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return SystemConstants.EMPTY_STRING;
			}
		} else {
			return SystemConstants.EMPTY_STRING;
		}
	}

}
