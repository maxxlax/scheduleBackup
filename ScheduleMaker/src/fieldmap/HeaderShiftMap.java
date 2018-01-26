package fieldmap;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import gui.SchedulePane;

@SuppressWarnings("serial")
public class HeaderShiftMap extends  AbstractShiftMap implements Iterable<ShiftField>
{
  public HeaderShiftMap(SchedulePane schedulePane)
  {
    super(schedulePane, 1);
  }

  protected void addFocusAndPane()
  {
    for(ShiftField sf: this)
    {
      sf.addFocusListener(new HeaderListener());
      schedulePane.add(sf);
    }
  }

  private class HeaderListener implements FocusListener
  {

    @Override
    public void focusGained(FocusEvent fe)
    {
      ShiftField current = (ShiftField) fe.getSource();
      schedulePane.newHeaderFocus(current, getID(current));
    }

    @Override
    public void focusLost(FocusEvent arg0)
    {
    }

  }
}
