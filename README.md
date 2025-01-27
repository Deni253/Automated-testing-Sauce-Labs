# Automated-testing-Sauce-Labs
Automated tests for regular functionalities of a simple website

## Features
-Testing logging in and logging out for each user.
-Testing navigation to cart for each user.
-Testing adding and removing(default cart functionalities) for each user.
-Testing checkout for each user.

## Short description of functionalities
- OpenPageTest class uses methods from Login class.
- Users are added to a list and are iterated over with a do while-loop and a global variable. For each user this tests log in and log out functionalities after which the page refreshes and repeats for each user from the list.
- NavigateToCheckout uses the same approach with added checks to the Log Entry. It checks if a string contains a word "Failed" i case fetching from the database is not successful and does that for every user in the list.
- CartFunctionality tests adding items to the the cart. Uses the same approach as before with added click counter on Add buttons to check wether all clicked items are added to the cart for each user.
- RemoveFromCartFunctionality tests removing items from the cart with a similar approach.
- Both CartFunctionality and RemoveFromCartFunctionality use methods from class CartFunctions.
- Most if not all classes use WebDriver wait command to wait for certain animations for example opening the hamburger menu and inbetween adding and removing items from cart because the tests sometimes failed explicitly due to high speed of execution.
- Every class uses a flag (the flag is set to 1 to implicate fail conditions) combined with a fail method from Assert and a StringBuilder object to generate a detailed explanation in Maven surefire reports.

## Technologies
- IntelliJ framework
- Maven
- Java
- TestNg
- Selenium

## Website Tested
- https://www.saucedemo.com/

