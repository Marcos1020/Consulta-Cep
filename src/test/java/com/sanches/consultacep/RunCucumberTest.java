package com.sanches.consultacep;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:busca_cep.feature",
        glue = "com.sanches.consultacep",
        plugin = {"pretty", "html:target/cucumber"})
public class RunCucumberTest {

}
