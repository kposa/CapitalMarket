package Stock.Stock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class GetHistoricalData {
	@SuppressWarnings("deprecation")
	@Test
	public void f() throws IOException, InterruptedException {
		InputStream ExcelFileToRead = new FileInputStream("Companies_List.xls");
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
		HSSFSheet BestCompanies = wb.getSheet("Best Stocks");
		HSSFRow row = null;

		String securityName = null;//"BHANSALI ENGINEERING POLYMERS LTD.-$";


		System.setProperty("webdriver.chrome.driver","F:\\DemoFrameworks\\Stock\\chromedriver_2.33.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		String baseUrl;

		for (int index = 1;index<=BestCompanies.getLastRowNum(); index++) {
			int currentRow = 0;
			row = BestCompanies.getRow(index);
			try{
				securityName = row.getCell(2).toString();
				File source = new File("F:/DemoFrameworks/Stock/templates/RSI-ATR-MACD-EMA-HV.xls");
				File dest = new File("F:/DemoFrameworks/Stock/"+securityName+".xls");

				InputStream is = null;
				OutputStream os = null;
				try {
					is = new FileInputStream(source);
					os = new FileOutputStream(dest);
					byte[] buffer = new byte[1024];
					int length;
					while ((length = is.read(buffer)) > 0) {
						os.write(buffer, 0, length);
					}

				}catch(Exception e)
				{
					System.out.println("Exception1: "+securityName);
				}
				finally {
					is.close();
					os.close();
				}

				String[] CompanyNameArray = securityName.split(" ");
				String CompanyName = CompanyNameArray[0];
				for (int i = 1; i < CompanyNameArray.length - 1; i++) {
					CompanyName = CompanyName + "%20" + CompanyNameArray[i];
				}
				ObjectMapper mapper = new ObjectMapper();
				CloseableHttpClient httpclient = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet("https://in.finance.yahoo.com/_finance_doubledown/api/resource/searchassist;searchTerm="+CompanyName+"?bkt=finance-IN-en-IN-def&device=desktop&feature=canvassOffnet%2CnewContentAttribution%2CrelatedVideoFeature%2CvideoNativePlaylist&intl=in&lang=en-IN&partner=none&prid=70uf22ld1j13t&region=IN&site=finance&tz=Asia%2FKolkata&ver=0.102.883&returnMeta=true");
				CloseableHttpResponse responseCompanyName = httpclient.execute(httpGet);
				String json = EntityUtils.toString(responseCompanyName.getEntity(), "UTF-8");
				RootObject rootObject = mapper.readValue(json, RootObject.class);
				ArrayList<Item> items = rootObject.getData().getItems();
				String symbol = "";
				symbol = items.get(0).getSymbol();
				baseUrl = "https://in.finance.yahoo.com/quote/"+symbol+"/history?p="+symbol;
				driver.get(baseUrl);
				JavascriptExecutor js = ((JavascriptExecutor) driver);

				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				WebElement htmltable=driver.findElement(By.xpath("//table[@data-test='historical-prices']"));
				List<WebElement> rows=htmltable.findElements(By.tagName("tr"));
				js.executeScript("arguments[0].scrollIntoView(true);",rows.get(rows.size()-2));
				Thread.sleep(5000);

				List<WebElement> rowsNew=htmltable.findElements(By.tagName("tr"));
				List<TradingData> excelRows = new ArrayList<TradingData>();
				InputStream TempalteFile = null;
				FileOutputStream out = null;

				for(int tableRow=1;;tableRow++)
				{
					try{
						if(rowsNew.get(tableRow).findElements(By.tagName("td")).size()==7){

							String date=rowsNew.get(tableRow).findElements(By.tagName("td")).get(0).getText();
							double open=Double.parseDouble(rowsNew.get(tableRow).findElements(By.tagName("td")).get(1).getText());
							double high=Double.parseDouble(rowsNew.get(tableRow).findElements(By.tagName("td")).get(2).getText());
							double low=Double.parseDouble(rowsNew.get(tableRow).findElements(By.tagName("td")).get(3).getText());
							double close=Double.parseDouble(rowsNew.get(tableRow).findElements(By.tagName("td")).get(4).getText());
							String volume=rowsNew.get(tableRow).findElements(By.tagName("td")).get(6).getText();
							double adjClose=Double.parseDouble(rowsNew.get(tableRow).findElements(By.tagName("td")).get(5).getText());
							if(open!=0){
								excelRows.add(new TradingData(date,open,high,low,close,volume,adjClose));
							}
							if(excelRows.size()>=167){
								break;
							}
						}

					}
					catch(Exception e){
						continue;
					}

				}

				TempalteFile = new FileInputStream("F:/DemoFrameworks/Stock/"+securityName+".xls");

				HSSFWorkbook TemplateWorkBook = new HSSFWorkbook(TempalteFile);
				HSSFSheet DataSheet = TemplateWorkBook.getSheet("Data");

				for(int excelRowNum=167;excelRowNum>=1;excelRowNum--)
				{
					try{
						HSSFRow excelRow = DataSheet.getRow(excelRowNum);
						HSSFCell Date = excelRow.getCell(0);
						Date.setCellValue(excelRows.get(currentRow).getDate());
						HSSFCell Open = excelRow.getCell(1);
						Open.setCellValue(excelRows.get(currentRow).getOpen());
						HSSFCell High = excelRow.getCell(2);
						High.setCellValue(excelRows.get(currentRow).getHigh());
						HSSFCell Low = excelRow.getCell(3);
						Low.setCellValue(excelRows.get(currentRow).getLow());
						HSSFCell Close = excelRow.getCell(4);
						Close.setCellValue(excelRows.get(currentRow).getClose());
						HSSFCell Volume = excelRow.getCell(5);
						Volume.setCellValue(excelRows.get(currentRow).getVolume());
						HSSFCell AdjClose = excelRow.getCell(6);
						AdjClose.setCellValue(excelRows.get(currentRow).getAdjClose());
						out = new FileOutputStream(new File("F:/DemoFrameworks/Stock/"+securityName+".xls"));
						TemplateWorkBook.write(out);
						currentRow++;
					}catch(Exception e){
						System.out.println("Exception: "+e);
					}
					finally {
						if(TempalteFile!=null){
							TempalteFile.close();
						}
						if(out!=null){
							out.close();
						}
					}

				}

			}
			catch(Exception e){
				System.out.println("Exception2: "+e);
			}

			//HSSFCell Volatility = row.getCell(6);
			//Volatility.setCellFormula("'["+securityName+".xls]Data'!$I$168");
			//HSSFCell RSI = row.getCell(7);
			//RSI.setCellFormula("'["+securityName+".xls]Data'!$P$168");
			//HSSFCell MACD = row.getCell(8);
			//MACD.setCellFormula("'["+securityName+".xls]Data'!$U$168");
			//HSSFCell Signal = row.getCell(9);
			//Signal.setCellFormula("'["+securityName+".xls]Data'!$V$168");
			//HSSFCell Histogram = row.getCell(10);
			//Histogram.setCellFormula("'["+securityName+".xls]Data'!$W$168");
			//FileOutputStream outCompaniesList = new FileOutputStream(new File("Companies_List.xls"));
			//wb.write(outCompaniesList);
		}
		driver.quit();
	}
}
