package sales;

import scheduling.Day;
import scheduling.ShiftPeriod;

public class ShiftSale extends ShiftPeriod implements Comparable<ShiftSale>
{
  private int sale;
  
  public ShiftSale(Day day, char shift, int sale)
  {
    super(day, shift);
    this.sale = sale;
  }

  public int getSale()
  {
    return sale;
  }

  public void setSale(int sale)
  {
    this.sale = sale;
  }

  @Override
  public int compareTo(ShiftSale ss)
  {
    if(sale < ss.sale)
    {
      return 1;
    }
    else if(sale > ss.sale)
    {
      return -1;
    }
    return 0;
  }
  
  @Override
  public String toString()
  {
    return sale + "";
  }
  
  public String toString(boolean flag)
  {
    if(flag)
    {
      
    }
    return "Shift Sale-- Day: " + day + shift + ": " + sale;
  }
}
