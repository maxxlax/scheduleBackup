package sales;

import java.util.ArrayList;

import scheduling.Day;

public class WeeklySales
{
	private ArrayList<DaySales> sales;

	public WeeklySales()
	{
		super();
		sales = new ArrayList<DaySales>();
		sales.add(new DaySales(-1, -1, Day.Sunday));
		sales.add(new DaySales(-1, -1, Day.Monday));
		sales.add(new DaySales(-1, -1, Day.Tuesday));
		sales.add(new DaySales(-1, -1, Day.Wednesday));
		sales.add(new DaySales(-1, -1, Day.Thursday));
		sales.add(new DaySales(-1, -1, Day.Friday));
		sales.add(new DaySales(-1, -1, Day.Saturday));
	}

	public WeeklySales(ArrayList<DaySales> sales)
	{
		super();
		this.sales = sales;
	}

	public ArrayList<DaySales> getSales()
	{
		return sales;
	}

	public void setSales(ArrayList<DaySales> sales)
	{
		this.sales = sales;
	}

	public void setSales(Day day, char shift, int num)
	{
		for (DaySales ds : sales)
		{
			if (ds.getDay().equals(day))
			{
				if (shift == 'a')
				{
					ds.setAmSales(num);
				}
				else if (shift == 'p')
				{
					ds.setPmSales(num);
				}
			}
		}
	}

	public void setSales(DaySales dayS)
	{
		for (DaySales ds : sales)
		{
			if (ds.getDay().equals(dayS.getDay()))
			{
				sales.set(sales.indexOf(ds), dayS);
			}
		}
	}

	public String toString()
	{
		String s = "Weekly Sales\n";
		for (DaySales ds : sales)
		{
			s += ds.toString() + "\n";
		}
		return s;
	}

	public ArrayList<Day> createPrioritizedSalesWeek()
	{
		ArrayList<Day> week = new ArrayList<Day>();
		ArrayList<DaySales> copyOfSales = new ArrayList<DaySales>(sales);
		for (int ii = 0; ii < 7; ii++)
		{
			int currentSales = 0;
			Day currentDay = Day.Sunday;
			int currentDSIndex = 0;
			int delDSIndex = 0;
			for (DaySales ds : copyOfSales)
			{
				if (ds.getAmSales() + ds.getPmSales() > currentSales)
				{
					currentSales = ds.getAmSales() + ds.getPmSales();
					currentDay = ds.getDay();
					delDSIndex = currentDSIndex;
				}
				currentDSIndex++;
			}
			week.add(currentDay);
			copyOfSales.remove(delDSIndex);
		}
		return week;
	}
}
