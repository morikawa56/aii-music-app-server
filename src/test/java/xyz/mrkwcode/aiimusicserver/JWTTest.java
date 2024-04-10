package xyz.mrkwcode.aiimusicserver;

import org.junit.jupiter.api.Test;
import xyz.mrkwcode.aiimusicserver.utils.JwtUtil;

public class JWTTest {
    @Test
    public void test1() {
        String test = JwtUtil.parseToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsidWlkIjoyLCJ1c2VybmFtZSI6Im1vcmlrYXdhNTYifSwiZXhwIjoxNzE1MzI4NTQ2fQ.fam8CVzU698i04Z-aoqZM5CDZbaJUdV7AK8pJbkphCc").toString();
        System.out.println(test);
    }
}
