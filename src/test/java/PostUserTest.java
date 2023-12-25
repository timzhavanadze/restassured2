import Models.Request.PostUserModel;
import Models.Response.Book;
import Models.Response.PostUserError;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.CoreMatchers.equalTo;

public class PostUserTest {
    @Test
    public void queryParameter() {
        Response response = given()
                .config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("ContentType.JSON", ContentType.TEXT)))
                .baseUri("https://bookstore.toolsqa.com/BookStore/v1")
                .queryParam("ISBN","9781449325862")
                .log().all()
                .get("/Book")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().
                response();
        Book book;
        book = response.as(Book.class);
        System.out.println(book.getPublisher());
        Assert.assertEquals(book.getPublisher(), "O'Reilly Media");
    }
    @Test(description = "register", priority = 1)
    @Description("register user")
    public void addUser() {
        PostUserModel postUserModel = new PostUserModel("User", "123");
        Response response = given()
                .config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("ContentType.JSON", ContentType.TEXT)))
                .baseUri("https://bookstore.toolsqa.com")
                .body(postUserModel)
                .log().all()
                .post("/Account/v1/User")
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .extract().
                        response();

        PostUserError postUserError;
        postUserError = response.as(PostUserError.class);
        System.out.println(postUserError.getCode());
        Assert.assertEquals(postUserError.getCode(), "1200");
        Assert.assertEquals(postUserError.getMessage(), "UserName and Password required.");
    }

}
