package org.lokhit.gamelove;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableRetry
@EnableCaching
@EntityScan(basePackages = "org.lokhit.gamelove.entity")
public class GameLoveServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(GameLoveServiceApplication.class, args);
    }
}
