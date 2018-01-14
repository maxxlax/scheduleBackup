package gui;

import javax.swing.SwingUtilities;

import app.MultimediaApplication;

public class ScheduleMakerApplication extends MultimediaApplication
{

	/**
     * The entry-point of the application
     *
     * @param args    The command-line arguments
     */
    public static void main(String[] args) throws Exception
    {
       SwingUtilities.invokeAndWait(
          new ScheduleMakerApplication(args, 983, 657));
    }

	public ScheduleMakerApplication(String[] args, int width, int height)
	{
		super(args, new ScheduleMakerApp(), width, height);
	}

}
