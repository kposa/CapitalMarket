package Stock.BusinessObjects;

import java.util.ArrayList;

public class Analysis {
	private ArrayList<Object> remarks;

	  public ArrayList<Object> getRemarks() { return this.remarks; }

	  public void setRemarks(ArrayList<Object> remarks) { this.remarks = remarks; }

	  private ArrayList<Object> bad;

	  public ArrayList<Object> getBad() { return this.bad; }

	  public void setBad(ArrayList<Object> bad) { this.bad = bad; }

	  private ArrayList<String> good;

	  public ArrayList<String> getGood() { return this.good; }

	  public void setGood(ArrayList<String> good) { this.good = good; }
}
