package com.ingenico.automation.client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TerminalApiClient {

    public Response getTerminalByTid(String tid) {
        return given()
                .pathParam("terminalTid", tid)
                .when()
                .get("/terminal/find/byTid/{terminalTid}");
    }
}