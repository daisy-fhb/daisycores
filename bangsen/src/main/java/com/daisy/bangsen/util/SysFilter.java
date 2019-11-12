package com.daisy.bangsen.util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class SysFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse)response;
        res.setCharacterEncoding("UTF-8");
//        String requestURI = req.getRequestURI();
//        res.setHeader("Content-type", "text/html;charset=UTF-8");
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
