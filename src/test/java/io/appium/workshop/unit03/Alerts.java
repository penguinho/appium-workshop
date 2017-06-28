package io.appium.workshop.unit03;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.workshop.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class Alerts {

    private AndroidDriver<WebElement> driver;

    @Before
    public void setUp() throws Exception {
        // set up workshop
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "android");
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
    public void testAlerts() throws Exception {

        // open the menu
        AndroidElement hamburgerButton = (AndroidElement) driver.findElementByXPath("//android.widget.ImageButton[@content-desc='ReferenceApp']");
        hamburgerButton.click();
        Thread.sleep(1000); // wait for animation

        // select input controls menu item
        AndroidElement menuItem = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[@text='Alerts']");
        menuItem.click();

        // trigger alert
        AndroidElement triggerAlertButton = (AndroidElement) driver.findElementById("com.amazonaws.devicefarm.android.referenceapp:id/notifications_alert_button");
        triggerAlertButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("android:id/alertTitle")));

        String alertTitle = driver.findElementById("android:id/alertTitle").getText();
        String alertMessage = driver.findElementById("android:id/message").getText();
        assertEquals(alertTitle, "Alert Title");
        assertEquals(alertMessage, "This is the alert message");

        // dismiss alert
        AndroidElement acceptAlertButton = (AndroidElement) driver.findElementById("android:id/button1");
        acceptAlertButton.click();
    }
}
