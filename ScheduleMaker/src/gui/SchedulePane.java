package gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fieldmap.ShiftField;
import fieldmap.ShiftFieldMap;
import fieldmap.ShiftHeaderMap;
import scheduling.Day;
import scheduling.Shift;
import scheduling.ShiftType;

@SuppressWarnings("serial")
public class SchedulePane extends JPanel
{
  ArrayList<ShiftField> fillers;
  ShiftFieldMap shiftFields;
  ShiftHeaderMap shiftHeaders;
  MainPanel mainPanel;
  private ShiftEditor shiftEditor;
  private String ampm;

  public SchedulePane(MainPanel mainPanel, ShiftEditor shiftEditor, String ampm)
  {
    setLayout(null);
    setBackground(mainPanel.red);
    int boxWidth = 76;
    int boxHeight = 32;
    this.mainPanel = mainPanel;
    this.shiftEditor = shiftEditor;
    this.ampm = ampm;

    // Build Fillers
    fillers = new ArrayList<ShiftField>();
    int column = 0;
    for (int row = 0; row < 19; row++)
    {
      ShiftField sf = new ShiftField();
      sf.setBounds(column * boxWidth, row * boxHeight, boxWidth, boxHeight);
      if (column == 1 && ((row > 1 && row < 10) || (row > 10 && row < 20)))
      {

      }
      fillers.add(sf);
    }
    fillers.get(1).setText("InShop");
    fillers.get(10).setText("Driver");
    for (int ii = 0; ii < fillers.size(); ii++)
    {
      add(fillers.get(ii));
    }
    ShiftField filler1 = new ShiftField();
    filler1.setBounds(boxWidth, 0, boxWidth, boxHeight);
    filler1.setEditable(false);
    add(filler1);

    ShiftField filler2 = new ShiftField();
    filler2.setBounds(boxWidth, boxHeight, boxWidth, boxHeight);
    filler2.setEditable(false);
    add(filler2);

    ShiftField filler3 = new ShiftField();
    filler3.setBounds(boxWidth, boxHeight * 10, boxWidth, boxHeight);
    filler3.setEditable(false);
    add(filler3);

    shiftHeaders = new ShiftHeaderMap(this);
    shiftFields = new ShiftFieldMap(boxWidth, boxHeight, this);

    for (ShiftField sf : shiftFields)
    {
      add(sf);
    }
  }

  public void drawShifts(ArrayList<Shift> arrayList, Day day)
  {
    int iCounter = 0, dCounter = 0;
    for (Shift shift : arrayList)
    {
      if (shift.type.equals(ShiftType.InShop))
      {
        shiftHeaders.get("inshop").get(iCounter).setShift(shift);
        shiftFields.get(day).get("inshop").get(iCounter).setShift(shift);
        iCounter++;
      }
      else if (shift.type.equals(ShiftType.Driver))
      {
        shiftHeaders.get("driver").get(dCounter).setShift(shift);
        shiftFields.get(day).get("driver").get(dCounter).setShift(shift);
        dCounter++;
      }
    }
    // Empty out other shifts
    for (int ii = iCounter; ii < shiftFields.get(day).get("inshop").size(); ii++)
    {
      shiftFields.get(day).get("inshop").get(ii).emptyShift();
    }
    for (int ii = dCounter; ii < shiftFields.get(day).get("driver").size(); ii++)
    {
      shiftFields.get(day).get("driver").get(ii).emptyShift();
    }
  }

  public void emptyAll()
  {
    emptyHeaders();
    emptyFields();
  }

  public void emptyHeaders()
  {
    shiftHeaders.emptyAll();
  }
  
  public void emptyFields()
  {
    shiftFields.emptyAll();
  }
  public void newHeaderFocus(ShiftField current, int id)
  {
    System.out.println("id: " + id);
    if (!current.isEmpty())
    {
      requestFocus();
      String[] options = {"Edit Shift", "Delete Shift", "Cancel"};
      int response = JOptionPane.showOptionDialog(null,
          "What would you like to do with this shift?\n" + current.getShift().toString(false),
          "Shift Selected", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
          options, options[2]);
  
      if (response == 0)
      {
        shiftEditor.editShift(current.getShift(), ampm, id);
      }
      else if (response == 1)
      {
        if (JOptionPane.showConfirmDialog(null,
            "Are you sure you want to remove this shift and all assignments made to it?") == 0)
        {
          mainPanel.removeShift(ampm, id);
        }
      }
    }
  }

  public void newFieldFocus(ShiftField current, int id)
  {
    if (!current.isEmpty())
    {
      mainPanel.viewShiftInfo(current.getShift(), ampm, id);
    }
  }

  public String getAMPM()
  {
    return ampm;
  }
}
