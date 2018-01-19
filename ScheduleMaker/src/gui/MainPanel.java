package gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import employee.Employee;
import employee.EmployeeList;
import io.Loader;
import io.Saver;
import sales.ShiftSale;
import sales.WeeklySales;
import scheduling.Day;
import scheduling.Schedule;
import scheduling.Scheduler;
import scheduling.Shift;
import scheduling.ShiftType;
import scheduling.Week;

@SuppressWarnings("serial")
public class MainPanel extends JPanel
{
  public final Color red = new Color(207, 10, 44);
  public final Color black = new Color(50, 50, 50);
  public final Color white = new Color(240, 240, 240);
  public final String SAVE_FILE_NAME = "ScheduleSaveData.txt";

  private Schedule schedule;
  private EmployeeList employeeList;
  private SalesPanel salesPanel;
  private EmployeePanel employeePanel;
  private ViewEmployeesPanel viewEmployeesPanel;
  private AddShiftPanel addShiftPanel;
  private ViewShiftPanel viewShiftPanel;
  private CreateSchedulePanel createSchedulePanel;
  private SchedulePanel schedulePanel;
  private JTabbedPane jtp;
  private Loader loader;
  private JLabel imageLabel;
  private ImageIcon image;
  private String editShiftAMPM;
  private int editShiftID;
  private ArrayList<ShiftSale> prioritizedSalesWeek;
  private Scheduler scheduler;
  private boolean salesReady, shiftsReady;
  @SuppressWarnings("unused")
  private WeeklySales weeklySales;

  public MainPanel()
  {
    setLayout(null);
    setLandF(1);
    setBounds(0, 0, 1000, 677);
    setVisible(true);
    setBackground(black);

    setSchedule(new Schedule());
    setEmployeeList(new EmployeeList());

    setSalesReady(false);
    setShiftsReady(false);

    schedulePanel = new SchedulePanel(this);
    add(schedulePanel);

    salesPanel = new SalesPanel(this);
    employeePanel = new EmployeePanel(this);
    viewEmployeesPanel = new ViewEmployeesPanel(this);
    viewShiftPanel = new ViewShiftPanel(this);
    createSchedulePanel = new CreateSchedulePanel(this, schedulePanel);
    addShiftPanel = new AddShiftPanel(this);

    jtp = new JTabbedPane();
    jtp.setTabPlacement(JTabbedPane.LEFT);
    jtp.setBounds(0, 0, 300, 677);
    jtp.addTab("Enter Sales", getSalesPanel());
    jtp.addTab("Add Employees", employeePanel);
    jtp.addTab("View Employees", viewEmployeesPanel);
    jtp.addTab("Add Shifts", addShiftPanel);
    jtp.addTab("View Shifts", viewShiftPanel);
    jtp.addTab("Schedule", createSchedulePanel);
    add(jtp);

    image = new ImageIcon("jj.jpg");
    imageLabel = new JLabel("", image, JLabel.CENTER);
    imageLabel.setBounds(1, 537, 105, 100);
    add(imageLabel);

    // addExtraTestEmployees();
    employeePanel.nameChanged();
  }

  public boolean addShift(Day day, String ampm, ShiftType type, int startTime, int endTime)
  {
    if (schedule.getNumShiftType(type, day, ampm) < 8)
    {
      getSchedule().get(day).get(ampm).add(new Shift(startTime, endTime, day, type));
      Collections.sort(getSchedule().get(day).get(ampm));
      setShiftsReady(true);
      redrawSchedule();
      return true;
    }
    else
    {
      notifyUser(
          "Can't add any more of that type of shift, remove shifts by clicking the time and selecting remove");
      return false;
    }
  }

  public void redrawSchedule()
  {
    schedulePanel.emptyFillers();
    schedulePanel.drawShifts(getSchedule());
  }

  public void setInfo(WeeklySales weeklySales, ArrayList<ShiftSale> prioritizedSalesWeek)
  {
    this.weeklySales = weeklySales;
    this.prioritizedSalesWeek = prioritizedSalesWeek;
    setSalesReady(true);
  }

  public void fillOpenShifts()
  {
    if (getEmployeeList().size() == 0 && employeeList.isReady())
    {
      notifyUser("No Employees Have Been Added");
    }
    else if ((getSchedule().get(Day.Sunday).get("am").size() == 0
        && getSchedule().get(Day.Sunday).get("pm").size() == 0) && isShiftsReady())
    {
      notifyUser("No Shifts Have Been Added");
    }
    else
    {
      scheduler = new Scheduler(getEmployeeList(), this);
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
      Shift s = getSchedule().get(day).get(editShiftAMPM).get(editShiftID);
      if (s.filled)
      {
        s.employee.currentHours -= s.endTime - s.startTime;
        s.employee.currentHours += end - start;
      }
      // Edit the shift
      getSchedule().get(day).get(editShiftAMPM).get(editShiftID).startTime = start;
      getSchedule().get(day).get(editShiftAMPM).get(editShiftID).endTime = end;
      Collections.sort(getSchedule().get(day).get(editShiftAMPM));
    }
    redrawSchedule();
  }

