package ru.job4j.architecture;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(DispatchDiapason.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (request.getRequestURI().contains("/signin")) {
            chain.doFilter(req, res);
        } else if (request.getSession().getAttribute("login") == null) {
            response.sendRedirect(String.format(request.getContextPath() + "/signin"));
            return;
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }

}
