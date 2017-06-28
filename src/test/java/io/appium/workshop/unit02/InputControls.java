package io.appium.workshop.unit02;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.workshop.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class InputControls {

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
    public void testUIComponents() throws Exception {

        // open the menu
        AndroidElement hamburgerButton = (AndroidElement)driver.findElementByXPath("//android.widget.ImageButton[@content-desc='ReferenceApp']");
        hamburgerButton.click();
        Thread.sleep(1000); // wait for animation

        // select input controls menu item
        AndroidElement menuItem = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[@text='Input Controls']");
        menuItem.click();

        // use keyboard
        AndroidElement textfield = (AndroidElement) driver.findElementByAccessibilityId("Text Input Control");
        textfield.sendKeys("abc123");
        String textfieldContent = textfield.getText();
        assertEquals(textfieldContent,"abc123");

        // select next menu item
        AndroidElement secondVisibleTabStripItem = (AndroidElement)driver.findElementByXPath("//android.view.ViewGroup[@resource-id='com.amazonaws.devicefarm.android.referenceapp:id/pager_tabstrip']/android.widget.TextView[2]");
        secondVisibleTabStripItem.click();
        Thread.sleep(500); // wait for animation

        // check the checkbox
        assertEquals(driver.findElementsByXPath("//android.widget.CheckBox[@checked='false']").size(), 1);
        assertEquals(driver.findElementsByXPath("//android.widget.CheckBox[@checked='true']").size(), 0);
        AndroidElement checkbox = (AndroidElement)driver.findElementByAccessibilityId("Checkbox Control");
        checkbox.click();
        assertEquals(driver.findElementsByXPath("//android.widget.CheckBox[@checked='false']").size(), 0);
        assertEquals(driver.findElementsByXPath("//android.widget.CheckBox[@checked='true']").size(), 1);

        // select next menu item
        AndroidElement thirdVisibleTabStripItem = (AndroidElement)driver.findElementByXPath("//android.view.ViewGroup[@resource-id='com.amazonaws.devicefarm.android.referenceapp:id/pager_tabstrip']/android.widget.TextView[3]");
        thirdVisibleTabStripItem.click();
        Thread.sleep(500); // wait for animation

        // verify initial radio button state
        assertEquals(driver.findElementsByXPath("//android.widget.RadioButton[@content-desc='Radio Button 1' and @checked='true']").size(), 1);
        assertEquals(driver.findElementsByXPath("//android.widget.RadioButton[@content-desc='Radio Button 2' and @checked='false']").size(), 1);
        assertEquals(driver.findElementsByXPath("//android.widget.RadioButton[@content-desc='Radio Button 3' and @checked='false']").size(), 1);

        // select radio button 2
        AndroidElement radioButton2 = (AndroidElement)driver.findElementByXPath("//android.widget.RadioButton[@content-desc='Radio Button 2']");
        radioButton2.click();
        assertEquals(driver.findElementsByXPath("//android.widget.RadioButton[@content-desc='Radio Button 1' and @checked='false']").size(), 1);
        assertEquals(driver.findElementsByXPath("//android.widget.RadioButton[@content-desc='Radio Button 2' and @checked='true']").size(), 1);
        assertEquals(driver.findElementsByXPath("//android.widget.RadioButton[@content-desc='Radio Button 3' and @checked='false']").size(), 1);

        // select radio button 3
        AndroidElement radioButton3 = (AndroidElement)driver.findElementByXPath("//android.widget.RadioButton[@content-desc='Radio Button 3']");
        radioButton3.click();
        assertEquals(driver.findElementsByXPath("//android.widget.RadioButton[@content-desc='Radio Button 1' and @checked='false']").size(), 1);
        assertEquals(driver.findElementsByXPath("//android.widget.RadioButton[@content-desc='Radio Button 2' and @checked='false']").size(), 1);
        assertEquals(driver.findElementsByXPath("//android.widget.RadioButton[@content-desc='Radio Button 3' and @checked='true']").size(), 1);

        // select the next menu item
        AndroidElement firstVisibleTabStripItem = (AndroidElement)driver.findElementByXPath("//android.view.ViewGroup[@resource-id='com.amazonaws.devicefarm.android.referenceapp:id/pager_tabstrip']/android.widget.TextView[1]");
        new TouchAction(driver).press(thirdVisibleTabStripItem).moveTo(firstVisibleTabStripItem).release().perform();
        //Thread.sleep(500); // wait for animation
        new TouchAction(driver).press(thirdVisibleTabStripItem).moveTo(firstVisibleTabStripItem).release().perform();
        //Thread.sleep(500); // wait for animation
        new TouchAction(driver).press(thirdVisibleTabStripItem).moveTo(firstVisibleTabStripItem).release().perform();
        //Thread.sleep(500); // wait for animation

        // check display
        AndroidElement display = (AndroidElement)driver.findElementByAccessibilityId("Refresh Display");
        assertEquals(display.getText(), "Pull to refresh time");

        // pull to refresh
        Dimension size = driver.manage().window().getSize();
        int startY = (size.height * 2) / 10;
        int endY = (size.height * 9) / 10;
        int startX = size.width / 2;
        int endX = startX;
        int timeInMs = 200;
        driver.swipe(startX, startY, endX, endY,timeInMs);
        assertNotEquals(display.getText(), "Pull to refresh time");
    }

}
