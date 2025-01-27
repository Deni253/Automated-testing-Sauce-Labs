package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.fail;

public class CartFunctions {
    WebDriver driver;
    public CartFunctions(WebDriver driver) {
        this.driver=driver;
    }

    public Integer addItemToCart(WebDriver driver, String itemId, WebDriverWait wait) {
        Integer click=0;
        try {

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(itemId)));
            addToCartButton.click();
            click++;
            CheckCartCount(driver, click);

        } catch (Exception e) {
            System.out.println("Failed to click on Add to Cart button for: " + itemId + ". Error: " + e.getMessage());
        }
        return click;
    }

    public void removeItemFromCart(WebDriver driver, String itemId) {
        try {
            driver.findElement(By.id(itemId)).click();
            System.out.println("Successfully clicked on Remove button for: " + itemId);
        } catch (Exception e) {
            System.out.println("Failed to remove item: " + itemId + ". Error: " + e.getMessage());
        }
    }

    public boolean CheckCartCount(WebDriver driver, Integer clicks) {
        WebElement cartIcon = driver.findElement(By.id("shopping_cart_container"));
        String cartItemCount = cartIcon.getText();
        if (!clicks.equals(Integer.parseInt(cartItemCount))) {
            return false;
        }
        return true;
    }
}
