package io.appium.workshop.unit01;

import static org.junit.Assert.assertTrue;
import io.appium.java_client.android.AndroidDriver;

import java.net.URL;

import io.appium.workshop.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ConnectingToAppium {

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
    public void pageSourceIsNotEmpty() throws Exception {
        assertTrue("App loaded",driver.getPageSource().length() > 0);
    }

}
