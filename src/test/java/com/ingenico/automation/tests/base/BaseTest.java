package com.ingenico.automation.tests.base;

import com.ingenico.automation.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.UUID;

public abstract class BaseTest {

    @BeforeAll
    static void setup() {
        TestConfig.init();

        // Filtry globalnie dla wszystkich requestów
        RestAssured.filters(
                correlationIdFilter(),
                logOnErrorStatusOrExceptionFilter()
        );
    }

    private static Filter correlationIdFilter() {
        return (FilterableRequestSpecification req,
                FilterableResponseSpecification res,
                FilterContext ctx) -> {

            req.header("X-Correlation-Id", UUID.randomUUID().toString());
            return ctx.next(req, res);
        };
    }

    /**
     * Działa w CI i z AssertJ:
     * - loguje request+response gdy status >= 400
     * - loguje request gdy poleci wyjątek (np. timeout/SSL)
     */
    private static Filter logOnErrorStatusOrExceptionFilter() {
        return (FilterableRequestSpecification req,
                FilterableResponseSpecification res,
                FilterContext ctx) -> {

            try {
                Response response = ctx.next(req, res);

                if (response != null && response.statusCode() >= 400) {
                    System.out.println("\n=== API ERROR (status >= 400) ===");
                    System.out.println(req.getMethod() + " " + req.getURI());
                    System.out.println("Headers: " + req.getHeaders());
                    if (req.getBody() != null) {
                        System.out.println("Request body:\n" + req.getBody());
                    }

                    System.out.println("\n--- Response ---");
                    System.out.println("Status: " + response.statusCode());
                    System.out.println("Headers: " + response.getHeaders());
                    System.out.println("Body:\n" + response.getBody().asPrettyString());
                    System.out.println("===============================\n");
                }

                return response;
            } catch (RuntimeException ex) {
                System.out.println("\n=== API EXCEPTION ===");
                System.out.println(req.getMethod() + " " + req.getURI());
                System.out.println("Headers: " + req.getHeaders());
                if (req.getBody() != null) {
                    System.out.println("Request body:\n" + req.getBody());
                }
                System.out.println("Exception: " + ex);
                System.out.println("=====================\n");
                throw ex;
            }
        };
    }
}

