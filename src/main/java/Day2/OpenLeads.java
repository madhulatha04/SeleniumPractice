package Day2;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OpenLeads {

    public static void main(String[] args) {
        WebDriver dr = new ChromeDriver();
        dr.manage().window().maximize();
        dr.get("https://login.salesforce.com/");

        try {
            WebDriverWait wait = new WebDriverWait(dr, Duration.ofSeconds(25));

            // Login
            WebElement username = dr.findElement(By.id("username"));
//            username.sendKeys("madhulathagaddam@learningcurve.com");  // Replace with actual username
            username.sendKeys("madhulathagaddam@ksq.com");  // Replace with actual username

            WebElement password = dr.findElement(By.id("password"));
//            password.sendKeys("R!$!ng25");  // Replace with actual password
            password.sendKeys("!nc0rrectP@$$");  // Replace with actual password

            WebElement loginButton = dr.findElement(By.id("Login"));
            loginButton.click();
            System.out.println("Clicked Login");

            // Handle verification if prompted
            try {
                WebElement verifyField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emc")));
                System.out.println("Verification code required. Please complete manually...");
                Thread.sleep(30000);
            } catch (TimeoutException e) {
                System.out.println("No verification prompt detected, continuing...");
            }

            // Wait until login completes
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.titleContains("Home"),
                    ExpectedConditions.titleContains("Lightning")));
            System.out.println("Login successful");

            // Open App Launcher
            WebElement appLauncher = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='slds-icon-waffle']")));
            ((JavascriptExecutor) dr).executeScript("arguments[0].click();", appLauncher);
            System.out.println("Clicked App Launcher");

            // Click 'View All'
            WebElement viewAll = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='View All']")));
            ((JavascriptExecutor) dr).executeScript("arguments[0].click();", viewAll);
            System.out.println("Clicked 'View All'");

            Thread.sleep(20000);

            // ---------- OPEN 'SALES' APP ----------
            WebElement salesApp = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//p[normalize-space()='Sales']")));

            // The modal is hidden with CSS, not removed, so skip invisibility wait
            Thread.sleep(2000); // small delay for animation

            ((JavascriptExecutor) dr).executeScript("arguments[0].click();", salesApp);
            System.out.println("Opened 'Sales' App");

            Thread.sleep(2000);

            // Click on 'Leads' Tab
            WebElement leadsTab = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//one-appnav//a[@title='Leads']")));
            ((JavascriptExecutor) dr).executeScript("arguments[0].click();", leadsTab);
            System.out.println("Navigated to Leads tab");

            // Create a new Lead
            WebElement newButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@name='New']")));
            //((JavascriptExecutor) dr).executeScript("arguments[0].click();", newButton);
            newButton.click();
            System.out.println("Clicked 'New' button");

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(text(),'New Lead')]")));

            dr.findElement(By.xpath("//input[@name='firstName']")).sendKeys("John");
            dr.findElement(By.xpath("//input[@name='lastName']")).sendKeys("Doe");
            dr.findElement(By.xpath("//input[@name='Company']")).sendKeys("Acme Inc");
            dr.findElement(By.xpath("//input[@name='Email']")).sendKeys("john.doe@example.com");

            WebElement saveButton = dr.findElement(By.xpath("//button[@name='SaveEdit']"));
            ((JavascriptExecutor) dr).executeScript("arguments[0].click();", saveButton);
            System.out.println("Lead created successfully");

        } catch (Exception e) {
            System.out.println("Testcase failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ignored) {}
            dr.quit();
            System.out.println("Browser closed.");
        }
    }
}
