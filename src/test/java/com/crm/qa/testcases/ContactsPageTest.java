package com.crm.qa.testcases;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;


public class ContactsPageTest extends TestBase {

	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	ContactsPage contactsPage;
	String sheetName = "contacts";

	public ContactsPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() throws InterruptedException {
		initialization();
		testUtil = new TestUtil();
		contactsPage = new ContactsPage();
		loginPage = new LoginPage();
		loginPage.clickOnLoginButton();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		TestUtil.runTimeInfo("error", "login successful");

	}

	//@Test(priority = 1)
	public void verifyContactsPageLabel() {
		Assert.assertTrue(contactsPage.verifyContactsLabel(), "contacts label is missing on the page");

	}

	@DataProvider
	public Object[][] getCRMTestData(){
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}

	@Test(priority = 1, dataProvider = "getCRMTestData")
	public void validateCreateNewContact(String firstName, String lastName, String company)
			throws InterruptedException {
		homePage.clickOnContactsLink();
		Thread.sleep(1000);
		contactsPage.ClickOnCreateButton();
		contactsPage.createNewContact(firstName, lastName, company);

	}

	@AfterMethod
	public void screenshotCapture(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			takeScreenshotAtEndOfTest(
					result.getTestContext().getName() + "_" + result.getMethod().getMethodName() + ".jpg");
		}
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
