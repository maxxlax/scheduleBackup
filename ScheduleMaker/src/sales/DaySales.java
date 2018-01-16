package sales;

import scheduling.Day;

public class DaySales
{
	private ShiftSale amShiftSale, pmShiftSale;
	private Day day;

	public DaySales(int amSales, int pmSales, Day day)
	{
		super();
		this.amShiftSale = new ShiftSale(day, 'a', amSales);
		this.pmShiftSale = new ShiftSale(day, 'p', pmSales);
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

	public ShiftSale getAmSales()
	{
		return amShiftSale;
	}

	public void setAmSales(ShiftSale amShiftSale)
	{
		this.amShiftSale = amShiftSale;
	}

	public ShiftSale getPmSales()
	{
		return pmShiftSale;
	}

	public void setPmSales(ShiftSale pmShiftSale)
	{
		this.pmShiftSale = pmShiftSale;
	}

	public String toString()
	{
		return "\t" + day + "\n\t AM: " + amShiftSale.sale + "\n\t PM: " + pmShiftSale.sale + "\n\t\tTotal: " + (amShiftSale.sale + pmShiftSale.sale)
				+ "\n";
	}

  public String toSaveString()
  {
    return day  + "|" + amShiftSale.sale + "|" + pmShiftSale.sale;
  }
}
