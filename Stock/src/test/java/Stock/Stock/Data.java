package Stock.Stock;

import java.util.ArrayList;

public class Data {
	private String suggestionTitleAccessor;

	  public String getSuggestionTitleAccessor() { return this.suggestionTitleAccessor; }

	  public void setSuggestionTitleAccessor(String suggestionTitleAccessor) { this.suggestionTitleAccessor = suggestionTitleAccessor; }

	  private ArrayList<String> suggestionMeta;

	  public ArrayList<String> getSuggestionMeta() { return this.suggestionMeta; }

	  public void setSuggestionMeta(ArrayList<String> suggestionMeta) { this.suggestionMeta = suggestionMeta; }

	  private boolean hiConf;

	  public boolean getHiConf() { return this.hiConf; }

	  public void setHiConf(boolean hiConf) { this.hiConf = hiConf; }

	  private ArrayList<Item> items;

	  public ArrayList<Item> getItems() { return this.items; }

	  public void setItems(ArrayList<Item> items) { this.items = items; }
}
