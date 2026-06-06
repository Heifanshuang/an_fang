package com.example.iotplatform.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * 用于生成和验证JWT令牌
 */
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * 生成JWT令牌
     * @param account 用户名
     * @return JWT令牌
     */
    public String generateToken(String account) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, account);
    }

    /**
     * 创建JWT令牌
     * @param claims 自定义声明
     * @param subject 主题（用户名）
     * @return JWT令牌
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 获取JWT令牌的签名密钥
     * @return 签名密钥
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 验证JWT令牌
     * @param token JWT令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // 令牌无效
            return false;
        }
    }

    /**
     * 从JWT令牌中获取用户名
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
