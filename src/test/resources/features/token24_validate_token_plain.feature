Feature: Token24Service plain SOAP examples

  @plain @token24
  Scenario: Validate token request without WS-Security
    Given SOAP endpoint "Token24Service" is configured
    And SOAP action is "validateToken"
    And I load XML template "token24/validateToken-plain.xml"
    And I replace request fields:
      | correlationId    | AUTO_GENERATE |
      | tokenRequestorId | 99999999999   |
      | tokenReferenceId | TEST_TOKEN_01 |
      | deviceName       | TEST_DEVICE   |
    When I send plain SOAP request
    Then HTTP status should be 200
