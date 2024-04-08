import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class AdminTest extends TestHelper {


    String user = "randomuser3";
    String pass = "randompass3";

    @Test
    public void loggedInWithoutLoggingIn() {
        Assert.assertFalse(isLoggedIn());
    }

    @Test
    public void registerAccount() {
        create(user, pass);
        delete(user, pass);
    }

    @Test
    public void loginNewAccount() {
        create(user, pass);
        login(user, pass);
        Assert.assertTrue(isLoggedIn());
        delete(user, pass);
    }

    @Test
    public void logoutNewAccount() {
        create(user, pass);
        login(user, pass);
        Assert.assertTrue(isLoggedIn());
        logout();
        Assert.assertFalse(isLoggedIn());
        delete(user, pass);
    }


    @Test
    public void deleteAccount() {
        create(user, pass);
        delete(user, pass);
        Assert.assertEquals(false, isLoggedIn());
        login(user, pass);
        Assert.assertEquals(false, isLoggedIn());
    }

    @Test
    public void addProduct() {
        create(user, pass);
        driver.findElement(By.linkText("New product")).click();
        waitForElementById("product_title");
        driver.findElement(By.id("product_title")).sendKeys("New Test Product");
        driver.findElement(By.id("product_description")).sendKeys("This is a test product");
        new Select(driver.findElement(By.id("product_prod_type"))).selectByValue("Books");
        driver.findElement(By.id("product_price")).sendKeys("10");
        driver.findElement(By.name("commit")).click();
        waitFor(ExpectedConditions.presenceOfElementLocated(By.linkText("New Product")));
        driver.findElement(By.linkText("New Test Product"));
    }
}
