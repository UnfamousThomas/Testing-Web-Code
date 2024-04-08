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
        addNewProduct(user, pass, "New Test Product", "10", "Books");
        Assert.assertTrue(isElementPresent(By.linkText("New Test Product")));
        driver.findElement(By.id("New Test Product")).findElement(By.linkText("Delete")).click();
        delete(user, pass);
    }

    @Test
    public void addProductWithNegativePrice() {
        addNewProduct(user, pass, "New Test Product", "-1", "Books");
        Assert.assertFalse(isElementPresent(By.linkText("New Test Product")));
        delete(user, pass);
    }

    @Test
    public void editProduct() {
        adminEditProduct(user, pass, "Books");
        driver.findElement(By.linkText("Products")).click();
        driver.findElement(By.id("Edited Test Product")).findElement(By.linkText("Delete")).click();
        delete(user, pass);
    }

    @Test
    public void editSunglassesAndCheckCategory() {
        adminEditProduct(user, pass, "Sunglasses");
        Assert.assertEquals("Type: Sunglasses", driver.findElement(By.id("main")).findElement(By.className("products_column")).findElements(By.cssSelector("p")).get(3).getText().trim());
    }

    @Test
    public void deleteProduct() {
        addNewProduct(user, pass, "New Test Product", "10", "Books");
        Assert.assertTrue(isElementPresent(By.linkText("New Test Product")));
        driver.findElement(By.id("New Test Product")).findElement(By.linkText("Delete")).click();
        delete(user, pass);
    }
}
