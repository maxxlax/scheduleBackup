package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import scheduling.Shift;

@SuppressWarnings("serial")
public class ShiftEditor extends JPanel
{
  JLabel noneSelected, title, startEnd, successfulEdit;
  JTextField startField, endField;
  public ShiftEditor(Color bg, Color fg, Color text)
  {
    setLayout(null);
    setBackground(bg);
    
    title = new JLabel("Shift Editor", JLabel.CENTER);
    title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 20));
    title.setForeground(text);
    title.setBounds(0, 5, 189, 30);
    add(title);
    
    successfulEdit = new JLabel("Successfully Edited Shift", JLabel.CENTER);
    successfulEdit.setForeground(text);
    successfulEdit.setBounds(5, 80, 184, 20);
    successfulEdit.setVisible(false);
    add(successfulEdit);
    
    noneSelected = new JLabel("Click on a shift to edit here", JLabel.CENTER);
    noneSelected.setForeground(text);
    noneSelected.setBounds(5, 50, 184, 30);
    add(noneSelected);
    
    startEnd = new JLabel("start         end");
    startEnd.setForeground(text);
    startEnd.setBounds(60, 40, 200, 20);
    startEnd.setVisible(false);
    add(startEnd);

    startField = new JTextField("11");
    startField.setBounds(45, 60, 50, 30);
    startField.setVisible(false);
    add(startField);

    endField = new JTextField("15");
    endField.setBounds(100, 60, 50, 30);
    endField.setVisible(false);
    add(endField);
  }
  public void editShift(Shift shift)
  {
    successfulEdit.setVisible(false);
    noneSelected.setVisible(false);
    startEnd.setVisible(true);
    startField.setVisible(true);
    endField.setVisible(true);
  }
}
