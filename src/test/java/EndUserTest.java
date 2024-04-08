import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class EndUserTest extends TestHelper {

    @Test
    public void addToCart() {
        String product = "Web Application Testing Book";
        addProductToCart(product);
        Assert.assertEquals(product, driver.findElement(By.id("cart")).findElement(By.id("current_item")).findElements(By.cssSelector("td")).get(1).getText().trim());
    }

    @Test
    public void increaseQuantity() {
        addProductToCart("Web Application Testing Book");
        driver.findElement(By.linkText("↑")).click();
        String amount = driver.findElement(By.id("cart")).findElement(By.id("current_item")).findElements(By.cssSelector("td")).get(0).getText().trim();
        Assert.assertEquals("2", amount.substring(0, amount.length() - 1));
    }

    @Test
    public void decreaseQuantity() {
        addProductToCart("Web Application Testing Book");
        driver.findElement(By.linkText("↑")).click();
        String amount = driver.findElement(By.id("cart")).findElement(By.id("current_item")).findElements(By.cssSelector("td")).get(0).getText().trim();
        Assert.assertEquals("2", amount.substring(0, amount.length() - 1));
        driver.findElement(By.linkText("↓")).click();
        amount = driver.findElement(By.id("cart")).findElement(By.id("current_item")).findElements(By.cssSelector("td")).get(0).getText().trim();
        Assert.assertEquals("1", amount.substring(0, amount.length() - 1));
    }

    @Test
    public void deleteOneProduct() {
        addProductToCart("Web Application Testing Book");
        String amount = driver.findElement(By.id("cart")).findElement(By.id("current_item")).findElements(By.cssSelector("td")).get(0).getText().trim();
        Assert.assertEquals("1", amount.substring(0, amount.length() - 1));
        driver.findElement(By.linkText("X")).click();
        Assert.assertEquals(0, driver.findElements(By.linkText("X")).size());
    }

    @Test
    public void emptyCart() {
        addProductToCart("Web Application Testing Book");
        addProductToCart("B45593 Sunglasses");
        driver.findElement(By.id("cart")).findElement(By.className("button_to")).findElement(By.cssSelector("input[type=submit]")).click();
        Assert.assertEquals(0, driver.findElements(By.linkText("X")).size());
    }

    @Test
    public void searchProductByName() {
        driver.findElement(By.id("search_input")).clear();
        driver.findElement(By.id("search_input")).sendKeys("Web Application Testing Book");
        Assert.assertEquals(0, driver.findElement(By.id("main")).findElements(By.linkText("B45593 Sunglasses")).size());
    }

    @Test
    public void showOnlySunglasses() {
        driver.findElement(By.linkText("Sunglasses")).click();
        waitForElementById("Sunglasses");
        Assert.assertEquals(0, driver.findElement(By.id("main")).findElements(By.linkText("Web Application Testing Book")).size());
    }

    @Test
    public void showOnlyBooks() {
        driver.findElement(By.linkText("Books")).click();
        waitForElementById("Books");
        Assert.assertEquals(0, driver.findElement(By.id("main")).findElements(By.partialLinkText("Sunglasses")).size());
    }

    @Test
    public void purchase() {
        addProductToCart("Web Application Testing Book");
        driver.findElement(By.id("cart")).findElements(By.cssSelector("form")).get(1).findElement(By.cssSelector("input[type=submit]")).click();
        waitForElementById("order_name");
        driver.findElement(By.id("order_name")).sendKeys("Test Name");
        driver.findElement(By.id("order_address")).sendKeys("Test Address");
        driver.findElement(By.id("order_email")).sendKeys("Test Email");
        new Select(driver.findElement(By.id("order_pay_type"))).selectByValue("Check");
        driver.findElement(By.id("place_order")).findElement(By.cssSelector("input[type=submit]")).click();
        waitForElementById("order_receipt");
        Assert.assertEquals(1, driver.findElements(By.id("order_receipt")).size());
    }
}
