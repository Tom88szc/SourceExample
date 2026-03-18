Feature: Token24Service secured SOAP examples

  @wssec @token24 @nsb
  Scenario: Send OTP with WS-Security profile NSB
    Given SOAP endpoint "Token24Service" is configured
    And SOAP action is "sendOtp"
    And I use WS-Security profile "NSB"
    And I load XML template "token24/sendOtp-valid.xml"
    And I replace request fields:
      | correlationId    | AUTO_GENERATE |
      | tokenRequestorId | 88877766655   |
    When I send secured SOAP request
    Then HTTP status should be 200
