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
        chatPage.acceptCookiesAndSwitchToIframe();

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
        Assert.assertTrue(chatPage.isEmailIdAccepted());
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


    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
