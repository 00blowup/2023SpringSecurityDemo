package hello.securitydemo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    public static String createToken (String userName, String key, long expireTimeMs) {
        Claims claims = Jwts.claims();  // 일종의 Map이다
        claims.put("userName", userName);   // 회원명을 저장

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 발행 시각
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs)) // 만료 시각
                .signWith(SignatureAlgorithm.HS256, key)    // HS256이라는 알고리즘과 주어진 key를 이용해 암호화
                .compact()
                ;

    }
}