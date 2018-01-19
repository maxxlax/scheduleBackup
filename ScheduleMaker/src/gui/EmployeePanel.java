package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import employee.Employee;
import employee.EmployeeBox;

@SuppressWarnings("serial")
public class EmployeePanel extends JPanel
{
  private MainPanel mainPanel;
  private EmployeeBox employeeBox;
  private JCheckBox inshop, driver, canDouble;
  private JLabel firstName, lastName, maxNumHoursLabel, avLab1, avLab2, avLab3, startEnd, sun, mon,
      tue, wed, thur, fri, sat;
  private JTextField firstNameArea, lastNameArea, maxNumHoursArea;
  private JTextPane err;
  private ArrayList<JTextField> availability;
  private JPanel avPanel;
  private JButton addEmployee, removeEmployee;

  public EmployeePanel(MainPanel mainPanel)
  {
    this.mainPanel = mainPanel;
    setLayout(null);
    setBackground(mainPanel.red);
    setBorder(BorderFactory.createBevelBorder(1, mainPanel.black, mainPanel.white));

    availability = new ArrayList<JTextField>();
    employeeBox = new EmployeeBox(mainPanel.getEmployeeList());
    employeeBox.addActionListener(new EmployeeListener());
    mainPanel.getEmployeeList().addObserver(employeeBox);
    add(employeeBox);

    firstName = new JLabel("First Name: ");
    firstName.setBounds(10, 25, 100, 30);
    add(firstName);

    firstNameArea = new JTextField();
    firstNameArea.setBounds(10, 50, 150, 30);
    firstNameArea.getDocument().addDocumentListener(new NameListener());
    add(firstNameArea);

    lastName = new JLabel("Last Name: ");
    lastName.setBounds(10, 70, 100, 30);
    add(lastName);

    lastNameArea = new JTextField();
    lastNameArea.setBounds(10, 95, 150, 30);
    lastNameArea.getDocument().addDocumentListener(new NameListener());
    add(lastNameArea);

    inshop = new JCheckBox("InShopper");
    inshop.setBounds(5, 130, 87, 20);
    add(inshop);

    driver = new JCheckBox("Driver");
    driver.setBounds(95, 130, 100, 20);
    add(driver);

    canDouble = new JCheckBox("Can Double");
    canDouble.setBounds(5, 150, 90, 20);
    add(canDouble);

    maxNumHoursLabel = new JLabel("Max Hours per Week");
    maxNumHoursLabel.setBounds(10, 175, 130, 20);
    add(maxNumHoursLabel);

    maxNumHoursArea = new JTextField();
    maxNumHoursArea.setBounds(125, 173, 30, 25);
    add(maxNumHoursArea);

    JPanel availabilityPanel = new JPanel();
    availabilityPanel.setLayout(null);
    availabilityPanel.setBounds(0, 200, 200, 282);
    availabilityPanel.setBackground(new Color(50, 50, 50));
    add(availabilityPanel);

    avLab1 = new JLabel("Availabilty", JLabel.CENTER);
    avLab1.setFont(new Font(avLab1.getFont().getName(), Font.PLAIN, 20));
    avLab1.setForeground(Color.WHITE);
    avLab1.setBounds(5, 0, 195, 30);
    availabilityPanel.add(avLab1);

    avLab2 = new JLabel("Enter in 24hr Format", JLabel.CENTER);
    avLab2.setBounds(5, 20, 195, 20);
    avLab2.setForeground(Color.WHITE);
    availabilityPanel.add(avLab2);

    avLab3 = new JLabel("-1 in start and end for none", JLabel.CENTER);
    avLab3.setBounds(5, 30, 200, 20);
    avLab3.setForeground(Color.WHITE);
    availabilityPanel.add(avLab3);

    JPanel avPan = new JPanel();
    avPan.setLayout(null);
    avPan.setBounds(5, 50, 180, 225);
    // avPan.setBounds(0, 50, 190, 225);
    avPan.setBackground(mainPanel.red);
    availabilityPanel.add(avPan);

    startEnd = new JLabel("Start            End");
    startEnd.setBounds(56, 0, 120, 20);
    avPan.add(startEnd);

    sun = new JLabel("Sun");
    sun.setBounds(5, 20, 50, 20);
    avPan.add(sun);

    mon = new JLabel("Mon");
    mon.setBounds(5, 50, 50, 20);
    avPan.add(mon);

    tue = new JLabel("Tue");
    tue.setBounds(5, 80, 50, 20);
    avPan.add(tue);

    wed = new JLabel("Wed");
    wed.setBounds(5, 110, 50, 20);
    avPan.add(wed);

    thur = new JLabel("Thur");
    thur.setBounds(5, 140, 50, 20);
    avPan.add(thur);

    fri = new JLabel("Fri");
    fri.setBounds(5, 170, 50, 20);
    avPan.add(fri);

    sat = new JLabel("Sat");
    sat.setBounds(5, 200, 50, 20);
    avPan.add(sat);

    avPanel = new JPanel();
    avPanel.setLayout(new GridLayout(0, 2));
    avPanel.setBounds(36, 20, 130, 200);
    avPanel.setBackground(Color.BLACK);
    avPan.add(avPanel);

    for (int ii = 0; ii < 14; ii++)
    {
      JTextField jta;
      if (ii % 2 == 0)
      {
        jta = new JTextField();
        jta.setBounds(0, 30 * (ii / 2), 40, 25);
      }
      else
      {
        jta = new JTextField();
        jta.setBounds(50, 30 * ((ii - 1) / 2), 40, 25);
      }
      availability.add(jta);
    }

    for (JTextField jta : availability)
    {
      avPanel.add(jta);
    }

    SimpleAttributeSet aSet = new SimpleAttributeSet();
    SimpleAttributeSet bSet = new SimpleAttributeSet();
    StyleConstants.setAlignment(bSet, StyleConstants.ALIGN_CENTER);
    err = new JTextPane();
    err.setBounds(10, 500, 170, 60);
    StyledDocument doc = err.getStyledDocument();
    doc.setCharacterAttributes(105, doc.getLength() - 105, aSet, false);
    doc.setParagraphAttributes(0, 104, bSet, false);
    err.setEditable(false);
    add(err);

    addEmployee = new JButton("Add Employee");
    addEmployee.setBounds(25, 570, 150, 30);
    addEmployee.addActionListener(new EmployeeListener());
    add(addEmployee);

    removeEmployee = new JButton("Remove Employee");
    removeEmployee.setBounds(25, 600, 150, 30);
    removeEmployee.addActionListener(new EmployeeListener());
    add(removeEmployee);
  }

