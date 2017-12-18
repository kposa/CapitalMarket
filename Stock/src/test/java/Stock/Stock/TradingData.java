package Stock.Stock;

public class TradingData {

	private String date;
	private double open;
	private double high;
	private double low;
	private double close;
	private String volume;
	private double adjClose;
	
	TradingData(String Date,double Open,double High,double Low,double Close,String Volume,double AdjClose)
	{
		setDate(Date);
		setOpen(Open);
		setHigh(High);
		setLow(Low);
		setClose(Close);
		setVolume(Volume);
		setAdjClose(AdjClose);
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public double getAdjClose() {
		return adjClose;
	}

	public void setAdjClose(double adjClose) {
		this.adjClose = adjClose;
	}
}
