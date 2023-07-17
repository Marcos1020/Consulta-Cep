Feature: Busca CEP

  Scenario: Busca CEP válido
    Given usuário informou um CEP válido
    When faço uma requisição para buscar o CEP
    Then a resposta deve ter o status 200 Ok
    And o corpo da resposta deve conter um retorno válido

  Scenario: Busca CEP inválido
    Given usuário informou um CEP inválido
    Then a resposta deve ter o status 400 Bad Request