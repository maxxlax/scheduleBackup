package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class RotatedLabel extends JLabel
{
  private static final long serialVersionUID = 1L;
  private double radians = 1.5708;

  public RotatedLabel(String text, int x, int y)
  {
    super(text);
    setFont(new Font("Arial", Font.BOLD, 26));
    setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.LineBorder(Color.red, 1),
        getBorder()));
    int width = getPreferredSize().width;
    int height = getPreferredSize().height;
    setBounds(x, y, width, height);
  }

  @Override
  public void paintComponent(Graphics g)
  {
    Graphics2D gx = (Graphics2D) g;
    gx.rotate(radians, 50, 50);
    super.paintComponent(g);
  }

  public void setRotation(int angle)
  {
    this.radians = angle * .0174533;
  }

}
