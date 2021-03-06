package stock.businessObjects;

import java.util.ArrayList;

public class ScreenerObject{ 
	  private String prime;

	  public String getPrime() { return this.prime; }

	  public void setPrime(String prime) { this.prime = prime; }
	  
	  private String bse_code;

	  public String getbse_code() { return this.bse_code; }

	  public void setbse_code(String bse_code) { this.bse_code = bse_code; }
	 
	  private NumberSet number_set;

	  public NumberSet getnumber_set() { return this.number_set; }

	  public void setnumber_set(NumberSet number_set) { this.number_set = number_set; }

	  private String short_name;

	  public String getshort_name() { return this.short_name; }

	  public void setshort_name(String short_name) { this.short_name = short_name; }

	  private String nse_code;

	  public String getnse_code() { return this.nse_code; }

	  public void setnse_code(String nse_code) { this.nse_code = nse_code; }

	  private ArrayList<CompanyratingSet> companyrating_set;

	  public ArrayList<CompanyratingSet> getcompanyrating_set() { return this.companyrating_set; }

	  public void setcompanyrating_set(ArrayList<CompanyratingSet> companyrating_set) { this.companyrating_set = companyrating_set; }

	  private ArrayList<AnnualreportSet> annualreport_set;

	  public ArrayList<AnnualreportSet> getannualreport_set() { return this.annualreport_set; }

	  public void setannualreport_set(ArrayList<AnnualreportSet> annualreport_set) { this.annualreport_set = annualreport_set; }

	  private ArrayList<AnnouncementSet> announcement_set;

	  public ArrayList<AnnouncementSet> getannouncement_set() { return this.announcement_set; }

	  public void setannouncement_set(ArrayList<AnnouncementSet> announcement_set) { this.announcement_set = announcement_set; }

	  private WarehouseSet warehouse_set;

	  public WarehouseSet getwarehouse_set() { return this.warehouse_set; }

	  public void setwarehouse_set(WarehouseSet warehouse_set) { this.warehouse_set = warehouse_set; }

	  private int id;

	  public int getId() { return this.id; }

	  public void setId(int id) { this.id = id; }

	  private String name;

	  public String getName() { return this.name; }

	  public void setName(String name) { this.name = name; }
}
