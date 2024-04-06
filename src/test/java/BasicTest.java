import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

public class BasicTest extends TestHelper {


    private final String username = "a";
    private String password = "1";

    @Test
    public void titleExistsTest(){
        String expectedTitle = "ST Online Store";
        String actualTitle = driver.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }


    /*
    In class Exercise

    Fill in loginLogoutTest() and login mehtod in TestHelper, so that the test passes correctly.

     */
    @Test
    public void loginLogoutTest(){

        login(username, password);

        WebElement adminHeader = driver.findElement(By.className("admin_menu"));
        assertNotNull(adminHeader);

        logout();
        WebElement adminNewHeader = driver.findElement(By.id("menu"));

        assertNotNull(adminNewHeader);

    }

    /*
    In class Exercise

     Write a test case, where you make sure, that one can’t log in with a false password

     */
    @Test
    public void loginFalsePassword() {
        login(username, "randompassword123");

        WebElement notice = driver.findElement(By.id("notice"));

        assertEquals(notice.getText().strip(), "Invalid user/password combination");

    }

}
