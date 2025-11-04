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
            // ---------- LOGIN ----------
            WebDriverWait wait = new WebDriverWait(dr, Duration.ofSeconds(20));
            String atitle = dr.getTitle();

            if (atitle != null && (atitle.equals("Login | Salesforce") || atitle.equals("Iniciar sesión | Salesforce"))) {
                System.out.println("Salesforce login page title verified: Testcase Passed");
            } else {
                System.out.println("Salesforce login page title mismatch: Testcase Failed");
            }

            WebElement userNameField = dr.findElement(By.id("username"));
            userNameField.sendKeys("madhulathagaddam@ksq.com"); //  replace with your username

            WebElement passwordField = dr.findElement(By.id("password"));
            passwordField.sendKeys("!nc0rrectP@$$"); //  replace with your password

            WebElement loginButton = dr.findElement(By.id("Login"));
            loginButton.click();

            // ---------- HANDLE VERIFICATION (if appears) ----------
            try {
                WebElement verifyField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emc")));
                System.out.println("Verification code required. Please enter it manually...");
                Thread.sleep(30000); // wait for user input
            } catch (TimeoutException e) {
                System.out.println("No verification prompt detected, continuing...");
            }

            // ---------- LOGIN SUCCESS VALIDATION ----------
            String loginSuccessTitle = dr.getTitle();
            if (loginSuccessTitle.contains("Home") || loginSuccessTitle.contains("Lightning")) {
                System.out.println("Login Successful: " + loginSuccessTitle);
            } else {
                System.out.println("Login may have failed. Page title: " + loginSuccessTitle);
            }

            // ---------- OPEN APP LAUNCHER ----------
            WebElement appLauncher = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='slds-icon-waffle']")));
            appLauncher.click();
            System.out.println("Clicked App Launcher");

            // ---------- CLICK 'VIEW ALL' ----------
            WebElement viewAll = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='View All']")));
            viewAll.click();
            System.out.println("Clicked 'View All'");

            Thread.sleep(20000); // wait for modal animation

            // ---------- OPEN 'SALES' APP ----------
            WebElement salesApp = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//p[normalize-space()='Sales']")));
            salesApp.click();
            System.out.println("Opened 'Sales' App");

            Thread.sleep(20000); // wait for Sales app to load

            // ---------- CLICK ON 'LEADS' TAB ----------
            WebElement leadsTab = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//one-appnav//a[@title='Leads']")));
            leadsTab.click();
            System.out.println("Navigated to Leads tab successfully");

        } catch (Exception e) {
            System.out.println("Testcase Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {}
            dr.quit();
            System.out.println("Browser closed.");
        }
    }
}