  public void removeShift(String ampm, int i)
  {
    for (Day day : new Week())
    {
      // Subtract shift hours from current hours
      Shift s = getSchedule().get(day).get(ampm).get(i);
      if (s.filled)
      {
        s.employee.currentHours -= s.endTime - s.startTime;
      }
      // Remove the shift
      getSchedule().get(day).get(ampm).remove(i);
    }
    redrawSchedule();
  }

  public void viewEmployee(Employee employee)
  {
    jtp.setSelectedIndex(1);
    employeePanel.getEmployeeBox().setSelectedItem(employee);
  }

  public void removeEmployee(String fullName)
  {
    Employee emp = null;
    ArrayList<Employee> copyList = new ArrayList<Employee>(getEmployeeList());
    for (Employee e : copyList)
    {
      if (e.fullName.equals(fullName))
      {
        emp = e;
        getEmployeeList().remove(e);
      }
    }
    if (emp == null)
    {
      notifyUser("Unable to find employee " + fullName);
    }
    else
    {
      viewEmployeesPanel.removeEmployee(emp);
    }
    for (Day day : new Week())
    {
      for (Shift shift : getSchedule().get(day).get("am"))
      {
        if (shift.employee.fullName.equals(fullName))
        {
          shift.empty();
        }
      }
      for (Shift shift : getSchedule().get(day).get("pm"))
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
      for (Shift shift : getSchedule().get(day).get("am"))
      {
        shift.empty();
      }

      for (Shift shift : getSchedule().get(day).get("pm"))
      {
        shift.empty();
      }
    }
  }

  public void notifyUser(String message)
  {
    JOptionPane.showMessageDialog(this, message);
  }

  @SuppressWarnings("unused")
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
    employeeList.add(new Employee("Jake", "Croston", false, true, true, 40, availabilityWeek));
    employeeList.add(new Employee("John", "Doe", true, true, false, 39, availabilityAll));
    employeeList.add(new Employee("Jim", "Halpert", true, true, true, 39, availabilityAll));
    employeeList.add(new Employee("Jill", "Dupuis", true, false, true, 39, availabilityAll));
    employeeList.add(new Employee("Drake", "Onsair", true, true, true, 39, availabilityWeekends));
    employeeList.add(new Employee("Jess", "Spencer", true, false, false, 39, availabilityAll));
    employeeList.add(new Employee("Tori", "Atkins", true, false, false, 39, availabilityWeek));
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
    Saver saver = new Saver(this);
    return saver.saveAll();
  }

  public void loadAllFiles()
  {
    try
    {
      loader = new Loader(SAVE_FILE_NAME, this, true);
      loader.loadFiles();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * @return the salesPanel
   */
  public SalesPanel getSalesPanel()
  {
    return salesPanel;
  }

  /**
   * @param salesPanel
   *          the salesPanel to set
   */
  public void setSalesPanel(SalesPanel salesPanel)
  {
    this.salesPanel = salesPanel;
  }

  /**
   * @return the employees
   */
  public EmployeeList getEmployeeList()
  {
    return employeeList;
  }

  /**
   * @param employeeList
   *          the employees to set
   */
  public void setEmployeeList(EmployeeList employeeList)
  {
    this.employeeList = employeeList;
  }

  /**
   * @return the schedule
   */
  public Schedule getSchedule()
  {
    return schedule;
  }

  /**
   * @param schedule
   *          the schedule to set
   */
  public void setSchedule(Schedule schedule)
  {
    this.schedule = schedule;
  }
  /*
   * public void editEmployee(String firstName, String lastName, boolean isInshop, boolean isDriver,
   * boolean canDouble, int maxNumHours, ArrayList<int[]> availability) { int index =
   * getEmployeeList() .indexOf(new Employee(firstName, lastName, isInshop, isDriver, canDouble,
   * maxNumHours, availability)); Employee emp = getEmployeeList().get(index); emp.firstName =
   * firstName; emp.lastName = lastName; emp.isInshop = isInshop; emp.isDriver = isDriver;
   * emp.canDouble = canDouble; emp.maxNumHours = maxNumHours; emp.setAvailability(availability); //
   * Iterate shifts and ensure that emp can take on all shifts for (Day day : new Week()) { for
   * (Shift shift : getSchedule().get(day).get("am")) { if (shift.employee.fullName.equals(firstName
   * + " " + lastName)) { if (!emp.isAvailable(shift)) { shift.empty(); } } } for (Shift shift :
   * getSchedule().get(day).get("pm")) { if (shift.employee.fullName.equals(firstName + " " +
   * lastName)) {
   * 
   * } } } }
   */

  /**
   * @return the salesReady
   */
  public boolean isSalesReady()
  {
    return salesReady;
  }

  /**
   * @param salesReady the salesReady to set
   */
  public void setSalesReady(boolean salesReady)
  {
    this.salesReady = salesReady;
  }

  /**
   * @return the shiftsReady
   */
  public boolean isShiftsReady()
  {
    return shiftsReady;
  }

  /**
   * @param shiftsReady the shiftsReady to set
   */
  public void setShiftsReady(boolean shiftsReady)
  {
    this.shiftsReady = shiftsReady;
  }

  public boolean isReady()
  {
    return isSalesReady() && employeeList.isReady() && isShiftsReady();
  }
}
