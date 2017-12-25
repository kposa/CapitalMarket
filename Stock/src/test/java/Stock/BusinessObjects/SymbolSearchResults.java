package Stock.BusinessObjects;

public class SymbolSearchResults {
	private SymbolResultsData data;

	  public SymbolResultsData getData() { return this.data; }

	  public void setData(SymbolResultsData data) { this.data = data; }

	  private Meta meta;

	  public Meta getMeta() { return this.meta; }

	  public void setMeta(Meta meta) { this.meta = meta; }
}
