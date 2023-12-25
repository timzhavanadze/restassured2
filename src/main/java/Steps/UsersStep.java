package Steps;

import Data.Constants;
import Models.Request.UsersModel;
import Models.Response.UsersResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class UsersStep {
    UsersModel model = new UsersModel();
    ObjectMapper objectMapper = new ObjectMapper();
    @Step("Set correct body parameters for response")
    public String setCorrectParameters() throws JsonProcessingException {
        model.setName(Constants.name);
        model.setJob(Constants.job);
        return objectMapper.writeValueAsString(model);
    }
    @Step("Send request and get response")
    public Response sendRequest(String parameters) {
        RestAssured.baseURI = "https://reqres.in/api/users";
        return given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .body(parameters)
                .log().all()
                .post(baseURI);
    }

    @Step("Deserialize Json objects for success response")
    public UsersResponse deserializeJsonObject(Response response) throws JsonProcessingException {
        return objectMapper.readValue(response.getBody().jsonPath().prettyPrint(), UsersResponse.class);
    }
    @Step("Validate response status code")
    public UsersStep validateResponseStatusCode(Response response){
        Assert.assertEquals(response.statusCode(),201);
        return this;
    }
    @Step("Validate response body")
    public UsersStep validateResponseBody(UsersResponse response){
        Assert.assertEquals(response.getJob(),Constants.job);
        Assert.assertEquals(response.getName(),Constants.name);
        Assert.assertNotNull(response.getId());
        return this;
    }
}
