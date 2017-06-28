package io.appium.workshop.unit04;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.workshop.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class WebViewContent {

    private AndroidDriver<WebElement> driver;

    @Before
    public void setUp() throws Exception {
        // set up workshop
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","android");
        capabilities.setCapability("platformVersion", "7.1");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("app", Config.AppPath);
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void pageSourceDiffers() throws Exception {

        // open the menu
        AndroidElement hamburgerButton = (AndroidElement)driver.findElementByXPath("//android.widget.ImageButton[@content-desc='ReferenceApp']");
        hamburgerButton.click();
        Thread.sleep(1000); // wait for animation

        // select local web view item
        AndroidElement menuItem = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[@text=\"Local Web View\"]");
        menuItem.click();

        // grab the initial page source
        String initialPageSource = driver.getPageSource();

        // wait for the web page to load
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until((WebDriver driver) -> ((AndroidDriver)driver).getContextHandles().toArray().length > 1);

        // change the context
        driver.context("WEBVIEW_com.amazonaws.devicefarm.android.referenceapp");
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("html")));

        // grab the html page source
        String newPageSource = driver.getPageSource();
        assertNotEquals(initialPageSource, newPageSource);
    }

}
