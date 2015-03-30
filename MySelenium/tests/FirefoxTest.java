import junit.*:
import static junit.framework.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxTest {
	private WebDriver driver;
	private String baseUrl;
	
	public void setUp() {
		driver = new FirefoxDriver();
		baseUrl = "http://book.theautomatedtester.co.uk/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
	}
}