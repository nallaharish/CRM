package com.crm.qa.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.JavascriptExecutor;



import com.crm.qa.base.TestBase;

public class TestUtil extends TestBase {

	public static int PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;
	public static String screenshotsSubFolderName;

	public static String TESTDATA_SHEET_PATH = "C:\\Users\\sh\\eclipse-workspace\\FreeCRM\\src\\main\\java\\com\\crm\\qa\\testdata\\FreeCrmTestData.xlsx";

	static Workbook book;
	static Sheet sheet;
	static JavascriptExecutor js;

	public void switchToFrame() {
		driver.switchTo().frame("downloadFrame");
	}

	public static Object[][] getTestData(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		// System.out.println(sheet.getLastRowNum() + "--------" +
		// sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				// System.out.println(data[i][k]);
			}
		}
		return data;
	}
	

	public static void runTimeInfo(String messageType, String message) throws InterruptedException {
		js = (JavascriptExecutor) driver;
		// Check for jQuery on the page, add it if need be
		js.executeScript("if (!window.jQuery) {"
				+ "var jquery = document.createElement('script'); jquery.type = 'text/javascript';"
				+ "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';"
				+ "document.getElementsByTagName('head')[0].appendChild(jquery);" + "}");
		Thread.sleep(5000);

		// Use jQuery to add jquery-growl to the page
		js.executeScript("$.getScript('https://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");

		// Use jQuery to add jquery-growl styles to the page
		js.executeScript("$('head').append('<link rel=\"stylesheet\" "
				+ "href=\"https://the-internet.herokuapp.com/css/jquery.growl.css\" " + "type=\"text/css\" />');");
		Thread.sleep(5000);

		// jquery-growl w/ no frills
		js.executeScript("$.growl({ title: 'GET', message: '/' });");
//'"+color+"'"
		if (messageType.equals("error")) {
			js.executeScript("$.growl.error({ title: 'ERROR', message: '" + message + "' });");
		} else if (messageType.equals("info")) {
			js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
		} else if (messageType.equals("warning")) {
			js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
		} else
			System.out.println("no error message");
		// jquery-growl w/ colorized output
//		js.executeScript("$.growl.error({ title: 'ERROR', message: 'your error message goes here' });");
//		js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
//		js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
		Thread.sleep(5000);
	}

	/*
	 * @AfterSuite public void generateExtentReports() throws Exception {
	 * extentReports.flush(); //Desktop.getDesktop().browse(new
	 * File("AllTests.html").toURI()); //Desktop.getDesktop().browse(new
	 * File("FailedTests.html").toURI()); }
	 * 
	 * @AfterMethod public void checkStatus(Method m, ITestResult result) {
	 * if(result.getStatus() == ITestResult.FAILURE) { String screenshotPath = null;
	 * screenshotPath = captureScreenshot(result.getTestContext().getName()+ "_"
	 * +result.getMethod().getMethodName()+".jpg");
	 * extentTest.addScreenCaptureFromPath(screenshotPath);
	 * extentTest.fail(result.getThrowable()); } else if(result.getStatus() ==
	 * ITestResult.SUCCESS) { extentTest.pass(m.getName() + " is passed"); }
	 * 
	 * extentTest.assignCategory(m.getAnnotation(Test.class).groups()); }
	 */

}
