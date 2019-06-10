package com.bigassdoggg;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthFilter extends ZuulFilter {


    /**
     * pre: 请求被路由之前
     * routing：请求被路由之中
     * post：请求被路由之后
     * error：处理请求发送错误时
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        /*HttpServletRequest request = requestContext.getRequest();
        String token = request.getHeader("x-token");

        if(StringUtils.isNotBlank(token) && "1234".equals(token)){
            requestContext.addZuulRequestHeader("token",token);
            requestContext.addZuulRequestHeader("level","10");
        }else {
            System.out.println("---------- access error! ---------------");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            requestContext.setResponseBody("---------- access error! ---------------");
            return null; //阻止向下游执行
        }*/

        return requestContext;
    }
}
