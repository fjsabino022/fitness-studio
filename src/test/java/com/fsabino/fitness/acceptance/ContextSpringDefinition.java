package com.fsabino.fitness.acceptance;

import com.fsabino.fitness.IntegrationApplication;
import io.cucumber.java.en.Given;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = IntegrationApplication.class)
@ActiveProfiles("integration")
public class ContextSpringDefinition {

    @Given("^I am a User$")
    public void i_am_a_user() throws Throwable {
    }
}
