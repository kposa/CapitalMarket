package stock.webDriverSetup;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import stock.util.TradingData;
import stock.util.Util;

public class WebDriverSetup {
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

	public static List<TradingData> navigateToHistoricalData(String workingDir,String securityName) throws ClientProtocolException, IOException, InterruptedException
	{
		String symbol = Util.getSymbol(securityName);
		List<TradingData> historicalData = null;
		if(symbol!="")
		{
			driver = WebDriverSetup.LaunchBrowser(workingDir+"\\chromedriver_2.33.exe");
			WebDriverSetup.navigate("https://in.finance.yahoo.com/quote/"+symbol+"/history?p="+symbol);
			WebDriverSetup.scrollDown();
			WebElement htmltable=driver.findElement(By.xpath("//table[@data-test='historical-prices']"));
			historicalData = Util.getHistoricalData(htmltable);
		}
		return historicalData;
	}

	public static void closeDriver()
	{
		driver.quit();
	}
}
