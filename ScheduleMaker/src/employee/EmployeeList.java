package employee;

import java.util.ArrayList;
import javax.swing.DefaultListModel;

import scheduling.Schedule;

@SuppressWarnings("serial")
public class EmployeeList extends ArrayList<Employee>
{
  private ArrayList<EmployeeListObserver> observers;
  private boolean isReady;

  public EmployeeList()
  {
    super();
    observers = new ArrayList<EmployeeListObserver>();
    setReady(false);
  }

  public void editEmployee(String firstName, String lastName, boolean isInshop, boolean isDriver,
      boolean canDouble, int maxNumHours, ArrayList<int[]> availability, Schedule schedule)
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
      Employee emp = remove(indexOf(new Employee(listModel.get(ii))));
      add(emp);
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
      notifyUsersAdd(employee);
    }
    return flag;
  }

  public boolean remove(Employee employee)
  {
    boolean flag = super.remove(employee);
    if (flag)
    {
      notifyUsersRemove(employee);
      if (size() == 0)
      {
        setReady(false);
      }
    }
    return flag;
  }

  public void addObserver(EmployeeListObserver elo)
  {
    observers.add(elo);
  }

  public void notifyUsersAdd(Employee employee)
  {
    for (EmployeeListObserver elo : observers)
    {
      elo.updateAdd(employee);
    }
  }

  public void notifyUsersRemove(Employee employee)
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
