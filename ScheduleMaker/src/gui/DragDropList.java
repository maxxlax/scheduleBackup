package gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import employee.Employee;

@SuppressWarnings("serial")
public class DragDropList extends JList<String>
{
	public DefaultListModel<String> model;

	public DragDropList(MainPanel mainPanel)
	{
		super(new DefaultListModel<String>());
		model = (DefaultListModel<String>) getModel();
		setDragEnabled(true);
		setDropMode(DropMode.INSERT);

		setTransferHandler(new MyListDropHandler(mainPanel));

		new MyDragListener(this);
	}

	public void addShift(String str)
	{
		model.addElement(str);
	}
	public void addEmployee(Employee emp)
	{
		model.addElement(emp.fullName);
	}
}

class MyDragListener implements DragSourceListener, DragGestureListener
{
	DragDropList list;

	DragSource ds = new DragSource();

	public MyDragListener(DragDropList list)
	{
		this.list = list;
		@SuppressWarnings("unused")
		DragGestureRecognizer dgr = ds.createDefaultDragGestureRecognizer(list, DnDConstants.ACTION_MOVE, this);
	}

	public void dragGestureRecognized(DragGestureEvent dge)
	{
		@SuppressWarnings("unused")
		StringSelection transferable = new StringSelection(Integer.toString(list.getSelectedIndex()));
		// ds.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
	}

	public void dragEnter(DragSourceDragEvent dsde)
	{
	}

	public void dragExit(DragSourceEvent dse)
	{
	}

	public void dragOver(DragSourceDragEvent dsde)
	{
	}

	public void dragDropEnd(DragSourceDropEvent dsde)
	{
		if (dsde.getDropSuccess())
		{
			System.out.println("Succeeded");
		}
		else
		{
			System.out.println("Failed");
		}
	}

	public void dropActionChanged(DragSourceDragEvent dsde)
	{
	}
}

@SuppressWarnings("serial")
class MyListDropHandler extends TransferHandler
{
	MainPanel mainPanel;
	public MyListDropHandler(MainPanel mainPanel)
	{
		this.mainPanel = mainPanel;
	}
	
	@Override
	public int getSourceActions(JComponent c)
	{
		return TransferHandler.COPY_OR_MOVE;
	}

	@Override
	protected Transferable createTransferable(JComponent source)
	{
		@SuppressWarnings("unchecked")
		JList<String> sourceList = (JList<String>) source;
		String data = sourceList.getSelectedValue();
		Transferable t = new StringSelection(data);
		return t;
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action)
	{
		@SuppressWarnings("unchecked")
		JList<String> sourceList = (JList<String>) source;
		String movedItem = sourceList.getSelectedValue();
		if (action == TransferHandler.MOVE)
		{
			DefaultListModel<String> listModel = (DefaultListModel<String>) sourceList.getModel();
			listModel.removeElement(movedItem);
		}
	}

	@Override
	public boolean canImport(TransferHandler.TransferSupport support)
	{
		if (!support.isDrop())
		{
			return false;
		}
		return support.isDataFlavorSupported(DataFlavor.stringFlavor);
	}

	@Override
	public boolean importData(TransferHandler.TransferSupport support)
	{
		if (!this.canImport(support))
		{
			return false;
		}
		Transferable t = support.getTransferable();
		String data = null;
		try
		{
			data = (String) t.getTransferData(DataFlavor.stringFlavor);
			if (data == null)
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		JList.DropLocation dropLocation = (JList.DropLocation) support.getDropLocation();
		int dropIndex = dropLocation.getIndex();
		@SuppressWarnings("unchecked")
		JList<String> targetList = (JList<String>) support.getComponent();
		DefaultListModel<String> listModel = (DefaultListModel<String>) targetList.getModel();
		if (dropLocation.isInsert())
		{
			listModel.add(dropIndex, data);
		}
		else
		{
			listModel.set(dropIndex, data);
		}
		if(listModel.get(0).subSequence(0, 2).equals("AM") || listModel.get(0).subSequence(0, 2).equals("PM"))
		{
			System.out.println("SHIFTS");
		}
		else
		{
			mainPanel.reorganizeEmployees(listModel);
		}
		return true;
	}
}
