package xyz.mrkwcode.aiimusicserver.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.mrkwcode.aiimusicserver.exceptions.UniverCustomException;
import xyz.mrkwcode.aiimusicserver.utils.JwtUtil;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // return HandlerInterceptor.super.preHandle(request, response, handler);
        // 令牌验证
        String token = request.getHeader("Authorization");
        try {
            Map<String, Object> clamis = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(clamis);
            return true;
        } catch (Exception e) {
            throw new UniverCustomException(401, "未登录");
        }    }
}
