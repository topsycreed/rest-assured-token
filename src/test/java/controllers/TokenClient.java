package controllers;

import config.TestPropertiesConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.NotImplementedException;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.URLENC;

public class TokenClient {
    private static final String TOKEN_ENDPOINT = "/ugp-api/auth/oauth/v4/token";

    static TestPropertiesConfig configProperties = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    public static RequestSpecification guestAuthSpec() {
        return given()
                .baseUri(configProperties.getApiBaseUrl())
                .accept(JSON)
                .contentType(URLENC)
                .header("aelang", "en_US")
                .header("aesite", "AEO_US")
                .header("aecountry", "US")
                .header("authorization", configProperties.getGuestHeaderAuth())
                .filter(new AllureRestAssured());
    }

    @Step("Get guest token")
    public static String getGuestToken() {
        Response response = guestAuthSpec()
                .when()
                .formParam("grant_type", "client_credentials")
                .post(TOKEN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response.jsonPath().getString("access_token");
    }

    public static String getAuthToken() {
        throw new NotImplementedException("Not yet implemented!");
    }
}
