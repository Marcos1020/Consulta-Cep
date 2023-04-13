package com.sanches.consultacep;

import com.sanches.consultacep.controller.CepController;
import com.sanches.consultacep.controller.request.CepRequest;
import com.sanches.consultacep.controller.response.CepResponse;
import com.sanches.consultacep.exception.BadRequestException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConsultaCepApplicationTests {

    @Test
    void contextLoads() {
    }


}
