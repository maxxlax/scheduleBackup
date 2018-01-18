package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import scheduling.Day;
import scheduling.Shift;
import scheduling.ShiftType;
import scheduling.Week;

@SuppressWarnings("serial")
public class AddShiftPanel extends JPanel
{
	JRadioButton irb, drb;
	ButtonGroup bg;
	JLabel startEnd;
	JTextField startArea, endArea;
	JButton addShift;
	ShiftEditor editorPanel;

	public AddShiftPanel(MainPanel mainPanel)
	{
		setLayout(null);
		setBackground(mainPanel.red);
		
		irb = new JRadioButton("InShop");
		irb.setBounds(30, 10, 70, 20);
		irb.setSelected(true);

		drb = new JRadioButton("Driver");
		drb.setBounds(100, 10, 70, 20);

		bg = new ButtonGroup();
		bg.add(irb);
		bg.add(drb);

		add(irb);
		add(drb);

		startEnd = new JLabel("start         end");
		startEnd.setBounds(60, 40, 200, 20);
		add(startEnd);

		startArea = new JTextField("11");
		startArea.setBounds(45, 60, 50, 30);
		add(startArea);

		endArea = new JTextField("15");
		endArea.setBounds(100, 60, 50, 30);
		add(endArea);

		addShift = new JButton("Add Shift");
		addShift.setBounds(50, 120, 100, 30);
		addShift.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				int startTime = -1, endTime = -1;

				try
				{
					startTime = Integer.parseInt(startArea.getText());
				}
				catch (NumberFormatException nfe)
				{
					startArea.setBackground(Color.RED);
				}

				try
				{
					endTime = Integer.parseInt(endArea.getText());
				}
				catch (NumberFormatException nfe)
				{
					endArea.setBackground(Color.RED);
				}

				//Ensure Start Time and End Time are set
				if (startTime != -1 && endTime != -1)
				{
					//Check values are within acceptable ranges
					if (startTime < 9 || startTime > 20)
					{
						startArea.setBackground(Color.RED);
					}
					else if (endTime < 12 || endTime > 22)
					{
						endArea.setBackground(Color.RED);
					}
					//Continue
					else
					{
						ShiftType type;
						if (irb.isSelected())
							type = ShiftType.InShop;
						else
							type = ShiftType.Driver;

						String ampm = "";
						if (startTime <= 12)
							ampm = "am";
						else
							ampm = "pm";
						for (Day day : new Week())
						{
							if(!mainPanel.addShift(day, ampm, type, startTime, endTime)) break;
						}
					}
				}
			}
		});
		add(addShift);
		
		editorPanel = new ShiftEditor(mainPanel, mainPanel.black, mainPanel.red, mainPanel.white);
		editorPanel.setBounds(1, 390, 189, 250);
		add(editorPanel);
	}
	
  public void viewShiftEditor(Shift shift)
  {
    editorPanel.editShift(shift);
  }
}
