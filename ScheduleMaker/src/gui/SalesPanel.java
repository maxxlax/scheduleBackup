package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sales.DaySales;
import sales.WeeklySales;
import scheduling.Day;
import scheduling.ShiftPeriod;

@SuppressWarnings("serial")
public class SalesPanel extends JPanel
{
	private WeeklySales weeklySales;
	private ArrayList<ShiftPeriod> prioritizedSalesWeek;
	ArrayList<JTextField> salesFields;
	
	public SalesPanel(MainPanel mainPanel)
	{
		setLayout(null);
		setBackground(mainPanel.red);
		JPanel textPanel = new JPanel();
		textPanel.setBackground(mainPanel.red);
		textPanel.setLayout(new GridLayout(0, 2));
		textPanel.setBounds(0, 0, 190, 550);

		weeklySales = new WeeklySales();
		setPrioritizedSalesWeek(weeklySales.createShiftPriorityBasedOnSales());

		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		labels.add(new JLabel("Sunday AM $", JLabel.CENTER));
		labels.add(new JLabel("Sunday PM $", JLabel.CENTER));
		labels.add(new JLabel("Monday AM $", JLabel.CENTER));
		labels.add(new JLabel("Monday PM $", JLabel.CENTER));
		labels.add(new JLabel("Tueday AM $", JLabel.CENTER));
		labels.add(new JLabel("Tueday PM $", JLabel.CENTER));
		labels.add(new JLabel("Wednesday AM $", JLabel.CENTER));
		labels.add(new JLabel("Wednesday PM $", JLabel.CENTER));
		labels.add(new JLabel("Thursday AM $", JLabel.CENTER));
		labels.add(new JLabel("Thursday PM $", JLabel.CENTER));
		labels.add(new JLabel("Friday AM $", JLabel.CENTER));
		labels.add(new JLabel("Friday PM $", JLabel.CENTER));
		labels.add(new JLabel("Saturday AM $", JLabel.CENTER));
		labels.add(new JLabel("Saturday PM $", JLabel.CENTER));

		salesFields = new ArrayList<JTextField>();
		for(int ii = 0; ii < 14; ii++)
		{
		  salesFields.add(new JTextField(""));
		}

		for (int ii = 0; ii < labels.size(); ii++)
		{
			textPanel.add(labels.get(ii));
			textPanel.add(salesFields.get(ii));
		}
		add(textPanel);

		JPanel confirmPanel = new JPanel();
		confirmPanel.setLayout(null);
		confirmPanel.setBackground(mainPanel.red);
		confirmPanel.setBounds(0, 550, 215, 120);

		JButton confirm = new JButton("Confirm");
		confirm.setBounds(50, 50, 100, 30);
		confirm.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				for (JTextField jtf : salesFields)
				{
					if (jtf.getText() != null && !jtf.getText().equals(""))
					{
						try
						{
							int num = Integer.parseInt(jtf.getText());
							if (num > 0)
							{
								jtf.setBackground(Color.GREEN);
							}
							else
							{
								jtf.setBackground(Color.RED);
							}
						}
						catch (NumberFormatException e)
						{
							jtf.setBackground(Color.RED);
						}
					}
					else
					{
						jtf.setBackground(Color.RED);
					}
				}
				boolean done = true;
				for (JTextField jtf : salesFields)
				{
					if (!jtf.getBackground().equals(Color.GREEN))
					{
						done = false;
					}
				}
				if (done)
				{
					weeklySales.setSales(new DaySales(Integer.parseInt(salesFields.get(0).getText()), Integer.parseInt(salesFields.get(1).getText()), Day.Sunday));
					weeklySales.setSales(new DaySales(Integer.parseInt(salesFields.get(2).getText()), Integer.parseInt(salesFields.get(3).getText()), Day.Monday));
					weeklySales.setSales(new DaySales(Integer.parseInt(salesFields.get(4).getText()), Integer.parseInt(salesFields.get(5).getText()), Day.Tuesday));
					weeklySales.setSales(new DaySales(Integer.parseInt(salesFields.get(6).getText()), Integer.parseInt(salesFields.get(7).getText()), Day.Wednesday));
					weeklySales.setSales(new DaySales(Integer.parseInt(salesFields.get(8).getText()), Integer.parseInt(salesFields.get(9).getText()), Day.Thursday));
					weeklySales.setSales(new DaySales(Integer.parseInt(salesFields.get(10).getText()), Integer.parseInt(salesFields.get(11).getText()), Day.Friday));
					weeklySales.setSales(new DaySales(Integer.parseInt(salesFields.get(12).getText()), Integer.parseInt(salesFields.get(13).getText()), Day.Saturday));
					setPrioritizedSalesWeek(weeklySales.createShiftPriorityBasedOnSales());
					mainPanel.setPrioritizedSalesWeek(prioritizedSalesWeek);
				}
			}
		});
		confirmPanel.add(confirm);

		add(confirmPanel);
	}
	/**
	 * @return the prioritizedSalesWeek
	 */
	public ArrayList<ShiftPeriod> getPrioritizedSalesWeek()
	{
		return prioritizedSalesWeek;
	}
	/**
	 * @param prioritizedSalesWeek the prioritizedSalesWeek to set
	 */
	public void setPrioritizedSalesWeek(ArrayList<ShiftPeriod> prioritizedSalesWeek)
	{
		this.prioritizedSalesWeek = prioritizedSalesWeek;
	}
  public WeeklySales getWs()
  {
    return weeklySales;
  }
  public void setWs(WeeklySales ws)
  {
    int ai = 0;
    this.weeklySales = ws;
    for(DaySales ds: ws.getSales())
    {
      salesFields.get(ai).setText("" + ds.getAmSales());
      ai++;
      salesFields.get(ai).setText("" + ds.getPmSales());
      ai++;
    }
  }
}
