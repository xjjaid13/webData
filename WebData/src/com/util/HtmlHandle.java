package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * html字符串处理类
 * 
 * @author cloud
 * */
public final class HtmlHandle {

	/** 转义尖括号等为字符 */
	public static String filter(String htmlContent) {
		if (htmlContent == null) {
			return "";
		}
		char content[] = new char[htmlContent.length()];
		htmlContent.getChars(0, htmlContent.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		for (int i = 0; i < content.length; i++) {
			switch (content[i]) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '"':
				result.append("&quot;");
				break;
			default:
				result.append(content[i]);
			}
		}
		return (result.toString());
	}

	/** 将尖括号等字符转会尖括号等 */
	public static String filterTextToHTML(String htmlContent) {
		if (htmlContent == null) {
			return "";
		}
		htmlContent = htmlContent.replaceAll("&lt;", "<");
		htmlContent = htmlContent.replaceAll("&gt;", ">");
		htmlContent = htmlContent.replaceAll("&amp;", "&");
		htmlContent = htmlContent.replaceAll("&quot;", "\"");
		return (htmlContent);
	}

	/** 除去HTML代码 或 \f\n\r\t特殊字符 */
	public static String removeHTMLTag(String value) {
		String regEx = "<[^>]+>|</[^>]|[\f\n\r\t]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(value);
		return m.replaceAll("");
	}

	public static String transBlank(String content) {
		return content.replaceAll(" ", "&nbsp;");
	}

	public static String inDatabase(String str) {
		while (str.indexOf("\n") != -1) {
			str = str.substring(0, str.indexOf("\n")) + "<br>"
					+ str.substring(str.indexOf("\n") + 1);
		}
		while (str.indexOf(" ") != -1) {
			str = str.substring(0, str.indexOf(" ")) + "&nbsp;"
					+ str.substring(str.indexOf(" ") + 1);
		}
		return str;
	}

	public static String inTextarea(String str) {
		while (str.indexOf("<br>") != -1) {
			str = str.substring(0, str.indexOf("<br>")) + "\n"
					+ str.substring(str.indexOf("<br>") + 4);
		}
		while (str.indexOf("&nbsp;") != -1) {
			str = str.substring(0, str.indexOf("&nbsp;")) + " "
					+ str.substring(str.indexOf("&nbsp;") + 6);
		}
		return str;
	}

	public static String inJsp(String str) {
		while (str.indexOf("\r") != -1) {
			str = str.substring(0, str.indexOf("\r")) + ""
					+ str.substring(str.indexOf("\r") + 1);
		}
		return str;
	}

	public static String Html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}
	
	public static String getWebCon(String domain) {
		StringBuffer sb = new StringBuffer();
		try {
			java.net.URL url = new java.net.URL(domain);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			in.close();
		} catch (Exception e) {
			Log.Error("无法解析" + e.getMessage());
		}
		return sb.toString();
	}
	
	public static String getDomain(String curl) {
		URL url = null;
		String q = "";
		try {
			url = new URL(curl);
			q = url.getHost();
			if(!q.startsWith("http")){
				q = "http://" + q;
			}
			if(url.getPort() != -1){
				q = q + ":" + url.getPort();
			}
		} catch (MalformedURLException e) {
			Log.Error(e.getMessage());
		}
		url = null;
		return q;
	}
	
	public static String joinUrl(String curl, String file) {
		URL url = null;
		String q = "";
		try {
			url = new URL(new URL(curl), file);
			q = url.toExternalForm();
		} catch (MalformedURLException e) {
			Log.Error(e.getMessage());
		}
		url = null;
		if (q.indexOf("#") != -1)
			q = q.replaceAll("^(.+?)#.*?$", "$1");
		return q;
	}
	
	public static void download(String urlString, String filename,
			String savePath) {
		try{
			// 构造URL
			URL url = new URL(urlString);
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(15 * 1000);
			// 输入流
			InputStream is = con.getInputStream();
	
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			
			filename = filename.replaceAll("\\?", "-");
			filename = filename.replaceAll("\\:", "-");
			
			File sf = new File(savePath);
			if (!sf.exists()) {
				sf.mkdirs();
			}
			OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
		}catch(Exception e){
			Log.Error("HtmlHandle.download异常 " + e.getMessage() + ";urlString="+urlString+
					",filename="+filename+" ,savePath=" + savePath);
		}
	}
	
	public static void main(String[] args) throws Exception {
		File sf = new File("D:/htmlContent/www.csdb.net/122");
		if (!sf.exists()) {
			sf.mkdirs();
		}
	}
	

}
