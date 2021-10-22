package com.verteil.besteleven.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.verteil.besteleven.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;

@Component
public class CustomInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle
            (HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        User user = (User) request.getSession().getAttribute("user");
        if (!"/login".equals(urlPathHelper.getLookupPathForRequest(request)) && null == user) {
            response.sendRedirect("/login");
            return false;
        } else {
            return true;
        }
    }
}