package com.insentai.zoominfo.pages;

import com.insentai.zoominfo.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChatPage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    public ChatPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitUtils = new WaitUtils(driver);

    }

    @FindBy(id = "hs-eu-confirmation-button")
    private WebElement cookieAcceptButton;

    @FindBy(xpath = "//div[@id='insent-popup-content']")
    private WebElement chatBotIcon;

    @FindBy(xpath = "//div[@class='sc-iwsKbI cWzUpN']")
    private WebElement welcomeMessage;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailTextBox;

    @FindBy(xpath = "//button[@class='sc-ckVGcZ gRuMQg']")
    private WebElement sendButton;

    @FindBy(xpath = "//div[@class='sc-kEYyzF faNzMh']")
    private WebElement invalidEmailErrorMsg;

    @FindBy(xpath = "//div[@id='insent-input-message-input-box-body-input-validate']/form/*[2]")
    private WebElement emailIdTickMark;

    @FindBy(xpath = "//div[text()='LinkedIn' and @id='insent-buttons-message-button']")
    private WebElement linkedInBtn;

    @FindBy(xpath = "//div[@id='insent-conversation-list']/div[6]/div/div/div[2]/div/div")
    private WebElement thankyouMessage;

    @FindBy(xpath = "//button[@class='sc-bEjcJn cRQNxc']")
    private WebElement resetConvoBtn;

    @FindBy(xpath = "//div[@id='insent-conversation-list']/div[8]/div/div/div[2]/div/div")
    private WebElement enterEmailIdTextLevel2;

    @FindBy(xpath = "//div[@data-testid='insent-test-card-close']")
    private WebElement chatBotCloseIcon;

    @FindBy(xpath = "//div[@id='insent-launcher-icon']")
    private WebElement closedChatBotLaunchIcon;

    public void acceptCookies() {
        try {
            waitUtils.waitForElementToBeClickable(cookieAcceptButton, 10);
            cookieAcceptButton.click();
        } catch (Exception e) {
            System.out.println("No 'Accept' button found for Cookies.");
        }
    }

    public void SwitchToChatBotIframe() {
        driver.switchTo().frame("insent-iframe");
    }

    public String getChatBotIconText() {
        return chatBotIcon.getText();
    }

    public void clickChatBot() {
        chatBotIcon.click();
    }

    public WebElement getWelcomeMessageElement() {
        return welcomeMessage;
    }

    public String getWelcomeMessage() {
        return welcomeMessage.getText();
    }

    public WebElement getEmailidTextBox() {
        return emailTextBox;
    }

    public void enterEmailAndSend(String email) {
        emailTextBox.sendKeys(email);
        sendButton.click();
    }

    public boolean isEmailIdAccepted() {
        return emailIdTickMark.isDisplayed();
    }

    public String getInvalidEmailErrorMessageText() {
        return invalidEmailErrorMsg.getText();
    }

    public boolean isChatBotDisplayed() {
        return chatBotIcon.isDisplayed();
    }

    public WebElement getLinkedInBtnWebElement() {
        return linkedInBtn;
    }

    public void clickLinkedInButton() {
        linkedInBtn.click();
    }

    public WebElement getThankYouMessageWebElement() {
        return thankyouMessage;
    }

    public String getThankYouMessageText() {
        return thankyouMessage.getText();
    }

    public void clickResetConversationButton() {
        resetConvoBtn.click();
    }

    public WebElement getEnterEmailIdTextElementLevel2() {
        return enterEmailIdTextLevel2;
    }

    public String getEnterEmailIdTextLevel2() {
        return enterEmailIdTextLevel2.getText();
    }

    public void closeChatBot() {
        chatBotCloseIcon.click();
    }

    public void reopenClosedChatBot() {
        closedChatBotLaunchIcon.click();
    }

}
