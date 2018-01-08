package scheduling;

import java.util.ArrayList;

import employee.Employee;
import gui.MainPanel;

public class Scheduler
{
	private ArrayList<Employee> employees;
	private MainPanel mainPanel;

	public Scheduler(ArrayList<Employee> employees, MainPanel mainPanel)
	{
		setEmployees(employees);
		this.mainPanel = mainPanel;
	}

	/**
	 * Schedule week with priority Sunday-Saturday
	 * 
	 * @return
	 */
	public int schedule()
	{
		// Employees
		for (Employee emp : employees)
		{
			// Days
			for (Day day : new Week())
			{
				iterateShifts(emp, day);
			}
		}
		return checkSchedule();
	}

	/**
	 * Schedule a week with prioritized days
	 * 
	 * @param week
	 * @return
	 */
	public int schedulePrioritizedWeek(ArrayList<Day> week)
	{
		// Employees
		for (Employee emp : employees)
		{
			if (week == null)
			{
				mainPanel.notifyUser("Sales Not Entered");
				break;
			}
			else
			{
				for (Day day : week)
				{
					iterateShifts(emp, day);
				}
			}
		}
		return checkSchedule();
	}

	/**
	 * Schedule a Sunday-Saturday week with prioritized shifts
	 * 
	 * @return
	 */
	public int schedulePrioritizedShifts(ArrayList<Shift> shifts)
	{
		// Employees
		for (Employee emp : employees)
		{
			// Days
			for (Day day : new Week())
			{
				iterateShifts(emp, day, shifts);
			}
		}
		return checkSchedule();
	}

	/**
	 * Schedule a week with prioritized days and shifts
	 * 
	 * @param week
	 * @return
	 */
	public int scheduleAll(ArrayList<Day> week, ArrayList<Shift> shifts)
	{
		// Employees
		for (Employee emp : employees)
		{
			// Days
			for (Day day : week)
			{
				iterateShifts(emp, day, shifts);
			}
		}
		return checkSchedule();
	}

	/**
	 * Iterate shifts through day
	 * 
	 * @param emp
	 * @param day
	 */
	private void iterateShifts(Employee emp, Day day)
	{
		// Available that day
		if (emp.getAvailability(day)[0] != -1)
		{
			// Shifts
			if (tryShifts(emp, day, "am"))
			{
				if (emp.canDouble)
				{
					tryShifts(emp, day, "pm");
				}
			}
			else
			{
				tryShifts(emp, day, "pm");
			}

		}
	}

	private boolean tryShifts(Employee emp, Day day, String ampm)
	{
		boolean timePeriodFilled = false;
		for (Shift shift : mainPanel.schedule.get(day).get(ampm))
		{
			// Filled shift
			if (shift.filled)
			{
				// If emp is already on a shift don't schedule again
				if (emp.fullName.equals(shift.getEmployee().fullName))
				{
					// Employee Not Available
					if (!emp.isAvailable(shift) || timePeriodFilled)
					{
						shift.empty();
					}
					// Employee may remain on shift
					else
					{
						timePeriodFilled = true;
					}
				}
			}
			// Open Shift
			else
			{
				// Isn't Already Scheduled During This Shift
				if (!timePeriodFilled)
				{
					// Check Type
					if (emp.can(shift.type))
					{
						// Within availability
						if (emp.isAvailable(shift))
						{
							// Make sure emp doesn't go over maxHours
							if (emp.currentHours + (shift.endTime - shift.startTime) <= emp.getMaxNumHours())
							{
								shift.setEmployee(emp);
								emp.currentHours += shift.endTime - shift.startTime;
								timePeriodFilled = true;
							}

						}
					}
				}
			}
		}
		return timePeriodFilled;
	}

	/**
	 * Iterate prioritized list of shifts
	 * 
	 * @param emp
	 * @param day
	 * @param shifts
	 */
	private void iterateShifts(Employee emp, Day day, ArrayList<Shift> shifts)
	{
		boolean amSet = false;
		boolean pmSet = false;
		// Available that day
		if (emp.getAvailability(day)[0] != -1)
		{
			// Shifts
			for (Shift shift : shifts)
			{
				boolean[] sets = checkShifts(emp, day, shift, amSet, pmSet);
				amSet = sets[0];
				pmSet = sets[1];
			}
		}
	}

	/**
	 * @param emp
	 * @param day
	 * @param shift
	 * @param amSet
	 * @param pmSet
	 * @return
	 */
	private boolean[] checkShifts(Employee emp, Day day, Shift shift, boolean amSet, boolean pmSet)
	{
		// Filled shift
		if (shift.filled)
		{
			// If emp is already on a shift don't schedule again(only needed for am)
			if (emp.fullName.equals(shift.getEmployee().fullName))
			{
				amSet = true;
			}
		}
		// Open Shift
		else
		{
			// Check Type
			if (emp.can(shift.type))
			{
				// If am is set, make sure emp can double
				if ((emp.canDouble && amSet) || !amSet)
				{
					// am and !amSet or pm and !pmSet
					if ((shift.isAM && !amSet) || (!shift.isAM && !pmSet))
					{
						// Within availability
						if (shift.startTime >= emp.getAvailability(day)[0]
								&& shift.endTime <= emp.getAvailability(day)[1])
						{
							// Make sure emp doesn't go over maxHours
							if (emp.currentHours + (shift.endTime - shift.startTime) <= emp.getMaxNumHours())
							{
								// System.out.println(shift.toString() + emp.toString() + "\n\tamSet: " + amSet
								// + "\n\tpmSet" + pmSet);
								shift.setEmployee(emp);
								emp.currentHours += shift.endTime - shift.startTime;
								if (shift.isAM)
								{
									amSet = true;
								}
								else
								{
									pmSet = true;
								}
							}
						}
					}
				}
			}
		}
		boolean[] sets = new boolean[2];
		sets[0] = amSet;
		sets[1] = pmSet;
		return sets;
	}

	/**
	 * @return 0 if all spaces filled, -1 otherwise
	 */
	private int checkSchedule()
	{
		for (Day day : new Week())
		{
			for (Shift shift : mainPanel.schedule.get(day).get("am"))
			{
				if (!shift.filled)
				{
					return -1;
				}
			}
			for (Shift shift : mainPanel.schedule.get(day).get("pm"))
			{
				if (!shift.filled)
				{
					return -1;
				}
			}
		}
		return 0;
	}

	/**
	 * @return the employees
	 */
	public ArrayList<Employee> getEmployees()
	{
		return employees;
	}

	/**
	 * @param employees
	 *            the employees to set
	 */
	public void setEmployees(ArrayList<Employee> employees)
	{
		this.employees = employees;
	}
}
