package com.ingenico.automation.tests;

import org.junit.jupiter.api.Test;
import com.ingenico.automation.client.TerminalApiClient;
import com.ingenico.automation.model.TerminalRecord;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for GET /terminal/find/byTid/{terminalTid}
 */
class TerminalGetTest extends BaseTest {

    private static final String EXISTING_TID = "12345678";

    private final TerminalApiClient terminalApiClient =
            new TerminalApiClient();

    @Test
    void shouldReturnTerminalWhenTidExists() {

        var response =
                terminalApiClient.getTerminalByTid(EXISTING_TID);

        assertThat(response.statusCode())
                .as("Verify HTTP status code")
                .isEqualTo(200);

        TerminalRecord terminal =
                response.as(TerminalRecord.class);

        assertThat(terminal.getTid())
                .as("Verify returned TID")
                .isEqualTo(EXISTING_TID);

        assertThat(terminal.getSerialNumber())
                .as("Verify serial number is present")
                .isNotBlank();
    }
}
