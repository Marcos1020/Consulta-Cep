package com.sanches.consultacep;

import com.sanches.consultacep.controller.CepController;
import com.sanches.consultacep.controller.request.CepRequest;
import com.sanches.consultacep.controller.response.CepResponse;
import com.sanches.consultacep.exception.BadRequestException;
import com.sanches.consultacep.exception.NotFoundException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
@CucumberContextConfiguration
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

    @Then("a resposta deve ter o status {int} Ok")
    public void aRespostaDeveTerOStatus200OK() {
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Then("a resposta deve ter o status 400 Bad Request")
    public void aRespostaDeveTerOStatus400BadRequest() {
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Then("o corpo da resposta deve conter um CepResponse válido")
    public void respostaContemCepEInformacoes() {
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(CepResponse.class);
        CepResponse cepResponse = (CepResponse) responseEntity.getBody();
        assertThat(cepResponse.getCep()).isEqualTo(cepRequest.getCep());
        assertThat(cepResponse.getCep()).isNotEmpty();
        assertThat(cepResponse.getValorFrete().equals(7.85));
        assertThat(cepResponse.getBairro().equals("Jardim Santa Cláudia"));
        assertThat(cepResponse.getEstado().equals("SP"));
        assertThat(cepResponse.getRua().equals("Rua Domingos Tedeschi"));
        assertThat(cepResponse.getComplemento().equals(""));
    }
}
