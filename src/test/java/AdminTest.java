import org.junit.Assert;
import org.junit.Before;
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
    public void registerExistingAccount() {
        create(user, user);
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
        Assert.assertFalse(isLoggedIn());
        login(user, pass);
        Assert.assertFalse(isLoggedIn());
    }

    @Test
    public void addProduct() {
        addNewProduct(user, pass, "New Test Product", "10");
        Assert.assertTrue(isElementPresent(By.linkText("New Test Product")));
        driver.findElement(By.id("New Test Product")).findElement(By.linkText("Delete")).click();
        delete(user, pass);
    }

    @Test
    public void addProductWithNegativePrice() {
        addNewProduct(user, pass, "New Test Product", "-1");
        Assert.assertFalse(isElementPresent(By.linkText("New Test Product")));
        delete(user, pass);
    }

    @Test
    public void editProduct() {
        addNewProduct(user, pass, "New Test Product", "10");
        Assert.assertTrue(isElementPresent(By.linkText("New Test Product")));
        driver.findElement(By.id("New Test Product")).findElement(By.linkText("Edit")).click();
        waitForElementById("product_title");
        driver.findElement(By.id("product_title")).clear();
        driver.findElement(By.id("product_title")).sendKeys("Edited Test Product");
        driver.findElement(By.name("commit")).click();
        Assert.assertEquals("Title: Edited Test Product", driver.findElement(By.id("main")).findElement(By.className("products_column")).findElements(By.cssSelector("p")).get(1).getText().trim());
        driver.findElement(By.linkText("Products")).click();
        driver.findElement(By.id("Edited Test Product")).findElement(By.linkText("Delete")).click();
        delete(user, pass);
    }

    @Test
    public void deleteProduct() {
        addNewProduct(user, pass, "New Test Product", "10");
        Assert.assertTrue(isElementPresent(By.linkText("New Test Product")));
        driver.findElement(By.id("New Test Product")).findElement(By.linkText("Delete")).click();
        delete(user, pass);
    }
}
