package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Login {
    WebDriver driver;
    public Login(WebDriver driver) {
        this.driver=driver;
    }
    void enter_username(String username) {
        driver.findElement(By.id("user-name")).sendKeys(username);
    }
    void enter_password(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }
}
