package Steps;

import Data.Constants;
import Models.Request.RequestModel;
import Models.Response.SuccessResponse;
import Models.Response.UnsuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class RegisterSteps {
    RequestModel model = new RequestModel();
    ObjectMapper objectMapper = new ObjectMapper();

    @Step("Set correct body parameters for Successful response")
    public String setCorrectParameters() throws JsonProcessingException {
        model.setEmail(Constants.correctEmail);
        model.setPassword(Constants.correctPassword);
        return objectMapper.writeValueAsString(model);
    }

    @Step("Set incorrect body parameters for Unsuccessful response")
    public String setIncorrectParameters() throws JsonProcessingException {
        model.setEmail(Constants.incorrectEmail);
        model.setEmail(null);
        return objectMapper.writeValueAsString(model);
    }

    @Step("Send request and get response")
    public Response sendRequest(String parameters) {
        RestAssured.baseURI = "https://reqres.in/api/register";
        return given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .body(parameters)
                .log().all()
                .post(baseURI);
    }

    @Step("Deserialize Json objects for success response")
    public SuccessResponse deserializeJsonObjectForSuccess(Response response) throws JsonProcessingException {
        return objectMapper.readValue(response.getBody().jsonPath().prettyPrint(), SuccessResponse.class);

    }

    @Step("Deserialize Json objects for failed response")
    public UnsuccessResponse deserializeJsonObjectForFailed(Response response) throws JsonProcessingException {
        return objectMapper.readValue(response.getBody().jsonPath().prettyPrint(), UnsuccessResponse.class);

    }
}
