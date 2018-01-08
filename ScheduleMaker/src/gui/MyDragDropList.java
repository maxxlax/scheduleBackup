package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import employee.Employee;

@SuppressWarnings("serial")
public class MyDragDropList extends JList<String> implements KeyListener
{
	private DefaultListModel<String> model;
	private int currentSelected = -1;

	public MyDragDropList()
	{
		super(new DefaultListModel<String>());
		addKeyListener(this);
		model = (DefaultListModel<String>) getModel();
		
		addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent lse)
			{
				currentSelected = getSelectedIndex();
			}

		});
	}

	public void addShift(String str)
	{
		model.addElement(str);
	}
	public void addEmployee(Employee emp)
	{
		model.addElement(emp.fullName);
	}

	public void removeEmployee(Employee emp)
	{
		model.removeElement(emp.fullName);
	}

	@Override
	public void keyPressed(KeyEvent ke)
	{
		if (currentSelected != -1)
		{
			String currentString = model.get(currentSelected);
			if (ke.getKeyCode() == 38)
			{
				if (currentSelected != 0)
				{
					int c = currentSelected;
					model.remove(c);
					model.insertElementAt(currentString, c - 1);
				}
			}
			if (ke.getKeyCode() == 40)
			{
				if(currentSelected != model.size() - 1)
				{
					int c = currentSelected;
					model.remove(c);
					model.insertElementAt(currentString, c + 1);
					setSelectedIndex(c);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
	}

	@Override
	public void keyTyped(KeyEvent ke)
	{
	}

	public void removeEmployee(String string)
	{
		model.removeElement(string);
	}
}
