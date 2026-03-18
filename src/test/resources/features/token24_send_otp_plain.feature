Feature: Token24Service plain SOAP

  @plain @token24
  Scenario: Send sendOtp request without WS-Security
    Given SOAP endpoint "Token24Service" is configured
    And SOAP action is "sendOtp"
    And I load XML template "token24/sendOtp-valid.xml"
    And I replace request fields:
      | correlationId    | AUTO_GENERATE |
      | tokenRequestorId | 99999999999   |
    When I send plain SOAP request
    Then HTTP status should be 200
