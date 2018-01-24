package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;

public class ComponentFormatter
{

  public static void formatBasic(JComponent component)
  {
    component.setFont(new Font("Monospaced", Font.BOLD, 14));
    component.setForeground(Color.WHITE);
  }

  public static void formatBasicSmall(JComponent component)
  {
    component.setFont(new Font("Monospaced", Font.BOLD, 12));
    component.setForeground(Color.WHITE);
  }

  public static void formatBasicLarge(JComponent component)
  {
    component.setFont(new Font("Monospaced", Font.BOLD, 18));
    component.setForeground(Color.WHITE);
  }

}
