package gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import scheduling.Day;
import scheduling.Shift;
import scheduling.ShiftType;
import scheduling.Week;

@SuppressWarnings("serial")
public class SchedulePane extends JPanel
{
	ArrayList<ArrayList<JTextArea>> fillers;
	HashMap<Day, HashMap<String, ArrayList<ShiftArea>>> shiftAreas;
	MainPanel mainPanel;

	public SchedulePane(MainPanel mainPanel)
	{
		setLayout(null);
		setBackground(mainPanel.red);
		int boxWidth = 76;
		int boxHeight = 32;
		this.mainPanel = mainPanel;

		shiftAreas = new HashMap<Day, HashMap<String, ArrayList<ShiftArea>>>();

		// Build Fillers
		fillers = new ArrayList<ArrayList<JTextArea>>();
		for (int column = 0; column < 2; column++)
		{
			fillers.add(new ArrayList<JTextArea>());
			for (int row = 0; row < 19; row++)
			{
				JTextArea jta = new JTextArea();
				jta.setEditable(false);
				jta.setBounds(column * boxWidth, row * boxHeight, boxWidth, boxHeight);
				fillers.get(column).add(jta);
			}
		}
		fillers.get(0).get(1).setText("InShop");
		fillers.get(0).get(10).setText("Driver");
		for (int ii = 0; ii < fillers.get(0).size(); ii++)
		{
			add(fillers.get(0).get(ii));
			add(fillers.get(1).get(ii));
		}
		for (Day day : new Week())
		{
			JTextArea jta = new JTextArea(day.toString());
			jta.setEditable(false);
			JTextArea jti = new JTextArea();
			jti.setEditable(false);
			JTextArea jtd = new JTextArea();
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

			HashMap<String, ArrayList<ShiftArea>> idMap = new HashMap<String, ArrayList<ShiftArea>>();

			ArrayList<ShiftArea> iShifts = new ArrayList<ShiftArea>();
			for (int ii = 0; ii < 8; ii++)
			{
				ShiftArea sa = new ShiftArea(new Shift(day, ShiftType.InShop));
				sa.setBounds(boxWidth * x, boxHeight * (ii + 2), boxWidth, boxHeight);
				sa.addFocusListener(new ShiftAreaListener());
				iShifts.add(sa);
			}
			idMap.put("InShop", iShifts);

			ArrayList<ShiftArea> dShifts = new ArrayList<ShiftArea>();
			for (int ii = 0; ii < 8; ii++)
			{
				ShiftArea sa = new ShiftArea(new Shift(day, ShiftType.Driver));
				sa.setBounds(boxWidth * x, boxHeight * (ii + 11), boxWidth, boxHeight);
				sa.addFocusListener(new ShiftAreaListener());
				dShifts.add(sa);
			}
			idMap.put("Driver", dShifts);

			shiftAreas.put(day, idMap);
		}
		for (Day day : new Week())
		{
			for (ShiftArea sa : shiftAreas.get(day).get("InShop"))
			{
				add(sa);
			}

			for (ShiftArea sa : shiftAreas.get(day).get("Driver"))
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
			int shiftStart = shift.startTime;
			if (shift.startTime > 12)
				shiftStart -= 12;
			if (shift.type.equals(ShiftType.InShop))
			{
				fillers.get(1).get(2 + iCounter).setText(shiftStart + "-" + (shift.endTime - 12));
				shiftAreas.get(day).get("InShop").get(iCounter).setShift(shift);
			}
			else if (shift.type.equals(ShiftType.Driver))
			{
				fillers.get(1).get(11 + dCounter).setText(shiftStart + "-" + (shift.endTime - 12));
				shiftAreas.get(day).get("Driver").get(dCounter).setShift(shift);
			}
			if (shift.type.equals(ShiftType.InShop))
				iCounter++;
			else if (shift.type.equals(ShiftType.Driver))
				dCounter++;
		}
	}
	
	private class ShiftAreaListener implements FocusListener
	{

		@Override
		public void focusGained(FocusEvent fe)
		{
			mainPanel.viewShift(((ShiftArea) fe.getSource()).getShift());
		}

		@Override
		public void focusLost(FocusEvent fe)
		{
		}

	}
}
