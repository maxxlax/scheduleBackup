package sales;

import java.util.ArrayList;
import java.util.Collections;

import scheduling.Day;
import scheduling.ShiftPeriod;

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
          ds.setAmSales(new ShiftSale(day, shift, num));
        }
        else if (shift == 'p')
        {
          ds.setPmSales(new ShiftSale(day, shift, num));
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

  public ArrayList<ShiftPeriod> createShiftPriorityBasedOnSales()
  {
    ArrayList<ShiftSale> priSales = new ArrayList<ShiftSale>();
    for (DaySales ds : sales)
    {
      priSales.add(ds.getAmSales());
      priSales.add(ds.getPmSales());
    }
    Collections.sort(priSales);
    
    ArrayList<ShiftPeriod> shiftTimes = new ArrayList<ShiftPeriod>();
    for(ShiftSale ss: priSales)
    {
      shiftTimes.add(new ShiftPeriod(ss.getDay(), ss.getShift()));
    }
    return shiftTimes;
  }

  public String toSaveString()
  {
    String str = "";
    for(DaySales ds: sales)
    {
      str += ds.toSaveString() + "\n";
    }
    return str;
  }
}
