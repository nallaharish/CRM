package com.crm.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.crm.qa.base.TestBase;

public class ContactsPage extends TestBase {

	@FindBy(xpath = "//span[contains(text(),'Contacts')]")	
	WebElement contactsLabel;
	
	@FindBy(xpath = "//button[contains(text(),'Create')]")
	WebElement clickOnCreateBtn;
	
	@FindBy(name="first_name")
	WebElement firstName;
	
	@FindBy(name="last_name")
	WebElement lastName;
	
	@FindBy(xpath="//label[text()='Company']/../div//input")
	WebElement company;
	
	@FindBy(xpath = "//button[text()='Save']")
	WebElement saveBtn;
	
	
	
	// Initializing the Page Objects:
	public ContactsPage() {
		PageFactory.initElements(driver, this);
	}
	
	
	public boolean verifyContactsLabel(){
		return contactsLabel.isDisplayed();
	}
	
	public void ClickOnCreateButton() {
		clickOnCreateBtn.click();		
	}
	
	public void createNewContact(String ftName, String ltName, String comp){	
		
		firstName.sendKeys(ftName);
		lastName.sendKeys(ltName);
		company.sendKeys(comp);
		saveBtn.click();
		
	}
	

}
