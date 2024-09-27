package com.practice.token.configuration;

import com.practice.token.exception.ErrorCode;
import com.practice.token.model.entity.User;
import com.practice.token.service.UserService;
import com.practice.token.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token;
        final String requestURI = request.getRequestURI();

        // 허용된 경로에서 JWT 검증 건너뛰기
        if (requestURI.equals("/join") || requestURI.equals("/login") || requestURI.equals("/token/refresh")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            if (header == null || !header.startsWith("Bearer ")) {
                log.error("Authorization Header does not start with Bearer {}", request.getRequestURI());
                chain.doFilter(request, response);
                return;
            } else {
                token = header.split(" ")[1].trim();
            }

            String userName = jwtTokenUtil.getUsernameByToken(token);
            User user = userService.findUserOrElseThrow(userName);

            if (jwtTokenUtil.isTokenExpired(token) || !userName.equals(user.getUsername())) {
                chain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            request.setAttribute(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getDesc());
        }

        chain.doFilter(request, response);
    }
}
