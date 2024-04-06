import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestHelper {

    static WebDriver driver;
    final int waitForResposeTime = 4;
	
	// here write a link to your admin website (e.g. http://my-app.herokuapp.com/admin)
    String baseUrlAdmin = "http://127.0.0.1:3000/admin";
	
	// here write a link to your website (e.g. http://my-app.herokuapp.com/)
    String baseUrl = "http://127.0.0.1:3000/";

    @Before
    public void setUp(){

        // if you use Chrome:
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\thomaspa\\Desktop\\Personal\\Testimine\\chromedriver.exe");
        driver = new ChromeDriver();

        // if you use Firefox:
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\...\\geckodriver.exe");
        //driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);

    }

    void goToPage(String page){
        WebElement elem = driver.findElement(By.linkText(page));
        elem.click();
        waitForElementById(page);
    }

    void waitForElementById(String id){
        waitFor(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isLoggedIn() {
        driver.get(baseUrlAdmin);

        return isElementPresent(By.linkText("Logout"));

    }

    void login(String username, String password){

        driver.get(baseUrlAdmin);

        driver.findElement(By.linkText("Login")).click();

        waitFor(ExpectedConditions.presenceOfElementLocated(By.id("name")));
        driver.findElement(By.id("name")).sendKeys(username);

        driver.findElement(By.id("password")).sendKeys(password);

        By loginButton = By.name("commit");
        driver.findElement(loginButton).click();

    }

    void logout(){
        WebElement logout = driver.findElement(By.linkText("Logout"));
        logout.click();

        waitForElementById("Admin");
    }

    void create(String username, String password) {
        if(isLoggedIn()) logout();
        driver.get(baseUrlAdmin);
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("user_name")).sendKeys(username);
        driver.findElement(By.id("user_password")).sendKeys(password);
        driver.findElement(By.id("user_password_confirmation")).sendKeys(password);

        driver.findElement(By.id("register_button")).findElement(By.name("commit")).click();

        if(isElementPresent(By.id("error_explanation"))) {
            delete(username, password);
            create(username, password);
        } else {
            logout();
        }
    }

    void delete(String adminUsername, String adminPassword) {
        if(!isLoggedIn()) login(adminUsername, adminPassword);
        driver.get(baseUrlAdmin);
        waitFor(ExpectedConditions.elementToBeClickable(
                driver.findElement(By.id(adminUsername)).findElement(By.linkText("Delete"))
        ));
        driver.findElement(By.id(adminUsername)).findElement(By.linkText("Delete")).click();

        waitFor(ExpectedConditions.presenceOfElementLocated(By.id("notice")));
        WebElement notice = driver.findElement(By.id("notice"));
        Assert.assertEquals("User was successfully deleted.", notice.getText().trim());
    }

    void waitFor(ExpectedCondition<WebElement> expectedConditions) {
        new WebDriverWait(driver, waitForResposeTime).ignoring(StaleElementReferenceException.class)
                .until(expectedConditions);
    }

    @After
    public void tearDown(){
        driver.close();
    }

}