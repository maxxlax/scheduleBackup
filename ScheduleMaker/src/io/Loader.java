package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import employee.Employee;
import gui.MainPanel;
import sales.DaySales;
import sales.WeeklySales;
import scheduling.Day;
import scheduling.Shift;
import scheduling.ShiftType;

public class Loader
{
  BufferedReader fileReader;
  MainPanel mainPanel;
  SaveTypes type;
  private boolean salesLoaded, employeesLoaded, amScheduleLoaded, pmScheduleLoaded;

  WeeklySales weeklySales;

  public Loader(String SAVE_FILE_NAME, MainPanel mainPanel)
  {
    this.mainPanel = mainPanel;
    salesLoaded = false;
    employeesLoaded = false;
    amScheduleLoaded = false;
    pmScheduleLoaded = false;
    try
    {
      FileReader fr = new FileReader(SAVE_FILE_NAME);
      fileReader = new BufferedReader(fr);
    }
    catch (FileNotFoundException fnf)
    {
      // TODO JOptionPane to announce fnf
    }
  }

  public void loadFiles() throws IOException
  {
    String line;
    while ((line = fileReader.readLine()) != null)
    {
      switch (line)
      {
        case "|":
          System.out.println("|");
          finishType();
          break;
        case "Sales":
          if(!salesLoaded)
          type = SaveTypes.SALES;
          createSales();
          break;
        case "Employees":
          if(!employeesLoaded)
          type = SaveTypes.EMPLOYEES;
          break;
        case "Schedule":
          break;
        case "am":
          if(!amScheduleLoaded)
          type = SaveTypes.AMSCHEDULE;
          break;
        case "pm":
          if(!pmScheduleLoaded)
          type = SaveTypes.PMSCHEDULE;
          break;
        default:
          StringTokenizer stoken = new StringTokenizer(line, "|");
          System.out.println(type + " " + stoken.countTokens());
          switch (type)
          {
            case SALES:
              addSale(stoken);
              break;
            case EMPLOYEES:
              mainPanel.addEmployee(parseEmployee(stoken));
              break;
            case AMSCHEDULE:
              parseScheduleItem("am", stoken);
              break;
            case PMSCHEDULE:
              parseScheduleItem("pm", stoken);
              break;
            default:
              break;
          }
          break;
      }
    }
    mainPanel.redrawSchedule();
  }

  private void finishType()
  {
    // TODO Set booleans to flag when a type is complete
    switch (type)
    {
      case SALES:
        salesLoaded = true;
        mainPanel.salesPanel.setWs(weeklySales);
        break;
      case EMPLOYEES:
        employeesLoaded = true;
        break;
      case AMSCHEDULE:
        amScheduleLoaded = true;
        break;
      case PMSCHEDULE:
        pmScheduleLoaded = true;
        break;
      default:
        break;
    }
  }

  private void createSales()
  {
    weeklySales = new WeeklySales();
  }

  private void addSale(StringTokenizer stoken)
  {
    String s = stoken.nextToken();
    System.out.println(s);
    Day day = Day.valueOf(s);
    int amSales = Integer.parseInt(stoken.nextToken());
    int pmSales = Integer.parseInt(stoken.nextToken());

    weeklySales.setSales(new DaySales(amSales, pmSales, day));
  }

  private Employee parseEmployee(StringTokenizer stoken)
  {
    String name = stoken.nextToken();
    boolean i = Boolean.parseBoolean(stoken.nextToken());
    boolean dr = Boolean.parseBoolean(stoken.nextToken());
    boolean dou = Boolean.parseBoolean(stoken.nextToken());
    int curr = Integer.parseInt(stoken.nextToken());
    int max = Integer.parseInt(stoken.nextToken());
    ArrayList<int[]> av = new ArrayList<int[]>();
    av.add(new int[] {Integer.parseInt(stoken.nextToken()), Integer.parseInt(stoken.nextToken())});
    av.add(new int[] {Integer.parseInt(stoken.nextToken()), Integer.parseInt(stoken.nextToken())});
    av.add(new int[] {Integer.parseInt(stoken.nextToken()), Integer.parseInt(stoken.nextToken())});
    av.add(new int[] {Integer.parseInt(stoken.nextToken()), Integer.parseInt(stoken.nextToken())});
    av.add(new int[] {Integer.parseInt(stoken.nextToken()), Integer.parseInt(stoken.nextToken())});
    av.add(new int[] {Integer.parseInt(stoken.nextToken()), Integer.parseInt(stoken.nextToken())});
    av.add(new int[] {Integer.parseInt(stoken.nextToken()), Integer.parseInt(stoken.nextToken())});
    System.out.println(name + "|" + i + "|" + dr + "|" + dou + "|" + curr + "|" + max + "|");
    for(int[] ii: av)
    {
      System.out.println(ii[0] + "|" + ii[1]);
    }
    Employee emp = new Employee(name, i, dr, dou, curr, max, av);
    return emp;
  }

  private void parseScheduleItem(String ampm, StringTokenizer stoken)
  {
    int start;
    int end;
    Day day;
    ShiftType type;
    Shift shift;
    boolean filled;
    String empName;
    Employee emp;

    start = Integer.parseInt(stoken.nextToken());
    end = Integer.parseInt(stoken.nextToken());
    day = Day.valueOf(stoken.nextToken());
    type = ShiftType.valueOf(stoken.nextToken());
    shift = new Shift(start, end, day, type);
    filled = Boolean.parseBoolean(stoken.nextToken());
    if (filled && employeesLoaded)
    {
      empName = stoken.nextToken();
      for (Employee e : mainPanel.employees)
      {
        System.out.println(mainPanel.employees);
        if (e.fullName.equals(empName))
        {
          emp = e;
          shift.setEmployee(emp);
          shift.filled = true;
          break;
        }
        else
        {
          shift.filled = false;
        }
      }
    }
    mainPanel.schedule.get(day).get(ampm).add(shift);
    Collections.sort(mainPanel.schedule.get(day).get(ampm));
    mainPanel.shiftsReady = true;
  }
}
