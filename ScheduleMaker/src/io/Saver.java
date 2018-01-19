package io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import employee.Employee;
import gui.MainPanel;
import sales.DaySales;
import sales.WeeklySales;
import scheduling.Day;
import scheduling.Shift;
import scheduling.Week;

public class Saver
{
	PrintWriter outputFile;
	MainPanel mainPanel;

	public Saver(MainPanel mainPanel)
	{
	  this.mainPanel= mainPanel;
	}
	
	public boolean saveAll()
	{
	    try
      {
        outputFile = new PrintWriter("ScheduleSaveData.txt");
        saveSales();
        saveEmployees();
        saveSchedule();
        outputFile.close();
      }
      catch (FileNotFoundException e)
      {
        e.printStackTrace();
        return false;
      }
	    return true;
	}

  private void saveSales()
  {
    outputFile.println("Sales");
    System.out.println("Sales");
    if(mainPanel.isSalesReady())
    {
      WeeklySales ws = mainPanel.getSalesPanel().getWs();
      for(DaySales ds: ws.getSales())
      {
        outputFile.println(ds.getDay() + "|" + ds.getAmSales() + "|" + ds.getPmSales());
        System.out.println(ds.getDay() + "|" + ds.getAmSales() + "|" + ds.getPmSales());
      }
    }
    outputFile.println("|");
  }
  
  private void saveEmployees()
  {
    outputFile.println("Employees");
    System.out.println("Employees");
    if(mainPanel.getEmployeeList().isReady())
    {
      for(Employee employee: mainPanel.getEmployeeList())
      {
        outputFile.println(employee.toSaveString());
        System.out.println(employee.toSaveString());
      }
    }
    outputFile.println("|");
  }

  private void saveSchedule()
  {
    outputFile.println("Schedule");
    System.out.println("Schedule");
    for(Day day: new Week())
    {
      outputFile.println("am");
      System.out.println("am");
      for(Shift shift: mainPanel.getSchedule().get(day).get("am"))
      {
        outputFile.println(shift.toSaveString());
        System.out.println(shift.toSaveString());
      }
      outputFile.println("pm");
      System.out.println("pm");
      for(Shift shift: mainPanel.getSchedule().get(day).get("pm"))
      {
        outputFile.println(shift.toSaveString());
        System.out.println(shift.toSaveString());
      }
    }
    outputFile.println("|");
  }
}
