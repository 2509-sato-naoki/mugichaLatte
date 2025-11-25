package com.example.mugichaLatte.filter;

import com.example.mugichaLatte.security.LoginUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApproverFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // 承認者ページ以外ならスルー
        if (!path.startsWith("/approve")) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails user = (LoginUserDetails) auth.getPrincipal();

        // 1:承認者,3:兼任
        if (user.getType() != 1 && user.getType() != 3) {
            List<String> errors = new ArrayList<>();
            errors.add("不正なアクセスです");
            request.setAttribute("errorMessages", errors);
            request.getRequestDispatcher("/home").forward(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}


