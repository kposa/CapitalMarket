package stock.technical;

import java.util.ArrayList;
import java.util.List;

import stock.util.TradingData;

public class TechnicalData {

	private List<Double> change = new ArrayList<Double>();
	private List<Double> gain = new ArrayList<Double>();
	private List<Double> loss = new ArrayList<Double>();
	private List<Double> avGain = new ArrayList<Double>();
	private List<Double> avLoss = new ArrayList<Double>();
	private List<Double> rs = new ArrayList<Double>();
	private List<Double> rsi = new ArrayList<Double>();
	private List<Double> EMA12Day= new ArrayList<Double>();
	private List<Double> EMA26Day= new ArrayList<Double>();
	private List<Double> MACD= new ArrayList<Double>();
	private List<Double> Signal= new ArrayList<Double>();
	private List<Double> Histogram= new ArrayList<Double>();
	private List<Double> MiddleBand= new ArrayList<Double>();
	private List<Double> StandardDeviation= new ArrayList<Double>();
	private List<Double> UpperBand= new ArrayList<Double>();
	private List<Double> LowerBand= new ArrayList<Double>();
	private List<Double> Bandwidth= new ArrayList<Double>();

	public void calculateTechnicalData(List<TradingData> historicalData)
	{
		this.change.add(0.0);
		this.gain.add(0.0);
		this.loss.add(0.0);
		for(int iteration=166;iteration>=153;iteration--)
		{
			this.avGain.add(0.0);
		}
		for(int iteration=166;iteration>=153;iteration--)
		{
			this.avLoss.add(0.0);
		}

		for(int iteration=166;iteration>=153;iteration--)
		{
			this.rs.add(0.0);
		}

		for(int iteration=166;iteration>=153;iteration--)
		{
			this.rsi.add(0.0);
		}

		for(int iteration=166;iteration>=156;iteration--)
		{
			this.EMA12Day.add(0.0);
		}

		for(int iteration=166;iteration>=142;iteration--)
		{
			this.EMA26Day.add(0.0);
		}

		for(int iteration=166;iteration>=142;iteration--)
		{
			this.MACD.add(0.0);
		}

		for(int iteration=166;iteration>=134;iteration--)
		{
			this.Signal.add(0.0);
		}

		for(int iteration=166;iteration>=134;iteration--)
		{
			this.Histogram.add(0.0);
		}

		for(int iteration=166;iteration>=147;iteration--)
		{
			this.MiddleBand.add(0.0);
		}

		for(int i=166;i>=1;i--)
		{
			this.change.add(historicalData.get(i-1).getClose()-historicalData.get(i).getClose());
		}

		for(int i=1;i<=166;i++)
		{
			if(this.change.get(i)>0)
			{
				this.gain.add(change.get(i));
			}
			else
			{
				this.gain.add(0.0);
			}
		}

		for(int i=1;i<=166;i++)
		{
			if(this.change.get(i)<0)
			{
				double value = (Double)Math.abs(change.get(i));
				this.loss.add(value);
			}
			else
			{
				this.loss.add(0.0);
			}
		}
		double gainVal = 0.0;
		for(int i=1;i<=14;i++)
		{
			gainVal+=this.gain.get(i);
		}
		this.avGain.add(gainVal/14);

		for(int i=15;i<=this.gain.size()-1;i++)
		{
			double val = ((this.avGain.get(i-1)*13)+this.gain.get(i))/14;
			this.avGain.add(val);
		}

		double lossVal = 0.0;
		for(int i=1;i<=14;i++)
		{
			lossVal+=this.loss.get(i);
		}
		this.avLoss.add(lossVal/14);

		for(int i=15;i<=this.loss.size()-1;i++)
		{
			double val = ((this.avLoss.get(i-1)*13)+this.loss.get(i))/14;
			this.avLoss.add(val);
		}

		for(int i=14;i<=this.avGain.size()-1;i++)
		{
			this.rs.add(this.avGain.get(i)/this.avLoss.get(i));
		}

		for(int i=14;i<=this.rs.size()-1;i++)
		{
			double val = 0.0;
			if(this.avLoss.get(i)==0)
			{
				val = 100.0;
			}
			else
			{
				val = 100-(100/(1+this.rs.get(i)));
			}
			this.rsi.add(val);
		}

		double ema12Days = 0.0;
		for(int i=166;i>=155;i--)
		{
			ema12Days+=historicalData.get(i).getClose();
		}
		this.EMA12Day.add(ema12Days/12);

		for(int i=154;i>=0;i--)
		{
			double a1 = (double)2/13;
			double a2 = (double)(1-a1);
			double val = (historicalData.get(i).getClose()*a1)+(EMA12Day.get(165-i)*a2);
			this.EMA12Day.add(val);
		}

		double ema26Days = 0.0;
		for(int i=166;i>=141;i--)
		{
			ema26Days+=historicalData.get(i).getClose();
		}
		this.EMA26Day.add(ema26Days/26);

		for(int i=140;i>=0;i--)
		{
			double a1 = (double)2/27;
			double a2 = (double)(1-a1);
			double val = (historicalData.get(i).getClose()*a1)+(EMA26Day.get(165-i)*a2);
			this.EMA26Day.add(val);
		}

		for(int i=25;i<=EMA12Day.size()-1;i++)
		{
			this.MACD.add(this.EMA12Day.get(i)-this.EMA26Day.get(i));
		}

		double macdValue = 0.0;
		for(int i=25;i<=33;i++)
		{
			macdValue+=this.MACD.get(i);
		}
		this.Signal.add(macdValue/9);

		for(int i=34;i<=MACD.size()-1;i++)
		{
			double a1 = (double)2/10;
			double a2 = (double)(1-a1);
			double val = (this.MACD.get(i)*a1)+(this.Signal.get(i-1)*a2);
			this.Signal.add(val);
		}

		for(int i=33;i<=Signal.size()-1;i++)
		{
			this.Histogram.add(this.MACD.get(i)-this.Signal.get(i));
		}
		for(int i=147;i>=1;i--)
		{
			double middleBandValue = 0.0;
			for(int j=i+19;j>=i;j--)
			{
				middleBandValue+=historicalData.get(j).getClose();
			}
			this.MiddleBand.add(middleBandValue);
		}
	}

	public List<Double> getRSI()
	{
		return this.rsi;
	}

	public List<Double> getMACD()
	{
		return this.MACD;
	}

	public List<Double> getSignal()
	{
		return this.Signal;
	}

	public List<Double> getHistogram()
	{
		return this.Histogram;
	}

}
