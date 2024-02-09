package com.crm.qa.base;

import java.lang.reflect.Method;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.crm.qa.util.TestUtil;
import com.crm.qa.util.WebEventListener;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.awt.Desktop;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;


import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;
	public static String screenshotsSubFolderName;
	

	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/com/crm" + "/qa/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void initialization() {
		ITestContext context = null;
		String browserName1 = prop.getProperty("browser");
		if (browserName1.equals("chrome")) {
			ChromeOptions chromeoption = new ChromeOptions();
			//chromeoption.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(chromeoption);			
		} else if (browserName1.equals("FF")) {
			System.setProperty("webdriver.gecko.driver", "E:\\Software\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with
		// EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(2000, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));	
	}
			
	// Only Screenshot
		public static void takeScreenshotAtEndOfTest(String fileName) {
			if (screenshotsSubFolderName == null) {
				LocalDateTime myDateObj = LocalDateTime.now();
				DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
				screenshotsSubFolderName = myDateObj.format(myFormatObj);
			}
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
			File destFile = new File("./Screenshots/" + screenshotsSubFolderName + "/" + fileName);
			try {
				FileUtils.copyFile(sourceFile, destFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Screenshot saved successfully");			
		}
		
		@AfterMethod
		public void screenshotCapture(ITestResult result) throws IOException {
			if (result.getStatus() == ITestResult.FAILURE) {
				takeScreenshotAtEndOfTest(
						result.getTestContext().getName() + "_" + result.getMethod().getMethodName() + ".jpg");
			}
		}
}



