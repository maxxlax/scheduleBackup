package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import scheduling.Shift;

@SuppressWarnings("serial")
public class CreateSchedulePanel extends JPanel
{
  private EmployeeSetter employeeSetter;;
  private JTextArea info;
  private JButton editEmployee, setEmployee, editShift, removeEmployee, cancelButton,
      fillOpenShifts, reschedule;
  private Shift currentShift;
  private MainPanel mainPanel;
  private String ampm;
  private int id;

  public CreateSchedulePanel(MainPanel mainPanel, SchedulePanel schedulePanel)
  {
    setLayout(null);
    setBackground(mainPanel.red);

    this.mainPanel = mainPanel;

    employeeSetter = new EmployeeSetter(mainPanel, this);
    add(employeeSetter);

    info = new JTextArea();
    info.setFont(new Font("monospaced", Font.PLAIN, 12));

    JScrollPane jsp = new JScrollPane(info);
    jsp.setBounds(10, 10, 170, 450);
    add(jsp);

    editEmployee = new JButton("View Employee");
    editEmployee.setBounds(10, 510, 170, 30);
    editEmployee.setVisible(false);
    editEmployee.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        editEmployee();
      }
    });
    add(editEmployee);

    setEmployee = new JButton("Set Employee");
    setEmployee.setBounds(10, 510, 170, 30);
    setEmployee.setVisible(false);
    setEmployee.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        openSetter();
      }
    });
    add(setEmployee);

    editShift = new JButton("Edit Shift");
    editShift.setBounds(10, 470, 170, 30);
    editShift.setVisible(false);
    editShift.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        editShift();
      }
    });
    add(editShift);

    removeEmployee = new JButton("Remove Employee From Shift");
    removeEmployee.setBounds(10, 550, 170, 30);
    removeEmployee.setVisible(false);
    removeEmployee.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        currentShift.empty();
        mainPanel.redrawSchedule();
        viewShift(currentShift, ampm, id);
      }
    });
    add(removeEmployee);

    cancelButton = new JButton("Cancel");
    cancelButton.setBounds(20, 590, 150, 30);
    cancelButton.setVisible(false);
    cancelButton.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        emptyView();
      }
    });
    add(cancelButton);

    fillOpenShifts = new JButton("Fill Open Shifts");
    fillOpenShifts.setBounds(20, 560, 150, 30);
    fillOpenShifts.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        mainPanel.fillOpenShifts();
        info.setText("");
      }
    });
    add(fillOpenShifts);

    reschedule = new JButton("Reschedule");
    reschedule.setBounds(20, 600, 150, 30);
    reschedule.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        mainPanel.getSchedule().emptyAllShifts();
        mainPanel.fillOpenShifts();
        info.setText("");
      }
    });
    add(reschedule);
  }

  public void emptyView()
  {
    fillOpenShifts.setVisible(true);
    reschedule.setVisible(true);
    removeEmployee.setVisible(false);
    cancelButton.setVisible(false);
    setEmployee.setVisible(false);
    editEmployee.setVisible(false);
    editShift.setVisible(false);
  }

  public void viewShift(Shift shift, String ampm, int id)
  {
    currentShift = shift;
    this.ampm = ampm;
    this.id = id;
    info.setText(shift.toString(true));
    closeSetter();
    fillOpenShifts.setVisible(false);
    reschedule.setVisible(false);
    removeEmployee.setVisible(true);
    cancelButton.setVisible(true);
    if (shift.filled)
    {
      setEmployee.setVisible(false);
      editEmployee.setVisible(true);
    }
    else
    {
      editEmployee.setVisible(false);
      setEmployee.setVisible(true);
    }
    editShift.setVisible(true);
  }

  public void editShift()
  {
    mainPanel.getShiftEditor().editShift(currentShift, ampm, id);
  }

  public void editEmployee()
  {
    mainPanel.viewEmployee(currentShift.employee);
  }

  private void openSetter()
  {
    info.setText("");
    info.setVisible(false);
    editEmployee.setVisible(false);
    setEmployee.setVisible(false);
    editShift.setVisible(false);
    employeeSetter.openSetter(currentShift);
  }

  public void closeSetter()
  {
    info.setVisible(true);
    employeeSetter.setVisible(false);
  }
}
