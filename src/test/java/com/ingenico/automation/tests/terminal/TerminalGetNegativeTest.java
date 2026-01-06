package com.ingenico.automation.tests.terminal;

import com.ingenico.automation.tests.base.BaseTest;
import org.junit.jupiter.api.Test;
import com.ingenico.automation.client.TerminalApiClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Negative tests for GET /terminal/find/byTid/{terminalTid}
 */
class TerminalGetNegativeTest extends BaseTest {

    private static final String NOT_EXISTING_TID = "99999999";

    private final TerminalApiClient terminalApiClient =
            new TerminalApiClient();

    @Test
    void shouldReturn404WhenTerminalDoesNotExist() {

        var response =
                terminalApiClient.getTerminalByTid(NOT_EXISTING_TID);

        assertThat(response.statusCode())
                .as("Verify HTTP status code for non-existing terminal")
                .isEqualTo(404);
    }
}
