package stock.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import stock.businessObjects.*;
import stock.webDriverSetup.WebDriverSetup;

public class Util {
	public static String getSymbol(String companyName) throws ClientProtocolException, IOException
	{
		String symbol = "";
		String[] CompanyNameArray = companyName.split(" ");
		String CompanyName = CompanyNameArray[0];
		for (int i = 1; i < CompanyNameArray.length - 1; i++) {
			if(CompanyNameArray[i].hashCode() =="&".hashCode())
			{
				CompanyName = CompanyName + "%20%26";	
			}
			else
			{
				CompanyName = CompanyName + "%20" + CompanyNameArray[i];
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://in.finance.yahoo.com/_finance_doubledown/api/resource/searchassist;searchTerm="+CompanyName+"?bkt=finance-IN-en-IN-def&device=desktop&feature=canvassOffnet%2CnewContentAttribution%2CrelatedVideoFeature%2CvideoNativePlaylist&intl=in&lang=en-IN&partner=none&prid=70uf22ld1j13t&region=IN&site=finance&tz=Asia%2FKolkata&ver=0.102.883&returnMeta=true");
		CloseableHttpResponse responseCompanyName = httpclient.execute(httpGet);
		String json = EntityUtils.toString(responseCompanyName.getEntity(), "UTF-8");
		SymbolSearchResults rootObject = mapper.readValue(json, SymbolSearchResults.class);
		ArrayList<SymbolResultItem> items = rootObject.getData().getItems();
		for(SymbolResultItem item:items)
		{
			if(item.getSymbol().startsWith(String.valueOf(companyName.charAt(0))))
			{
				if(item.getSymbol().indexOf(".BO")!=-1);
				{
					symbol = item.getSymbol();
					break;
				}
			}
		}
		if(symbol=="")
		{
			CompanyName = CompanyNameArray[0];
			for (int i = 1; i < CompanyNameArray.length - 2; i++) {
				if(CompanyNameArray[i].hashCode() =="&".hashCode())
				{
					CompanyName = CompanyName + "%20%26";	
				}
				else
				{
					CompanyName = CompanyName + "%20" + CompanyNameArray[i];
				}
			}
			mapper = new ObjectMapper();
			httpclient = HttpClients.createDefault();
			httpGet = new HttpGet("https://in.finance.yahoo.com/_finance_doubledown/api/resource/searchassist;searchTerm="+CompanyName+"?bkt=finance-IN-en-IN-def&device=desktop&feature=canvassOffnet%2CnewContentAttribution%2CrelatedVideoFeature%2CvideoNativePlaylist&intl=in&lang=en-IN&partner=none&prid=70uf22ld1j13t&region=IN&site=finance&tz=Asia%2FKolkata&ver=0.102.883&returnMeta=true");
			responseCompanyName = httpclient.execute(httpGet);
			json = EntityUtils.toString(responseCompanyName.getEntity(), "UTF-8");
			rootObject = mapper.readValue(json, SymbolSearchResults.class);
			items = rootObject.getData().getItems();
			for(SymbolResultItem item:items)
			{
				if(item.getSymbol().startsWith(String.valueOf(companyName.charAt(0))))
				{
					if(item.getSymbol().indexOf(".BO")!=-1);
					{
						symbol = item.getSymbol();
						break;
					}
				}
			}
		}
		return symbol;
	}

	public static List<TradingData> getHistoricalData(WebElement htmltable) throws InterruptedException
	{
		List<WebElement> rows=htmltable.findElements(By.tagName("tr"));
		WebDriverSetup.getFocus(rows.get(rows.size()-2));

		List<WebElement> rowsNew=htmltable.findElements(By.tagName("tr"));
		List<TradingData> historicalData = new ArrayList<TradingData>();
		if(rowsNew.size()>=167)
		{
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
							historicalData.add(new TradingData(date,open,high,low,close,volume,adjClose));
						}
						if(historicalData.size()>=167){
							break;
						}
					}

				}
				catch(Exception e){
					continue;
				}

			}
		}
		return historicalData;
	}

	public static strictfp double populationStandardDeviation(List<Double> values) {
		double mean = mean(values);
		double n = values.size();
		double dv = 0;
		for (double d : values) {
			double dm = d - mean;
			dv += dm * dm;
		}
		return Math.sqrt(dv / n);
	}

	private static strictfp double mean(List<Double> values) {
		return sum(values) / values.size();
	}

	private static strictfp double sum(List<Double> values) {
		if (values == null || values.size() == 0) {
			throw new IllegalArgumentException("The data array either is null or does not contain any data.");
		}
		else {
			double sum = 0;
			for (int i = 0; i < values.size(); i++) {
				sum += values.get(i);
			}
			return sum;
		}
	}
}
