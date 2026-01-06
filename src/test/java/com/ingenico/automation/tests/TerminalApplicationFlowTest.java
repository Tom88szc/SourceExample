package com.ingenico.automation.tests;

import org.junit.jupiter.api.Test;
import com.ingenico.automation.client.ApplicationApiClient;
import com.ingenico.automation.client.TerminalApiClient;
import com.ingenico.automation.model.TerminalRecord;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Business flow test:
 *
 * GET terminal by TID
 * POST install application
 * GET terminal again
 */
class TerminalApplicationFlowTest extends BaseTest {

    private static final String EXISTING_TID = "12345678";
    private static final String APPLICATION_LABEL = "PAYMENT_APP";

    private final TerminalApiClient terminalApiClient =
            new TerminalApiClient();

    private final ApplicationApiClient applicationApiClient =
            new ApplicationApiClient();

    @Test
    void shouldInstallApplicationForTerminalFlow() {

        // STEP 1: GET terminal
        var getTerminalResponse =
                terminalApiClient.getTerminalByTid(EXISTING_TID);

        assertThat(getTerminalResponse.statusCode())
                .as("Verify GET terminal response")
                .isEqualTo(200);

        TerminalRecord terminal =
                getTerminalResponse.as(TerminalRecord.class);

        assertThat(terminal.getTid())
                .isEqualTo(EXISTING_TID);

        String serialNumber = terminal.getSerialNumber();
        assertThat(serialNumber).isNotBlank();

        // STEP 2: POST install application
        var installResponse =
                applicationApiClient.installApplication(APPLICATION_LABEL);

        assertThat(installResponse.statusCode())
                .as("Verify application install response")
                .isEqualTo(200);

        // STEP 3: GET terminal again
        var getTerminalAfterInstallResponse =
                terminalApiClient.getTerminalByTid(EXISTING_TID);

        assertThat(getTerminalAfterInstallResponse.statusCode())
                .as("Verify GET terminal after install")
                .isEqualTo(200);

        TerminalRecord terminalAfterInstall =
                getTerminalAfterInstallResponse.as(TerminalRecord.class);

        assertThat(terminalAfterInstall.getTid())
                .isEqualTo(EXISTING_TID);

        // Stability check – terminal identity should not change
        assertThat(terminalAfterInstall.getSerialNumber())
                .isEqualTo(serialNumber);
    }
}
