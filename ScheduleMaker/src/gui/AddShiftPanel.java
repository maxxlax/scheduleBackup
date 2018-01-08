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
import scheduling.ShiftType;
import scheduling.Week;

@SuppressWarnings("serial")
public class AddShiftPanel extends JPanel
{
	JRadioButton irb, drb;
	ButtonGroup bg;
	JLabel startEnd;
	JTextField startArea, endArea;
	JButton confirm;

	public AddShiftPanel(MainPanel mainPanel)
	{
		setLayout(null);
		setBackground(mainPanel.red);
		
		irb = new JRadioButton("InShop");
		irb.setBounds(25, 10, 70, 20);
		irb.setSelected(true);

		drb = new JRadioButton("Driver");
		drb.setBounds(95, 10, 70, 20);

		bg = new ButtonGroup();
		bg.add(irb);
		bg.add(drb);

		add(irb);
		add(drb);

		startEnd = new JLabel("start         end");
		startEnd.setBounds(50, 40, 200, 20);
		add(startEnd);

		startArea = new JTextField("11");
		startArea.setBounds(35, 60, 50, 30);
		add(startArea);

		endArea = new JTextField("15");
		endArea.setBounds(90, 60, 50, 30);
		add(endArea);

		confirm = new JButton("Confirm");
		confirm.setBounds(50, 600, 100, 30);
		confirm.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				int startTime = -1, endTime = -1;

				//Ensure Start Time and End Time are set
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
						setError("Check Start Time");
					}
					else if (endTime < 12 || endTime > 22)
					{
						endArea.setBackground(Color.RED);
						setError("Check End Time");
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
							mainPanel.addShift(day, ampm, type, startTime, endTime);
						}
					}
				}
			}
		});
		add(confirm);
	}
	
	private void setError(String string)
	{
		// TODO Auto-generated method stub
		
	}
}
