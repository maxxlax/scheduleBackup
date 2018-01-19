package employee;

public interface EmployeeListObserver
{
  abstract public void updateAdd(Employee employee);
  abstract public void updateRemove(Employee employee);
}
