package com.cloud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 资源工具，读取资源文件，封装连接等.
 *
 * @author zhuz
 * @date 2020/7/29
 */
public class CookieUtils {
    private static Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    private CookieUtils() {
    }

    public static String COOKIE_TOKEN = "token";

    private static int COOKIE_TIME;

    @Value("cookie-time")
    public void setExpireTime(int cookieTime) {
        CookieUtils.COOKIE_TIME = cookieTime * 60 * 60 * 24;
    }

    public static boolean isMobile(HttpServletRequest request) {
        boolean isMobile = false;
        String[] mobileAgents = {"iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
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
                "Googlebot-Mobile"};
        if (request.getHeader(ActiveEnum.USER_AGENT_EVENT.getValue()) != null) {
            for (String mobileAgent : mobileAgents) {
                if (request.getHeader(ActiveEnum.USER_AGENT_EVENT.getValue()).toLowerCase().indexOf(mobileAgent) >= 0) {
                    //客户端类型
                    logger.info("User-Agent:" + request.getHeader(ActiveEnum.USER_AGENT_EVENT.getValue()).toLowerCase());
                    isMobile = true;
                    break;
                }
            }
        }
        return isMobile;
    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response,
                                 String nameCookie, String value, String domainName) {
        Cookie cookie = new Cookie(nameCookie, value);
        //手机端设置30天有效 电脑端浏览器关闭前有效
        if (CookieUtils.isMobile(request)) {
            cookie.setMaxAge(COOKIE_TIME);
        } else {
            cookie.setMaxAge(-2);
        }
        cookie.setHttpOnly(true);
        cookie.setDomain(domainName);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static final String getCookie(HttpServletRequest servletRequest, String nameCookie) {
        String token = null;
        Cookie[] cookies = servletRequest.getCookies();
        logger.info("cookies" + cookies);
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (nameCookie.equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }
}
