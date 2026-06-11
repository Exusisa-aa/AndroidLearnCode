package com.restaurant.filter;

import com.alibaba.fastjson.JSON;
import com.restaurant.common.BaseContext;
import com.restaurant.common.Result;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

/**
 * Check if the user is logged in
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    // Path Matcher
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. Get URI
        String requestURI = request.getRequestURI();

        log.info("Intercepted request: {}", requestURI);

        // 2. Define paths that do not need to be processed
        String[] urls = new String[]{
                "/user/login",
                "/user/sendMsg", // send verify code
                "/user/logout",
                "/common/**",
                "/static/**", // static resources
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };

        // 3. Check if the path needs processing
        boolean check = check(urls, requestURI);

        // 4. If no need, let it go
        if (check) {
            log.info("Request {} does not need processing", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // 5. Check if logged in (Session)
        // Note: For Android client, we need to ensure Cookie is handled, OR use a Header token.
        // For this demo, we check Session "user" attribute which is standard Spring Security/Session behavior.
        // If Android client sends JSESSIONID cookie, this works.
        if (request.getSession().getAttribute("user") != null) {
            log.info("User logged in, ID: {}", request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request, response);
            return;
        }

        // 6. Not logged in
        log.info("User not logged in");
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
    }

    /**
     * Path matching check
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
