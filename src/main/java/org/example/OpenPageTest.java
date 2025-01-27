package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.*;
//ZAPRAVO LOGIN TEST
public class OpenPageTest {
    @Test
    public void Login() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Login login = new Login(driver);
        driver.get("https://www.saucedemo.com/"); //dolazimo do url
        driver.manage().window().maximize();

        List<String> usernames = new ArrayList<String>();

        usernames.add("standard_user");
        usernames.add("locked_out_user");
        usernames.add("problem_user");
        usernames.add("performance_glitch_user");
        usernames.add("error_user");
        usernames.add("visual_user");


        int i=0; //nezz jel to najbolji način al provjerit ćemo
        do{
                login.enter_username(usernames.get(i));
                login.enter_password("secret_sauce");
                driver.findElement(By.id("login-button")).click();
                if(driver.findElements(By.cssSelector(".error-message-container")).size() > 0){ // size radi za razliku od isDisplayed()-Findelement presretne isDisplayed()
                    System.out.println("Login failed at " + usernames.get(i).toUpperCase());
                    //probao isVisible, size > 0 nije radilo
                    // bili su clear umjesto refresha
                    driver.navigate().refresh();
                    i++;
                    continue;                                                                                              // resetira do while petlju s time da moramo povećat brojać za idućeg usera.
                }
                driver.findElement(By.id("react-burger-menu-btn")).click();
                WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
                logoutLink.click();
            i++;
        }while(i < usernames.size());

    }
}