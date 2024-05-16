package com.qa.demo.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class HomePage {
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(600); // Adjust timeout duration as needed
    public static Properties prop;
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    static {
        try {

            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\demo\\config\\config.properties");
            prop.load(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JavascriptExecutor js;
    @FindBy(id = "ParkingLot")
    private WebElement parkingLotDropdown;
    @FindBy(id = "StartingDate")
    private WebElement startingDateInput;
    @FindBy(id = "StartingTime")
    private WebElement startingTimeInput;
    @FindBy(xpath = "//input[@name='StartingTimeAMPM'][@value='AM']")
    private WebElement startingAMRadio;
    @FindBy(xpath = "//input[@name='StartingTimeAMPM'][@value='PM']")
    private WebElement startingPMRadio;
    @FindBy(id = "LeavingDate")
    private WebElement leavingDateInput;
    @FindBy(id = "LeavingTime")
    private WebElement leavingTimeInput;
    @FindBy(xpath = "//input[@name='LeavingTimeAMPM'][@value='AM']")
    private WebElement leavingAMRadio;
    @FindBy(xpath = "//input[@name='LeavingTimeAMPM'][@value='PM']")
    private WebElement leavingPMRadio;
    @FindBy(name = "Submit")
    private WebElement submitButton;
    @FindBy(xpath = "//span[@class='SubHead']/b")
    private WebElement totalCost;

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void WaitUntilItsClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TIMEOUT_DURATION);
        wait.until(ExpectedConditions.visibilityOf(element));

        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("arguments[0].click()", element);
    }

    public void initialization() throws InterruptedException {
        String BrowserName = prop.getProperty("browser");
        WebDriver driver = null;
        switch (BrowserName) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/com/qa/demo/data/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "chromeheadless":
                ChromeOptions chromeOptions = new ChromeOptions();
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/com/qa/demo/data/chromedriver.exe");
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920x1080");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/main/java/com/qa/demo/data/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "firefoxheadless":
                FirefoxOptions fireFoxOptions = new FirefoxOptions();
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/main/java/com/qa/demo/data/geckodriver.exe");
                fireFoxOptions.addArguments("--headless");
                fireFoxOptions.addArguments("--disable-gpu");
                fireFoxOptions.addArguments("--window-size=1920x1080");
                driver = new FirefoxDriver(fireFoxOptions);
                break;
            default:
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/com/qa/demo/data/chromedriver.exe");
                driver = new ChromeDriver();
        }
        driverThreadLocal.set(driver);
        PageFactory.initElements(driver, this);  // Initialize elements using PageFactory

        driver.get(prop.getProperty("url"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='" + 0.9 + "'");
        Thread.sleep(3000);

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();


    }

    public void openParkingCostCalculatorHomePage() throws InterruptedException {
        initialization();
    }

    public void selectParkingLot(String lot) {
        Select parkingLot = new Select(parkingLotDropdown);
        parkingLot.selectByVisibleText(lot);
    }

    public void setStartingDateTime(String date, String time, String ampm) {
        startingDateInput.clear();
        startingDateInput.sendKeys(date);
        startingTimeInput.clear();
        startingTimeInput.sendKeys(time);
        if (ampm.equalsIgnoreCase("AM")) {
            WaitUntilItsClickable(startingAMRadio);
        } else {
            WaitUntilItsClickable(startingPMRadio);
        }
    }

    public void setLeavingDateTime(String date, String time, String ampm) {
        leavingDateInput.clear();
        leavingDateInput.sendKeys(date);
        leavingTimeInput.clear();
        leavingTimeInput.sendKeys(time);
        if (ampm.equalsIgnoreCase("AM")) {
            WaitUntilItsClickable(leavingAMRadio);
        } else {
            WaitUntilItsClickable(leavingPMRadio);
        }
    }

    public void calculateCost() {
        WaitUntilItsClickable(submitButton);
    }

    public void closeBrowser() {
        driverThreadLocal.get().close();
    }

    public void testParkingCostCalculation(String ExepectedTotalCost) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(totalCost));

        String actualTotalCost = totalCost.getText().trim();
        assertTrue(actualTotalCost.contains(ExepectedTotalCost));
    }
}
