package ru.job4j.architecture;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

public class AuthFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(DispatchDiapason.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }

}
