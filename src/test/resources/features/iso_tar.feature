Feature: IsoService plain SOAP requests

  @plain @iso @smoke
  Scenario: Send TAR request without WS-Security
    Given SOAP endpoint "IsoService" is configured
    And SOAP action is "tar"
    And I load XML template "iso/tar-valid.xml"
    And I replace request fields:
      | pan                | 5555444433331111 |
      | panExpiryDate      | 2901             |
      | terminalId         | TERM_TEST_001    |
      | acceptorId         | ACC_TEST_001     |
      | acceptorName       | TEST_ACCEPTOR    |
      | correlationId      | AUTO_GENERATE    |
      | tokenAction        | 01               |
      | tokenRequestorId   | 99999999999      |
      | tokenRequesterType | 3                |
      | deviceName         | TEST_DEVICE      |
      | path               | 1                |
    When I send plain SOAP request
    Then HTTP status should be 200
