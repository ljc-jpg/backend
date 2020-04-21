package com.cloud.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 资源工具，读取资源文件，封装连接等.
 *
 * @version 2012-3-5
 * @since jdk1.5
 */
public class CookieUtils {
    private static Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    public static final String COOKIE_TOKEN = "token";

    public static final String getCookie(HttpServletRequest servletRequest, String nameCookie) {
        String result = null;
        Cookie[] cookies = servletRequest.getCookies();
        logger.debug("cookies", cookies);
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (nameCookie.equals(cookie.getName())) {
                    result = cookie.getValue();
                    break;
                }
            }
        }
        return result;
    }


}
