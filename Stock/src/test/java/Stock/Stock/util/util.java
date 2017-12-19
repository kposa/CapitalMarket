package Stock.Stock.util;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import Stock.Stock.Item;
import Stock.Stock.RootObject;

public class util {
	public static String getSymbol(String companyName) throws ClientProtocolException, IOException
	{
		String symbol = "";
		String[] CompanyNameArray = companyName.split(" ");
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
		for(Item item:items)
		{
			if(item.getSymbol().indexOf(".BO")!=-1)
			{
				symbol = item.getSymbol();
				break;
			}
		}
		return symbol;
	}
}
