import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Tests {
    @Test
    public void task() {
        given().
                header("accept","application/json").
                queryParam("ISBN","9781449325862").
                when().
                get("https://bookstore.toolsqa.com/BookStore/v1/Book").
                then().
                assertThat().
                statusCode(200).
                body("title",equalTo("Git Pocket Guide"));
    }

    @DataProvider(name = "indexAndCountry")
    public Object[][] data() {
        return new Object[][] {
                {1, "USA"},
                {5, "Hungary"}
        };
    }

    @Test(dataProvider = "indexAndCountry")
    public void test (int index, String country){

        String circuitId = given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                extract().
                path("MRData.CircuitTable.Circuits.circuitId[" + index + "]");
        System.out.println(circuitId);
        given().
                pathParam("circuitId", circuitId).
                when().
                get("http://ergast.com/api/f1/circuits/{circuitId}.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.Location[0].country", equalTo(country));
    }

}
