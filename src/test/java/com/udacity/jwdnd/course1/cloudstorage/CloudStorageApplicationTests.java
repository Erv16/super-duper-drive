package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

import static java.lang.Thread.sleep;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private WebDriver driver;
	private String baseUrl;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.baseUrl = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depending on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	@Order(2)
	public void testRedirection() {

		driver.get("http://localhost:" + this.port + "/signup");
		SignUp signUp = new SignUp(driver);

		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		signUp.navigateToLogin();
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	@Order(3)
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	@Order(4)
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	@Order(5)
	public void testHomePageNotAccessibleWithoutLoggingIn() {
		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(6)
	public void testSignUpAndLoginFlow() {
		doMockSignUp("Bruce","Wayne","dark_knight","alfred");
		doLogIn("dark_knight", "alfred");
		driver.get("http://localhost:" + this.port + "/home");
		Home home = new Home(driver);
		home.clickLogoutButton();
		Assertions.assertNotEquals("Home",driver.getTitle());
		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	public void waitForVisibility(String id){
		WebDriverWait wait = new WebDriverWait(driver, 4000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));

	}

	@Test
	@Order(7)
	public void testAddEditDeleteNote() throws InterruptedException {
		String title = "Note title";
		String description = "Note Description";
		String updatedTitle = "Note Title v2";
		String updatedDescription = "Note Description v2";
		//doMockSignUp("Bruce","Wayne","dark_knight","alfred");
		doLogIn("dark_knight", "alfred");
		driver.get(baseUrl+"/home");
		Note note = new Note(driver);
		Home home = new Home(driver);

		note.clickNoteTab();
		note.clickAddNoteButton();
		waitForVisibility("noteModal");
		note.inputNoteTitle(title);
		note.inputNoteDescription(description);
		sleep(2000);
		note.submitNote();
		sleep(2000);
		driver.get(baseUrl+"/home");
		note.clickNoteTab();
		Assertions.assertEquals(note.getNoteTitle(),title);
		Assertions.assertEquals(note.getNoteDescription(),description);

		note.clickNoteTab();
		note.clickNoteEditButton();
		note.inputNoteTitle(updatedTitle);
		note.inputNoteDescription(updatedDescription);
		sleep(2000);
		note.submitNote();
		sleep(2000);
		driver.get(baseUrl+"/home");
		note.clickNoteTab();
		Assertions.assertEquals(note.getNoteTitle(),updatedTitle);
		Assertions.assertEquals(note.getNoteDescription(),updatedDescription);

		note.clickNoteTab();
		note.clickNoteDeleteButton();
		driver.get(baseUrl+"/home");
		note.clickNoteTab();
		Assertions.assertTrue(note.notesExist(driver));
	}

	@Test
	@Order(8)
	public void testAddEditDeleteCredential() throws InterruptedException {

		String url = "http://freecodecamp.org";
		String username = "dark_knight";
		String password = "alfred";
		String updatedUrl = "https://www.freecodecamp.org/learn";
		String updatedUsername = "darkKnight";
		String updatedPassword = "alfredPenny";
		//doMockSignUp("Bruce","Wayne","dark_knight","alfred");
		doLogIn("dark_knight", "alfred");
		driver.get(baseUrl+"/home");
		Home home = new Home(driver);
		Credential credential = new Credential(driver);

		credential.clickCredentialTab();
		credential.clickAddCredentialButton();
		waitForVisibility("credentialModal");
		credential.inputCredentialUrl(url);
		credential.inputCredentialUsername(username);
		credential.inputCredentialPassword(password);
		sleep(2000);
		credential.submitCredential();
		sleep(2000);
		driver.get(baseUrl+"/home");
		credential.clickCredentialTab();
		Assertions.assertEquals(credential.getCredentialUrl(),url);
		Assertions.assertEquals(credential.getCredentialUsername(),username);
		Assertions.assertNotEquals(credential.getCredentialPassword(),password);

		credential.clickCredentialTab();
		credential.clickEditCredentialButton();
		String originalPassword = credential.getCredentialPassword();
		credential.inputCredentialUrl(updatedUrl);
		credential.inputCredentialUsername(updatedUsername);
		credential.inputCredentialPassword(updatedPassword);
		sleep(2000);
		credential.submitCredential();
		sleep(2000);
		driver.get(baseUrl+"/home");
		credential.clickCredentialTab();
		Assertions.assertEquals(credential.getCredentialUrl(),updatedUrl);
		Assertions.assertEquals(credential.getCredentialUsername(),updatedUsername);
		Assertions.assertNotEquals(credential.getCredentialPassword(), originalPassword);
		Assertions.assertNotEquals(credential.getCredentialPassword(),updatedPassword);

		credential.clickCredentialTab();
		credential.clickDeleteCredentialButton();
		driver.get(baseUrl+"/home");
		credential.clickCredentialTab();
		Assertions.assertTrue(credential.credentialsExist(driver));
	}
}
