package xyz.mrkwcode.aiimusicserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.mrkwcode.aiimusicserver.controllers.UserController;

@SpringBootTest
class AiiMusicServerApplicationTests {
    @Autowired
    private UserController userService;
    @Test
    void contextLoads() {

    }

}
