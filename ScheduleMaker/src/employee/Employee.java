package employee;

import java.util.ArrayList;
import java.util.StringTokenizer;

import scheduling.Day;
import scheduling.Shift;
import scheduling.ShiftType;

public class Employee implements Comparable<Employee>
{
	public String firstName, lastName, fullName;
	public boolean isInshop, isDriver, canDouble;
	public int currentHours, maxNumHours;
	private ArrayList<int[]> availability;

	/**
	 * 
	 */
	public Employee()
	{
		firstName = "";
		lastName = "";
		fullName = "";
		setInshop(false);
		setDriver(false);
		canDouble = false;
		currentHours = 0;
		setMaxNumHours(-1);
		setAvailability();
	}
	
	public Employee(String fullName)
	{
		firstName = "";
		lastName = "";
		this.fullName = fullName;
		setInshop(false);
		setDriver(false);
		canDouble = false;
		currentHours = 0;
		setMaxNumHours(-1);
		setAvailability();
	}

	/**
	 * @param firstName
	 */
	public Employee(String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		fullName = firstName + " " + lastName;
		setInshop(false);
		setDriver(false);
		canDouble = false;
		currentHours = 0;
		setMaxNumHours(-1);
		setAvailability();
	}

	/**
	 * Main Constructor
	 * 
	 * @param firstName
	 * @param isInshop
	 * @param isDriver
	 * @param isManager
	 * @param maxNumHours
	 * @param availability
	 */
	public Employee(String firstName, String lastName, boolean isInshop, boolean isDriver,
			boolean canDouble, int maxNumHours, ArrayList<int[]> availability)
	{
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = firstName + " " + lastName;
		setInshop(isInshop);
		setDriver(isDriver);
		this.canDouble = canDouble;
		currentHours = 0;
		setMaxNumHours(maxNumHours);
		setAvailability(availability);
	}

	/**
	 * Constructor if employee already has hours elsewhere
	 * 
	 * @param name
	 * @param isInshop
	 * @param isDriver
	 * @param isManager
	 * @param currentHours
	 * @param maxNumHours
	 * @param availability
	 */
	public Employee(String name, boolean isInshop, boolean isDriver, boolean canDouble,
			int currentHours, int maxNumHours, ArrayList<int[]> availability)
	{
		super();
		this.fullName = name;
		StringTokenizer st = new StringTokenizer(fullName);
		firstName = st.nextToken();
		lastName = st.nextToken();
		setInshop(isInshop);
		setDriver(isDriver);
		this.canDouble = canDouble;
		this.currentHours = currentHours;
		setMaxNumHours(maxNumHours);
		setAvailability(availability);
	}

	public String toSaveString()
  {
    String str = fullName + "|" + isInshop + "|" + isDriver + "|" + canDouble + "|" + currentHours + "|" + maxNumHours;
    for(int[] av: availability)
    {
      str += "|" + av[0] + "|" + av[1];
    }
    return str;
  }

  /**
	 * @return the isDriver
	 */
	public boolean isDriver()
	{
		return isDriver;
	}

	/**
	 * @param isDriver
	 *            the isDriver to set
	 */
	public void setDriver(boolean isDriver)
	{
		this.isDriver = isDriver;
	}

	/**
	 * @return the isInshop
	 */
	public boolean isInshop()
	{
		return isInshop;
	}

	/**
	 * @param isInshop
	 *            the isInshop to set
	 */
	public void setInshop(boolean isInshop)
	{
		this.isInshop = isInshop;
	}

	/**
	 * @return the availability
	 */
	public ArrayList<int[]> getAvailability()
	{
		return availability;
	}

	private void setAvailability()
	{
		ArrayList<int[]> av = new ArrayList<int[]>();
		for(int ii = 0; ii < 7; ii++)
		{
			av.add(new int[] {-1, -1});
		}
		availability = new ArrayList<int[]>(av);
	}

	/**
	 * @param availability
	 *            the availability to set
	 */
	public void setAvailability(ArrayList<int[]> availability)
	{
		this.availability = availability;
	}

	/**
	 * @param day
	 * @param start
	 * @param end
	 * @return
	 */
	public int setAvailability(Day day, int start, int end)
	{
		int ret = 0;
		switch (day)
		{
		case Sunday:
			availability.set(0, new int[]
			{ start, end });
			break;
		case Monday:
			availability.set(1, new int[]
			{ start, end });
			break;
		case Tuesday:
			availability.set(2, new int[]
			{ start, end });
			break;
		case Wednesday:
			availability.set(3, new int[]
			{ start, end });
			break;
		case Thursday:
			availability.set(4, new int[]
			{ start, end });
			break;
		case Friday:
			availability.set(5, new int[]
			{ start, end });
			break;
		case Saturday:
			availability.set(6, new int[]
			{ start, end });
			break;
		default:
			ret = -1;
			break;
		}
		return ret;
	}

	/**
	 * @param day
	 * @return
	 */
	public int[] getAvailability(Day day)
	{
		switch (day)
		{
		case Sunday:
			return availability.get(0);
		case Monday:
			return availability.get(1);
		case Tuesday:
			return availability.get(2);
		case Wednesday:
			return availability.get(3);
		case Thursday:
			return availability.get(4);
		case Friday:
			return availability.get(5);
		case Saturday:
			return availability.get(6);
		default:
			return new int[]
			{ -1, -1 };
		}
	}

	/**
	 * @return the maxNumHours
	 */
	public int getMaxNumHours()
	{
		return maxNumHours;
	}

	/**
	 * @param maxNumHours
	 *            the maxNumHours to set
	 */
	public void setMaxNumHours(int maxNumHours)
	{
		this.maxNumHours = maxNumHours;
	}

	public boolean can(ShiftType type)
	{
		switch (type)
		{
		case Driver:
			if (isDriver)
			{
				return true;
			}
			break;
		case InShop:
			if (isInshop)
			{
				return true;
			}
			break;
		}
		return false;
	}

	public String toString()
	{
		return fullName;
	}

	public String toString(boolean extended)
	{
		return "------Employee------\n First Name: " + firstName + "\n Last Name: " + lastName + "\n Inshop: " + isInshop
				+ "\n Driver: " + isDriver + "\n Can Double: " + canDouble
				+ "\n Current Hours: " + currentHours + "\n Max Weekly Hrs: " + maxNumHours;
	}

	@Override
	public int compareTo(Employee emp)
	{
		if(fullName.equals(emp.fullName))
		{
			return 0;
		}
		return -1;
	}
	
	@Override 
	public boolean equals(Object o)
	{
		if(o instanceof Employee)
		{
			return compareTo((Employee) o) == 0;
		}
		return false;
		
	}

	public boolean isAvailable(Shift shift)
	{
		// Within availability
		if (shift.startTime >= getAvailability(shift.day)[0] && shift.endTime <= getAvailability(shift.day)[1])
		{
			return true;
		}
		return false;
	}
}
