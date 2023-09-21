package com.insentai.zoominfo.tests;

import com.insentai.zoominfo.pages.ChatPage;
import com.insentai.zoominfo.utils.WaitUtils;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class ZoomInfoChatTest {

    private WebDriver driver;
    private ChatPage chatPage;
    private WaitUtils waitUtils;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--incognito");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        chatPage = new ChatPage(driver);
        waitUtils = new WaitUtils(driver);

        driver.get("https://recruitment.web-test.insent.ai/fe-assignment");
        chatPage.acceptCookies();
        chatPage.SwitchToChatBotIframe();

    }

    @Test(priority = 1)
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify if the chat bot is displayed on the screen")
    public void testBotVisibility() {

        Assert.assertTrue(chatPage.isChatBotDisplayed(), "Chat bot is not displayed.");
    }

    @Test(priority = 2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify welcome message and other text validation")
    public void testBotWelcomeMessage() {

        Assert.assertEquals(chatPage.getChatBotIconText(), "Hi there, welcome");

        chatPage.clickChatBot();

        Assert.assertEquals(chatPage.getWelcomeMessage(), "Hello there\n" +
                "Please give your email ID");
    }

    @Test(priority = 3)
    @Severity(SeverityLevel.NORMAL)
    @Description("Enter invalid data and verify error message")
    public void testInvalidEmailDataAndErrorMessage() {
        chatPage.clickChatBot();

        chatPage.enterEmailAndSend("abc");
        Assert.assertEquals(chatPage.getInvalidEmailErrorMessageText(), "Please enter a valid email address.");

        chatPage.enterEmailAndSend("abc@gmail.com");
        Assert.assertEquals(chatPage.getInvalidEmailErrorMessageText(), "Please use a business email address.");

    }

    @Test(priority = 4)
    @Severity(SeverityLevel.NORMAL)
    @Description("Enter valid Email data and verify the tick mark")
    public void testValidEmailDataAndErrorMessage() {
        chatPage.clickChatBot();
        chatPage.enterEmailAndSend("xyz@abc.com");
        Assert.assertTrue(chatPage.isEmailIdAccepted(), "Valid Email Id is Not Accepted");
    }

    @Test(priority = 5)
    @Severity(SeverityLevel.NORMAL)
    @Description("Enter valid Email data and verify the tick mark")
    public void testChatBotInteractions() {
        chatPage.clickChatBot();
        chatPage.enterEmailAndSend("xyz@abc.com");

        waitUtils.waitForElementToBeVisible(chatPage.getLinkedInBtnWebElement(), 10);
        chatPage.clickLinkedInButton();

        waitUtils.waitForElementToBeVisible(chatPage.getThankYouMessageWebElement(), 10);
        Assert.assertEquals(chatPage.getThankYouMessageText(), "Thanks for your time!");
    }

    @Test(priority = 6)
    @Severity(SeverityLevel.NORMAL)
    @Description("Restart the conversation")
    public void testRestartConversation() {
        chatPage.clickChatBot();
        chatPage.clickResetConversationButton();

        Assert.assertTrue(chatPage.isChatBotDisplayed(), "Chat bot is not displayed after restart.");
    }

    @Test(priority = 7)
    @Severity(SeverityLevel.NORMAL)
    @Description("Close and open the chat")
    public void testCloseAndOpenChat() {
        chatPage.clickChatBot();
        chatPage.closeChatBot();

        // Verify that the chat is closed
        Assert.assertFalse(chatPage.isChatBotDisplayed(), "Chat bot is still displayed after closing.");

        chatPage.reopenClosedChatBot();

        // Verify that the chat is reopened
        Assert.assertTrue(chatPage.isChatBotDisplayed(), "Chat bot is not displayed after reopening.");
    }

    @Test(priority = 8)
    @Severity(SeverityLevel.NORMAL)
    @Description("Open a new tab with google.com and return to chat page")
    public void testOpenNewTabAndReturnToChat() {
        chatPage.clickChatBot();
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

        chatPage.SwitchToChatBotIframe();
        chatPage.enterEmailAndSend("abc@xyz.com");
        Assert.assertTrue(chatPage.isEmailIdAccepted(), "Valid Email Id is Not Accepted, after returning from a new tab.");

        waitUtils.waitForElementToBeVisible(chatPage.getLinkedInBtnWebElement(), 10);
        chatPage.clickLinkedInButton();

        waitUtils.waitForElementToBeVisible(chatPage.getThankYouMessageWebElement(), 10);
        Assert.assertEquals(chatPage.getThankYouMessageText(), "Thanks for your time!");

        chatPage.closeChatBot();
    }


    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
