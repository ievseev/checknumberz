package ru.dragontime.proxy;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

class IsNumberTest extends BaseAPI {

    @ParameterizedTest(name = "Check that int value {0} is number")
    @ValueSource(ints = {Integer.MIN_VALUE, -100, 0, 100, Integer.MAX_VALUE})
    void intIsNumber(int number) {
        String requestString = String.format("{\"value\":%d}", number);

        given().
                contentType(ContentType.JSON).
                body(requestString).
        when().
                post("/isnumber").
        then().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("schemas/isnumber.json")).
                body("isNumber", equalTo(true));
    }

    @ParameterizedTest(name = "Check that double value {0} is number")
    @ValueSource(doubles = {Double.MIN_VALUE, -100.1, 0.0, 100.1, Double.MAX_VALUE})
    void doubleIsNumber(double number) {
        String requestString = String.format("{\"value\":%s}", number);

        given().
                contentType(ContentType.JSON).
                body(requestString).
        when().
                post("/isnumber").
        then().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("schemas/isnumber.json")).
                body("isNumber", equalTo(true));
    }

    @ParameterizedTest(name = "Check that string value \"{0}\" is not number")
    @ValueSource(strings = {"", "c", "string", "xxi"})
    void stringIsNotNumber(String str) {
        String requestString = String.format("{\"value\":\"%s\"}", str);

        given().
                contentType(ContentType.JSON).
                body(requestString).
        when().
                post("/isnumber").
        then().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("schemas/isnumber.json")).
                body("isNumber", equalTo(false));
    }

    @DisplayName("Validate request with incorrect body")
    @Test
    void requestWithIncorrectBody() {
        String requestString = String.format("{\"unknown\":\"%s\"}", 10);

        given().
                contentType(ContentType.JSON).
                body(requestString).
        when().
                post("/isnumber").
        then().
                statusCode(400);
    }

    @DisplayName("Validate request without body")
    @Test
    void requestWithEmptyBody() {
        given().
                contentType(ContentType.JSON).
        when().
                post("/isnumber").
        then().
                statusCode(400);
    }
}
