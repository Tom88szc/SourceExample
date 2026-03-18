Feature: Token24Service secured SOAP

  @wssec @token24 @gbw
  Scenario: Send sendOtp request with WS-Security profile GBW
    Given SOAP endpoint "Token24Service" is configured
    And SOAP action is "sendOtp"
    And I use WS-Security profile "GBW"
    And I load XML template "token24/sendOtp-secured.xml"
    And I replace request fields:
      | correlationId       | AUTO_GENERATE |
      | tokenRequestorId    | 99999999999   |
      | deviceName          | TEST_DEVICE   |
      | tokenRequesterType  | 3             |
      | path                | 1             |
    When I send secured SOAP request
    Then HTTP status should be 200
    And SOAP response should contain "correlationId"
