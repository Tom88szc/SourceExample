Feature: Token24Service secured SOAP requests

  @wssec @token24
  Scenario: Send OTP with WS-Security profile GBW
    Given SOAP endpoint "Token24Service" is configured
    And SOAP action is "sendOtp"
    And I use WS-Security profile "GBW"
    And I load XML template "token24/sendOtp-valid.xml"
    And I replace request fields:
      | correlationId    | AUTO_GENERATE |
      | tokenRequestorId | 12332111111   |
    When I send secured SOAP request
    Then HTTP status should be 200
