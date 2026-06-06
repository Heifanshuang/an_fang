package com.example.iotplatform.config;

import com.example.iotplatform.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT认证过滤器
 * 用于验证请求中的JWT令牌
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 从请求头中获取Authorization字段
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;

            // 检查Authorization头是否存在且格式正确
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtUtils.getUsernameFromToken(token);
            }

            // 如果令牌有效且用户未认证
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 加载用户信息
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // 验证令牌
                if (jwtUtils.validateToken(token)) {
                    // 创建认证对象
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 设置认证信息到上下文
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            logger.error("JWT认证失败: {}", e.getMessage());
        }

        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }
}
