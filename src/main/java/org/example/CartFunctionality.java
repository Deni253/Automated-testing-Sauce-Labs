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

public class CartFunctionality {
    @Test
    public void CartFunctionality() {
        //Zapravo addToCartTest
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
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
                continue;                                                                                              // resetira do while petlju s time da moramo povećat brojać za idućeg usera.
            }

            Integer clicks = 0;
            clicks+=cartFunctions.addItemToCart(driver, "add-to-cart-sauce-labs-backpack", wait);
            clicks+=cartFunctions.addItemToCart(driver, "add-to-cart-sauce-labs-bike-light", wait);
            clicks+=cartFunctions.addItemToCart(driver, "add-to-cart-sauce-labs-bolt-t-shirt", wait);
            clicks+=cartFunctions.addItemToCart(driver, "add-to-cart-sauce-labs-fleece-jacket", wait);
            clicks+=cartFunctions.addItemToCart(driver, "add-to-cart-sauce-labs-onesie", wait);
            clicks+=cartFunctions.addItemToCart(driver, "add-to-cart-test.allthethings()-t-shirt-(red)", wait);

            // Log the cart count after adding items
            if(!cartFunctions.CheckCartCount(driver, clicks)){
                System.out.println("Failed to add to cart on user: "+ usernames.get(items).toString());
                failureReport.append("\nUser failed to add items to cart: " + usernames.get(items).toString()+".");
                flag=1;
            }


            // Continue shopping and log out
            items++;
            driver.findElement(By.id("react-burger-menu-btn")).click();
            WebElement reset = wait.until(ExpectedConditions.elementToBeClickable(By.id("reset_sidebar_link")));
            reset.click();
            driver.navigate().refresh();
            driver.findElement(By.id("react-burger-menu-btn")).click();
            WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
            logoutLink.click();

        }while(items < usernames.size());
        driver.close();
        if(flag >0){ fail("Not all users were able to add items to the cart: \n" + failureReport.toString()); }

    }
}