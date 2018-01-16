package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import employee.Employee;

@SuppressWarnings("serial")
public class ViewEmployeesPanel extends JPanel
{

	public MyDragDropList myEmployeeList;
	public JScrollPane empPane;
	private JLabel label, label2;
  public MainPanel mainPanel;

	public ViewEmployeesPanel(MainPanel mainPanel)
	{
		setLayout(null);
		setBackground(mainPanel.red);
		this.mainPanel = mainPanel;
		
		label = new JLabel("Select an Employee", JLabel.CENTER);
		label.setBounds(10, 10, 170, 20);
		add(label);
		
		label2 = new JLabel("Up and Down to adjust Employee Priority");
		label2.setBounds(8, 30, 200, 20);
		add(label2);
		
		myEmployeeList = new MyDragDropList(mainPanel);
		empPane = new JScrollPane(myEmployeeList);
		empPane.setBounds(10, 50, 170, 570);
		add(empPane);
	}

	public void addEmployee(Employee emp)
	{
		myEmployeeList.addEmployee(emp);
	}

	public void removeEmployee(Employee emp)
	{
		myEmployeeList.removeEmployee(emp);
	}
}