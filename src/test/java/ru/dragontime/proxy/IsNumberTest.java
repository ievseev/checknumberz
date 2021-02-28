package ru.dragontime.proxy;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

class IsNumberTest extends BaseAPI {

    @ParameterizedTest
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

    @ParameterizedTest
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

    @ParameterizedTest
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

    @Test
    void requestWithoutBody() {
        given().
                contentType(ContentType.JSON).
        when().
                post("/isnumber").
        then().
                statusCode(400);
    }

    @Test
    void requestWithEmptyBody() {
        String requestString = "{}";

        given().
                contentType(ContentType.JSON).
                body(requestString).
        when().
                post("/isnumber").
        then().
                statusCode(400);
    }
}
