package stock.ExcelHelper;

import java.io.*;

import java.util.LinkedHashMap;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.*;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;
import stock.BusinessObjects.ScreenerCompanySearchResponse;
import stock.BusinessObjects.ScreenerObject;

public class UpdateScreenerData {
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void getScreenerData() throws ClientProtocolException, IOException, JsonProcessingException {
		String securityName = null;
		String securityCode = null;
		String securityId = null;
		String group = null;
		String faceValue = null;
		String industry = null;
		int currentRow = 1;
		InputStream ExcelFileToRead = new FileInputStream("Companies_List.xls");
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
		HSSFSheet filteredCompanies = wb.getSheet("Best Stocks");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFRow excelRow = null;
		excelRow = filteredCompanies.createRow(0);
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
		for (int index = 1;index<=sheet.getLastRowNum(); index++) {
			try{
				row = sheet.getRow(index);
				securityCode = row.getCell(0).toString();
				securityId = row.getCell(1).toString();
				securityName = row.getCell(2).toString();
				group = row.getCell(3).toString();
				faceValue = row.getCell(4).toString();
				industry = row.getCell(5).toString();
				String[] CompanyNameArray = securityName.split(" ");
				String CompanyName = CompanyNameArray[0];
				for (int i = 1; i < CompanyNameArray.length - 1; i++) {
					CompanyName = CompanyName + "+" + CompanyNameArray[i];
				}
				ObjectMapper mapper = new ObjectMapper();
				CloseableHttpClient httpclient = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet("https://www.screener.in/api/company/search/?q=" + CompanyName);
				CloseableHttpResponse responseCompanyName = httpclient.execute(httpGet);
				String json = EntityUtils.toString(responseCompanyName.getEntity(), "UTF-8");
				ScreenerCompanySearchResponse[] responseContent = mapper.readValue(json, ScreenerCompanySearchResponse[].class);
				String baseUrl = "http://screener.in/api" + responseContent[0].getUrl();
				HttpGet httpGetScreener = new HttpGet(baseUrl);
				CloseableHttpResponse responseScreener = httpclient.execute(httpGetScreener);
				String screenerString = EntityUtils.toString(responseScreener.getEntity(), "UTF-8")
						.replaceAll("2005-03-31", "Mar_2005").replaceAll("2006-03-31", "Mar_2006")
						.replaceAll("2007-03-31", "Mar_2007").replaceAll("2008-03-31", "Mar_2008")
						.replaceAll("2009-03-31", "Mar_2009").replaceAll("2010-03-31", "Mar_2010")
						.replaceAll("2011-03-31", "Mar_2011").replaceAll("2012-03-31", "Mar_2012")
						.replaceAll("2013-03-31", "Mar_2013").replaceAll("2014-03-31", "Mar_2014")
						.replaceAll("2015-03-31", "Mar_2015").replaceAll("2016-03-31", "Mar_2016")
						.replaceAll("2017-03-31", "Mar_2017").replaceAll("2018-03-31", "Mar_2018")
						.replaceAll("2014-12-31", "Dec_2014").replaceAll("2015-06-30", "June_2015")
						.replaceAll("2015-09-30", "Sep_2015").replaceAll("2015-12-31", "Dec_2015")
						.replaceAll("2016-06-30", "June_2016").replaceAll("2016-09-30", "Sep_2016")
						.replaceAll("2016-12-31", "Dec_2016").replaceAll("2017-06-30", "June_2017")
						.replaceAll("2017-09-30", "Sep_2017").replaceAll("2017-12-31", "Dec_2017");

				ScreenerObject screenerObject = mapper.readValue(screenerString, ScreenerObject.class);

				LinkedHashMap<String, Double> val = (LinkedHashMap<String, Double>) screenerObject.getnumber_set()
						.getquarters().get(9).get(1);

				Double TTM = val.get("Dec_2016") + val.get("Mar_2017") + val.get("June_2017") + val.get("Sep_2017");

				LinkedHashMap<String, Double> PreviousYear = (LinkedHashMap<String, Double>) screenerObject
						.getnumber_set().getannual().get(9).get(1);
				Double PreviousYearNetProfit = PreviousYear.get("Mar_2017");
				if (PreviousYearNetProfit == null) {
					PreviousYearNetProfit = PreviousYear.get("Dec_2016");
				}

				if (TTM > PreviousYearNetProfit) {
					LinkedHashMap<String, Double> SalesQuarters = (LinkedHashMap<String, Double>) screenerObject
							.getnumber_set().getquarters().get(0).get(1);
					LinkedHashMap<String, Double> NetProfitQuarters = (LinkedHashMap<String, Double>) screenerObject
							.getnumber_set().getquarters().get(9).get(1);
					Double SalesLatestQuarter = SalesQuarters.get("Sep_2017");
					Double SalesLatestCorrespondingQuarter = SalesQuarters.get("Sep_2016");
					Double NetProfitLatestQuarter = NetProfitQuarters.get("Sep_2017");
					Double NetProfitLatestCorrespondingQuarter = NetProfitQuarters.get("Sep_2016");
					if ((NetProfitLatestQuarter > NetProfitLatestCorrespondingQuarter)
							&& SalesLatestQuarter > SalesLatestCorrespondingQuarter) {
						Double SalesLatestButOneQuarter = SalesQuarters.get("June_2017");
						Double SalesLatestButOneCorrespondingQuarter = SalesQuarters.get("June_2016");
						Double NetProfitLatestButOneQuarter = NetProfitQuarters.get("June_2017");
						Double NetProfitLatestButOneCorrespondingQuarter = NetProfitQuarters.get("June_2016");
						if ((NetProfitLatestButOneQuarter > NetProfitLatestButOneCorrespondingQuarter)
								&& (SalesLatestButOneQuarter > SalesLatestButOneCorrespondingQuarter)) {
							Double SalesLatestButTowQuarter = SalesQuarters.get("Mar_2017");
							Double SalesLatestButTowCorrespondingQuarter = SalesQuarters.get("Mar_2016");
							Double NetProfitLatestButTowQuarter = NetProfitQuarters.get("Mar_2017");
							Double NetProfitLatestButTowCorrespondingQuarter = NetProfitQuarters.get("Mar_2016");
							if ((NetProfitLatestButTowQuarter > NetProfitLatestButTowCorrespondingQuarter)
									&& (SalesLatestButTowQuarter > SalesLatestButTowCorrespondingQuarter)) {
								Double SalesLatestButThreeQuarter = SalesQuarters.get("Dec_2016");
								Double SalesLatestButThreeCorrespondingQuarter = SalesQuarters.get("Dec_2015");
								Double NetProfitLatestButThreeQuarter = NetProfitQuarters.get("Dec_2016");
								Double NetProfitLatestButThreeCorrespondingQuarter = NetProfitQuarters.get("Dec_2015");
								if ((NetProfitLatestButThreeQuarter > NetProfitLatestButThreeCorrespondingQuarter)
										&& (SalesLatestButThreeQuarter > SalesLatestButThreeCorrespondingQuarter)) {
									if ((NetProfitLatestQuarter > NetProfitLatestButOneQuarter)
											&& (SalesLatestQuarter > SalesLatestButOneQuarter)) {
										excelRow = filteredCompanies.createRow(currentRow);
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
										FileOutputStream out = new FileOutputStream(new File("Companies_List.xls"));
										wb.write(out);
										currentRow++;
										System.out.println("Success: "+securityCode+"-"+ securityName);

									}
								}
							}
						}
					}
				}
			}
			catch(Exception e){
				continue;
			}
		}
	}
}