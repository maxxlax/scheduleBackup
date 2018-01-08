package gui;

import javax.swing.JTextArea;

import scheduling.Shift;

@SuppressWarnings("serial")
public class ShiftArea extends JTextArea
{
	private Shift shift;
	
	public ShiftArea(Shift shift)
	{
		this.shift = shift;
		setEditable(false);
	}

	public void setShift(Shift shift)
	{
		this.shift = shift;
		setText(shift.employee.firstName);
	}
	
	public Shift getShift()
	{
		return shift;
	}
}
