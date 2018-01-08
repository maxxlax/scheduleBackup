package gui;
/**
 * 
 */
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 * @author Max Croston
 *
 */
@SuppressWarnings("serial")
public class MyFrame extends JFrame implements WindowListener
{
    public MyFrame()
    {
        super("MY GUI");
        //setLayout(null);
        setSize(1000, 677);
        Container cp = getContentPane();
        
        MainPanel mainPanel = new MainPanel();
        cp.add(mainPanel);
        
        addWindowListener(this);
        setVisible(true);
    }
    
    @Override
    public void windowClosing(WindowEvent evt)
    {
        System.exit(0);
    }

    @Override
    public void windowActivated(WindowEvent e){}

    @Override
    public void windowClosed(WindowEvent e){}

    @Override
    public void windowDeactivated(WindowEvent e){}

    @Override
    public void windowDeiconified(WindowEvent e){}

    @Override
    public void windowIconified(WindowEvent e){}

    @Override
    public void windowOpened(WindowEvent e){}
}
