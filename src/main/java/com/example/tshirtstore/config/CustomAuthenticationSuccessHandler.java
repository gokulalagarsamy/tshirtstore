//package com.example.tshirtstore.config;
//
//import java.io.IOException;
//import java.util.Collection;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication)
//            throws IOException, ServletException {
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        String redirectUrl = "/";
//
//        for (GrantedAuthority authority : authorities) {
//            if (authority.getAuthority().equals("ROLE_ADMIN")) {
//                redirectUrl = "/admin";
//                break;
//            } else if (authority.getAuthority().equals("ROLE_USER")) {
//                redirectUrl = "/user";
//                break;
//            }
//        }
//
//        response.sendRedirect(redirectUrl);
//    }
//}
