package team20.mk.ukim.finki.skit;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import team20.mk.ukim.finki.skit.model.Category;
import team20.mk.ukim.finki.skit.model.Order;
import team20.mk.ukim.finki.skit.model.ShoppingCart;
import team20.mk.ukim.finki.skit.model.User;
import team20.mk.ukim.finki.skit.model.enumerations.Role;
import team20.mk.ukim.finki.skit.service.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {


    @Autowired
    AuthService authService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ItemService itemService;


    @Autowired
    OrderService orderService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    UserService userService;
    User login1;
    User login2;
    ShoppingCart shoppingCart;

    private HtmlUnitDriver driver;
    private static Order m1;
    private static Order m2;
    private static AuthService c1;
    private static AuthService c2;

    private static OrderService orderService1;
    private static AuthService authService1;


    private static User regularUser;
    private static User adminUser;

    private static String user = "user";
    private static String admin = "admin";
    private static boolean dataInitialized = false;

    private void initData() {
        if (!dataInitialized) {
            login1 = authService1.login("login1", "login1");
            login2 = authService1.login("login2", "login2");

            regularUser = userService.register(user, user, user, user, user, Role.ROLE_USER);
            adminUser = userService.register(admin, admin, admin, admin, admin, Role.ROLE_ADMIN);
            dataInitialized = true;

            shoppingCart=new ShoppingCart(adminUser);
            m1= orderService1.createOrder(shoppingCart);

        }
    }
    @BeforeEach
    private void setup() {
        this.driver = new HtmlUnitDriver(true);
        initData();
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }

    @Test
    public void testScenario() throws Exception {
        ItemPage productsPage = ItemPage.to(this.driver);
        productsPage.assertElemts(0, 0, 0, 0, 0);
        LoginPage loginPage = LoginPage.openLogin(this.driver);

        productsPage = LoginPage.doLogin(this.driver, loginPage, adminUser.getUsername(), admin);
        productsPage.assertElemts(0, 0, 0, 0, 1);

        productsPage.getDeleteButtons().get(1).click();
        productsPage.assertElemts(1, 1, 1, 1, 1);

        loginPage = LoginPage.logout(this.driver);
        productsPage = LoginPage.doLogin(this.driver, loginPage, regularUser.getUsername(), user);
        productsPage.assertElemts(1, 0, 0, 1, 0);

        productsPage.getCartButtons().get(0).click();
        Assert.assertEquals("url do not match", "http://localhost:8080/shopping-cart", this.driver.getCurrentUrl());

        ShoppingCartPage cartPage = ShoppingCartPage.init(this.driver);
        cartPage.assertElemts(1);

    }
}