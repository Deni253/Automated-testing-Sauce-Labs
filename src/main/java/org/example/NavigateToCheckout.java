package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.awt.SystemColor.window;
import static org.testng.Assert.fail;

public class NavigateToCheckout {
    @Test
    public void NavigateToCheckout() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Login login = new Login(driver);
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        Integer items = 0;
        Integer flag=0;
        StringBuilder report = new StringBuilder();
        List<String> usernames = new ArrayList<String>();

        usernames.add("standard_user");
        //usernames.add("locked_out_user");
        usernames.add("problem_user");
        usernames.add("performance_glitch_user");
        usernames.add("error_user");
        usernames.add("visual_user");

            do{
                    login.enter_username(usernames.get(items));
                    login.enter_password("secret_sauce");
                    driver.findElement(By.id("login-button")).click();

                    LogEntries logs = driver.manage().logs().get(LogType.BROWSER);

                    // RJEŠENJE BLANK SCREENA

                    for (LogEntry entry : logs) {
                        System.out.println(entry.getMessage());

                        // provjerava console log na chrome

                        if (entry.getMessage().contains("Failed")) {
                            flag=1;
                            report.append("Fetch request failed: " + entry.getMessage()+usernames.get(items));
                            driver.get("https://www.saucedemo.com/");
                        }

                    }

                    if (driver.findElements(By.cssSelector(".error-message-container")).size() > 0) {
                        driver.navigate().refresh();
                        items++;
                        continue;
                    }

                    driver.findElement(By.linkText("Sauce Labs Fleece Jacket")).click();
                    driver.findElement(By.id("add-to-cart")).click();
                    driver.findElement(By.id("shopping_cart_container")).click();

                    driver.findElement(By.id("checkout")).click();
                    driver.findElement(By.id("first-name")).sendKeys("Ivan");
                    driver.findElement(By.id("last-name")).sendKeys("Ivanković");
                    driver.findElement(By.id("postal-code")).sendKeys("31111");
                    driver.findElement(By.id("continue")).click();
                    driver.findElement(By.id("finish")).click();
                    driver.findElement(By.id("back-to-products")).click();
                    items++;
                    driver.findElement(By.id("react-burger-menu-btn")).click();
                    WebElement reset = wait.until(ExpectedConditions.elementToBeClickable(By.id("reset_sidebar_link")));
                    reset.click();
                    driver.navigate().refresh();
                    driver.findElement(By.id("react-burger-menu-btn")).click();
                    WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
                    logoutLink.click();

            }while(items<usernames.size());

        if(flag==1){fail("Users could not navigate to checkout page therefore cannot process payment: "+ report.toString());}
    }
}
