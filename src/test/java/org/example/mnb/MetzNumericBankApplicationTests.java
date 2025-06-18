package org.example.mnb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MetzNumericBankApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void getCount_returnIsRight(String number) {
        Assertions.assertEquals("3000000", number );
    }
}
