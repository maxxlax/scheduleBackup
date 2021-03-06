package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import gui.HelpFrame;
import gui.MainPanel;
import io.MyImageCreator;

public class ScheduleMakerApp extends AbstractMultimediaApp
{
  MainPanel mainPanel;
  JMenuItem loadItem, saveItem, helpItem;

  @Override
  public void init()
  {
    JPanel contentPane = (JPanel) rootPaneContainer.getContentPane();
    contentPane.setLayout(null);

    JMenuBar menuBar = new JMenuBar();
    menuBar.setBounds(0, 0, 1000, 20);

    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);
    saveItem = new JMenuItem("Save File");
    saveItem.addActionListener(new MyListener());
    fileMenu.add(saveItem);
    loadItem = new JMenuItem("Load File");
    loadItem.addActionListener(new MyListener());
    fileMenu.add(loadItem);

    JMenu menu = new JMenu("Help");
    menuBar.add(menu);
    helpItem = new JMenuItem("Open Help");
    helpItem.addActionListener(new MyListener());
    BufferedImage image = MyImageCreator.getResizedBufferedImage("qmark.jpg", MyImageCreator.ICON_WIDTH, MyImageCreator.ICON_HEIGHT);
    helpItem.setIcon(new ImageIcon(image));
    menu.add(helpItem);
    contentPane.add(menuBar);

    mainPanel = new MainPanel();
    mainPanel.setBounds(0, 19, 1000, 800);
    contentPane.add(mainPanel);
  }

  @Override
  public boolean saveShit()
  {
    return mainPanel.saveAllInfo();
  }

  private class MyListener implements ActionListener
  {

    @Override
    public void actionPerformed(ActionEvent ae)
    {
      if (ae.getSource().equals(loadItem))
      {
        System.out.println("LOADING...");
        mainPanel.loadAllFiles();
      }
      else if (ae.getSource().equals(saveItem))
      {
        mainPanel.saveAllInfo();
      }
      else if (ae.getSource().equals(helpItem))
      {
        @SuppressWarnings("unused")
        HelpFrame helpFrame = new HelpFrame();
      }
    }

  }
}
