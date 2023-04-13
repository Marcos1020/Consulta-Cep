
Feature: Busca de CEP

  Scenario: Buscar um CEP válido
    Given um CEP válido
    When o serviço de busca de CEP é chamado
    Then a resposta deve conter o CEP e suas informações correspondentes