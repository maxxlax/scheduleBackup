package fieldmap;

import javax.swing.JTextField;

import scheduling.Shift;

@SuppressWarnings("serial")
public class ShiftField extends JTextField
{
  private Shift shift;
  private boolean empty;
  private boolean filler = false;

  public ShiftField()
  {
    setEditable(false);
    empty = true;
    filler = true;
  }

  public ShiftField(Shift shift)
  {
    this.shift = shift;
    setEditable(false);
    empty = false;

  }

  public ShiftField(String string)
  {
    super(string);
  }

  public void setShift(Shift shift)
  {
    this.shift = shift;
    if (filler)
    {
      int start = shift.startTime;
      if (start > 12)
      {
        start -= 12;
      }
      int end = shift.endTime;
      if (end > 12)
      {
        end -= 12;
      }
      setText(start + "-" + end);
    }
    else
    {
      setText(shift.employee.firstName);
    }
    empty = false;
  }

  public Shift getShift()
  {
    return shift;
  }

  public void emptyShift()
  {
    empty = true;
    setText("");
  }

  public boolean isEmpty()
  {
    return empty;
  }

  public void setFiller(boolean filler)
  {
    this.filler = filler;
  }
}
