package timer;

import javax.swing.JLabel;

public class TimeRunner implements Runnable
{
  JLabel timeLabel1, timeLabel2, weekLabel;
  
  public TimeRunner(JLabel weekLabel, JLabel timeLabel1, JLabel timeLabel2)
  {
    this.weekLabel = weekLabel;
    this.timeLabel1 = timeLabel1;
    this.timeLabel2 = timeLabel2;
  }

  @Override
  public void run()
  {
    MyTimer timer = new MyTimer(weekLabel, timeLabel1, timeLabel2);
    timer.start();
  }

}
