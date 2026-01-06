package com.ingenico.automation.tests.base;

import org.junit.jupiter.api.BeforeAll;
import com.ingenico.automation.config.LoggingConfig;
import com.ingenico.automation.config.TestConfig;

/**
 * Base class for all API tests.
 *
 * <p>Responsible for global test setup such as configuration
 * and request/response logging.</p>
 *
 * <p>All test classes should extend this class.</p>
 */
public abstract class BaseTest {

    @BeforeAll
    static void setup() {
        // Load environment configuration (base URL etc.)
        TestConfig.init();

        // Enable minimal request/response logging
        LoggingConfig.enable();
    }
}