  private void setError(String message)
  {
    err.setText(message);
  }

  private class EmployeeListener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent ae)
    {
      //Add/Edit Employee
      if (ae.getSource() == addEmployee)
      {
        setTextAreasColor(Color.WHITE);
        if (checkEmployee())
        {
          if (nameNotFound())
          {
            Employee emp = new Employee(firstNameArea.getText(), lastNameArea.getText(),
                inshop.isSelected(), driver.isSelected(), canDouble.isSelected(),
                Integer.parseInt(maxNumHoursArea.getText()), getAvailabilityFromFields());
            mainPanel.getEmployeeList().add(emp);
            setError("Employee Added");
            System.out.println(emp.toString(true));
          }
          else
          {
            mainPanel.getEmployeeList().editEmployee(firstNameArea.getText(), lastNameArea.getText(),
                inshop.isSelected(), driver.isSelected(), canDouble.isSelected(),
                Integer.parseInt(maxNumHoursArea.getText()), getAvailabilityFromFields(), mainPanel.getSchedule());
            setError("Employee Edited");
          }
        }
      }
      //Remove Employee
      else if (ae.getSource() == removeEmployee)
      {
        if (!nameNotFound())
        {
          mainPanel.removeEmployee(firstNameArea.getText() + " " + lastNameArea.getText());
        }
      }
      //Handle Employee Box
      else if (ae.getSource() == employeeBox)
      {
        setTextAreasColor(Color.WHITE);
        Employee emp = (Employee) employeeBox.getSelectedItem();
        firstNameArea.setText(emp.firstName);
        lastNameArea.setText(emp.lastName);
        inshop.setSelected(emp.isInshop());
        driver.setSelected(emp.isDriver());
        canDouble.setSelected(emp.canDouble);
        maxNumHoursArea.setText("" + emp.getMaxNumHours());
        for (int ii = 0; ii < availability.size(); ii += 2)
        {
          availability.get(ii).setText("" + emp.getAvailability().get(ii / 2)[0]);
          availability.get(ii + 1).setText("" + emp.getAvailability().get(ii / 2)[1]);
        }
      }
    }
  }

  private ArrayList<int[]> getAvailabilityFromFields()
  {
    ArrayList<int[]> av = new ArrayList<int[]>();
    for (int ii = 0; ii < availability.size(); ii += 2)
    {
      av.add(new int[] {Integer.parseInt(availability.get(ii).getText()),
          Integer.parseInt(availability.get(ii + 1).getText())});
    }
    return av;
  }

  public void setFieldsToMatchEmployee(Employee emp)
  {
    firstNameArea.setText(emp.firstName);
    lastNameArea.setText(emp.lastName);
    inshop.setSelected(emp.isInshop());
    driver.setSelected(emp.isDriver());
    canDouble.setSelected(emp.canDouble);
    maxNumHoursArea.setText("" + emp.getMaxNumHours());
    for (int ii = 0; ii < availability.size(); ii += 2)
    {
      availability.get(ii).setText("" + emp.getAvailability().get(ii / 2)[0]);
      availability.get(ii + 1).setText("" + emp.getAvailability().get(ii / 2)[1]);
    }
  }

  private boolean checkEmployee()
  {
    boolean flag = true;
    if (firstNameArea.getText().length() > 0)
    {
      firstNameArea.setBackground(Color.GREEN);
    }
    else
    {
      flag = false;
      firstNameArea.setBackground(Color.RED);
    }
    if (lastNameArea.getText().length() > 0)
    {
      lastNameArea.setBackground(Color.GREEN);
    }
    else
    {
      flag = false;
      lastName.setBackground(Color.RED);
    }
    if (!(inshop.isSelected() || driver.isSelected()))
    {
      inshop.setForeground(Color.RED);
      driver.setForeground(Color.RED);
      flag = false;
      setError("Must select Driver or Inshop");
    }
    return flag && checkIntegers();
  }

  private boolean checkIntegers()
  {
    boolean ret = true;
    // Max Num Hours
    try
    {
      if (Integer.parseInt(maxNumHoursArea.getText()) < 1)
      {
        maxNumHoursArea.setBackground(Color.RED);
        setError("Max hours per week must \nbe greater than 1");
        ret = false;
      }
      else
      {
        maxNumHoursArea.setBackground(Color.GREEN);
      }
    }
    catch (NumberFormatException nfe)
    {
      maxNumHoursArea.setBackground(Color.RED);
      setError("Make sure max hour entry is a number.");
      ret = false;
    }

    // Availability
    try
    {
      for (JTextField jta : availability)
      {
        if(jta.getText().equals(""))
        {
          jta.setText("-1");
        }
        int av = Integer.parseInt(jta.getText());
        if ((av < 1 || av > 24) && av != -1)
        {
          jta.setBackground(Color.RED);
          setError("Make sure all availability\nentries are between\n1 and 24.");
          ret = false;
        }
        else
        {
          jta.setBackground(Color.GREEN);
        }
      }
    }
    catch (NumberFormatException nfe)
    {
      setError("Make all availability entries are numbers.");
      ret = false;
    }
    return ret;
  }

  private void setTextAreasColor(Color color)
  {
    firstNameArea.setBackground(color);
    lastNameArea.setBackground(color);
    maxNumHoursArea.setBackground(color);
    for (int ii = 0; ii < availability.size(); ii++)
    {
      availability.get(ii).setBackground(color);
    }
    inshop.setForeground(Color.BLACK);
    driver.setForeground(Color.BLACK);
  }

  public void nameChanged()
  {
    System.out.println("AAAAAAA" + nameNotFound());
    if (nameNotFound())
    {
      addEmployee.setText("Add Employee");
      emptyAreasExceptFNLN();
      setTextAreasColor(mainPanel.white);
      setError("");
    }
    else
    {
      setError("");
      addEmployee.setText("Edit Employee");
      employeeBox.setSelectedItem(firstNameArea.getText() + " " + lastNameArea.getText());
    }
  }

  private void emptyAreasExceptFNLN()
  {
    inshop.setSelected(false);
    driver.setSelected(false);
    canDouble.setSelected(false);
    maxNumHoursArea.setText("");
    for (JTextField jtf : availability)
    {
      jtf.setText("");
    }
  }

  public Employee getEmployee(String string)
  {
    for (Employee e : mainPanel.getEmployeeList())
    {
      if (e.fullName.equals(firstNameArea.getText() + " " + lastNameArea.getText()))
      {
        return e;
      }
    }
    return null;
  }

  private class NameListener implements DocumentListener
  {

    @Override
    public void changedUpdate(DocumentEvent de)
    {
      System.out.println("SS");
      nameChanged();
    }

    @Override
    public void insertUpdate(DocumentEvent de)
    {
      nameChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent de)
    {
      nameChanged();
    }
  }

  private boolean nameNotFound()
  {
    if(mainPanel.getEmployeeList().findName(getFullNameFromFields()) == null)
    {
      return true;
    }
    return false;
  }

  private String getFullNameFromFields()
  {
    return firstNameArea.getText() + " " + lastNameArea.getText();
  }

  public EmployeeBox getEmployeeBox()
  {
    return employeeBox;
  }

  public void setEmployeeBox(EmployeeBox employeeBox)
  {
    this.employeeBox = employeeBox;
  }
}
