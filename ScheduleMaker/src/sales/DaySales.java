package sales;

import scheduling.Day;

public class DaySales
{
	private int amSales;
	private int pmSales;
	private Day day;

	public DaySales(int amSales, int pmSales, Day day)
	{
		super();
		this.amSales = amSales;
		this.pmSales = pmSales;
		this.day = day;
	}

	public Day getDay()
	{
		return day;
	}

	public void setDay(Day day)
	{
		this.day = day;
	}

	public int getAmSales()
	{
		return amSales;
	}

	public void setAmSales(int amSales)
	{
		this.amSales = amSales;
	}

	public int getPmSales()
	{
		return pmSales;
	}

	public void setPmSales(int pmSales)
	{
		this.pmSales = pmSales;
	}

	public String toString()
	{
		return "\t" + day + "\n\t AM: " + amSales + "\n\t PM: " + pmSales + "\n\t\tTotal: " + (amSales + pmSales)
				+ "\n";
	}
}
