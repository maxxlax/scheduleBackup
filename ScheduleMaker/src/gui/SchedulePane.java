package gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import scheduling.Day;
import scheduling.Shift;
import scheduling.ShiftType;
import scheduling.Week;

@SuppressWarnings("serial")
public class SchedulePane extends JPanel
{
  ArrayList<ArrayList<ShiftField>> fillers;
  HashMap<Day, HashMap<String, ArrayList<ShiftField>>> shiftAreas;
  MainPanel mainPanel;

  public SchedulePane(MainPanel mainPanel)
  {
    setLayout(null);
    setBackground(mainPanel.red);
    int boxWidth = 76;
    int boxHeight = 32;
    this.mainPanel = mainPanel;

    shiftAreas = new HashMap<Day, HashMap<String, ArrayList<ShiftField>>>();

    // Build Fillers
    fillers = new ArrayList<ArrayList<ShiftField>>();
    for (int column = 0; column < 2; column++)
    {
      fillers.add(new ArrayList<ShiftField>());
      for (int row = 0; row < 19; row++)
      {
        ShiftField sf = new ShiftField();
        sf.setBounds(column * boxWidth, row * boxHeight, boxWidth, boxHeight);
        if (column == 1 && ((row > 1 && row < 10) || (row > 10 && row < 20)))
        {
          sf.addFocusListener(new FillerAreaListener());
        }
        fillers.get(column).add(sf);
      }
    }
    fillers.get(0).get(1).setText("InShop");
    fillers.get(0).get(10).setText("Driver");
    for (int ii = 0; ii < fillers.get(0).size(); ii++)
    {
      add(fillers.get(0).get(ii));
      add(fillers.get(1).get(ii));
    }
    for (Day day : new Week())
    {
      JTextField jta = new JTextField(day.toString());
      jta.setEditable(false);
      JTextField jti = new JTextField();
      jti.setEditable(false);
      JTextField jtd = new JTextField();
      jtd.setEditable(false);

      int x = 2;
      switch (day)
      {
        case Sunday:
          x = 2;
          break;
        case Monday:
          x = 3;
          break;
        case Tuesday:
          x = 4;
          break;
        case Wednesday:
          x = 5;
          break;
        case Thursday:
          x = 6;
          break;
        case Friday:
          x = 7;
          break;
        case Saturday:
          x = 8;
          break;
        default:
          break;
      }

      jta.setBounds(boxWidth * x, 0, boxWidth, boxHeight);
      jti.setBounds(boxWidth * x, boxHeight, boxWidth, boxHeight);
      jtd.setBounds(boxWidth * x, boxHeight * 10, boxWidth, boxHeight);
      add(jta);
      add(jti);
      add(jtd);

      // Build ShiftAreas

      HashMap<String, ArrayList<ShiftField>> idMap = new HashMap<String, ArrayList<ShiftField>>();

      ArrayList<ShiftField> iShifts = new ArrayList<ShiftField>();
      for (int ii = 0; ii < 8; ii++)
      {
        ShiftField sa = new ShiftField(new Shift(day, ShiftType.InShop));
        sa.setBounds(boxWidth * x, boxHeight * (ii + 2), boxWidth, boxHeight);
        sa.addFocusListener(new ShiftAreaListener());
        iShifts.add(sa);
      }
      idMap.put("InShop", iShifts);

      ArrayList<ShiftField> dShifts = new ArrayList<ShiftField>();
      for (int ii = 0; ii < 8; ii++)
      {
        ShiftField sa = new ShiftField(new Shift(day, ShiftType.Driver));
        sa.setBounds(boxWidth * x, boxHeight * (ii + 11), boxWidth, boxHeight);
        sa.addFocusListener(new ShiftAreaListener());
        dShifts.add(sa);
      }
      idMap.put("Driver", dShifts);

      shiftAreas.put(day, idMap);
    }
    for (Day day : new Week())
    {
      for (ShiftField sa : shiftAreas.get(day).get("InShop"))
      {
        add(sa);
      }

      for (ShiftField sa : shiftAreas.get(day).get("Driver"))
      {
        add(sa);
      }
    }
  }

