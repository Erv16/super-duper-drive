package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Note {

    private final JavascriptExecutor js;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "add-note")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "notetitle")
    private WebElement noteTitleData;

    @FindBy(id = "notedescription")
    private WebElement noteDescriptionData;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id = "edit-note")
    private WebElement noteEditButton;

    @FindBy(id = "delete-note")
    private WebElement noteDeleteButton;

    public Note(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        js = (JavascriptExecutor) webDriver;
    }

    public void clickNoteTab(){
        noteTab.click();
    }

    public void clickAddNoteButton(){ js.executeScript("arguments[0].click();", addNoteButton); }

    public void inputNoteTitle(String title){
        js.executeScript("arguments[0].value='" + title + "';", noteTitle);
    }

    public void inputNoteDescription(String description) {
        js.executeScript("arguments[0].value='" + description + "';", noteDescription);
    }

    public void submitNote() {
        noteSubmitButton.submit();
    }

    public String getNoteTitle(){
        return noteTitleData.getAttribute("innerHTML");
    }

    public String getNoteDescription(){
        return noteDescriptionData.getAttribute("innerHTML");
    }

    public void clickNoteEditButton() {
        js.executeScript("arguments[0].click();", noteEditButton);
    }

    public void clickNoteDeleteButton(){
        js.executeScript("arguments[0].click();", noteDeleteButton);
    }

    public boolean elementExists(By locatorKey, WebDriver driver) {
        try {
            driver.findElement(locatorKey);

            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public boolean notesExist(WebDriver driver) {
        return !elementExists(By.id("notetitle"), driver) && !elementExists(By.id("notedescription"), driver);
    }
}
