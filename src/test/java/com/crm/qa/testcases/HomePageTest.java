package com.crm.qa.testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;

public class HomePageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	ContactsPage contactsPage;

	public HomePageTest() {
		super();
	}

	//test cases should be separated -- independent with each other
	//before each test case -- launch the browser and login
	//@test -- execute test case
	//after each test case -- close the browser
	
	@BeforeMethod
	public void setUp() {
		//initialization(null);
		testUtil = new TestUtil();
		contactsPage = new ContactsPage();
		loginPage = new LoginPage();		
		loginPage.clickOnLoginButton();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	//@Test(priority=1)
	public void verifyHomePageTitleTest(){
		String homePageTitle = homePage.verifyHomePageTitle();
		Assert.assertEquals(homePageTitle, "Cogmento CRM","Home page title not matched");
	}
	
	//@Test(priority=2)
	public void verifyUserNameTest() throws InterruptedException{		
		Thread.sleep(2000);
		Assert.assertTrue(homePage.verifyCorrectUserName());
	}
	
	@Test(priority=1)
	public void verifyContactsLinkTest(){
		homePage.clickOnContactsLink();
	}	
	
	@AfterMethod
	public void screenshotCapture(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			takeScreenshotAtEndOfTest(
					result.getTestContext().getName() + "_" + result.getMethod().getMethodName() + ".jpg");
		}
	}
	
	@AfterMethod
	public void tearDown(){
		//driver.quit();
	}
	}
