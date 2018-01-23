package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import scheduling.Schedule;
import scheduling.Shift;

@SuppressWarnings("serial")
public class ShiftEditor extends JPanel
{
  JLabel noneSelected, title, startEnd, successfulEdit;
  JTextField startField, endField;
  JButton editButton, cancelButton;
  int start, end;
  MainPanel mainPanel;
  boolean isAM;
  private int id;
  private String ampm;
  private Color bg, text;
  private Schedule schedule;

  public ShiftEditor(MainPanel mainPanel, Schedule schedule)
  {
    bg = mainPanel.black;
    text = mainPanel.white;
    
    setLayout(null);
    setBackground(bg);
    this.mainPanel = mainPanel;
    this.schedule = schedule;
    
    title = new JLabel("Shift Editor", JLabel.CENTER);
    title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 20));
    title.setForeground(text);
    title.setBounds(0, 5, 189, 30);
    add(title);

    successfulEdit = new JLabel("Successfully Edited Shift", JLabel.CENTER);
    successfulEdit.setForeground(text);
    successfulEdit.setBounds(5, 50, 184, 20);
    successfulEdit.setVisible(false);
    add(successfulEdit);

    noneSelected = new JLabel("Click on a shift to edit here", JLabel.CENTER);
    noneSelected.setForeground(text);
    noneSelected.setBounds(5, 80, 184, 30);
    add(noneSelected);

    startEnd = new JLabel("start         end");
    startEnd.setForeground(text);
    startEnd.setBounds(60, 40, 200, 20);
    startEnd.setVisible(false);
    add(startEnd);

    startField = new JTextField("11");
    startField.setBounds(45, 60, 50, 30);
    startField.setVisible(false);
    add(startField);

    endField = new JTextField("15");
    endField.setBounds(100, 60, 50, 30);
    endField.setVisible(false);
    add(endField);

    editButton = new JButton("Edit Shift");
    editButton.setBounds(45, 120, 105, 30);
    editButton.setVisible(false);
    editButton.addActionListener(new EditListener());
    add(editButton);

    cancelButton = new JButton("Cancel");
    cancelButton.setBounds(55, 155, 85, 20);
    cancelButton.setVisible(false);
    cancelButton.addActionListener(new EditListener());
    add(cancelButton);
  }

  public void editShift(Shift shift, String ampm, int id)
  {
    showShiftEditor();
    this.ampm = ampm;
    this.id = id;
    startField.setText(shift.startTime + "");
    endField.setText(shift.endTime + "");
    isAM = shift.isAM;
  }

  private void completeShiftEdit()
  {
    schedule.editShift(start, end, ampm, id);
    mainPanel.redrawSchedule();
    hideShiftEditor();
  }
  private void showShiftEditor()
  {
    mainPanel.getJtp().setSelectedIndex(3);
    ((AddShiftPanel) getParent()).switchToShiftEditor();
    successfulEdit.setVisible(false);
    noneSelected.setVisible(false);
    startEnd.setVisible(true);
    startField.setVisible(true);
    endField.setVisible(true);
    editButton.setVisible(true);
    cancelButton.setVisible(true);
  }

  private void hideShiftEditor()
  {
    ((AddShiftPanel) getParent()).switchOffShiftEditor();
    noneSelected.setVisible(true);
    startEnd.setVisible(false);
    startField.setVisible(false);
    endField.setVisible(false);
    editButton.setVisible(false);
    cancelButton.setVisible(false);
  }

  public boolean checkFields()
  {
    boolean check = true;
    int startTime = -1, endTime = -1;
    try
    {
      startTime = Integer.parseInt(startField.getText());
    }
    catch (NumberFormatException nfe)
    {
      startField.setBackground(Color.RED);
      check = false;
    }
  
    try
    {
      endTime = Integer.parseInt(endField.getText());
    }
    catch (NumberFormatException nfe)
    {
      endField.setBackground(Color.RED);
      check = false;
    }
  
    if (startTime == -1 || startTime < 5 || startTime > 20)
    {
      startField.setBackground(Color.RED);
      check = false;
    }
    if (endTime == -1 || endTime < 10 || endTime > 24)
    {
      endField.setBackground(Color.RED);
      check = false;
    }
    if (isAM)
    {
      if (startTime < 1 || startTime > 16)
      {
        startField.setBackground(Color.RED);
        check = false;
      }
      if (endTime <= startTime || endTime > 16)
      {
        endField.setBackground(Color.RED);
        check = false;
      }
    }
    else
    {
      if (startTime < 12 || startTime > 20)
      {
        startField.setBackground(Color.RED);
        check = false;
      }
      if (endTime <= startTime || endTime > 24)
      {
        endField.setBackground(Color.RED);
        check = false;
      }
    }
    if (check)
    {
      start = startTime;
      end = endTime;
    }
    return check;
  }

  private class EditListener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent ae)
    {
      if (ae.getSource().equals(editButton))
      {
        if (checkFields())
        {
          completeShiftEdit();
          startField.setBackground(Color.WHITE);
          endField.setBackground(Color.WHITE);
        }
      }
      else if (ae.getSource().equals(cancelButton))
      {
        hideShiftEditor();
      }
    }
  }
}
