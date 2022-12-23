import Data.Constants;
import Data.DataProviderClass;
import Models.Response.SuccessResponse;
import Models.Response.UnsuccessResponse;
import Models.Response.UsersResponse;
import Steps.RegisterSteps;
import Steps.UsersStep;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestingOurMethods {
    //სახელი არასწორადაა შერჩეული. ვარჩევთ აპის მეთოდის მიხედვით მაგალითად;

    RegisterSteps registerSteps = new RegisterSteps();
    UsersStep usersStep = new UsersStep();
    SuccessResponse successResponse = new SuccessResponse();
    UnsuccessResponse unsuccessResponse = new UnsuccessResponse();
    UsersResponse usersResponse = new UsersResponse();

    @Test(dataProvider = "registration", dataProviderClass = DataProviderClass.class, priority = 1)
    @Description("ვტესტავთ საშინაო დავალებას - ნაწილი 1")
    @Severity(SeverityLevel.CRITICAL)
    public void userRegistration(String variant) throws JsonProcessingException {
        if(variant.equals("successfulRegistration")){
            Response response = registerSteps.sendRequest(registerSteps.setCorrectParameters());
            successResponse = registerSteps.deserializeJsonObjectForSuccess(response);
            if(response.statusCode() == 200){
                //ასერტები შეგგვიძლია ცალცალკე გავიტანოთ სტეპებში, მაგრამ თვალსაჩინოებისთვის აქ დავტოვე
                Assert.assertEquals(successResponse.getId(), Constants.successID);
                Assert.assertEquals(successResponse.getToken(), Constants.successToken);
            }
            else{
                Assert.fail();
            }
        }
        if(variant.equals("unsuccessfulRegistration")){
            Response response = registerSteps.sendRequest(registerSteps.setIncorrectParameters());
            unsuccessResponse = registerSteps.deserializeJsonObjectForFailed(response);
            if(response.statusCode() == 400){
                //ასერტები შეგგვიძლია ცალცალკე გავიტანოთ სტეპებში, მაგრამ თვალსაჩინოებისთვის აქ დავტოვე
                Assert.assertEquals(unsuccessResponse.getError(), Constants.missingEmailOrPasswordError);
            }
            else{
                Assert.fail();
            }
        }
    }
    @Test(priority = 2)
    @Description("ვტესტავთ საშინაო დავალებას - ნაწილი 2")
    @Severity(SeverityLevel.CRITICAL)
    public void userTest() throws JsonProcessingException {
        Response response = usersStep.sendRequest(usersStep.setCorrectParameters());
        usersResponse = usersStep.deserializeJsonObject(response);
        usersStep.validateResponseStatusCode(response);
        usersStep.validateResponseBody(usersResponse);
    }

}
