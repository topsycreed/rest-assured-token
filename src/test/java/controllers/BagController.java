package controllers;

import config.TestPropertiesConfig;
import dto.AddItemRequest;
import dto.BagResponse;
import enums.UserRole;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import support.TokenManager;

import java.util.List;

import static enums.UserRole.GUEST;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class BagController {
    RequestSpecification requestSpecification;
    private static final String ITEMS_ENDPOINT = "/ugp-api/bag/v1/items";
    private static final String BAG_ENDPOINT = "/ugp-api/bag/v1";

    TestPropertiesConfig configProperties = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    public BagController() {
        this.requestSpecification = given()
                .accept(JSON)
                .contentType(JSON)
                .baseUri(configProperties.getApiBaseUrl())
                .header("aesite", "AEO_US")
                .header("x-access-token", TokenManager.getToken(GUEST))
                .filter(new AllureRestAssured());
    }

    @Step("Add item to bag")
    public Response addItem(String skuId, int quantity) {
        AddItemRequest.Item item = new AddItemRequest.Item(skuId, quantity);
        AddItemRequest request = new AddItemRequest(List.of(item));

        return given(this.requestSpecification)
                .body(request)
                .when()
                .post(ITEMS_ENDPOINT)
                .andReturn();
    }

    @Step("Get bag")
    public BagResponse getBag() {
        return given(this.requestSpecification)
                .when()
                .get(BAG_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(BagResponse.class);
    }
}
