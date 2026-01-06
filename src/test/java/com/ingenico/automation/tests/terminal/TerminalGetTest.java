package com.ingenico.automation.tests.terminal;

import com.ingenico.automation.tests.base.BaseTest;
import org.junit.jupiter.api.Test;
import com.ingenico.automation.client.TerminalApiClient;
import com.ingenico.automation.model.TerminalRecord;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Happy path tests for GET /terminal/find/byTid/{terminalTid}
 */
class TerminalGetTest extends BaseTest {

    private static final String EXISTING_TID = "12345678";

    private final TerminalApiClient terminalApiClient =
            new TerminalApiClient();

    @Test
    void shouldReturnValidTerminalDataAccordingToSwagger() {

        var response =
                terminalApiClient.getTerminalByTid(EXISTING_TID);

        // --- HTTP level ---
        assertThat(response.statusCode())
                .as("Verify HTTP status code")
                .isEqualTo(200);

        // --- Contract level ---
        TerminalRecord terminal =
                response.as(TerminalRecord.class);

        assertThat(terminal.getTid())
                .as("TID must match requested value")
                .isEqualTo(EXISTING_TID);

        assertThat(terminal.getMerchant())
                .as("Merchant should be present")
                .isNotBlank();

        assertThat(terminal.getSerialNumber())
                .as("Serial number should be present")
                .isNotBlank();

        assertThat(terminal.getDesc())
                .as("Description should be present")
                .isNotNull();
    }
}
