package scheduling;

import java.util.ArrayList;
import java.util.HashMap;

import sales.DaySales;
import sales.WeeklySales;

public class ShiftCreator
{
	public HashMap<Day, HashMap<String, ArrayList<Shift>>> schedule;
	private ShiftType prev;
	public ShiftCreator(WeeklySales weeklySales)
	{
		schedule = new HashMap<Day, HashMap<String, ArrayList<Shift>>>();
		determineShiftsNeeded(weeklySales);
	}

	private void determineShiftsNeeded(WeeklySales weeklySales)
	{
		for (DaySales ds : weeklySales.getSales())
		{
			schedule.put(ds.getDay(), createDayShifts(ds));
		}
	}

	private HashMap<String, ArrayList<Shift>> createDayShifts(DaySales ds)
	{
		HashMap<String, ArrayList<Shift>> dayShifts = new HashMap<String, ArrayList<Shift>>();
		dayShifts.put("am", getAMShifts(ds));
		dayShifts.put("pm", getPMShifts(ds));
		return dayShifts;
	}

	private ArrayList<Shift> getAMShifts(DaySales ds)
	{
		ArrayList<Shift> shifts = new ArrayList<Shift>();
		shifts.add(new Shift(10, 16, ds.getDay(), ShiftType.Driver));
		if (ds.getAmSales() > 700)
		{
			shifts.add(new Shift(11, 15, ds.getDay(), ShiftType.Driver));
			if (ds.getAmSales() > 1200)
			{
				shifts.add(new Shift(11, 15, ds.getDay(), ShiftType.Driver));
				if (ds.getAmSales() > 1600)
				{
					shifts.add(new Shift(11, 14, ds.getDay(), ShiftType.Driver));
					if (ds.getAmSales() > 2000)
					{
						shifts.add(new Shift(11, 14, ds.getDay(), ShiftType.Driver));
					}
				}
			}
		}
		shifts.add(new Shift(11, 16, ds.getDay(), ShiftType.InShop));
		if (ds.getAmSales() > 1200)
		{
			shifts.add(new Shift(11, 15, ds.getDay(), ShiftType.InShop));
			if (ds.getAmSales() > 1600)
			{
				shifts.add(new Shift(11, 15, ds.getDay(), ShiftType.InShop));
				if (ds.getAmSales() > 2000)
				{
					shifts.add(new Shift(11, 14, ds.getDay(), ShiftType.InShop));
					shifts.add(new Shift(11, 14, ds.getDay(), ShiftType.InShop));
				}
			}
		}
		shifts.add(new Shift(11, 14, ds.getDay(), ShiftType.InShop));
		return shifts;
	}

	private ArrayList<Shift> getPMShifts(DaySales ds)
	{
		ArrayList<Shift> shifts = new ArrayList<Shift>();
		shifts.add(new Shift(16, 22, ds.getDay(), ShiftType.Driver));
		if (ds.getAmSales() > 700)
		{
			if (ds.getAmSales() > 1200)
			{
				shifts.add(new Shift(17, 21, ds.getDay(), ShiftType.Driver));
				if (ds.getAmSales() > 1600)
				{
					shifts.add(new Shift(17, 20, ds.getDay(), ShiftType.Driver));
					if (ds.getAmSales() > 2000)
					{
						shifts.add(new Shift(17, 20, ds.getDay(), ShiftType.Driver));
					}
				}
			}
			shifts.add(new Shift(16, 20, ds.getDay(), ShiftType.Driver));
		}
		shifts.add(new Shift(16, 20, ds.getDay(), ShiftType.InShop));
		shifts.add(new Shift(16, 20, ds.getDay(), ShiftType.InShop));
		if (ds.getAmSales() > 1200)
		{
			if (ds.getAmSales() > 1600)
			{
				if (ds.getAmSales() > 2000)
				{
					shifts.add(new Shift(16, 20, ds.getDay(), ShiftType.InShop));
					shifts.add(new Shift(16, 20, ds.getDay(), ShiftType.InShop));
				}
				shifts.add(new Shift(17, 20, ds.getDay(), ShiftType.InShop));
				shifts.add(new Shift(17, 20, ds.getDay(), ShiftType.InShop));
			}
		}
		return shifts;
	}

	public ArrayList<Shift> getShiftsForDay(Day day)
	{
		ArrayList<Shift> allShifts = new ArrayList<Shift>(schedule.get(day).get("am"));
		for(Shift shift: schedule.get(day).get("pm"))
		{
			allShifts.add(shift);
		}
		return allShifts;
	}

	public String toString()
	{
		String s = "Schedule\n";
		s += addDay(Day.Sunday);
		s += addDay(Day.Monday);
		s += addDay(Day.Tuesday);
		s += addDay(Day.Wednesday);
		s += addDay(Day.Thursday);
		s += addDay(Day.Friday);
		s += addDay(Day.Saturday);
		return s;
	}

	private String addDay(Day current)
	{
		prev = null;
		String s = "";
		s += "\t" + current + "\n";
		for (Shift shift : schedule.get(current).get("am"))
		{
			s += addShifts(shift);
		}
		for (Shift shift : schedule.get(current).get("pm"))
		{
			s += addShifts(shift);
		}
		return s;
	}

	private String addShifts(Shift shift)
	{
		String s = "";
		if (!prev.equals(shift.type))
		{
			s += "\t\t" + shift.type + "\n";
			s += "\t\t   " + time(shift) + ": " + shift.getEmployee().fullName + "\n";
		}
		else
		{
			s += "\t\t   " + time(shift) + ": " + shift.getEmployee().fullName + "\n";
		}
		prev = shift.type;
		return s;
	}

	private String time(Shift shift)
	{
		String s = "";
		s += shift.startTime % 12;
		if (shift.startTime > 12)
		{
			s += "PM";
		}
		else
		{
			s += "AM";
		}
		if (shift.endTime == 22)
		{
			s += "-Close";
		}
		else
		{
			s += "-" + shift.endTime % 12 + "PM";
		}
		return s;
	}
}
