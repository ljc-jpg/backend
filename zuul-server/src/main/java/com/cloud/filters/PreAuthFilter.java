package com.cloud.filters;


import com.auth0.jwt.interfaces.Claim;
import com.cloud.utils.CookieUtils;
import com.cloud.utils.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author zhu zheng
 * @Description: 请求过滤
 * @date 2020/4/13
 * @return
 */
public class PreAuthFilter extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(PreAuthFilter.class);

    public static final String[] excludes = new String[]{"/login", "/static/**", "/auth/logout"};

    public static final Map<String, String[]> map = new HashMap<>();

    @Value("${login-url}")
    private String loginUrl;

    @Autowired
    public StringRedisTemplate redisTemplate;

    @Value("${domain-name}")
    private String domainName;

    static {
        map.put("news-web", new String[]{"/news-web/homePage/getThomePageBySchId/**", "/news-web/information/syncInfomation"});
        map.put("online-shop", new String[]{"/online-shop/pay/payWeiXinByJsapi", "/online-shop/api/pay/payWeiXinByJsapi"});
    }

    /**
     * @Description: 表名该过滤器为前置过滤器，在请求被路由前执行
     * @author zhu zheng
     * @date 2020/4/13
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * @Description: 多个ZuulFilter执行顺序，返回值越小，执行顺序越优先
     * @author zhu zheng
     * @date 2020/4/13
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    /**
     * @Description: 当前filter是否生效
     * @author zhu zheng
     * @date 2020/4/13
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * @Description:过滤器具体业务 登录验证 白名单去除
     * @author zhu zheng
     * @date 2020/4/13
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        // 获取请求的参数
        String uri = request.getRequestURI();
        //去重白名单路径默认拦截
        boolean isInterceptor = false;
        //token验证默认拦截
        boolean isToken = false;

        //判断路径在白名单中
        String[] uris = getExcludesByUri(uri);
        if (uris == null) {
            uris = excludes;
        }
        for (String exclude : uris) {
            if (exclude.contains("/**") && uri.indexOf(StringUtils.substringBetween(exclude, "/", "/**")) >= 0) {
                isInterceptor = true;
                break;
            } else if (uri.contains(exclude)) {
                isInterceptor = true;
                break;
            }
        }
        //非白名单验证token
        if (!isInterceptor) {
            String token = CookieUtils.getCookie(request, CookieUtils.COOKIE_TOKEN);
            if (!StringUtils.isEmpty(token)) {
                String sign = getKey();
                if (!StringUtils.isEmpty(sign)) {
                    Map<String, Claim> map = JwtUtil.decode(token, sign);
                    logger.debug("map:" + map);
                    String jwtSign = map.get("id").asString();
                    if (sign.equals(jwtSign)) {
                        request.getSession().setAttribute("user", map.get("user"));
                        isToken = true;
                    }
                } else {
                    Cookie cookie = new Cookie("token", token);
                    cookie.setHttpOnly(true);
                    cookie.setMaxAge(0);
                    cookie.setDomain(domainName);
                    cookie.setPath("/");
                    ctx.getResponse().addCookie(cookie);
                }
            }
        }

        //白名单或者携带正确token不拦截
        if (isInterceptor || (isToken)) {
            //对该请求进行路由
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            // 让下一个Filter看到上一个Filter的状态
            ctx.set("isSuccess", false);
            return null;
        } else {
            logger.debug("请求被拦截 uri:" + uri);
            ctx.getResponse().setContentType("application/json;charset=UTF-8");
            try {
                ctx.getResponse().sendRedirect(loginUrl);
            } catch (IOException e) {
                e.printStackTrace();
                logger.debug("登录跳转失败：" + e);
            }
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);// 返回错误码
            ctx.setResponseBody("{\"code\":\"401\",\"result\":\"请先登录!\"}");
            ctx.set("isSuccess", false);
            return null;
        }
    }

    private String[] getExcludesByUri(String uri) {
        String[] url = null;
        if (!StringUtils.isEmpty(uri)) {
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                if (uri.contains(entry.getKey())) {
                    url = entry.getValue();
                    break;
                }
            }
        }
        return url;
    }

    private String getIp(HttpServletRequest request) {
        //对应nginx配置查询请求头是否有"X-Real-IP"信息
        String ipAddr = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ipAddr) && !"unknown".equalsIgnoreCase(ipAddr)) {
            return ipAddr;
        }
        //对应nginx配置查询请求头是否有"X-Forwarded-For"信息
        ipAddr = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ipAddr) && !"unknown".equalsIgnoreCase(ipAddr)) {
            // 如果经过了多次反向代理会有多个IP值，第一个为真实IP。
            int index = ipAddr.indexOf(',');
            if (index != -1) {
                return ipAddr.substring(0, index);
            } else {
                return ipAddr;
            }
        }
        //如果没有经过代理或nginx为配置，则直接通过request获取
        return request.getRemoteAddr();
    }

    @Cacheable(value = "token",key = "'token' + #id",sync = true)
    public String getKey() {
        return null;
    }

}
