package Stock.ExcelHelper;

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

import Stock.util.TradingData;

public class excelHelper {
	public static HSSFSheet getSheet(String ExcelFile,String SheetName) throws IOException
	{
		InputStream excelFileToRead = new FileInputStream(ExcelFile);
		HSSFWorkbook workBook = new HSSFWorkbook(excelFileToRead);
		return workBook.getSheet(SheetName);
	}

	public static void copyFile(String source, String destination) throws IOException
	{
		InputStream is = null;
		OutputStream os = null;
		try
		{
			File template = new File(source);
			File historicalData = new File(destination);
			is = new FileInputStream(template);
			os = new FileOutputStream(historicalData);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		}catch(Exception e)
		{
			System.out.println("Exception: "+e);
		}
		finally {
			if(is!=null)
			{
				is.close();
			}
			if(os!=null)
			{
				os.close();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void updateHistoricalData(HSSFWorkbook TemplateWorkBook,HSSFSheet DataSheet,List<TradingData> historicalData,String FilePath) throws IOException
	{
		int currentRow = 0;
		for(int excelRowNum=167;excelRowNum>=1;excelRowNum--)
		{
			InputStream TempalteFile = null;
			FileOutputStream out = null;
			try{
				TempalteFile = new FileInputStream(FilePath);
				HSSFRow excelRow = DataSheet.getRow(excelRowNum);
				HSSFCell Date = excelRow.getCell(0);
				Date.setCellValue(historicalData.get(currentRow).getDate());
				HSSFCell Open = excelRow.getCell(1);
				Open.setCellValue(historicalData.get(currentRow).getOpen());
				HSSFCell High = excelRow.getCell(2);
				High.setCellValue(historicalData.get(currentRow).getHigh());
				HSSFCell Low = excelRow.getCell(3);
				Low.setCellValue(historicalData.get(currentRow).getLow());
				HSSFCell Close = excelRow.getCell(4);
				Close.setCellValue(historicalData.get(currentRow).getClose());
				HSSFCell Volume = excelRow.getCell(5);
				Volume.setCellValue(historicalData.get(currentRow).getVolume());
				HSSFCell AdjClose = excelRow.getCell(6);
				AdjClose.setCellValue(historicalData.get(currentRow).getAdjClose());
				out = new FileOutputStream(new File(FilePath));
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
}

