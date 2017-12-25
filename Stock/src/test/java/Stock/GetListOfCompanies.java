package stock;

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class GetListOfCompanies {
  @SuppressWarnings("deprecation")
@Test
  public void getListOfCompanies() {
	  String workingDir = System.getProperty("user.dir");
	  System.setProperty("webdriver.chrome.driver",workingDir+"\\chromedriver_2.33.exe");
	  WebDriver driver = new ChromeDriver();
	  driver.manage().window().maximize();
      String baseUrl = "http://www.bseindia.com/corporates/List_Scrips.aspx?expandable=1";
      driver.get(baseUrl);
      Select segment = new Select(driver.findElement(By.id("ctl00_ContentPlaceHolder1_ddSegment")));
      segment.selectByVisibleText("Equity");
      
      Select status = new Select(driver.findElement(By.id("ctl00_ContentPlaceHolder1_ddlStatus")));
      status.selectByVisibleText("Active");
      
      driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnSubmit")).click();
      
      String str = driver.findElement(By.id("ctl00_ContentPlaceHolder1_trData")).getText();
      int numberOfPages = Integer.parseInt(str.split(" ")[str.split(" ").length-1]);
      HSSFWorkbook workbook = new HSSFWorkbook();
      HSSFSheet sheet = workbook.createSheet("Companies List");
      HSSFRow excelRow = null;
      FileOutputStream out = null;
      try {
    	 out = new FileOutputStream(new File("Companies_List.xls"));
	  } catch (FileNotFoundException e) {
  		e.printStackTrace();
	  }
      int excleCurrentRow = 0;
      for(int page=1;page<=numberOfPages;page++)
      {
    	  if(page!=1)
    	  {
    		  if(page==11)
    		  {
    			  driver.findElement(By.linkText("...")).click();
    		  }
    		  else if(page==21||page==31||page==41||page==51||page==61||page==71||page==81||page==91||page==101||page==111||page==121||page==131||page==141||page==151||page==161||page==171)
    		  {
    			  driver.findElements(By.linkText("...")).get(1).click();
    		  }
    		  else
    		  {
    			  driver.findElement(By.linkText(Integer.toString(page))).click();
    		  }
    	  }
    	  else
    	  {
    		  excelRow = sheet.createRow(excleCurrentRow);
    	      HSSFCell securityCodeCol = excelRow.createCell(0);
    	      securityCodeCol.setCellValue("Security Code");
    	      HSSFCell securityIdCol = excelRow.createCell(1);
    	      securityIdCol.setCellValue("Security Id");
    	      HSSFCell securityNameCol = excelRow.createCell(2);
    	      securityNameCol.setCellValue("Security Name");
    	      HSSFCell groupCol = excelRow.createCell(3);
    	      groupCol.setCellValue("Group");
    	      HSSFCell faceValueCol = excelRow.createCell(4);
    	      faceValueCol.setCellValue("Face Value");
    	      HSSFCell IndustryCol = excelRow.createCell(5);
    	      IndustryCol.setCellValue("Industry");
    	      excleCurrentRow++;
    	  }
	      WebElement htmltable=driver.findElement(By.id("ctl00_ContentPlaceHolder1_gvData"));
	      List<WebElement> rows=htmltable.findElements(By.tagName("tr"));
	      
	      System.out.println("Rows"+rows.size());
	      
	      for(int row=3;row<rows.size()-2;row++)
	      {
	    	  String securityCode=rows.get(row).findElements(By.tagName("td")).get(0).getText();
	    	  String securityId=rows.get(row).findElements(By.tagName("td")).get(1).getText();
	    	  String securityName=rows.get(row).findElements(By.tagName("td")).get(2).getText();
	    	  String group=rows.get(row).findElements(By.tagName("td")).get(4).getText();
	    	  String faceValue=rows.get(row).findElements(By.tagName("td")).get(5).getText();
	    	  String industry=rows.get(row).findElements(By.tagName("td")).get(7).getText();
	    	  excelRow = sheet.createRow(excleCurrentRow);
	          HSSFCell securityCodeColVal = excelRow.createCell(0);
	          securityCodeColVal.setCellValue(securityCode);
	          HSSFCell securityIdColVal = excelRow.createCell(1);
	          securityIdColVal.setCellValue(securityId);
	          HSSFCell securityNameColVal = excelRow.createCell(2);
	          securityNameColVal.setCellValue(securityName);
	          HSSFCell groupColVal = excelRow.createCell(3);
	          groupColVal.setCellValue(group);
	          HSSFCell faceValueColVal = excelRow.createCell(4);
	          faceValueColVal.setCellValue(faceValue);
	          HSSFCell IndustryColVal = excelRow.createCell(5);
	          IndustryColVal.setCellValue(industry);
	          excleCurrentRow++;
	      }
      }
      
      try {
    		workbook.write(out);
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
        try {
  		out.close();
  	} catch (IOException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
      driver.close();
  }
}
