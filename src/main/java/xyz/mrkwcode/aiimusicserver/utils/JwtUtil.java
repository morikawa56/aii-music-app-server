package xyz.mrkwcode.aiimusicserver.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "mrkwcode";
	
	//接收业务数据,生成token并返回
    public static String genToken(Map<String, Object> claims) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.add(Calendar.DATE, 30);
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(calendar.getTimeInMillis()))
                .sign(Algorithm.HMAC256(KEY));
    }

	//接收token,验证token,并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

}
