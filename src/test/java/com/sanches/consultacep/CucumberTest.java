package com.sanches.consultacep;

import com.sanches.consultacep.controller.CepController;
import com.sanches.consultacep.controller.request.CepRequest;
import com.sanches.consultacep.controller.response.CepResponse;
import com.sanches.consultacep.exception.BadRequestException;
import com.sanches.consultacep.exception.NotFoundException;
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
import static org.junit.Assert.assertEquals;
@CucumberContextConfiguration
@SpringBootTest
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:busca_cep.feature",
        glue = "com.sanches.consultacep",
        plugin = {"pretty", "html:target/cucumber"})
public class CucumberTest {

    private CepRequest cepRequest;
    private ResponseEntity<?> responseEntity;

    private CepController cepController;

    @Autowired
    public CucumberTest(CepController cepController) {
        this.cepController = cepController;
    }

    @Given("usuário informou um CEP válido")
    public void umCepValido() {
        cepRequest = new CepRequest("15138226");
    }

    @Given("usuário informou um CEP inválido")
    public void oUsuárioInformouUmCEPInválido() {
        cepRequest = new CepRequest("151382-26");
    }

    @When("faço uma requisição para buscar o CEP")
    public void buscaCep() throws BadRequestException, NotFoundException {
        responseEntity = cepController.buscaCep(cepRequest);
    }

    @Then("a resposta deve ter o status 200 Ok")
    public void aRespostaDeveTerOStatus200OK() {
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Then("a resposta deve ter o status 400 Bad Request")
    public void aRespostaDeveTerOStatus400BadRequest() {
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Then("o corpo da resposta deve conter um retorno válido")
    public void respostaContemCepEInformacoes() {

        String cep = "15138-226";
        Double freightValuee = 7.85;
        String neighborhood = "Jardim Santa Cláudia";
        String state = "SP";
        String street = "Rua Domingos Tedeschi";

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(CepResponse.class);
        CepResponse cepResponse = (CepResponse) responseEntity.getBody();
        assertThat(cepResponse.getCep()).isEqualTo(cep);
        assertThat(cepResponse.getCep()).isNotEmpty();
        assertThat(cepResponse.getValorFrete().equals(freightValuee));
        assertThat(cepResponse.getBairro().equals(neighborhood));
        assertThat(cepResponse.getEstado().toUpperCase().equals(state));
        assertThat(cepResponse.getRua().equals(street));
        assertThat(cepResponse.getComplemento().equals(""));
    }
}