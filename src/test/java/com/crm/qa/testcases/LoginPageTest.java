package com.crm.qa.testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;

public class LoginPageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;

	public LoginPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
		loginPage = new LoginPage();
	}

	@Test(priority = 1)
	public void loginPageTitleTest() {
		String title = loginPage.validateLoginPageTitle();
		Assert.assertEquals(title, "#1 Free CRM App for every business customer relationship management cloud");
	}

	@Test(priority = 2)
	public void crmLogoImageTest() {
		boolean flag = loginPage.validateCRMImage();
		Assert.assertTrue(flag);
	}
	
	@Test(priority = 3)
	public void clickOnLoginButtonTest() throws InterruptedException {
		loginPage.clickOnLoginButton();
		Thread.sleep(4000);
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
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
