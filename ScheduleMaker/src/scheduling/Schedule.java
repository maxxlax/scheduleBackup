package scheduling;

import java.util.ArrayList;
import java.util.HashMap;

import employee.Employee;

@SuppressWarnings("serial")
public class Schedule extends HashMap<Day, HashMap<String, ArrayList<Shift>>>
{
  public Schedule()
  {
    for (Day day : new Week())
    {
      put(day, new HashMap<String, ArrayList<Shift>>());
      get(day).put("am", new ArrayList<Shift>());
      get(day).put("pm", new ArrayList<Shift>());
    }
  }

  public void emptyUnavailableShifts(Employee emp)
  {
    // Iterate shifts and ensure that emp can take on all shifts
    for (Day day : new Week())
    {
      for (Shift shift : get(day).get("am"))
      {
        if (shift.employee.fullName.equals(emp.firstName + " " + emp.lastName))
        {
          if (!emp.isAvailable(shift))
          {
            shift.empty();
          }
        }
      }
      for (Shift shift : get(day).get("pm"))
      {
        if (shift.employee.fullName.equals(emp.firstName + " " + emp.lastName))
        {

        }
      }
    }
  }
  
  public int getNumShiftType(ShiftType type, Day day, String ampm)
  {
    int counter = 0;
    for (Shift shift : get(day).get(ampm))
    {
      if (shift.type.equals(type))
      {
        counter++;
      }
    }
    return counter;
  }

}
