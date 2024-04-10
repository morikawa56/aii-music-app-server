package xyz.mrkwcode.aiimusicserver.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.mrkwcode.aiimusicserver.exceptions.UniverCustomException;
import xyz.mrkwcode.aiimusicserver.utils.JwtUtil;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // return HandlerInterceptor.super.preHandle(request, response, handler);
        // 令牌验证
        String token = request.getHeader("Authorization");
        try {
            // 从Redis获取相同的token
            Map<String, Object> clamis = JwtUtil.parseToken(token);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get("AiiMusic:" + "usertoken-" + clamis.get("uid"));
            if(redisToken == null) {
                throw new RuntimeException();
            }
            // 把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(clamis);
            return true;
        } catch (Exception e) {
            throw new UniverCustomException(401, "未登录");
        }    }
}
