package org.players;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "org.players")
@EnableAsync
public class PlayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayerApplication.class, args);
    }

}
