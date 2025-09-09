package org.lokhit.gamelove.test.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.lokhit.gamelove.GameLoveServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(classes = GameLoveServiceApplication.class)
@ActiveProfiles("test")
public class CucumberSpringConfiguration {

}


