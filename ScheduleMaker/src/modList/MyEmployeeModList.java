package modList;

import employee.Employee;
import employee.EmployeeListObserver;
import gui.MainPanel;

@SuppressWarnings("serial")
public class MyEmployeeModList extends MyModList implements EmployeeListObserver
{

  public MyEmployeeModList(MainPanel mainPanel)
  {
    super(mainPanel);
  }

  public void addEmployee(Employee emp)
  {
    model.addElement(emp.fullName);
  }

  public void removeEmployee(Employee emp)
  {
    if (model.removeElement(emp.fullName))
    {
      System.out.println("Successfully Removed");
    }
    else
    {
      System.out.println("Couldnt remove");
    }
  }

  public void removeEmployee(String string)
  {
    model.removeElement(string);
  }

  @Override
  public void updateAdd(Employee employee)
  {
    addEmployee(employee);
  }

  @Override
  public void updateRemove(Employee employee)
  {
    removeEmployee(employee);
  }

  @Override
  protected void reorganize()
  {
    mainPanel.getEmployeeList().reorganizeEmployees(model);
  }
}
