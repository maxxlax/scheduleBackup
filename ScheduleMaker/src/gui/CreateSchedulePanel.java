package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import scheduling.Shift;

@SuppressWarnings("serial")
public class CreateSchedulePanel extends JPanel
{
	private JTextArea info;
	private JButton editEmployee, editShift, fillOpenShifts, reschedule;
	private Shift currentShift;
	private MainPanel mainPanel;

	public CreateSchedulePanel(MainPanel mainPanel, SchedulePanel schedulePanel)
	{
		setLayout(null);
		setBackground(mainPanel.red);

		this.mainPanel = mainPanel;
		info = new JTextArea();

		JScrollPane jsp = new JScrollPane(info);
		jsp.setBounds(10, 10, 170, 450);
		add(jsp);

		editEmployee = new JButton("Edit Employee");
		editEmployee.setBounds(10, 510, 170, 30);
		editEmployee.setVisible(false);
		editEmployee.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				editEmployee();
			}
		});
		add(editEmployee);

		editShift = new JButton("Edit Shift");
		editShift.setBounds(10, 470, 170, 30);
		editShift.setVisible(false);
		editShift.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				editShift();
			}
		});
		add(editShift);

		fillOpenShifts = new JButton("Fill Open Shifts");
		fillOpenShifts.setBounds(20, 560, 150, 30);
		fillOpenShifts.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				mainPanel.fillOpenShifts();
				info.setText("");
			}
		});
		add(fillOpenShifts);
		
		reschedule = new JButton("Reschedule");
		reschedule.setBounds(20, 600, 150, 30);
		reschedule.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				mainPanel.emptyAllShifts();
				mainPanel.fillOpenShifts();
				info.setText("");
			}
		});
		add(reschedule);
	}

	public void viewShift(Shift shift)
	{
		currentShift = shift;
		info.setText(shift.toString(true));
		editEmployee.setVisible(true);
		editShift.setVisible(true);
	}

	public void editShift()
	{
		//TODO
	}
	
	public void editEmployee()
	{
		mainPanel.viewEmployee(currentShift.employee);
	}
	
	public void changeEmployee()
	{
		//TODO
	}
}
