package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import employee.Employee;
import scheduling.Shift;

@SuppressWarnings("serial")
public class EmployeeSetter extends JPanel
{
  private JComboBox<Employee> employees;
  private MainPanel mainPanel;
  private JButton setButton, cancelButton;
  private JTextPane shiftPane, empPane;
  private CreateSchedulePanel csp;
  private JLabel title;
  private Employee currentEmp = null;
  private Shift currentShift = null;

  public EmployeeSetter(MainPanel mainPanel, CreateSchedulePanel csp)
  {
    setBounds(10, 10, 170, 540);
    setLayout(null);
    setVisible(false);
    setBackground(mainPanel.black);

    this.mainPanel = mainPanel;
    this.csp = csp;

    title = new JLabel("Set Employee", JLabel.CENTER);
    title.setBounds(10, 0, 150, 30);
    ComponentFormatter.formatBasicLarge(title);
    add(title);
    shiftPane = new JTextPane();
    shiftPane.setBounds(10, 50, 150, 120);
    add(shiftPane);

    empPane = new JTextPane();
    empPane.setBounds(10, 250, 150, 210);
    add(empPane);
    
    setButton = new JButton("Set Employee");
    setButton.setBounds(20, 470, 130, 30);
    setButton.addActionListener(new SetListener());
    add(setButton);

    cancelButton = new JButton("Cancel");
    cancelButton.setBounds(50, 505, 70, 30);
    cancelButton.addActionListener(new SetListener());
    add(cancelButton);
  }

  public void openSetter(Shift shift)
  {
    shiftPane.setText(shift.toString(true));
    employees = new JComboBox<Employee>();
    for (Employee employee : mainPanel.getEmployeeList())
    {
      employees.addItem(employee);
    }
    employees.setBounds(10, 210, 150, 30);
    employees.addActionListener(new SetListener());
    if(employees.getItemCount() > 0)
    {
      employees.setSelectedIndex(0);
    }
    add(employees);
    setVisible(true);
  }

  private class SetListener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent ae)
    {
      if(ae.getSource().equals(employees))
      {
        currentEmp = (Employee)employees.getSelectedItem();
        empPane.setText(currentEmp.toString(true));
      }
      else if(ae.getSource().equals(setButton))
      {
        if(currentEmp != null && currentShift != null)
        {
          if(currentEmp.canWorkShift(currentShift))
          {
            //currentShift.setEmployee(currentEmp);
          }
        }
        csp.closeSetter();
      }
      else if(ae.getSource().equals(cancelButton))
      {
        csp.closeSetter();
      }
    }
  }
}
