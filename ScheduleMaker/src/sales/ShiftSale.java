package sales;

import scheduling.Day;

public class ShiftSale implements Comparable<ShiftSale>
{
  public Day day;
  public char shift;
  public int sale;
  
  public ShiftSale(Day day, char shift, int sale)
  {
    this.day = day;
    this.sale = sale;
    this.shift = shift;
  }

  @Override
  public int compareTo(ShiftSale ss)
  {
    if(sale < ss.sale)
    {
      return -1;
    }
    else if(sale > ss.sale)
    {
      return 1;
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
