package com.cloud.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 会话操作类
 *
 * @author gjchen
 */
public class SessionUtils {
    private static final Logger logger = LoggerFactory.getLogger(SessionUtils.class);

    public static User getUser(HttpServletRequest request) {
        logger.info("sessionId:{}", request.getSession().getId());
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            user = new User();
            user.setUserId("guest");
            user.setLoginName("guest");
        }
        return user;
    }

    public static void setUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        logger.info("sessionId setUser():{}", session.getId());
        session.setAttribute("user", user);
    }

    public static void logout(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            request.getSession().invalidate();
        } catch (Exception e) {
            logger.error("logout error", e);
        }
    }

}
