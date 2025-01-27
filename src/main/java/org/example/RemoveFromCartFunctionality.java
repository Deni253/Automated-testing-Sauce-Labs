package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.fail;

public class RemoveFromCartFunctionality {
    @Test
    public void RemoveFromCartFunctionality() {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        CartFunctions cartFunctions = new CartFunctions(driver);
        Login login = new Login(driver);

        Integer items = 0;
        Integer flag=0;

        StringBuilder failureReport = new StringBuilder();
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();

        List<String> usernames = new ArrayList<String>();

        usernames.add("standard_user");
        usernames.add("locked_out_user");
        usernames.add("problem_user");
        usernames.add("performance_glitch_user");
        usernames.add("error_user");
        usernames.add("visual_user");

        do{
            login.enter_username(usernames.get(items));
            login.enter_password("secret_sauce");

            driver.findElement(By.id("login-button")).click();

            if(driver.findElements(By.cssSelector(".error-message-container")).size() > 0){
                driver.navigate().refresh();
                items++;
                continue;
            }

            cartFunctions.addItemToCart(driver, "add-to-cart-sauce-labs-backpack", wait);
            cartFunctions.addItemToCart(driver, "add-to-cart-sauce-labs-bike-light", wait);
            cartFunctions.addItemToCart(driver, "add-to-cart-sauce-labs-bolt-t-shirt", wait);
            cartFunctions.addItemToCart(driver, "add-to-cart-sauce-labs-fleece-jacket", wait);
            cartFunctions.addItemToCart(driver, "add-to-cart-sauce-labs-onesie", wait);
            cartFunctions.addItemToCart(driver, "add-to-cart-test.allthethings()-t-shirt-(red)", wait);

            driver.findElement(By.id("shopping_cart_container")).click();

            cartFunctions.removeItemFromCart(driver, "remove-sauce-labs-backpack");
            cartFunctions.removeItemFromCart(driver, "remove-sauce-labs-bike-light");
            cartFunctions.removeItemFromCart(driver, "remove-sauce-labs-bolt-t-shirt");
            cartFunctions.removeItemFromCart(driver, "remove-sauce-labs-fleece-jacket");
            cartFunctions.removeItemFromCart(driver, "remove-sauce-labs-onesie");
            cartFunctions.removeItemFromCart(driver, "remove-test.allthethings()-t-shirt-(red)");

            List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_item"));
            System.out.println("Number of cart items found: " + cartItems.size());

            if(driver.findElements(By.cssSelector(".cart_item")).size()>0){
                flag=1;
                System.out.println("item failed to remove");
                failureReport.append("User that failed to remove items from cart\n"+ usernames.get(items));
                driver.findElement(By.id("react-burger-menu-btn")).click();
                WebElement reset = wait.until(ExpectedConditions.elementToBeClickable(By.id("reset_sidebar_link")));
                reset.click();
                driver.navigate().refresh();
                driver.findElement(By.id("react-burger-menu-btn")).click();
                WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
                logoutLink.click();
                items++;
                continue;
            }

            items++;
            driver.findElement(By.id("react-burger-menu-btn")).click();
            WebElement reset = wait.until(ExpectedConditions.elementToBeClickable(By.id("reset_sidebar_link")));
            reset.click();
            driver.navigate().refresh();
            driver.findElement(By.id("react-burger-menu-btn")).click();
            WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
            logoutLink.click();

        }while(items < usernames.size());

        if(flag > 0){ fail("Not all users were able to remove items to the cart: \n" + failureReport.toString()); }
    }
}
