import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

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
}
