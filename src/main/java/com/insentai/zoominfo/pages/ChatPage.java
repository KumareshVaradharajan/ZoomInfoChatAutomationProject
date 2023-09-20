package com.insentai.zoominfo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChatPage {

    private WebDriver driver;

    public ChatPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

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

    @FindBy(xpath = "//div[text()='LinkedIn']")
    private WebElement linkedInBtn;

    @FindBy(xpath = "//div[@id='insent-conversation-list']/div[6]/div/div/div[2]/div/div")
    private WebElement exitMessage;

    @FindBy(xpath = "//button[@class='sc-bEjcJn cRQNxc']")
    private WebElement resetConvoBtn;

    @FindBy(xpath = "//div[@data-testid='insent-test-card-close']")
    private WebElement chatBotCloseIcon;

    @FindBy(xpath = "//div[@id='insent-launcher-icon']")
    private WebElement closedChatBotLaunchIcon;



    public void clickChatBot() {
        chatBotIcon.click();
    }

    public String getWelcomeMessage() {
        return welcomeMessage.getText();
    }

    public void enterEmailAndSend(String email) {
        emailTextBox.sendKeys(email);
        sendButton.click();
    }

    public String getInvalidEmailErrorMessageText() {
        return invalidEmailErrorMsg.getText();
    }

    public boolean isChatBotDisplayed() {
        return chatBotIcon.isDisplayed();
    }

    public void clickSendButton() {
        sendButton.click();
    }

    public void clickLinkedInButton() {
        linkedInBtn.click();
    }

    public String getExitMessageText() {
        return exitMessage.getText();
    }

    public void clickResetConversationButton() {
        resetConvoBtn.click();
    }

    public void closeChatBot() {
        chatBotCloseIcon.click();
    }

    public void reopenClosedChatBot() {
        closedChatBotLaunchIcon.click();
    }

}
