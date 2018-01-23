package gui;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modList.MyShiftModList;
import scheduling.ShiftPeriod;

@SuppressWarnings("serial")
public class ViewShiftPanel extends JPanel
{
	private MyShiftModList shiftList;
	private JScrollPane shiftPane;

	public ViewShiftPanel(MainPanel mainPanel)
	{
		setLayout(null);
		setBackground(mainPanel.red);
		
		shiftList = new MyShiftModList(mainPanel);
		shiftList.addShift("AM Sunday");
		shiftList.addShift("PM Sunday");
		shiftList.addShift("AM Monday");
		shiftList.addShift("PM Monday");
		shiftList.addShift("AM Tuesday");
		shiftList.addShift("PM Tuesday");
		shiftList.addShift("AM Wednesday");
		shiftList.addShift("PM Wednesday");
		shiftList.addShift("AM Thursday");
		shiftList.addShift("PM Thursday");
		shiftList.addShift("AM Friday");
		shiftList.addShift("PM Friday");
		shiftList.addShift("AM Saturday");
		shiftList.addShift("PM Saturday");
		shiftPane = new JScrollPane(shiftList);
		shiftPane.setBounds(10, 10, 170, 615);
		add(shiftPane);
	}

  public void setElements(ArrayList<ShiftPeriod> prioritizedSalesWeek)
  {
    ArrayList<String> shifts = new ArrayList<String>();
    for(ShiftPeriod ss: prioritizedSalesWeek)
    {
      String s = "";
      if(ss.getShift() == 'a')
      {
        s += "AM ";
      }
      else
      {
        s += "PM ";
      }
      shifts.add(s + ss.getDay());
    }
    shiftList.setElements(shifts);
  }
}
