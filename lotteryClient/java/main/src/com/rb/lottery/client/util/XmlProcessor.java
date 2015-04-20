/**
 * @文件名称: XmlProcessor.java
 * @类路径:   com.rb.lottery.util
 * @描述:     TODO
 * @作者:     robin
 * @时间:     2011-11-3 下午07:31:43
 * @版本:     1.0.0
 */
package com.rb.lottery.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.rb.lottery.client.common.EncodingConstants;
import com.rb.lottery.client.common.HtmlTagConstants;
import com.rb.lottery.client.common.SystemConstants;

/**
 * @类功能说明: XML配置文件读取修改处理
 * @类修改者:robin
 * @修改日期:2011-12-24
 * @修改说明:处理节点列表读取
 * @作者: robin
 * @创建时间: 2011-11-3 下午07:31:43
 * @版本: 1.0.0
 */

public final class XmlProcessor {

	private static final Logger log = Logger.getLogger(XmlProcessor.class);

	/**
	 * @方法说明: 获取xml文件key节点的值（单个值）
	 * @参数: @param srcFile
	 * @参数: @param key
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static String getXmlValueByKey(File srcFile, String key) {
		// 输入流对象
		InputStreamReader fis = null;
		try {
			fis = new InputStreamReader(new FileInputStream(srcFile),
					EncodingConstants.UTF8_ENCODING);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// jdom解析器
		SAXBuilder sab = new SAXBuilder();
		Document doc = null;
		try {
			doc = sab.build(fis);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 获得XML的根元素
		Element root = doc.getRootElement();
		// 获得根元素下的所有子元素
		// String value = root.getChildTextTrim(key);

		// 队列操作
		List<Element> sysConf = new LinkedList(root.getChildren());
		String value = SystemConstants.EMPTY_STRING;
		if (sysConf != null) {
			while (sysConf.size() > 0) {
				Element elem = sysConf.remove(0);
				if (key.equalsIgnoreCase(elem.getName())) {
					value = elem.getValue();
					break;
				}
				List<Element> chrildren = elem.getChildren();
				if (chrildren != null && chrildren.size() > 0) {
					sysConf.addAll(chrildren);
				}
			}
		}
		return value;
	}

	/**
	 * @方法说明: 获取xml文件key节点的值（多个值/由换行符隔开）
	 * @参数: @param srcFile
	 * @参数: @param key
	 * @参数: @return
	 * @return List<String>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getXmlValuesByKey(File srcFile, String key) {
		// 输入流对象
		InputStreamReader fis = null;
		try {
			fis = new InputStreamReader(new FileInputStream(srcFile),
					EncodingConstants.UTF8_ENCODING);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// jdom解析器
		SAXBuilder sab = new SAXBuilder();
		Document doc = null;
		try {
			doc = sab.build(fis);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 获得XML的根元素
		Element root = doc.getRootElement();
		// 获得根元素下的所有子元素
		// String value = root.getChildTextTrim(key);

		// 队列操作
		List<Element> sysConf = new LinkedList(root.getChildren());
		List<String> values = new ArrayList<String>();
		if (sysConf != null) {
			while (sysConf.size() > 0) {
				Element elem = sysConf.remove(0);
				if (key.equalsIgnoreCase(elem.getName())) {
					String val = elem.getValue();
					if (val != null) {
						val = val.trim();
						String[] vals = val.split(SystemConstants.NEW_LINE);
						for (String value : vals) {
							values.add(value.trim());
						}
					}
					break;
				}
				List<Element> chrildren = elem.getChildren();
				if (chrildren != null && chrildren.size() > 0) {
					sysConf.addAll(chrildren);
				}
			}
		}
		return values;
	}

	/**
	 * @方法说明:
	 * @参数: @param srcFile
	 * @参数: @param key
	 * @参数: @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static String getXmlAttributeValueByKey(File srcFile, String key,
			String attribue) {
		// 输入流对象
		InputStreamReader fis = null;
		try {
			fis = new InputStreamReader(new FileInputStream(srcFile),
					EncodingConstants.UTF8_ENCODING);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// jdom解析器
		SAXBuilder sab = new SAXBuilder();
		Document doc = null;
		try {
			doc = sab.build(fis);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 获得XML的根元素
		Element root = doc.getRootElement();
		// 获得根元素下的所有子元素
		// String value = root.getChildTextTrim(key);

		// 队列操作
		List<Element> sysConf = new LinkedList(root.getChildren());
		String value = SystemConstants.EMPTY_STRING;
		if (sysConf != null) {
			while (sysConf.size() > 0) {
				Element elem = sysConf.remove(0);
				if (key.equalsIgnoreCase(elem.getName())) {
					org.jdom.Attribute attr = elem.getAttribute(attribue);
					if (attr != null) {
						value = attr.getValue();
					}
					break;
				}
				List<Element> chrildren = elem.getChildren();
				if (chrildren != null && chrildren.size() > 0) {
					sysConf.addAll(chrildren);
				}
			}
		}
		return value;
	}

	public static boolean docToXmlFile(org.w3c.dom.Document document,
			File srcFile) {
		boolean flag = true;
		try {
			/** 将document中的内容写入文件中 */
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			/** 编码 */
			transformer.setOutputProperty(OutputKeys.ENCODING,
					EncodingConstants.UTF8_ENCODING);
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(srcFile);
			transformer.transform(source, result);
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	public static org.w3c.dom.Document loadDocument(File srcFile) {
		org.w3c.dom.Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(srcFile);
			log.debug(document.toString());
			document.normalize();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	/**
	 * @方法说明: 更新XML文件
	 * @参数: @param srcFile
	 * @参数: @param key
	 * @参数: @param value
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static void updateXmlValue(File srcFile, String key, String value) {
		try {
			InputStreamReader fis = new InputStreamReader(new FileInputStream(
					srcFile), EncodingConstants.UTF8_ENCODING);
			SAXReader saxReader = new SAXReader();
			org.dom4j.Document document = saxReader.read(fis);
			String selectNode = HtmlTagConstants.XML_NODE_SELECTOR + key;
			List list = document.selectNodes(selectNode);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				org.dom4j.Element element = (org.dom4j.Element) iter.next();
				element.setText(value);
			}
			XMLWriter output = new XMLWriter(new FileWriter(srcFile));
			output.write(document);
			output.close();
		}

		catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法说明: 更新XML文件
	 * @参数: @param srcFile
	 * @参数: @param nodeName
	 * @参数: @param nodeAuttribue
	 * @参数: @param nodeAuttribueValue
	 * @参数: @param nodeText
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static void updateXmlValue(File srcFile, String nodeName,
			String nodeAuttribue, String nodeAuttribueValue, String nodeText) {
		try {
			InputStreamReader fis = new InputStreamReader(new FileInputStream(
					srcFile), EncodingConstants.UTF8_ENCODING);
			SAXReader saxReader = new SAXReader();
			org.dom4j.Document document = saxReader.read(fis);
			String selectNode = HtmlTagConstants.XML_NODE_SELECTOR + nodeName
					+ HtmlTagConstants.XML_ATTRIBUTE_SELECTOR + nodeAuttribue;
			List list = document.selectNodes(selectNode);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attr = (Attribute) iter.next();
				attr.setValue(nodeAuttribueValue);
			}
			String selectText = HtmlTagConstants.XML_NODE_SELECTOR + nodeName;
			List tlist = document.selectNodes(selectText);
			Iterator titer = tlist.iterator();
			while (titer.hasNext()) {
				org.dom4j.Element element = (org.dom4j.Element) titer.next();
				element.setText(nodeText);
			}
			XMLWriter output = new XMLWriter(new FileWriter(srcFile));
			output.write(document);
			output.close();
		}

		catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
