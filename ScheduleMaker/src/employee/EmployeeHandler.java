package employee;
import scheduling.Day;
import scheduling.Shift;
import scheduling.ShiftCreator;
import scheduling.ShiftType;

public class EmployeeHandler
{
	private ShiftCreator sc;

	public EmployeeHandler(ShiftCreator sc)
	{
		this.sc = sc;
	}

	public void setShift(Day day, int startTime, int endTime, ShiftType type, Employee employee)
	{
		if(startTime < 12)
		{
		for (Shift shift : sc.schedule.get(day).get("am"))
		{
			if (shift.startTime == startTime && shift.endTime == endTime && shift.type == type)
			{
				shift.setEmployee(employee);
			}
		}
		}
		else
		{
			for (Shift shift : sc.schedule.get(day).get("pm"))
			{
				if (shift.startTime == startTime && shift.endTime == endTime && shift.type == type)
				{
					shift.setEmployee(employee);
				}
			}
		}
	}
}
