package Stock.Stock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import Stock.Stock.ExcelHelper.excelHelper;
import Stock.Stock.util.util;
import Stock.Stock.webDriverSetup.webDriverSetup;

public class GetHistoricalData {
	@SuppressWarnings("deprecation")
	@Test
	public void f() throws IOException, InterruptedException {
		HSSFRow row = null;
		String securityName = null;//"BHANSALI ENGINEERING POLYMERS LTD.-$";
		String baseUrl;

		HSSFSheet BestCompanies = excelHelper.getSheet("Companies_List.xls", "Best Stocks");
		WebDriver driver = webDriverSetup.LaunchBrowser("F:\\DemoFrameworks\\CapitalMarket\\Stock\\chromedriver_2.33.exe");

		for (int index = 1;index<=BestCompanies.getLastRowNum(); index++) {
			int currentRow = 0;
			row = BestCompanies.getRow(index);
			try{
				securityName = row.getCell(2).toString();
				excelHelper.copyFile("F:/DemoFrameworks/CapitalMarket/Stock/templates/RSI-ATR-MACD-EMA-HV.xls", "F:/DemoFrameworks/CapitalMarket/Stock/"+securityName+".xls");
				String symbol = util.getSymbol(securityName);
				webDriverSetup.navigate("https://in.finance.yahoo.com/quote/"+symbol+"/history?p="+symbol);
				webDriverSetup.scrollDown();
				WebElement htmltable=driver.findElement(By.xpath("//table[@data-test='historical-prices']"));
				List<WebElement> rows=htmltable.findElements(By.tagName("tr"));
				webDriverSetup.getFocus(rows.get(rows.size()-2));

				List<WebElement> rowsNew=htmltable.findElements(By.tagName("tr"));
				List<TradingData> excelRows = new ArrayList<TradingData>();
				InputStream TempalteFile = null;
				FileOutputStream out = null;

				for(int tableRow=1;;tableRow++)
				{
					try{
						if(rowsNew.get(tableRow).findElements(By.tagName("td")).size()==7){

							String date=rowsNew.get(tableRow).findElements(By.tagName("td")).get(0).getText();
							double open=Double.parseDouble(rowsNew.get(tableRow).findElements(By.tagName("td")).get(1).getText().replaceAll("[,]",""));
							double high=Double.parseDouble(rowsNew.get(tableRow).findElements(By.tagName("td")).get(2).getText().replaceAll("[,]",""));
							double low=Double.parseDouble(rowsNew.get(tableRow).findElements(By.tagName("td")).get(3).getText().replaceAll("[,]",""));
							double close=Double.parseDouble(rowsNew.get(tableRow).findElements(By.tagName("td")).get(4).getText().replaceAll("[,]",""));
							String volume=rowsNew.get(tableRow).findElements(By.tagName("td")).get(6).getText();
							double adjClose=Double.parseDouble(rowsNew.get(tableRow).findElements(By.tagName("td")).get(5).getText().replaceAll("[,]",""));
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

				TempalteFile = new FileInputStream("F:/DemoFrameworks/CapitalMarket/Stock/"+securityName+".xls");

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
						out = new FileOutputStream(new File("F:/DemoFrameworks/CapitalMarket/Stock/"+securityName+".xls"));
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


		}
		driver.quit();
	}
}
