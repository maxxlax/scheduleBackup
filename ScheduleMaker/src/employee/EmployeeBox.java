package employee;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class EmployeeBox extends JComboBox<Employee> implements EmployeeListObserver
{
  private Employee newEmployee;
  private boolean newEmployeeRemoved;

  public EmployeeBox(EmployeeList employeeList)
  {
    super();
    setEditable(false);
    setBounds(10, 10, 150, 20);
    newEmployee = new Employee("New Employee");

    if (employeeList.size() == 0)
    {
      addNewEmployee();
    }
    else
    {
      for (Employee emp : employeeList)
      {
        addItem(emp);
      }
    }
  }

  @Override
  public void addItem(Employee employee)
  {
    super.addItem(employee);
    if (!newEmployeeRemoved)
    {
      removeNewEmployee();
    }
    setSelectedItem(employee);
  }

  public void removeItem(Employee employee)
  {
    if (getItemCount() >= 1)
    {
      super.removeItem(employee);
      if (getItemCount() == 0)
      {
        addNewEmployee();
      }
    }
  }

  private void addNewEmployee()
  {
    super.addItem(newEmployee);
    newEmployeeRemoved = false;
  }

  private void removeNewEmployee()
  {
    super.removeItem(newEmployee);
    newEmployeeRemoved = true;
  }

  @Override
  public void updateAdd(Employee employee)
  {
    addItem(employee);
  }

  @Override
  public void updateRemove(Employee employee)
  {
    removeItem(employee);
  }
}
