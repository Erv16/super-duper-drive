package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Home {

    @FindBy(id="logout")
    WebElement logoutButton;

    public Home(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void clickLogoutButton(){
        logoutButton.click();
    }
}
