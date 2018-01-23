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
  ArrayList<ShiftField> fillers;
  HashMap<Day, HashMap<String, ArrayList<ShiftField>>> shiftFields;
  HashMap<String, ArrayList<ShiftField>> shiftHeaders;
  MainPanel mainPanel;
  private ShiftEditor shiftEditor;

  public SchedulePane(MainPanel mainPanel, ShiftEditor shiftEditor)
  {
    setLayout(null);
    setBackground(mainPanel.red);
    int boxWidth = 76;
    int boxHeight = 32;
    this.mainPanel = mainPanel;
    this.shiftEditor = shiftEditor;

    shiftFields = new HashMap<Day, HashMap<String, ArrayList<ShiftField>>>();

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
    
    ArrayList<ShiftField> ishiftHeaders = new ArrayList<ShiftField>();
    for(int ii = 2; ii < 10; ii++)
    {
      ShiftField sf = new ShiftField();
      sf.setBounds(boxWidth, boxHeight * ii, boxWidth, boxHeight);
      sf.addFocusListener(new FillerAreaListener());
      ishiftHeaders.add(sf);
      add(sf);
    }
    
    ArrayList<ShiftField> dshiftHeaders = new ArrayList<ShiftField>();
    for(int ii = 11; ii < 19; ii++)
    {
      ShiftField sf = new ShiftField();
      sf.setBounds(boxWidth, boxHeight * ii, boxWidth, boxHeight);
      sf.addFocusListener(new FillerAreaListener());
      dshiftHeaders.add(sf);
      add(sf);
    }
    
    shiftHeaders = new HashMap<String, ArrayList<ShiftField>>();
    shiftHeaders.put("InShop", ishiftHeaders);
    shiftHeaders.put("Driver", dshiftHeaders);
    
    //Begin Creating Week
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

      shiftFields.put(day, idMap);
    }
    for (Day day : new Week())
    {
      for (ShiftField sa : shiftFields.get(day).get("InShop"))
      {
        add(sa);
      }

      for (ShiftField sa : shiftFields.get(day).get("Driver"))
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
        shiftHeaders.get("InShop").get(iCounter).setShift(shift);
        shiftFields.get(day).get("InShop").get(iCounter).setShift(shift);
      }
      else if (shift.type.equals(ShiftType.Driver))
      {
        shiftHeaders.get("Driver").get(dCounter).setShift(shift);
        shiftFields.get(day).get("Driver").get(dCounter).setShift(shift);
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
    for (int ii = iCounter; ii < shiftFields.get(day).get("InShop").size(); ii++)
    {
      shiftFields.get(day).get("InShop").get(ii).emptyShift();
    }
    for (int ii = dCounter; ii < shiftFields.get(day).get("Driver").size(); ii++)
    {
      shiftFields.get(day).get("Driver").get(ii).emptyShift();
    }
  }

  public int getShiftHeaderID(ShiftField current)
  {
    if(shiftHeaders.get("InShop").indexOf(current) == -1)
    {
      return shiftHeaders.get("Driver").indexOf(current);
    }
    return shiftHeaders.get("InShop").indexOf(current);
  }

  public String getShiftHeaderInDr(ShiftField current)
  {
    if(shiftHeaders.get("InShop").indexOf(current) == -1)
    {
      return "Driver";
    }
    return "InShop";
  }

  public String getShiftAMPM(Shift shift)
  {
    if (shift.startTime <= 12)
    {
      return "am";
    }
    return "pm";
  }

  public int getShiftFieldID(ShiftField current)
  {
    if(shiftFields.get(Day.Sunday).get("InShop").indexOf(current) == -1)
    {
      return shiftFields.get(Day.Sunday).get("Driver").indexOf(current);
    }
    return shiftFields.get(Day.Sunday).get("InShop").indexOf(current);
  }

  public String getShiftFieldInDr(ShiftField current)
  {
    if(shiftFields.get(Day.Sunday).get("InShop").indexOf(current) == -1)
    {
      return "Driver";
    }
    return "InShop";
  }
  public void emptyAll()
  {
    for (ShiftField sf : shiftHeaders.get("InShop"))
    {
      sf.setText("");
    }
    for (ShiftField sf : shiftHeaders.get("Driver"))
    {
      sf.setText("");
    }
    for (Day day : new Week())
    {
      for (ShiftField sf : shiftFields.get(day).get("am"))
      {
        sf.setText("");
      }
      for (ShiftField sf : shiftFields.get(day).get("pm"))
      {
        sf.setText("");
      }
    }
  }

  public void emptyFillers()
  {
    for (ShiftField sf : shiftHeaders.get("InShop"))
    {
      sf.emptyShift();
    }
    
    for (ShiftField sf : shiftHeaders.get("Driver"))
    {
      sf.emptyShift();
    }
  }

  public int numInshop()
  {
    for (int ii = 0; ii < 8; ii++)
    {
      if (shiftHeaders.get("InShop").get(ii).isEmpty())
      {
        return ii;
      }
    }
    return -1;
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
        int response = JOptionPane.showOptionDialog(null,
            "What would you like to do with this shift?\n" + current.getShift().toString(false),
            "Shift Selected", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, options, options[2]);
  
        int id = getShiftHeaderID(current);
        if(getShiftHeaderInDr(current).equals("Driver"))
        {
          id += numInshop();
        }
        String ampm = getShiftAMPM(current.getShift());
    
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
      ShiftField current = (ShiftField) fe.getSource();
      int id = getShiftFieldID(current);
      if(getShiftFieldInDr(current).equals("Driver"))
      {
        id += numInshop();
      }
      String ampm = getShiftAMPM(current.getShift());
      mainPanel.viewShiftInfo(current.getShift(), ampm, id);
    }

    @Override
    public void focusLost(FocusEvent fe)
    {
    }

  }
}
