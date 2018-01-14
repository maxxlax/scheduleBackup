package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import employee.Employee;
import sales.WeeklySales;
import scheduling.Day;
import scheduling.Scheduler;
import scheduling.Shift;
import scheduling.ShiftCreator;
import scheduling.ShiftType;
import scheduling.Week;

@SuppressWarnings("serial")
public class MainPanel extends Toolbox
{
  public final Color red = new Color(207, 10, 44);
  public final Color black = new Color(50, 50, 50);
  public final Color white = new Color(240, 240, 240);

  SchedulePanel schedulePanel;
  JTabbedPane jtp;
  EmployeePanel employeePanel;
  ViewShiftPanel setShiftPanel;
  AddShiftPanel addShiftPanel;
  JLabel imageLabel;
  ImageIcon image;
  private String editShiftAMPM;
  private int editShiftID;

  public MainPanel()
  {
    super();
    setLayout(null);
    setLandF(1);
    setBounds(0, 0, 1000, 677);
    setVisible(true);
    setBackground(black);

    employees = new ArrayList<Employee>();

    salesReady = false;

    schedulePanel = new SchedulePanel(this);
    add(schedulePanel);

    employeePanel = new EmployeePanel(this);
    viewEmployeesPanel = new ViewEmployeesPanel(this);
    setShiftPanel = new ViewShiftPanel(this);
    createSchedulePanel = new CreateSchedulePanel(this, schedulePanel);
    addShiftPanel = new AddShiftPanel(this);

    jtp = new JTabbedPane();
    jtp.setTabPlacement(JTabbedPane.LEFT);
    jtp.setBounds(0, 0, 300, 677);
    jtp.addTab("Enter Sales", new SalesPanel(this));
    jtp.addTab("Add Employees", employeePanel);
    jtp.addTab("View Employees", viewEmployeesPanel);
    jtp.addTab("Add Shifts", addShiftPanel);
    jtp.addTab("View Shifts", setShiftPanel);
    jtp.addTab("Schedule", createSchedulePanel);
    add(jtp);

    image = new ImageIcon("jj.jpg");
    imageLabel = new JLabel("", image, JLabel.CENTER);
    imageLabel.setBounds(1, 537, 105, 100);
    add(imageLabel);

    addExtraTestEmployees();
    employeePanel.nameChanged();
  }

  public void addShift(Day day, String ampm, ShiftType type, int startTime, int endTime)
  {
    schedule.get(day).get(ampm).add(new Shift(startTime, endTime, day, type));
    Collections.sort(schedule.get(day).get(ampm));
    redrawSchedule();
  }

  private void redrawSchedule()
  {
    schedulePanel.emptyFillers();
    schedulePanel.drawShifts(schedule);
  }

  public void setInfo(WeeklySales ws, ArrayList<Day> prioritizedSalesWeek, ShiftCreator sc)
  {
    this.ws = ws;
    this.prioritizedSalesWeek = prioritizedSalesWeek;
    this.sc = sc;
    salesReady = true;
  }

  public void fillOpenShifts()
  {
    if (employees.size() == 0)
    {
      notifyUser("No Employees Have Been Added");
    }
    else if (schedule.get(Day.Sunday).get("am").size() == 0
        && schedule.get(Day.Sunday).get("pm").size() == 0)
    {
      notifyUser("No Shifts Have Been Added");
    }
    else
    {
      scheduler = new Scheduler(employees, this);
      scheduler.schedulePrioritizedWeek(prioritizedSalesWeek);
      redrawSchedule();
    }
  }

  /**
   * Changes to final tab and displays shift info
   * 
   * @param shift
   */
  public void viewShiftInfo(Shift shift)
  {
    jtp.setSelectedIndex(jtp.getTabCount() - 1);
    createSchedulePanel.viewShift(shift);
  }

  public void viewShiftEditor(Shift shift, String ampm, int id)
  {
    this.editShiftAMPM = ampm;
    this.editShiftID = id;
    jtp.setSelectedIndex(3);
    addShiftPanel.viewShiftEditor(shift);
  }

  public void editShift(int start, int end)
  {
    for (Day day : new Week())
    {
      // Subtract difference shift hours from current hours
      Shift s = schedule.get(day).get(editShiftAMPM).get(editShiftID);
      if (s.filled)
      {
        s.employee.currentHours -= s.endTime - s.startTime;
        s.employee.currentHours += end - start;
      }
      // Edit the shift
      schedule.get(day).get(editShiftAMPM).get(editShiftID).startTime = start;
      schedule.get(day).get(editShiftAMPM).get(editShiftID).endTime = end;
    }
    redrawSchedule();
  }

