package org.example.mnb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "org.example.mnb.adapters.out")
@SpringBootApplication
public class MetzNumericBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetzNumericBankApplication.class, args);
    }

}
