package Stock.Stock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
		HSSFWorkbook templateWorkBook = null;
		HSSFWorkbook technicalWorkBook = null;
		String securityName = null;
		String templateFilePath = workingDir+"\\templates\\RSI-ATR-MACD-EMA-HV.xls";
		HSSFSheet bestCompanies = excelHelper.getSheet("Companies_List.xls", "Best Stocks");
		for (int index = 1;index<=bestCompanies.getLastRowNum(); index++) 
		{
			try{
				HSSFRow row = bestCompanies.getRow(index);
				securityName = row.getCell(2).toString();
				filePath = workingDir+"\\Best\\"+securityName+".xls";
				excelHelper.copyFile(templateFilePath, filePath);
				List<TradingData> historicalData = webDriverSetup.navigateToHistoricalData(workingDir,securityName);
				technicalWorkBook = new HSSFWorkbook(new FileInputStream(filePath));
				HSSFSheet DataSheet = technicalWorkBook.getSheet("Data");
				excelHelper.updateHistoricalData(technicalWorkBook,DataSheet,historicalData,filePath);
			}
			catch(Exception e){
				System.out.println("Exception2: "+e);
			}
		}
		/*for (int index = 1;index<=bestCompanies.getLastRowNum(); index++) 
		{
			try{
				templateWorkBook = new HSSFWorkbook(new FileInputStream(templateFilePath));
				HSSFRow row = bestCompanies.getRow(index);
				HSSFCell volatility = row.getCell(6);
				volatility.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				String formula = workingDir+"\\Best\\["+securityName+".xls]Data"+"'"+"!$I$168";
				volatility.setCellFormula(formula.toString());
				FileOutputStream out = new FileOutputStream(new File(templateFilePath));
				templateWorkBook.write(out);
			}
			catch(Exception e){
				System.out.println("Exception2: "+e);
			}
		}*/

		webDriverSetup.closeDriver();
	}
}