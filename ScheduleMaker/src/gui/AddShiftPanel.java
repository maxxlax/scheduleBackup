package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import scheduling.Day;
import scheduling.Shift;
import scheduling.ShiftType;
import scheduling.Week;

@SuppressWarnings("serial")
public class AddShiftPanel extends JPanel
{
  private JRadioButton irb, drb;
  private ButtonGroup bg;
  private JLabel startEnd, formatLabel1, formatLabel2;
  private JTextField startArea, endArea;
  private JButton addShift;

  public AddShiftPanel(MainPanel mainPanel, ShiftEditor shiftEditor)
  {
    setLayout(null);
    setBackground(mainPanel.black);

    // Info Panel
    JPanel info = new JPanel();
    info.setBounds(0, 0, 200, 75);
    info.setBackground(mainPanel.black);
    info.setLayout(null);
    add(info);

    formatLabel1 = new JLabel("Enter Shifts", JLabel.CENTER);
    ComponentFormatter.formatBasicLarge(formatLabel1);
    formatLabel1.setBounds(0, 10, 192, 20);
    info.add(formatLabel1);

    formatLabel2 = new JLabel("(24hr Format)", JLabel.CENTER);
    ComponentFormatter.formatBasic(formatLabel2);
    formatLabel2.setBounds(0, 30, 192, 20);
    info.add(formatLabel2);

    // Add Panel
    JPanel addPanel = new JPanel();
    addPanel.setLayout(null);
    addPanel.setBackground(mainPanel.red);
    addPanel.setBounds(0, 75, 200, 300);
    add(addPanel);

    irb = new JRadioButton("InShop");
    ComponentFormatter.formatBasic(irb);
    irb.setBounds(15, 10, 100, 20);
    irb.setSelected(true);

    drb = new JRadioButton("Driver");
    ComponentFormatter.formatBasic(drb);
    drb.setBounds(100, 10, 100, 20);

    bg = new ButtonGroup();
    bg.add(irb);
    bg.add(drb);

    addPanel.add(irb);
    addPanel.add(drb);

    startEnd = new JLabel("Start  End");
    ComponentFormatter.formatBasic(startEnd);
    startEnd.setBounds(50, 40, 200, 20);
    addPanel.add(startEnd);

    startArea = new JTextField("11");
    startArea.setBounds(45, 60, 50, 30);
    addPanel.add(startArea);

    endArea = new JTextField("15");
    endArea.setBounds(100, 60, 50, 30);
    addPanel.add(endArea);

    addShift = new JButton("Add Shift");
    addShift.setBounds(50, 120, 100, 30);
    addShift.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent ae)
      {
        int startTime = -1, endTime = -1;
        boolean sFlag = true, eFlag = true;
        try
        {
          startTime = Integer.parseInt(startArea.getText());
        }
        catch (NumberFormatException nfe)
        {
          sFlag = false;
        }

        try
        {
          endTime = Integer.parseInt(endArea.getText());
        }
        catch (NumberFormatException nfe)
        {
          eFlag = false;
        }

        // Check values are within acceptable ranges
        if (startTime < 8 || startTime > 20)
        {
          sFlag = false;
        }
        if (endTime < 12 || endTime > 22)
        {
          eFlag = false;
        }
        
        if(startTime >= endTime)
        {
          sFlag = false;
          eFlag = false;
        }
        // Continue
        if (sFlag && eFlag)
        {
          startArea.setBackground(mainPanel.white);
          endArea.setBackground(mainPanel.white);
          ShiftType type;
          if (irb.isSelected())
            type = ShiftType.InShop;
          else
            type = ShiftType.Driver;

          String ampm = "";
          if (startTime <= 12)
            ampm = "am";
          else
            ampm = "pm";
          for (Day day : new Week())
          {
            if (!mainPanel.addShift(new Shift(startTime, endTime, day, type), ampm))
              break;
          }
        }
        if (!sFlag)
        {
          startArea.setBackground(Color.RED);
        }
        if (!eFlag)
        {
          endArea.setBackground(Color.RED);
        }
      }
    });
    addPanel.add(addShift);

    shiftEditor.setBounds(0, 390, 192, 250);
    add(shiftEditor);
  }

  public void switchToShiftEditor()
  {
    addShift.setEnabled(false);
    irb.setEnabled(false);
    drb.setEnabled(false);
    startArea.setText("");
    endArea.setText("");
    startArea.setEditable(false);
    endArea.setEditable(false);
  }

  public void switchOffShiftEditor()
  {
    addShift.setEnabled(true);
    irb.setEnabled(true);
    drb.setEnabled(true);
    startArea.setEditable(true);
    endArea.setEditable(true);
  }
}
