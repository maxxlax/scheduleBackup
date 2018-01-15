package scheduling;

import employee.Employee;

/**
 * @author Max Croston
 *
 */
public class Shift implements Comparable<Shift>
{
  public int startTime, endTime;
  public Day day;
  public ShiftType type;
  public Employee employee;
  public boolean filled;
  public boolean isAM;

  // Used for ShiftAreas
  public Shift(Day day, ShiftType type)
  {
    super();
    this.day = day;
    this.type = type;
    filled = false;
    setEmployee(new Employee());
  }

  public Shift(int startTime, int endTime, Day day, ShiftType type)
  {
    super();
    this.startTime = startTime;
    this.endTime = endTime;
    this.day = day;
    this.type = type;
    employee = new Employee();
    filled = false;
    if (startTime < 14)
    {
      isAM = true;
    }
    else
    {
      isAM = false;
    }
  }
  
  public Shift(int startTime, int endTime, Day day, ShiftType type, boolean filled, Employee employee)
  {
    super();
    this.startTime = startTime;
    this.endTime = endTime;
    this.day = day;
    this.type = type;
    this.employee = employee;
    this.filled = filled;
    if (startTime < 14)
    {
      isAM = true;
    }
    else
    {
      isAM = false;
    }
  }

  public Employee getEmployee()
  {
    return employee;
  }

  public void setEmployee(Employee employee)
  {
    this.employee = employee;
    filled = true;
  }

  public String toString()
  {
    return "Shift:" + "\n    Day: " + day + "\n    startTime: " + startTime + "\n    endTime: "
        + endTime + "\n    ShiftType: " + type + "\n    Employee: " + employee.fullName
        + "\n    filled: " + filled + "\n    isAM: " + isAM + "\n";
  }

  @Override
  public int compareTo(Shift shift)
  {
    if (startTime == shift.startTime && endTime == shift.endTime && day.equals(shift.day)
        && type == shift.type)
    {
      return 0;
    }

    if (!shift.type.equals(type))
    {
      if (type.equals(ShiftType.Driver))
        return 1;
      else
        return -1;
    }
    else if (startTime == shift.startTime)
    {
      if (endTime > shift.endTime)
        return -1;
      else if (endTime < shift.endTime)
        return 1;
      else
        return 0;
    }
    else if (startTime < shift.startTime)
      return -1;
    else if (startTime > shift.startTime)
      return 1;
    else
      return -1;
  }

  public String toString(boolean b)
  {
    if (b)
    {
      return toString() + employee.toString(true);
    }
    else
    {
      return "Day: " + day + "\nStart Time: " + startTime + "\nEnd Time: " + endTime;
    }
  }

  public void empty()
  {
    employee.currentHours -= endTime - startTime;
    setEmployee(new Employee());
    filled = false;
  }

  public String toSaveString()
  {
    String str = startTime + "|" + endTime + "|" + day + "|" + type + "|" + filled;
    if(filled)
    {
      str += "|" + employee.fullName;
    }
    return str;
  }
}
