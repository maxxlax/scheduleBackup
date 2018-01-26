package fieldmap;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import gui.SchedulePane;

@SuppressWarnings("serial")
public class FieldShiftMap extends AbstractShiftMap
{

  public FieldShiftMap(SchedulePane schedulePane, int offset)
  {
    super(schedulePane, offset);
  }

  @Override
  protected void addFocusAndPane()
  {
    for(ShiftField sf: this)
    {
      sf.addFocusListener(new ShiftAreaListener());
      sf.setFiller(false);
      schedulePane.add(sf);
    }
  }

  private class ShiftAreaListener implements FocusListener
  {

    @Override
    public void focusGained(FocusEvent fe)
    {
      ShiftField current = (ShiftField) fe.getSource();
      schedulePane.newFieldFocus(current, getID(current));
    }

    @Override
    public void focusLost(FocusEvent fe)
    {
    }

  }
}
