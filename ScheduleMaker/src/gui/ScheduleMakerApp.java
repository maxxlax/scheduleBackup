package gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import app.AbstractMultimediaApp;

public class ScheduleMakerApp extends AbstractMultimediaApp
{
  MainPanel mainPanel;
  @Override
  public void init()
  {
    JPanel contentPane = (JPanel) rootPaneContainer.getContentPane();
    contentPane.setLayout(null);
    
    JMenuBar menuBar = new JMenuBar();
    menuBar.setBounds(0, 0, 1000, 20);
    JMenu menu = new JMenu("Help");
    menuBar.add(menu);
    
    JMenuItem menuItem = new JMenuItem("Open Help");
    menu.add(menuItem);
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
}
