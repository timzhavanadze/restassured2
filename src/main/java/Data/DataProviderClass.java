package Data;

import org.testng.annotations.DataProvider;

public class DataProviderClass {
    @DataProvider(name = "registration")
    public static  Object[][] successAndUnsuccessful(){
        return new Object[][]{
                {"successfulRegistration"},{"unsuccessfulRegistration"}
        };
    }
}
