package org.laotie777.activiti.utils;

import org.laotie777.activiti.entity.Employee;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 2018/1/15.
 */
public class SpringWebUtil {


    public static final String GLOBLE_USER_SESSION = "globle_user";

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession(false);
    }

    public static String getRealRootPath() {
        return getRequest().getServletContext().getRealPath("/");
    }

    public static String getContextPath() {
        return getRequest().getContextPath();
    }

    public static void setUser(Employee user){
        if(user!=null){
            getSession().setAttribute(GLOBLE_USER_SESSION, user);
        }else{
            getSession().removeAttribute(GLOBLE_USER_SESSION);
        }
    }

    public static Employee get(){
        return (Employee) getSession().getAttribute(GLOBLE_USER_SESSION);
    }

}


