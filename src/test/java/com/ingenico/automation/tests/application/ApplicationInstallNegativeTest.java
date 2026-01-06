package com.ingenico.automation.tests.application;

import com.ingenico.automation.tests.base.BaseTest;
import org.junit.jupiter.api.Test;
import com.ingenico.automation.client.ApplicationApiClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Negative tests for POST /application/install/{applicationLabel}
 */
class ApplicationInstallNegativeTest extends BaseTest {

    private static final String INVALID_APPLICATION_LABEL = "INVALID_APP";

    private final ApplicationApiClient applicationApiClient =
            new ApplicationApiClient();

    @Test
    void shouldReturnClientErrorWhenApplicationLabelIsInvalid() {

        var response =
                applicationApiClient.installApplication(
                        INVALID_APPLICATION_LABEL
                );

        assertThat(response.statusCode())
                .as("Verify HTTP status for invalid application label")
                .isIn(400, 404);
    }
}
