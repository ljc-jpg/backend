package com.cloud.filters;

import com.cloud.util.ActiveEnum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhu zheng
 * @Description: 通过微服务名字匹配对应微服务接口
 * @date 2020/4/13
 * @return
 */
public class RibbonFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(RibbonFilter.class);

    /**
     * @Description: 表名该过滤器为前置过滤器，在请求被路由前执行
     * @author zhu zheng
     * @date 2020/4/13
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    /**
     * @Description: 当前filter是否生效
     * @author zhu zheng
     * @date 2020/4/13
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        logger.debug("serverName: " + ctx.getRequest().getServerName());
        HttpServletRequest request = ctx.getRequest();
        return null != request.getServerName() && request.getServerName().contains(ActiveEnum.PRE_EVENT.getValue());
    }

    /**
     * 过滤器具体业务 转发到对应的微服务
     *
     * @author zhu zheng
     * @date 2020/4/13
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (null != request.getServerName() && request.getServerName().contains(ActiveEnum.PRE_EVENT.getValue())) {
            RibbonFilterContextHolder.getCurrentContext().add(ActiveEnum.PRE_EVENT.getValue(), ActiveEnum.ONE_EVENT.getValue());
        }
        return null;
    }

}
