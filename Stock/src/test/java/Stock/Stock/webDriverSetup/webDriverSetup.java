package Stock.Stock.webDriverSetup;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class webDriverSetup {
	private static WebDriver driver;
	public static WebDriver webDriver()
	{
		return driver;
	}
	public static WebDriver LaunchBrowser(String driverPath)
	{
		System.setProperty("webdriver.chrome.driver",driverPath);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	
	public static void navigate(String url)
	{
		driver.get(url);
	}
	
	public static void scrollDown()
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public static void getFocus(WebElement element) throws InterruptedException
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);",element);
		Thread.sleep(5000);
	}
}
