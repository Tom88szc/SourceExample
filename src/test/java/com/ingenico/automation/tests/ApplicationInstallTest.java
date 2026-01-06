package com.ingenico.automation.tests;

import org.junit.jupiter.api.Test;
import com.ingenico.automation.client.ApplicationApiClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for POST /application/install/{applicationLabel}
 */
class ApplicationInstallTest extends BaseTest {

    private static final String APPLICATION_LABEL = "PAYMENT_APP";

    private final ApplicationApiClient applicationApiClient =
            new ApplicationApiClient();

    @Test
    void shouldInstallApplicationSuccessfully() {

        var response =
                applicationApiClient.installApplication(APPLICATION_LABEL);

        assertThat(response.statusCode())
                .as("Verify application install response status")
                .isEqualTo(200);
    }
}
