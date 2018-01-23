package gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTabbedPane;

import scheduling.Day;
import scheduling.Shift;
import scheduling.Week;

@SuppressWarnings("serial")
public class SchedulePanel extends JTabbedPane
{
	SchedulePane amSchedulePane, pmSchedulePane;

	public SchedulePanel(MainPanel mainPanel, ShiftEditor shiftEditor)
	{
		super();
		setBounds(300, 0, 1000 - 315, 677 - 35);
		setBackground(mainPanel.red);
		setVisible(true);
		setTabPlacement(JTabbedPane.TOP);
		amSchedulePane = new SchedulePane(mainPanel, shiftEditor);
		amSchedulePane.setBackground(mainPanel.black);
		pmSchedulePane = new SchedulePane(mainPanel, shiftEditor);
		pmSchedulePane.setBackground(mainPanel.black);
		addTab("AM Schedule", amSchedulePane);
		addTab("PM Schedule", pmSchedulePane);
	}

	public void drawShifts(HashMap<Day, HashMap<String, ArrayList<Shift>>> schedule)
	{
		for (Day day : new Week())
		{
			amSchedulePane.drawShifts(schedule.get(day).get("am"), day);
			pmSchedulePane.drawShifts(schedule.get(day).get("pm"), day);
		}
	}

  public void emptyAll()
  {
    amSchedulePane.emptyAll();
    pmSchedulePane.emptyAll();
  }

  public void emptyFillers()
  {
    amSchedulePane.emptyFillers();
    pmSchedulePane.emptyFillers();
  }
}
