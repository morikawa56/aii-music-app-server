package xyz.mrkwcode.aiimusicserver.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import xyz.mrkwcode.aiimusicserver.annos.ResponseResult;
import xyz.mrkwcode.aiimusicserver.pojos.Result;

@Slf4j
@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        final String returnTypeName = returnType.getParameterType().getName();
        boolean flag = !"xyz.mrkwcode.aiimusicserver.pojos.Result".equals(returnTypeName)
                && !"org.springframework.http.ResponseEntity".equals(returnTypeName);
        log.info("supports:" + flag + " " + returnTypeName);
        return flag;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        log.info("before");
        final String returnTypeName = returnType.getParameterType().getName();
        response.setStatusCode(HttpStatusCode.valueOf(200));
        if("void".equals(returnTypeName)) {
            return Result.success(null, 200);
        }
        if(!selectedContentType.includes(MediaType.APPLICATION_JSON)) {
            return Result.success(body, 200);
        }
        if("xyz.mrkwcode.aiimusicserver.pojos.Result".equals(returnTypeName)) {
            return body;
        }
        return Result.success(body, 200);
    }
}
