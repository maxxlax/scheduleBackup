package io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import gui.MainPanel;

public class Saver
{
	PrintWriter outputFile;

	public Saver(boolean input, MainPanel mainPanel)
	{
		//Input
		if (input)
		{
			
		}
		//Output
		else
		{
			try
			{
				outputFile = new PrintWriter("ScheduleSaveData.txt");
			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