  public void removeShift(String ampm, int i)
  {
    for (Day day : new Week())
    {
      // Subtract shift hours from current hours
      Shift s = schedule.get(day).get(ampm).get(i);
      if (s.filled)
      {
        s.employee.currentHours -= s.endTime - s.startTime;
      }
      // Remove the shift
      schedule.get(day).get(ampm).remove(i);
    }
    redrawSchedule();
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
      Employee emp = employees.remove(employees.indexOf(new Employee(listModel.get(ii))));
      employees.add(emp);
    }

  }

  public void viewEmployee(Employee employee)
  {
    jtp.setSelectedIndex(1);
    employeePanel.setSelectedEmployee(employee);
  }

  public void removeEmployee(String fullName)
  {
    Employee emp = null;
    ArrayList<Employee> copyList = new ArrayList<Employee>(employees);
    for (Employee e : copyList)
    {
      if (e.fullName.equals(fullName))
      {
        emp = e;
        employees.remove(e);
      }
    }
    if (emp == null)
    {
      JOptionPane.showMessageDialog(null, "Unable to find employee " + fullName, "Error",
          JOptionPane.INFORMATION_MESSAGE);
    }
    else
    {
      if (employees.size() == 0)
      {
        addEmployee(employeePanel.newEmployee, employeePanel.getEmployees());
      }
      employeePanel.getEmployees().removeItem(emp);
      viewEmployeesPanel.removeEmployee(emp);
    }
    if (employees.size() == 0)
    {
      employeesReady = false;
    }
    // TODO Check Schedule for this emp and remove
    for (Day day : new Week())
    {
      for (Shift shift : schedule.get(day).get("am"))
      {
        if (shift.employee.fullName.equals(fullName))
        {
          shift.empty();
        }
      }
      for (Shift shift : schedule.get(day).get("pm"))
      {
        if (shift.employee.fullName.equals(fullName))
        {
          shift.empty();
        }
      }
    }
    redrawSchedule();
  }

  public void emptyAllShifts()
  {
    for (Day day : new Week())
    {
      for (Shift shift : schedule.get(day).get("am"))
      {
        shift.empty();
      }

      for (Shift shift : schedule.get(day).get("pm"))
      {
        shift.empty();
      }
    }
  }

  public void notifyUser(String message)
  {
    JOptionPane.showMessageDialog(null, message);
  }

  private void addExtraTestEmployees()
  {
    // Create availabilities
    ArrayList<int[]> availabilityAll = new ArrayList<int[]>();
    for (int ii = 0; ii < 7; ii++)
    {
      availabilityAll.add(new int[] {10, 23});
    }

    ArrayList<int[]> availabilityWeekends = new ArrayList<int[]>();
    availabilityWeekends.add(new int[] {10, 23});
    availabilityWeekends.add(new int[] {-1, -1});
    availabilityWeekends.add(new int[] {-1, -1});
    availabilityWeekends.add(new int[] {-1, -1});
    availabilityWeekends.add(new int[] {-1, -1});
    availabilityWeekends.add(new int[] {16, 23});
    availabilityWeekends.add(new int[] {10, 23});

    ArrayList<int[]> availabilityWeek = new ArrayList<int[]>();
    availabilityWeek.add(new int[] {-1, -1});
    availabilityWeek.add(new int[] {10, 23});
    availabilityWeek.add(new int[] {10, 23});
    availabilityWeek.add(new int[] {10, 23});
    availabilityWeek.add(new int[] {10, 23});
    availabilityWeek.add(new int[] {-1, -1});
    availabilityWeek.add(new int[] {-1, -1});
    addEmployee(new Employee("Jake", "Croston", false, true, true, 40, availabilityWeek),
        employeePanel.getEmployees());
    addEmployee(new Employee("John", "Doe", true, true, false, 39, availabilityAll),
        employeePanel.getEmployees());
    addEmployee(new Employee("Jim", "Halpert", true, true, true, 39, availabilityAll),
        employeePanel.getEmployees());
    addEmployee(new Employee("Jill", "Dupuis", true, false, true, 39, availabilityAll),
        employeePanel.getEmployees());
    addEmployee(new Employee("Drake", "Onsair", true, true, true, 39, availabilityWeekends),
        employeePanel.getEmployees());
    addEmployee(new Employee("Jess", "Spencer", true, false, false, 39, availabilityAll),
        employeePanel.getEmployees());
    addEmployee(new Employee("Tori", "Atkins", true, false, false, 39, availabilityWeek),
        employeePanel.getEmployees());
  }

  public void setLandF(int value)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getInstalledLookAndFeels()[value].getClassName());
    }
    catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e)
    {
      e.printStackTrace();
    }
  }

  public boolean saveAllInfo()
  {
    System.out.println("Saving...");
    return true;
  }
}
