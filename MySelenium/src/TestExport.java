
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static junit.framework.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


public class TestExport {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception{
    driver = new FirefoxDriver();
    baseUrl = "http://book.theautomatedtester.co.uk";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testExport() throws Exception{
    driver.get(baseUrl + "/");
    driver.findElement(By.linkText("Chapter1")).click();
    driver.findElement(By.id("secondajaxbutton")).click();
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if ("I have been added with a timeout".equals(driver.findElement(By.cssSelector("#html5div > div")).getText())) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

 /* private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  } */
}
