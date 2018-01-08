package gui;

import javax.swing.JPanel;

import app.AbstractMultimediaApp;

public class ScheduleMakerApp extends AbstractMultimediaApp
{
	@Override
	public void init()
	{
		JPanel contentPane;
		System.out.println(rootPaneContainer);
		contentPane = (JPanel)rootPaneContainer.getContentPane();
        MainPanel mainPanel = new MainPanel();
        contentPane.add(mainPanel);
	}
}
