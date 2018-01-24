package fieldmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JTextField;

import gui.SchedulePane;
import scheduling.Day;
import scheduling.Week;

@SuppressWarnings("serial")
public class ShiftFieldMap extends HashMap<Day, ShiftMap> implements Iterable<ShiftField>
{
  private int boxWidth, boxHeight;
  private SchedulePane schedulePane;

  public ShiftFieldMap(int boxWidth, int boxHeight, SchedulePane schedulePane)
  {
    super();
    this.boxWidth = boxWidth;
    this.boxHeight = boxHeight;
    this.schedulePane = schedulePane;
    buildAndPut();
  }

  private void buildAndPut()
  {
    for (Day day : new Week())
    {
      JTextField jta = new JTextField(day.toString());
      jta.setEditable(false);
      JTextField jti = new JTextField();
      jti.setEditable(false);
      JTextField jtd = new JTextField();
      jtd.setEditable(false);

      int offset = 2;
      switch (day)
      {
        case Sunday:
          offset = 2;
          break;
        case Monday:
          offset = 3;
          break;
        case Tuesday:
          offset = 4;
          break;
        case Wednesday:
          offset = 5;
          break;
        case Thursday:
          offset = 6;
          break;
        case Friday:
          offset = 7;
          break;
        case Saturday:
          offset = 8;
          break;
        default:
          break;
      }

      jta.setBounds(boxWidth * offset, 0, boxWidth, boxHeight);
      jti.setBounds(boxWidth * offset, boxHeight, boxWidth, boxHeight);
      jtd.setBounds(boxWidth * offset, boxHeight * 10, boxWidth, boxHeight);
      schedulePane.add(jta);
      schedulePane.add(jti);
      schedulePane.add(jtd);

      // Build ShiftAreas
      ShiftMap idMap = new ShiftMap(schedulePane, offset);
      put(day, idMap);
    }
  }

  @Deprecated
  public ArrayList<ShiftField> getDayAsOneList(Day day)
  {
    ArrayList<ShiftField> sf = new ArrayList<ShiftField>();
    sf.addAll(get(day).get("inshop"));
    sf.addAll(get(day).get("driver"));
    return sf;
  }

  @Override
  public Iterator<ShiftField> iterator()
  {
    Iterator<ShiftField> it = new Iterator<ShiftField>()
    {
      private Day day = Day.Sunday;
      private String ID = "inshop";
      private ShiftField current = null;

      @Override
      public boolean hasNext()
      {
        if (current == null)
        {
          for (Day day : new Week())
          {
            if (get(day).get("inshop").size() > 0)
            {
              current = get(day).get("inshop").get(0);
              return true;
            }
            if (get(day).get("driver").size() > 0)
            {
              current = get(day).get("driver").get(0);
              return true;
            }
          }
        }
        else
        {
          boolean done = false;
          int index = get(day).get(ID).indexOf(current);
          if (get(day).get(ID).size() > index + 1)
          {
            current = get(day).get(ID).get(index + 1);
            return true;
          }
          else if (ID.equals("driver") && day.equals(Day.Saturday))
          {
            done = true;
          }
          else
          {
            if (ID.equals("inshop"))
            {
              ID = "driver";
            }
            else
            {
              ID = "inshop";
              day = new Week().get(new Week().indexOf(day) + 1);
            }
            while (get(day).get(ID).size() == 0 && !done)
            {
              if (ID.equals("inshop"))
              {
                ID = "driver";
              }
              else if (ID.equals("driver"))
              {
                if (day.equals(Day.Saturday))
                {
                  done = true;
                }
                else
                {
                  day = new Week().get(new Week().indexOf(day) + 1);
                  ID = "inshop";
                }
              }
            }
          }
          if (!done)
          {
            current = get(day).get(ID).get(0);
            return true;
          }
        }
        return false;
      }

      @Override
      public ShiftField next()
      {
        return current;
      }

    };
    return it;
  }

  public void emptyAll()
  {
    for (ShiftField sf : this)
    {
      sf.emptyShift();
    }
  }
}
