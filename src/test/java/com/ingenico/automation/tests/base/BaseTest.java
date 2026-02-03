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


//include:
//        - project: "EMEA-PUBLIC/SCM/Templates/gitlab-ci"
//ref: release
//file: "/template-functions.yml"
//
//image: harbor-emea.1dc.com/customimages/scm/maven-jdk-git-alpine:3.9.6_17.0.15_p6-r0_2.47.2_3.21.3-44
//
//stages:
//        - build
//  - test
//
//# Kiedy pipeline w ogóle ma się uruchamiać
//workflow:
//rules:
//        - if: $CI_PIPELINE_SOURCE == "merge_request_event"
//        - if: $CI_PIPELINE_SOURCE == "schedule"
//        - if: $CI_COMMIT_BRANCH == "main"
//        - if: $CI_COMMIT_BRANCH == "develop"
//        - when: never
//
//variables:
//MAVEN_OPTS: "-Dmaven.repo.local=.m2/repository"
//MAVEN_CLI_OPTS: "-B -e -V"
//        # Domyślnie na MR odpalamy smoke, a pełne suite ustawiamy regułami poniżej
//TAGS: "smoke"
//        # Opcjonalnie: sterowanie środowiskiem przez CI
//  # BASE_URL: "https://...."
//        # TLS_RELAXED: "true"
//
//cache:
//key: maven-cache
//paths:
//        - .m2/repository
//
//default:
//tags:
//        - scm-eks-shared
//interruptible: true
//retry:
//max: 1
//when:
//        - runner_system_failure
//      - stuck_or_timeout_failure
//      - api_failure
//timeout: 20m
//
//# --- (Opcjonalnie) maven settings.xml z CI variable (proxy / nexus) ---
//        # Ustaw w GitLab: Settings -> CI/CD -> Variables:
//        # MAVEN_SETTINGS_XML = (cała treść settings.xml)
//        .before_maven_settings:
//before_script:
//        - mkdir -p ~/.m2
//    - |
//            if [ -n "$MAVEN_SETTINGS_XML" ]; then
//echo "$MAVEN_SETTINGS_XML" > ~/.m2/settings.xml
//echo "[INFO] Maven settings.xml provided via CI variable"
//        else
//echo "[INFO] No Maven settings.xml provided"
//fi
//
//# --- BUILD: kompilacja + paczka bez testów ---
//build:
//stage: build
//  extends: .before_maven_settings
//script:
//        - mvn $MAVEN_CLI_OPTS -DskipTests clean package
//artifacts:
//when: always
//expire_in: 1 day
//paths:
//        - target/*.jar
//      - target/classes/
//      - target/test-classes/
//
//# --- TESTS: uruchomienie testów ---
//api_tests:
//  stage: test
//  extends: .before_maven_settings
//  needs: ["build"]
//  script:
//    # Nie robimy "clean", żeby nie kasować target po buildzie.
//    # TAGS steruje uruchamianiem (smoke/flow/contract/negative)
//    - mvn $MAVEN_CLI_OPTS test -Dtags="${TAGS}"
//  artifacts:
//    when: always
//    expire_in: 7 days
//    reports:
//      junit:
//        - target/surefire-reports/TEST-*.xml
//    paths:
//      - target/surefire-reports/
//  rules:
//    # MR -> smoke
//    - if: $CI_PIPELINE_SOURCE == "merge_request_event"
//      variables:
//        TAGS: "smoke"
//    # main/develop -> pełny zestaw (przykład)
//    - if: $CI_COMMIT_BRANCH == "main"
//      variables:
//        TAGS: "smoke,contract,flow,negative"
//    - if: $CI_COMMIT_BRANCH == "develop"
//      variables:
//        TAGS: "smoke,contract,flow,negative"
//    # schedule/nightly -> to, co ustawisz w harmonogramie (albo fallback)
//    - if: $CI_PIPELINE_SOURCE == "schedule"
//      when: on_success
