package gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
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
import scheduling.Day;
import scheduling.Schedule;
import scheduling.Scheduler;
import scheduling.Shift;
import scheduling.ShiftPeriod;
import timer.TimeRunner;

/**
 * Jimmy Johns Scheduler 
 * @version 1
 * @author Max Croston
 *
 */
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
  private ShiftEditor shiftEditor;
  private AddShiftPanel addShiftPanel;
  private ViewShiftPanel viewShiftPanel;
  private CreateSchedulePanel createSchedulePanel;
  private SchedulePanel schedulePanel;
  private JTabbedPane jtp;
  private JLabel timeLabel1, timeLabel2;
  private ArrayList<ShiftPeriod> prioritizedSalesWeek;
  private Scheduler scheduler;
  private boolean salesReady, shiftsReady;
  private JLabel weekLabel;

  /**
   * Constructor Builds The Panel
   */
  public MainPanel()
  {
    setLayout(null);
    setLandF(1);
    setBounds(0, 0, 1000, 677);
    setVisible(true);
    setBackground(black);

    setSalesReady(false);
    setShiftsReady(false);

    schedule = new Schedule();
    employeeList = new EmployeeList(schedule);
    shiftEditor = new ShiftEditor(this, schedule);
    
    schedulePanel = new SchedulePanel(this, shiftEditor);
    schedule.setSchedulePanel(schedulePanel);
    add(schedulePanel);

    salesPanel = new SalesPanel(this);
    employeePanel = new EmployeePanel(this);
    viewEmployeesPanel = new ViewEmployeesPanel(this);
    viewEmployeesPanel.setModListAsObserver(employeeList);
    viewShiftPanel = new ViewShiftPanel(this);
    createSchedulePanel = new CreateSchedulePanel(this, schedulePanel);
    addShiftPanel = new AddShiftPanel(this, shiftEditor);

    jtp = new JTabbedPane();
    jtp.setTabPlacement(JTabbedPane.LEFT);
    jtp.setBounds(0, 0, 300, 677);
    jtp.addTab("Sales", getSalesPanel());
    jtp.addTab("Employees", employeePanel);
    jtp.addTab("Move Employee", viewEmployeesPanel);
    jtp.addTab("Shifts", addShiftPanel);
    jtp.addTab("Move Shifts", viewShiftPanel);
    jtp.addTab("Schedule", createSchedulePanel);
    add(jtp);

    JLabel myLabel = new JLabel("<HTML>J<br>I<br>M<br>M<br>Y<br><br>J<br>O<br>H<br>N<br>S</HTML>", JLabel.RIGHT);
    myLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
    myLabel.setForeground(Color.WHITE);
    myLabel.setBounds(0, 215, 100, 300);
    add(myLabel);
    
    // addExtraTestEmployees();
    employeePanel.nameChanged();
    
    //Week Number
    weekLabel = new JLabel("Week 0", JLabel.CENTER);
    weekLabel.setBounds(0, 550, 100, 30);
    weekLabel.setFont(new Font("Monospaced", Font.BOLD, 13));
    weekLabel.setForeground(Color.WHITE);
    add(weekLabel);
    
    //Timer
    timeLabel1 = new JLabel("11:38", JLabel.CENTER);
    timeLabel1.setBounds(0, 600, 100, 30);
    timeLabel1.setFont(new Font("Monospaced", Font.BOLD, 13));
    timeLabel1.setForeground(Color.WHITE);
    add(timeLabel1);
    
    timeLabel2 = new JLabel("2018/01/22", JLabel.CENTER);
    timeLabel2.setBounds(0, 575, 100, 30);
    timeLabel2.setFont(new Font("Monospaced", Font.BOLD, 13));
    timeLabel2.setForeground(Color.WHITE);
    add(timeLabel2);
    
    Thread timerThread = new Thread(new TimeRunner(weekLabel, timeLabel1, timeLabel2));
    timerThread.start();
  }

  /**
   * Adds a shift to the schedule and sorts it
   * shiftReady = true
   * redraw
   * @param shift
   * @param ampm
   * @return true if ampm not full
   */
  public boolean addShift(Shift shift, String ampm)
  {
    if(schedule.addShift(shift, ampm))
    {
      shiftsReady = true;
      redrawSchedule();
      return true;
    }
    return false;
  }

  /**
   * Emptys all fillers and redraws the schedule
   */
  public void redrawSchedule()
  {
    schedulePanel.emptyFillers();
    schedulePanel.drawShifts(schedule);
  }

  /**
   * Set Prioritized Sales Week
   * @param prioritizedSalesWeek
   */
  public void setPrioritizedSalesWeek(ArrayList<ShiftPeriod> prioritizedSalesWeek)
  {
    this.prioritizedSalesWeek = prioritizedSalesWeek;
    viewShiftPanel.setElements(prioritizedSalesWeek);
    setSalesReady(true);
  }

  /**
   * Verify that employees and shifts have been added
   * Maintain current shifts while drawing in new emps
   */
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
      scheduler = new Scheduler(employeeList, this);
      scheduler.schedulePrioritizedWeek(prioritizedSalesWeek);
      redrawSchedule();
    }
  }

  /**
   * Changes to final tab and displays shift info
   * 
   * @param shift
   * @param id 
   * @param ampm 
   */
  public void viewShiftInfo(Shift shift, String ampm, int id)
  {
    jtp.setSelectedIndex(jtp.getTabCount() - 1);
    createSchedulePanel.viewShift(shift, ampm, id);
  }

  /**
   * Removes the shift at index i
   * Removes hours from shift employee
   * @param ampm
   * @param i
   */
  public void removeShift(String ampm, int i)
  {
    schedule.removeShift(ampm, i);
    redrawSchedule();
  }
  
  /**
   * Switches to EmployeePanel and sets current employee
   * @param employee
   */
  public void viewEmployee(Employee employee)
  {
    jtp.setSelectedIndex(1);
    employeePanel.getEmployeeBox().setSelectedItem(employee);
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

  /**
   * Creates a saver that completely replaces all text in save file
   * @return
   */
  public boolean saveAllInfo()
  {
    System.out.println("Saving...");
    Thread thread = new Thread(new Saver(this));
    thread.start();
    return true;
  }

  /**
   * Loads info from save file
   */
  public void loadAllFiles()
  {
    Thread thread = new Thread(new Loader(SAVE_FILE_NAME, this, true));
    thread.start();
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

  public JTabbedPane getJtp()
  {
    return jtp;
  }

  public void setJtp(JTabbedPane jtp)
  {
    this.jtp = jtp;
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

  /**
   * Sales Ready + EmployeeList Ready + Shifts Ready
   * @return
   */
  public boolean isReady()
  {
    return isSalesReady() && employeeList.isReady() && isShiftsReady();
  }

  public ShiftEditor getShiftEditor()
  {
    return shiftEditor;
  }
}
