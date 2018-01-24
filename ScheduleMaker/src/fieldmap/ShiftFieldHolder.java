package fieldmap;

import gui.SchedulePane;

public class ShiftFieldHolder
{
  private ShiftHeaderMap shiftHeaders;
  private ShiftFieldMap shiftFields;
  
  public ShiftFieldHolder(int boxWidth, int boxHeight, SchedulePane sp)
  {
    //shiftHeaders = new ShiftHeaderMap(boxWidth, boxHeight, sp);
    shiftFields = new ShiftFieldMap(boxWidth, boxHeight, sp);
  }
  
  public void emptyAll()
  {
    shiftHeaders.emptyAll();
    shiftFields.emptyAll();
  }
}
