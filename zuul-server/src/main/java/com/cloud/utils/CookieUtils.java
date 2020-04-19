package com.cloud.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 资源工具，读取资源文件，封装连接等.
 * @since jdk1.5
 * @version 2012-3-5
 */
public class CookieUtils {
	private static Logger logger = LoggerFactory.getLogger(CookieUtils.class);

	public static final String COOKIE_NAME_USER="ticket";

	public static final String COOKIE_TOKEN="token";

	public static boolean isMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
				"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
				"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
				"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
				"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
				"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
				"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
				"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
				"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
				"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}

	public static final boolean hasCookies(HttpServletRequest servletRequest, String nameCookie) {
		boolean result = false;
		Cookie[] cookies = servletRequest.getCookies();
		if(null!=cookies) {
			for(Cookie cookie:cookies) {
				if(nameCookie.equals(cookie.getName())) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	public static final String getCookie(HttpServletRequest servletRequest, String nameCookie) {
		String result = null;
		Cookie[] cookies = servletRequest.getCookies();
		if(null!=cookies) {
			for(Cookie cookie:cookies) {
				if(nameCookie.equals(cookie.getName())) {
					result = cookie.getValue();
					break;
				}
			}
		}
		return result;
	}
	
	public static final void addCookie(HttpServletRequest request, HttpServletResponse response,
                                       String nameCookie, String value, String domainName) {
		Cookie cookie = new Cookie(nameCookie, value);
		if(StringUtils.isNotEmpty(domainName)) {
			cookie.setDomain(domainName);
		}else {
			cookie.setDomain("47.99.201.4");
		}
		cookie.setPath("/");
		int hous = 2;
		if(CookieUtils.isMoblie(request)) {
			hous = 24*30;//30天有效
		}
		logger.info("addCookie nameCookie：{} hous:{}",nameCookie,hous);
		cookie.setMaxAge(60*60*hous);
		response.addCookie(cookie);
	}

}
