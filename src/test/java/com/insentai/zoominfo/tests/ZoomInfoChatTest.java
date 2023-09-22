package com.insentai.zoominfo.tests;

import com.insentai.zoominfo.pages.ChatPage;
import com.insentai.zoominfo.utils.RetryAnalyzer;
import com.insentai.zoominfo.utils.WaitUtils;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ZoomInfoChatTest {

    private WebDriver driver;
    private ChatPage chatPage;
    private WaitUtils waitUtils;
    Properties properties;

    @BeforeTest
    public void loadProperties() {
        // Load the configuration file only once for the entire test suite
        properties = new Properties();
        try {
            FileInputStream configFile = new FileInputStream("src/test/resources/config.properties");
            properties.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to set up the test environment before executing the tests.
     * It initializes the WebDriver, configures Chrome options, maximizes the browser window,
     * sets implicit timeouts, navigates to the test website, accepts cookies, and switches to the chat bot iframe.
     */
    @Step("Setup method")
    @BeforeMethod
    public void setUp() {

        // Initialize the WebDriver based on the specified browser from the config file
        initializeWebDriver(properties.getProperty("browser"));

        // Maximize the browser window for better visibility
        driver.manage().window().maximize();

        // Set implicit wait timeouts to 10 seconds
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Initialize the ChatPage and WaitUtils objects with the WebDriver instance
        chatPage = new ChatPage(driver);
        waitUtils = new WaitUtils(driver);

        // Navigate to the test website
        driver.get(properties.getProperty("URL"));

        // Accept cookies on the website
        chatPage.acceptCookies();

        // Switch to the chat bot iframe
        chatPage.SwitchToChatBotIframe();
    }

    /**
     * Initializes the WebDriver based on the specified browser.
     *
     * @param browser The name of the browser to use (e.g., "Chrome").
     */
    private void initializeWebDriver(String browser) {
        if (browser.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--incognito");
            driver = new ChromeDriver(chromeOptions);
        }
    }


    /**
     * This test case checks if the chat bot icon is visible on the web page.
     * It asserts that the chat bot should be displayed.
     */
    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify if the chat bot is displayed on the screen")
    public void testBotVisibility() {

        Assert.assertTrue(chatPage.isChatBotDisplayed(), "Chat bot is not displayed.");
    }


    /**
     * Test case to verify the welcome message and other text validation of the chat bot.
     * It checks if the chat bot icon text is as expected and clicks the chat bot to check the welcome message.
     */
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify welcome message and other text validation")
    public void testBotWelcomeMessage() {
        // Verify if the chat bot icon text is as expected
        Assert.assertEquals(chatPage.getChatBotIconText(), "Hi there, welcome");

        // Click the chat bot and check the welcome message
        chatPage.clickChatBot();
        Assert.assertEquals(chatPage.getWelcomeMessage(), "Hello there\n" +
                "Please give your email ID");
    }


    /**
     * Test case to enter invalid email data and verify the error message.
     */
    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Enter invalid data and verify error message")
    public void testInvalidEmailDataAndErrorMessage() {
        // Step 1: Clicks the chat bot to open it.
        chatPage.clickChatBot();

        // Step 2: Enters an invalid email address and verifies the error message
        chatPage.enterEmailAndSend(properties.getProperty("invalidEmail1"));
        Assert.assertEquals(chatPage.getInvalidEmailErrorMessageText(), "Please enter a valid email address.");

        // Step 3: Enters another invalid email address and verifies the error message
        chatPage.enterEmailAndSend(properties.getProperty("invalidEmail2"));
        Assert.assertEquals(chatPage.getInvalidEmailErrorMessageText(), "Please use a business email address.");
    }


    /**
     * Test to enter valid Email data and verify the tick mark.
     */
    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Enter valid Email data and verify the tick mark")
    public void testValidEmailDataAndErrorMessage() {

        // Step 1: Click on the chat bot icon.
        chatPage.clickChatBot();

        // Step 2: Enter a valid email address (e.g., xyz@abc.com) in the email text box.
        chatPage.enterEmailAndSend(properties.getProperty("validEmail"));

        // Step 3: Verify if the tick mark indicating the acceptance of the email is displayed.
        Assert.assertTrue(chatPage.isEmailIdAccepted(), "Valid Email Id is Not Accepted");
    }


    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Restart the conversation")
    public void testRestartConversation() {
        // Step 1: Click on the chat bot to start the conversation
        chatPage.clickChatBot();

        // Step 2: Enter an email address and send it
        chatPage.enterEmailAndSend("abc@xyz.com");

        // Step 3: Wait for the LinkedIn button to be visible and click it
        waitUtils.waitForElementToBeVisible(chatPage.getLinkedInBtnWebElement(), 10);
        chatPage.clickLinkedInButton();

        // Step 4: Wait for the 'Thank You' message to be visible and click the reset conversation button
        waitUtils.waitForElementToBeVisible(chatPage.getThankYouMessageWebElement(), 10);
        chatPage.clickResetConversationButton();

        // Step 5: Wait for the 'Enter Email ID' message to be visible and verify it
        waitUtils.waitForElementToBeVisible(chatPage.getEnterEmailIdTextElementLevel2(), 10);
        Assert.assertEquals(chatPage.getEnterEmailIdTextLevel2(), "Hello there\n" +
                "Please give your email ID");
    }


    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Close and open the chat")
    public void testCloseAndOpenChat() {
        // Step 1: Click on the chat bot to open it
        chatPage.clickChatBot();

        // Step 2: Wait for the email input field to be visible and enter an email
        waitUtils.waitForElementToBeVisible(chatPage.getEmailidTextBox(), 10);
        chatPage.enterEmailAndSend("abc@xyz.com");

        // Step 3: Close the chat bot
        chatPage.closeChatBot();

        // Step 4: Verify that the chat bot is closed
        try {
            Assert.assertFalse(chatPage.getEmailidTextBox().isDisplayed(), "Chat bot is not closed.");
        } catch (NoSuchElementException e) {
            Assert.assertTrue(true, "Chat bot is closed as expected");
        }

        // Step 5: Reopen the closed chat bot
        chatPage.reopenClosedChatBot();

        // Step 6: Verify that the chat bot is reopened
        Assert.assertTrue(chatPage.getWelcomeMessageElement().isDisplayed(), "Chat bot is not reopened.");
    }


    /**
     * Test to open a new tab with google.com and return to the chat page.
     */
    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Open a new tab with google.com and return to chat page")
    public void testOpenNewTabAndReturnToChat() {

        // Click the chat bot icon to initiate the chat
        chatPage.clickChatBot();

        // Switch back to the default content
        driver.switchTo().defaultContent();

        // Open a new tab with google.com
        ((JavascriptExecutor) driver).executeScript("window.open('', '_blank');");
        driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());

        driver.get("https://www.google.com");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Switch back to the original tab (index 0)
        driver.close();
        driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());

        // Switch back to the chat bot iframe
        chatPage.SwitchToChatBotIframe();

        // Enter an email and send it
        chatPage.enterEmailAndSend("abc@xyz.com");

        // Verify that the email is accepted
        Assert.assertTrue(chatPage.isEmailIdAccepted(), "Valid Email Id is Not Accepted, after returning from a new tab.");

        // Wait for the LinkedIn button to be visible and click it
        waitUtils.waitForElementToBeVisible(chatPage.getLinkedInBtnWebElement(), 10);
        chatPage.clickLinkedInButton();

        // Wait for the thank you message to be visible and verify its text
        waitUtils.waitForElementToBeVisible(chatPage.getThankYouMessageWebElement(), 10);
        Assert.assertEquals(chatPage.getThankYouMessageText(), "Thanks for your time!");

        // Close the chat bot
        chatPage.closeChatBot();
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
