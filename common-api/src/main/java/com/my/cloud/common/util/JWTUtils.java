package com.my.cloud.common.util;

import com.my.cloud.common.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtils {
    private static final long EXPIRE = 24 * 60 * 60 * 1000;

    private static final String SECRET = "JUST";//盐值

    private static final String TOKEN_PREFIX = "SpringCloudStudentManager";

    private static final String SUBJECT = "user";

    public static String geneJsonWebToken(User user) {
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("userId", user.getId())//载荷 - 有效信息 - 登录用户的有效信息,是为了校验用户是否登录的信息
                .claim("username", user.getUsername())
                .setIssuedAt(new Date())//颁布时间
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //生成签名 - token值
                //HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();//签名
        token = TOKEN_PREFIX + token;
        return token;
    }

    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

    public static Long getId(String token) {
        Claims claims = JWTUtils.checkJWT(token);
        if (claims != null) {
            return claims.get("userId", Long.class);
        }
        return null;
    }

}
