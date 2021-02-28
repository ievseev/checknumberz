package ru.dragontime.proxy;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;

import ru.dragontime.proxy.config.Configuration;
import ru.dragontime.proxy.config.ConfigurationManager;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseAPI {

    protected static Configuration configuration;

    @BeforeAll
    public static void beforeAllTests() {
        configuration = ConfigurationManager.getConfiguration();

        baseURI = configuration.baseURI();
        basePath = configuration.basePath();
        port = configuration.port();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
