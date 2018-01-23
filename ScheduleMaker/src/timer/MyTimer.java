package timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MyTimer extends Timer
{
  public MyTimer(JLabel weekLabel, JLabel timeLabel1, JLabel timeLabel2)
  {
    super(1000, new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        Calendar specialWeekCal = Calendar.getInstance();
        specialWeekCal.add(Calendar.DAY_OF_YEAR, -3);
        int week = specialWeekCal.get(Calendar.WEEK_OF_YEAR);
        
        Calendar now = Calendar.getInstance();
        weekLabel.setText("Week " + week);
        timeLabel1.setText(new SimpleDateFormat("hh:mm").format(now.getTime()));
        timeLabel2.setText(new SimpleDateFormat("yyyy/MM/dd").format(now.getTime()));
        //System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(now.getTime()));
      }
    });
  }
}
