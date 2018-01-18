package gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class HelpFrame extends JFrame
{
  JTextArea jta;
  public HelpFrame()
  {
    super("Help");
    setVisible(true);
    setBounds(200, 30, 750, 600);
    
    JPanel cp = (JPanel) getContentPane(); 
    
    jta = new JTextArea();
    jta.setEditable(false);
    jta.setBounds(0, 0, 745, 590);
    
    JScrollPane jsp = new JScrollPane(jta);
    cp.add(jsp);
    
    readHelpFromFile("help.txt");
  }
  private void readHelpFromFile(String string)
  {
    FileReader fr = null;
    try
    {
      fr = new FileReader(string);
      BufferedReader br = new BufferedReader(fr);
      String line;
      while((line = br.readLine()) != null)
      {
        jta.append(line + "\n");
      }
      br.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
