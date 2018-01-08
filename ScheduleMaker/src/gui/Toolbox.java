package gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import employee.Employee;
import sales.WeeklySales;
import scheduling.Day;
import scheduling.Scheduler;
import scheduling.Shift;
import scheduling.ShiftCreator;
import scheduling.Week;

@SuppressWarnings("serial")
public class Toolbox extends JPanel
{
	public HashMap<Day, HashMap<String, ArrayList<Shift>>> schedule;
	WeeklySales ws;
	ArrayList<Day> prioritizedSalesWeek;
	ShiftCreator sc;
	Scheduler scheduler;
	ArrayList<Employee> employees;
	boolean salesReady, employeesReady;
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

	public void addEmployee(Employee employee, JComboBox<Employee> employeeCB)
	{
		employees.add(employee);
		employeeCB.addItem(employee);
		if (!newEmployeeRemoved)
		{
			employeeCB.removeItemAt(0);
			newEmployeeRemoved = true;
		}
		employeesReady = true;
		viewEmployeesPanel.addEmployee(employee);
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
		// TODO iterate shifts and ensure that emp can take on all shifts
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
		return salesReady && employeesReady;
	}
	/*
	 * public void setSC() { sc = new ShiftCreator(ws); scheduler = new
	 * Scheduler(employees, sc); scheduler.schedule(); System.out.println("AAA" +
	 * sc.toString()); }
	 */
}
