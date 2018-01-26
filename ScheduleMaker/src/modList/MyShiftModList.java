package modList;

import java.util.ArrayList;
import java.util.StringTokenizer;

import gui.MainPanel;
import scheduling.Day;
import scheduling.ShiftPeriod;

@SuppressWarnings("serial")
public class MyShiftModList extends AbstractModList
{

  public MyShiftModList(MainPanel mainPanel)
  {
    super(mainPanel);
    // TODO Auto-generated constructor stub
  }

  public void addShift(String str)
  {
    model.addElement(str);
  }

  @Override
  protected void reorganize()
  {
    ArrayList<ShiftPeriod> ssList = new ArrayList<ShiftPeriod>();
    for (int ii = 0; ii < model.getSize(); ii++)
    {
      char ap = 0;
      Day day;

      String s = model.get(ii);
      StringTokenizer st = new StringTokenizer(s);
      ap = st.nextToken().equals("AM") ? 'a' : 'p';
      day = Day.valueOf(st.nextToken());
      ssList.add(new ShiftPeriod(day, ap));
    }
    mainPanel.setPrioritizedSalesWeek(ssList);
  }

}
