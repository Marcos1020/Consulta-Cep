package com.sanches.consultacep.stepDefinitions;

import com.sanches.consultacep.controller.CepController;
import com.sanches.consultacep.controller.request.CepRequest;
import com.sanches.consultacep.controller.response.CepResponse;
import com.sanches.consultacep.exception.BadRequestException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@CucumberContextConfiguration
@RunWith(Cucumber.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberOptions(features = "cep.feature:buscaCep.feature")
public class BuscaCepSteps {

    private CepRequest cepRequest;
    private ResponseEntity<?> responseEntity;

    private CepController cepController;

    @Autowired
    public BuscaCepSteps(CepController cepController) {
        this.cepController = cepController;
    }

    @Given("um CEP válido")
    public void umCepValido() {
        cepRequest = new CepRequest("15138226");
    }

    @When("o serviço de busca de CEP é chamado")
    public void buscaCep() throws BadRequestException {
        responseEntity = cepController.buscaCep(cepRequest);
    }

    @Then("a resposta deve conter o CEP e suas informações correspondentes")
    public void respostaContemCepEInformacoes() {
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(CepResponse.class);
        CepResponse cepResponse = (CepResponse) responseEntity.getBody();
        assertThat(cepResponse.getCep()).isEqualTo(cepRequest.getCep());
        assertThat(cepResponse.getCep()).isNotEmpty();
    }
}
