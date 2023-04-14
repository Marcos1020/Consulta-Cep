Feature: Busca CEP

  Scenario: Busca CEP válido
    Given usuário informou um CEP válido
    When eu faço uma requisição para buscar o CEP
    Then a resposta deve ter o status 200 OK
    And o corpo da resposta deve conter um CepResponse válido

  Scenario: Busca CEP inválido
    Given usuário informou um CEP inválido
    When eu faço uma requisição para buscar o CEP
    Then a resposta deve ter o status 400 Bad Request

