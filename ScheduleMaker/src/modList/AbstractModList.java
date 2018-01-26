package modList;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.MainPanel;

@SuppressWarnings("serial")
abstract public class AbstractModList extends JList<String> implements KeyListener
{
  protected DefaultListModel<String> model;
  private int currentSelected = -1;
  protected MainPanel mainPanel;

  public AbstractModList(MainPanel mainPanel)
  {
    super(new DefaultListModel<String>());
    this.mainPanel = mainPanel;
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

  public void setElements(ArrayList<String> elements)
  {
    model.clear();
    for(String s: elements)
    {
      model.addElement(s);
    }
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
          reorganize();
        }
      }
      if (ke.getKeyCode() == 40)
      {
        if (currentSelected != model.size() - 1)
        {
          int c = currentSelected;
          model.remove(c);
          model.insertElementAt(currentString, c + 1);
          setSelectedIndex(c);
          reorganize();
        }
      }
    }
  }

  protected abstract void reorganize();
  
  @Override
  public void keyReleased(KeyEvent arg0)
  {
  }

  @Override
  public void keyTyped(KeyEvent ke)
  {
  }
  
}
