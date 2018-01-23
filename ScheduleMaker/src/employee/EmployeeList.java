package employee;

import java.util.ArrayList;
import javax.swing.DefaultListModel;

import scheduling.Day;
import scheduling.Schedule;
import scheduling.Shift;
import scheduling.Week;

@SuppressWarnings("serial")
public class EmployeeList extends ArrayList<Employee>
{
  private ArrayList<EmployeeListObserver> observers;
  private boolean isReady;
  private Schedule schedule;

  public EmployeeList(Schedule schedule)
  {
    super();
    this.schedule = schedule;
    observers = new ArrayList<EmployeeListObserver>();
    setReady(false);
  }

  public void editEmployee(String firstName, String lastName, boolean isInshop, boolean isDriver,
      boolean canDouble, int maxNumHours, ArrayList<int[]> availability)
  {
    int index = indexOf(new Employee(firstName, lastName, isInshop, isDriver, canDouble,
        maxNumHours, availability));
    Employee emp = get(index);
    emp.firstName = firstName;
    emp.lastName = lastName;
    emp.isInshop = isInshop;
    emp.isDriver = isDriver;
    emp.canDouble = canDouble;
    emp.maxNumHours = maxNumHours;
    emp.setAvailability(availability);
    schedule.emptyUnavailableShifts(emp);
  }

  /**
   * Removes employees from the list and replaces them in the order that the user has set.
   * 
   * @param listModel
   */
  public void reorganizeEmployees(DefaultListModel<String> listModel)
  {
    for (int ii = 0; ii < listModel.size(); ii++)
    {
      Employee emp = super.remove(indexOf(new Employee(listModel.get(ii))));
      super.add(emp);
    }

  }

  public Employee findName(String name)
  {
    for (Employee e : this)
    {
      if (e.fullName.equals(name))
      {
        return e;
      }
    }
    return null;
  }

  @Override
  public boolean add(Employee employee)
  {
    boolean flag = super.add(employee);
    if (flag)
    {
      setReady(true);
      notifyObserversAdd(employee);
    }
    return flag;
  }

  public boolean remove(Employee employee)
  {
    System.out.println("Removing... " + employee.toSaveString());
    boolean flag = super.remove(employee);
    if (flag)
    {
      notifyObserversRemove(employee);
      if (size() == 0)
      {
        setReady(false);
      }
      //Empty Shifts
      for (Day day : new Week())
      {
        for (Shift shift : schedule.get(day).get("am"))
        {
          if (shift.employee.fullName.equals(employee.fullName))
          {
            shift.empty();
          }
        }
        for (Shift shift : schedule.get(day).get("pm"))
        {
          if (shift.employee.fullName.equals(employee.fullName))
          {
            shift.empty();
          }
        }
      }
      schedule.redraw();
    }
    return flag;
  }
  
  public boolean remove(String name)
  {
    return remove(findName(name));
  }

  public void addObserver(EmployeeListObserver elo)
  {
    observers.add(elo);
  }

  public void notifyObserversAdd(Employee employee)
  {
    for (EmployeeListObserver elo : observers)
    {
      elo.updateAdd(employee);
    }
  }

  public void notifyObserversRemove(Employee employee)
  {
    for (EmployeeListObserver elo : observers)
    {
      elo.updateRemove(employee);
    }
  }

  /**
   * @return the employeeListReady
   */
  public boolean isReady()
  {
    return isReady;
  }

  /**
   * @param isReady
   *          the employeeListReady to set
   */
  public void setReady(boolean isReady)
  {
    this.isReady = isReady;
  }
}
