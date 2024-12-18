package org.apiautomation.com.base;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import org.apiautomation.com.asserts.AssertActions;
import org.apiautomation.com.endpoints.APIConstants;
import org.apiautomation.com.modules.PayloadManager;
import org.apiautomation.com.pojos.Booking;
import org.apiautomation.com.pojos.BookingRespons;
import org.apiautomation.com.pojos.Bookingdates;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    public RequestSpecification requestSpecification;
    public AssertActions assertActions;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;
    public Response response;
    public ValidatableResponse validatableResponse;


    @BeforeTest(groups = {"integration","P0"})
    public void setUp() {
        // BASE URL, Content Type JSON
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();

//        requestSpecification =
//                RestAssured.given()
//                        .baseUri(APIConstants.BASE_URL)
//                        .contentType(ContentType.JSON)
//                        .log().all();

        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(APIConstants.BASE_URL)
                .addHeader("Content-Type", "application/json")
                .build().log().all();


    }

    public String getToken() {
        requestSpecification = RestAssured
                .given()
                .baseUri(APIConstants.BASE_URL)
                .basePath(APIConstants.AUTH_URL);

        // Setting the payload
        String payload = payloadManager.setAuthPayload();

        // Get the Token
        response = requestSpecification.contentType(ContentType.JSON).body(payload).when().post();

        // String Extraction
        String token = payloadManager.getTokenFromJSON(response.asString());

        return token;

    }


}