package Stock.Stock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.annotations.Test;
import Stock.Stock.ExcelHelper.excelHelper;
import Stock.Stock.util.TradingData;
import Stock.Stock.webDriverSetup.webDriverSetup;

public class GetHistoricalData {
	@Test
	public void getHistoricalData() throws IOException, InterruptedException {
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

				//List<TradingData> historicalData = webDriverSetup.navigateToHistoricalData(workingDir,securityName);
				//webDriverSetup.closeDriver();
				//if(historicalData.size()!=0)
				//{
					//excelHelper.copyFile(templateFilePath, filePath);
					//technicalWorkBook = new HSSFWorkbook(new FileInputStream(filePath));
					//HSSFSheet DataSheet = technicalWorkBook.getSheet("Data");
					//excelHelper.updateHistoricalData(technicalWorkBook,DataSheet,historicalData,filePath);
				
				
					HSSFWorkbook templateWorkBook = new HSSFWorkbook(new FileInputStream("Companies_List.xls"));
					bestCompanies = templateWorkBook.getSheet("Best Stocks");
					HSSFRow excelRow = bestCompanies.getRow(index);
					HSSFCell volatility = excelRow.getCell(6);
					volatility.setCellType(HSSFCell.CELL_TYPE_FORMULA);
					String formula = "\'C:\\Users\\ewr5tb5\\Documents\\Workspace\\CapitalMarket\\Stock\\Best\\["+securityName+".xls]Data'!$I$168";
					volatility.setCellFormula(formula);
					FileOutputStream out = new FileOutputStream(new File("Companies_List.xls"));
					templateWorkBook.write(out);
					out.close();
					
					
				//}
				//else
				//{
				//	continue;
				//}
			}
			catch(Exception e)
			{
				System.out.println("Exception2: "+e);
			}
		}
	}
}