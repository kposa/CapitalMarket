package Stock.Stock.ExcelHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
}

