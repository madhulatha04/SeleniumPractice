package Day1;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FirstTestcase {

    public static void main(String[] args) {
        WebDriver dr = new ChromeDriver();
        dr.manage().window().maximize();
        dr.get("https://login.salesforce.com/");

        try {
            // ---------- LOGIN ----------
            String atitle = dr.getTitle();
            if (atitle != null && (atitle.equals("Login | Salesforce") || atitle.equals("Iniciar sesión | Salesforce"))) {
                System.out.println("Salesforce login page title verified: Testcase Passed");
            } else {
                System.out.println("Salesforce login page title mismatch: Testcase Failed");
            }

            WebElement userNameField = dr.findElement(By.id("username"));
            userNameField.sendKeys("your-username");

            WebElement passwordField = dr.findElement(By.id("password"));
            passwordField.sendKeys("your-password");

            WebElement loginButton = dr.findElement(By.id("Login"));
            loginButton.click();

            WebDriverWait wait = new WebDriverWait(dr, Duration.ofSeconds(20));

            // ---------- HANDLE VERIFICATION (if appears) ----------
            try {
                WebElement verifyField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emc")));
                System.out.println("Verification code required. Please enter it manually...");
                Thread.sleep(30000); // give user time to complete verification
            } catch (TimeoutException e) {
                System.out.println("No verification prompt detected, continuing...");
            }

            // ---------- LOGIN SUCCESS VALIDATION ----------
            String loginSuccessTitle = dr.getTitle();
            if (loginSuccessTitle.contains("Home") || loginSuccessTitle.contains("Lightning")) {
                System.out.println("Login Successful: " + loginSuccessTitle);
            } else {
                System.out.println("Login may have failed, current page title: " + loginSuccessTitle);
            }

            // ---------- NAVIGATE VIA APP LAUNCHER ----------
            WebElement appLauncher = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='slds-icon-waffle']")));
            appLauncher.click();
            System.out.println("Clicked App Launcher");

            WebElement viewAll = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='View All']")));
            viewAll.click();
            System.out.println("Clicked 'View All'");

            Thread.sleep(3000); // give modal animation time

            // ---------- SEARCH 'LEADS' ----------
//            WebElement searchItems = null;
//            try {
//                // Try normal DOM first
//                searchItems = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath("//input[contains(@placeholder, 'Search apps')]")));
//                System.out.println("Found search box (normal)");
//            } catch (TimeoutException e) {
//                // Fallback if inside shadow DOM
//                JavascriptExecutor js = (JavascriptExecutor) dr;
//                searchItems = (WebElement) js.executeScript(
//                        "return document.querySelector('one-app-launcher-modal')?.shadowRoot.querySelector('input[placeholder*=Search]')");
//                if (searchItems == null) {
//                    throw new NoSuchElementException("Unable to locate search box in App Launcher");
//                }
//                System.out.println("Found search box (via shadow DOM)");
//            }
////
////            WebElement searchItems = wait.until(ExpectedConditions.visibilityOfElementLocated(
////                    By.xpath("//input[contains(@placeholder, 'Search apps')]")));
//
//
//            // Type 'Leads'
//            searchItems.sendKeys("Leads");
//            System.out.println("Typed 'Leads' in search box");
//            Thread.sleep(60000); // Allow Salesforce time to display the search results
//
//            By leadsLocator = By.xpath("//mark[normalize-space()='Leads'] | //p[normalize-space()='Leads']");
//
//            WebElement leadsOption = null;
//            try {
//                leadsOption = wait.until(ExpectedConditions.elementToBeClickable(leadsLocator));
//                System.out.println("Found 'Leads' option, clicking normally...");
//                leadsOption.click();
//            } catch (TimeoutException e) {
//                System.out.println("Normal click failed, retrying with JavaScript...");
//                JavascriptExecutor js = (JavascriptExecutor) dr;
//                leadsOption = (WebElement) js.executeScript(
//                        "return document.querySelector('one-app-launcher-modal')?.shadowRoot.querySelector('mark, p')");
//                if (leadsOption != null) {
//                    js.executeScript("arguments[0].click();", leadsOption);
//                    System.out.println("Clicked 'Leads' via JavaScript");
//                } else {
//                    throw new NoSuchElementException("Unable to locate 'Leads' in App Launcher.");
//                }
//            }
//
//            System.out.println("✅ Opened Leads tab successfully");
            WebElement searchItems = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[contains(@placeholder, 'Search apps')]")));
            searchItems.sendKeys("Leads");
            System.out.println("Typed 'Leads' in search box");

            Thread.sleep(2000); // allow results to appear

// Click on the "Leads" option
            WebElement leadsOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//mark[normalize-space()='Leads'] | //p[normalize-space()='Leads']")));
            ((JavascriptExecutor) dr).executeScript("arguments[0].click();", leadsOption);
            System.out.println("✅ Opened Leads tab successfully");

        } catch (Exception e) {
            System.out.println("❌ Testcase Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {
            }
            dr.quit();
        }
    }
}
