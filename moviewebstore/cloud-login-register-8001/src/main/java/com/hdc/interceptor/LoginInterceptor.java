//package com.hdc.interceptor;
//
//import com.hdc.User;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@Component
//public class LoginInterceptor implements HandlerInterceptor {
//
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession(true);
//        User user = (User) session.getAttribute("user");
//        if (user == null){
//            response.sendRedirect(request.getContextPath()+"/user/login");
//            return false;
//        }else {
//            session.setAttribute("user",user);
//            return true;
//        }
//    }
//}
