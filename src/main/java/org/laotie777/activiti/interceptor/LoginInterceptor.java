package org.laotie777.activiti.interceptor;

import org.laotie777.activiti.utils.SpringWebUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;

/**
 * @author yuh
 * 2018/1/15.
 */
public class LoginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String requestURI = httpServletRequest.getRequestURI();
        if("/".equals(requestURI) || "/login".equals(requestURI)){
            return true;
        }else{
            HttpSession session = httpServletRequest.getSession();
            Object attribute = session.getAttribute(SpringWebUtil.GLOBLE_USER_SESSION);
            if(attribute == null){
                //httpServletRequest.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(httpServletRequest, httpServletResponse);
                httpServletResponse.sendRedirect("/");
                return false;
            }
        }

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
