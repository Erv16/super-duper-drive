package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUp {

    @FindBy(id = "inputFirstName")
    WebElement firstNameField;

    @FindBy(id = "inputLastName")
    WebElement lastNameField;

    @FindBy(id = "inputUsername")
    WebElement usernameField;

    @FindBy(id = "inputPassword")
    WebElement passwordField;

    @FindBy(id = "buttonSignUp")
    WebElement signUpButton;

    @FindBy(id = "buttonNavigateToLogin")
    WebElement loginNavigationButton;

    public SignUp(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void signUp(String firstName, String lastName, String username, String password){
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        signUpButton.submit();
    }

    public void navigateToLogin(){
        this.loginNavigationButton.click();
    }
}
