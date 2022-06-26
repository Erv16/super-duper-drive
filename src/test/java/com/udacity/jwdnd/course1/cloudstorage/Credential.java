package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Credential {

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "add-credential")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmitButton;

    @FindBy(id = "credentialUrl")
    private WebElement credentialUrlData;

    @FindBy(id = "credentialUsername")
    private WebElement credentialUsernameData;

    @FindBy(id = "credentialPassword")
    private WebElement credentialPasswordData;

    @FindBy(id = "edit-credential")
    private WebElement editCredentialButton;

    @FindBy(id = "delete-credential")
    private WebElement deleteCredentialButton;

    private final JavascriptExecutor js;

    public Credential(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        js = (JavascriptExecutor) webDriver;
    }

    public void clickCredentialTab(){
        credentialTab.click();
    }

    public void clickAddCredentialButton(){
        js.executeScript("arguments[0].click();", addCredentialButton);
    }

    public void clickEditCredentialButton(){
        js.executeScript("arguments[0].click();", editCredentialButton);
    }

    public void clickDeleteCredentialButton(){
        js.executeScript("arguments[0].click();", deleteCredentialButton);
    }

    public void inputCredentialUrl(String url){
        js.executeScript("arguments[0].value='" + url + "';", credentialUrl);
    }

    public void inputCredentialUsername(String username){
        js.executeScript("arguments[0].value='" + username + "';", credentialUsername);
    }

    public void inputCredentialPassword(String password){
        js.executeScript("arguments[0].value='" + password + "';", credentialPassword);
    }

    public void submitCredential(){
        credentialSubmitButton.submit();
    }

    public String getCredentialUrl(){
        return credentialUrlData.getAttribute("innerHTML");
    }

    public String getCredentialUsername(){
        return credentialUsernameData.getAttribute("innerHTML");
    }

    public String getCredentialPassword(){
        return credentialPasswordData.getAttribute("innerHTML");
    }

    public boolean elementExists(By locatorKey, WebDriver driver) {
        try {
            driver.findElement(locatorKey);

            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public boolean credentialsExist(WebDriver driver) {
        return !elementExists(By.id("tblCredentialUrl"), driver)
                && !elementExists(By.id("tblCredentialUsername"), driver)
                && !elementExists(By.id("tblCredentialPassword"), driver);
    }
}
