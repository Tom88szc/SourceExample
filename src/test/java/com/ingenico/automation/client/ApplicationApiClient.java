package com.ingenico.automation.client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApplicationApiClient {

    public Response installApplication(String applicationLabel) {
        return given()
                .pathParam("applicationLabel", applicationLabel)
                .when()
                .post("/application/install/{applicationLabel}");
    }
}