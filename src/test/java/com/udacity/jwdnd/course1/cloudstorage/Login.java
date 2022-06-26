package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {

    @FindBy(id = "inputUsername")
    private WebElement usernameInputField;

    @FindBy(id = "inputPassword")
    private WebElement passwordInputField;

    @FindBy(id = "login-button")
    private WebElement submitButton;

    public Login(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        this.usernameInputField.sendKeys(username);
        this.passwordInputField.sendKeys(password);
        this.submitButton.click();
    }
}
