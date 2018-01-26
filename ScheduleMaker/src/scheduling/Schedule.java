package scheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import employee.Employee;
import gui.SchedulePanel;

@SuppressWarnings("serial")
public class Schedule extends HashMap<Day, HashMap<String, ArrayList<Shift>>>
{
  private SchedulePanel schedulePanel;

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

  public int getNumShiftOfType(ShiftType type, Day day, String ampm)
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

  public void editShift(int start, int end, String editShiftAMPM, int editShiftID)
  {
    for (Day day : new Week())
    {
      // Subtract difference shift hours from current hours
      Shift s = get(day).get(editShiftAMPM).get(editShiftID);
      if (s.filled)
      {
        s.employee.currentHours -= s.endTime - s.startTime;
        s.employee.currentHours += end - start;
      }
      // Edit the shift
      get(day).get(editShiftAMPM).get(editShiftID).startTime = start;
      get(day).get(editShiftAMPM).get(editShiftID).endTime = end;
      Collections.sort(get(day).get(editShiftAMPM));

      // Ensure emp doesn't go over max
      if (s.employee.currentHours > s.employee.maxNumHours)
      {
        get(day).get(editShiftAMPM).get(editShiftID).empty();
      }
    }
    redraw();
  }

  /**
   * @param ampm
   * @param i
   */
  public void removeShift(String ampm, int i)
  {
    for (Day day : new Week())
    {
      // Subtract shift hours from current hours
      Shift s = get(day).get(ampm).remove(i);
      if (s.filled)
      {
        s.employee.currentHours -= s.endTime - s.startTime;
      }
    }
  }

  /**
   * Adds a shift to the schedule and sorts it
   * 
   * @param shift
   * @param ampm
   * @return true if ampm not full
   */
  public boolean addShift(Shift shift, String ampm)
  {
    if (getNumShiftOfType(shift.type, shift.day, ampm) < 8)
    {
      get(shift.day).get(ampm).add(shift);
      Collections.sort(get(shift.day).get(ampm));
      return true;
    }
    return false;
  }

  /**
   * Completely emptys all shifts in the schedule
   */
  public void emptyAllShifts()
  {
    for (Day day : new Week())
    {
      for (Shift shift : get(day).get("am"))
      {
        shift.empty();
      }

      for (Shift shift : get(day).get("pm"))
      {
        shift.empty();
      }
    }
  }

  public void redraw()
  {
    // TODO Create this instead of mainPanel.redraw
    schedulePanel.emptyFillers();
    schedulePanel.drawShifts(this);
  }

  public void setSchedulePanel(SchedulePanel schedulePanel)
  {
    this.schedulePanel = schedulePanel;
  }

  public void removeAllShifts()
  {
    for (int ii = 0; ii < get(Day.Sunday).get("am").size(); ii++)
    {
      removeShift("am", ii);
    }
    for (int ii = 0; ii < get(Day.Sunday).get("pm").size(); ii++)
    {
      removeShift("pm", ii);
    }
  }

}
