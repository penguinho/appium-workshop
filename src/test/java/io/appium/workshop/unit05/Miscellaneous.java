package io.appium.workshop.unit05;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.workshop.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.nio.ch.Net;

import java.net.URL;

import static org.junit.Assert.assertTrue;

public class Miscellaneous {

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
    public void testMisc() throws Exception {

        // open the menu
        AndroidElement hamburgerButton = (AndroidElement) driver.findElementByXPath("//android.widget.ImageButton[@content-desc='ReferenceApp']");
        hamburgerButton.click();
        Thread.sleep(1000); // wait for animation

        // select input controls menu item
        AndroidElement menuItem = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[@text='Fixtures']");
        menuItem.click();

        // change the location of the device
        driver.setLocation(new Location(42.50,21.50,0));
        AndroidElement longitudeDisplay = (AndroidElement)driver.findElementById("com.amazonaws.devicefarm.android.referenceapp:id/longitude");
        AndroidElement latitudeDisplay = (AndroidElement)driver.findElementById("com.amazonaws.devicefarm.android.referenceapp:id/lat");
        Thread.sleep(1000); // wait for change to take effect
        assertTrue(latitudeDisplay.getText().startsWith("42."));
        assertTrue(longitudeDisplay.getText().startsWith("21."));

    }
}