  public void drawShifts(ArrayList<Shift> arrayList, Day day)
  {
    int iCounter = 0, dCounter = 0;
    for (Shift shift : arrayList)
    {
      if (shift.type.equals(ShiftType.InShop))
      {
        fillers.get(1).get(2 + iCounter).setShift(shift);
        shiftAreas.get(day).get("InShop").get(iCounter).setShift(shift);
      }
      else if (shift.type.equals(ShiftType.Driver))
      {
        fillers.get(1).get(11 + dCounter).setShift(shift);
        shiftAreas.get(day).get("Driver").get(dCounter).setShift(shift);
      }
      if (shift.type.equals(ShiftType.InShop))
      {
        iCounter++;
      }
      else if (shift.type.equals(ShiftType.Driver))
      {
        dCounter++;
      }
    }
    // Empty out other shifts
    for (int ii = iCounter; ii < shiftAreas.get(day).get("InShop").size(); ii++)
    {
      shiftAreas.get(day).get("InShop").get(ii).emptyShift();
    }
    for (int ii = dCounter; ii < shiftAreas.get(day).get("Driver").size(); ii++)
    {
      shiftAreas.get(day).get("Driver").get(ii).emptyShift();
    }
  }

  private class FillerAreaListener implements FocusListener
  {

    @Override
    public void focusGained(FocusEvent fe)
    {
      ShiftField current = (ShiftField) fe.getSource();
      if (!current.isEmpty())
      {
        requestFocus();
        String[] options = {"Edit Shift", "Delete Shift", "Cancel"};
        int response = JOptionPane.showOptionDialog(null, "What would you like to do with this shift?\n" + current.getShift().toString(false), "Shift Selected",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
            options[2]);
        if (response == 0)
        {
          mainPanel.viewShiftEditor(current.getShift());
        }
        else if (response == 1)
        {
          if (JOptionPane.showConfirmDialog(null,
              "Are you sure you want to remove this shift and all assignments made to it?") == 0)
          {
            int id = getFillerShiftID(current);
            String ampm;
            if (current.getShift().startTime <= 12)
            {
              ampm = "am";
            }
            else
            {
              ampm = "pm";
            }
            if (id > 1 && id < 10)
            {
              mainPanel.removeShift(ampm, id - 2);
            }
            else if (id > 10 && id < 20)
            {
              mainPanel.removeShift(ampm, (id - 11) + numInshop());
            }
          }
        }
      }
    }

    @Override
    public void focusLost(FocusEvent arg0)
    {
    }

  }

  private class ShiftAreaListener implements FocusListener
  {

    @Override
    public void focusGained(FocusEvent fe)
    {
      mainPanel.viewShiftInfo(((ShiftField) fe.getSource()).getShift());
    }

    @Override
    public void focusLost(FocusEvent fe)
    {
    }

  }

  public int getFillerShiftID(ShiftField current)
  {
    return fillers.get(1).indexOf(current);
  }

  public void emptyAll()
  {
    for (ShiftField sf : fillers.get(1))
    {
      sf.setText("");
    }
    for (Day day : new Week())
    {
      for (ShiftField sf : shiftAreas.get(day).get("am"))
      {
        sf.setText("");
      }
      for (ShiftField sf : shiftAreas.get(day).get("pm"))
      {
        sf.setText("");
      }
    }
  }

  public void emptyFillers()
  {
    for (ShiftField sf : fillers.get(1))
    {
      sf.emptyShift();
    }
  }

  public int numInshop()
  {
    int ret = 0;
    for (int ii = 2; ii < 10; ii++)
    {
      if (fillers.get(1).get(ii).isEmpty())
      {
        return ret;
      }
      ret++;
    }
    return ret;
  }
}
