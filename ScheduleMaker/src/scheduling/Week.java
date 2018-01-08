package scheduling;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Week extends ArrayList<Day>
{
	public Week()
	{
		add(Day.Sunday);
		add(Day.Monday);
		add(Day.Tuesday);
		add(Day.Wednesday);
		add(Day.Thursday);
		add(Day.Friday);
		add(Day.Saturday);
	}
}
