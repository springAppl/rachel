package org.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class FingerPrintFilter implements Filter {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        if (Objects.equals(httpServletRequest.getRequestURI(), "/api/finger-print")) {
            String name = httpServletRequest.getParameter("name");
            String fingerPrint = httpServletRequest.getParameter("fingerPrint");
            FingerPrintToken fingerPrintToken = new FingerPrintToken();
            fingerPrintToken.setName(name);
            fingerPrintToken.setFingerPrint(fingerPrint);
            Authentication auth = authenticationManager.authenticate(fingerPrintToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            httpServletResponse.sendRedirect("/api/test");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
