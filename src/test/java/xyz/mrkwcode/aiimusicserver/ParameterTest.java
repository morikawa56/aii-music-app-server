package xyz.mrkwcode.aiimusicserver;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import xyz.mrkwcode.aiimusicserver.pojos.Result;
import xyz.mrkwcode.aiimusicserver.pojos.User;

@SpringBootTest
@Slf4j
public class ParameterTest {

    @Test
    public void methodP(MethodParameter returnType) {
        final String returnTypeName = returnType.getParameterName();
        boolean flag = !"xyz.mrkwcode.aiimusicserver.pojos.Result".equals(returnTypeName)
                && !"org.springframework.http.ResponseEntity".equals(returnTypeName);
        log.info("supports:" + flag + " " + returnTypeName);
    }
}

