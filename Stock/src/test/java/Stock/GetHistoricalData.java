package Stock;

import java.io.*;
import java.util.List;
import org.apache.poi.hssf.usermodel.*;
import org.testng.annotations.Test;
import Stock.ExcelHelper.excelHelper;
import Stock.Technical.TechnicalData;
import Stock.util.TradingData;
import Stock.webDriverSetup.webDriverSetup;

public class GetHistoricalData {
	@Test
	public void getHistoricalData() throws Exception {
		String workingDir = System.getProperty("user.dir");
		String filePath = null;
		HSSFWorkbook technicalWorkBook = null;
		String securityName = null;
		String templateFilePath = workingDir+"\\templates\\RSI-ATR-MACD-EMA-HV.xls";
		HSSFSheet bestCompanies = excelHelper.getSheet("Companies_List.xls", "Best Stocks");
		for (int index = 1;index<=bestCompanies.getLastRowNum(); index++) 
		{
			try
			{
				HSSFRow row = bestCompanies.getRow(index);
				securityName = row.getCell(2).toString();
				filePath = workingDir+"\\Best\\"+securityName+".xls";

				List<TradingData> historicalData = webDriverSetup.navigateToHistoricalData(workingDir,securityName);
				webDriverSetup.closeDriver();
				if(historicalData!=null)
				{
					excelHelper.copyFile(templateFilePath, filePath);
					technicalWorkBook = new HSSFWorkbook(new FileInputStream(filePath));
					HSSFSheet DataSheet = technicalWorkBook.getSheet("Data");
					excelHelper.updateHistoricalData(technicalWorkBook,DataSheet,historicalData,filePath);

					TechnicalData technicalData = new TechnicalData();
					technicalData.calculateTechnicalData(historicalData);
					
					HSSFWorkbook templateWorkBook = new HSSFWorkbook(new FileInputStream("Companies_List.xls"));
					bestCompanies = templateWorkBook.getSheet("Best Stocks");
					HSSFRow excelRow = bestCompanies.getRow(index);
					HSSFCell rsiCell = excelRow.getCell(7);
					HSSFCell macdCell = excelRow.getCell(8);
					HSSFCell signalCell = excelRow.getCell(9);
					HSSFCell histogramCell = excelRow.getCell(10);
					double rsi = technicalData.getRSI().get(166);
					double macd = technicalData.getMACD().get(166);
					double signal = technicalData.getSignal().get(166);
					double histogram = technicalData.getHistogram().get(166);
					rsiCell.setCellValue(rsi);
					macdCell.setCellValue(macd);
					signalCell.setCellValue(signal);
					histogramCell.setCellValue(histogram);
					FileOutputStream out = new FileOutputStream(new File("Companies_List.xls"));
					templateWorkBook.write(out);
					out.close();
				}
				else
				{
					continue;
				}
			}
			catch(Exception e)
			{
				System.out.println("Exception2: "+ e);
				webDriverSetup.closeDriver();
				continue;
			}
		}
	}
}