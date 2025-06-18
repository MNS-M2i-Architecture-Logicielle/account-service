package org.example.mnb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnitTests {

    @Test
    void getClient_returnIsRight(int idClient, Client client){
        Assertions.assertEquals(getClient(idClient).name, client);
    }

    @Test
    void getCount_returnIsRight(int idClient, int idCount, int number) {
        Assertions.assertEquals(getClient(idClient).getCount(idCount), number);
    }

}
