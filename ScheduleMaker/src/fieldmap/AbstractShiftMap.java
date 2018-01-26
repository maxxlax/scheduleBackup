package fieldmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import gui.SchedulePane;
import scheduling.ShiftType;

@SuppressWarnings("serial")
public abstract class AbstractShiftMap extends HashMap<String, ArrayList<ShiftField>>
    implements Iterable<ShiftField>
{
  protected int boxWidth = 76;
  protected int boxHeight = 32;
  protected SchedulePane schedulePane;

  public AbstractShiftMap(SchedulePane schedulePane, int offset)
  {
    super();
    this.schedulePane = schedulePane;
    buildAndPutLists(offset);
    addFocusAndPane();
  }

  protected void buildAndPutLists(int offset)
  {
    ArrayList<ShiftField> ishiftHeaders = new ArrayList<ShiftField>();
    for (int ii = 2; ii < 10; ii++)
    {
      ShiftField sf = new ShiftField();
      sf.setBounds(boxWidth * offset, boxHeight * ii, boxWidth, boxHeight);
      ishiftHeaders.add(sf);
    }
    
    ArrayList<ShiftField> dshiftHeaders = new ArrayList<ShiftField>();
    for (int ii = 11; ii < 19; ii++)
    {
      ShiftField sf = new ShiftField();
      sf.setBounds(boxWidth * offset, boxHeight * ii, boxWidth, boxHeight);
      dshiftHeaders.add(sf);
    }
    put("inshop", ishiftHeaders);
    put("driver", dshiftHeaders);
  }
  protected abstract void addFocusAndPane();

  public void emptyAll()
  {
    for (ShiftField sf : this)
    {
      sf.emptyShift();
    }
  }

  public int getID(ShiftField current)
  {
    int index = 0;
    for (ShiftField sf : this)
    {
      if (sf.equals(current))
      {
        return index;
      }
      if(!sf.isEmpty())
      {
        index++;
      }
    }
    return -1;
  }

  public String getInDr(ShiftField current)
  {
    if (current.getShift().type.equals(ShiftType.InShop))
    {
      return "inshop";
    }
    return "driver";
  }
  
  @Override
  public Iterator<ShiftField> iterator()
  {
    Iterator<ShiftField> it = new Iterator<ShiftField>()
    {
      private String ID = "inshop";
      private ShiftField current = null;

      @Override
      public boolean hasNext()
      {
        if (current == null)
        {
          if (get("inshop").size() > 0)
          {
            current = get("inshop").get(0);
            return true;
          }
          else if (get("driver").size() > 0)
          {
            current = get("driver").get(0);
            return true;
          }

        }
        else
        {
          int index = get(ID).indexOf(current);
          if (get(ID).size() > index + 1)
          {
            current = get(ID).get(index + 1);
            return true;
          }
          else if (ID.equals("inshop"))
          {
            if (get("driver").size() > 0)
            {
              ID = "driver";
              current = get(ID).get(0);
              return true;
            }
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
}
