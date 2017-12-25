package stock.businessObjects;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WarehouseSet{ 
	  private String high_price;

	  public String gethigh_price() { return this.high_price; }

	  public void sethigh_price(String high_price) { this.high_price = high_price; }

	  private double low_price;

	  public double getlow_price() { return this.low_price; }

	  public void setlow_price(double low_price) { this.low_price = low_price; }

	  private double sales_growth;

	  public double getsales_growth() { return this.sales_growth; }

	  public void setsales_growth(double sales_growth) { this.sales_growth = sales_growth; }

	  private double current_price;

	  public double getcurrent_price() { return this.current_price; }

	  public void setcurrent_pricee(double current_price) { this.current_price = current_price; }

	  private double dividend_yield;

	  public double getdividend_yield() { return this.dividend_yield; }

	  public void setdividend_yield(double dividend_yield) { this.dividend_yield = dividend_yield; }

	  private double face_value;

	  public double getface_value() { return this.face_value; }

	  public void setface_value(double face_value) { this.face_value = face_value; }

	  private int id;

	  public int getid() { return this.id; }

	  public void setid(int id) { this.id = id; }

	  private double sales_growth_3years;

	  public double getsales_growth_3years() { return this.sales_growth_3years; }

	  public void setsales_growth_3years(double sales_growth_3years) { this.sales_growth_3years = sales_growth_3years; }

	  private double profit_growth_5years;

	  public double getprofit_growth_5years() { return this.profit_growth_5years; }

	  public void setprofit_growth_5years(double profit_growth_5years) { this.profit_growth_5years = profit_growth_5years; }

	  private double average_return_on_equity_3years;

	  public double getaverage_return_on_equity_3years() { return this.average_return_on_equity_3years; }

	  public void setaverage_return_on_equity_3years(double average_return_on_equity_3years) { this.average_return_on_equity_3years = average_return_on_equity_3years; }

	  private double book_value;

	  public double getbook_value() { return this.book_value; }

	  public void setbook_value(double book_value) { this.book_value = book_value; }

	  private String status;

	  public String getstatus() { return this.status; }

	  public void setstatus(String status) { this.status = status; }

	  private Object pair_url;

	  public Object getpair_url() { return this.pair_url; }

	  public void setpair_url(Object pair_url) { this.pair_url = pair_url; }

	  private double sales_growth_10years;

	  public double getsales_growth_10yearss() { return this.sales_growth_10years; }

	  public void setsales_growth_10years(double sales_growth_10years) { this.sales_growth_10years = sales_growth_10years; }

	  private double average_return_on_equity_10years;

	  public double getaverage_return_on_equity_10years() { return this.average_return_on_equity_10years; }

	  public void setaverage_return_on_equity_10years(double average_return_on_equity_10years) { this.average_return_on_equity_10years = average_return_on_equity_10years; }

	  private double profit_growth;

	  public double getprofit_growth() { return this.profit_growth; }

	  public void setprofit_growth(double profit_growth) { this.profit_growth = profit_growth; }

	  private double market_capitalization;

	  public double getmarket_capitalization() { return this.market_capitalization; }

	  public void setmarket_capitalization(double market_capitalization) { this.market_capitalization = market_capitalization; }

	  private double profit_growth_10years;

	  public double getprofit_growth_10years() { return this.profit_growth_10years; }

	  public void setprofit_growth_10years(double profit_growth_10years) { this.profit_growth_10years = profit_growth_10years; }

	  private double price_to_earning;

	  public double getprice_to_earning() { return this.price_to_earning; }

	  public void setprice_to_earning(double price_to_earning) { this.price_to_earning = price_to_earning; }

	  private String industry;

	  public String getindustry() { return this.industry; }

	  public void setindustry(String industry) { this.industry = industry; }

	  private Analysis analysis;

	  public Analysis getanalysis() { return this.analysis; }

	  public void setanalysis(Analysis analysis) { this.analysis = analysis; }

	  private String result_type;

	  public String getresult_type() { return this.result_type; }

	  public void setresult_type(String result_type) { this.result_type = result_type; }

	  private double profit_growth_3years;

	  public double getprofit_growth_3years() { return this.profit_growth_3years; }

	  public void setprofit_growth_3years(double profit_growth_3years) { this.profit_growth_3years = profit_growth_3years; }

	  private double sales_growth_5years;

	  public double getsales_growth_5years() { return this.sales_growth_5years; }

	  public void setsales_growth_5yearss(double sales_growth_5years) { this.sales_growth_5years = sales_growth_5years; }

	  private double return_on_equity;

	  public double getreturn_on_equity() { return this.return_on_equity; }

	  public void setreturn_on_equityy(double return_on_equity) { this.return_on_equity = return_on_equity; }

	  private double average_return_on_equity_5years;

	  public double getaverage_return_on_equity_5years() { return this.average_return_on_equity_5years; }

	  public void setaverage_return_on_equity_5years(double average_return_on_equity_5years) { this.average_return_on_equity_5years = average_return_on_equity_5years; }
	}
