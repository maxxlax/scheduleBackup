package scheduling;

public class ShiftPeriod
{
  protected Day day;
  protected char shift;
  
  public ShiftPeriod(Day day, char shift)
  {
    this.setDay(day);
    this.setShift(shift);
  }

  /**
   * @return the day
   */
  public Day getDay()
  {
    return day;
  }

  /**
   * @param day the day to set
   */
  public void setDay(Day day)
  {
    this.day = day;
  }

  /**
   * @return the shift
   */
  public char getShift()
  {
    return shift;
  }

  /**
   * @param shift the shift to set
   */
  public void setShift(char shift)
  {
    this.shift = shift;
  }
  
}
