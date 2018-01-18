package gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import employee.Employee;
import sales.ShiftSale;
import sales.WeeklySales;
import scheduling.Day;
import scheduling.Scheduler;
import scheduling.Shift;
import scheduling.Week;

@SuppressWarnings("serial")
public class Toolbox extends JPanel
{
	public HashMap<Day, HashMap<String, ArrayList<Shift>>> schedule;
	WeeklySales ws;
	ArrayList<ShiftSale> prioritizedSalesWeek;
	Scheduler scheduler;
	public ArrayList<Employee> employees;
	public boolean salesReady, employeesReady, shiftsReady, scheduleSet;
	CreateSchedulePanel createSchedulePanel;
	ViewEmployeesPanel viewEmployeesPanel;
	boolean newEmployeeRemoved;

	public Toolbox()
	{
		buildSchedule();
		newEmployeeRemoved = false;
	}

	private void buildSchedule()
	{
		schedule = new HashMap<Day, HashMap<String, ArrayList<Shift>>>();
		for (Day day : new Week())
		{
			schedule.put(day, new HashMap<String, ArrayList<Shift>>());
			schedule.get(day).put("am", new ArrayList<Shift>());
			schedule.get(day).put("pm", new ArrayList<Shift>());
		}
	}

	public void editEmployee(String firstName, String lastName, boolean isInshop, boolean isDriver, boolean canDouble,
			int maxNumHours, ArrayList<int[]> availability)
	{
		int index = employees
				.indexOf(new Employee(firstName, lastName, isInshop, isDriver, canDouble, maxNumHours, availability));
		Employee emp = employees.get(index);
		emp.firstName = firstName;
		emp.lastName = lastName;
		emp.isInshop = isInshop;
		emp.isDriver = isDriver;
		emp.canDouble = canDouble;
		emp.maxNumHours = maxNumHours;
		emp.setAvailability(availability);
		// Iterate shifts and ensure that emp can take on all shifts
		for (Day day : new Week())
		{
			for (Shift shift : schedule.get(day).get("am"))
			{
				if (shift.employee.fullName.equals(firstName + " " + lastName))
				{
					if (!emp.isAvailable(shift))
					{
						shift.empty();
					}
				}
			}
			for (Shift shift : schedule.get(day).get("pm"))
			{
				if (shift.employee.fullName.equals(firstName + " " + lastName))
				{

				}
			}
		}
	}

	public boolean isReady()
	{
		return salesReady && employeesReady && shiftsReady;
	}
}
