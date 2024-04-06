import org.junit.Assert;
import org.junit.Test;

public class AdminTest extends TestHelper {


    String user = "randomuser3";
    String pass = "randompass3";
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

}
