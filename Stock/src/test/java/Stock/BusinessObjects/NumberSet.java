package stock.businessObjects;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NumberSet{
	  private ArrayList<ArrayList<Object>> balancesheet;

	  public ArrayList<ArrayList<Object>> getbalancesheet() { return this.balancesheet; }

	  public void setbalancesheet(ArrayList<ArrayList<Object>> balancesheet) { this.balancesheet = balancesheet; }

	  private ArrayList<ArrayList<Object>> annual;

	  public ArrayList<ArrayList<Object>> getannual() { return this.annual; }

	  public void setannual(ArrayList<ArrayList<Object>> annual) { this.annual = annual; }

	  private ArrayList<ArrayList<Object>> cashflow;

	  public ArrayList<ArrayList<Object>> getcashflow() { return this.cashflow; }

	  public void setcashflow(ArrayList<ArrayList<Object>> cashflow) { this.cashflow = cashflow; }

	  private ArrayList<ArrayList<Object>> quarters;

	  public ArrayList<ArrayList<Object>> getquarters() { return this.quarters; }

	  public void setquarters(ArrayList<ArrayList<Object>> quarters) { this.quarters = quarters; }
}
