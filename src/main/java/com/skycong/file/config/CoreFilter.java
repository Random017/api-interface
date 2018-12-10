package com.skycong.file.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author RMC 2018/11/20 13:25
 */
@Component
public class CoreFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private static final String origin = "Access-Control-Allow-Origin";
    private static final String credentials = "Access-Control-Allow-Credentials";
    private static final String methods = "Access-Control-Allow-Methods";
    private static final String method = "POST,GET,OPTIONS,PUT,DELETE";
    private static final String headers = "Access-Control-Allow-Headers";
    private static final String header = "Origin, X-Requested-With, Content-Type, Accept,AccessToken,cache-control,if-modified-since,pragma";

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setHeader(origin, request.getHeader("origin"));
        response.setHeader(credentials, "true");
        response.addHeader(methods, method);
        response.setHeader(headers, header);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
