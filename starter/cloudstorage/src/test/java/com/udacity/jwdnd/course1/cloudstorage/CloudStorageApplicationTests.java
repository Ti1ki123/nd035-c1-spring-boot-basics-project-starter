package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
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
		// success message below depening on the rest of your code.
		*/
        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }


    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
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
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");
        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login?signup=true", driver.getCurrentUrl());
    }

    //Write a Selenium test that verifies that the home page is not accessible without logging in.
    @Test
    public void testHomepagewithouLogging() {
        // Create a test account
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/home");
        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    //Write a Selenium test that signs up a new user, logs that user in, verifies that they can access the home page, then logs out and verifies that the home page is no longer accessible.
    @Test
    public void testLoginGetHomeandLogoutGethome() {
        // Create a test account
        doMockSignUp("LoginAndlogout", "Test123", "RT123456", "123");
        doLogIn("RT123456","123");
        WebElement logoutButton = driver.findElement(By.id("logout-button"));
        logoutButton.click();
        Assertions.assertTrue(driver.findElement(By.id("logout-message")).getText().contains("You have been logged out"));
        driver.get("http://localhost:" + this.port + "/home");
        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    private void doMockCreateNote(String title, String description) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        WebElement logoutButton = driver.findElement(By.id("nav-notes-tab"));
        logoutButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-note-button")));

        WebElement addnotebutton = driver.findElement(By.id("add-new-note-button"));
        addnotebutton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-form")));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement inputFirstName = driver.findElement(By.id("note-title"));
        inputFirstName.click();
        inputFirstName.sendKeys(title);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement inputLastName = driver.findElement(By.id("note-description"));
        inputLastName.click();
        inputLastName.sendKeys(description);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-change-note")));
        WebElement savenotes = driver.findElement(By.id("save-change-note"));
        savenotes.click();
        Assertions.assertEquals("http://localhost:" + this.port + "/note/add", driver.getCurrentUrl());

        Assertions.assertTrue(driver.findElement(By.id("result-success")).getText().contains("Your changes were successfully saved"));


        WebElement homelink = driver.findElement(By.id("return-home"));
        homelink.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-notetitle-0")));

        Assertions.assertTrue(driver.findElement(By.id("note-notetitle-0")).getText().contains(title));
    }
    //Write a Selenium test that logs in an existing user, creates a note and verifies that the note details are visible in the note list.
    @Test
    public void TestCreateNote() {
        // Create a test account
        doMockSignUp("CreateNote", "Test123", "CreateNote", "123");
        doLogIn("CreateNote","123");
        doMockCreateNote("TODO11","-checkmail");

    }

    //Write a Selenium test that logs in an existing user with existing notes, clicks the edit note button on an existing note, changes the note data, saves the changes, and verifies that the changes appear in the note list
    @Test
    public void TestUpdateNote() {
//         Create a test account
        doMockSignUp("UpdateNoted", "Test123", "UpdateNoted", "123");
        doLogIn("UpdateNoted","123");
        doMockCreateNote("TODO21","-checktodo");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        WebElement movenoteButton = driver.findElement(By.id("nav-notes-tab"));
        movenoteButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-edit-0")));

        WebElement notebutton = driver.findElement(By.id("note-edit-0"));
        notebutton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-form")));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement inputFirstName = driver.findElement(By.id("note-title"));
        inputFirstName.click();
        inputFirstName.sendKeys("TODO21-updated");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement inputLastName = driver.findElement(By.id("note-description"));
        inputLastName.click();
        inputLastName.sendKeys("-check mail-updated");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-change-note")));
        WebElement savenotes = driver.findElement(By.id("save-change-note"));
        savenotes.click();;

        Assertions.assertEquals("http://localhost:" + this.port + "/note/change", driver.getCurrentUrl());

        Assertions.assertTrue(driver.findElement(By.id("result-success")).getText().contains("Your changes were successfully saved"));


        WebElement homelink = driver.findElement(By.id("return-home"));
        homelink.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-notetitle-0")));

        Assertions.assertTrue(driver.findElement(By.id("note-notetitle-0")).getText().contains("TODO21TODO21-updated"));

    }

    @Test
    public void TestDeleteNote() {
//         Create a test account
        doMockSignUp("DeleteNote", "Test123", "DeleteNote", "123");
        doLogIn("DeleteNote","123");
        doMockCreateNote("TODO21123","-checktodo");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 7);
        WebElement movenoteButton = driver.findElement(By.id("nav-notes-tab"));
        movenoteButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-edit-0")));

        WebElement notebutton = driver.findElement(By.id("note-delete-0"));
        notebutton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-form")));


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-change-note")));
        WebElement savenotes = driver.findElement(By.id("save-change-note"));
        savenotes.click();;

        Assertions.assertEquals("http://localhost:" + this.port + "/note/delete", driver.getCurrentUrl());

        Assertions.assertTrue(driver.findElement(By.id("result-success")).getText().contains("Your changes were successfully saved"));


        WebElement homelink = driver.findElement(By.id("return-home"));
        homelink.click();



        try {
            // Try to find an element that does not exist
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-notetitle-0")));
            // If no exception occurs, the test should fail
            Assertions.fail("TimeoutException was not thrown when element was not found.");
        } catch (TimeoutException e) {
            // Assert that the exception was thrown
            Assertions.assertTrue(true, "TimeoutException was correctly thrown");
        }

    }
    // Method to mock creating a credential
    private void doMockCreateCredential(String username, String password, String url) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        WebElement logoutButton = driver.findElement(By.id("nav-credentials-tab"));
        logoutButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-credential-button")));

        WebElement addCredentialButton = driver.findElement(By.id("add-new-credential-button"));
        addCredentialButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-form")));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement inputurl = driver.findElement(By.id("credential-url"));
        inputurl.click();
        inputurl.sendKeys(url);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username"))); // Changed from "note-description"
        WebElement inputusername = driver.findElement(By.id("credential-username")); // Changed from "note-description"
        inputusername.click();
        inputusername.sendKeys(username);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement inputpass = driver.findElement(By.id("credential-password"));
        inputpass.click();
        inputpass.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-change-credential")));
        WebElement saveCredentials = driver.findElement(By.id("save-change-credential"));
        saveCredentials.click();
        Assertions.assertEquals("http://localhost:" + this.port + "/credential/add", driver.getCurrentUrl());

        Assertions.assertTrue(driver.findElement(By.id("result-success")).getText().contains("Your changes were successfully saved"));

        WebElement homeLink = driver.findElement(By.id("return-home"));
        homeLink.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username-0")));

        Assertions.assertTrue(driver.findElement(By.id("credential-username-0")).getText().contains(username));
    }

    // Test method to create a credential
    @Test
    public void TestCreateCredential() {
        // Create a test account
        doMockSignUp("CreateCredential", "Test123", "CreateCredential", "123");
        doLogIn("CreateCredential", "123");
        doMockCreateCredential("Chinhpahn", "ti1ki123","http://localhost:" + this.port + "/login");
    }

    // Test method to update a credential
    @Test
    public void TestUpdateCredential() {
        // Create a test account
        doMockSignUp("UpdateCredential", "Test123", "UpdateCredential", "123");
        doLogIn("UpdateCredential", "123");
        doMockCreateCredential("ChinhPhankoko", "ti1ki12ddd3","http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        WebElement moveCredentialButton = driver.findElement(By.id("nav-credentials-tab")); // Changed from "nav-notes-tab"
        moveCredentialButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-edit-0"))); // Changed from "note-edit-0"

        WebElement editButton = driver.findElement(By.id("credential-edit-0")); // Changed from "note-edit-0"
        editButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-form"))); // Changed from "note-form"

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement inputurl = driver.findElement(By.id("credential-url"));
        inputurl.clear();
        inputurl.click();

        inputurl.sendKeys("http://localhost:" + this.port + "/login");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username"))); // Changed from "note-description"
        WebElement inputusername = driver.findElement(By.id("credential-username")); // Changed from "note-description"
        inputusername.click();
        inputusername.sendKeys("ChinhPhankokoUpdate");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement inputpass = driver.findElement(By.id("credential-password"));
        inputpass.click();
        inputpass.sendKeys("ti1ki123");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-change-credential"))); // Changed from "save-change-note"
        WebElement saveCredentials = driver.findElement(By.id("save-change-credential")); // Changed from "save-change-note"
        saveCredentials.click();

        Assertions.assertEquals("http://localhost:" + this.port + "/credential/change", driver.getCurrentUrl()); // Changed URL

        Assertions.assertTrue(driver.findElement(By.id("result-success")).getText().contains("Your changes were successfully saved"));

        WebElement homeLink = driver.findElement(By.id("return-home"));
        homeLink.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username-0"))); //

        Assertions.assertTrue(driver.findElement(By.id("credential-username-0")).getText().contains("ChinhPhankokoChinhPhankokoUpda"));
    }

    // Test method to delete a credential
    @Test
    public void TestDeleteCredential() {
        // Create a test account
        doMockSignUp("DeleteCredential", "Test123", "DeleteCredential", "123");
        doLogIn("DeleteCredential", "123");
        doMockCreateCredential("Credential3gggg", "check todo","http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 7);
        WebElement moveCredentialButton = driver.findElement(By.id("nav-credentials-tab"));
        moveCredentialButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-edit-0")));

        WebElement deleteButton = driver.findElement(By.id("credential-delete-0"));
        deleteButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-form")));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-change-credential")));
        WebElement saveCredentials = driver.findElement(By.id("save-change-credential"));
        saveCredentials.click();

        Assertions.assertEquals("http://localhost:" + this.port + "/credential/delete", driver.getCurrentUrl());

        Assertions.assertTrue(driver.findElement(By.id("result-success")).getText().contains("Your changes were successfully saved"));

        WebElement homeLink = driver.findElement(By.id("return-home"));
        homeLink.click();

        try {
            // Try to find an element that does not exist
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-credentialtitle-0")));
            // If no exception occurs, the test should fail
            Assertions.fail("TimeoutException was not thrown when element was not found.");
        } catch (TimeoutException e) {
            // Assert that the exception was thrown
            Assertions.assertTrue(true, "TimeoutException was correctly thrown");
        }
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "UT", "123");
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
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
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


}
