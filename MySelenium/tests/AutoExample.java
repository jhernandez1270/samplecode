package com.selftechy.wdriverbasics;

/*
 * Author - Seetaram Hegde
 * Copyrights - All rights reserved. 
 * 
 */

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CreateAccount {
	public static WebDriver driver;
	public Alert alert;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver=new FirefoxDriver();
		driver.get("file:///C:/Sample%20Website/CreateAccount.htm");
	}

	@Test
	public void testCreateAccount() throws InterruptedException{
		navigatetoWebpage(driver,"file:///C:/Sample%20Website/CreateAccount.htm");
		typeinEditbox(driver,"name","FirstName","Karthik");
		typeinEditbox(driver,"name","Lname","Shetty");
		typeinEditbox(driver,"xpath","//textarea[@name='street']","No. 425, 3rd Main, 7th Cross, 1st Sector, HSR Layout");
		selectValue(driver,"Chennai");
		selectValue(driver,"Tamilnadu");
		selectValue(driver,"Germany");
		selectRadiobutton(driver,"name","male");
		selectCheckbox(driver,"name","kannada","ON");
		selectCheckbox(driver,"name","english","ON");
		selectCheckbox(driver,"name","hindi","ON");
		clickButton(driver,"name","Save");
		closeJscriptPopup(driver,alert);
		Thread.sleep(50);
		clickLink(driver,"xpath","//a[@href='CustomerInfo.htm']");
		Thread.sleep(50);
		clickLink(driver,"xpath","//a[@href='CreateAccount.htm']");
	}
	
	public static void closeJscriptPopup(WebDriver driver, Alert alert){
		alert = driver.switchTo().alert();
		alert.accept();
	}

	public static void navigatetoWebpage(WebDriver driver, String url){
		driver.get(url);
	}
	public static void clickButton(WebDriver driver, String identifyBy, String locator){
		if (identifyBy.equalsIgnoreCase("xpath")){
			driver.findElement(By.xpath(locator)).click();
		}else if (identifyBy.equalsIgnoreCase("id")){
			driver.findElement(By.id(locator)).click();	
		}else if (identifyBy.equalsIgnoreCase("name")){
			driver.findElement(By.name(locator)).click();	
		}
		
	}
	
	public static void clickLink(WebDriver driver, String identifyBy, String locator){
		if (identifyBy.equalsIgnoreCase("xpath")){
			driver.findElement(By.xpath(locator)).click();
		}else if (identifyBy.equalsIgnoreCase("id")){
			driver.findElement(By.id(locator)).click();	
		}else if (identifyBy.equalsIgnoreCase("name")){
			driver.findElement(By.name(locator)).click();	
		}else if (identifyBy.equalsIgnoreCase("name")){
			driver.findElement(By.linkText(locator)).click();	
		}else if (identifyBy.equalsIgnoreCase("name")){
			driver.findElement(By.partialLinkText(locator)).click();	
		}
	}
	
	public static void typeinEditbox(WebDriver driver, String identifyBy, String locator, String valuetoType){
		if (identifyBy.equalsIgnoreCase("xpath")){
			driver.findElement(By.xpath(locator)).sendKeys(valuetoType);
		}else if (identifyBy.equalsIgnoreCase("id")){
			driver.findElement(By.id(locator)).sendKeys(valuetoType);	
		}else if (identifyBy.equalsIgnoreCase("name")){
			driver.findElement(By.name(locator)).sendKeys(valuetoType);	
		}
		
	}
	
	public static void selectRadiobutton(WebDriver driver, String identifyBy, String locator){
		if (identifyBy.equalsIgnoreCase("xpath")){
			driver.findElement(By.xpath(locator)).click();
		}else if (identifyBy.equalsIgnoreCase("id")){
			driver.findElement(By.id(locator)).click();	
		}else if (identifyBy.equalsIgnoreCase("name")){
			driver.findElement(By.name(locator)).click();	
		}
		
	}
	
	public static void selectCheckbox(WebDriver driver, String identifyBy, String locator, String checkFlag){
		if (identifyBy.equalsIgnoreCase("xpath")){
			if ((checkFlag).equalsIgnoreCase("ON")){
				if (!(driver.findElement(By.xpath(locator)).isSelected())){
					driver.findElement(By.xpath(locator)).click();
				}
			}
		}else if (identifyBy.equalsIgnoreCase("id")){
			if ((checkFlag).equalsIgnoreCase("ON")){
				if (!(driver.findElement(By.id(locator)).isSelected())){
					driver.findElement(By.id(locator)).click();
				}
			}
		}else if (identifyBy.equalsIgnoreCase("name")){
			if ((checkFlag).equalsIgnoreCase("ON")){
				if (!(driver.findElement(By.name(locator)).isSelected())){
					driver.findElement(By.name(locator)).click();	
				}
			}
		}
	}

	public static void selectValue(WebDriver driver, String valToBeSelected){
        List <WebElement> options = driver.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (valToBeSelected.equalsIgnoreCase(option.getText())){
				option.click();
			}
		    } 
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Execution completed.....");
		//driver.quit(); //if you want to stop the webdriver after execution, then remove the comment
	}

}